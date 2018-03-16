package com.lnsel.srfoods.activity2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.lnsel.srfoods.activity2.DealerActivity2;
import com.lnsel.srfoods.settergetterclass2.RememberData2;
import com.lnsel.srfoods.util2.CreateDialog2;
import com.lnsel.srfoods.util2.Logout2;
import com.lnsel.srfoods.util2.Sharepreferences2;
import com.lnsel.srfoods.util2.Webservice2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DealerProfile2 extends Activity {

    Button btnBack;
    Button btnLogout;
    TextView headerTxt;

    Button btndealerreg,btndealerpassupdate;
    EditText etndealername,etndealeremail,etndealerephone,etndealeremobile,etndealereadditionalmobile,etndealerepassword,
            etndealereaddress,etndealereshippingaddress,etndealerlocation,etndealercity,etndealerzip,etndealervatno,
            etndealerusername,etndealereconpassword,etndealereoldpassword,Dealer_Category;
    Spinner spinnervatreg,spinnerdealerstate;

    String dealername="",dealeremail="",dealerephone="",dealeremobile="",dealereadditionalmobile="",dealerepassword="",
            dealereaddress="",dealereshippingaddress="",dealerlocation="",dealercity="",dealerzip="",dealervatno="",dealereconpassword,
            dealerusername="",dealereoldpassword="";

    String RegStateid="",RegState="";
    String VatRegType="";

    ArrayList<String> vatreg = new ArrayList<String>();
    ArrayList<String> vatregId = new ArrayList<String>();

    ArrayList<String> stateList = new ArrayList<String>();
    ArrayList<String> stateId = new ArrayList<String>();

    ArrayAdapter<String> adapterState;
    ArrayAdapter<String> adapterVatReg;

    ProgressDialog progressDialog1;
    ProgressDialog progressDialog2;
    ProgressDialog progressDialog3;
    ProgressDialog progressDialog4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dealer_profile_pcj);
        getwidgets();
        doCityList();
        //getprofiledetails();
    }

    void getwidgets(){

        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnBack=(Button)findViewById(R.id.btnBack);
        headerTxt.setText("Dealer Profile");

        Dealer_Category=(EditText)findViewById(R.id.Dealer_Category);
        etndealername=(EditText)findViewById(R.id.etndealername);
        etndealeremail=(EditText)findViewById(R.id.etndealeremail);
        etndealerephone=(EditText)findViewById(R.id.etndealerephone);
        etndealeremobile=(EditText)findViewById(R.id.etndealeremobile);
        etndealereadditionalmobile=(EditText)findViewById(R.id.etndealereadditionalmobile);
        etndealerepassword=(EditText)findViewById(R.id.etndealerepassword);
        etndealereoldpassword=(EditText)findViewById(R.id.etndealereoldpassword);
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


        adapterState = new ArrayAdapter<String>(DealerProfile2.this,R.layout.spinner_rows, stateList);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdealerstate.setAdapter(adapterState);

        vatreg.add("-Select Vat Reg Type-");
        vatreg.add("Registered");
        vatreg.add("Applied For");
        vatreg.add("Unregistered");

        adapterVatReg = new ArrayAdapter<String>(DealerProfile2.this,R.layout.spinner_rows, vatreg);
        adapterVatReg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnervatreg.setAdapter(adapterVatReg);

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
                if(progressDialog4!=null)
                    progressDialog4.dismiss();
                Logout2.logout(DealerProfile2.this);
                finish();
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
                if(progressDialog4!=null)
                    progressDialog4.dismiss();
                startActivity(new Intent(DealerProfile2.this,DealerActivity2.class));
                finish();
            }
        });

        spinnervatreg.setEnabled(false);
        spinnervatreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                VatRegType=vatreg.get(position);
                if(VatRegType.equalsIgnoreCase("Registered")){
                    etndealervatno.setVisibility(View.VISIBLE);
                }else {
                    etndealervatno.setText("");
                    etndealervatno.setVisibility(View.GONE);
                    //dealervatno="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerdealerstate.setEnabled(false);
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
        btndealerpassupdate=(Button)findViewById(R.id.btndealerpassupdate);
        btndealerpassupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealerepassword = etndealerepassword.getText().toString();
                dealereoldpassword = etndealereoldpassword.getText().toString();
                dealereconpassword = etndealereconpassword.getText().toString();
                if(dealereoldpassword.equalsIgnoreCase(""))
                {
                    CreateDialog2.showDialog(DealerProfile2.this, "Enter old password");

                }else if(dealerepassword.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter new password");

                }else if(dealereconpassword.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter confirm password");

                }else if(!dealereconpassword.equalsIgnoreCase(dealerepassword)){

                    CreateDialog2.showDialog(DealerProfile2.this, "Confirm password not match");

                }else {
                    dpdatepassword();
                }
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
                    CreateDialog2.showDialog(DealerProfile2.this, "Enter Dealer name");

                }else if(dealeremail.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter Dealer mail");

                } else if(!isValidEmail(dealeremail)){

                    CreateDialog2.showDialog(DealerProfile2.this, "email id not valid");

                }/*else if(dealerephone.equalsIgnoreCase("")){

                    CreateDialog.showDialog(DelareRegActivity.this, "Enter Dealer phone no");

                }*/else if(dealeremobile.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter Dealer mobile no");

                } else if(dealereaddress.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter Dealer address");

                }else if(dealereshippingaddress.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter Dealer shipping address");

                }
                else if(dealerlocation.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter Dealer location");

                }
                else if(dealercity.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter Dealer city name");

                }
                else if(dealerzip.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Enter Dealer zip code");

                }
                else if(RegState.equalsIgnoreCase("-Select State-")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Please Select state");

                }
                else if(VatRegType.equalsIgnoreCase("-Select Vat Reg Type-")){

                    CreateDialog2.showDialog(DealerProfile2.this, "Please Select Vat Reg Type");

                }else if(VatRegType.equalsIgnoreCase("Registered")){
                    if(dealervatno.equalsIgnoreCase("")){
                        CreateDialog2.showDialog(DealerProfile2.this, "Enter vat no");
                    }else {
                        doupdateprofile();
                    }
                }else {
                    doupdateprofile();
                }
            }
        });

    }
    void doCityList()
    {
        progressDialog1=new ProgressDialog(DealerProfile2.this);
        progressDialog1.setMessage("loading...");
        progressDialog1.show();
        progressDialog1.setCancelable(false);
        progressDialog1.setCanceledOnTouchOutside(false);
        String url;
        //System.out.println("StateID "+stateId);

        url = Webservice2.STATE_LIST;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseCity(response);
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
                        Toast.makeText(DealerProfile2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("country_id","101");
                params.put("format","json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();

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
        getprofiledetails();
    }

    public  void getprofiledetails(){
        progressDialog2=new ProgressDialog(DealerProfile2.this);
        progressDialog2.setMessage("loading...");
        progressDialog2.show();
        progressDialog2.setCancelable(false);
        progressDialog2.setCanceledOnTouchOutside(false);
        String url;
        url = Webservice2.DEALER_PROFILE_DETAILS;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseprofiledetails(response);
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
                        Toast.makeText(DealerProfile2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData=new RememberData();
                RememberData2 rememberData=Sharepreferences2.getDealerinfo(DealerProfile2.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Dealer_Code",rememberData.getDealerCode());
                //params.put("Dealer_Code","DC281");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parseprofiledetails(String response){
        try {

            JSONObject parentObj = new JSONObject(response);


            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                //String Delear_Code=parentObj.optString("Delear_Code");
                /*etndealername,etndealeremail,etndealerephone,etndealeremobile,etndealereadditionalmobile,etndealerepassword,
            etndealereaddress,etndealereshippingaddress,etndealerlocation,etndealercity,etndealerzip,etndealervatno*/
                JSONObject inerobj=parentObj.getJSONObject("data");
                etndealername.setText(inerobj.optString("Dealer_Name"));

                Dealer_Category.setText(inerobj.optString("Dealer_Category"));

                etndealeremail.setText(inerobj.optString("Dealer_Email"));
                etndealerephone.setText(inerobj.optString("Dealer_Phone"));
                etndealeremobile.setText(inerobj.optString("Dealer_Mobile"));
                etndealereadditionalmobile.setText(inerobj.optString("Dealer_Additional_Mobile"));
                etndealereaddress.setText(inerobj.optString("Dealer_Address"));
                etndealereshippingaddress.setText(inerobj.optString("Shipping_Address"));
                etndealerlocation.setText(inerobj.optString("Dealer_Location"));
                etndealerzip.setText(inerobj.optString("Dealer_Pin"));
                etndealervatno.setText(inerobj.optString("Vat_No"));
                etndealercity.setText(inerobj.optString("Dealer_City"));

                String Dealer_State=inerobj.optString("Dealer_State");
                System.out.println(stateList.size()+" ####  "+Dealer_State);
                for(int i=0;i<stateList.size();i++){
                   // System.out.println("@@@@@  "+stateList.get(i));
                    if(stateList.get(i).equalsIgnoreCase(inerobj.optString("Dealer_State"))){
                        spinnerdealerstate.setSelection(i);
                    }
                }

               // spinnerdealerstate.setSelection(Integer.valueOf(inerobj.optString("Dealer_State")));
                if(inerobj.optString("Vat_Regn_Status").equalsIgnoreCase("Registered")){
                    spinnervatreg.setSelection(1);
                }else if(inerobj.optString("Vat_Regn_Status").equalsIgnoreCase("Applied For")){
                    spinnervatreg.setSelection(2);
                }else if(inerobj.optString("Vat_Regn_Status").equalsIgnoreCase("Unregistered")){
                    spinnervatreg.setSelection(3);
                }else {
                    spinnervatreg.setSelection(0);
                }




               // Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
            }else if(status.equalsIgnoreCase("error")) {
                String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), status + " : " + message, Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public  void doupdateprofile(){

        progressDialog3=new ProgressDialog(DealerProfile2.this);
        progressDialog3.setMessage("loading...");
        progressDialog3.show();
        progressDialog3.setCancelable(false);
        progressDialog3.setCanceledOnTouchOutside(false);
        String url;
        System.out.println("StateID "+stateId);

        url = Webservice2.DEALER_REG;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseUpdateresponse(response);
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
                        Toast.makeText(DealerProfile2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData2 rememberData=Sharepreferences2.getDealerinfo(DealerProfile2.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("action_code","2");
                params.put("Dealer_Code",rememberData.getDealerCode());
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
                params.put("Dealer_Location",dealerlocation);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public  void parseUpdateresponse(String response){

        try {

            JSONObject parentObj = new JSONObject(response);


            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                //String Delear_Code=parentObj.optString("Delear_Code");
                Toast.makeText(getApplicationContext(),status+" ",Toast.LENGTH_LONG).show();
            }else if(status.equalsIgnoreCase("error")) {
                String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), status + " : " + message, Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void dpdatepassword(){

        progressDialog4=new ProgressDialog(DealerProfile2.this);
        progressDialog4.setMessage("loading...");
        progressDialog4.show();
        progressDialog4.setCancelable(false);
        progressDialog4.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice2.DEALER_PASS_UPDATE;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseUpdatepassresponse(response);
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
                        Toast.makeText(DealerProfile2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData2 rememberData=Sharepreferences2.getDealerinfo(DealerProfile2.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Dealer_Code",rememberData.getDealerCode());
                params.put("OldPassword",dealereoldpassword);
                params.put("NewPassword",dealerepassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public  void parseUpdatepassresponse(String response){

        try {

            JSONObject parentObj = new JSONObject(response);


            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(),status+" : "+message,Toast.LENGTH_LONG).show();
            }else if(status.equalsIgnoreCase("error")) {
                String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), status + " : " + message, Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DealerProfile2.this);
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
                if(progressDialog4!=null)
                    progressDialog4.dismiss();
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



}
