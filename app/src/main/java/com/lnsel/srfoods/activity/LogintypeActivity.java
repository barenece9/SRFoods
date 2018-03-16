package com.lnsel.srfoods.activity;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lnsel.srfoods.R;


public class LogintypeActivity extends Activity implements OnClickListener{


	Button btnChef;
	Button btnCustomer;
	Button btnloginSalesPerson;
	Button btnBack,btnLogout;
	TextView headerTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logintype_activity);


		setWidget();
	}


	public void setWidget()
	{
		btnLogout=(Button)findViewById(R.id.btnLogout);
		btnLogout.setVisibility(View.GONE);
		btnChef = (Button)findViewById(R.id.btnloginChef);
		btnChef.setOnClickListener(this);
		btnCustomer = (Button)findViewById(R.id.btnloginCustomer);
		btnCustomer.setOnClickListener(this);
		btnloginSalesPerson = (Button)findViewById(R.id.btnloginSalesPerson);
		btnloginSalesPerson.setOnClickListener(this);
		headerTxt=(TextView)findViewById(R.id.headerTxt);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LogintypeActivity.this,HomeActivity.class));
				finish();
			}
		});
		headerTxt.setText("Login");
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btnloginChef:
			
			startActivity(new Intent(LogintypeActivity.this,DeliveryBoyLoginActivity.class));
			finish();
			break;

		case R.id.btnloginCustomer:

			startActivity(new Intent(LogintypeActivity.this,DelareLoginActivity.class));
			finish();
			break;

			case R.id.btnloginSalesPerson:

				startActivity(new Intent(LogintypeActivity.this,SalesParsonLoginActivity.class));
				finish();
				break;

		}

	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogintypeActivity.this);
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
