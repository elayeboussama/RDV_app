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


import com.example.rdv_app.Models.userDb.DbManagerUser;
import com.example.rdv_app.Models.userDb.User;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    public Button LoginBTN;

    public EditText name_et;
    public EditText password_et;
    private DbManagerUser dbUser;
    final int  REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        dbUser = new DbManagerUser(this);

        try {
            dbUser.open();
        } catch (SQLDataException throwables) {
            throwables.printStackTrace();
        }


        name_et = (EditText) findViewById(R.id.et_rdv_username_login);
        password_et = (EditText) findViewById(R.id.et_rdv_password_login);
        LoginBTN = (Button) findViewById(R.id.btn_login);
        ArrayList<User> users = new ArrayList<User>();
        User user;

        Cursor c = dbUser.fetch();
        if (c == null) {
            Toast.makeText(this, "there is no data in the table", Toast.LENGTH_LONG).show();
        } else {

            if (c.moveToFirst()){

                do{
                    @SuppressLint("Range")
                    int idFromCursor = Integer.parseInt(c.getString(c.getColumnIndex("id_")));
                    @SuppressLint("Range")
                    String usernameFromCursor = c.getString(c.getColumnIndex("username"));
                    @SuppressLint("Range")
                    String passwordFromCursor = c.getString(c.getColumnIndex("password"));

                    user = new User(idFromCursor,usernameFromCursor,passwordFromCursor);
                    users.add(user);
                }while(c.moveToNext());

            }

        }
        c.close();

        LoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users.size()!=0){
                    if(((name_et.getText().toString()).equals(users.get(0).userName)) && ((password_et.getText().toString()).equals(users.get(0).password))){
                        Intent i = new Intent(getApplicationContext(), RdvListActivity.class);
                        i.putExtra("user", users.get(0).userName);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "there is no user with this information", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "there is no user with this information", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}