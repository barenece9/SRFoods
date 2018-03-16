package com.lnsel.srfoods.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.lnsel.srfoods.appconroller.AppController;
import com.lnsel.srfoods.util.CreateDialog;
import com.lnsel.srfoods.util.Webservice;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DelareRegActivity extends Activity {


    Button btnBack;
    Button btnLogout;
    TextView headerTxt;

    Button btndealerreg;
    EditText etndealername,etndealeremail,etndealerephone,etndealeremobile,etndealereadditionalmobile,etndealerepassword,
            etndealereaddress,etndealereshippingaddress,etndealerlocation,etndealercity,etndealerzip,etndealervatno,
            etndealerusername,etndealereconpassword;
    Spinner spinnervatreg,spinnerdealerstate,spinnerwarehouselist,spinnertranspoter;

    String dealername="",dealeremail="",dealerephone="",dealeremobile="",dealereadditionalmobile="",dealerepassword="",
            dealereaddress="",dealereshippingaddress="",dealerlocation="",dealercity="",dealerzip="",dealervatno="",dealereconpassword,
            dealerusername;

    String RegStateid="",RegState="",selectwarehouse="",selecttranspoter="";
    String VatRegType="";

    ArrayList<String> vatreg = new ArrayList<String>();
    ArrayList<String> vatregId = new ArrayList<String>();

    ArrayList<String> stateList = new ArrayList<String>();
    ArrayList<String> stateId = new ArrayList<String>();

    ArrayAdapter<String> adapterState;
    ArrayAdapter<String> adapterVatReg;

    ArrayList<String> warehouseList = new ArrayList<String>();
    ArrayList<String> warehouseId = new ArrayList<String>();

    ArrayList<String> transpoterList = new ArrayList<String>();
    ArrayList<String> transpoterId = new ArrayList<String>();

    ArrayAdapter<String> adapterwarehouse;
    ArrayAdapter<String> adaptertranspoter;

    ProgressDialog progressDialog1;
    ProgressDialog progressDialog2;
    ProgressDialog progressDialog3;
    ProgressDialog progressDialog4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delare_reg);
        getwidgets();
        doCityList();
    }
    void getwidgets(){

        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnBack=(Button)findViewById(R.id.btnBack);
        headerTxt.setText("Dealer Registation");
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setVisibility(View.GONE);

        etndealername=(EditText)findViewById(R.id.etndealername);
        etndealeremail=(EditText)findViewById(R.id.etndealeremail);
        etndealerephone=(EditText)findViewById(R.id.etndealerephone);
        etndealeremobile=(EditText)findViewById(R.id.etndealeremobile);
        etndealereadditionalmobile=(EditText)findViewById(R.id.etndealereadditionalmobile);
        etndealerepassword=(EditText)findViewById(R.id.etndealerepassword);
        etndealereaddress=(EditText)findViewById(R.id.etndealereaddress);
        etndealereshippingaddress=(EditText)findViewById(R.id.etndealereshippingaddress);
        etndealerlocation=(EditText)findViewById(R.id.etndealerlocation);
        etndealercity=(EditText)findViewById(R.id.etndealercity);
        etndealerzip=(EditText)findViewById(R.id.etndealerzip);
        etndealervatno=(EditText)findViewById(R.id.etndealervatno);

        etndealerusername=(EditText)findViewById(R.id.etndealerusername);
        etndealereconpassword=(EditText)findViewById(R.id.etndealereconpassword);

        spinnervatreg=(Spinner)findViewById(R.id.spinnervatreg);
        spinnerdealerstate=(Spinner)findViewById(R.id.spinnerdealerstate);
        spinnerwarehouselist=(Spinner)findViewById(R.id.spinnerwarehouselist);
        spinnertranspoter=(Spinner)findViewById(R.id.spinnertranspoter);


        adapterState = new ArrayAdapter<String>(DelareRegActivity.this,R.layout.spinner_rows, stateList);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdealerstate.setAdapter(adapterState);

        vatreg.add("-Select Vat Reg Type-");
        vatreg.add("Registered");
        vatreg.add("Applied For");
        vatreg.add("Unregistered");

        adapterVatReg = new ArrayAdapter<String>(DelareRegActivity.this,R.layout.spinner_rows, vatreg);
        adapterVatReg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnervatreg.setAdapter(adapterVatReg);

        adapterwarehouse = new ArrayAdapter<String>(DelareRegActivity.this,R.layout.spinner_rows, warehouseList);
        adapterwarehouse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerwarehouselist.setAdapter(adapterwarehouse);

        adaptertranspoter = new ArrayAdapter<String>(DelareRegActivity.this,R.layout.spinner_rows, transpoterList);
        adaptertranspoter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertranspoter.setAdapter(adaptertranspoter);

        etndealername.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                etndealerusername.setText(s);
                // TODO Auto-generated method stub
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog1!=null)
                    progressDialog1.dismiss();
                if(progressDialog2!=null)
                    progressDialog2.dismiss();
                if(progressDialog3!=null)
                    progressDialog3.dismiss();
                startActivity(new Intent(DelareRegActivity.this,HomeActivity.class));
                finish();
            }
        });

        spinnertranspoter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selecttranspoter=transpoterList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerwarehouselist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectwarehouse=warehouseId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnervatreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                VatRegType=vatreg.get(position);
                if(VatRegType.equalsIgnoreCase("Registered")){
                    etndealervatno.setVisibility(View.VISIBLE);
                }else {
                    etndealervatno.setText("");
                    etndealervatno.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerdealerstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                RegState = stateList.get(position);
                RegStateid = stateId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btndealerreg=(Button)findViewById(R.id.btndealerreg);
        btndealerreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dealername = etndealername.getText().toString();
                dealeremail = etndealeremail.getText().toString();
                dealerephone = etndealerephone.getText().toString();
                dealeremobile = etndealeremobile.getText().toString();
                dealereadditionalmobile = etndealereadditionalmobile.getText().toString();
                dealerepassword = etndealerepassword.getText().toString();
                dealerusername = etndealerusername.getText().toString();
                dealereconpassword = etndealereconpassword.getText().toString();
                dealereaddress = etndealereaddress.getText().toString();

                dealereshippingaddress = etndealereshippingaddress.getText().toString();
                dealerlocation = etndealerlocation.getText().toString();
                dealercity = etndealercity.getText().toString();
                dealerzip = etndealerzip.getText().toString();
                dealervatno = etndealervatno.getText().toString();

                if(dealername.equalsIgnoreCase(""))
                {
                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer name");

                }else if(dealeremail.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer email");

                }
                else if(!isValidEmail(dealeremail)){

                    CreateDialog.showDialog(DelareRegActivity.this, "email id not valid");

                }/*else if(dealerephone.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer phone no");

                }*/else if(dealeremobile.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer mobile no");

                }
                else if(!isValidMobile(dealeremobile)){

                    CreateDialog.showDialog(DelareRegActivity.this, "mobile no not valid");

                }else if(dealerusername.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter a username");

                }else if(dealerepassword.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter a password");

                }else if(dealereconpassword.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter a confirm password");

                }
                else if(!dealereconpassword.equalsIgnoreCase(dealerepassword)){

                    CreateDialog.showDialog(DelareRegActivity.this, "confirm password not match");

                }else if(dealereaddress.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer address");

                }else if(dealereshippingaddress.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer shipping address");

                }
                else if(dealerlocation.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer location");

                }
                else if(dealercity.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer city name");

                }
                else if(dealerzip.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer zip code");

                }
                else if(!isValidPin(dealerzip)){

                    CreateDialog.showDialog(DelareRegActivity.this, "zip code not valid");

                }
                /*else if(selectwarehouse.equalsIgnoreCase("")||selectwarehouse.equalsIgnoreCase("-1")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Please Select warehouse");

                }*/
                else if(RegState.equalsIgnoreCase("")||RegState.equalsIgnoreCase("-Select State-")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Please Select state");

                }
                else if(VatRegType.equalsIgnoreCase("")||VatRegType.equalsIgnoreCase("-Select Vat Reg Type-")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Please Select Vat Reg Type");

                }else if(VatRegType.equalsIgnoreCase("Registered")){
                    if(dealervatno.equalsIgnoreCase("")){
                        CreateDialog.showDialog(DelareRegActivity.this, "Enter vat no");
                    }else {
                        if(vatdigitcount(etndealervatno.getText().toString())==11) {
                            doregistation();
                        }
                        else {
                            CreateDialog.showDialog(DelareRegActivity.this, "vat no should be 11 digits");
                        }
                    }
                }else {
                    doregistation();
                }
            }
        });

    }

    //================================== Get city data ====================================

    void doCityList()
    {
        progressDialog2=new ProgressDialog(DelareRegActivity.this);
        progressDialog2.setMessage("loading...");
        progressDialog2.show();
        progressDialog2.setCancelable(false);
        progressDialog2.setCanceledOnTouchOutside(false);
        String url;
       // System.out.println("StateID "+stateId);

        url = Webservice.STATE_LIST;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseCity(response);
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
                        Toast.makeText(DelareRegActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
               // params.put("country_id","101");
               // params.put("format","json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parseCity(String response)
    {
        try {
            stateList.clear();
            stateId.clear();

            JSONObject parentObj = new JSONObject(response);

            JSONArray jArray = parentObj.getJSONArray("data");

            stateList.add("-Select State-");
            stateId.add("-1");

            for(int i=1;i<jArray.length();i++){

                JSONObject innerObj = jArray.optJSONObject(i);
                //JSONObject records = innerObj.optJSONObject("records");

                stateList.add(innerObj.optString("State_Name"));
                stateId.add(innerObj.optString("ID"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        adapterState.notifyDataSetChanged();
       // doWarehouseList();
    }

    public  void doregistation(){

        progressDialog1=new ProgressDialog(DelareRegActivity.this);
        progressDialog1.setMessage("loading...");
        progressDialog1.show();
        progressDialog1.setCancelable(false);
        progressDialog1.setCanceledOnTouchOutside(false);
        String url;
        System.out.println("StateID "+stateId);

        url = Webservice.DEALER_REG;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseRegresponse(response);
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
                        Toast.makeText(DelareRegActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action_code","1");
                params.put("User_Name",dealerusername);
                params.put("Password",dealerepassword);
                params.put("dealer_name",dealername);
                params.put("dealer_address",dealereaddress);
                params.put("shipping_address",dealereshippingaddress);
                params.put("Dealer_Phone",dealerephone);
                params.put("Dealer_Mobile",dealeremobile);
                params.put("Dealer_Additional_Mobile",dealereadditionalmobile);
                params.put("Dealer_Email",dealeremail);
                params.put("Credit_Limit","0");

                params.put("Total_Outstanding","0");

                params.put("Vat_Regn_Status",VatRegType);

                params.put("Vat_No",dealervatno);
                params.put("Dealer_City",dealercity);

                params.put("Dealer_State",RegState);

                params.put("Dealer_Pin",dealerzip);

               /* params.put("Warehouse_Code","");*/

                params.put("Dealer_Location",dealerlocation);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
     //   AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public  void parseRegresponse(String response){

        try {

            JSONObject parentObj = new JSONObject(response);


                String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                String Delear_Code=parentObj.optString("Delear_Code");
                String admin_mobile=parentObj.optString("admin_mobile");
                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                sendsms(admin_mobile,dealeremobile,Delear_Code);
               /* startActivity(new Intent(DelareRegActivity.this,DelareLoginActivity.class));
                finish();*/
            }else if(status.equalsIgnoreCase("error")) {
                String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), status + " : " + message, Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    void doWarehouseList()
    {
        final ProgressDialog progressDialog=new ProgressDialog(DelareRegActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.WAREHOUSE_LIST;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseWarehouselist(response);
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
                        Toast.makeText(DelareRegActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parseWarehouselist(String response)
    {
        try {
            warehouseList.clear();
            warehouseId.clear();

            JSONObject parentObj = new JSONObject(response);

            JSONArray jArray = parentObj.getJSONArray("data");

            warehouseList.add("Select Warehouse");
            warehouseId.add("-1");

            for(int i=0;i<jArray.length();i++){

                JSONObject innerObj = jArray.optJSONObject(i);
                //JSONObject records = innerObj.optJSONObject("records");

                warehouseList.add(innerObj.optString("Warehouse_Name"));
                warehouseId.add(innerObj.optString("Warehouse_Code"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        adapterwarehouse.notifyDataSetChanged();
       // dotranspoterlist();
    }


    void dotranspoterlist()
    {
        final ProgressDialog progressDialog=new ProgressDialog(DelareRegActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.TRANSPOTER_LIST;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parsetranspoterlist(response);
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
                        Toast.makeText(DelareRegActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parsetranspoterlist(String response)
    {
        try {
            transpoterList.clear();
            transpoterId.clear();

            JSONObject parentObj = new JSONObject(response);

            JSONArray jArray = parentObj.getJSONArray("data");

            transpoterList.add("Select Warehouse");
            transpoterId.add("-1");

            for(int i=0;i<jArray.length();i++){

                JSONObject innerObj = jArray.optJSONObject(i);
                //JSONObject records = innerObj.optJSONObject("records");

                transpoterList.add(innerObj.optString("Warehouse_Name"));
                transpoterId.add(innerObj.optString("Warehouse_Code"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        adaptertranspoter.notifyDataSetChanged();
    }


    //sms send....

    public  void sendsms(final String admin_mobile, final String Dealer_Mobile,final String Delear_Code){

        progressDialog3=new ProgressDialog(DelareRegActivity.this);
        progressDialog3.setMessage("loading...");
        progressDialog3.show();
        progressDialog3.setCancelable(false);
        progressDialog3.setCanceledOnTouchOutside(false);
        String url;

        //url = "http://bluewavesmedia.co.in/api/mt/SendSMS?user=srfoods&password=srfoods@123&senderid=SRFOOD&channel=2&DCS=0&flashsms=0&number=91' + dealerMob + '&text=' + smsMsg + ' is placed and will be confirmed shortly&route=1'";
        String msgtext="Thank you, You registered Successfully and your account will be activated shortly";
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
                        //parsesensmsstatus(response);
                        sendsmstoadmin(admin_mobile,Delear_Code);
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
                        Toast.makeText(DelareRegActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData= Sharepreferences.getDealerinfo(DealerNeworder2.this);
                //String msg=message_content+"is placed and will be confirmed shortly";
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();

    }


    public  void sendsmstoadmin(String admin_mobile,String Delear_Code){

        progressDialog4=new ProgressDialog(DelareRegActivity.this);
        progressDialog4.setMessage("loading...");
        progressDialog4.show();
        progressDialog4.setCancelable(false);
        progressDialog4.setCanceledOnTouchOutside(false);
        String url;

        //url = "http://bluewavesmedia.co.in/api/mt/SendSMS?user=srfoods&password=srfoods@123&senderid=SRFOOD&channel=2&DCS=0&flashsms=0&number=91' + dealerMob + '&text=' + smsMsg + ' is placed and will be confirmed shortly&route=1'";
        String msgtext="New Dealer Registered successfully, please active this account,Dealer Code : "+Delear_Code;
        String encodedURL="";
        try {
            encodedURL = URLEncoder.encode(msgtext, "UTF-8");
        }catch (Exception e){
            System.out.println("Exception....@@@@@@@@@@@@@@2");
        }
        url="http://bluewavesmedia.co.in/api/mt/SendSMS?user=srfoods&password=srfoods@1232022&senderid=SRFOOD&channel=2&DCS=0&flashsms=0"+"&number=91"+admin_mobile+"&text="+encodedURL+"&route=1";
        // url="http://bluewavesmedia.co.in/api/mt/SendSMS";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(" @@@@@@@@@@@@@@@ "+response);
                        parsesensmsstatus(response);
                        if(progressDialog4!=null)
                            progressDialog4.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog4!=null)
                            progressDialog4.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(DelareRegActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData= Sharepreferences.getDealerinfo(DealerNeworder2.this);
                //String msg=message_content+"is placed and will be confirmed shortly";
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
        //  AppController.getInstance().getRequestQueue().getCache().remove(url);
        //  AppController.getInstance().getRequestQueue().getCache().clear();

    }

    public  void parsesensmsstatus(String response){

        startActivity(new Intent(DelareRegActivity.this,DelareLoginActivity.class));
        finish();

    }


    public int vatdigitcount(String dealervatno){
        int count = 0;
        for (int i = 0, len = dealervatno.length(); i < len; i++) {
            if (Character.isDigit(dealervatno.charAt(i))) {
                count++;
            }
        }
        System.out.println("@@@@@@@@@ count value : "+count);
        return count;

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DelareRegActivity.this);
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









    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(phone.length() < 10) {
            // if(phone.length() != 10) {
            check = false;
            //txtPhone.setError("Not Valid Number");
        } else {
            check = true;
        }
        return check;
    }

    private boolean isValidPin(String pin) {
        boolean check=false;
        if(pin.length() < 6) {
            // if(phone.length() != 10) {
            check = false;
            //txtPhone.setError("Not Valid Number");
        } else {
            check = true;
        }
        return check;
    }
}
