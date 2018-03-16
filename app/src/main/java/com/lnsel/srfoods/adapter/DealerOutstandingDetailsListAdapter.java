package com.lnsel.srfoods.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.activity.DealerNeworder;
import com.lnsel.srfoods.activity.DealerOutstanding;
import com.lnsel.srfoods.activity.DealerOutstandingDetails;
import com.lnsel.srfoods.appconroller.AppController;

import java.util.ArrayList;
import java.util.HashMap;

public class DealerOutstandingDetailsListAdapter extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> listitems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private static LayoutInflater inflater=null;
    public DealerOutstandingDetailsListAdapter(DealerOutstandingDetails mainActivity, ArrayList<HashMap<String,String>> listitems1) {
        // TODO Auto-generated constructor stub
        listitems=listitems1;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DealerOutstandingDetailsListAdapter(DealerNeworder mainActivity) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        /*inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listitems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView Payment_Amount,Payment_Mode,Payment_Date,Cheque_No,Payment_Status;
        Button btn_details;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.all_payment_list_items, null);
        holder.Payment_Amount=(TextView) rowView.findViewById(R.id.Payment_Amount);
        holder.Payment_Mode=(TextView) rowView.findViewById(R.id.Payment_Mode);
        holder.Payment_Date=(TextView)rowView.findViewById(R.id.Payment_Date);

        holder.Cheque_No=(TextView) rowView.findViewById(R.id.Cheque_No);
        holder.Payment_Status=(TextView)rowView.findViewById(R.id.Payment_Status);

        holder.Payment_Amount.setText("Payment Amount : Rs. "+listitems.get(position).get("Payment_Amount")+"/-");
        holder.Payment_Mode.setText("Payment Mode : "+listitems.get(position).get("Payment_Mode"));
        holder.Payment_Date.setText("Payment Date : "+listitems.get(position).get("Payment_Date"));

        //String Cheque_No=listitems.get(position).get("Cheque_No");
        if(listitems.get(position).get("Payment_Mode").equalsIgnoreCase("cheque")){
            holder.Cheque_No.setVisibility(View.VISIBLE);
            holder.Cheque_No.setText("Cheque No : "+listitems.get(position).get("Cheque_No"));
        }
       // holder.Payment_Status.setText("Payment Status : "+listitems.get(position).get("Payment_Status"));



        return rowView;
    }

}