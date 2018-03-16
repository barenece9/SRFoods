package com.lnsel.srfoods.adapter2;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.activity.DealerNeworder;
import com.lnsel.srfoods.activity2.DealerNeworder2;
import com.lnsel.srfoods.appconroller.AppController;
import com.lnsel.srfoods.util.Webservice;
import com.lnsel.srfoods.util2.Webservice2;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter2 extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> listitems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private static LayoutInflater inflater=null;
    public CustomAdapter2(DealerNeworder2 mainActivity, ArrayList<HashMap<String,String>> listitems1) {
        // TODO Auto-generated constructor stub
        listitems=listitems1;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomAdapter2(DealerNeworder mainActivity) {
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
        TextView product_name,mrp,product_qty;
        NetworkImageView list_image;
        Button btn_delete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_items_pcj, null);
        holder.product_name=(TextView) rowView.findViewById(R.id.product_name);
        holder.mrp=(TextView) rowView.findViewById(R.id.mrp);
        holder.product_qty=(TextView) rowView.findViewById(R.id.product_qty);

        holder.btn_delete=(Button)rowView.findViewById(R.id.btn_delete);
        holder.list_image=(NetworkImageView) rowView.findViewById(R.id.list_image);

        holder.product_name.setText("Product Name : "+listitems.get(position).get("Product_Name"));
        holder.mrp.setText("Product MRP : "+listitems.get(position).get("MRP")+" INR");
        holder.product_qty.setText("Product Qty : "+listitems.get(position).get("Product_Qty"));

        if(!listitems.get(position).get("Product_Images").equalsIgnoreCase("Image_Not_Found")) {
            holder.list_image.setImageUrl(Webservice.PRODUCT_IMAGE+listitems.get(position).get("Product_Images"), imageLoader);
        }

       // holder.img.setImageResource(imageId[position]);
        holder.btn_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String slno=listitems.get(position).get("SL_NO");
                //Toast.makeText(context, "You Clicked "+slno, Toast.LENGTH_LONG).show();
                if(context instanceof DealerNeworder){
                    ((DealerNeworder)context).yourDesiredMethod(slno);
                }
            }
        });
        holder.list_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listitems.get(position).get("Product_Images").equalsIgnoreCase("Image_Not_Found")) {
                    String img_url= Webservice2.PRODUCT_IMAGE+listitems.get(position).get("Product_Images");
                    showDialog(img_url);
                }
            }
        });
        return rowView;
    }


   /* public void showDialog( String img_url){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.custom_img_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        NetworkImageView img_view = (NetworkImageView) dialog.findViewById(R.id.img_view);

        img_view.setImageUrl(img_url, imageLoader);

        dialog.show();
    }*/


    public void showDialog( String img_url){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Product Image");


        WebView wv = new WebView(context);
       // wv.loadUrl("http:\\www.google.com");
        wv.setInitialScale(80);

        wv.getSettings().setBuiltInZoomControls(true);
        wv.loadUrl(img_url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}