package com.example.locationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Add_Location extends AppCompatActivity {
    TextView input1, input2, address_new;
    Button generateAddress;


    //Google API for location services.
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);


        input1 = findViewById(R.id.input_1);
        input2 = findViewById(R.id.input_2);
        address_new = findViewById(R.id.Address_new);
        generateAddress = findViewById(R.id.GenrateAddress);


        generateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double longitude = Double.parseDouble(input1.getText().toString());
                Double latitude = Double.parseDouble(input2.getText().toString());
                Geocoder geocoder = new Geocoder(Add_Location.this, Locale.getDefault());

                try {
                    List<Address>listAddress = geocoder.getFromLocation(44,-80,1);
                    if (listAddress.size() > 0){
                        //address_new.setText(listAddress.get(0).getCountryName());
                        Toast.makeText(Add_Location.this,listAddress.get(0).getCountryName(),Toast.LENGTH_LONG ).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });






    }
}