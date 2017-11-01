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
  String ACTION_CREATE_DETAILS= "company_details";
  String ACTION_CREATE_GST= "company_gst";
  String ACTION_CREATE_ADDITIONAL= "company_additional";
  String ACTION_CREATE_LOGO= "company_logo";
  String ACTION_CREATE_SIGNATURE= "company_signature";
  String ACTION_CREATE_LOGIN= "company_login";
  String ACTION_COMPANY_LIST= "company_list";
  String ACTION_DELETE_COMPANY= "delete_company";
  String ACTION_GET_PACKAGES= "get_packages";
  String ACTION_GET_INDUSTRY= "get_industry";
  String ACTION_COMPANY_AUTHENTICATE= "company_authenticate";
  String SERVICE_NAME = "NetworkingService";
  String TIMEOUT="Something went wrong. Please try again";
  String KEY_FB_PARAMS = "fields";
  String FB_PARAMETERS = "id,email,first_name,last_name,birthday";




}
