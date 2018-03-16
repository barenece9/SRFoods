package com.lnsel.srfoods.adapter2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.activity.ConfirmOrderDetails;
import com.lnsel.srfoods.activity.DealerNeworder;
import com.lnsel.srfoods.activity2.ConfirmOrderDetails2;
import com.lnsel.srfoods.appconroller.AppController;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfirmOrderListAdapter2 extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> listitems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private static LayoutInflater inflater=null;
    public ConfirmOrderListAdapter2(ConfirmOrderDetails2 mainActivity, ArrayList<HashMap<String,String>> listitems1) {
        // TODO Auto-generated constructor stub
        listitems=listitems1;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ConfirmOrderListAdapter2(DealerNeworder mainActivity) {
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
        TextView Product_Code,Product_Name,MRP,Selling_Price,Weight,Product_Qty,Total_Weight,Total_Amount,
                schemeNo,VAT,Free_Item,Batch_No;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.confirm_order_list_items_pcj, null);
        holder.Product_Code=(TextView) rowView.findViewById(R.id.Product_Code);
        holder.Product_Name=(TextView) rowView.findViewById(R.id.Product_Name);
        holder.MRP=(TextView) rowView.findViewById(R.id.MRP);
        holder.Selling_Price=(TextView) rowView.findViewById(R.id.Selling_Price);
        holder.Weight=(TextView) rowView.findViewById(R.id.Weight);

        holder.Product_Qty=(TextView) rowView.findViewById(R.id.Product_Qty);
        holder.Total_Weight=(TextView) rowView.findViewById(R.id.Total_Weight);
        holder.Total_Amount=(TextView) rowView.findViewById(R.id.Total_Amount);
        holder.schemeNo=(TextView) rowView.findViewById(R.id.schemeNo);
        holder.VAT=(TextView) rowView.findViewById(R.id.VAT);
        holder.Free_Item=(TextView) rowView.findViewById(R.id.Free_Item);
        holder.Batch_No=(TextView) rowView.findViewById(R.id.Batch_No);

        holder.Product_Code.setText("Product Code : "+listitems.get(position).get("Product_Code"));
        holder.Product_Name.setText("Product Name : "+listitems.get(position).get("Product_Name"));
        holder.MRP.setText("MRP : "+listitems.get(position).get("MRP")+"INR");
        holder.Selling_Price.setText("Selling Price : "+listitems.get(position).get("Selling_Price")+" INR");
        holder.Weight.setText("Weight : "+listitems.get(position).get("Weight")+" Kg");

        holder.Product_Qty.setText("Product Qty : "+listitems.get(position).get("Product_Qty"));
        holder.Total_Weight.setText("Total Weight : "+listitems.get(position).get("Weight1")+" Kg");
        holder.Total_Amount.setText("Total Amount : "+listitems.get(position).get("Amount1")+" INR");
        holder.schemeNo.setText("Scheme No : "+listitems.get(position).get("schemeNo"));
        holder.VAT.setText("VAT : "+listitems.get(position).get("VAT"));

        holder.Free_Item.setText("Free Item : "+listitems.get(position).get("Free_Item"));
        holder.Batch_No.setText("Batch No : "+listitems.get(position).get("Batch_No"));

        return rowView;
    }

}