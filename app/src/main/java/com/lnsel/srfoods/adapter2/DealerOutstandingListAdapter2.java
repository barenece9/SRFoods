package com.lnsel.srfoods.adapter2;
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
import com.lnsel.srfoods.activity2.DealerOutstanding2;
import com.lnsel.srfoods.appconroller.AppController;

import java.util.ArrayList;
import java.util.HashMap;

public class DealerOutstandingListAdapter2 extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> listitems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private static LayoutInflater inflater=null;
    public DealerOutstandingListAdapter2(DealerOutstanding2 mainActivity, ArrayList<HashMap<String,String>> listitems1) {
        // TODO Auto-generated constructor stub
        listitems=listitems1;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DealerOutstandingListAdapter2(DealerNeworder mainActivity) {
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
        TextView order_no,due_amount,order_date;
        Button btn_details;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.all_outstanding_list_items_pcj, null);
        holder.order_no=(TextView) rowView.findViewById(R.id.order_no);
        holder.due_amount=(TextView) rowView.findViewById(R.id.due_amount);
        holder.order_date=(TextView)rowView.findViewById(R.id.order_date);
        holder.btn_details=(Button)rowView.findViewById(R.id.btn_details);


        holder.order_no.setText("Invoice Number : "+listitems.get(position).get("Invoice_number"));
        //holder.order_no.setText("Order No : "+listitems.get(position).get("order_no"));
        holder.order_date.setText("Order Date : "+listitems.get(position).get("Order_Date"));
        holder.due_amount.setText("Due Amount : Rs. "+listitems.get(position).get("due_amount")+"/-");

        holder.btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof DealerOutstanding){
                    ((DealerOutstanding)context).viewdetails(position);
                }
            }
        });


        return rowView;
    }

}