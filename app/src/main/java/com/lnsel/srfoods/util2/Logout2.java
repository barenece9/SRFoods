package com.lnsel.srfoods.util2;

import android.content.Context;
import android.content.Intent;

import com.lnsel.srfoods.activity2.HomeActivity2;

/**
 * Created by db on 1/30/2017.
 */
public class Logout2 {
    public static final void logout(Context context){
        context.startActivity(new Intent(context,HomeActivity2.class));
    }
}
