package com.lkintechnology.mBilling.entities;


import com.lkintechnology.mBilling.networks.api_response.bill_sundry.BillSundryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUser {
    public String auth_token;
    public String mobile;
    public String password;
    public String name;
    public String email;
    public String zipcode;
    public String device_type="Android";
    public String salesmanmobile;
    public String user_id;
    public String otp;
    public String fb_id;
    public String company_name;
    public String print_name;
    public String short_name;
    public String city;
    public String logo="";
    public String signature="";
    public String companyUserName;
    public String companyUserPassword;
    public String companymobile;
    public String country;
    public String state;
    public String address;
    public String financial_year_from;
    public String books_commencing_from;
    public String cin;
    public String it_pin;
    public String comapny_phone_number;
    public String ward;
    public String fax;
    public String company_email;
    public String currency_information;
    public String type_of_dealer;
    public String gst;
    public String currency_symbol;
    public String currenyString;
    public String currenySubString;
    public String cid;
    public String account_master_group;
    public ArrayList<String> cname=new ArrayList<>();
    public ArrayList<String> industry_type=new ArrayList<>();
    public ArrayList<Integer> industry_id=new ArrayList<>();
    public ArrayList<String> group_name=new ArrayList<>();
    public ArrayList<Integer> group_id=new ArrayList<>();
    public ArrayList<String> arr_account_group_name=new ArrayList<>();
    public ArrayList<String> arr_con_type=new ArrayList<>();
    public ArrayList<Integer> arr_account_group_id=new ArrayList<>();
    public ArrayList<String> arr_item_group_name=new ArrayList<>();
    public ArrayList<Integer> arr_item_group_id=new ArrayList<>();
    public ArrayList<String> group_name1=new ArrayList<>();
    public ArrayList<Integer> group_id1=new ArrayList<>();
    public ArrayList<String> arr_uqcname=new ArrayList<>();
    public ArrayList<Integer> arr_uqcid=new ArrayList<>();
    public String uqc_id;
    public String industryId;
    public String default_tax_rate1;
    public String default_tax_rate2;
    public String company_id;
    public String cusername;
    public String cpassword;
    public String titlecname = "";
    public String search_company_id;
    public String company_user_id;
    public String company_logo="";
    public String company_state;

    public String account_group_id;
    public String item_group_id;
    public String item_group_name;
    public String account_name;
    public String acount_opening_balance;
    public String acount_opening_balance_type;
    public String prev_year_balance;
    public String prev_year_balance_type;
    public String credit_days_for_sale;
    public String credit_days_for_purchase;
    public String bank_account_number;
    public String bank_ifsc_code;
    public String bank_name;
    public String serial_voucher_type;
    public String serial_voucher_id;
    public String account_id;
    public String delete_group_id;
    public String delete_account_id;
    public String edit_group_id;
    public String edit_group_id1;
    public String delete_item_group_id;
    public String account_group_from_group_list="";
    public String account_amount_receivable="";
    public String account_amount_payable="";
    public String account_address="";
    public String account_city="";
    public String account_state="";
    public String account_pinCode="";
    public String account_gst="";
    public String account_type_of_dealer="";
    public String account_aadhaar="";
    public String account_pan="";
    public String account_credit_limit="";
    public String account_credit_sale="";
    public String account_credit_purchase="";
    public String account_mobile_number;
    public String account_email;
    public String create_account_group_id;
    public String account_group_name;
    public String edit_account_id;
    public String package_id;
    public String package_amount;
    public String package_serail_number;
    public String package_mode;
    public String material_centre_name="";
    public String material_centre_group_id="";
    public String material_centre_account_id="";
    public String material_centre_address="";
    public String material_centre_group_name="";
    public String edit_material_centre_group_id;
    public String delete_material_centre_group_id;
    public String edit_material_centre_id;
    public String delete_material_centre_id;
    public ArrayList<String> unitConversionId=new ArrayList<>();
    public ArrayList<String> unitConversionUnitName=new ArrayList<>();
    public ArrayList<String> unitConversionSubUnitName=new ArrayList<>();
    public ArrayList<String> arr_unitConversionId=new ArrayList<>();
    public ArrayList<String> arr_unitlistConversionId=new ArrayList<>();
    public ArrayList<String> arr_unitConversionUnitName=new ArrayList<>();
    public ArrayList<String> arr_unitConversionSubUnitName=new ArrayList<>();
    public ArrayList<String> materialCentreGroupId=new ArrayList<>();
    public ArrayList<String> materialCentreGroupName=new ArrayList<>();
    public ArrayList<String> arr_materialCentreGroupId=new ArrayList<>();
    public ArrayList<String> arr_materialCentreGroupName=new ArrayList<>();
    public ArrayList<String> arr_unitId=new ArrayList<>();
    public ArrayList<String> arr_unitName=new ArrayList<>();
    public ArrayList<String> arr_billSundryName=new ArrayList<>();
    public ArrayList<String> billSundryName=new ArrayList<>();
    public ArrayList<String> arr_billSundryId=new ArrayList<>();
    public ArrayList<String> billSundryId=new ArrayList<>();
    public ArrayList<String> arr_bill_sundry_nature_id=new ArrayList<>();
    public ArrayList<String> arr_bill_sundry_nature_name=new ArrayList<>();
    public ArrayList<String> arr_tax_category_id=new ArrayList<>();
    public ArrayList<String> arr_tax_category_name=new ArrayList<>();
    public ArrayList<String> arr_subunitName=new ArrayList<>();
    public ArrayList<String> unitId=new ArrayList<>();
    public ArrayList<String> unitName=new ArrayList<>();
    public ArrayList<String> subunitName=new ArrayList<>();
    public String material_centre_city;
    public String stock_id;
    public ArrayList<String> arr_stock_name=new ArrayList<>();
    public ArrayList<String> arr_stock_id=new ArrayList<>();

    public String delete_unit_id;
    public String edit_unit_id;
    public String delete_unit_conversion_id;
    public String edit_unit_conversion_id;
    public String uqc;
    public String unit_name;
    public String confactor;
    public String unit_conversion_main_unit;
    public String unit_conversion_sub_unit;
    public String main_unit_id;
    public String sub_unit_id;

    public String item_name;
    public String item_company_id="";
    public String item_item_group_id="";
    public String item_unit_id;
    public String item_unit_name;
    public String item_stock_quantity="";
    public String item_stock_amount="";
    public String item_alternate_unit_id;
   // public String item_alternate_unit_name;
    /* public String barcode;*/
    public String item_conversion_factor;
    public String item_conversion_type;
    public String item_opening_stock_quantity_alternate;
    public String item_package_unit_detail_name;
    public String item_price_info_id="";
    public String item_package_unit_detail_id;
	 public String item_default_unit_for_sales;
    public String item_default_unit_for_purchase;
    public int item_tax_category;
    public String item_tax_category_name;
    public String item_hsn_number;
    public String item_description;
    public String item_serial_number_wise_detail;
    public String item_batch_wise_detail;
    public String item_set_critical_level;
    public String item_critical_level_id="";
    public String item_parameterized_detail="";
    public String item_exp_month_date_required="";
    public String item_dont_maintain_stock_balance;
    public String item_specify_purchase_account;
    public String item_settings_specify_purchase_account;

    public String item_settings_alternate_unit;
    public String item_specify_sales_account;
    public String item_price_info_sale_price_applied_on;
    public String item_price_info_purchase_price_applied_on;
    public String item_price_info_sales_price_edittext;
    public String item_price_info_sale_price_alt_unit_edittext;
    public String item_price_info_min_sale_price_alt_edittext;
    public String item_price_info_purchase_price_min_edittext;
    public String item_price_info_purchase_price_alt_edittext;
    public String item_price_mrp;
    public String item_price_info_self_val_price;
    public String item_price_info_min_sale_price_main_edittext;
    public String item_setting_critical_min_level_qty;
    public String item_setting_critical_recorded_level_qty;
    public String item_setting_critical_max_level_qty;
    public String item_setting_critical_min_level_days;
    public String item_setting_critical_recorded_level_days;
    public String item_setting_critical_max_level_days;





    public String item_stock_value="";
    public String item_stock_quantity_alternate="";
    public String item_conversion_factor_pkg_unit;
    public String item_salse_price;

    public String edit_item_id="";

    public String bill_sundry_name="";
    public String bill_sundry_nature="";
    public Double bill_sundry_default_value;
    public String bill_sundry_amount_of_bill_sundry_fed_as = "";
    public String bill_sundry_of_percentage = "";
    public String bill_sundry_number_of_bill_sundry = "";
    public String bill_sundry_consolidate_bill_sundry = "";
    public String bill_sundry_calculated_on = "";
    public String bill_sundry_amount_round_off = "";
    public String bill_sundry_rouding_off_nearest = "";
    public String bill_sundry_type = "";
    public String sale_affect_accounting = "";
    public String sale_affect_sale_amount = "";
    public String sale_affect_sale_amount_specify_in = "";
    public String sale_adjust_in_party_amount = "";
    public String sale_party_amount_specify_in = "";
    public String sale_account_head_to_post_party_amount = "";
    public String sale_account_head_to_post_sale_amount = "";
    public String sale_post_over_above = "";
    public String purchase_affect_accounting = "";
    public String purchase_affect_purchase_amount = "";
    public String purchase_affect_purchase_amount_specify_in = "";
    public String purchase_account_head_to_post_purchase_amount = "";
    public String purchase_adjust_in_party_amount = "";

    public String purchase_party_amount_specify_in = "";
    public String purchase_account_head_to_post_party_amount = "";
    public String purchase_post_over_above = "";
    public String cost_goods_in_sale = "";
    public String cost_goods_in_purchase = "";
    public String cost_material_issue = "";
    public String cost_material_receipt = "";
    public String cost_stock_transfer = "";

    public String edit_bill_sundry_id;
    public String delete_bill_sundry_id;
    public String delete_item_id;
    public String item_alternate_unit_name = "";

    public String sale_series = "";
    public String sale_date = "";
    public String sale_vchNo = "";
    public String sale_saleType = "";
    public String sale_store = "";
    public String sale_store_to = "";
    public String sale_partyName = "";
    public String sale_party_group="";
    public String sale_mobileNumber = "";
    public String sale_cash_credit;
    public String sale_narration = "";
    public String sale_attachment = "";
    public String sale_return_attachment = "";
    public String purchase_attachment = "";
    public String gst_nature_purchase = "";
    public String purchase_return_attachment = "";
    public String sale_type_name="";

    public String purchase_type_name="";
    public String stock_type_id="";
    public String stock_type_name="";
    public String childId = "";
    public List<Map> mListMapForItemSale = new ArrayList();
    public List<Map> mListMapForItemCreditNote = new ArrayList();
    public List<Map> mListMapForItemDebitNote=new ArrayList<>();

    public List<Map<String, String>> mListMapForBillSale = new ArrayList();
    public Map<String,String> mMapSaleVoucherBill=new HashMap<>();
    public Map<String,String> mMapSaleVoucherItem=new HashMap<>();

public List<Map> mListMapForItemSaleReturn = new ArrayList();
    public List<Map<String, String>> mListMapForBillSaleReturn = new ArrayList();
    public Map<String,String> mMapSaleReturnBill=new HashMap<>();
    public Map<String,String> mMapSaleReturnItem=new HashMap<>();

    public Map<String,String> mMapPurchaseVoucherItem=new HashMap<>();
    public Map<String,String> mMapPurchaseReturnItem=new HashMap<>();

    public List<Map> mListMapForItemPurchase = new ArrayList();
    public List<Map<String, String>> mListMapForBillPurchase = new ArrayList();



    public String sale_return_series = "";
    public String sale_return_date = "";
    public String sale_return_vchNo = "";
    public String sale_return_saleType = "";
    public String sale_return_store = "";
    public String sale_return_partyName = "";
    public String sale_return_mobileNumber = "";
    public String sale_return_cash_credit = "";
    public String sale_return_narration = "";

    public List<Map> mListMapForItemPurchaseReturn = new ArrayList();
    public List<Map<String, String>> mListMapForBillPurchaseReturn = new ArrayList();




    public Map map = new HashMap();

    public String deposit_to_id="";
    public String deposit_by_id="";
    public String deposit_to_name="";
    public String deposit_by_name="";
    public String bank_cash_deposit_voucher_series="";
    public String bank_cash_deposit_date;
    public String bank_cash_deposit_voucher_no="";
    public Double bank_cash_deposit_amount;
    public String bank_cash_deposit_narration="";
    public String bank_cash_deposit_attachment="";
    public String delete_bank_cash_deposit_id;
    public String delete_sale_voucher_id;
    public String delete_sale_return_voucher_id;
    public String delete_purchase_voucher_id;
    public String delete_stock_transfer_id;
    public String delete_purchase_return_voucher_id;
    public String edit_bank_cash_deposit_id;
    public ArrayList<String> serial_arr=new ArrayList<>();
    public ArrayList<String> stock_serial_arr=new ArrayList<>();
    public ArrayList<String> stock_item_serail_arr=new ArrayList<>();
    public ArrayList<String> purchase_item_serail_arr=new ArrayList<>();
    public ArrayList<String> sale_item_serial_arr=new ArrayList<>();
    public ArrayList<BillSundryData> billSundryData = new ArrayList<>();
    public ArrayList<BillSundryData> SalebillSundryData = new ArrayList<>();
    public ArrayList<String> billsundrytotal=new ArrayList<>();
    public ArrayList<String> billsundrytotalPurchase=new ArrayList<>();
    public ArrayList<String> itemtotal=new ArrayList<>();
    public ArrayList<String> itemtotalPurchase=new ArrayList<>();
    public ArrayList<String> unitlist=new ArrayList<>();
    public String item_id;
    public String purchase_date = "";
    public String purchase_voucher_series="";
    public String purchase_company_id="";
    public String purchase_voucher_number="";
    public String purchase_puchase_type_id="";
    public String purchase_payment_type="";
    public String purchase_mobile_number="";
    public String purchase_account_master_id="";
    public String purchase_material_center_id="";
    public String stock_from_material_center_id="";
    public String stock_item_material_center_id="";
    public String stock_item_material_center="";
    public String stock_to_material_center_id="";
    public String purchase_itc_eligibility="";
    public String purchase_narration = "";
    public String purchase_created_at="";
    public String purchase_update_at="";

    public String withdraw_from_id;
    public String withdraw_by_id;
    public String withdraw_from_name;
    public String withdraw_by_name;
    public String bank_cash_withdraw_voucher_series="";
    public String bank_cash_withdraw_date;
    public String bank_cash_withdraw_voucher_no="";
    public Double bank_cash_withdraw_amount;
    public String bank_cash_withdraw_narration="";
    public String bank_cash_withdraw_attachment="";
    public String delete_bank_cash_withdraw_id;
    public String edit_bank_cash_withdraw_id;

    public String received_into_id;
    public String received_from_id;
    public String received_into_name;
    public String received_from_name;
    public String income_voucher_series="";
    public String income_date;
    public String income_voucher_no="";
    public Double income_amount;
    public String income_narration="";
    public String income_attachment="";
    public String delete_income_id;
    public String edit_income_id;

    public String paid_from_id;
    public String paid_to_id;
    public String paid_from_name;
    public String paid_to_name;
    public String expence_voucher_series="";
    public String expence_date;
    public String expence_voucher_no="";
    public Double expence_amount;
    public String expence_narration="";
    public String expence_attachment="";
    public String delete_expence_id;
	public String edit_expence_id;
    public String bill_sundry_fed_as="";
    public String bill_sundry_sale_voucher_type="";

    public String account_name_debit_id;
    public String account_name_credit_id;
    public String account_name_debit_name;
    public String account_name_credit_name;
    public String journal_voucher_voucher_series="";
    public String journal_voucher_date;
    public String journal_voucher_gst_nature="";
    public String journal_voucher_gst_nature_description="";
    public String journal_voucher_voucher_no="";
    public Double journal_voucher_amount;
    public String journal_voucher_narration="";
    public String journal_voucher_attachment="";
    public String delete_journal_voucher_id;
    public String edit_journal_voucher_id;

    public String receipt_received_from_id;
    public String receipt_received_by_id;
    public String receipt_received_from_name;
    public String receipt_received_by_name;
    public String receipt_voucher_series="";
    public String receipt_date;
    public String receipt_date_pdc;
    public String receipt_type="";
    public String receipt_gst_nature="";
    public String receipt_gst_nature_description="";
    public String receipt_voucher_no="";
    public Double receipt_amount;
    public String receipt_narration="";
    public String receipt_attachment="";
    public String delete_receipt_id;
    public String edit_receipt_id;

    public String account_name_credit_note_id;
    public String account_name_debit_note="";
    public String credit_note_voucher_series="";
    public String credit_note_date;
    public String credit_note_gst_nature="";
    public String credit_note_gst_nature_description="";
    public String credit_note_voucher_no="";
    public Double credit_note_amount;
    public String credit_note_narration="";
    public String credit_note_attachment="";
    public String delete_credit_note_id;
    public String edit_credit_note_id;

    public String account_name_debit_note_id;
    public String account_name_credit_note="";
    public String debit_note_voucher_series="";
    public String debit_note_date;
    public String debit_note_gst_nature="";
    public String debit_note_gst_nature_description="";
    public String debit_note_voucher_no="";
    public Double debit_note_amount;
    public String debit_note_narration="";
    public String debit_note_attachment="";
    public String delete_debit_note_id;
    public String edit_debit_note_id;

    public String payment_paid_to_id;
    public String payment_paid_from_id;
    public String payment_paid_to_name;
    public String payment_paid_from_name;
    public String payment_voucher_series="";
    public String payment_date;
    public String payment_date_pdc="";
    public String payment_type;
    public String payment_gst_nature="";
    public String payment_gst_nature_description="";
    public String payment_voucher_no="";
    public Double payment_amount;
    public String payment_narration="";
    public String payment_attachment="";
    public String delete_payment_id;
    public String edit_payment_id;

    public ArrayList<Map> companyLoginArray=new ArrayList();
    public Boolean boolSetOrAddtoMap;

    public String totalamount;
    public String items_amount;
    public String bill_sundries_amount;

    public String pdf_account_id;
    public String pdf_start_date;
    public String pdf_end_date;

    public String voucher_type;
    public String sales_duration_spinner;
    public String expenses_duration_spinner;
    public String income_duration_spinner;
    public String bank_cash_deposit_duration_spinner;
    public String bank_cash_withdraw_duration_spinner;
    public String payment_duration_spinner;
    public String receipt_duration_spinner;
    public String journal_voucher_duration_spinner;
    public String debit_note_duration_spinner;
    public String credit_note_duration_spinner;
    public List<Map> mListMapForItemReceipt = new ArrayList();
    public String email_yes_no="";
    public String edit_sale_voucher_id;
    public String edit_stock_transfer_id="";

    public String enable_user="";
    public String allow_user_to_delete_voucher="";
    public String allow_user_to_edit_voucher="";
    public int authorizations__setting_user_id;
    public String serial_ref_no;
    public Map<String,String> transport_details =new HashMap<>();
   // public String forAccountIntentName="";
   // public String forAccountIntentId="";
    //public boolean forAccountIntentBool;


}
