package com.example.locationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
                Column_Latitude + " TEXT," +
                Column_Longitude + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    void addLocation(String address, String latitude, String longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_ADDRESS, address);
        contentValues.put(Column_Latitude, latitude);
        contentValues.put(Column_Longitude, longitude);

        long check_result = db.insert(Table_Name, null, contentValues);
        if (check_result == -1) {
            Toast.makeText(context, "Failed to add note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Note Added Successfully!!!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getData() {
        String query = "SELECT * FROM " + Table_Name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor getSearchData(String target) {

        String query = "SELECT * FROM " + Table_Name + " WHERE " + Column_ADDRESS +" Like " + "'" +target + "%'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    void updateDatabase(String id, String address, String latitude, String longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_ADDRESS , address);
        contentValues.put(Column_Latitude , latitude);
        contentValues.put(Column_Longitude , longitude);
        long result = db.update (Table_Name, contentValues , Column_ID +"=?", new String[] {id} );

        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            System.out.println(" the result is :"+ result);
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    void DeleteRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete (Table_Name, Column_ID +"=?", new String[] {id} );
        if(result == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else {
            System.out.println(" the result is :"+ result);
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
