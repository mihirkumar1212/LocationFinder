package com.example.locationfinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase  extends SQLiteOpenHelper {

    private Context context;
    // naming the database
    private static final String DATABASE_NAME = "LocationFinder.db";
    private static final int DATABASE_VERSION = 1;
    private static final String Table_Name = "LOCATION_FINDER";
    private static final String Column_ID = "ID";
    private static final String Column_ADDRESS = "Address";
    private static final String Column_Latitude = "Latitude";
    private static final String Column_Longitude = "Longitude";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Table_Name +
                " (" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_ADDRESS + " TEXT," +
                Column_Latitude + " DOUBLE," +
                Column_Longitude + " DOUBLE);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }
}
