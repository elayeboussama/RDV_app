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

import com.example.rdv_app.Models.rdvDb.DbManager;
import com.example.rdv_app.Models.rdvDb.RDV;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class EditRdvActivity extends AppCompatActivity {
    public Button editRDVBTN;
    public Button cancelRDVBTN;

    public Button List;
    public EditText date_et;
    public EditText time_et;
    public EditText title_et;
    public EditText content_et;
    private DbManager db;
    final int  LAUNCH_SECOND = 1;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_rdv_activity);

        editRDVBTN = findViewById(R.id.btn_rdv_edit);
        cancelRDVBTN = findViewById(R.id.btn_rdv_cancel_edit);

        date_et = findViewById(R.id.et_rdv_date_edit);
        time_et = findViewById(R.id.et_rdv_time_edit);
        title_et = findViewById(R.id.et_rdv_title_edit);
        content_et = findViewById(R.id.et_rdv_content_edit);

        i = getIntent();







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




        RDV rdv = i.getParcelableExtra("notif") ;
        System.out.println(rdv);
        date_et.setText(i.getStringExtra("date"));
        time_et.setText(i.getStringExtra("time"));
        title_et.setText(i.getStringExtra("title"));
        content_et.setText(i.getStringExtra("content"));

        editRDVBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = date_et.getText().toString();
                String time = time_et.getText().toString();
                String title = title_et.getText().toString();
                String content = content_et.getText().toString();
                int test = db.update(i.getStringExtra("id"), date, time, title, content);
                Toast.makeText(getApplicationContext(), "Data has been updated successfully! "+test, Toast.LENGTH_LONG).show();
            }

        });




        cancelRDVBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RdvListActivity.class);
                startActivity(intent);
            }

        });
    }

}
