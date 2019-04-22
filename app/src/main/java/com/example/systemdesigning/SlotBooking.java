package com.example.systemdesigning;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SlotBooking extends AppCompatActivity {

    ImageView mr,af1,af2,af3;
    Button b;
    TextView t;
    int mr1=0,aft1=0,aft2=0,aft3=0;//for image shifting
    DatePickerDialog timePickerDialog;
    int[] times = {11,1,3,5};
    SharedPreferences sp;
    int timepick = 0;
    int finaltime = 0;
    String finaldate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_booking);
        if (Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        sp = getSharedPreferences("mycredentials",Context.MODE_PRIVATE);
        final String phone = sp.getString("phone","NA");
        final String names = sp.getString("name","NA");
        final String aplis = sp.getString("appliances","NA");
        final String service = sp.getString("service","NA");
        final int price = sp.getInt("price",0);

        t = findViewById(R.id.textView4);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog timePickerDialog = new DatePickerDialog(SlotBooking.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        view.setMinDate(Calendar.getInstance());
                        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
                        t.setText(dayOfMonth + " " + months[month] + "," + year);
                    }
                },0, 0, 0);  //time picker starts with 12 o clock
// use currentHour and currentMinute in the above line makes the time picker starts with current time
                timePickerDialog.show();
            }
        });

        mr = findViewById(R.id.morning1);
        mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mr1 == 0){
                    mr.setImageResource(R.drawable.morning1);
                    mr1++;
                    timepick += 1;
                }
                else{
                    mr.setImageResource(R.drawable.morning);
                    mr1--;
                    timepick -= 1;
                }
            }
        });


        af1 = findViewById(R.id.after1);
        af1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aft1 == 0){
                    af1.setImageResource(R.drawable.afternoon11);
                    aft1++;
                    timepick += 2;
                }
                else{
                    af1.setImageResource(R.drawable.afternoon1);
                    aft1--;
                    timepick -= 2;
                }
            }
        });

        af2 = findViewById(R.id.after2);
        af2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aft2 == 0){
                    af2.setImageResource(R.drawable.afternoon21);
                    aft2++;
                    timepick += 3;
                }
                else{
                    af2.setImageResource(R.drawable.afternoon2);
                    aft2--;
                    timepick -= 3;
                }
            }
        });

        af3 = findViewById(R.id.after3);
        af3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aft3 == 0){
                    af3.setImageResource(R.drawable.afternoon31);
                    aft3++;
                    timepick += 4;
                }
                else{
                    af3.setImageResource(R.drawable.afternoon3);
                    aft3--;
                    timepick -= 4;
                }

            }
        });

        b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = t.getText().toString();

                int year = Calendar.getInstance().get(Calendar.YEAR);

                if(s.equals("Enter Date")){
                    Toast.makeText(getApplicationContext(),"Please select date",Toast.LENGTH_SHORT).show();
                }
                else if((mr1 == 0 && aft1 == 0 && aft2 == 0 && aft3 == 0) ){
                    Toast.makeText(getApplicationContext(),"Please select time slot",Toast.LENGTH_SHORT).show();
                }
                else if ((mr1+aft1+aft2+aft3)!=1){
                    Toast.makeText(getApplicationContext(),"Please select one time slot",Toast.LENGTH_SHORT).show();
                }
                else{
                    finaltime = times[timepick-1];
                    finaldate = t.getText().toString();
                    sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("phone",phone);
                    edit.putString("name",names);
                    edit.putString("appliances",aplis);
                    edit.putString("service",service);
                    edit.putInt("price",price);
                    edit.putInt("time",finaltime);
                    edit.putString("date",finaldate);
                    edit.commit();
                    Intent i = new Intent(SlotBooking.this,BookingSummary.class);
                    startActivity(i);
                    YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(b);
                }
            }
        });


    }
}
