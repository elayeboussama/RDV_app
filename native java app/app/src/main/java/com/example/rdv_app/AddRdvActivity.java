package com.example.rdv_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class AddRdvActivity extends AppCompatActivity {
    public Button newRDVBTN;
    public Button cancelRDVBTN;

    public Button List;
    public EditText date_et;
    public EditText time_et;
    public EditText title_et;
    public EditText content_et;
    private DbManager db;
    final int  LAUNCH_SECOND = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rdv_activity);
        db = new DbManager(this);
        try {
            db.open();
        } catch (SQLDataException throwables) {
            throwables.printStackTrace();
        }

        ArrayList<RDV> data = new ArrayList<RDV>();
        Cursor c = db.fetch();
        if (c == null) {
            Toast.makeText(this, "there is no data in the table", Toast.LENGTH_LONG).show();
        } else {

            if (c.moveToFirst()){


                do{
                    @SuppressLint("Range")
                    int idFromCursor = Integer.parseInt(c.getString(c.getColumnIndex("id_")));
                    @SuppressLint("Range")
                    String dateFromCursor = c.getString(c.getColumnIndex("date"));
                    @SuppressLint("Range")
                    String timeFromCursor = c.getString(c.getColumnIndex("time"));
                    @SuppressLint("Range")
                    String titleFromCursor = c.getString(c.getColumnIndex("title"));
                    @SuppressLint("Range")
                    String contentFromCursor = c.getString(c.getColumnIndex("content"));

                    data.add(new RDV(idFromCursor,dateFromCursor,timeFromCursor,titleFromCursor,contentFromCursor));

                }while(c.moveToNext());


                }

            }
            c.close();

        newRDVBTN = findViewById(R.id.btn_rdv_add);
        cancelRDVBTN = findViewById(R.id.btn_rdv_cancel);


        date_et = findViewById(R.id.et_rdv_date);
        time_et = findViewById(R.id.et_rdv_time);
        title_et = findViewById(R.id.et_rdv_title);
        content_et = findViewById(R.id.et_rdv_content);

        newRDVBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = date_et.getText().toString();
                String time = time_et.getText().toString();
                String title = title_et.getText().toString();
                String content = content_et.getText().toString();
                db.insert(date, time, title, content);
                Toast.makeText(getApplicationContext(), "Data has been saved successfully!", Toast.LENGTH_LONG).show();
            }

        });


        cancelRDVBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRdvActivity.this,RdvListActivity.class);
                startActivity(intent);
            }

        });
    }

}
