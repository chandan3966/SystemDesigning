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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddressActivity extends AppCompatActivity {

    Button button;
    EditText e1,e2,e3,e4,e5,e6,e7;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        if (Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        e1 = findViewById(R.id.fname);
        e2 = findViewById(R.id.lname);
        e3 = findViewById(R.id.local);
        e4 = findViewById(R.id.dist);
        e5 = findViewById(R.id.state);
        e6 = findViewById(R.id.country);
        e7 = findViewById(R.id.pin);

        sp = getSharedPreferences("mycredentials",Context.MODE_PRIVATE);
        final String phone = sp.getString("phone","NA");
        db = openOrCreateDatabase("ServiceDB", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM Address WHERE number='" + phone + "'", null);
        if (c.getCount()!=0 && c.moveToFirst()){
            e1.setText(c.getString(1));
            e2.setText(c.getString(2));
            e3.setText(c.getString(3));
            e4.setText(c.getString(4));
            e5.setText(c.getString(5));
            e6.setText(c.getString(6));
            e7.setText(c.getString(7));
        }
//        db.execSQL("CREATE TABLE IF NOT EXISTS Address(number VARCHAR,fname VARCHAR,lname VARCHAR,locality VARCHAR,district VARCHAR,state VARCHAR,country VARCHAR,pincode VARCHAR);");
        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(e1.getText().toString().length()<1 || e2.getText().toString().length()<1 ||e3.getText().toString().length()<1
                ||e4.getText().toString().length()<1 || e5.getText().toString().length()<1 || e6.getText().toString().length()<1
                ||e7.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(),"Please Enter a Valid Address",Toast.LENGTH_SHORT).show();
                }
                else{
                    Cursor c1 = db.rawQuery("SELECT * FROM Address WHERE number='" + phone + "'", null);
                    if (c1.getCount()!=0 && c1.moveToFirst()){
                        db.execSQL("UPDATE Address SET fname='" + e1.getText().toString() + "',lname='" + e2.getText().toString() + "',locality='" + e3.getText().toString() + "',district='" + e4.getText().toString() + "',state='" + e5.getText().toString() + "',country='" + e6.getText().toString() + "',pincode='" + e7.getText().toString() + "' WHERE number='" + phone + "'");
                        Intent i = new Intent(AddressActivity.this,BookingSummary.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        db.execSQL("INSERT INTO Address VALUES('" + phone + "','" + e1.getText().toString() + "','" + e2.getText().toString() + "','" + e3.getText().toString() + "','" + e4.getText().toString() + "','" + e5.getText().toString() + "','" + e6.getText().toString() + "','" + e7.getText().toString() + "');");
                        Intent i = new Intent(AddressActivity.this,Main2Activity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        });

    }
}
