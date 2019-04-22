package com.example.systemdesigning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button b;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        b = findViewById(R.id.button);
        db = openOrCreateDatabase("ServiceDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS useraccount(number VARCHAR,name VARCHAR,appliance VARCHAR);");

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
                                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(),"Exists",Toast.LENGTH_SHORT).show();
                                finish();
                            }



                        }
                        else{
                            sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("phone",et.getText().toString());
                            edit.commit();

                            db.execSQL("INSERT INTO useraccount VALUES('" + et.getText().toString() + "','" + 0 + "','" + 0 + "');");
                            Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this,Main2Activity.class);
                            startActivity(i);
                            finish();
                        }

                }
            }
        });


    }
}
