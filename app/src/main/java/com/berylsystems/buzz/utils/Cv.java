package com.berylsystems.buzz.utils;


public interface Cv {
  String BASE_URL="http://accounts.geeksonrails.com/api/v1/";
  //String BASE_URL ="http://192.168.1.4:3000/api/v1/";
  int PERMISSIONS_BUZZ_REQUEST = 0xABC;
  String PREFS_APP_USER = "com.berylsystems.buzz.utils.app_user";
  String ACTION_LOGIN = "login_user";
  String ACTION_FACEBOOK_CHECK= "facebook_check";
  String ACTION_FORGOT_PASSWORD = "forgot_password";
  String ACTION_VERIFICATION = "verification";
  String ACTION_NEW_PASSWORD = "new_password";
  String ACTION_RESEND_OTP = "resend_otp";
  String ACTION_UPDATE_MOBILE_NUMBER="update_mobile_number";
  String ACTION_REGISTER_USER = "register_user";
  String ACTION_CREATE_COMPANY= "create_company";
  String ACTION_CREATE_BASIC= "create_basic";
  String ACTION_CREATE_DETAILS= "company_details";
  String ACTION_CREATE_GST= "company_gst";
  String ACTION_CREATE_ADDITIONAL= "company_additional";
  String ACTION_CREATE_LOGO= "company_logo";
  String ACTION_CREATE_SIGNATURE= "company_signature";
  String ACTION_CREATE_LOGIN= "company_login";
  String ACTION_COMPANY_LIST= "company_list";
  String ACTION_DELETE_COMPANY= "delete_company";
  String ACTION_GET_PACKAGES= "get_packages";
  String ACTION_PLANS= "plans";
  String ACTION_GET_INDUSTRY= "get_industry";
  String ACTION_EDIT_LOGIN="edit_login";
  String ACTION_CREATE_ACCOUNT_GROUP="create_account_group";
  String ACTION_GET_ACCOUNT_GROUP="get_account_group";
  String ACTION_GET_ACCOUNT_GROUP_DETAILS="get_account_group_details";
  String ACTION_EDIT_ACCOUNT_GROUP="edit_account_group";
  String ACTION_DELETE_ACCOUNT_GROUP="delete_account_group";
  String ACTION_CREATE_ACCOUNT="create_account";
  String ACTION_GET_ACCOUNT="get_account";
  String ACTION_GET_ACCOUNT_DETAILS="get_account_details";
  String ACTION_EDIT_ACCOUNT="edit_account";
  String ACTION_DELETE_ACCOUNT="delete_account";
  String ACTION_COMPANY_AUTHENTICATE= "company_authenticate";
  String ACTION_GET_COMPANY= "get_company";
  String ACTION_SEARCH_COMPANY= "search_company";
  String ACTION_GET_COMPANY_USER= "get_company_user";
  String SERVICE_NAME = "NetworkingService";
  String TIMEOUT="Something went wrong. Please try again";
  String KEY_FB_PARAMS = "fields";
  String FB_PARAMETERS = "id,email,first_name,last_name,birthday";




}
