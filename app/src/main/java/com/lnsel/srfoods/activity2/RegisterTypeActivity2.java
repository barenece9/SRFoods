package com.lnsel.srfoods.activity2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.lnsel.srfoods.R;



public class RegisterTypeActivity2 extends Activity implements OnClickListener{


	Button btnChefReg;
	Button btnCustomerReg;
	Button btnBack;
	Button btnLogout;
	TextView headerTxt;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registertype_activity_pcj);

		setWidget();
	}


	public void setWidget()
	{
		btnLogout=(Button)findViewById(R.id.btnLogout);
		btnLogout.setVisibility(View.GONE);
		btnChefReg = (Button)findViewById(R.id.btnRegChef);
		btnChefReg.setOnClickListener(this);
		btnCustomerReg = (Button)findViewById(R.id.btnRegCustomer);
		btnCustomerReg.setOnClickListener(this);
		headerTxt=(TextView)findViewById(R.id.headerTxt);
		btnBack=(Button)findViewById(R.id.btnBack);
		headerTxt.setText("Registration");
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegisterTypeActivity2.this,HomeActivity2.class));
				finish();
			}
		});
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btnRegChef:
			
			//startActivity(new Intent(RegisterTypeActivity.this,HomeActivity.class));

			break;

		case R.id.btnRegCustomer:

			startActivity(new Intent(RegisterTypeActivity2.this,DelareRegActivity2.class));
			finish();
			break;

		}
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterTypeActivity2.this);
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
