package com.example.smartbilling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<POJOInvoices> implements View.OnClickListener {

    private ArrayList<POJOInvoices> dataSet;
    Context mContext;
    private static class ViewHolder {
        TextView txtInvoiceNo;
        TextView txtInvoiceDate;
        TextView txtUsername;
    }

    public ListAdapter(ArrayList<POJOInvoices> data, Context context) {
        super(context, R.layout.total_sale_list, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        POJOInvoices dataModel=(POJOInvoices) object;

        /*switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*/
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        POJOInvoices dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.total_sale_list, parent, false);
            viewHolder.txtInvoiceNo = (TextView) convertView.findViewById(R.id.listInvoiceNo);
            viewHolder.txtInvoiceDate = (TextView) convertView.findViewById(R.id.listDate);
            viewHolder.txtUsername = (TextView) convertView.findViewById(R.id.listUsername);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txtInvoiceNo.setText(dataModel.getInvoiceNo());
        viewHolder.txtInvoiceDate.setText(dataModel.getDate());
        viewHolder.txtUsername.setText(dataModel.getUsername());
        // Return the completed view to render on screen
        return convertView;
    }


}
