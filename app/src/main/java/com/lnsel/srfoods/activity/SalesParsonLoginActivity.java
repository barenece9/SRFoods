package com.lnsel.srfoods.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.lnsel.srfoods.settergetterclass.RememberData;
import com.lnsel.srfoods.util.CreateDialog;
import com.lnsel.srfoods.util.Sharepreferences;
import com.lnsel.srfoods.util.Webservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SalesParsonLoginActivity extends Activity {

    EditText etndealerusername,etndealerpassword;
    Button btndealerlogin;
    String dealerusername="",dealerpassword="";
    //ProgressDialog progressDialog;
    Button btnBack,btnLogout;
    TextView headerTxt;
    CheckBox rememberme;
    TextView forgotpass;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delare_login);
        regwidgets();
    }
    public void regwidgets(){
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setVisibility(View.GONE);
        etndealerusername=(EditText)findViewById(R.id.etndealerusername);
        etndealerpassword=(EditText)findViewById(R.id.etndealerpassword);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(SalesParsonLoginActivity.this,LogintypeActivity.class));
                finish();
            }
        });
        headerTxt.setText("Sales Person Login");
        forgotpass=(TextView)findViewById(R.id.forgotpass);
        forgotpass.setVisibility(View.GONE);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(SalesParsonLoginActivity.this,ForgotePassActivity.class));
                finish();
            }
        });
        rememberme = (CheckBox)findViewById(R.id.rememberme);
        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean check) {
                // TODO Auto-generated method stub

                if(check){

                    dealerusername=etndealerusername.getText().toString();
                    dealerpassword=etndealerpassword.getText().toString();

                    Sharepreferences.setRememberMeSalePerson(SalesParsonLoginActivity.this, dealerusername, dealerpassword, 1);

                }else{

                    Sharepreferences.setRememberMeSalePerson(SalesParsonLoginActivity.this, "", "", 0);
                }
            }
        });
        btndealerlogin=(Button)findViewById(R.id.btndealerlogin);
        btndealerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealerusername=etndealerusername.getText().toString();
                dealerpassword=etndealerpassword.getText().toString();
                if(dealerusername.equalsIgnoreCase(""))
                {
                    CreateDialog.showDialog(SalesParsonLoginActivity.this, "Enter username");

                }else if(dealerpassword.equalsIgnoreCase("")){

                    CreateDialog.showDialog(SalesParsonLoginActivity.this, "Enter password");

                }else {
                    if(isNetworkAvailable()) {
                        dologin();
                    }else {
                        CreateDialog.showDialog(SalesParsonLoginActivity.this, "No Network Connection");
                    }
                }
            }
        });

        //rebember pass and username fetch..
        RememberData rememberData = Sharepreferences.getRememberMeSalePerson(this);

        if(rememberData.getRemember() == 1){

            etndealerusername.setText(rememberData.getUserName());
            etndealerpassword.setText(rememberData.getPassword());
            rememberme.setChecked(true);
        }else{

            etndealerusername.setText("");
            etndealerpassword.setText("");
            rememberme.setChecked(false) ;
        }
    }
    public  void dologin(){

        progressDialog=new ProgressDialog(SalesParsonLoginActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.SALES_PERSON_LOGIN;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parselOGINresponse(response);
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
                        Toast.makeText(SalesParsonLoginActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("User_Name",dealerusername);
                params.put("Password",dealerpassword);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       /* AppController.getInstance().getRequestQueue().getCache().remove(url);
        AppController.getInstance().getRequestQueue().getCache().clear();*/
    }

    public  void parselOGINresponse(String response){
        try {

            JSONObject parentObj = new JSONObject(response);


            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                String Employee_Code=parentObj.optString("Employee_Code");
                String Warehouse_Code=parentObj.optString("Warehouse_Code");
                String Employee_Name=parentObj.optString("Employee_Name");

                Sharepreferences.setSalesPersoninfo(SalesParsonLoginActivity.this,Employee_Code,Warehouse_Code);

               // Toast.makeText(getApplicationContext(),status+" : Login as "+Delear_Name,Toast.LENGTH_LONG).show();

                RememberData rememberData = Sharepreferences.getRememberMeSalePerson(this);
                if(rememberData.getRemember() == 1){
                    dealerusername=etndealerusername.getText().toString();
                    dealerpassword=etndealerpassword.getText().toString();
                    Sharepreferences.setRememberMeSalePerson(SalesParsonLoginActivity.this, dealerusername, dealerpassword, 1);
                }else{
                    Sharepreferences.setRememberMeSalePerson(SalesParsonLoginActivity.this, "", "", 0);
                }

                startActivity(new Intent(SalesParsonLoginActivity.this,SalesPersonActivity.class));
                finish();
            }else if(status.equalsIgnoreCase("error")) {
                String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), status + " : " + message, Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SalesParsonLoginActivity.this);
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
