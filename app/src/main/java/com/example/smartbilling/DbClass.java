package com.example.smartbilling;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbClass extends SQLiteOpenHelper {

    public static final String DATABASE="MyDB.db";
    public static final int VERSION=1;

    public static String SharedPreferenceName = "BillingApp";

    public DbClass(Context ctx){
        super(ctx,DATABASE,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table Users(Username, Name, StoreName, EmailAddress, Password)");
        db.execSQL("create table Invoices(InvoiceNo, Date, PaymentType, JewelleryType, CustomerName, PhoneNumber, Quantity, Amount, TotalAmount, Address, Username)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {


    }

}
