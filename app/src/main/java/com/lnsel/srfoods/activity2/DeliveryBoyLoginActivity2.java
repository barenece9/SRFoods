package com.lnsel.srfoods.activity2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.lnsel.srfoods.activity.DelivaryActivity;
import com.lnsel.srfoods.activity.LocationService;
import com.lnsel.srfoods.settergetterclass2.RememberData2;
import com.lnsel.srfoods.util2.CreateDialog2;
import com.lnsel.srfoods.util2.Sharepreferences2;
import com.lnsel.srfoods.util2.Webservice2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeliveryBoyLoginActivity2 extends Activity {

    EditText etndealerusername,etndealerpassword;
    Button btndealerlogin;
    String dealerusername="",dealerpassword="";

    Button btnBack,btnLogout;
    TextView headerTxt;
    CheckBox rememberme;
    TextView forgotpass;


    private static final int REQUEST_ACCESS_FINE_LOCATION = 111;
    private static final int REQUEST_ACCESS_FINE_LOCATION_LOG_IN= 112;

    boolean hasPermissionLocation;
    boolean enabled;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delare_login_pcj);
        regwidgets();
    }
    public void regwidgets(){



        hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(DeliveryBoyLoginActivity2.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        }

        //gps check for bellow ..6.0.................
        final LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            buildAlertMessageNoGps();
        }else {

        }

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
                startActivity(new Intent(DeliveryBoyLoginActivity2.this,LogintypeActivity2.class));
                finish();
            }
        });
        headerTxt.setText("Delivery Boy Login");
        forgotpass=(TextView)findViewById(R.id.forgotpass);
        forgotpass.setVisibility(View.GONE);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(DeliveryBoyLoginActivity.this,ForgotePassActivity.class));
               // finish();
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

                    Sharepreferences2.setRememberDeliveryboy(DeliveryBoyLoginActivity2.this, dealerusername, dealerpassword, 1);

                }else{

                    Sharepreferences2.setRememberDeliveryboy(DeliveryBoyLoginActivity2.this, "", "", 0);
                }
            }
        });
        btndealerlogin=(Button)findViewById(R.id.btndealerlogin);
        btndealerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealerusername=etndealerusername.getText().toString();
                dealerpassword=etndealerpassword.getText().toString();
                hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
                if(dealerusername.equalsIgnoreCase(""))
                {
                    CreateDialog2.showDialog(DeliveryBoyLoginActivity2.this, "Enter username");

                }else if(dealerpassword.equalsIgnoreCase("")){

                    CreateDialog2.showDialog(DeliveryBoyLoginActivity2.this, "Enter password");

                }else if(!hasPermissionLocation){
                    ActivityCompat.requestPermissions(DeliveryBoyLoginActivity2.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_ACCESS_FINE_LOCATION_LOG_IN);
                }
                else {
                    enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if(enabled) {
                        dologin();
                    }
                    else {
                        buildAlertMessageNoGps();
                    }
                  //  dologin();
                }
            }
        });

        //rebember pass and username fetch..
        RememberData2 rememberData = Sharepreferences2.getRememberDeliveryboy(this);

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

        progressDialog=new ProgressDialog(DeliveryBoyLoginActivity2.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice2.DELIVERY_BOY_LOGIN;

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
                        Toast.makeText(DeliveryBoyLoginActivity2.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
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
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public  void parselOGINresponse(String response){
        try {

            JSONObject parentObj = new JSONObject(response);


            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                String Employee_Code=parentObj.optString("Employee_Code");
                String Warehouse_Code=parentObj.optString("Warehouse_Code");
                Sharepreferences2.setDeliveryinfo(DeliveryBoyLoginActivity2.this,Employee_Code,Warehouse_Code);
                String Employee_Name=parentObj.optString("Employee_Name");

               // Toast.makeText(getApplicationContext(),status+" : Login as "+Employee_Name,Toast.LENGTH_LONG).show();

                RememberData2 rememberData = Sharepreferences2.getRememberDeliveryboy(this);
                if(rememberData.getRemember() == 1){
                    dealerusername=etndealerusername.getText().toString();
                    dealerpassword=etndealerpassword.getText().toString();
                    Sharepreferences2.setRememberDeliveryboy(DeliveryBoyLoginActivity2.this, dealerusername, dealerpassword, 1);
                }else{
                    Sharepreferences2.setRememberDeliveryboy(DeliveryBoyLoginActivity2.this, "", "", 0);
                }

                //startService(new Intent(DeliveryBoyLoginActivity.this, MyService.class));
                startService(new Intent(DeliveryBoyLoginActivity2.this, LocationService2.class));
              // startService(new Intent(DeliveryBoyLoginActivity.this, MyLocation.class));


               // stopService(new Intent(DeliveryBoyLoginActivity.this,LocationService2.class));

                startActivity(new Intent(DeliveryBoyLoginActivity2.this,DelivaryActivity2.class));
                finish();
            }else if(status.equalsIgnoreCase("error")) {
                String message = parentObj.optString("message");
                Toast.makeText(getApplicationContext(), "Error :"+message, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(DeliveryBoyLoginActivity2.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    //reload my activity with permission granted or use the features what required the permission
                    //finish();
                    // startActivity(getIntent());
                } else
                {
                    Toast.makeText(DeliveryBoyLoginActivity2.this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_ACCESS_FINE_LOCATION_LOG_IN: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(DeliveryBoyLoginActivity2.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    //reload my activity with permission granted or use the features what required the permission
                    // finish();
                    //start location service.....
                    // startService(new Intent(LoginActivity.this,ServiceLocation.class));


                    //  startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else
                {
                    Toast.makeText(DeliveryBoyLoginActivity2.this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

        }
    }



    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled,\n do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliveryBoyLoginActivity2.this);
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
