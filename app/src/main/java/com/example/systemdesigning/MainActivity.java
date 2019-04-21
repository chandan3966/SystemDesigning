package com.example.systemdesigning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.equals("") || et.length()<10){
                    Toast.makeText(getApplicationContext(),"Invalid number",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
