package com.example.smartbilling;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class TotalSell extends AppCompatActivity {


    ListView listView;
//    private static ListAdapter adapter;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_sell);

        //Get user
        Bundle bundle = getIntent().getExtras();
        Username = bundle.getString("Username");

        listView = (ListView) findViewById(R.id.listInvoices);

        Search(Username);

    }

    public void Search(String Username) {

        DbClass helper;
        SQLiteDatabase SQdb;
        Cursor cursor;

        helper = new DbClass(getApplicationContext());
        SQdb = helper.getReadableDatabase();
        ArrayList<POJOInvoices> dataModels;

      try {
            String payload = "select * from Invoices where Username='"+Username+"'";
//                cursor = SQdb.rawQuery("select * from Invoices where InvoiceNo='"+InvoiceNo+"' and Username='"+LoggedUsername+"'", null);
            cursor = SQdb.rawQuery(payload, null);
            if (cursor != null) {
                dataModels= new ArrayList<>();

                if (cursor.moveToFirst()) {
                    do {
                        String InvoiceNumber = cursor.getString(cursor.getColumnIndex("InvoiceNo"));
                        String Date = cursor.getString(cursor.getColumnIndex("Date"));

                        dataModels.add(new POJOInvoices(InvoiceNumber, Date, Username));
                    } while (cursor.moveToNext());
                   setAdapterData(dataModels, getApplicationContext());
                }
            }
        }
        catch (Exception e){
            Log.d(TAG, "initialize() returned: " + e.getMessage());
            e.printStackTrace();
        }

    }

    protected void setAdapterData(ArrayList<POJOInvoices> data, Context context)
    {
        ListAdapter adapter= new ListAdapter(data,context);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                POJOInvoices dataModel= data.get(position);
                String InvoiceNo = dataModel.getInvoiceNo();
                String Username = dataModel.getUsername();
                String Date = dataModel.getDate();
//                Toast.makeText(TotalSell.this, InvoiceNo+"\n"+Username+"\n"+Date, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TotalSell.this, InvoiceDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("InvoiceNo", InvoiceNo);
                bundle.putString("Username", Username);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }


}