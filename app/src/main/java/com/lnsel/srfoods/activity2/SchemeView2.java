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
import com.lnsel.srfoods.activity2.DealerActivity2;

import com.lnsel.srfoods.adapter2.DealerSchemeListAdapter2;
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

public class SchemeView2 extends Activity {

    Button btnBack,btnLogout;
    TextView headerTxt;
    ArrayList<HashMap<String,String>> alllist;
    ListView scheme_list;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scheme_view_pcj);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Logout2.logout(SchemeView2.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(SchemeView2.this,DealerActivity2.class));
                finish();
            }
        });
        headerTxt.setText("Scheme");
        scheme_list=(ListView)findViewById(R.id.scheme_list);
        alllist=new ArrayList<>();
       // getScheme();
        getSchemeStatus();
    }


    public  void getSchemeStatus(){
        progressDialog2=new ProgressDialog(SchemeView2.this);
        progressDialog2.setMessage("loading...");
        progressDialog2.setCancelable(false);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.show();
        String url;
        url = Webservice2.DEALER_SCHEME_STATUS;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parsestatus(response);
                        if(progressDialog2!=null)
                            progressDialog2.dismiss();
                        /*if(progressDialog.isShowing())
                            progressDialog.dismiss();*/
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog2!=null)
                            progressDialog2.dismiss();
                       /* if(progressDialog.isShowing())
                            progressDialog.dismiss();*/
                        System.out.println("Error=========="+error);
                        Toast.makeText(SchemeView2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData=new RememberData();
                RememberData2 rememberData= Sharepreferences2.getDealerinfo(SchemeView2.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Dealer_Code",rememberData.getDealerCode());
               // params.put("Warehouse_Code",rememberData.getDealerWarehouse());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
        // AppController.getInstance().getRequestQueue().getCache().remove(url);
        // AppController.getInstance().getRequestQueue().getCache().clear();

    }
    public  void  parsestatus(String response){
        try {
            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("Enable")){
                 getScheme();
                //Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "No Scheme available", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public  void getScheme(){
        progressDialog=new ProgressDialog(SchemeView2.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String url;
        url = Webservice2.DEALER_SCHEME;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseallorder(response);
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        /*if(progressDialog.isShowing())
                            progressDialog.dismiss();*/
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                       /* if(progressDialog.isShowing())
                            progressDialog.dismiss();*/
                        System.out.println("Error=========="+error);
                        Toast.makeText(SchemeView2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData=new RememberData();
                RememberData2 rememberData= Sharepreferences2.getDealerinfo(SchemeView2.this);
                Map<String, String>  params = new HashMap<String, String>();
               // params.put("DealerCode",rememberData.getDealerCode());
                params.put("Warehouse_Code",rememberData.getDealerWarehouse());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();

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

                    String From_Date=inerobj.optString("From_Date");
                    String To_Date=inerobj.optString("To_Date");
                    String Warehouse_Name=inerobj.optString("Warehouse_Name");
                    String Product_Name=inerobj.optString("Product_Name");
                    String Range_Quantity=inerobj.optString("Range_Quantity");

                    String free_product=inerobj.optString("free_product");
                    String Quantity=inerobj.optString("Quantity");
                    String Discount=inerobj.optString("Discount");

                    list.put("From_Date",From_Date);
                    list.put("To_Date",To_Date);
                    list.put("Warehouse_Name",Warehouse_Name);
                    list.put("Product_Name",Product_Name);
                    list.put("Range_Quantity",Range_Quantity);
                    list.put("free_product",free_product);
                    list.put("Quantity",Quantity);
                    list.put("Discount",Discount);

                    alllist.add(list);
                }
               // Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
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
        scheme_list.setAdapter(new DealerSchemeListAdapter2(SchemeView2.this,alllist));
    }





    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SchemeView2.this);
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
