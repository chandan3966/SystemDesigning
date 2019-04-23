package com.example.systemdesigning;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button b;
    SQLiteDatabase db;
    SharedPreferences sp;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        b = findViewById(R.id.button);

        if(!checkPermission())
        {
            requestPermission();
        }


        db = openOrCreateDatabase("ServiceDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS useraccount(number VARCHAR,name VARCHAR,appliance VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Address(number VARCHAR,fname VARCHAR,lname VARCHAR,locality VARCHAR,district VARCHAR,state VARCHAR,country VARCHAR,pincode VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Request(number VARCHAR,name VARCHAR,service VARCHAR,serviceamt VARCHAR,date VARCHAR,time VARCHAR,loacl VARCHAR,state VARCHAR,country VARCHAR);");


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.equals("") || et.length()<10){
                    Toast.makeText(getApplicationContext(),"Invalid number",Toast.LENGTH_SHORT).show();
                }
                else{
                    Cursor c = db.rawQuery("SELECT * FROM useraccount WHERE number='" + et.getText().toString() + "'", null);
                        if(c.getCount()!=0 && c.moveToFirst()){
                            if (c.getString(1).equals("0") || c.getString(2).equals("0")){
                                sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("phone",et.getText().toString());
                                edit.putString("name","");
                                edit.putString("appliances","");
                                edit.commit();
                                Toast.makeText(getApplicationContext(),"Kindly check Name or Appliance",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this,Profile.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(),"Exists",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("phone",et.getText().toString());
                                edit.putString("name",c.getString(1));
                                edit.putString("appliances",c.getString(2));
                                edit.commit();
                                Cursor c2 = db.rawQuery("SELECT * FROM Address WHERE number='" + et.getText().toString() + "'", null);
                                if (c2.getCount()!=0 && c2.moveToFirst()){
                                    Intent i = new Intent(MainActivity.this,Main2Activity.class);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(),"Exists",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Intent i = new Intent(MainActivity.this,AddressActivity.class);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(),"Exists",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }



                        }
                        else{
                            sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("phone",et.getText().toString());
                            edit.putString("name","");
                            edit.putString("appliances","");
                            edit.commit();

                            db.execSQL("INSERT INTO useraccount VALUES('" + et.getText().toString() + "','" + 0 + "','" + 0 + "');");
                            Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this,Profile.class);
                            startActivity(i);
                            finish();
                        }

                }
            }
        });


    }


        private boolean checkPermission() {

            int result = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
            int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);

            return result == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED;
        }

        private void requestPermission() {

            ActivityCompat.requestPermissions(this, new String[]{RECEIVE_SMS,SEND_SMS}, PERMISSION_REQUEST_CODE);

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean messageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean messageAccepted1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (messageAccepted && messageAccepted1)
                    {
                        Toast.makeText(MainActivity.this, "Permissions provided", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(RECEIVE_SMS)) {
                                showMessageOKCancel("You need to allow access to the required permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{RECEIVE_SMS,SEND_SMS},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }

                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                        finish();
                    }
                })
                .create()
                .show();
    }
}
