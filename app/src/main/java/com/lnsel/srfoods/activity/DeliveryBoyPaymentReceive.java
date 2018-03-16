package com.lnsel.srfoods.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lnsel.srfoods.MainActivity;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.appconroller.AppController;
import com.lnsel.srfoods.settergetterclass.RememberData;
import com.lnsel.srfoods.settergetterclass.payment;
import com.lnsel.srfoods.util.CreateDialog;
import com.lnsel.srfoods.util.Logout;
import com.lnsel.srfoods.util.Sharepreferences;
import com.lnsel.srfoods.util.Webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeliveryBoyPaymentReceive extends Activity {
    Button btnBack,btnLogout;
    TextView headerTxt,due_amount,total_amount,tv_bankname,tv_chequeno;
    LinearLayout ll_bankname,ll_cheque;
    EditText etn_exact_amount,cheque_no;
    AutoCompleteTextView bank_name;
    Button btn_pay,btn_skip;
    RadioButton radio_cheque,radio_cash;
    RadioGroup radio_group;
    private RadioButton radioButton;

    String orderno="";
    String Dealer_Code="";
    String Payment_Amount="";
    String Payment_ID="";
    String bankname="";
    String chequeno="";
    String paymentmode="";

    private String uniqueId;

    ArrayList<String> bankList=new ArrayList<>();
    ArrayList<String> bankId=new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;
    ProgressDialog progressDialog1;
    ProgressDialog progressDialog2;
    ProgressDialog progressDialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delivery_boy_payment_receive);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog1!=null)
                    progressDialog1.dismiss();
                if(progressDialog2!=null)
                    progressDialog2.dismiss();
                if(progressDialog3!=null)
                    progressDialog3.dismiss();
                stopService(new Intent(DeliveryBoyPaymentReceive.this,LocationService.class));
                Logout.logout(DeliveryBoyPaymentReceive.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog1!=null)
                    progressDialog1.dismiss();
                if(progressDialog2!=null)
                    progressDialog2.dismiss();
                if(progressDialog3!=null)
                    progressDialog3.dismiss();
                startActivity(new Intent(DeliveryBoyPaymentReceive.this,DeliveryBoyOrderListActivity.class));
                finish();
            }
        });
        btn_skip=(Button)findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callskipdialog();
            }
        });
        headerTxt.setText("Payment");
        etn_exact_amount=(EditText)findViewById(R.id.etn_exact_amount);
        due_amount=(TextView) findViewById(R.id.due_amount);
        due_amount.setText("Rs. "+payment.getDueAmt()+"/-");
        total_amount=(TextView)findViewById(R.id.total_amount);
        total_amount.setText("Rs. "+payment.getTotalAmt()+"/-");
        bank_name=(AutoCompleteTextView)findViewById(R.id.bank_name);

        arrayAdapter = new ArrayAdapter<String>(
                DeliveryBoyPaymentReceive.this, android.R.layout.simple_dropdown_item_1line,
                bankList);

        bank_name.setAdapter(arrayAdapter);
        bank_name.setThreshold(1);
        bank_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                bank_name.showDropDown();
            }
        });


        cheque_no=(EditText)findViewById(R.id.cheque_no);

        ll_cheque=(LinearLayout)findViewById(R.id.ll_cheque);
        ll_bankname=(LinearLayout)findViewById(R.id.ll_bankname);

        radio_group=(RadioGroup)findViewById(R.id.radio_group);
        radio_cheque=(RadioButton)findViewById(R.id.radio_cheque);
        radio_cheque.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radio_group.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                ll_bankname.setVisibility(View.VISIBLE);
                ll_cheque.setVisibility(View.VISIBLE);

                Toast.makeText(DeliveryBoyPaymentReceive.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();
                paymentmode=radioButton.getText().toString();
            }
        });
        radio_cash=(RadioButton)findViewById(R.id.radio_cash);
        radio_cash.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radio_group.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                ll_bankname.setVisibility(View.GONE);
                ll_cheque.setVisibility(View.GONE);
                cheque_no.setText("");
                bank_name.setText("");
                radioButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(DeliveryBoyPaymentReceive.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();
                paymentmode=radioButton.getText().toString();
            }
        });

        btn_pay=(Button)findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.valueOf(payment.getDueAmt())==0){
                    CreateDialog.showDialog(DeliveryBoyPaymentReceive.this,"payment can not be process");
                }else if(paymentmode.equalsIgnoreCase("")){
                    CreateDialog.showDialog(DeliveryBoyPaymentReceive.this,"Select payment mode");
                }else if(etn_exact_amount.getText().toString().equalsIgnoreCase("")){
                    CreateDialog.showDialog(DeliveryBoyPaymentReceive.this,"enter pay amount");
                }/*else if(Integer.valueOf(etn_exact_amount.getText().toString())>Integer.valueOf(payment.getDueAmt())){
                    CreateDialog.showDialog(DeliveryBoyPaymentReceive.this,"pay amount not getter than due amount");
                }*/else if(Integer.valueOf(etn_exact_amount.getText().toString())==0){
                    CreateDialog.showDialog(DeliveryBoyPaymentReceive.this,"pay amount can not be 0");
                }else if(paymentmode.equalsIgnoreCase("cash")){
                    //submitPayment();
                    getpaymentId();
                }else if(cheque_no.getText().toString().equalsIgnoreCase("")){
                    CreateDialog.showDialog(DeliveryBoyPaymentReceive.this,"enter cheque no");
                }else if(bank_name.getText().toString().equalsIgnoreCase("")){
                    CreateDialog.showDialog(DeliveryBoyPaymentReceive.this,"enter bank name");
                }else {
                   // submitPayment();
                    bankname=bank_name.getText().toString();
                    getpaymentId();
                }
            }
        });

       // payment pay=new payment();
        orderno=payment.getOrderNumber();
        Dealer_Code=payment.getDealerCode();
        Payment_Amount=payment.getDueAmt();

      //  getpaymentId();

        getbankname();

    }


    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:",String.valueOf(todaysDate));
        return(String.valueOf(todaysDate));

    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));

    }

    public void getpaymentId(){
        progressDialog3=new ProgressDialog(DeliveryBoyPaymentReceive.this);
        progressDialog3.setMessage("loading...");
        progressDialog3.show();
        progressDialog3.setCancelable(false);
        progressDialog3.setCanceledOnTouchOutside(false);
        String url;
        url = Webservice.GET_PATMENT_ID;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parsepaymentId(response);
                        if(progressDialog3!=null)
                            progressDialog3.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog3!=null)
                            progressDialog3.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(DeliveryBoyPaymentReceive.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                if(paymentmode.equalsIgnoreCase("cheque")) {
                    params.put("Dealer_Code", Dealer_Code);
                    params.put("Payment_Amount", etn_exact_amount.getText().toString());
                    params.put("Payment_Date", getTodaysDate());
                    params.put("Payment_Mode", "cheque");//[cash, cheque]
                    params.put("Cheque_No", cheque_no.getText().toString());
                }else if(paymentmode.equalsIgnoreCase("cash")) {
                    params.put("Dealer_Code", Dealer_Code);
                    params.put("Payment_Amount", etn_exact_amount.getText().toString());
                    params.put("Payment_Date", getTodaysDate());
                    params.put("Payment_Mode", "cash");//[cash, cheque]
                    params.put("Cheque_No", cheque_no.getText().toString());
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parsepaymentId(final String response){

       // System.out.println("######### "+response);
        try {
            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                Payment_ID=parentObj.optString("Payment_ID");
                submitPayment();
                Toast.makeText(getApplicationContext(), "Payment_ID "+Payment_ID, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void submitPayment(){

        progressDialog2=new ProgressDialog(DeliveryBoyPaymentReceive.this);
        progressDialog2.setMessage("loading...");
        progressDialog2.show();
        progressDialog2.setCancelable(false);
        progressDialog2.setCanceledOnTouchOutside(false);
        String url;
        url = Webservice.PROCESS_PAYMENT;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parsepaymentstatus(response);
                        if(progressDialog2!=null)
                            progressDialog2.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog2!=null)
                            progressDialog2.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(DeliveryBoyPaymentReceive.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("pmtID",Payment_ID);
                params.put("dealerCode",Dealer_Code);
                params.put("orderNo",orderno);
                params.put("pmtAmt",etn_exact_amount.getText().toString());
                params.put("pmtDate",getTodaysDate());
                params.put("pmtMode",paymentmode);//[cash, cheque]
                params.put("chequeNo",cheque_no.getText().toString());
                params.put("bankName",bankname);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parsepaymentstatus(final String response){


         System.out.println("Payment Status "+response);

        try {
            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(DeliveryBoyPaymentReceive.this,SignatureActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    public void getbankname(){

        progressDialog1=new ProgressDialog(DeliveryBoyPaymentReceive.this);
        progressDialog1.setMessage("loading...");
        progressDialog1.show();
        progressDialog1.setCancelable(false);
        progressDialog1.setCanceledOnTouchOutside(false);
        String url;
        //System.out.println("StateID "+stateId);

        url = Webservice.BANK_NAME;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parsebankname(response);
                        if(progressDialog1!=null)
                            progressDialog1.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog1!=null)
                            progressDialog1.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(DeliveryBoyPaymentReceive.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                /*params.put("country_id","101");
                params.put("format","json");*/
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }


    public void parsebankname(final String response){
            try {
                bankList.clear();
                bankId.clear();

                JSONObject parentObj = new JSONObject(response);

                JSONArray jArray = parentObj.getJSONArray("data");

               // bankList.add("Select Bank");
               // bankId.add("-1");

                for(int i=0;i<jArray.length();i++){

                    JSONObject innerObj = jArray.optJSONObject(i);
                    //JSONObject records = innerObj.optJSONObject("records");

                    bankList.add(innerObj.optString("Bank_Name"));
                    bankId.add(innerObj.optString("ID"));
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            arrayAdapter.notifyDataSetChanged();
    }

    public void callskipdialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(DeliveryBoyPaymentReceive.this);
        dialog.setCancelable(false);
        dialog.setTitle("Are you sure");
        dialog.setMessage("You want to deliver product with out payment?" );
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(progressDialog1!=null)
                    progressDialog1.dismiss();
                if(progressDialog2!=null)
                    progressDialog2.dismiss();
                if(progressDialog3!=null)
                    progressDialog3.dismiss();
                Intent intent=new Intent(DeliveryBoyPaymentReceive.this,SignatureActivity.class);
                startActivity(intent);
                finish();
            }
        })
                .setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliveryBoyPaymentReceive.this);
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
                if(progressDialog1!=null)
                    progressDialog1.dismiss();
                if(progressDialog2!=null)
                    progressDialog2.dismiss();
                if(progressDialog3!=null)
                    progressDialog3.dismiss();
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
