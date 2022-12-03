package com.example.rdv_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;
import java.util.Date;
import java.util.Timer;

public class DbManager {
    private MyDBHandler dbHandler;
    private Context context;
    private SQLiteDatabase database;

    public  DbManager(Context c){context=c;}
    public  void  open()throws SQLDataException {
        dbHandler = new MyDBHandler(context, null);
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public void insert( String date, String time, String title, String content){
        ContentValues cont= new ContentValues();
        cont.put(MyDBHandler.COLUMN_DATE, date);
        cont.put(MyDBHandler.COLUMN_TIME, time);
        cont.put(MyDBHandler.COLUMN_TITLE,title);
        cont.put(MyDBHandler.COLUMN_CONTENT,content);
        database.insert(MyDBHandler.TABLE_PRODUCTS,null,cont);
    }

    public Cursor fetch(){
        Cursor c;
        c= database.rawQuery("SELECT * FROM "+MyDBHandler.TABLE_PRODUCTS,null);
        if (c!=null){
            c.moveToFirst();
        }

        return c;
    }

    public int update(String _id,Date date, Timer time, String title, String content){
        ContentValues cont= new ContentValues();

        cont.put(MyDBHandler.COLUMN_DATE, String.valueOf(date));
        cont.put(MyDBHandler.COLUMN_TIME, String.valueOf(time));
        cont.put(MyDBHandler.COLUMN_TITLE,title);
        cont.put(MyDBHandler.COLUMN_CONTENT,content);
        int res=database.update(MyDBHandler.TABLE_PRODUCTS,cont,MyDBHandler.COLUMN_ID + "=" + _id,null);
        return res;
    }

    public void delete(String _id){
        database.delete(MyDBHandler.TABLE_PRODUCTS,MyDBHandler.COLUMN_ID + "=" +_id,null);
    }
}

