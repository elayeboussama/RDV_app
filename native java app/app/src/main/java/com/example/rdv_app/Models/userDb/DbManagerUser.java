package com.example.rdv_app.Models.userDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;
import java.util.Date;
import java.util.Timer;

public class DbManagerUser {
    private MyDBHandlerUser dbHandlerUser;
    private Context context;
    private SQLiteDatabase database;

    public DbManagerUser(Context c){context=c;}
    public  void  open()throws SQLDataException {
        System.out.println("ds user open");
        dbHandlerUser = new MyDBHandlerUser(context, null);
        database = dbHandlerUser.getWritableDatabase();
        dbHandlerUser.onCreate(database);
    }

    public void close() {
        dbHandlerUser.close();
    }

    public void insert( String name, String password){
        ContentValues cont= new ContentValues();
        cont.put(MyDBHandlerUser.COLUMN_NAME, name);
        cont.put(MyDBHandlerUser.COLUMN_PASSWORD, password);
        database.insert(MyDBHandlerUser.TABLE_USERS,null,cont);
    }

    public Cursor fetch(){
        Cursor c;
        c= database.rawQuery("SELECT * FROM "+ MyDBHandlerUser.TABLE_USERS,null);
        if (c!=null){
            c.moveToFirst();
        }

        return c;
    }

    public int update(String _id, String name, String password){
        ContentValues cont= new ContentValues();

        cont.put(MyDBHandlerUser.COLUMN_NAME, String.valueOf(name));
        cont.put(MyDBHandlerUser.COLUMN_PASSWORD, String.valueOf(password));
        int res=database.update(MyDBHandlerUser.TABLE_USERS,cont, MyDBHandlerUser.COLUMN_ID + "=" + _id,null);
        return res;
    }

    public void delete(String _id){
        database.delete(MyDBHandlerUser.TABLE_USERS, MyDBHandlerUser.COLUMN_ID + "=" +_id,null);
    }
}

