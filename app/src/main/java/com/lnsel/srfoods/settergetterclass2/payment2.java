package com.lnsel.srfoods.settergetterclass2;

/**
 * Created by db on 3/1/2017.
 */
public class payment2 {

    public static String Order_Number = "";
    public static String Dealer_Code = "";
    public static String Total_Amt = "";
    public static String Due_Amt = "";

    public static String getOrderNumber() {
        return Order_Number;
    }
    public static void setOrderNumber(String Order_Number1) {
        Order_Number = Order_Number1;
    }
    public static String getDealerCode() {
        return Dealer_Code;
    }
    public static void setDealerCode(String Dealer_Code1) {
        Dealer_Code = Dealer_Code1;
    }
    public static String getTotalAmt() {
        return Total_Amt;
    }
    public static void setTotalAmt(String Total_Amt1) {
        Total_Amt = Total_Amt1;
    }
    public static String getDueAmt() {
        return Due_Amt;
    }
    public static void setDueAmt(String Due_Amt1) {
        Due_Amt = Due_Amt1;
    }
}
