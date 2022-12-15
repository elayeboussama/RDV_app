package com.example.rdv_app.Models.rdvDb;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.jetbrains.annotations.Nullable;

class MyDBHandler extends SQLiteOpenHelper {
    static final int DATABASE_VERSION =1 ;
    static final String DATABASE_NAME ="rdvDB.db" ;


    static final String TABLE_RDVS ="rdvs" ;
    static final String COLUMN_ID ="id_" ;
    static final String COLUMN_DATE ="date" ;
    static final String COLUMN_TIME ="time" ;
    static final String COLUMN_TITLE ="title" ;
    static final String COLUMN_CONTENT = "content";

    public MyDBHandler(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_RDVS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_TITLE + " TEXT," + COLUMN_CONTENT + " TEXT );";
        db.execSQL(CREATE_PRODUCTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RDVS);
        onCreate(db);
    }
}