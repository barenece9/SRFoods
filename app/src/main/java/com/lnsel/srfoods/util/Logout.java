package com.lnsel.srfoods.util;

import android.content.Context;
import android.content.Intent;

import com.lnsel.srfoods.activity.HomeActivity;

/**
 * Created by db on 1/30/2017.
 */
public class Logout {
    public static final void logout(Context context){
        context.startActivity(new Intent(context,HomeActivity.class));
    }
}
