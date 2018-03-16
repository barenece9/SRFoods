package com.lnsel.srfoods.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
import com.lnsel.srfoods.filedownload.InputStreamVolleyRequest;
import com.lnsel.srfoods.util.Logout;
import com.lnsel.srfoods.util.Webservice;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
/*import com.lnsel.srfoods.filedownload.StringTokenizer;*/


public class DealerOutstandingOrderDetails extends Activity implements Response.Listener<byte[]>, Response.ErrorListener {

    Button btnBack,btnLogout;
    TextView headerTxt;
    String orderno="";
    Button btn_download;
    private WebView webView;

    Bitmap bmp=null;
    InputStreamVolleyRequest request;
    int count;
    ProgressDialog progressDialog1=null;
    ProgressDialog progressDialog;


    private static final int REQUEST_WRITE_STORAGE = 112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatched_order_details);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog1!=null)
                    progressDialog1.dismiss();
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Logout.logout(DealerOutstandingOrderDetails.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog1!=null)
                    progressDialog1.dismiss();
                if(progressDialog!=null)
                    progressDialog.dismiss();
                startActivity(new Intent(DealerOutstandingOrderDetails.this,DealerOutstanding.class));
                finish();
            }
        });
        headerTxt.setText("Outstanding Order Details");
        Bundle bundle = getIntent().getExtras();
        orderno = bundle.getString("orderno");
        setwidget();
    }
    public  void setwidget(){
        webView = (WebView) findViewById(R.id.webView1);
        btn_download=(Button)findViewById(R.id.btn_download);
        btn_download.setVisibility(View.GONE);
        String url=Webservice.DOWNLOAD_INVOICE+"?order_number="+orderno;
        startWebView(url);

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // downloadinvoice();

                boolean hasPermissionWrite = (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermissionWrite) {
                    ActivityCompat.requestPermissions(DealerOutstandingOrderDetails.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_STORAGE);
                }else {

                    progressDialog1=new ProgressDialog(DealerOutstandingOrderDetails.this);
                    progressDialog1.setMessage("loading...");
                    progressDialog1.show();
                    progressDialog1.setCancelable(false);
                    progressDialog1.setCanceledOnTouchOutside(false);
                    //Change your url below
                    String mUrl=Webservice.DISPATCH_ORDER_INVOICE_DOWNLOAD;
                    // String mUrl="http://api.androidhive.info/progressdialog/hive.jpg";
                    // String mUrl="http://61.16.131.206/projects/srfoods/API/Tax_Invoice.pdf";
                    request = new InputStreamVolleyRequest(Request.Method.GET, mUrl, DealerOutstandingOrderDetails.this, DealerOutstandingOrderDetails.this, null);
                    RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
                            new HurlStack());
                    mRequestQueue.add(request);
                }
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
                    progressDialog = new ProgressDialog(DealerOutstandingOrderDetails.this);
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

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DealerOutstandingOrderDetails.this);
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

    /*public void SimplePDFTable() throws Exception {


        File direct = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/AamirPDF");
        if (!direct.exists()) {
            if (direct.mkdir()) {
                Toast.makeText(DispatchedOrderDetails.this,
                        "Folder Is created in sd card", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        String test = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/AamirPDF";
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(test
                + "/mypdf.pdf"));

        document.open();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Image image = Image.getInstance(byteArray);


        image.scaleToFit(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        document.add(image);

        document.close();

    }*/

    public void downloadinvoice(){
        final ProgressDialog progressDialog=new ProgressDialog(DealerOutstandingOrderDetails.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url="http://61.16.131.206/projects/srfoods/API/pdf.php";

        //url = Webservice.DISPATCH_ORDER_INVOICE;

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
                        Toast.makeText(DealerOutstandingOrderDetails.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("order_number",orderno);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
        //AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    @Override
    public void onResponse(byte[] response) {
        if(progressDialog1!=null)
            progressDialog1.dismiss();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            if (response!=null) {
                try{
                    long lenghtOfFile = response.length;
                    //covert reponse to input stream
                    InputStream input = new ByteArrayInputStream(response);
                    File file21 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SRfoods");
                    if (!file21.exists()) {
                        file21.mkdirs();
                    }

                    /*File file21 = DispatchedOrderDetails.this.getDir("SRfoods", Context.MODE_PRIVATE); //Creating an internal dir;
                    if (!file21.exists())
                    {
                        file21.mkdirs();
                    }
*/


                    String string = orderno;
                    String[] parts = string.split("/");
                    String separetor = parts[4]; // 004
                    OutputStream file22 = new FileOutputStream(file21+"/"+"Tax_Invoice_"+separetor+".pdf");
                    map.put("resume_path", file22.toString());
                    BufferedOutputStream output = new BufferedOutputStream(file22);
                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        output.write(data, 0, count);
                    }

                    output.flush();

                    output.close();
                    input.close();
                    Toast.makeText(getApplicationContext(),"Pdf download and save on phone defult location",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Set Phone default location",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
            Toast.makeText(getApplicationContext(),"UNABLE TO DOWNLOAD FILE",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(progressDialog1!=null)
            progressDialog1.dismiss();
        Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE. ERROR:: "+error.getMessage());
        Toast.makeText(getApplicationContext(),"UNABLE TO DOWNLOAD FILE",Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(DealerOutstandingOrderDetails.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    //reload my activity with permission granted or use the features what required the permission
                    //finish();
                    //startActivity(getIntent());
                } else
                {
                    Toast.makeText(DealerOutstandingOrderDetails.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


}
