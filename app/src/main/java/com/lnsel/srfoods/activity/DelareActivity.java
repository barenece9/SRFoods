package com.lnsel.srfoods.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lnsel.srfoods.R;
import com.lnsel.srfoods.util.Logout;

public class DelareActivity extends Activity {


    Button btnBack,btnLogout;
    TextView headerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delare);
        headerTxt=(TextView)findViewById(R.id.headerTxt);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout.logout(DelareActivity.this);
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

                startActivity(new Intent(DelareActivity.this,DealerProfile.class));
                finish();
                break;

            case R.id.btndealernewoder:

                startActivity(new Intent(DelareActivity.this,DealerNeworder.class));
                finish();
                break;

            case R.id.btndealerpriviousoder:

                startActivity(new Intent(DelareActivity.this,DealerPreviousActivity.class));
                finish();
                break;

            case R.id.btndealerscheme:
                startActivity(new Intent(DelareActivity.this,SchemeView.class));
                finish();
                break;

            case R.id.btnOutstanding:
                startActivity(new Intent(DelareActivity.this,DealerOutstanding.class));
                finish();
                break;
        }

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DelareActivity.this);
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
