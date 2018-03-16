package com.lnsel.srfoods.activity;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lnsel.srfoods.appconroller.AppController;
import com.lnsel.srfoods.settergetterclass.RememberData;
import com.lnsel.srfoods.util.GPSTracker;
import com.lnsel.srfoods.util.Sharepreferences;
import com.lnsel.srfoods.util.Webservice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by db on 2/24/2017.
 */
public class MyLocation extends Service{

    double latitude=0;
    double longitute=0;
    //GPSTracker gpsTracker;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Log.d("Service","service is Started");
        //gpsTracker = new GPSTracker(this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 20 seconds
                Toast.makeText(getApplicationContext(), "Service is running", Toast.LENGTH_SHORT).show();
                Log.d("Service: ","running");

                GPSTracker gpsTracker = new GPSTracker(MyLocation.this);
                String stringLatitude = String.valueOf(gpsTracker.latitude);
                String stringLongitude = String.valueOf(gpsTracker.longitude);

                Toast.makeText(getApplicationContext(),stringLatitude+"  "+stringLongitude,Toast.LENGTH_SHORT).show();

                latitude=gpsTracker.latitude;
                longitute=gpsTracker.longitude;

                sendtoserver(latitude,longitute);

                handler.postDelayed(this, 35000);
            }
        }, 25000);  //the time is in miliseconds

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("Service","service is Destroy...");
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    public void sendtoserver(final Double lat, final Double lng){

        final ProgressDialog progressDialog=new ProgressDialog(MyLocation.this);
        progressDialog.setMessage("loading...");
        // progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.POST_LAT_LONG;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // serverresponse(response);
                        System.out.println("###############"+response);
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
                        // Toast.makeText(MyService.this, "Have  Network Error\nPlease check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData rememberData= Sharepreferences.getDeleveryinfo(MyLocation.this);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Employee_Code",rememberData.getDealerCode());
                params.put("latitude",String.valueOf(lat));
                params.put("longitude",String.valueOf(lng));
                System.out.println(rememberData.getDealerCode()+"@@@@@@"+String.valueOf(lat)+"$$$$$$$$$"+String.valueOf(lng));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();

    }
}
