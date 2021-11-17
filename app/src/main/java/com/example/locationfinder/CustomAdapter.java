package com.example.locationfinder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    int pos;
    private final ArrayList address, latitude, longitude, location_id;
    Activity activity;
    CustomAdapter( Activity activity, Context context,
                   ArrayList location_id,
                   ArrayList address,
                   ArrayList latitude,
                   ArrayList longitude){
        this.activity = activity;
        this.context = context;
        this.location_id = location_id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.location_display, parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.address_txt.setText(String.valueOf(address.get(position)));
        holder.latitude_text.setText("Latitude: "+ String.valueOf(latitude.get(position)));
        holder.longitude_txt.setText("Longitude: "+ String.valueOf(longitude.get(position)));

        holder.cardBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = holder.getAdapterPosition();
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("id", String.valueOf(location_id.get(pos)));
                intent.putExtra("address", String.valueOf(address.get(pos)));
                intent.putExtra("latitude", String.valueOf(latitude.get(pos)));
                intent.putExtra("longitude", String.valueOf(longitude.get(pos)));
                //context.startActivity(intent);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {

        return address.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address_txt, latitude_text, longitude_txt;
        CardView cardBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address_txt = itemView.findViewById(R.id.Address_txt);
            latitude_text = itemView.findViewById(R.id.latitude_txt);
            longitude_txt = itemView.findViewById(R.id.longitude_txt);
            cardBackground = itemView.findViewById(R.id.backgroundCard);
        }
    }
}
