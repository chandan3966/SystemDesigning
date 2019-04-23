package com.example.systemdesigning;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class BookingSummary extends AppCompatActivity {

    Button b,b2;
    SharedPreferences sp,sp1;
    SQLiteDatabase db;
    String fn,ln,pin;
    String phone,message,operator;
    TextView service,serviceamt,rcfinal,date,time,local,state,country,pho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);
        if (Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        db = openOrCreateDatabase("ServiceDB", Context.MODE_PRIVATE, null);


        b2 = findViewById(R.id.button2);
        service = findViewById(R.id.service);
        serviceamt = findViewById(R.id.serviceamt);
        rcfinal = findViewById(R.id.rcfinal);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        local = findViewById(R.id.local);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        pho = findViewById(R.id.phone);

        sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        phone = sp.getString("phone","NA");
        final String names = sp.getString("name","NA");
        final String aplis = sp.getString("appliances","NA");
        final String services = sp.getString("service","NA");
        final int price = sp.getInt("price",0);
        final int times = sp.getInt("time",0);
        final String dates = sp.getString("date","NA");

        service.setText(services);
        serviceamt.setText("Rs."+price+".00");
        rcfinal.setText("Rs."+price+".00");
        date.setText(dates);
        if (times == 11)
            time.setText(times+":00am to 1:00pm");
        else
            time.setText(times+":00pm to "+(times+2)+":00pm");
        Cursor c = db.rawQuery("SELECT * FROM Address WHERE number='" + phone + "'", null);
        if (c.getCount()!=0 && c.moveToFirst()){
            local.setText(c.getString(3));
            state.setText(c.getString(5));
            country.setText(c.getString(6));
            pho.setText(c.getString(0));
            pin = c.getString(7);
            fn = c.getString(1);
            ln = c.getString(2);
        }




        b = findViewById(R.id.address);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(BookingSummary.this,AddressActivity.class);
                    startActivity(i);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * FROM Request WHERE date='" + date.getText().toString() + "' and time='" + time.getText().toString() + "'", null);
                if (c.getCount()!=0){
                    Toast.makeText(getApplicationContext(),"Slot Already Booked",Toast.LENGTH_SHORT).show();
                }
                else {
                    db.execSQL("INSERT INTO Request VALUES('" + phone + "','" + (fn+" "+ln) + "','" + services + "','" + serviceamt.getText().toString() + "','" + time.getText().toString() + "','" + dates + "','" + local.getText().toString() + "','" + state.getText().toString() + "','" + country.getText().toString() + "');");
                    message = "Dear "+fn+" "+ln+",\nHave booked a service on "+date.getText().toString()+","+time.getText().toString()+".\nFor any queries contact:1234567890";
                    operator ="Mr/Mrs. "+fn+" "+ln+",\nHave booked a service on "+date.getText().toString()+","+time.getText().toString()+".\nLocated at:"+local.getText().toString()+"\n\t"+state.getText().toString()+" ,"+country.getText().toString()+"\n"+pin;
                    showdialogbox();
                }
            }
        });
    }



    public void showdialogbox(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialogbox,null);
        Button confirmed = view.findViewById(R.id.button3);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.show();
        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Booking Confirmed!",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

                Intent intent=new Intent(getApplicationContext(),BookingHistory.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                SmsManager sms= SmsManager.getDefault();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SmsManager sms1= SmsManager.getDefault();
                        sms1.sendTextMessage(phone,null,message,null,null);
                    }
                },1000);
                sms.sendTextMessage("8220341247",null,operator,pi,null);
                Toast.makeText(getApplicationContext(), "Your Request will be proceeded soon!",
                        Toast.LENGTH_LONG).show();

            }
        });

    }
}
