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
import android.widget.AdapterView;
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
import com.lnsel.srfoods.activity.ApprovalPendingOrderDetails;
import com.lnsel.srfoods.activity.ConfirmOrderDetails;
import com.lnsel.srfoods.activity2.DealerDeliveryOrderDetails2;
import com.lnsel.srfoods.activity2.DealerActivity2;
import com.lnsel.srfoods.activity2.DispatchedOrderDetails2;

import com.lnsel.srfoods.adapter2.DealerOrderListAdapter2;
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

public class DealerPreviousActivity2 extends Activity {

    Button btnBack,btnLogout;
    TextView headerTxt;
    ListView order_list;
    ArrayList<HashMap<String,String>> alllist;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dealer_previous_pcj);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null) {
                    progressDialog.dismiss();
                }
                startActivity(new Intent(DealerPreviousActivity2.this,DealerActivity2.class));
                finish();
            }
        });

        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null) {
                    progressDialog.dismiss();
                }
                Logout2.logout(DealerPreviousActivity2.this);
                finish();
            }
        });
        headerTxt.setText("Order List");
        setwidget();
        getAllOrdrList();
    }
    public  void setwidget(){
        order_list=(ListView)findViewById(R.id.order_list);
        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String orderno=alllist.get(position).get("Order_Number");
                String status=alllist.get(position).get("Order_Status");
               // startActivity(new Intent(DealerPreviousActivity.this,ConfirmOrderDetails.class));
                if(progressDialog!=null) {
                    progressDialog.dismiss();
                }

                if(status.equalsIgnoreCase("Confirmed")) {
                    Intent intent = new Intent(DealerPreviousActivity2.this, ConfirmOrderDetails2.class);
                    intent.putExtra("orderno", orderno);
                    startActivity(intent);
                    finish();
                }else if(status.equalsIgnoreCase("Approval Pending")){
                    Intent intent = new Intent(DealerPreviousActivity2.this, ApprovalPendingOrderDetails2.class);
                    intent.putExtra("orderno", orderno);
                    startActivity(intent);
                    finish();
                }else if(status.equalsIgnoreCase("Dispatched")){
                    Intent intent = new Intent(DealerPreviousActivity2.this, DispatchedOrderDetails2.class);
                    intent.putExtra("orderno", orderno);
                    startActivity(intent);
                    finish();
                }else if(status.equalsIgnoreCase("Delivered")){
                    Intent intent = new Intent(DealerPreviousActivity2.this, DealerDeliveryOrderDetails2.class);
                    intent.putExtra("orderno", orderno);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(DealerPreviousActivity2.this, "Sorry,Can't open this order", Toast.LENGTH_SHORT).show();

                }
            }
        });
        alllist=new ArrayList<>();

    }
    public  void getAllOrdrList(){
            progressDialog=new ProgressDialog(DealerPreviousActivity2.this);
            progressDialog.setMessage("loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            String url;
            url = Webservice2.DEALER_ALL_ORDER;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            parseallorder(response);
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
                            Toast.makeText(DealerPreviousActivity2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams()
                {
                    //RememberData rememberData=new RememberData();
                    RememberData2 rememberData= Sharepreferences2.getDealerinfo(DealerPreviousActivity2.this);
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("DealerCode",rememberData.getDealerCode());
                    params.put("Warehouse_Code",rememberData.getDealerWarehouse());
                    /*params.put("DealerCode","DC286");
                    params.put("Warehouse_Code","WH1");*/
                    System.out.println(rememberData.getDealerCode()+" %%%%%%% "     +rememberData.getDealerWarehouse());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
          //  AppController.getInstance().getRequestQueue().getCache().remove(url);
          //  AppController.getInstance().getRequestQueue().getCache().clear();

    }
    public  void  parseallorder(String response){
        try {
            alllist.clear();
            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){

                JSONArray listarray=parentObj.getJSONArray("data");
                for(int i=0;i<listarray.length();i++){
                    JSONObject inerobj=listarray.getJSONObject(i);
                    HashMap<String,String> list=new HashMap<>();

                    String Invoice_number=inerobj.optString("Invoice_number");
                    String Order_Number=inerobj.optString("Order_Number");
                    String Order_Date=inerobj.optString("Order_Date");
                    String Quantity=inerobj.optString("Quantity");
                    String Order_Amount=inerobj.optString("Order_Amount");
                    String Order_Status=inerobj.optString("Order_Status");

                    list.put("Invoice_number",Invoice_number);
                    list.put("Order_Number",Order_Number);
                    list.put("Order_Date",Order_Date);
                    list.put("Quantity",Quantity);
                    list.put("Order_Amount",Order_Amount);
                    list.put("Order_Status",Order_Status);

                    alllist.add(list);
                }
               // Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
            }else if(status.equalsIgnoreCase("error")) {
               // String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), "No Records", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        order_list.setAdapter(new DealerOrderListAdapter2(DealerPreviousActivity2.this,alllist));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DealerPreviousActivity2.this);
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
                if(progressDialog!=null) {
                    progressDialog.dismiss();
                }
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
