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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookingHistory extends AppCompatActivity {

    SQLiteDatabase db;
    Button b;
    ListView lv;
    SharedPreferences sp;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String[] history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        if (Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
//        db.execSQL("CREATE TABLE IF NOT EXISTS Request(number VARCHAR,name VARCHAR,service VARCHAR,serviceamt VARCHAR,date VARCHAR,time VARCHAR,loacl VARCHAR,state VARCHAR,country VARCHAR);");
        lv = findViewById(R.id.listview);
        b = findViewById(R.id.button2);
        sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        final String phone = sp.getString("phone","NA");
        db = openOrCreateDatabase("ServiceDB", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM Request WHERE number='" + phone + "'", null);
        while (c.moveToNext()){
            list.add(c.getString(1)+"\n"+c.getString(2)+"\n"+c.getString(4)+"\n"+c.getString(5)+"\n"+c.getString(6)+"\n"+c.getString(7)+"\n"+c.getString(8));
        }
        adapter = new ArrayAdapter<String>(this,R.layout.listbooking, R.id.history,list);
        lv.setAdapter(adapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingHistory.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
