package com.example.locationfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDatabase database;
    FloatingActionButton add;
    ArrayList<String> location_id, address, latitude, longitude;
    CustomAdapter customAdapter;
    public boolean displaySearchAddress = false;
    public String target;
    TextView searchView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new MyDatabase(MainActivity.this);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Location.class);
                startActivity(intent);

            }
        });
        searchView = findViewById(R.id.Search);
        database = new MyDatabase(MainActivity.this);
        location_id = new ArrayList<>();
        address = new ArrayList<>();
        latitude = new ArrayList<>();
        longitude = new ArrayList<>();

        DisplayAddress(target);
        callAdapter();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                target = searchView.getText().toString();
                displaySearchAddress = true;
                DisplayAddress(target);
                callAdapter();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void callAdapter(){
        recyclerView = findViewById(R.id.recyclerView);
        customAdapter = new CustomAdapter(MainActivity.this,this, location_id, address, latitude, longitude);
        recyclerView.setAdapter(customAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void DisplayAddress(String target) {
        Cursor cursor;
        if(displaySearchAddress){
            cursor = database.getSearchData(target);
        } else {
            cursor = database.getData();
        }

        if(cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "No available data in the database", Toast.LENGTH_SHORT).show();
        } else{
            location_id.clear();
            address.clear();
            longitude.clear();
            latitude.clear();

            while (cursor.moveToNext()){
                location_id.add(cursor.getString(0));
                address.add(cursor.getString(1));
                latitude.add(cursor.getString(2));
                longitude.add(cursor.getString(3));

            }
        }
    }
}