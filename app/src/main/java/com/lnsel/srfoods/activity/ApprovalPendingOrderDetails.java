package com.lnsel.srfoods.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.adapter.CustomAdapter;
import com.lnsel.srfoods.adapter.PendingOrderListAdapter;
import com.lnsel.srfoods.appconroller.AppController;
import com.lnsel.srfoods.settergetterclass.RememberData;
import com.lnsel.srfoods.util.Logout;
import com.lnsel.srfoods.util.Sharepreferences;
import com.lnsel.srfoods.util.Webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApprovalPendingOrderDetails extends Activity {

    Button btnBack,btnLogout;
    TextView headerTxt;
    ListView listView;
    ArrayList<HashMap<String,String>> listarray;

    String Total_Quantity="",Total_Weight="",Total_With_Out_VAT_Before_Dsicount="",Total_With_Out_VAT_After_Dsicount="",
            Total_VAT="",Discount_Percentage="",Total_Dealer_Discount="",invoicetotal="";

    TextView total_quantity,total_weiget,total_without_vat_before_discount,total_without_vat_after_discount,total_vat,discount_percentage,
            total_discount,invoice_total;

    String orderno="";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_pending_order_details);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Logout.logout(ApprovalPendingOrderDetails.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(ApprovalPendingOrderDetails.this,DealerPreviousActivity.class));
                finish();
            }
        });
        headerTxt.setText("Pending Order Details");
        Bundle bundle = getIntent().getExtras();
        orderno = bundle.getString("orderno");
        setwidget();

    }

    public  void setwidget() {

        total_quantity=(TextView)findViewById(R.id.total_quantity);
        total_weiget=(TextView)findViewById(R.id.total_weiget);
        total_without_vat_before_discount=(TextView)findViewById(R.id.total_without_vat_before_discount);
        total_without_vat_after_discount=(TextView)findViewById(R.id.total_without_vat_after_discount);
        total_vat=(TextView)findViewById(R.id.total_vat);
        discount_percentage=(TextView)findViewById(R.id.discount_percentage);
        total_discount=(TextView)findViewById(R.id.total_discount);
        invoice_total=(TextView)findViewById(R.id.invoice_total);

        listView = (ListView) findViewById(R.id.order_list);
        listarray = new ArrayList<HashMap<String, String>>();
        getOrderDetails();
    }


    public  void getOrderDetails(){
        progressDialog=new ProgressDialog(ApprovalPendingOrderDetails.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.ORDER_DETAILS_BY_ORDER_NO;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseOrderDetails(response);
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
                        Toast.makeText(ApprovalPendingOrderDetails.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData rememberData= Sharepreferences.getDealerinfo(ApprovalPendingOrderDetails.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("dealerCode",rememberData.getDealerCode());
                params.put("orderNo",orderno);
                //params.put("dealerCode","DC278");
                // params.put("orderNo","SRFB/RC/SO/16-17/09356");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public  void  parseOrderDetails(String response){
        try {

            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            listarray.clear();
            {
                Total_Quantity="";
                Total_Weight = "";
                Total_With_Out_VAT_Before_Dsicount = "";
                Total_With_Out_VAT_After_Dsicount = "";
                Total_VAT = "";Discount_Percentage = "";
                Total_Dealer_Discount = "";
                invoicetotal = "";
            }

            if(status.equalsIgnoreCase("success")){
                JSONArray jarray=parentObj.getJSONArray("item_list");

                for(int i=0;i<jarray.length();i++){

                    HashMap<String,String> item=new HashMap<>();

                    JSONObject innerObj = jarray.optJSONObject(i);
                    //JSONObject records = innerObj.optJSONObject("records");
                    item.put("SL_NO",innerObj.optString("SL_NO"));
                    item.put("Product_Name",innerObj.optString("Product_Name"));
                    item.put("MRP",innerObj.optString("MRP"));
                    item.put("Price_With_Out_VAT",innerObj.optString("Price_With_Out_VAT"));
                    item.put("Weight",innerObj.optString("Weight"));
                    item.put("Product_Qty",innerObj.optString("Product_Qty"));

                    item.put("Total_Weight",innerObj.optString("Total_Weight"));
                    item.put("Total_With_Out_VAT_Before_Dsicount",innerObj.optString("Total_With_Out_VAT_Before_Dsicount"));
                    item.put("Total_With_Out_VAT_After_Dsicount",innerObj.optString("Total_With_Out_VAT_After_Dsicount"));
                    item.put("VAT",innerObj.optString("VAT"));
                    item.put("Free_Item",innerObj.optString("Free_Item"));

                   // item.put("Product_Images",innerObj.optString("Product_Images"));
                    listarray.add(item);
                }
                JSONObject result_all_total=parentObj.getJSONObject("result_all_total");
                Total_Quantity=result_all_total.optString("Total_Quantity");
                total_quantity.setText("Total Quantity : "+Total_Quantity);
                Total_Weight=result_all_total.optString("Total_Weight");
                total_weiget.setText("Total Weight : "+Total_Weight+" Kg");
                Total_With_Out_VAT_Before_Dsicount=result_all_total.optString("VAT_5P");
                total_without_vat_before_discount.setText("VAT(5%) : "+Total_With_Out_VAT_Before_Dsicount+" INR");
                Total_With_Out_VAT_After_Dsicount=result_all_total.optString("VAT_14_5_P");
                total_without_vat_after_discount.setText("VAT(14.5%) : "+Total_With_Out_VAT_After_Dsicount+" INR");
                Total_VAT=result_all_total.optString("Total_VAT");
                total_vat.setText("Total VAT : "+Total_VAT+" INR");

                JSONObject discount_applied_for=parentObj.getJSONObject("discount_applied_for");
                Discount_Percentage=discount_applied_for.optString("Discount_Percentage");
                discount_percentage.setText("Discount Percentage : "+Discount_Percentage);
                Total_Dealer_Discount=discount_applied_for.optString("Total_Dealer_Discount");
                total_discount.setText("Total Dealer Discount : "+Total_Dealer_Discount+" INR");
                invoicetotal=parentObj.optString("invoice_total");
                invoice_total.setText("Invoice Total : "+invoicetotal+" INR");

            }else if(status.equalsIgnoreCase("error")){
                String message=parentObj.optString("message");
                Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // callviewdialog();
        listView.setAdapter(new PendingOrderListAdapter(ApprovalPendingOrderDetails.this, listarray));
        //notifyAll();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ApprovalPendingOrderDetails.this);
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
