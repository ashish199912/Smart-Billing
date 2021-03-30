package com.example.smartbilling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Invoice extends AppCompatActivity {

    String LoggedUsername;

     TextView txtWelcome;
     Button btnNewBill,btnSearch,btnTotalSell;
     ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        SharedPreferences prefs = getSharedPreferences(DbClass.SharedPreferenceName, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if(isLoggedIn) {
            LoggedUsername = prefs.getString("Username", "");
        }else{
            startActivity(new Intent(Invoice.this, MainActivity.class));
            finish();
        }

        /*//Get user
        Bundle bundle = getIntent().getExtras();
        LoggedUsername = bundle.getString("Username");*/

        txtWelcome=(TextView)findViewById(R.id.txtWelcome);
        btnNewBill=(Button)findViewById(R.id.btnNewBill);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        btnTotalSell=(Button)findViewById(R.id.btnTotalSell);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(DbClass.SharedPreferenceName, MODE_PRIVATE).edit();
                editor.putBoolean("isLoggedIn", false);
                editor.putString("Username", "Dummy");
                editor.apply();
                startActivity(new Intent(Invoice.this, MainActivity.class));
                finish();
            }
        });

        btnNewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Invoice.this,NewBill.class);
                Bundle bundle = new Bundle();
                bundle.putString("Username", LoggedUsername);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Invoice.this, SearchInvoice.class);
                Bundle bundle = new Bundle();
                bundle.putString("Username", LoggedUsername);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnTotalSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Invoice.this,TotalSell.class);
                Bundle bundle = new Bundle();
                bundle.putString("Username", LoggedUsername);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }
}