package com.example.erickcruz.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private  static final  String TAG = "UserDatabaseHelper";

    public static final String TABLE_NAME = "UserRegister";
    public static final String COL = "_id";
    public static final String COL1 = "company";
    public static final String COL2 = "fullname";
    public static final String COL3 = "username";
    public static final String COL4 = "password";
    public static final String COL5 = "usertype";

    public UserDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COL + " INTEGER PRIMARY KEY, " + COL1 + " TEXT, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String company, String fullname, String username, String password, String usertype) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, company);
        contentValues.put(COL2, fullname);
        contentValues.put(COL3, username);
        contentValues.put(COL4, password);
        contentValues.put(COL5, usertype);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor viewData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL3 + " = ?", new String[] { username });
        if (res.getCount() > 0 ) {
            return res;
        } else {
            return null;
        }
    }
}
