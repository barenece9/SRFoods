package com.lnsel.srfoods.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lnsel.srfoods.MainActivity;
import com.lnsel.srfoods.R;


public class HomeActivity extends Activity implements OnClickListener{

	Button btnlogin;
	Button btnRegister;
	Button btnBack,btnLogout;
	TextView headerTxt;

	private static final int REQUEST_READ_PHONE_STATE = 110 ,
			REQUEST_ACCESS_FINE_LOCATION = 111,
			REQUEST_WRITE_STORAGE = 112,
			REQUEST_CALL = 113,
			REQUEST_SMS = 114,
			REQUEST_WRITE_INTERNAL = 115;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_activity);
		setWidget();
	}


	public void setWidget()
	{
		btnLogout=(Button)findViewById(R.id.btnLogout);
		btnLogout.setVisibility(View.GONE);
		btnlogin = (Button)findViewById(R.id.btnlogin);
		btnlogin.setOnClickListener(this);
		btnRegister = (Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
		headerTxt=(TextView)findViewById(R.id.headerTxt);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, MainActivity.class));
				finish();
			}
		});
		//btnBack.setVisibility(View.GONE);
		headerTxt.setText("Home");


		/*boolean hasPermissionPhoneState = (ContextCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
		if (!hasPermissionPhoneState) {
			ActivityCompat.requestPermissions(HomeActivity.this,
					new String[]{Manifest.permission.READ_PHONE_STATE},
					REQUEST_READ_PHONE_STATE);
		}
		*/

		boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
		if (!hasPermissionLocation) {
			ActivityCompat.requestPermissions(HomeActivity.this,
					new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
					REQUEST_ACCESS_FINE_LOCATION);
		}

		boolean hasPermissionWrite = (ContextCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
		if (!hasPermissionWrite) {
			ActivityCompat.requestPermissions(HomeActivity.this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
					REQUEST_WRITE_STORAGE);
		}

		boolean hasPermissionCall = (ContextCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED);
		if (!hasPermissionCall) {
			ActivityCompat.requestPermissions(HomeActivity.this,
					new String[]{Manifest.permission.CALL_PHONE},
					REQUEST_CALL);
		}

		/*boolean hasPermissionSms = (ContextCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED);
		if (!hasPermissionSms) {
			ActivityCompat.requestPermissions(HomeActivity.this,
					new String[]{Manifest.permission.SEND_SMS},
					REQUEST_SMS);
		}*/

	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		switch (view.getId()) {
		case R.id.btnlogin:
			
			startActivity(new Intent(HomeActivity.this,LogintypeActivity.class));
			//startActivity(new Intent(HomeActivity.this,CustomerLoginActivity.class));
			finish();

			break;

		case R.id.btnRegister:
			
			//startActivity(new Intent(HomeActivity.this,CustomerRrgisterActivity.class));
			startActivity(new Intent(HomeActivity.this,RegisterTypeActivity.class));
			finish();
			
			break;

			case R.id.btnBack:

				//startActivity(new Intent(HomeActivity.this,CustomerRrgisterActivity.class));
				//startActivity(new Intent(HomeActivity.this,RegisterTypeActivity.class));
				finish();

				break;

		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode)
		{
			/*case REQUEST_READ_PHONE_STATE: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					Toast.makeText(HomeActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
					//reload my activity with permission granted or use the features what required the permission
					finish();
					startActivity(getIntent());
				} else
				{
					Toast.makeText(HomeActivity.this, "The app was not allowed to get your phone state. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
				}
			}
			*/
			case REQUEST_ACCESS_FINE_LOCATION: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					Toast.makeText(HomeActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
					//reload my activity with permission granted or use the features what required the permission
					//finish();
					//startActivity(getIntent());
				} else
				{
					Toast.makeText(HomeActivity.this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
				}
			}

			case REQUEST_WRITE_STORAGE: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					Toast.makeText(HomeActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
					//reload my activity with permission granted or use the features what required the permission
					//finish();
					//startActivity(getIntent());
				} else
				{
					Toast.makeText(HomeActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
				}
			}

			case REQUEST_CALL: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					Toast.makeText(HomeActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
					//reload my activity with permission granted or use the features what required the permission
					//finish();
					//startActivity(getIntent());
				} else
				{
					Toast.makeText(HomeActivity.this, "The app was not allowed to call. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
				}
			}

			/*case REQUEST_SMS: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					Toast.makeText(HomeActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
					//reload my activity with permission granted or use the features what required the permission
					finish();
					startActivity(getIntent());
				} else
				{
					Toast.makeText(HomeActivity.this, "The app was not allowed to send sms. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
				}
			}*/
		}

	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
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
