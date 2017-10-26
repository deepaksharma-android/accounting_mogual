package com.berylsystems.buzz.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pc on 10/7/2016.
 */
public class Preferences {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "iParkSmartPreferences";
    private static Preferences instance;
    private static final String IS_LOGIN = "Login";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";
    private static final String PASSWORD = "password";
    private static final String IMAGE = "image";
    private static final String ID = "id";
    private static final String PAYUMONEYPREF = "payumoneypref";
    private static final String AUTH = "auth";

  


    private Preferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public static Preferences getInstance(Context context) {
        if (instance == null) {
            instance = new Preferences(context);
        }
        return instance;
    }

    public void setLogin(Boolean isLogin) {
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }

    public Boolean getLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setPayUMoneyPref(String payumoneypref){
        editor.putString(PAYUMONEYPREF,payumoneypref);
        editor.commit();
    }
    public String getPayUMoneyPref(){
        return pref.getString(PAYUMONEYPREF,"");
    }

    public void setAuth(String auth){
        editor.putString(AUTH,auth);
        editor.commit();
    }
    public String getAuth(){
        return pref.getString(AUTH,"");
    }


    public void setName(String name){
        editor.putString(NAME,name);
        editor.commit();
    }
    public String getName(){
        return pref.getString(NAME,"");
    }

    public void setEmail(String email){
        editor.putString(EMAIL,email);
        editor.commit();
    }
    public String getEmail(){
        return pref.getString(EMAIL,"");
    }

    public void setMobile(String mobile){
        editor.putString(MOBILE,mobile);
        editor.commit();
    }
    public String getMobile(){
        return pref.getString(MOBILE,"");
    }

    public void setPassword(String password){
        editor.putString(PASSWORD,password);
        editor.commit();
    }
    public String getPassword(){
        return pref.getString(PASSWORD,"");
    }

    public void setProfilePic(String url){
        editor.putString(IMAGE,url);
        editor.commit();
    }
    public String getProfilePic(){
        return pref.getString(IMAGE,"");
    }

    public void setId(String id){
        editor.putString(ID,id);
        editor.commit();
    }
    public String getID(){
        return pref.getString(ID,"");
    }



    }
