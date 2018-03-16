package com.lnsel.srfoods.util2;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lnsel.srfoods.settergetterclass.RememberData;
import com.lnsel.srfoods.settergetterclass2.RememberData2;


public class Sharepreferences2 {


	private static String preferanceName2 = "SaveUserInfo2";
	private static String preferanceRemember2 = "RememberMe2";
	private static String preferanceRememberPass2 = "RememberPass2";
	private static String preferanceRememberdeliveryboy2="RememberDeliveryBoy2";
	private static String preferanceRememberPassdelivery2="RememberPassDelivery2";

	private static String preferanceRememberSalesPerson2="RememberSalesPerson2";
	private static String preferanceRememberSalesPersonInfo2="RememberSalesPersonInfo2";



	public static void setRememberMe(Context context,String userName , String password , int remember)
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRemember2, context.MODE_PRIVATE);
		Editor editor = pref.edit(); 

		editor.putString("userName", userName); 
		editor.putString("password",password); 
		editor.putInt("remember", remember);   

		editor.commit();
	}

	public static RememberData2 getRememberMe(Context context)
	{
		RememberData2 rememberData = new RememberData2();

		SharedPreferences pref = context.getSharedPreferences(preferanceRemember2, context.MODE_PRIVATE);

		rememberData.setUserName(pref.getString("userName", ""));
		rememberData.setPassword(pref.getString("password", ""));
		rememberData.setRemember(pref.getInt("remember", 0)); 

		return rememberData; 
	}


	public static void setRememberDeliveryboy(Context context,String userName , String password , int remember)
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberdeliveryboy2, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("userName", userName);
		editor.putString("password",password);
		editor.putInt("remember", remember);

		editor.commit();
	}

	public static RememberData2 getRememberDeliveryboy(Context context)
	{
		RememberData2 rememberData = new RememberData2();

		SharedPreferences pref = context.getSharedPreferences(preferanceRememberdeliveryboy2, context.MODE_PRIVATE);

		rememberData.setUserName(pref.getString("userName", ""));
		rememberData.setPassword(pref.getString("password", ""));
		rememberData.setRemember(pref.getInt("remember", 0));

		return rememberData;
	}


	public static void setDealerinfo(Context context,String dealercode,String dealerwarehouse ,String state_id)
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberPass2, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("dealercode",dealercode);
		editor.putString("dealerwarehouse",dealerwarehouse);
		editor.putString("state_id",state_id);

		editor.commit();
	}

	public static RememberData2 getDealerinfo(Context context)
	{
		RememberData2 rememberData = new RememberData2();
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberPass2, context.MODE_PRIVATE);

		rememberData.setDealerCode(pref.getString("dealercode", ""));
		rememberData.setDealerWarehouse(pref.getString("dealerwarehouse", ""));
		rememberData.setState_id(pref.getString("state_id",""));

		return rememberData;
	}

	/*delivery boy info...*/
	public static void setDeliveryinfo(Context context,String dealercode,String dealerwarehouse )
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberPassdelivery2, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("dealercode",dealercode);
		editor.putString("dealerwarehouse",dealerwarehouse);

		editor.commit();
	}

	public static RememberData2 getDeleveryinfo(Context context)
	{
		RememberData2 rememberData = new RememberData2();
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberPassdelivery2, context.MODE_PRIVATE);

		rememberData.setDealerCode(pref.getString("dealercode", ""));
		rememberData.setDealerWarehouse(pref.getString("dealerwarehouse", ""));

		return rememberData;
	}


	// sales person module.....................................................

	public static void setRememberMeSalePerson(Context context,String userName , String password , int remember)
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberSalesPerson2, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("userName", userName);
		editor.putString("password",password);
		editor.putInt("remember", remember);

		editor.commit();
	}

	public static RememberData2 getRememberMeSalePerson(Context context)
	{
		RememberData2 rememberData = new RememberData2();

		SharedPreferences pref = context.getSharedPreferences(preferanceRememberSalesPerson2, context.MODE_PRIVATE);

		rememberData.setUserName(pref.getString("userName", ""));
		rememberData.setPassword(pref.getString("password", ""));
		rememberData.setRemember(pref.getInt("remember", 0));

		return rememberData;
	}

	public static void setSalesPersoninfo(Context context,String dealercode,String dealerwarehouse )
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberSalesPersonInfo2, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("dealercode",dealercode);
		editor.putString("dealerwarehouse",dealerwarehouse);

		editor.commit();
	}

	public static RememberData2 getSalesPersoninfo(Context context)
	{
		RememberData2 rememberData = new RememberData2();
		SharedPreferences pref = context.getSharedPreferences(preferanceRememberSalesPersonInfo2, context.MODE_PRIVATE);

		rememberData.setDealerCode(pref.getString("dealercode", ""));
		rememberData.setDealerWarehouse(pref.getString("dealerwarehouse", ""));

		return rememberData;
	}
}
