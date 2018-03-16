package com.lnsel.srfoods.activity2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lnsel.srfoods.R;
import com.lnsel.srfoods.activity2.DealerOutstanding2;
import com.lnsel.srfoods.activity2.DealerPreviousActivity2;
import com.lnsel.srfoods.activity2.DealerProfile2;
import com.lnsel.srfoods.activity2.SchemeView2;
import com.lnsel.srfoods.util2.Logout2;

public class DealerActivity2 extends Activity {


    Button btnBack,btnLogout;
    TextView headerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delare_pcj);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout2.logout(DealerActivity2.this);
                finish();
            }
        });
        btnBack=(Button)findViewById(R.id.btnBack);
       // btnBack.setVisibility(View.GONE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(DelareActivity.this,HomeActivity.class));
               // finish();
            }
        });
        headerTxt.setText("Dealer");
    }


    public void click(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.btndealerprofile:

                startActivity(new Intent(DealerActivity2.this,DealerProfile2.class));
                finish();
                break;

            case R.id.btndealernewoder:

                startActivity(new Intent(DealerActivity2.this,DealerNeworder2.class));
                finish();
                break;

            case R.id.btndealerpriviousoder:

                startActivity(new Intent(DealerActivity2.this,DealerPreviousActivity2.class));
                finish();
                break;

            case R.id.btndealerscheme:
                startActivity(new Intent(DealerActivity2.this,SchemeView2.class));
                finish();
                break;

            case R.id.btnOutstanding:
                startActivity(new Intent(DealerActivity2.this,DealerOutstanding2.class));
                finish();
                break;
        }

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DealerActivity2.this);
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
