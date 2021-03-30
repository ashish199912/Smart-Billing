package com.example.smartbilling;

public class POJOInvoices {

    String InvoiceNo, Date, Username;

    public POJOInvoices(String invoiceNo, String date, String username) {
        InvoiceNo = invoiceNo;
        Date = date;
        Username = username;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
