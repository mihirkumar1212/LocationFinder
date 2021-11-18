package com.example.locationfinder;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        //random_button = findViewById(R.id.Random_button);
        //randomNumber = findViewById(R.id.random_number);
        save_button = findViewById(R.id.Save);


        generateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress();
            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = address_new.getText().toString();
                // calling a function to store the data in the database.
                database = new MyDatabase(Add_Location.this);
                database.addLocation(address,String.valueOf(latitude),String.valueOf(longitude));
                Intent intent = new Intent(Add_Location.this, MainActivity.class);
                startActivity(intent);

            }
        });


        /*random_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //randomNum();
            }
        });*/
    }
    public void randomNum(){
        double result = Double.parseDouble(randomNumber(-90.0, 90.0));
        double result2 = Double.parseDouble(randomNumber(-180.0, 80.0));
        input1.setText(String.valueOf(result));
        input2.setText(String.valueOf(result2));
    }

    public  void getAddress(){
        //randomNum();
        Geocoder geo = new Geocoder(Add_Location.this, Locale.getDefault());

        longitude = Double.parseDouble(input2.getText().toString());
        latitude = Double.parseDouble(input1.getText().toString());
        if(latitude >= -90.0 && latitude  <= 90.0 && longitude >= -180.0 && longitude <= 80.0) {
            try {
                List<Address> listAddress = geo.getFromLocation(latitude, longitude, 1);
                if(listAddress.size() == 0){
                    Toast.makeText(Add_Location.this, "No address found try different coordinates",Toast.LENGTH_SHORT).show();
                }
                if (listAddress.size() > 0) {
                    if (listAddress.get(0).getAddressLine(0) != null) {
                        address_new.setText(listAddress.get(0).getAddressLine(0));
                        Toast.makeText(Add_Location.this, "Found address",Toast.LENGTH_SHORT).show();
                        if(listAddress.get(0).getPostalCode() != null){
                            String temp = listAddress.get(0).getAddressLine(0) + listAddress.get(0).getPostalCode();
                            address_new.setText(temp);
                            Toast.makeText(Add_Location.this, "Found address and Postal code",Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Toast.makeText(Add_Location.this, listAddress.get(0).getCountryName(),Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(Add_Location.this, "Invalid Longitude and Latitude!",Toast.LENGTH_SHORT).show();
        }
    }
    public String randomNumber (Double min, Double max){
        double num;
        num = (Math.random() * (max - min)) + min;

        return String.format("%.1f", num);
    }

}