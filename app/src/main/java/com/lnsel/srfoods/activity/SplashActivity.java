package com.lnsel.srfoods.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;

import com.lnsel.srfoods.MainActivity;
import com.lnsel.srfoods.R;


public class SplashActivity extends Activity {
	
	private static final int SPLASH_TIME_OUT = 1000;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);

     	mRunnable = new Runnable() {
            @Override
            public void run() {
                openLandingActivity();
            }
        };
        mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);
    }

    protected void openLandingActivity() {
		// TODO Auto-generated method stub
			//startActivity(new Intent(SplashActivity.this,HomeActivity.class));
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
			finish();
		}
	@Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        // TODO Auto-generated method stub
	        if(keyCode == KeyEvent.KEYCODE_BACK) {
	            mHandler.removeCallbacks(mRunnable);
	            finish();
	        }
	        return super.onKeyDown(keyCode, event);
	    }
}
