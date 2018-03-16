package com.lnsel.srfoods.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.activity.DealerNeworder;
import com.lnsel.srfoods.activity.DealerPreviousActivity;
import com.lnsel.srfoods.activity.SchemeView;
import com.lnsel.srfoods.activity2.SchemeView2;
import com.lnsel.srfoods.appconroller.AppController;

import java.util.ArrayList;
import java.util.HashMap;

public class DealerSchemeListAdapter extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> listitems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private static LayoutInflater inflater=null;
    public DealerSchemeListAdapter(SchemeView mainActivity, ArrayList<HashMap<String,String>> listitems1) {
        // TODO Auto-generated constructor stub
        listitems=listitems1;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DealerSchemeListAdapter(DealerNeworder mainActivity) {
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
        TextView From_Date,To_Date,Warehouse_Name,Product_Name,Range_Quantity,free_product,Quantity,Discount;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.all_scheme_list_items, null);
        holder.From_Date=(TextView) rowView.findViewById(R.id.From_Date);
        holder.To_Date=(TextView) rowView.findViewById(R.id.To_Date);
        holder.Warehouse_Name=(TextView) rowView.findViewById(R.id.Warehouse_Name);

        holder.Product_Name=(TextView) rowView.findViewById(R.id.Product_Name);
        holder.Range_Quantity=(TextView) rowView.findViewById(R.id.Range_Quantity);

        holder.free_product=(TextView) rowView.findViewById(R.id.free_product);
        holder.Discount=(TextView) rowView.findViewById(R.id.Discount);
        holder.Quantity=(TextView) rowView.findViewById(R.id.Quantity);

        holder.From_Date.setText("From Date : "+listitems.get(position).get("From_Date"));
        holder.To_Date.setText("To Date : "+listitems.get(position).get("To_Date"));
        holder.Quantity.setText("Free Quantity : "+listitems.get(position).get("Quantity"));
        holder.Product_Name.setText("Product Name : "+listitems.get(position).get("Product_Name"));
        holder.Range_Quantity.setText("Buy Quantity : "+listitems.get(position).get("Range_Quantity"));

        holder.Warehouse_Name.setText("Warehouse Name : "+listitems.get(position).get("Warehouse_Name"));
        holder.free_product.setText("Free Product : "+listitems.get(position).get("free_product"));
        holder.Discount.setText("Discount : "+listitems.get(position).get("Discount"));

        return rowView;
    }

}