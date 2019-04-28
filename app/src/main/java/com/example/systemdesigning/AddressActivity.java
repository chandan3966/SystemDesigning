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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class AddressActivity extends AppCompatActivity {

    Button button;
    EditText e1,e2,e3,e4,e7;
    AutoCompleteTextView e6,e5;
    SQLiteDatabase db;
    SharedPreferences sp;
    String[] countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
    String[] states = {"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal","Andaman and Nicobar","Chandigarh","Dadra and Nagar Haveli","Daman and Diu","Lakshadweep","Delhi","Puducherry"};
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

        ArrayAdapter<String> ada = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,countries);
        e6.setAdapter(ada);

        ArrayAdapter<String> ada1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,states);
        e5.setAdapter(ada1);

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
                else if (e6.getText().toString().equals("India") && !(Arrays.asList(states).contains(e5.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"Please Enter a Valid State",Toast.LENGTH_SHORT).show();
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
