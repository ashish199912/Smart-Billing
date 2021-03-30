package com.example.smartbilling;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class SearchInvoice extends AppCompatActivity {

    String LoggedUsername;

    EditText txtInvoiceNumber;
    Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_invoice);

        //Get user
        Bundle bundle = getIntent().getExtras();
        LoggedUsername = bundle.getString("Username");

        txtInvoiceNumber=(EditText)findViewById(R.id.txtInvoiceNumber);
        btnSearch=(Button)findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String InvoiceNumber = txtInvoiceNumber.getText().toString();
                if(!InvoiceNumber.isEmpty())
                {
                    Intent intent = new Intent(SearchInvoice.this, InvoiceDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("InvoiceNo", InvoiceNumber);
                    bundle.putString("Username", LoggedUsername);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else
                    Toast.makeText(SearchInvoice.this, "Enter invoice number to proceed", Toast.LENGTH_LONG).show();
            }
        });



        }
    }
