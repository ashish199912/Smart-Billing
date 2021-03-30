package com.example.smartbilling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    EditText txtUserName, txtPassword;
    Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserName = findViewById(R.id.txtUserNameSignIn);
        txtPassword = findViewById(R.id.txtPasswordSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUpSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Registration.class));
                finish();
            }
        });
    }

    protected void checkFields()
    {
        String username = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();
        if(!username.isEmpty() && !password.isEmpty())
        {
            doLogin(username, password);
        }else{
            Toast.makeText(this, "Username or password can't be empty", Toast.LENGTH_LONG).show();
        }
    }

    protected void doLogin(String username, String password)
    {
        int count = 0;
        DbClass helper;
        SQLiteDatabase SQdb;
        Cursor cursor;

        helper=new DbClass(getApplicationContext());
        SQdb=helper.getReadableDatabase();

        try {
            cursor = SQdb.rawQuery("select * from Users where Username='"+username+"' and Password='"+password+"'", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        count++;
                    } while (cursor.moveToNext());
                }
            }
        }
        catch (Exception e){
            Log.d(TAG, "initialize() returned: " + e.getMessage());
        }

        if(count>0)
        {
            SharedPreferences.Editor editor = getSharedPreferences(DbClass.SharedPreferenceName, MODE_PRIVATE).edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("Username", username);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, Invoice.class);
            Bundle bundle = new Bundle();
            bundle.putString("Username", username);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }else
            Toast.makeText(this, "Invalid username or password \nPlease try again", Toast.LENGTH_LONG).show();
    }
}