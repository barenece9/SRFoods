package com.lnsel.srfoods.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.activity.DealerNeworder;
import com.lnsel.srfoods.activity.DealerPreviousActivity;
import com.lnsel.srfoods.activity2.DealerPreviousActivity2;
import com.lnsel.srfoods.appconroller.AppController;
import com.lnsel.srfoods.util.Webservice;

import java.util.ArrayList;
import java.util.HashMap;

public class DealerOrderListAdapter extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> listitems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private static LayoutInflater inflater=null;
    public DealerOrderListAdapter(DealerPreviousActivity mainActivity, ArrayList<HashMap<String,String>> listitems1) {
        // TODO Auto-generated constructor stub
        listitems=listitems1;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DealerOrderListAdapter(DealerNeworder mainActivity) {
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
        TextView Order_Number,Order_Date,Quantity,Order_Amount,Order_Status;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.all_order_list_items, null);
        holder.Order_Number=(TextView) rowView.findViewById(R.id.Order_Number);
        holder.Order_Date=(TextView) rowView.findViewById(R.id.Order_Date);
        holder.Quantity=(TextView) rowView.findViewById(R.id.Quantity);

        holder.Order_Amount=(TextView) rowView.findViewById(R.id.Order_Amount);
        holder.Order_Status=(TextView) rowView.findViewById(R.id.Order_Status);

        if(listitems.get(position).get("Invoice_number").equalsIgnoreCase("")){
            holder.Order_Number.setText("Order Number : "+listitems.get(position).get("Order_Number"));
        }else {
            holder.Order_Number.setText("Invoice number : " + listitems.get(position).get("Invoice_number"));
        }
        holder.Order_Date.setText("Order Date : "+listitems.get(position).get("Order_Date"));
        holder.Quantity.setText("Quantity : "+listitems.get(position).get("Quantity"));
        holder.Order_Amount.setText("Order Amount : Rs: "+listitems.get(position).get("Order_Amount")+"/-");
        holder.Order_Status.setText("Order Status : "+listitems.get(position).get("Order_Status"));

        return rowView;
    }

}