package com.lnsel.srfoods.util;

/**
 * Created by db on 1/21/2017.
 */
public class Webservice {

    //public static final String BASE_URL="http://ims.srfoods.in/API/";
    //public static final String PRODUCT_IMAGE="http://ims.srfoods.in/product_management/product_images/";

    public static final String BASE_URL="http://61.16.131.206/projects/srfoods/API/";
    public static final String PRODUCT_IMAGE="http://61.16.131.206/projects/srfoods/product_management/product_images/";

    public static final String STATE_LIST = BASE_URL+"state_list.php";
    public static final String DEALER_REG = BASE_URL+"API_Dealer.php";
    public static final String DEALER_LOGIN = BASE_URL+"login.php";
    public static final String DEALER_PASS_UPDATE = BASE_URL+"password_reset.php";
    public static final String FORGOT_PASSWORD = BASE_URL+"forget_password.php";
    public static final String DEALER_PROFILE_DETAILS= BASE_URL+"profile_details.php";
    public static final String PRODUCT_NO= BASE_URL+"order_number.php";
    public static final String WAREHOUSE_LIST= BASE_URL+"warehouse_list.php";
    public static final String TRANSPOTER_LIST="";
    public static final String PRODUCT_LIST= BASE_URL+"product_list.php";
    public static final String MAKE_ORDER= BASE_URL+"insert_order_info.php";
    public static final String DELETE_ORDER= BASE_URL+"del_from_order.php";
    public static final String ORDER_DETAILS_BY_ORDER_NO= BASE_URL+"view_temp_order_details.php";
    public static final String CONFIRM_ORDER= BASE_URL+"update_temp_master.php";
    public static final String CLEAR_ALL_ORDER= BASE_URL+"cancel_order.php";
    public static final String DELIVERY_BOY_LOGIN = BASE_URL+"login_delivery_boy.php";
    public static final String DEALER_ALL_ORDER= BASE_URL+"order_status.php";
    public static final String CONFIRM_ORDER_DETAILS= BASE_URL+"approved_order_details.php";
    public static final String DISPATCH_ORDER_INVOICE= BASE_URL+"newtax_invoice.php";
    public static final String DOWNLOAD_INVOICE= BASE_URL+"pdf.php";
    public static final String DISPATCH_ORDER_INVOICE_DOWNLOAD= BASE_URL+"Tax_Invoice.pdf";
    public static final String Assigned_Order_list_to_Delivery_Boy= BASE_URL+"assigned_order_list_to_delivery_boy.php";
    public static final String DEALER_SCHEME_STATUS= BASE_URL+"show_scheme.php";
    public static final String DEALER_SCHEME= BASE_URL+"scheme_list.php";
    public static final String POST_LAT_LONG = BASE_URL+"update_delivery_boy_position.php";
    public static final String GET_PATMENT_ID = BASE_URL+"getPaymentID.php";
    public static final String PROCESS_PAYMENT = BASE_URL+"process_payment.php";
    public static final String DEALER_OUTSTANDING= BASE_URL+"dealer_due_list.php";
    public static final String DEALER_OUTSTANDING_DETAILS= BASE_URL+"payment_list.php";
    public static final String BANK_NAME = BASE_URL+"bank_list.php";
    public static final String SUBMIT_SIGNATURE = BASE_URL+"upload_dealer_signature.php";
    public static final String DELIVERY_BOY_COMPLATE_ORDER_LIST = BASE_URL+"delivery_boy_complete_order.php";

    public static final String CATEGORY_LIST=BASE_URL+"category_list.php";

    public static final String SALES_PERSON_LOGIN = BASE_URL+"sales_person_login.php";
    public static final String DEALER_LIST= BASE_URL+"dealer_list.php";
    public static final String SALES_PERSON_PRODUCT_NO= BASE_URL+"order_number.php";
    public static final String SALES_PERSON_ALL_ORDER= BASE_URL+"sp_order_status.php";
}
