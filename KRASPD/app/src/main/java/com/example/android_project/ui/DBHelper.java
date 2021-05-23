package com.example.android_project.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "kraspd.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table kraspd(" +
                "id INTEGER primary key AUTOINCREMENT, " +
                "date TEXT, " +
                "place TEXT, " +
                "incident TEXT, " +
                "description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists kraspd");
        onCreate(DB);
    }

    public Boolean insertkraspd(String date, String place, String incident, String description)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("place", place);
        contentValues.put("incident", incident);
        contentValues.put("description", description);
        long result=DB.insert("kraspd", null, contentValues);
        return result != -1;
    }

    public Boolean deletedata (int id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from kraspd where id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = DB.delete("kraspd", "id=?", new String[]{String.valueOf(id)});
            return result != -1;
        } else {
            return false;
        }

    }

    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from kraspd", null);
    }
    public void deleteeverything()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("Delete from kraspd where id > -1");
    }
}

