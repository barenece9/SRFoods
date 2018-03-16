package com.lnsel.srfoods.activity2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
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
import com.lnsel.srfoods.activity2.DelivaryActivity2;
import com.lnsel.srfoods.activity.DeliveryOrderDetails;
import com.lnsel.srfoods.activity.LocationService;
import com.lnsel.srfoods.adapter.DeliveryBoyOrderListAdapter;
import com.lnsel.srfoods.adapter2.DeliveryBoyOrderListAdapter2;
import com.lnsel.srfoods.settergetterclass2.RememberData2;
import com.lnsel.srfoods.settergetterclass2.payment2;
import com.lnsel.srfoods.util2.Logout2;
import com.lnsel.srfoods.util2.Sharepreferences2;
import com.lnsel.srfoods.util2.Webservice2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeliveryBoyOrderListActivity2 extends Activity {

    Button btnBack,btnLogout;
    TextView headerTxt;
    ListView order_list;
    ArrayList<HashMap<String,String>> alllist;


    private static final int REQUEST_CALL = 113;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_order_list_pcj);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                stopService(new Intent(DeliveryBoyOrderListActivity2.this,LocationService2.class));
                Logout2.logout(DeliveryBoyOrderListActivity2.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(DeliveryBoyOrderListActivity2.this,DelivaryActivity2.class));
                finish();
            }
        });
        headerTxt.setText("Pending Order");
        setwidget();
        getAllOrdrList();
    }

    public  void setwidget(){
        order_list=(ListView)findViewById(R.id.order_list);
        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String orderno=alllist.get(position).get("Order_Number");
              //  String status=alllist.get(position).get("Order_Status");
                // startActivity(new Intent(DealerPreviousActivity.this,ConfirmOrderDetails.class));

            }
        });
        alllist=new ArrayList<>();

    }
    public  void getAllOrdrList(){
        progressDialog=new ProgressDialog(DeliveryBoyOrderListActivity2.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;
        url = Webservice2.Assigned_Order_list_to_Delivery_Boy;

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
                        Toast.makeText(DeliveryBoyOrderListActivity2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData=new RememberData();
                RememberData2 rememberData= Sharepreferences2.getDeleveryinfo(DeliveryBoyOrderListActivity2.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Employee_Code",rememberData.getDealerCode());
                //params.put("Employee_Code","EC201");
                System.out.println(rememberData.getDealerCode()+" %%%%%% "     +rememberData.getDealerWarehouse());
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
                    String Order_Assign_Date=inerobj.optString("Order_Assign_Date");
                    String Est_Delivery_Date=inerobj.optString("Est_Delivery_Date");
                    String Dealer_Code=inerobj.optString("Dealer_Code");
                    String Dealer_Name=inerobj.optString("Dealer_Name");

                    String Shipping_Address=inerobj.optString("Shipping_Address");
                    String Dealer_Mobile=inerobj.optString("Dealer_Mobile");
                    String Total_Amt=inerobj.optString("Total_Amt");
                    String Order_Qty=inerobj.optString("Order_Qty");
                    String Due_Amt=inerobj.optString("Due_Amt");

                    list.put("Invoice_number",Invoice_number);
                    list.put("Order_Number",Order_Number);
                    list.put("Order_Assign_Date",Order_Assign_Date);
                    list.put("Est_Delivery_Date",Est_Delivery_Date);
                    list.put("Dealer_Code",Dealer_Code);
                    list.put("Dealer_Name",Dealer_Name);
                    list.put("Shipping_Address",Shipping_Address);
                    list.put("Dealer_Mobile",Dealer_Mobile);
                    list.put("Total_Amt",Total_Amt);
                    list.put("Due_Amt",Due_Amt);
                    list.put("Order_Qty",Order_Qty);

                    alllist.add(list);
                }
               // Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
            }else if(status.equalsIgnoreCase("Error")) {
                // String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        order_list.setAdapter(new DeliveryBoyOrderListAdapter2(DeliveryBoyOrderListActivity2.this,alllist));
    }


    public void viewdetails(int position){
        Intent intent=new Intent(DeliveryBoyOrderListActivity2.this, DeliveryOrderDetails2.class);
        payment2.setDealerCode(alllist.get(position).get("Dealer_Code"));
        payment2.setOrderNumber(alllist.get(position).get("Order_Number"));
        payment2.setDueAmt(alllist.get(position).get("Due_Amt"));
        payment2.setTotalAmt(alllist.get(position).get("Total_Amt"));
        startActivity(intent);
        finish();
    }

    public void call(String number){

        boolean hasPermissionCall = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionCall) {
            ActivityCompat.requestPermissions(DeliveryBoyOrderListActivity2.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);
        }else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliveryBoyOrderListActivity2.this);
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



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(DeliveryBoyOrderListActivity2.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    //reload my activity with permission granted or use the features what required the permission
                    //finish();
                    //startActivity(getIntent());
                } else
                {
                    Toast.makeText(DeliveryBoyOrderListActivity2.this, "The app was not allowed to call. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

        }

    }



}
