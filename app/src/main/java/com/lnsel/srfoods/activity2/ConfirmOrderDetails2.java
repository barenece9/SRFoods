package com.lnsel.srfoods.activity2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.activity2.DealerPreviousActivity2;
import com.lnsel.srfoods.adapter.ConfirmOrderListAdapter;
import com.lnsel.srfoods.adapter2.ConfirmOrderListAdapter2;
import com.lnsel.srfoods.settergetterclass2.RememberData2;
import com.lnsel.srfoods.util2.Logout2;
import com.lnsel.srfoods.util2.Sharepreferences2;
import com.lnsel.srfoods.util2.Webservice2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderDetails2 extends Activity {

    Button btnBack,btnLogout;
    TextView headerTxt;
    ListView order_list;
    ArrayList<HashMap<String,String>> alllist;
    String orderno="";

    TextView order_date,Total_Quantity,Total_Weight,Total_Amount,Total_VAT,percentage,total_discount,type,discount_amount,
            s_total_discount,invoice_total;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_confirm_order_details_pcj);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Logout2.logout(ConfirmOrderDetails2.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(ConfirmOrderDetails2.this,DealerPreviousActivity2.class));
                finish();
            }
        });
        headerTxt.setText("Confirm Order Details");
        Bundle bundle = getIntent().getExtras();
        orderno = bundle.getString("orderno");
        setwidget();
        getOrderdetails();
    }
    public  void setwidget(){
        order_list=(ListView)findViewById(R.id.order_list);
        order_date=(TextView)findViewById(R.id.order_date);
        Total_Quantity=(TextView)findViewById(R.id.Total_Quantity);
        Total_Weight=(TextView)findViewById(R.id.Total_Weight);
        Total_Amount=(TextView)findViewById(R.id.Total_Amount);
        Total_VAT=(TextView)findViewById(R.id.Total_VAT);

        percentage=(TextView)findViewById(R.id.percentage);
        total_discount=(TextView)findViewById(R.id.total_discount);
        type=(TextView)findViewById(R.id.type);
        discount_amount=(TextView)findViewById(R.id.discount_amount);
        s_total_discount=(TextView)findViewById(R.id.s_total_discount);

        invoice_total=(TextView)findViewById(R.id.invoice_total);
        alllist=new ArrayList<>();

    }

    public  void getOrderdetails(){
        progressDialog=new ProgressDialog(ConfirmOrderDetails2.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;
        url = Webservice2.CONFIRM_ORDER_DETAILS;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseorderdetails(response);
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(ConfirmOrderDetails2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData=new RememberData();
                RememberData2 rememberData= Sharepreferences2.getDealerinfo(ConfirmOrderDetails2.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("orderNo",orderno);
                System.out.println(rememberData.getDealerCode()+" %%%%%% "     +rememberData.getDealerWarehouse());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();

    }
    public  void  parseorderdetails(String response){
        try {
            alllist.clear();
            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                String orderdate=parentObj.optString("order_date");
                order_date.setText("Date : "+orderdate);
                JSONObject result_all_total=parentObj.getJSONObject("result_all_total");
                String TotalQuantity=result_all_total.optString("Total_Quantity");
                Total_Quantity.setText("Total Quantity : "+TotalQuantity);
                String TotalWeight=result_all_total.optString("Total_Weight");
                Total_Weight.setText("Total Weight : "+TotalWeight+" Kg");
                String TotalAmount=result_all_total.optString("Total_Amount");
                Total_Amount.setText("Total Amount : "+TotalAmount+" INR");
                String TotalVAT=result_all_total.optString("Total_VAT");
                Total_VAT.setText("Total VAT : "+TotalVAT+" INR");

                JSONObject discount_details=parentObj.getJSONObject("discount_details");

                JSONObject discount=discount_details.getJSONObject("discount");
                String d_percentage=discount.optString("percentage");
                String totaldiscount=discount.optString("total_discount");
                percentage.setText("Discount Percentage : "+d_percentage);
                total_discount.setText("Total Discount : "+totaldiscount+" INR");

                JSONObject special_discount=discount_details.getJSONObject("special_discount");
                String d_type=special_discount.optString("type");
                String damount=special_discount.optString("amount");
                type.setText("Discount Type : "+d_type);
                if(d_type.equalsIgnoreCase("%")){
                    discount_amount.setText("Discount Percentage : "+damount+" %");
                }else {
                    discount_amount.setText("Discount Amount : "+damount+" INR");
                }
                String d_totaldiscount=special_discount.optString("total_discount");
                s_total_discount.setText("Total Discount : "+d_totaldiscount+" INR");


                String invoicetotal=parentObj.optString("invoice_total");
                invoice_total.setText("Total Invoice : "+invoicetotal+" INR");

                JSONArray item_list=parentObj.getJSONArray("item_list");
                for(int i=0;i<item_list.length();i++){
                    JSONObject inerobj=item_list.getJSONObject(i);
                    HashMap<String,String> list=new HashMap<>();
                    String Product_Code=inerobj.optString("Product_Code");
                    String Product_Name=inerobj.optString("Product_Name");
                    String MRP=inerobj.optString("MRP");
                    String Selling_Price=inerobj.optString("Selling_Price");
                    String Weight=inerobj.optString("Weight");

                    String Product_Qty=inerobj.optString("Product_Qty");
                    String Weight1=inerobj.optString("Total_Weight");
                    String Amount1=inerobj.optString("Total_Amount");
                    String schemeNo=inerobj.optString("schemeNo");
                    String VAT=inerobj.optString("VAT");
                    String Free_Item=inerobj.optString("Free_Item");
                    String Batch_No=inerobj.optString("Batch No");

                    list.put("Product_Code",Product_Code);
                    list.put("Product_Name",Product_Name);
                    list.put("MRP",MRP);
                    list.put("Selling_Price",Selling_Price);
                    list.put("Weight",Weight);
                    list.put("Product_Qty",Product_Qty);
                    list.put("Weight1",Weight1);
                    list.put("Amount1",Amount1);
                    list.put("schemeNo",schemeNo);
                    list.put("VAT",VAT);
                    list.put("Free_Item",Free_Item);
                    list.put("Batch_No",Batch_No);

                    alllist.add(list);
                }
                Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
            }else if(status.equalsIgnoreCase("error")) {
                // String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        order_list.setAdapter(new ConfirmOrderListAdapter2(ConfirmOrderDetails2.this,alllist));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderDetails2.this);
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Exit...");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want exit this?");
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Write your code here to invoke YES event
                //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                if(progressDialog!=null)
                    progressDialog.dismiss();
                finish();
            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}
