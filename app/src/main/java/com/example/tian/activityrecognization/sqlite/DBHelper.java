package com.example.tian.activityrecognization.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by tupac on 12/6/2016.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "SQLiteData.db";
    private static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "activity";
    public static final String ACTIVITY_ID = "_id";
    public static final String ACTIVITY_NAME = "a_name";
    public static final String ACTIVITY_CONFIDENCE = "a_confidence";
    public static final String ACTIVITY_TIME = "a_time";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                ACTIVITY_ID + " INTEGER PRIMARY KEY, " +
                ACTIVITY_NAME + " TEXT, " +
                ACTIVITY_CONFIDENCE + " INTEGER, " +
                ACTIVITY_TIME + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public synchronized int getID(){
        int id = -2;

        SQLiteDatabase db = getReadableDatabase();
        id = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();

        return id + 1;
    }

    public boolean insertActivity(int id, String name, int confidence, long time) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTIVITY_ID, id);
        contentValues.put(ACTIVITY_NAME, name);
        contentValues.put(ACTIVITY_CONFIDENCE, confidence);
        contentValues.put(ACTIVITY_TIME, time);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateActivity(int id, String name, int confidence, int time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTIVITY_NAME, name);
        contentValues.put(ACTIVITY_CONFIDENCE, confidence);
        contentValues.put(ACTIVITY_TIME, time);
        db.update(TABLE_NAME, contentValues, ACTIVITY_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getActivity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " +
                ACTIVITY_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }
    public Cursor getAllActivities() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        return res;
    }

    public Integer deleteActivity(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                ACTIVITY_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }


    public Cursor getAllActivitiesFor(int i) {

//        long yourmilliseconds = Long.parseLong();
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
//        Date resultdate = new Date(yourmilliseconds);
//        sdf.format(resultdate);

        long DAY = i * 60 * 60 * 1000;

        long past = System.currentTimeMillis() - DAY;

        SQLiteDatabase db = this.getReadableDatabase();

        //Define projection
        String[] projection = {
                ACTIVITY_ID,
                ACTIVITY_NAME,
                ACTIVITY_TIME
        };

        //selection
        String selection = ACTIVITY_TIME + " >=?";
        String [] selectionArgs = {Long.toString(past)};


        Cursor res = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        return res;
    }
}
