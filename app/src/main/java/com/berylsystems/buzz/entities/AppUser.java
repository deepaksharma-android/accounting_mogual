package com.berylsystems.buzz.entities;


import java.util.ArrayList;

public class AppUser {
    public String auth_token;
    public String mobile;
    public String password;
    public String name;
    public String email;
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
    public ArrayList<String> cname=new ArrayList<>();
    public ArrayList<String> industry_type=new ArrayList<>();
    public ArrayList<Integer> industry_id=new ArrayList<>();
    public ArrayList<String> group_name=new ArrayList<>();
    public ArrayList<Integer> group_id=new ArrayList<>();
    public ArrayList<String> arr_account_group_name=new ArrayList<>();
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

    public String account_group_id;
    public String item_group_id;
    public String item_group_name;
    public String account_name;
    public String account_type_of_dealer;
    public String acount_opening_balance;
    public String acount_opening_balance_type;
    public String prev_year_balance;
    public String prev_year_balance_type;
    public String credit_days_for_sale;
    public String credit_days_for_purchase;
    public String bank_account_number;
    public String bank_ifsc_code;
    public String bank_name;
    public String account_id;
    public String delete_group_id;
    public String delete_account_id;
    public String edit_group_id;
    public String edit_group_id1;
    public String account_group_from_group_list="";
    public String account_amount_receivable="";
    public String account_amount_payable="";
    public String account_address="";
    public String account_city="";
    public String account_state="";
    public String account_gst="";
    public String account_aadhaar="";
    public String account_pan="";
    public String account_credit_limit="";
    public String account_credit_sale="";
    public String account_credit_purchase="";
    public String account_mobile_number;
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
    public String main_unit_id;
    public String sub_unit_id;
	 public String item_id="";
    public String item_name="";
    public String item_company_id="";
    public String item_item_group_id="";
    public String item_unit_id="";
    public String item_stock_quantity="";
    public String item_stock_amount="";
    public String item_alternate_unit_id;
    public String item_alternate_unit_name;
    /* public String barcode;*/
    public String item_conversion_factor;
    public String item_conversion_type;
    public String item_opening_stock_quantity_alternate;
    public String item_package_unit_detail_name="";
    public String item_price_info_id="";
    public String item_package_unit_detail_id="";
	 public String item_default_unit_for_sales;
    public String item_default_unit_for_purchase;
    public int item_tax_category;
    public String item_hsn_number="";
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
    public String item_settings_mrp;
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
    public String bill_sundry_type="";
    public String edit_bill_sundry_id;
    public String delete_bill_sundry_id;
    public String delete_item_id;








}
