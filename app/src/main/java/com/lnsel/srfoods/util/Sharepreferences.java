package com.lnsel.srfoods.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lnsel.srfoods.settergetterclass.RememberData;


public class Sharepreferences {


	private static String preferanceName = "SaveUserInfo"; 
	private static String preferanceRemember = "RememberMe";
	private static String preferanceRememberPass = "RememberPass";
	private static String preferanceRememberdeliveryboy="RememberDeliveryBoy";
	private static String preferanceRememberPassdelivery="RememberPassDelivery";

	private static String preferanceRememberSalesPerson="RememberSalesPerson";
	private static String preferanceRememberSalesPersonInfo="RememberSalesPersonInfo";



	public static void setRememberMe(Context context,String userName , String password , int remember)
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRemember, context.MODE_PRIVATE); 
		Editor editor = pref.edit(); 

		editor.putString("userName", userName); 
		editor.putString("password",password); 
		editor.putInt("remember", remember);   

		editor.commit();
	}

	public static RememberData getRememberMe(Context context)
	{
		RememberData rememberData = new RememberData();

		SharedPreferences pref = context.getSharedPreferences(preferanceRemember, context.MODE_PRIVATE);

		rememberData.setUserName(pref.getString("userName", ""));
		rememberData.setPassword(pref.getString("password", ""));
		rememberData.setRemember(pref.getInt("remember", 0)); 

		return rememberData; 
	}


	public static void setRememberDeliveryboy(Context context,String userName , String password , int remember)
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberdeliveryboy, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("userName", userName);
		editor.putString("password",password);
		editor.putInt("remember", remember);

		editor.commit();
	}

	public static RememberData getRememberDeliveryboy(Context context)
	{
		RememberData rememberData = new RememberData();

		SharedPreferences pref = context.getSharedPreferences(preferanceRememberdeliveryboy, context.MODE_PRIVATE);

		rememberData.setUserName(pref.getString("userName", ""));
		rememberData.setPassword(pref.getString("password", ""));
		rememberData.setRemember(pref.getInt("remember", 0));

		return rememberData;
	}


	public static void setDealerinfo(Context context,String dealercode,String dealerwarehouse )
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberPass, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("dealercode",dealercode);
		editor.putString("dealerwarehouse",dealerwarehouse);

		editor.commit();
	}

	public static RememberData getDealerinfo(Context context)
	{
		RememberData rememberData = new RememberData();
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberPass, context.MODE_PRIVATE);

		rememberData.setDealerCode(pref.getString("dealercode", ""));
		rememberData.setDealerWarehouse(pref.getString("dealerwarehouse", ""));

		return rememberData;
	}

	/*delivery boy info...*/
	public static void setDeliveryinfo(Context context,String dealercode,String dealerwarehouse )
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberPassdelivery, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("dealercode",dealercode);
		editor.putString("dealerwarehouse",dealerwarehouse);

		editor.commit();
	}

	public static RememberData getDeleveryinfo(Context context)
	{
		RememberData rememberData = new RememberData();
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberPassdelivery, context.MODE_PRIVATE);

		rememberData.setDealerCode(pref.getString("dealercode", ""));
		rememberData.setDealerWarehouse(pref.getString("dealerwarehouse", ""));

		return rememberData;
	}


	// sales person module.....................................................

	public static void setRememberMeSalePerson(Context context,String userName , String password , int remember)
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberSalesPerson, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("userName", userName);
		editor.putString("password",password);
		editor.putInt("remember", remember);

		editor.commit();
	}

	public static RememberData getRememberMeSalePerson(Context context)
	{
		RememberData rememberData = new RememberData();

		SharedPreferences pref = context.getSharedPreferences(preferanceRememberSalesPerson, context.MODE_PRIVATE);

		rememberData.setUserName(pref.getString("userName", ""));
		rememberData.setPassword(pref.getString("password", ""));
		rememberData.setRemember(pref.getInt("remember", 0));

		return rememberData;
	}

	/*delivery boy info...*/
	public static void setSalesPersoninfo(Context context,String dealercode,String dealerwarehouse )
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberSalesPersonInfo, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("dealercode",dealercode);
		editor.putString("dealerwarehouse",dealerwarehouse);

		editor.commit();
	}

	public static RememberData getSalesPersoninfo(Context context)
	{
		RememberData rememberData = new RememberData();
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberSalesPersonInfo, context.MODE_PRIVATE);

		rememberData.setDealerCode(pref.getString("dealercode", ""));
		rememberData.setDealerWarehouse(pref.getString("dealerwarehouse", ""));

		return rememberData;
	}

}
