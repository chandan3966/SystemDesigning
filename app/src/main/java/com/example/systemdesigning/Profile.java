package com.example.systemdesigning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import java.util.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Profile extends AppCompatActivity {

    SQLiteDatabase db;
    Button b,b1;
    SharedPreferences sp,sp1;
    EditText e1,e2;
    AutoCompleteTextView e3;
    boolean visible;
    String[] countries = new String[]{"Haier","Daikin","Voltas","LG","Mitsubishi","Bluestar","Hitachi","Samsung","Carrier","Godrej","Whirlpool"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        e1 = findViewById(R.id.username);
        e2 = findViewById(R.id.number);
        e3 = findViewById(R.id.brand);
        b = findViewById(R.id.button2);
        b1 = findViewById(R.id.bhistory);

        ArrayAdapter<String> ada = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        e3.setAdapter(ada);


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


        e2.setText(phone);
        if (e1.getText().toString().equals("0")) {
            e1.setText("");
        } else {
            e1.setText(names);
        }
        if (e3.getText().toString().equals("0")) {
            e3.setText("");
        } else {
            e3.setText(aplis);
        }
        db = openOrCreateDatabase("ServiceDB", Context.MODE_PRIVATE, null);
        Cursor c1 = db.rawQuery("SELECT * FROM Request WHERE number='" +phone + "'", null);
        if (c1.getCount()!=0){
            b1.setVisibility(View.VISIBLE);
        }
        else{
            b1.setVisibility(View.INVISIBLE);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,BookingHistory.class);
                startActivity(i);
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(b1);
                finish();
            }
        });
//        sp = getSharedPreferences("Profiledetails", Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("phone",et.getText().toString());
//        edit.commit();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (e2.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),"Please enter valid number",Toast.LENGTH_SHORT).show();
                }
                else if (e1.getText().toString().length()<5){
                    Toast.makeText(getApplicationContext(),"Username shouldn't be less than 5 characters",Toast.LENGTH_SHORT).show();
                }
                else if (e3.getText().toString().length()<3 && !(Arrays.asList(countries).contains(e3.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"Appliance should be valid",Toast.LENGTH_SHORT).show();
                }
                else{
                    Cursor c = db.rawQuery("SELECT * FROM useraccount WHERE number='" + e2.getText().toString() + "'", null);
                    if (c.getCount()>1 ){
                        Toast.makeText(getApplicationContext(),"Already account exists ,try another",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        db.execSQL("UPDATE useraccount SET number='" + e2.getText().toString() + "',name='" + e1.getText().toString() + "',appliance='" + e3.getText().toString() + "' WHERE number='" + phone + "'");
                        sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("name",e1.getText().toString());
                        edit.putString("phone",e2.getText().toString());
                        edit.putString("appliances",e3.getText().toString());
                        edit.commit();
                        Cursor c1 = db.rawQuery("SELECT * FROM Address WHERE number='" + phone + "'", null);
                        if (c1.getCount()!=0 && c1.moveToFirst()){

                            Intent i = new Intent(Profile.this,Main2Activity.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Intent i = new Intent(Profile.this,AddressActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }
                }

            }
        });

    }

    public static boolean useSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return set.contains(targetValue);
    }
}
