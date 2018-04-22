package com.example.erickcruz.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JobDatabaseHelper extends SQLiteOpenHelper {
    private  static final  String TAG = "JobDatabaseHelper";

    public static final String TABLE_NAME = "Jobs";
    public static final String COL = "_id";
    public static final String COL1 = "companyName";
    public static final String COL2 = "fullName";
    public static final String COL3 = "jobTitle";
    public static final String COL4 = "jobDescription";

    public JobDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COL + " INTEGER PRIMARY KEY, " + COL1 + " TEXT, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String companyStr, String fullnameStr, String jobTitle, String jobDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, companyStr);
        contentValues.put(COL2, fullnameStr);
        contentValues.put(COL3, jobTitle);
        contentValues.put(COL4, jobDescription);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getJobDescription(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;

    }
}