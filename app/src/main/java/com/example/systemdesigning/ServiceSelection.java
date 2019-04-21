package com.example.systemdesigning;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class ServiceSelection extends AppCompatActivity {

    RadioButton r1,r2,r3,r4,r5,r6;
    RadioGroup rg;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_selection);
        if (Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r1.isChecked() || r2.isChecked() || r3.isChecked() || r4.isChecked() || r5.isChecked() || r6.isChecked()){
                    Intent i = new Intent(ServiceSelection.this,SlotBooking.class);
                    startActivity(i);
                    YoYo.with(Techniques.RubberBand).duration(600).repeat(0).playOn(b);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select something",Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    public void onclicked(View v){
        rg = findViewById(R.id.radiogroup);
        r1 = findViewById(R.id.radioButton1);
        r2 = findViewById(R.id.radioButton);
        r3 = findViewById(R.id.radioButton2);
        r4 = findViewById(R.id.radioButton3);
        r5 = findViewById(R.id.radioButton4);
        r6 = findViewById(R.id.radioButton5);
        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()){
            case R.id.radioButton:
                if(checked)
                    r2.setChecked(true);
                break;

            case R.id.radioButton1:
                if(checked)
                    r1.setChecked(true);
                break;
            case R.id.radioButton2:
                if(checked)
                    r3.setChecked(true);
                break;
            case R.id.radioButton3:
                if(checked)
                    r4.setChecked(true);
                break;
            case R.id.radioButton4:
                if(checked)
                    r5.setChecked(true);
                break;
            case R.id.radioButton5:
                if(checked)
                    r6.setChecked(true);
                break;

        }

    }


}
