package com.example.smartbilling;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class InvoiceDetails extends AppCompatActivity {

    TextView txtInvoiceNo, txtDate, txtPaymentType, txtJewelleryType, txtCustomerName, txtPhoneNumber, txtQuantity, txtAmount, txtTotalAmount, txtAddress, txtStoreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_details);

        Bundle bundle = getIntent().getExtras();
        String InvoiceNo = bundle.getString("InvoiceNo");
        String Username = bundle.getString("Username");

        txtInvoiceNo = findViewById(R.id.IDInvoiceNo);
        txtDate = findViewById(R.id.IDDate);
        txtPaymentType = findViewById(R.id.IDPaymentType);
        txtJewelleryType = findViewById(R.id.IDProductType);
        txtCustomerName = findViewById(R.id.IDCustomerName);
        txtPhoneNumber = findViewById(R.id.IDPhoneNumber);
        txtQuantity = findViewById(R.id.IDQuantity);
        txtAmount = findViewById(R.id.IDAmount);
        txtTotalAmount = findViewById(R.id.IDTotalAmount);
        txtAddress = findViewById(R.id.IDAddress);
        txtStoreName = findViewById(R.id.IDStoreName);

        Search(InvoiceNo, Username);
    }

    public void Search(String InvoiceNo, String Username){

        int count = 0;
        DbClass helper;
        SQLiteDatabase SQdb;
        Cursor cursor;

        helper=new DbClass(getApplicationContext());
        SQdb=helper.getReadableDatabase();

        try {
            String payload = "select * from Invoices where Username='"+Username+"' and InvoiceNo='"+InvoiceNo+"'";
//              cursor = SQdb.rawQuery("select * from Invoices where InvoiceNo='"+InvoiceNo+"' and Username='"+LoggedUsername+"'", null);
            cursor = SQdb.rawQuery(payload, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String InvoiceNumber = cursor.getString(cursor.getColumnIndex("InvoiceNo"));
                        String Date = cursor.getString(cursor.getColumnIndex("Date"));
                        String PaymentType = cursor.getString(cursor.getColumnIndex("PaymentType"));
                        String JewelleryType = cursor.getString(cursor.getColumnIndex("JewelleryType"));
                        String CustomerName = cursor.getString(cursor.getColumnIndex("CustomerName"));
                        String PhoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
                        String Quantity = cursor.getString(cursor.getColumnIndex("Quantity"));
                        String Amount = cursor.getString(cursor.getColumnIndex("Amount"));
                        String TotalAmount = cursor.getString(cursor.getColumnIndex("TotalAmount"));
                        String Address = cursor.getString(cursor.getColumnIndex("Address"));
                        String StoreName = cursor.getString(cursor.getColumnIndex("Username"));

                        txtInvoiceNo.setText(InvoiceNumber);
                        txtDate.setText(Date);
                        txtPaymentType.setText(PaymentType);
                        txtJewelleryType.setText(JewelleryType);
                        txtCustomerName.setText(CustomerName);
                        txtPhoneNumber.setText(PhoneNumber);
                        txtQuantity.setText(Quantity);
                        txtAmount.setText(Amount);
                        txtTotalAmount.setText(TotalAmount);
                        txtAddress.setText(Address);
                        txtStoreName.setText(StoreName);

                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
        }
        catch (Exception e){
            Log.d(TAG, "initialize() returned: " + e.getMessage());
        }

    }
}
