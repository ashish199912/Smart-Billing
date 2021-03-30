package com.example.smartbilling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    EditText txtName, txtUserName, txtStoreName, txtEmail, txtPassword, txtConfirmPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        txtName = findViewById(R.id.txtName);
        txtUserName = findViewById(R.id.txtUserName);
        txtStoreName = findViewById(R.id.txtStoreName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        btnSignUp=findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doRegistration();

                /*Intent intent=new Intent(Registration.this,MainActivity.class);
                startActivity(intent);*/
            }
        });

    }

    protected void doRegistration()
    {
        String name = txtName.getText().toString();
        String userName = txtUserName.getText().toString();
        String storeName = txtStoreName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();

        if(!name.isEmpty() && !userName.isEmpty() && !storeName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty())
        {
            if(password.equals(confirmPassword))
            {
                insertData(name, userName, storeName, email, password);
            }else
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this, "Please provide all the values", Toast.LENGTH_LONG).show();

    }

    protected void insertData(String Name, String Username, String StoreName, String Email, String Password) {
        DbClass helper;
        SQLiteDatabase SQdb;
        SQLiteStatement SQstmt;

        helper = new DbClass(getBaseContext());
        SQdb = helper.getWritableDatabase();

        //to insert data into db
        SQstmt = SQdb.compileStatement("insert into Users values(?,?,?,?,?)");
        SQstmt.bindString(1, Username);
        SQstmt.bindString(2, Name);
        SQstmt.bindString(3, StoreName);
        SQstmt.bindString(4, Email);
        SQstmt.bindString(5, Password);
        long i = SQstmt.executeInsert();

        if (i > 0) {
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = getSharedPreferences(DbClass.SharedPreferenceName, MODE_PRIVATE).edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("Username", Username);
            editor.apply();

            Bundle bundle = new Bundle();
            bundle.putString("Username", Username);
            Intent intent = new Intent(Registration.this, Invoice.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else
            Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG).show();

        }
}