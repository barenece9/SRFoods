package com.lnsel.srfoods;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.lnsel.srfoods.activity.HomeActivity;
import com.lnsel.srfoods.activity2.HomeActivity2;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


    }
    public  void btn_click(View view){
        if(view.getId()==R.id.btn_srfoods){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        }
        else if(view.getId()==R.id.btn_pcj){
            startActivity(new Intent(MainActivity.this, HomeActivity2.class));
            finish();
        }
    }
}
