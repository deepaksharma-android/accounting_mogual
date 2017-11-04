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
    private static final String PREF_NAME = "Bahikhata";
    private static Preferences instance;
    private static final String IS_LOGIN = "Login";
    private static final String CID="cid";
    private static final String CNAME="name";
    private static final String CPRINTNAME="print_name";
    private static final String CSHORTNAME="short_name";
    private static final String CPHONENUMBER="phone";
    private static final String CFINANCIALYEAR="financial";
    private static final String CBOOKYEAR="book";
    private static final String CCIN="cin";
    private static final String CPAN="pan";
    private static final String CADDRESS="address";
    private static final String CCITY="city";
    private static final String CCOUNTRY="country";
    private static final String CSTATE="state";
    private static final String CINDUSTRYTYPE="industry";
    private static final String CWARD="ward";
    private static final String CFAX="fax";
    private static final String CEMAIL="email";
    private static final String CGST="gst";
    private static final String CDEALER="dealer";
    private static final String CTAX1="tax1";
    private static final String CTAX2="tax2";
    private static final String CSYMBOL="symbol";
    private static final String CSTRING="string";
    private static final String CSUBSTRING="substring";
    private static final String CLOGO="logo";
    private static final String CSIGN="sign";
    private static final String CUSERNAME="username";
    private static final String CPASSWORD="password";





  


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
    public void setCid(String cid){
        editor.putString(CID,cid);
        editor.commit();
    }
    public String getCid(){
        return pref.getString(CID,"");
    }

    public void setCname(String cname){
        editor.putString(CNAME,cname);
        editor.commit();
    }
    public String getCname(){
        return pref.getString(CNAME,"");
    }

    public void setCprintname(String cprintname){
        editor.putString(CPRINTNAME,cprintname);
        editor.commit();
    }
    public String getCprintname(){
        return pref.getString(CPRINTNAME,"");
    }

    public void setCshortname(String cshortname){
        editor.putString(CSHORTNAME,cshortname);
        editor.commit();
    }
    public String getCshortname(){
        return pref.getString(CSHORTNAME,"");
    }

    public void setCphonenumber(String cphonenumber){
        editor.putString(CPHONENUMBER,cphonenumber);
        editor.commit();
    }
    public String getCphonenumber(){
        return pref.getString(CPHONENUMBER,"");
    }

    public void setCfinancialyear(String cfinancilayear){
        editor.putString(CFINANCIALYEAR,cfinancilayear);
        editor.commit();
    }
    public String getCfinancialyear(){
        return pref.getString(CFINANCIALYEAR,"");
    }

    public void setCbookyear(String cbookyear){
        editor.putString(CBOOKYEAR,cbookyear);
        editor.commit();
    }
    public String getCbookyear(){
        return pref.getString(CBOOKYEAR,"");
    }

    public void setCcin(String ccin){
        editor.putString(CCIN,ccin);
        editor.commit();
    }
    public String getCcin(){
        return pref.getString(CCIN,"");
    }

    public void setCpan(String cpan){
        editor.putString(CPAN,cpan);
        editor.commit();
    }
    public String getCpan(){
        return pref.getString(CPAN,"");
    }

    public void setCaddress(String caddress){
        editor.putString(CADDRESS,caddress);
        editor.commit();
    }
    public String getCaddress(){
        return pref.getString(CADDRESS,"");
    }

    public void setCcity(String ccity){
        editor.putString(CCITY,ccity);
        editor.commit();
    }
    public String getCcity(){
        return pref.getString(CCITY,"");
    }

    public void setCcountry(String ccountry){
        editor.putString(CCOUNTRY,ccountry);
        editor.commit();
    }
    public String getCcountry(){
        return pref.getString(CCOUNTRY,"");
    }

    public void setCstate(String cstate){
        editor.putString(CSTATE,cstate);
        editor.commit();
    }
    public String getCstate(){
        return pref.getString(CSTATE,"");
    }

    public void setCindustrytype(String cindustrytype){
        editor.putString(CINDUSTRYTYPE,cindustrytype);
        editor.commit();
    }
    public String getCindustrytype(){
        return pref.getString(CINDUSTRYTYPE,"");
    }

    public void setCward(String cward){
        editor.putString(CWARD,cward);
        editor.commit();
    }
    public String getCward(){
        return pref.getString(CWARD,"");
    }
    public void setCfax(String cfax){
        editor.putString(CFAX,cfax);
        editor.commit();
    }
    public String getCfax(){
        return pref.getString(CFAX,"");
    }
    public void setCemail(String cemail){
        editor.putString(CEMAIL,cemail);
        editor.commit();
    }
    public String getCemail(){
        return pref.getString(CEMAIL,"");
    }
    public void setCgst(String cgst){
        editor.putString(CGST,cgst);
        editor.commit();
    }
    public String getCgst(){
        return pref.getString(CGST,"");
    }
    public void setCdealer(String cdealer){
        editor.putString(CDEALER,cdealer);
        editor.commit();
    }
    public String getCdealer(){
        return pref.getString(CDEALER,"");
    }
    public void setCtax1(String ctax1){
        editor.putString(CTAX1,ctax1);
        editor.commit();
    }
    public String getCtax1(){
        return pref.getString(CTAX1,"");
    }
    public void setCtax2(String ctax2){
        editor.putString(CTAX2,ctax2);
        editor.commit();
    }
    public String getCtax2(){
        return pref.getString(CTAX2,"");
    }
    public void setCsymbol(String csymbol){
        editor.putString(CSYMBOL,csymbol);
        editor.commit();
    }
    public String getCsymbol(){
        return pref.getString(CSYMBOL,"");
    }
    public void setCstring(String cstring){
        editor.putString(CSTRING,cstring);
        editor.commit();
    }
    public String getCstring(){
        return pref.getString(CSTRING,"");
    }
    public void setCsubstring(String csubstring){
        editor.putString(CSUBSTRING,csubstring);
        editor.commit();
    }
    public String getCsubstring(){
        return pref.getString(CSUBSTRING,"");
    }

    public void setClogo(String clogo){
        editor.putString(CLOGO,clogo);
        editor.commit();
    }
    public String getClogo(){
        return pref.getString(CLOGO,"");
    }
    public void setCsign(String csign){
        editor.putString(CSIGN,csign);
        editor.commit();
    }
    public String getCsign(){
        return pref.getString(CSIGN,"");
    }
    public void setCusername(String cusername){
        editor.putString(CUSERNAME,cusername);
        editor.commit();
    }
    public String getCusername(){
        return pref.getString(CUSERNAME,"");
    }
    public void setCpassword(String cpassword){
        editor.putString(CPASSWORD,cpassword);
        editor.commit();
    }
    public String getCpassword(){
        return pref.getString(CPASSWORD,"");
    }





    }
