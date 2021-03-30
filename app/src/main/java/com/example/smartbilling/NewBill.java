package com.example.smartbilling;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class NewBill extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String LoggedUsername;

    DatePickerDialog datepicker;
    Spinner spinnerPayment,spinnerProduct;
    Button submitbtn;
    EditText txtBillDate,txtInvoiceNo,txtCustomerName,txtPhoneNo,txtAddress,txtQuantity,txtTotalAmount,txtAmountAfterTax;

    String selectedPaymentType, selectedProductType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);

        //Get user
        Bundle bundle = getIntent().getExtras();
        LoggedUsername = bundle.getString("Username");

        txtBillDate=findViewById(R.id.txtBillDate);
        txtInvoiceNo=findViewById(R.id.txtInvoiceNo);
        txtCustomerName=findViewById(R.id.txtCustomerName);
        txtPhoneNo=findViewById(R.id.txtPhoneNo);
        txtAddress=findViewById(R.id.txtAddress);
        txtQuantity=findViewById(R.id.txtQuantity);
        txtTotalAmount=findViewById(R.id.txtTotalAmount);
        txtAmountAfterTax=findViewById(R.id.txtAmountAfterTax);

        submitbtn=(Button)findViewById(R.id.submit_btn);
        spinnerPayment = (Spinner) findViewById(R.id.spinnerPayment);
        spinnerProduct = (Spinner) findViewById(R.id.spinnerProduct);

//        txtBillDate.setInputType(InputType.TYPE_NULL);
        txtBillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker =new DatePickerDialog(NewBill.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtBillDate.setText(dayOfMonth + "/" + (dayOfMonth + 1) + "/" + year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });

         

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.payment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayment.setAdapter(adapter);
        spinnerPayment.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.product, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(adapter1);
        spinnerProduct.setOnItemSelectedListener(this);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
            }
        });



        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPaymentType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProductType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*String text=parent.getItemAtPosition(position).toString();

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void GetData(){
        String Date = txtBillDate.getText().toString();
        String Invoice_No = txtInvoiceNo.getText().toString();
        String Customer_Name = txtCustomerName.getText().toString();
        String Phone = txtPhoneNo.getText().toString();
        String Address = txtAddress.getText().toString();
        String Quantity = txtQuantity.getText().toString();
        String Amount = txtTotalAmount.getText().toString();
        String TotalAmount = txtAmountAfterTax.getText().toString();
        if(!selectedPaymentType.isEmpty() )
        {
            if(!selectedProductType.isEmpty())
            {
                insertData(Date, Invoice_No, Customer_Name, Phone, Address, Quantity, Amount, TotalAmount, selectedPaymentType, selectedProductType);
            }else
                Toast.makeText(this, "Select product", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this, "Select payment type", Toast.LENGTH_LONG).show();
    }

    protected void insertData(String Date, String Invoice_No, String Customer_Name, String Phone, String Address, String Quantity,String Amount,String TotalAmount, String PaymentType, String ProductType) {
        DbClass helper;
        SQLiteDatabase SQdb;
        SQLiteStatement SQstmt;

        helper = new DbClass(getBaseContext());
        SQdb = helper.getWritableDatabase();

        //to insert data into db
        SQstmt = SQdb.compileStatement("insert into Invoices values(?,?,?,?,?,?,?,?,?,?,?)");
        SQstmt.bindString(1, Invoice_No);
        SQstmt.bindString(2, Date);
        SQstmt.bindString(3, PaymentType);
        SQstmt.bindString(4, ProductType);
        SQstmt.bindString(5, Customer_Name);
        SQstmt.bindString(6, Phone);
        SQstmt.bindString(7, Quantity);
        SQstmt.bindString(8, Amount);
        SQstmt.bindString(9, TotalAmount);
        SQstmt.bindString(10, Address);
        SQstmt.bindString(11, LoggedUsername);
        long i = SQstmt.executeInsert();
        if(i>0) {
            Toast.makeText(this, "Record Generated", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Enter The Fields", Toast.LENGTH_LONG).show();

    }


}