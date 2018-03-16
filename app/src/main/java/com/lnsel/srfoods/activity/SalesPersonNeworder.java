package com.lnsel.srfoods.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.lnsel.srfoods.adapter.CustomAdapter;
import com.lnsel.srfoods.adapter.SalesPersonCustomAdapter;
import com.lnsel.srfoods.appconroller.AppController;
import com.lnsel.srfoods.settergetterclass.RememberData;
import com.lnsel.srfoods.util.CreateDialog;
import com.lnsel.srfoods.util.Sharepreferences;
import com.lnsel.srfoods.util.Webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SalesPersonNeworder extends Activity  {

    EditText etn_dealer_remark;
    Button btnBack,btnLogout;
    TextView headerTxt;
    TextView tvorderno;
    Spinner spinnerwarehouselist;
    AutoCompleteTextView spinnerproductlist2,spinnerdealerlist;
    Button btnadd,btnsummary,btndone,btnclearall;
    ListView listView;
    EditText editquantity;
    TextView total_quantity,total_weiget,total_without_vat_before_discount,total_without_vat_after_discount,total_vat,discount_percentage,
            total_discount,invoice_total;

    ArrayList<String> warehouseList = new ArrayList<String>();
    ArrayList<String> warehouseId = new ArrayList<String>();

    ArrayList<String> productList = new ArrayList<String>();
    ArrayList<String> productId = new ArrayList<String>();

    ArrayList<String> dealerList = new ArrayList<String>();
    ArrayList<String> dealerId = new ArrayList<String>();


    ArrayAdapter<String> adapterwarehouse;


    ArrayAdapter<String> adapterDealer;
    ArrayAdapter<String> arrayAdapter;

    String quantity="",selectwarehouse="",selectproduct="",orderno="",selectdealer="";

    ArrayList<HashMap<String,String>> listarray;

    String Total_Quantity="",Total_Weight="",Total_With_Out_VAT_Before_Dsicount="",Total_With_Out_VAT_After_Dsicount="",
            Total_VAT="",Discount_Percentage="",Total_Dealer_Discount="",invoicetotal="";

    String message_content="",Dealer_Mobile="";

    int minteger = 0;

    String remark="";




    //private ProgressDialog progressDialog;
    ProgressDialog progressDialog1;
    ProgressDialog progressDialog2;
    ProgressDialog progressDialog3;
    ProgressDialog progressDialog4;
    ProgressDialog progressDialog5;
    ProgressDialog progressDialog6;

    ProgressDialog progressDialog7;
    ProgressDialog progressDialog8;
    ProgressDialog progressDialog9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sales_person_neworder);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        headerTxt.setText("New Order");
        setwidget();
        doDealerList();
    }



    public  void setwidget(){

        tvorderno=(TextView)findViewById(R.id.tvorderno);
        spinnerwarehouselist=(Spinner)findViewById(R.id.spinnerwarehouselist);



        ///for product list =============================================================
        spinnerproductlist2=(AutoCompleteTextView) findViewById(R.id.spinnerproductlist);
        arrayAdapter = new ArrayAdapter<String>(
                SalesPersonNeworder.this, android.R.layout.simple_dropdown_item_1line,
                productList);

        spinnerproductlist2.setAdapter(arrayAdapter);
        spinnerproductlist2.setThreshold(1);
        spinnerproductlist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                spinnerproductlist2.showDropDown();
            }
        });



        ///for dealer list =============================================================
        spinnerdealerlist=(AutoCompleteTextView) findViewById(R.id.spinnerdealerlist);
        adapterDealer= new ArrayAdapter<String>(
                SalesPersonNeworder.this, android.R.layout.simple_dropdown_item_1line,
                dealerList);

        spinnerdealerlist.setAdapter(adapterDealer);
        spinnerdealerlist.setThreshold(1);


        spinnerdealerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {


                Toast.makeText(getApplicationContext(),parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();

                selectdealer=dealerId.get(pos);
                System.out.println(dealerId.get(pos)+"  SELECTED DEALER "+dealerList.get(pos));

                /*String dealer=spinnerdealerlist.getText().toString();
                for(int i=1;i<dealerList.size();i++){

                    if(dealerList.get(i).equalsIgnoreCase(dealer)){
                        //doProductList(dealerId.get(i));
                        selectdealer=dealerId.get(pos);
                        System.out.println(dealerId.get(i)+"  PICKED DEALER "+dealerList.get(i));
                    }
                }*/

            }
        });



        editquantity=(EditText) findViewById(R.id.editquantity);
        listView=(ListView)findViewById(R.id.listView);

        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalesPersonNeworder.this,SalesPersonActivity.class));
                finish();
            }
        });

        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setVisibility(View.GONE);

        /*btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Logout.logout(DealerNeworder2.this);
              //  finish();
                btnLogout.setVisibility(View.GONE);
            }
        });*/


        listarray=new ArrayList<HashMap<String,String>>();

        btndone=(Button)findViewById(R.id.btndone);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remarkesdilog();
            }
        });


        btnsummary=(Button)findViewById(R.id.btnview);
        btnsummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callviewdialog();

            }
        });

        btnclearall=(Button)findViewById(R.id.btnclearall);
        btnclearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backclearalltemporder();
            }
        });

        btnadd=(Button)findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity=editquantity.getText().toString();

                selectproduct="";
                String productname=spinnerproductlist2.getText().toString();
                for(int i=0;i<productList.size();i++){
                    if( productList.get(i).equalsIgnoreCase(productname)){
                        selectproduct=productId.get(i);
                        System.out.println(productname+" !!!!!!!!!!!!!!!! "+selectproduct);
                    }
                }


                selectdealer="";
                String dealername=spinnerdealerlist.getText().toString();
                for(int i=0;i<dealerList.size();i++){
                    if( dealerList.get(i).equalsIgnoreCase(dealername)){
                        selectdealer=dealerId.get(i);
                        System.out.println(selectdealer+" !!!!!!!!!!!!!!!! "+selectdealer);
                    }
                }

                if(quantity.equalsIgnoreCase(""))
                {
                    CreateDialog.showDialog(SalesPersonNeworder.this, "Enter quantity");

                }else if(selectdealer.equalsIgnoreCase("")||selectdealer.equalsIgnoreCase("-1")){

                    CreateDialog.showDialog(SalesPersonNeworder.this, "Select dealer");

                }else if(selectproduct.equalsIgnoreCase("")||selectproduct.equalsIgnoreCase("-1")){

                    CreateDialog.showDialog(SalesPersonNeworder.this, "Select a product");

                }else {
                    if(!orderno.equalsIgnoreCase("")){
                        addproduct();
                    }else {
                        getorderno();
                    }
                }
            }
        });

        adapterwarehouse = new ArrayAdapter<String>(SalesPersonNeworder.this,R.layout.spinner_rows, warehouseList);
        adapterwarehouse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerwarehouselist.setAdapter(adapterwarehouse);


        spinnerwarehouselist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectwarehouse=warehouseId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public  void getorderno(){

        progressDialog1=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog1.setMessage("loading...");
        //  progressDialog.show();
        progressDialog1.setCancelable(false);
        progressDialog1.setCanceledOnTouchOutside(false);
        String url;
        url = Webservice.SALES_PERSON_PRODUCT_NO;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseOderNo(response);
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
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData rememberData= Sharepreferences.getSalesPersoninfo(SalesPersonNeworder.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("DealerCode",selectdealer);  // dealer code
                params.put("Employee_Code",rememberData.getDealerCode()); //employee code
                params.put("Warehouse_Code",rememberData.getDealerWarehouse());


                Log.d("DealerCode",selectdealer);
                Log.d("Employee_Code",rememberData.getDealerCode());
                Log.d("Warehouse_Code",rememberData.getDealerWarehouse());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parseOderNo(String response)
    {
        try {

            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                tvorderno.setText(parentObj.optString("Order_Number"));
                orderno=parentObj.optString("Order_Number");
                addproduct();
            }else if(status.equalsIgnoreCase("exist")){
                tvorderno.setText(parentObj.optString("Order_Number"));
                orderno=parentObj.optString("Order_Number");
                //getOrderDetails();
                addproduct();
            }
            else{
                //Toast.makeText(getApplicationContext(),"Sorry No Oder No Found",Toast.LENGTH_SHORT).show();
            }



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //doWarehouseList();
       // doProductList();
    }



    void doProductList()
    {
        progressDialog3=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog3.setMessage("loading...");
        progressDialog3.show();
        progressDialog3.setCancelable(false);
        progressDialog3.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.PRODUCT_LIST;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseProductlist(response);
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
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
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

    public void parseProductlist(String response)
    {
        try {
            productList.clear();
            productId.clear();

            JSONObject parentObj = new JSONObject(response);

            JSONArray jArray = parentObj.getJSONArray("data");

            productList.add("Select Product");
            productId.add("-1");

            for(int i=0;i<jArray.length();i++){

                JSONObject innerObj = jArray.optJSONObject(i);
                //JSONObject records = innerObj.optJSONObject("records");

                productList.add(innerObj.optString("Product_Name"));
                productId.add(innerObj.optString("Product_Code"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //adapterproduct.notifyDataSetChanged();
        arrayAdapter.notifyDataSetChanged();
       // getorderno();
    }

    public  void addproduct(){
        progressDialog4=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog4.setMessage("loading...");
         // progressDialog.show();
        progressDialog4.setCancelable(false);
        progressDialog4.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.MAKE_ORDER;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseGiveOrder(response);
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
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData rememberData= Sharepreferences.getSalesPersoninfo(SalesPersonNeworder.this);
                System.out.println("########### "+rememberData.getDealerWarehouse());
                Map<String, String>  params = new HashMap<String, String>();
                params.put("dealerCode",selectdealer);
                params.put("whCode",rememberData.getDealerWarehouse());
                params.put("productCode",selectproduct);
                params.put("orderNo",orderno);
                params.put("orderQty",quantity);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parseGiveOrder(String response)
    {
        try {

            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                Toast.makeText(getApplicationContext(),"Oder Placed Successfully",Toast.LENGTH_SHORT).show();
                tvorderno.setText(orderno);
               // spinnerproductlist.setSelection(0);
                spinnerproductlist2.setText("");
                editquantity.setText("");
                //addstatus=true;

            }else if(status.equalsIgnoreCase("error")){
                String message=parentObj.optString("message");
               // spinnerproductlist.setSelection(0);
                spinnerproductlist2.setText("");
                editquantity.setText("");
                CreateDialog.showDialog(SalesPersonNeworder.this,message);
                //Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getOrderDetails();
    }

    public  void getOrderDetails(){
        progressDialog5=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog5.setMessage("loading...");
        progressDialog5.show();
        progressDialog5.setCancelable(false);
        progressDialog5.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.ORDER_DETAILS_BY_ORDER_NO;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseOrderDetails(response);
                        if(progressDialog5!=null)
                            progressDialog5.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog5!=null)
                            progressDialog5.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData rememberData= Sharepreferences.getDealerinfo(SalesPersonNeworder.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("dealerCode",selectdealer);
                params.put("orderNo",orderno);
                System.out.println("@@@@@@@@@@@@@@@@@@  "+orderno);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    public  void  parseOrderDetails(String response){
        try {

            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            listarray.clear();
            {
                        Total_Quantity="";
                        Total_Weight = "";
                        Total_With_Out_VAT_Before_Dsicount = "";
                        Total_With_Out_VAT_After_Dsicount = "";
                        Total_VAT = "";Discount_Percentage = "";
                        Total_Dealer_Discount = "";
                        invoicetotal = "";
            }

            if(status.equalsIgnoreCase("success")){
               JSONArray jarray=parentObj.getJSONArray("item_list");

                for(int i=0;i<jarray.length();i++){

                    HashMap<String,String> item=new HashMap<>();

                    JSONObject innerObj = jarray.optJSONObject(i);
                    //JSONObject records = innerObj.optJSONObject("records");
                    item.put("SL_NO",innerObj.optString("SL_NO"));
                    item.put("Product_Name",innerObj.optString("Product_Name"));
                    item.put("MRP",innerObj.optString("MRP"));
                    item.put("Product_Qty",innerObj.optString("Product_Qty"));
                    item.put("Product_Images",innerObj.optString("Product_Images"));
                    listarray.add(item);
                }
                JSONObject result_all_total=parentObj.getJSONObject("result_all_total");
                Total_Quantity=result_all_total.optString("Total_Quantity");
                //total_quantity.setText(Total_Quantity);
                Total_Weight=result_all_total.optString("Total_Weight");
                //total_weiget.setText(Total_Weight);


                //Total_With_Out_VAT_Before_Dsicount=result_all_total.optString("Total_With_Out_VAT_Before_Dsicount");
                Total_With_Out_VAT_Before_Dsicount=result_all_total.optString("VAT_5P");


                //total_without_vat_before_discount.setText(Total_With_Out_VAT_Before_Dsicount);
                //Total_With_Out_VAT_After_Dsicount=result_all_total.optString("Total_With_Out_VAT_After_Dsicount");
                Total_With_Out_VAT_After_Dsicount=result_all_total.optString("VAT_14_5_P");



                //total_without_vat_after_discount.setText(Total_With_Out_VAT_After_Dsicount);
                Total_VAT=result_all_total.optString("Total_VAT");
                //total_vat.setText(Total_VAT);

                JSONObject discount_applied_for=parentObj.getJSONObject("discount_applied_for");
                Discount_Percentage=discount_applied_for.optString("Discount_Percentage");
                //discount_percentage.setText(Discount_Percentage);
                Total_Dealer_Discount=discount_applied_for.optString("Total_Dealer_Discount");
                //total_discount.setText(Total_Dealer_Discount);
                invoicetotal=parentObj.optString("invoice_total");

                //invoice_total.setText(invoicetotal);
            }else if(status.equalsIgnoreCase("error")){
                String message=parentObj.optString("message");
                Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       // callviewdialog();
        listView.setAdapter(new SalesPersonCustomAdapter(SalesPersonNeworder.this, listarray));
        //notifyAll();
    }

    public void callviewdialog(){
        // Create custom dialog object
        final Dialog dialog = new Dialog(SalesPersonNeworder.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_summary);
        dialog.setCancelable(true);
        // Set dialog title
        //dialog.setTitle("Order Summary");


        // set values for custom dialog components - text, image and button
       // TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        //text.setText("Custom dialog Android example.");
        total_quantity=(TextView)dialog.findViewById(R.id.total_quantity);
        total_weiget=(TextView)dialog.findViewById(R.id.total_weiget);
        total_without_vat_before_discount=(TextView)dialog.findViewById(R.id.total_without_vat_before_discount);
        total_without_vat_after_discount=(TextView)dialog.findViewById(R.id.total_without_vat_after_discount);
        total_vat=(TextView)dialog.findViewById(R.id.total_vat);
        discount_percentage=(TextView)dialog.findViewById(R.id.discount_percentage);
        total_discount=(TextView)dialog.findViewById(R.id.total_discount);
        invoice_total=(TextView)dialog.findViewById(R.id.invoice_total);

        total_quantity.setText(Total_Quantity);
        total_weiget.setText(Total_Weight);
        total_without_vat_before_discount.setText(Total_With_Out_VAT_Before_Dsicount);
        total_without_vat_after_discount.setText(Total_With_Out_VAT_After_Dsicount);
        total_vat.setText(Total_VAT);
        discount_percentage.setText(Discount_Percentage);
        total_discount.setText(Total_Dealer_Discount);
        invoice_total.setText(invoicetotal);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    public  void yourDesiredMethod(String slno){
       // Toast.makeText(getApplicationContext(), "You Clicked "+slno, Toast.LENGTH_LONG).show();
        dodeleteorder(slno);
    }

    public void dodeleteorder(final String slno){
        progressDialog6=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog6.setMessage("loading...");
        //  progressDialog.show();
        progressDialog6.setCancelable(false);
        progressDialog6.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.DELETE_ORDER;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(" @@@@@@@@@@@@@@@ "+response);
                        parsedeleteOrderstatus(response);
                        if(progressDialog6!=null)
                            progressDialog6.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog6!=null)
                            progressDialog6.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("sln",slno);
                params.put("orderNo",orderno);
                System.out.println(slno+" ##$%^^^&^&&&*&  "+orderno);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }
    public  void  parsedeleteOrderstatus(String response){
        try {

            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                Toast.makeText(getApplicationContext(),"Oder Deleted Successfully",Toast.LENGTH_SHORT).show();

            }else if(status.equalsIgnoreCase("error")){
                //String message=parentObj.optString("message");
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        getOrderDetails();
    }

    //final submission......
    public  void finalordersubmit(){
        progressDialog7=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog7.setMessage("loading...");
        //  progressDialog.show();
        progressDialog7.setCancelable(false);
        progressDialog7.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.CONFIRM_ORDER ;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(" @@@@@@@@@@@@@@@ "+response);
                        parsefinalorderstatus(response);
                        if(progressDialog7!=null)
                            progressDialog7.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog7!=null)
                            progressDialog7.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData rememberData= Sharepreferences.getSalesPersoninfo(SalesPersonNeworder.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Total_Amt",invoicetotal);
                params.put("DealerCode",selectdealer);
                params.put("OrderNo",orderno);
                params.put("dealer_remarks",remark);
                System.out.println(invoicetotal+" ##$%^^^&^&&&*&  "+orderno+"  %%%%%%%%  "+rememberData.getDealerCode());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
      //  AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();

    }

    public  void parsefinalorderstatus(String response){
        try {

            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                message_content=parentObj.optString("message_content");
                Dealer_Mobile=parentObj.optString("Dealer_Mobile");
                Toast.makeText(getApplicationContext(),status+" : "+message_content,Toast.LENGTH_SHORT).show();
                sendsms();
               /* startActivity(new Intent(SalesPersonNeworder.this,SalesPersonActivity.class));
                finish();*/

            }else if(status.equalsIgnoreCase("error")){
                String message=parentObj.optString("message");
                Toast.makeText(getApplicationContext(),status+" : "+message,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SalesPersonNeworder.this);
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
               // clearalltemporder();
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


    public void clearalltemporder(){
       progressDialog8=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog8.setMessage("loading...");
          progressDialog8.show();
        progressDialog8.setCancelable(false);
        progressDialog8.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.CLEAR_ALL_ORDER ;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(" @@@@@@@@@@@@@@@ "+response);
                        parseclearorderstatus(response);
                        if(progressDialog8!=null)
                            progressDialog8.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog8!=null)
                            progressDialog8.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("OrderNo",orderno);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
       // AppController.getInstance().getRequestQueue().getCache().remove(url);
       // AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public  void parseclearorderstatus(String response){
        try {

            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
               // Toast.makeText(getApplicationContext(),"Order Cancel",Toast.LENGTH_SHORT).show();
                //getOrderDetails();
                finish();
            }else if(status.equalsIgnoreCase("error")){
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Not cancel",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //sms send....

    public  void sendsms(){

        progressDialog9=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog9.setMessage("loading...");
        //  progressDialog.show();
        progressDialog9.setCancelable(false);
        progressDialog9.setCanceledOnTouchOutside(false);
        String url;

        //url = "http://bluewavesmedia.co.in/api/mt/SendSMS?user=srfoods&password=srfoods@123&senderid=SRFOOD&channel=2&DCS=0&flashsms=0&number=91' + dealerMob + '&text=' + smsMsg + ' is placed and will be confirmed shortly&route=1'";
       String msgtext=message_content+" is placed and will be confirmed shortly";
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
                        if(progressDialog9!=null)
                            progressDialog9.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog9!=null)
                            progressDialog9.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                //RememberData rememberData= Sharepreferences.getDealerinfo(DealerNeworder2.this);
                String msg=message_content+"is placed and will be confirmed shortly";
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
        startActivity(new Intent(SalesPersonNeworder.this,SalesPersonActivity.class));
        finish();
    }




    public void backclearalltemporder(){
        final ProgressDialog progressDialog=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.CLEAR_ALL_ORDER ;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(" @@@@@@@@@@@@@@@ "+response);
                        backparseclearorderstatus(response);
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
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("OrderNo",orderno);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
     //   AppController.getInstance().getRequestQueue().getCache().remove(url);
      //  AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public  void backparseclearorderstatus(String response){
        try {
            JSONObject parentObj = new JSONObject(response);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("success")){
                //Toast.makeText(getApplicationContext(),"Order Cancel",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SalesPersonNeworder.this,SalesPersonActivity.class));
                finish();
            }else if(status.equalsIgnoreCase("error")){
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Not cancel",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void increaseInteger(View view) {
        minteger = minteger + 1;
        quantity=String.valueOf(minteger);
        editquantity.setText(quantity);

    }public void decreaseInteger(View view) {
        minteger = minteger - 1;
        quantity=String.valueOf(minteger);
        editquantity.setText(quantity);
    }


    public void remarkesdilog(){

        final Dialog dialog = new Dialog(SalesPersonNeworder.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.remarkes_dialog);
        dialog.setCanceledOnTouchOutside(true);

        etn_dealer_remark=(EditText)dialog.findViewById(R.id.etn_remark);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_cancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remark=etn_dealer_remark.getText().toString();
                finalordersubmit();
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    void doDealerList()
    {
        progressDialog2=new ProgressDialog(SalesPersonNeworder.this);
        progressDialog2.setMessage("loading...");
        progressDialog2.show();
        progressDialog2.setCancelable(false);
        progressDialog2.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.DEALER_LIST;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseDealerlist(response);
                        if(progressDialog2!=null)
                            progressDialog2.dismiss();
                        Log.d("Dealer List Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog2!=null)
                            progressDialog2.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(SalesPersonNeworder.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                RememberData rememberData= Sharepreferences.getSalesPersoninfo(SalesPersonNeworder.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Warehouse_Code",rememberData.getDealerWarehouse());
                System.out.println(" Warehouse_Code !!!!!!!!!!!!!!  "+rememberData.getDealerWarehouse());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
        AppController.getInstance().getRequestQueue().getCache().remove(url);
        AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void parseDealerlist(String response)
    {
        try {
            dealerList.clear();
            dealerId.clear();

            JSONObject parentObj = new JSONObject(response);

            String status=parentObj.getString("status");
            if(status.equalsIgnoreCase("success")) {

                JSONArray jArray = parentObj.getJSONArray("data");

                dealerList.add("Select Dealer");
                dealerId.add("-1");

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject innerObj = jArray.optJSONObject(i);
                    //JSONObject records = innerObj.optJSONObject("records");

                    dealerList.add(innerObj.optString("Dealer_Name"));
                    dealerId.add(innerObj.optString("Dealer_Code"));
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        adapterDealer.notifyDataSetChanged();
        doProductList();
    }

}
