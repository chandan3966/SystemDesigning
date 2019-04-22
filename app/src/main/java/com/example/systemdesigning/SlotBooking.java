package com.example.systemdesigning;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    int mr1=0,aft1=0,aft2=0,aft3=0;
    DatePickerDialog timePickerDialog;
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
        t = findViewById(R.id.textView4);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Date c = Calendar.getInstance().getTime();
//                System.out.println("Current time => " + c);
//
//                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//                String formattedDate = df.format(c);
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
                }
                else{
                    mr.setImageResource(R.drawable.morning);
                    mr1--;
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
                }
                else{
                    af1.setImageResource(R.drawable.afternoon1);
                    aft1--;
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
                }
                else{
                    af2.setImageResource(R.drawable.afternoon2);
                    aft2--;
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
                }
                else{
                    af3.setImageResource(R.drawable.afternoon3);
                    aft3--;
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
                    Intent i = new Intent(SlotBooking.this,BookingSummary.class);
                    startActivity(i);
                    YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(b);
                }
            }
        });


    }
}
