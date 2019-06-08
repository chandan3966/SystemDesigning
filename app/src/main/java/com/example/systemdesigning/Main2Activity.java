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
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Main2Activity extends AppCompatActivity {

    ImageView im,im1,im2,im3,im4,im5,im6,im7,im8;
    SharedPreferences sp;
    Button b,out;
    String phone;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db = openOrCreateDatabase("ServiceDB", Context.MODE_PRIVATE, null);

        sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        phone = sp.getString("phone","NA");


        out = findViewById(R.id.logout);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("DELETE FROM login WHERE number='" + phone + "'");
                Cursor c = db.rawQuery("SELECT * FROM login WHERE number='" +phone + "'",null);
                if (c.getCount()!=0 && c.moveToFirst()){
                    db.execSQL("UPDATE login SET status='" + "0" + "' WHERE number='" + phone + "'");
                }
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        b = findViewById(R.id.profile);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,Profile.class);
                startActivity(i);
            }
        });
        im = findViewById(R.id.acservice);
        im1 = findViewById(R.id.washing);
        im2 = findViewById(R.id.dishwash);
        im3 = findViewById(R.id.tv);
        im4 = findViewById(R.id.heater);
        im5 = findViewById(R.id.fridge);
        im6 = findViewById(R.id.sound);
        im7 = findViewById(R.id.chimney);
        im8 = findViewById(R.id.dish);
        if (Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im);
                Intent i = new Intent(Main2Activity.this,ServiceSelection.class);
                startActivity(i);
                Animatoo.animateFade(Main2Activity.this);
            }
        });
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im1);
            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im2);
            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im3);
            }
        });
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im4);
            }
        });
        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im5);
            }
        });
        im6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im6);
            }
        });
        im7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im7);
            }
        });
        im8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(im8);
            }
        });

    }
}
