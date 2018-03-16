package com.lnsel.srfoods.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lnsel.srfoods.R;
import com.lnsel.srfoods.appconroller.AppController;
import com.lnsel.srfoods.filedownload.InputStreamVolleyRequest;
import com.lnsel.srfoods.filedownload.StringTokenizer;
import com.lnsel.srfoods.settergetterclass.payment;
import com.lnsel.srfoods.util.CreateDialog;
import com.lnsel.srfoods.util.Logout;
import com.lnsel.srfoods.util.Webservice;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
/*import com.lnsel.srfoods.filedownload.StringTokenizer;*/


public class DeliveryOrderDetails extends Activity  {

    Button btnBack,btnLogout;
    TextView headerTxt;
    String orderno="";
    String Dealer_Code="";
    Button pay,cancel;
    private WebView webView;

    Bitmap bmp=null;
    InputStreamVolleyRequest request;
    int count;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_order_details);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                stopService(new Intent(DeliveryOrderDetails.this,LocationService.class));
                Logout.logout(DeliveryOrderDetails.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(DeliveryOrderDetails.this,DeliveryBoyOrderListActivity.class));
                finish();
            }
        });
        headerTxt.setText("Order Details");
        //payment pay=new payment();
        orderno=payment.getOrderNumber();
        Dealer_Code=payment.getDealerCode();
        System.out.println(orderno+"@@@@@@@@@"+Dealer_Code);
        setwidget();
    }
    public  void setwidget(){
        webView = (WebView) findViewById(R.id.webView1);
        pay=(Button)findViewById(R.id.btn_pay);
        cancel=(Button)findViewById(R.id.btn_cancel);
        String url=Webservice.DOWNLOAD_INVOICE+"?order_number="+orderno;
        startWebView(url);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                    Intent intent = new Intent(DeliveryOrderDetails.this, DeliveryBoyPaymentReceive.class);
                    startActivity(intent);
                    finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(DeliveryOrderDetails.this,DeliveryBoyOrderListActivity.class));
                finish();
            }
        });
    }

    private void startWebView(String url) {
        webView.setWebViewClient(new WebViewClient() {
            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(DeliveryOrderDetails.this);
                    progressDialog.setMessage("Loading...");
                    //progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        //float scale = 100 * webView.getScale();

        webView.setInitialScale(75);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(url);


    }

    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        /*if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }*/

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliveryOrderDetails.this);
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

    public void converthtmltopdf(String response){
        try {
           // String k = "<html><body> This is my Project </body></html>";
           // String k = "<html><body>"+response+"</body></html>";
            String k=response;
            File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SRfoods");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            OutputStream file = new FileOutputStream(file2+"/demo.pdf");
            //OutputStream file = new FileOutputStream(new File("C:\\Test.pdf"));
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            InputStream is = new ByteArrayInputStream(k.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
            Toast.makeText(getApplicationContext(),"Pdf dowmload and save",Toast.LENGTH_SHORT).show();
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void downloadinvoice(){
        final ProgressDialog progressDialog=new ProgressDialog(DeliveryOrderDetails.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.DISPATCH_ORDER_INVOICE;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                       // parseOrderDetails(response);
                        converthtmltopdf(response);
                        System.out.println("@@@@@@@@@@@@ "+response);
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
                        Toast.makeText(DeliveryOrderDetails.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("order_number",orderno);
                //params.put("dealerCode","DC278");
                // params.put("orderNo","SRFB/RC/SO/16-17/09356");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();
    }


}
