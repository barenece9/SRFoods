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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.util2.CreateDialog2;
import com.lnsel.srfoods.util2.Webservice2;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ForgotePassActivity2 extends Activity {

    ProgressDialog progressDialog1;
    ProgressDialog progressDialog2;
    Button btnBack;
    TextView headerTxt;
    EditText etndealeremailid;
    Button btnforgetpassdone;
    String dealeremailid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forgote_pass_pcj);
        etndealeremailid=(EditText)findViewById(R.id.etndealeremailid);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog1!=null)
                    progressDialog1.dismiss();
                if(progressDialog2!=null)
                    progressDialog2.dismiss();
                startActivity(new Intent(ForgotePassActivity2.this,DelareLoginActivity2.class));
                finish();
            }
        });
        headerTxt.setText("Forgot Password");
        btnforgetpassdone=(Button) findViewById(R.id.btnforgetpassdone);
        btnforgetpassdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealeremailid=etndealeremailid.getText().toString();
                if(dealeremailid.equalsIgnoreCase("")){
                    CreateDialog2.showDialog(ForgotePassActivity2.this, "Enter registered mobile number");
                }else {
                    doforgotepassword();
                }
            }
        });

    }

    public void doforgotepassword(){
       progressDialog2=new ProgressDialog(ForgotePassActivity2.this);
        progressDialog2.setMessage("loading...");
        progressDialog2.show();
        progressDialog2.setCancelable(false);
        progressDialog2.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice2.FORGOT_PASSWORD;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseForgetpassresponse(response);
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
                        Toast.makeText(ForgotePassActivity2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Dealer_Mobile",dealeremailid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public  void parseForgetpassresponse(String response){
        try {

            JSONObject parentObj = new JSONObject(response);

            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                String Phone_no = parentObj.optString("Phone_no");
                String Password = parentObj.optString("Password");
                String User_Name=parentObj.optString("User_Name");
                //Toast.makeText(getApplicationContext(),status+"Pa",Toast.LENGTH_LONG).show();
                sendsms(Phone_no,Password,User_Name);
            }else if(status.equalsIgnoreCase("error")) {
                String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), status + " : " + message, Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //sms send....

    public  void sendsms(String Dealer_Mobile,String Password,String User_Name){

        progressDialog1=new ProgressDialog(ForgotePassActivity2.this);
        progressDialog1.setMessage("loading...");
        //  progressDialog.show();
        progressDialog1.setCancelable(false);
        progressDialog1.setCanceledOnTouchOutside(false);
        String url;

        //url = "http://bluewavesmedia.co.in/api/mt/SendSMS?user=srfoods&password=srfoods@123&senderid=SRFOOD&channel=2&DCS=0&flashsms=0&number=91' + dealerMob + '&text=' + smsMsg + ' is placed and will be confirmed shortly&route=1'";
        String msgtext="Your PCG credential is Username : "+User_Name+" & Password : "+Password+", Thank You for contacting with us.";
        String encodedURL="";
        try {
            encodedURL = URLEncoder.encode(msgtext, "UTF-8");
        }catch (Exception e){
            System.out.println("Exception....@@@@@@@@@@@@@@2");
        }
        url="http://bluewavesmedia.co.in/api/mt/SendSMS?user=srfoods&password=srfoods@1232022&senderid=SRFOOD&channel=2&DCS=0&flashsms=0"+"&number=91"+Dealer_Mobile+"&text="+encodedURL+"&route=1";
        // url="http://bluewavesmedia.co.in/api/mt/SendSMS";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(" @@@@@@@@@@@@@@@ "+response);
                        parsesensmsstatus(response);
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
                        Toast.makeText(ForgotePassActivity2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData= Sharepreferences.getDealerinfo(DealerNeworder2.this);
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
        //AppController.getInstance().getRequestQueue().getCache().remove(url);
        //AppController.getInstance().getRequestQueue().getCache().clear();

    }
    public  void parsesensmsstatus(String response){
        Toast.makeText(getApplicationContext(), "Password send to your registered mobile number", Toast.LENGTH_LONG).show();
        startActivity(new Intent(ForgotePassActivity2.this,DelareLoginActivity2.class));
        finish();

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ForgotePassActivity2.this);
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
