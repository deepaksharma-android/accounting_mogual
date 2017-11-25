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
    private static final String CID = "cid";
    private static final String CNAME = "name";
    private static final String CPRINTNAME = "print_name";
    private static final String CSHORTNAME = "short_name";
    private static final String CPHONENUMBER = "phone";
    private static final String CFINANCIALYEAR = "financial";
    private static final String CBOOKYEAR = "book";
    private static final String CCIN = "cin";
    private static final String CPAN = "pan";
    private static final String CADDRESS = "address";
    private static final String CCITY = "city";
    private static final String CCOUNTRY = "country";
    private static final String CSTATE = "state";
    private static final String CINDUSTRYTYPE = "industry";
    private static final String CWARD = "ward";
    private static final String CFAX = "fax";
    private static final String CEMAIL = "email";
    private static final String CGST = "gst";
    private static final String CDEALER = "dealer";
    private static final String CTAX1 = "tax1";
    private static final String CTAX2 = "tax2";
    private static final String CSYMBOL = "symbol";
    private static final String CSTRING = "string";
    private static final String CSUBSTRING = "substring";
    private static final String CLOGO = "logo";
    private static final String CSIGN = "sign";
    private static final String CUSERNAME = "username";
    private static final String CPASSWORD = "password";


    private static final String bill_sundry_amount_of_bill_sundry_fed_as = "bill_sundry_amount_of_bill_sundry_fed_as";
    private static final String bill_sundry_of_percentage = "bill_sundry_of_percentage";
    private static final String bill_sundry_number_of_bill_sundry = "bill_sundry_number_of_bill_sundry";
    private static final String bill_sundry_consolidate_bill_sundry = "bill_sundry_consolidate_bill_sundry";
    private static final String bill_sundry_calculated_on = "bill_sundry_calculated_on";
    private static final String bill_sundry_amount_round_off = "bill_sundry_amount_round_off";
    private static final String bill_sundry_rouding_off_nearest = "bill_sundry_rouding_off_nearest";
    private static final String bill_sundry_rounding_off_limit = "bill_sundry_rounding_off_limit";
    private static final String bill_sundry_type = "bill_sundry_type";
    private static final String calculated_on_bill_sundry_id = "calculated_on_bill_sundry_id";

    private static final String sale_affect_accounting = "sale_affect_accounting";
    private static final String sale_affect_sale_amount = "sale_affect_sale_amount";
    private static final String sale_affect_sale_amount_specify_in = "sale_affect_sale_amount_specify_in";
    private static final String sale_adjust_in_party_amount = "sale_adjust_in_party_amount";
    private static final String sale_party_amount_specify_in = "sale_party_amount_specify_in";
    private static final String sale_account_head_to_post_party_amount = "sale_account_head_to_post_party_amount";
    private static final String sale_account_head_to_post_sale_amount = "sale_account_head_to_post_sale_amount";
    private static final String sale_post_over_above = "sale_post_over_above";
    private static final String sale_account_head_to_post_party_amount_id = "sale_account_head_to_post_party_amount_id";
    private static final String sale_account_head_to_post_sale_amount_id = "sale_account_head_to_post_sale_amount_id";

    private static final String purchase_affect_accounting = "purchase_affect_accounting";
    private static final String purchase_affect_purchase_amount = "purchase_affect_purchase_amount";
    private static final String purchase_affect_purchase_amount_specify_in = "purchase_affect_purchase_amount_specify_in";
    private static final String purchase_account_head_to_post_purchase_amount = "purchase_account_head_to_post_purchase_amount";
    private static final String purchase_adjust_in_party_amount = "purchase_adjust_in_party_amount";
    private static final String purchase_party_amount_specify_in = "purchase_party_amount_specify_in";
    private static final String purchase_account_head_to_post_party_amount = "purchase_account_head_to_post_party_amount";
    private static final String purchase_post_over_above = "purchase_post_over_above";
    private static final String purchase_account_head_to_post_party_amount_id = "purchase_account_head_to_post_party_amount_id";
    private static final String purchase_account_head_to_post_purchase_amount_id = "purchase_account_head_to_post_purchase_amount_id";

    private static final String cost_goods_in_sale = "cost_goods_in_sale";
    private static final String cost_goods_in_purchase = "cost_goods_in_purchase";
    private static final String cost_material_issue = "cost_material_issue";
    private static final String cost_material_receipt = "cost_material_receipt";
    private static final String cost_stock_transfer = "cost_stock_transfer";

    private static final String item_stock_quantity = "item_stock_quantity";
    private static final String item_stock_amount = "item_stock_amount";
    private static final String item_stock_value = "item_stock_value";





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

    public void setCid(String cid) {
        editor.putString(CID, cid);
        editor.commit();
    }

    public String getCid() {
        return pref.getString(CID, "");
    }

    public void setCname(String cname) {
        editor.putString(CNAME, cname);
        editor.commit();
    }

    public String getCname() {
        return pref.getString(CNAME, "");
    }

    public void setCprintname(String cprintname) {
        editor.putString(CPRINTNAME, cprintname);
        editor.commit();
    }

    public String getCprintname() {
        return pref.getString(CPRINTNAME, "");
    }

    public void setCshortname(String cshortname) {
        editor.putString(CSHORTNAME, cshortname);
        editor.commit();
    }

    public String getCshortname() {
        return pref.getString(CSHORTNAME, "");
    }

    public void setCphonenumber(String cphonenumber) {
        editor.putString(CPHONENUMBER, cphonenumber);
        editor.commit();
    }

    public String getCphonenumber() {
        return pref.getString(CPHONENUMBER, "");
    }

    public void setCfinancialyear(String cfinancilayear) {
        editor.putString(CFINANCIALYEAR, cfinancilayear);
        editor.commit();
    }

    public String getCfinancialyear() {
        return pref.getString(CFINANCIALYEAR, "");
    }

    public void setCbookyear(String cbookyear) {
        editor.putString(CBOOKYEAR, cbookyear);
        editor.commit();
    }

    public String getCbookyear() {
        return pref.getString(CBOOKYEAR, "");
    }

    public void setCcin(String ccin) {
        editor.putString(CCIN, ccin);
        editor.commit();
    }

    public String getCcin() {
        return pref.getString(CCIN, "");
    }

    public void setCpan(String cpan) {
        editor.putString(CPAN, cpan);
        editor.commit();
    }

    public String getCpan() {
        return pref.getString(CPAN, "");
    }

    public void setCaddress(String caddress) {
        editor.putString(CADDRESS, caddress);
        editor.commit();
    }

    public String getCaddress() {
        return pref.getString(CADDRESS, "");
    }

    public void setCcity(String ccity) {
        editor.putString(CCITY, ccity);
        editor.commit();
    }

    public String getCcity() {
        return pref.getString(CCITY, "");
    }

    public void setCcountry(String ccountry) {
        editor.putString(CCOUNTRY, ccountry);
        editor.commit();
    }

    public String getCcountry() {
        return pref.getString(CCOUNTRY, "");
    }

    public void setCstate(String cstate) {
        editor.putString(CSTATE, cstate);
        editor.commit();
    }

    public String getCstate() {
        return pref.getString(CSTATE, "");
    }

    public void setCindustrytype(String cindustrytype) {
        editor.putString(CINDUSTRYTYPE, cindustrytype);
        editor.commit();
    }

    public String getCindustrytype() {
        return pref.getString(CINDUSTRYTYPE, "");
    }

    public void setCward(String cward) {
        editor.putString(CWARD, cward);
        editor.commit();
    }

    public String getCward() {
        return pref.getString(CWARD, "");
    }

    public void setCfax(String cfax) {
        editor.putString(CFAX, cfax);
        editor.commit();
    }

    public String getCfax() {
        return pref.getString(CFAX, "");
    }

    public void setCemail(String cemail) {
        editor.putString(CEMAIL, cemail);
        editor.commit();
    }

    public String getCemail() {
        return pref.getString(CEMAIL, "");
    }

    public void setCgst(String cgst) {
        editor.putString(CGST, cgst);
        editor.commit();
    }

    public String getCgst() {
        return pref.getString(CGST, "");
    }

    public void setCdealer(String cdealer) {
        editor.putString(CDEALER, cdealer);
        editor.commit();
    }

    public String getCdealer() {
        return pref.getString(CDEALER, "");
    }

    public void setCtax1(String ctax1) {
        editor.putString(CTAX1, ctax1);
        editor.commit();
    }

    public String getCtax1() {
        return pref.getString(CTAX1, "");
    }

    public void setCtax2(String ctax2) {
        editor.putString(CTAX2, ctax2);
        editor.commit();
    }

    public String getCtax2() {
        return pref.getString(CTAX2, "");
    }

    public void setCsymbol(String csymbol) {
        editor.putString(CSYMBOL, csymbol);
        editor.commit();
    }

    public String getCsymbol() {
        return pref.getString(CSYMBOL, "");
    }

    public void setCstring(String cstring) {
        editor.putString(CSTRING, cstring);
        editor.commit();
    }

    public String getCstring() {
        return pref.getString(CSTRING, "");
    }

    public void setCsubstring(String csubstring) {
        editor.putString(CSUBSTRING, csubstring);
        editor.commit();
    }

    public String getCsubstring() {
        return pref.getString(CSUBSTRING, "");
    }

    public void setClogo(String clogo) {
        editor.putString(CLOGO, clogo);
        editor.commit();
    }

    public String getClogo() {
        return pref.getString(CLOGO, "");
    }

    public void setCsign(String csign) {
        editor.putString(CSIGN, csign);
        editor.commit();
    }

    public String getCsign() {
        return pref.getString(CSIGN, "");
    }

    public void setCusername(String cusername) {
        editor.putString(CUSERNAME, cusername);
        editor.commit();
    }

    public String getCusername() {
        return pref.getString(CUSERNAME, "");
    }

    public void setCpassword(String cpassword) {
        editor.putString(CPASSWORD, cpassword);
        editor.commit();
    }

    public String getCpassword() {
        return pref.getString(CPASSWORD, "");
    }


    public void setbill_sundry_amount_of_bill_sundry_fed_as(String setbillsundryamountofbillsundryfedas) {
        editor.putString(bill_sundry_amount_of_bill_sundry_fed_as, setbillsundryamountofbillsundryfedas);
        editor.commit();
    }

    public String getbill_sundry_amount_of_bill_sundry_fed_as() {
        return pref.getString(bill_sundry_amount_of_bill_sundry_fed_as, "");
    }

    public void setbill_sundry_of_percentage(String billsundryofpercentage) {
        editor.putString(bill_sundry_of_percentage, billsundryofpercentage);
        editor.commit();
    }

    public String getbill_sundry_of_percentage() {
        return pref.getString(bill_sundry_of_percentage, "");
    }

    public void setbill_sundry_number_of_bill_sundry(String billsundrynumberofbill_sundry) {
        editor.putString(bill_sundry_number_of_bill_sundry, billsundrynumberofbill_sundry);
        editor.commit();
    }

    public String getbill_sundry_number_of_bill_sundry() {
        return pref.getString(bill_sundry_number_of_bill_sundry, "");
    }

    public void setbill_sundry_consolidate_bill_sundry(String billsundryconsolidatebillsundry) {
        editor.putString(bill_sundry_consolidate_bill_sundry, billsundryconsolidatebillsundry);
        editor.commit();
    }

    public String getbill_sundry_consolidate_bill_sundry() {
        return pref.getString(bill_sundry_consolidate_bill_sundry, "");
    }

    public void setbill_sundry_calculated_on(String billsundrycalculatedon) {
        editor.putString(bill_sundry_calculated_on, billsundrycalculatedon);
        editor.commit();
    }

    public String getbill_sundry_calculated_on() {
        return pref.getString(bill_sundry_calculated_on, "");
    }

    public void setbill_sundry_amount_round_off(String billsundryamountroundoff) {
        editor.putString(bill_sundry_amount_round_off, billsundryamountroundoff);
        editor.commit();
    }

    public String getbill_sundry_amount_round_off() {
        return pref.getString(bill_sundry_amount_round_off, "");
    }

    public void setcalculated_on_bill_sundry_id(String billsundryroudingoffnearest) {
        editor.putString(calculated_on_bill_sundry_id, billsundryroudingoffnearest);
        editor.commit();
    }

    public String getcalculated_on_bill_sundry_id() {
        return pref.getString(calculated_on_bill_sundry_id, "");
    }

    public void setbill_sundry_rounding_off_limit(String billsundryroundingofflimit) {
        editor.putString(bill_sundry_rounding_off_limit, billsundryroundingofflimit);
        editor.commit();
    }

    public String getbill_sundry_rounding_off_limit() {
        return pref.getString(bill_sundry_rounding_off_limit, "");
    }

    public void setbill_sundry_type(String billsundrytype) {
        editor.putString(bill_sundry_type, billsundrytype);
        editor.commit();
    }

    public String getbill_sundry_type() {
        return pref.getString(bill_sundry_type, "");
    }

    public void setbill_sundry_rouding_off_nearest(String billsundryroudingoffnearest) {
        editor.putString(bill_sundry_rouding_off_nearest, billsundryroudingoffnearest);
        editor.commit();
    }

    public String getbill_sundry_rouding_off_nearest() {
        return pref.getString(bill_sundry_rouding_off_nearest, "");
    }

    public void setsale_affect_accounting(String saleaffectaccounting) {
        editor.putString(sale_affect_accounting, saleaffectaccounting);
        editor.commit();
    }

    public String getsale_affect_accounting() {
        return pref.getString(sale_affect_accounting, "");
    }

    public void setsale_affect_sale_amount(String saleaffectsaleamount) {
        editor.putString(sale_affect_sale_amount, saleaffectsaleamount);
        editor.commit();
    }

    public String getsale_affect_sale_amount() {
        return pref.getString(sale_affect_sale_amount, "");
    }

    public void setsale_affect_sale_amount_specify_in(String saleaffectsaleamountspecifyin) {
        editor.putString(sale_affect_sale_amount_specify_in, saleaffectsaleamountspecifyin);
        editor.commit();
    }

    public String getsale_affect_sale_amount_specify_in() {
        return pref.getString(sale_affect_sale_amount_specify_in, "");
    }

    public void setsale_adjust_in_party_amount(String saleadjustinpartyamount) {
        editor.putString(sale_adjust_in_party_amount, saleadjustinpartyamount);
        editor.commit();
    }

    public String getsale_adjust_in_party_amount() {
        return pref.getString(sale_adjust_in_party_amount, "");
    }

    public void setsale_party_amount_specify_in(String salepartyamountspecifyin) {
        editor.putString(sale_party_amount_specify_in, salepartyamountspecifyin);
        editor.commit();
    }

    public String getsale_party_amount_specify_in() {
        return pref.getString(sale_party_amount_specify_in, "");
    }

    public void setsale_account_head_to_post_party_amount(String saleaccountheadtopostpartyamount) {
        editor.putString(sale_account_head_to_post_party_amount, saleaccountheadtopostpartyamount);
        editor.commit();
    }

    public String getsale_account_head_to_post_party_amount() {
        return pref.getString(sale_account_head_to_post_party_amount, "");
    }

    public void setsale_account_head_to_post_sale_amount(String saleaccountheadtopostsaleamount) {
        editor.putString(sale_account_head_to_post_sale_amount, saleaccountheadtopostsaleamount);
        editor.commit();
    }

    public String getsale_account_head_to_post_sale_amount() {
        return pref.getString(sale_account_head_to_post_sale_amount, "");
    }

    public void setsale_post_over_above(String salepostoverabove) {
        editor.putString(sale_post_over_above, salepostoverabove);
        editor.commit();
    }

    public String getsale_post_over_above() {
        return pref.getString(sale_post_over_above, "");
    }

    public void setsale_account_head_to_post_party_amount_id(String saleaccountheadtopostpartyamountid) {
        editor.putString(sale_account_head_to_post_party_amount_id, saleaccountheadtopostpartyamountid);
        editor.commit();
    }

    public String getsale_account_head_to_post_party_amount_id() {
        return pref.getString(sale_account_head_to_post_party_amount_id, "");
    }

    public void setsale_account_head_to_post_sale_amount_id(String saleaccountheadtopostsaleamountid) {
        editor.putString(sale_account_head_to_post_sale_amount_id, saleaccountheadtopostsaleamountid);
        editor.commit();
    }

    public String getsale_account_head_to_post_sale_amount_id() {
        return pref.getString(sale_account_head_to_post_sale_amount_id, "");
    }


    public void setpurchase_affect_accounting(String purchaseaffectaccounting) {
        editor.putString(purchase_affect_accounting, purchaseaffectaccounting);
        editor.commit();
    }

    public String getpurchase_affect_accounting() {
        return pref.getString(purchase_affect_accounting, "");
    }

    public void setpurchase_affect_purchase_amount(String purchaseaffectpurchaseamount) {
        editor.putString(purchase_affect_purchase_amount, purchaseaffectpurchaseamount);
        editor.commit();
    }

    public String getpurchase_affect_purchase_amount() {
        return pref.getString(purchase_affect_purchase_amount, "");
    }

    public void setpurchase_affect_purchase_amount_specify_in(String purchaseaffectpurchaseamountspecifyin) {
        editor.putString(purchase_affect_purchase_amount_specify_in, purchaseaffectpurchaseamountspecifyin);
        editor.commit();
    }

    public String getpurchase_affect_purchase_amount_specify_in() {
        return pref.getString(purchase_affect_purchase_amount_specify_in, "");
    }

    public void setpurchase_adjust_in_party_amount(String purchaseadjustinpartyamount) {
        editor.putString(purchase_adjust_in_party_amount, purchaseadjustinpartyamount);
        editor.commit();
    }

    public String getpurchase_adjust_in_party_amount() {
        return pref.getString(purchase_adjust_in_party_amount, "");
    }

    public void setpurchase_party_amount_specify_in(String purchasepartyamountspecifyin) {
        editor.putString(purchase_party_amount_specify_in, purchasepartyamountspecifyin);
        editor.commit();
    }

    public String getpurchase_party_amount_specify_in() {
        return pref.getString(purchase_party_amount_specify_in, "");
    }

    public void setpurchase_account_head_to_post_party_amount(String purchaseaccountheadtopostpartyamount) {
        editor.putString(purchase_account_head_to_post_party_amount, purchaseaccountheadtopostpartyamount);
        editor.commit();
    }

    public String getpurchase_account_head_to_post_party_amount() {
        return pref.getString(purchase_account_head_to_post_party_amount, "");
    }

    public void setpurchase_account_head_to_post_purchase_amount(String purchaseaccountheadtopostsaleamount) {
        editor.putString(purchase_account_head_to_post_purchase_amount, purchaseaccountheadtopostsaleamount);
        editor.commit();
    }

    public String getpurchase_account_head_to_post_purchase_amount() {
        return pref.getString(purchase_account_head_to_post_purchase_amount, "");
    }

    public void setpurchase_post_over_above(String purchasepostoverabove) {
        editor.putString(purchase_post_over_above, purchasepostoverabove);
        editor.commit();
    }

    public String getpurchase_post_over_above() {
        return pref.getString(purchase_post_over_above, "");
    }

    public void setpurchase_account_head_to_post_party_amount_id(String purchaseaccountheadtopostpartyamountid) {
        editor.putString(purchase_account_head_to_post_party_amount_id, purchaseaccountheadtopostpartyamountid);
        editor.commit();
    }

    public String getpurchase_account_head_to_post_party_amount_id() {
        return pref.getString(purchase_account_head_to_post_party_amount_id, "");
    }

    public void setpurchase_account_head_to_post_purchase_amount_id(String purchaseaccountheadtopostpurchaseamountid) {
        editor.putString(purchase_account_head_to_post_purchase_amount_id, purchaseaccountheadtopostpurchaseamountid);
        editor.commit();
    }

    public String getpurchase_account_head_to_post_purchase_amount_id() {
        return pref.getString(purchase_account_head_to_post_purchase_amount_id, "");
    }


    public void setcost_goods_in_sale(String costgoodsinsale) {
        editor.putString(cost_goods_in_sale, costgoodsinsale);
        editor.commit();
    }

    public String getcost_goods_in_sale() {
        return pref.getString(cost_goods_in_sale, "");
    }

    public void setcost_goods_in_purchase(String costgoodsinpurchase) {
        editor.putString(cost_goods_in_purchase, costgoodsinpurchase);
        editor.commit();
    }

    public String getcost_goods_in_purchase() {
        return pref.getString(cost_goods_in_purchase, "");
    }

    public void setcost_material_issue(String costmaterialissue) {
        editor.putString(cost_material_issue, costmaterialissue);
        editor.commit();
    }

    public String getcost_material_issue() {
        return pref.getString(cost_material_issue, "");
    }

    public void setcost_material_receipt(String costmaterialreceipt) {
        editor.putString(cost_material_receipt, costmaterialreceipt);
        editor.commit();
    }

    public String getcost_material_receipt() {
        return pref.getString(cost_material_receipt, "");
    }

    public void setcost_stock_transfer(String coststocktransfer) {
        editor.putString(cost_stock_transfer, coststocktransfer);
        editor.commit();
    }

    public String getcost_stock_transfer() {
        return pref.getString(cost_stock_transfer, "");
    }

    public void setItem_stock_quantity(String itemstockquantity) {
        editor.putString(item_stock_quantity, itemstockquantity);
        editor.commit();
    }

    public String getItem_stock_quantity() {
        return pref.getString(item_stock_quantity, "");
    }

    public void setItem_stock_amount(String itemstockamount) {
        editor.putString(item_stock_amount, itemstockamount);
        editor.commit();
    }

    public String getItem_stock_amount() {
        return pref.getString(item_stock_amount, "");
    }

    public void setItem_stock_value(String itemstockvalue) {
        editor.putString(item_stock_value, itemstockvalue);
        editor.commit();
    }

    public String getItem_stock_value() {
        return pref.getString(item_stock_value, "");
    }
}
