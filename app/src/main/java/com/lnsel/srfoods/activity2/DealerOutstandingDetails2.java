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
import com.lnsel.srfoods.activity2.DealerOutstanding2;
import com.lnsel.srfoods.adapter.DealerOutstandingDetailsListAdapter;
import com.lnsel.srfoods.adapter2.DealerOutstandingDetailsListAdapter2;
import com.lnsel.srfoods.util2.Logout2;
import com.lnsel.srfoods.util2.Webservice2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*import com.lnsel.srfoods.filedownload.StringTokenizer;*/


public class DealerOutstandingDetails2 extends Activity  {

    Button btnBack,btnLogout;
    TextView headerTxt,txt_order_no,txt_outstanding,txt_invoice_amount;
    ArrayList<HashMap<String,String>> alllist;
    ListView scheme_list;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;
    TextView total_due;
    String totaldue="";
    String orderno="",due_amount="",Invoice_number="",Invoice_amount="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_outstanding_details_pcj);
        Bundle bundle = getIntent().getExtras();
        orderno = bundle.getString("orderno");
        due_amount=bundle.getString("due_amount");
        Invoice_number=bundle.getString("Invoice_number");
        Invoice_amount=bundle.getString("Invoice_amount");

        txt_order_no=(TextView)findViewById(R.id.txt_order_no);
        txt_order_no.setText("Invoice Number : "+Invoice_number);
        txt_outstanding=(TextView)findViewById(R.id.txt_outstanding);
        txt_outstanding.setText("Due Amount : Rs. "+due_amount+"/-");
        txt_invoice_amount=(TextView)findViewById(R.id.txt_invoice_amount);
        txt_invoice_amount.setText("Invoice Amount : Rs. "+Invoice_amount+"/-");

        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Logout2.logout(DealerOutstandingDetails2.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(DealerOutstandingDetails2.this,DealerOutstanding2.class));
                finish();
            }
        });
        headerTxt.setText("Outstanding  Details");
        scheme_list=(ListView)findViewById(R.id.scheme_list);
        alllist=new ArrayList<>();
        getAllOutstanding();
    }
    public  void getAllOutstanding(){
        progressDialog=new ProgressDialog(DealerOutstandingDetails2.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String url;
        url = Webservice2.DEALER_OUTSTANDING_DETAILS;

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
                        Toast.makeText(DealerOutstandingDetails2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("orderNo",orderno);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);

    }
    public  void  parsealloutstanding(String response){
        try {
            alllist.clear();
            JSONObject parentObj = new JSONObject(response);

            JSONArray listarray=parentObj.getJSONArray("data");
            for(int i=0;i<listarray.length();i++){
                JSONObject inerobj=listarray.getJSONObject(i);
                HashMap<String,String> list=new HashMap<>();

                String Payment_Id=inerobj.optString("Payment_Id");
                String Payment_Amount=inerobj.optString("Payment_Amount");
                String Payment_Mode=inerobj.optString("Payment_Mode");
                String Payment_Date=inerobj.optString("Payment_Date");

                String Cheque_No=inerobj.optString("Cheque_No");
                String Payment_Status=inerobj.optString("Payment_Status");

                list.put("Payment_Id",Payment_Id);
                list.put("Payment_Amount",Payment_Amount);
                list.put("Payment_Mode",Payment_Mode);
                list.put("Payment_Date",Payment_Date);

                list.put("Cheque_No",Cheque_No);
                list.put("Payment_Status",Payment_Status);


                alllist.add(list);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       scheme_list.setAdapter(new DealerOutstandingDetailsListAdapter2(DealerOutstandingDetails2.this,alllist));

    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DealerOutstandingDetails2.this);
        alertDialog.setTitle("Confirm Exit...");
        alertDialog.setMessage("Are you sure you want exit this?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


}
