package com.example.locationfinder;


import androidx.appcompat.app.AppCompatActivity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Add_Location extends AppCompatActivity {
    TextView input1, input2, address_new, randomNumber;
    Button generateAddress, random_button, save_button;
    MyDatabase database;
    double longitude, latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        input1 = findViewById(R.id.input_1);
        input2 = findViewById(R.id.input_2);
        address_new = findViewById(R.id.Address_new);
        generateAddress = findViewById(R.id.GenrateAddress);
        random_button = findViewById(R.id.Random_button);
        randomNumber = findViewById(R.id.random_number);
        save_button = findViewById(R.id.Save);


        generateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets the address form the user
                Geocoder geo = new Geocoder(Add_Location.this, Locale.getDefault());

                longitude = Double.parseDouble(input2.getText().toString());
                latitude = Double.parseDouble(input1.getText().toString());
                try {
                    List<Address> listAddress = geo.getFromLocation(latitude,longitude,1);
                    if (listAddress.size() > 0){
                        address_new.setText(listAddress.get(0).getAddressLine(0));
                        // Toast.makeText(Add_Location.this, listAddress.get(0).getCountryName(),Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = address_new.getText().toString();
                // calling a function to store the data in the database.
                database = new MyDatabase(Add_Location.this);
                database.addLocation(address,String.valueOf(latitude),String.valueOf(longitude));

            }
        });


        random_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result = Double.parseDouble(randomNumber(-90.0, 90.0));
                double result2 = Double.parseDouble(randomNumber(-180.0, 80.0));
                input1.setText(String.valueOf(result));
                input2.setText(String.valueOf(result2));
                //randomnumber.setText(String.format("%.2f",result));
            }
        });
    }

    public String randomNumber (Double min, Double max){
        double num;
        num = (Math.random() * (max - min)) + min;

        return String.format("%.1f", num);
    }

}