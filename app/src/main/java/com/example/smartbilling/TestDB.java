package com.example.smartbilling;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class TestDB {

    DbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;
    SQLiteStatement SQstmt;

    Context context;

    public void testDB()
    {
        helper=new DbClass(context);
        SQdb=helper.getReadableDatabase();

        //to insert data into db
        SQstmt=SQdb.compileStatement("insert into Subjects values(?,?)");
      //  SQstmt.bindString(1,jsonSubject.getString("SubjectNo"));
       // SQstmt.bindString(2,jsonSubject.getString("SubjectName"));
        SQstmt.executeInsert();

        //to read data from db
        List<Map<String,String>> data= null;
        data=new ArrayList<Map<String,String>>();
        SimpleAdapter simpleAdapter=null;
        try {
            cursor = SQdb.rawQuery("select * from Subjects", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Map<String,String> datanum=new HashMap<String, String>();
                        datanum.put("SubjectNo",cursor.getString(cursor.getColumnIndex("SubjectNo")));
                        datanum.put("Subject",cursor.getString(cursor.getColumnIndex("Subject")));
                        data.add(datanum);
                    } while (cursor.moveToNext());

                    String[] fromwhere={"SubjectNo","Subject"};
                  //  simpleAdapter=new SimpleAdapter(context,data,R.layout.list_syl_sub,fromwhere,viewswhere);

                }
            }
        }
        catch (Exception e){
            Log.d(TAG, "initialize() returned: " + e.getMessage());
        }
    }


}
