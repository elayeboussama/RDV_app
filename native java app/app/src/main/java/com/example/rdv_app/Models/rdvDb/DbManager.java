package com.example.rdv_app.Models.rdvDb;

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
        database.insert(MyDBHandler.TABLE_RDVS,null,cont);
    }

    public Cursor fetch(){
        Cursor c;
        c= database.rawQuery("SELECT * FROM "+MyDBHandler.TABLE_RDVS,null);
        if (c!=null){
            c.moveToFirst();
        }

        return c;
    }

    public int update(String _id,String date, String time, String title, String content){
        ContentValues cont= new ContentValues();

        cont.put(MyDBHandler.COLUMN_DATE, String.valueOf(date));
        cont.put(MyDBHandler.COLUMN_TIME, String.valueOf(time));
        cont.put(MyDBHandler.COLUMN_TITLE,title);
        cont.put(MyDBHandler.COLUMN_CONTENT,content);
        int res=database.update(MyDBHandler.TABLE_RDVS,cont,MyDBHandler.COLUMN_ID + "=" + _id,null);
        return res;
    }

    public void delete(String _id){
        try{
//            String query = " DELETE FROM " + MyDBHandler.TABLE_RDVS + " WHERE id = " + _id;
//            database.execSQL(" DELETE FROM " + MyDBHandler.TABLE_RDVS + " WHERE " + MyDBHandler.COLUMN_ID + " = " + _id);
//          System.out.println("ddddddddd((trrrrr: "+_id);
          database.delete(MyDBHandler.TABLE_RDVS,String.format( "%s = %s" ,MyDBHandler.COLUMN_ID,_id),null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

