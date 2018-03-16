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
import com.lnsel.srfoods.activity.DealerOutstandingDetails;
import com.lnsel.srfoods.activity2.DealerActivity2;
import com.lnsel.srfoods.adapter.DealerOutstandingListAdapter;
import com.lnsel.srfoods.adapter2.DealerOutstandingListAdapter2;
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

public class DealerOutstanding2 extends Activity {

    Button btnBack,btnLogout;
    TextView headerTxt;
    ArrayList<HashMap<String,String>> alllist;
    ListView scheme_list;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;
    TextView total_due;
    String totaldue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_outstanding_view_pcj);
        total_due=(TextView)findViewById(R.id.total_due);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Logout2.logout(DealerOutstanding2.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(DealerOutstanding2.this,DealerActivity2.class));
                finish();
            }
        });
        headerTxt.setText("Outstanding");
        scheme_list=(ListView)findViewById(R.id.scheme_list);
        alllist=new ArrayList<>();
        getAllOutstanding();
    }


    public  void getAllOutstanding(){
        progressDialog=new ProgressDialog(DealerOutstanding2.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String url;
        url = Webservice2.DEALER_OUTSTANDING;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parsealloutstanding(response);
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
                        Toast.makeText(DealerOutstanding2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData=new RememberData();
                RememberData2 rememberData= Sharepreferences2.getDealerinfo(DealerOutstanding2.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Dealer_Code",rememberData.getDealerCode());
                //params.put("Warehouse_Code",rememberData.getDealerWarehouse());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();

    }
    public  void  parsealloutstanding(String response){
        try {
            alllist.clear();
            JSONObject parentObj = new JSONObject(response);
          //  String status=parentObj.optString("status");

                JSONArray listarray=parentObj.getJSONArray("due");
                for(int i=0;i<listarray.length();i++){
                    JSONObject inerobj=listarray.getJSONObject(i);
                    HashMap<String,String> list=new HashMap<>();

                    String Invoice_number=inerobj.optString("Invoice_number");
                    String order_no=inerobj.optString("order_no");
                    String due_amount=inerobj.optString("due_amount");
                    String Invoice_amount=inerobj.optString("Invoice_amount");
                    String Order_Date=inerobj.optString("Order_Date");

                    list.put("Invoice_number",Invoice_number);
                    list.put("order_no",order_no);
                    list.put("Invoice_amount",Invoice_amount);
                    list.put("due_amount",due_amount);
                    list.put("Order_Date",Order_Date);


                    alllist.add(list);
                }
            totaldue=parentObj.optString("total_due");


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        scheme_list.setAdapter(new DealerOutstandingListAdapter2(DealerOutstanding2.this,alllist));
        total_due.setText("Total Outstanding : Rs. "+totaldue+"/-");
    }


    public void viewdetails(int position){
        Intent intent=new Intent(DealerOutstanding2.this, DealerOutstandingDetails.class);
        intent.putExtra("orderno",alllist.get(position).get("order_no"));
        intent.putExtra("Invoice_number",alllist.get(position).get("Invoice_number"));
        intent.putExtra("Invoice_amount",alllist.get(position).get("Invoice_amount"));
        intent.putExtra("due_amount",alllist.get(position).get("due_amount"));
        startActivity(intent);
        finish();
    }





    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DealerOutstanding2.this);
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
