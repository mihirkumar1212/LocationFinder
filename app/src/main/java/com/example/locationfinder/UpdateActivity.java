package com.example.locationfinder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {
    TextView address, latitude, longitude;
    Button update_button, delete_button, generate_button;
    String address_txt, latitude_txt, longitude_txt, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        address = findViewById(R.id.Address_new1);
        latitude = findViewById(R.id.input_11);
        longitude = findViewById(R.id.input_21);
        update_button = findViewById(R.id.update);


        getIntentData();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(address_txt);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabase database = new MyDatabase(UpdateActivity.this);

                address_txt = address.getText().toString().trim();
                latitude_txt = latitude.getText().toString().trim();
                longitude_txt = longitude.getText().toString().trim();
                database.updateDatabase(id, address_txt, latitude_txt, longitude_txt);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        delete_button = findViewById(R.id.delete);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        generate_button = findViewById(R.id.GenrateAddress1);
        generate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress();
            }
        });


    }
    public void getIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("address") && getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")){
            id = getIntent().getStringExtra("id");
            address_txt = getIntent().getStringExtra("address");
            latitude_txt = getIntent().getStringExtra("latitude");
            longitude_txt = getIntent().getStringExtra("longitude");

            // store the string

            latitude.setText(latitude_txt);
            longitude.setText(longitude_txt);
        }else {
            Toast.makeText(UpdateActivity.this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public  void getAddress(){
        Geocoder geo = new Geocoder(UpdateActivity.this, Locale.getDefault());


        double latitude1 = Double.parseDouble(latitude.getText().toString());
        double  longitude1 = Double.parseDouble(longitude.getText().toString());
        try {
            List<Address> listAddress = geo.getFromLocation(latitude1,longitude1,1);
            if (listAddress.size() > 0){
                address.setText(listAddress.get(0).getAddressLine(0));
                //Toast.makeText(Add_Location.this, listAddress.get(0).getCountryName(),Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete \"" + address_txt + " \" ?");
        builder.setMessage(" Are you you sure you wish to delete \"" + address_txt + " \" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabase database = new MyDatabase(UpdateActivity.this);
                database.DeleteRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}