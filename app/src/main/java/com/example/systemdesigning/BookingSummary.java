package com.example.systemdesigning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookingSummary extends AppCompatActivity {

    Button b,b2;
    SharedPreferences sp;
    SQLiteDatabase db;
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
        b2 = findViewById(R.id.button2);
        service = findViewById(R.id.service);
        serviceamt = findViewById(R.id.serviceamt);
        rcfinal = findViewById(R.id.rcfinal);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        final String phone = sp.getString("phone","NA");
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
        }


        db = openOrCreateDatabase("ServiceDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Request(number VARCHAR,service VARCHAR,serviceamt VARCHAR,date VARCHAR,time VARCHAR);");


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
                    db.execSQL("INSERT INTO Request VALUES('" + phone + "','" + services + "','" + serviceamt.getText().toString() + "','" + time.getText().toString() + "','" + dates + "');");
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
                Intent i = new Intent(BookingSummary.this,BookingHistory.class);
                startActivity(i);
                finish();
            }
        });

    }
}
