package com.berylsystems.buzz.utils;


public interface Cv {
  String BASE_URL ="http://192.168.1.4:3000/api/v1/";
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
  String SERVICE_NAME = "NetworkingService";
  String TIMEOUT="Something went wrong. Please try again";
  String KEY_FB_PARAMS = "fields";
  String FB_PARAMETERS = "id,email,first_name,last_name,birthday";




}
