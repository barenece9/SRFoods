package com.lnsel.srfoods.adapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.activity.DealerNeworder;
import com.lnsel.srfoods.activity.DeliveryBoyCompleteOrderActivity;
import com.lnsel.srfoods.activity.DeliveryBoyOrderListActivity;
import com.lnsel.srfoods.appconroller.AppController;

import java.util.ArrayList;
import java.util.HashMap;

public class DeliveryBoyCompleteOrderListAdapter extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> listitems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private static LayoutInflater inflater=null;
    public DeliveryBoyCompleteOrderListAdapter(DeliveryBoyCompleteOrderActivity mainActivity, ArrayList<HashMap<String,String>> listitems1) {
        // TODO Auto-generated constructor stub
        listitems=listitems1;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DeliveryBoyCompleteOrderListAdapter(DealerNeworder mainActivity) {
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
        TextView Invoice_number,Order_Number,Order_Assign_Date,Est_Delivery_Date,Dealer_Name,Order_Status,Shipping_Address,Dealer_Mobile,Due_Amt;
        Button view_details,call;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.assigne_all_order_list_items, null);

        holder.Invoice_number=(TextView)rowView.findViewById(R.id.Invoice_number);
        holder.Order_Number=(TextView) rowView.findViewById(R.id.Order_Number);
        holder.Order_Assign_Date=(TextView) rowView.findViewById(R.id.Order_Assign_Date);
        holder.Est_Delivery_Date=(TextView) rowView.findViewById(R.id.Est_Delivery_Date);

        holder.Dealer_Name=(TextView) rowView.findViewById(R.id.Dealer_Name);
        holder.Order_Status=(TextView) rowView.findViewById(R.id.Order_Status);

        holder.Shipping_Address=(TextView) rowView.findViewById(R.id.Shipping_Address);
        holder.Dealer_Mobile=(TextView) rowView.findViewById(R.id.Dealer_Mobile);

        holder.Due_Amt=(TextView)rowView.findViewById(R.id.Due_Amt);

        holder.call=(Button)rowView.findViewById(R.id.call);
        holder.view_details=(Button)rowView.findViewById(R.id.view_details);

        holder.Invoice_number.setText("Invoice Number : "+listitems.get(position).get("Invoice_number"));
        holder.Order_Number.setText("Order Number : "+listitems.get(position).get("Order_Number"));
        holder.Order_Assign_Date.setText("Order Assign Date  : "+listitems.get(position).get("Order_Assign_Date"));
        holder.Est_Delivery_Date.setText("Est Delivery Date : "+listitems.get(position).get("Est_Delivery_Date"));
        holder.Dealer_Name.setText("Dealer Name : "+listitems.get(position).get("Dealer_Name"));

        holder.Shipping_Address.setText("Shipping Address : "+listitems.get(position).get("Shipping_Address"));
        holder.Dealer_Mobile.setText("Dealer Mobile : "+listitems.get(position).get("Dealer_Mobile"));
        holder.Due_Amt.setText("Due Amount : Rs. "+listitems.get(position).get("Due_Amt")+"/-");

        holder.call.setVisibility(View.GONE);
       /* holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=listitems.get(position).get("Dealer_Mobile");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                context.startActivity(callIntent);
            }
        });*/

        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(context instanceof DeliveryBoyCompleteOrderActivity){
                    ((DeliveryBoyCompleteOrderActivity)context).viewdetails(position);
                }
                /*Intent intent=new Intent(context, DeliveryOrderDetails.class);
                payment.setDealerCode(listitems.get(position).get("Dealer_Code"));
                payment.setOrderNumber(listitems.get(position).get("Order_Number"));
                payment.setDueAmt(listitems.get(position).get("Due_Amt"));
                payment.setTotalAmt(listitems.get(position).get("Total_Amt"));
                context.startActivity(intent);*/
            }
        });

        return rowView;
    }

}