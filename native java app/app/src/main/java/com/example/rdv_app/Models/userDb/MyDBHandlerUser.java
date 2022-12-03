package com.example.rdv_app.Models.userDb;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;

class MyDBHandlerUser extends SQLiteOpenHelper {
    static final int DATABASE_VERSION =1 ;
    static final String DATABASE_NAME ="rdvDB.db" ;


    static final String TABLE_USERS ="users" ;
    static final String COLUMN_ID ="id_" ;
    static final String COLUMN_NAME ="username" ;
    static final String COLUMN_PASSWORD ="password" ;

    public MyDBHandlerUser(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_PASSWORD + " TEXT );";

        String INSERT_USER = "INSERT INTO " + TABLE_USERS + "(" +
                COLUMN_ID + " ," + COLUMN_NAME + " ," + COLUMN_PASSWORD + " ) " +
                " values( \"Malek\", \"Malek\") ;";


        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(INSERT_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}