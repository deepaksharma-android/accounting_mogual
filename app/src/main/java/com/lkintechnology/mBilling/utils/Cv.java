package com.lkintechnology.mBilling.utils;


public interface Cv {







   //String BASE_URL = "https://mbilling.in/api/v1/";
    String BASE_URL = "http://accounts.geeksonrails.com/api/v1/";
     //String BASE_URL = "http://192.168.1.36:3000/api/v1/";


    int PERMISSIONS_BUZZ_REQUEST = 0xABC;
    String PREFS_APP_USER = "com.berylsystems.buzz.utils.app_user";
    String ACTION_LOGIN = "login_user";
    String ACTION_FACEBOOK_CHECK = "facebook_check";
    String ACTION_FORGOT_PASSWORD = "forgot_password";
    String ACTION_VERIFICATION = "verification";
    String ACTION_NEW_PASSWORD = "new_password";
    String ACTION_RESEND_OTP = "resend_otp";
    String ACTION_UPDATE_MOBILE_NUMBER = "update_mobile_number";
    String ACTION_REGISTER_USER = "register_user";
    String ACTION_UPDATE_USER = "update_user";
    String ACTION_CREATE_COMPANY = "create_company";
    String ACTION_CREATE_BASIC = "create_basic";
    String ACTION_VERSION = "show_version";
    String ACTION_CREATE_DETAILS = "company_details";
    String ACTION_CREATE_BANK_DETAILS = "company_bank_details";
    String ACTION_CREATE_GST = "company_gst";
    String ACTION_CREATE_INVOICE_FORMAT = "invoice_format";
    String ACTION_VOUCHER_SERIES = "voucher_series";
    String ACTION_CREATE_ADDITIONAL = "company_additional";
    String ACTION_CREATE_LOGO = "company_logo";
    String ACTION_CREATE_SIGNATURE = "company_signature";
    String ACTION_CREATE_LOGIN = "company_login";
    String ACTION_COMPANY_LIST = "company_list";
    String ACTION_DELETE_COMPANY = "delete_company";
    String ACTION_GET_PACKAGES = "get_packages";
    String ACTION_PLANS = "plans";
    String ACTION_GET_INDUSTRY = "get_industry";
    String ACTION_EDIT_LOGIN = "edit_login";
    String ACTION_CREATE_ACCOUNT_GROUP = "create_account_group";
    String ACTION_GET_ACCOUNT_GROUP = "get_account_group";
    String ACTION_GET_ACCOUNT_GROUP_DETAILS = "get_account_group_details";
    String ACTION_EDIT_ACCOUNT_GROUP = "edit_account_group";
    String ACTION_DELETE_ACCOUNT_GROUP = "delete_account_group";
    String ACTION_CREATE_ACCOUNT = "create_account";
    String ACTION_GET_ACCOUNT = "get_account";
    String ACTION_GET_ACCOUNT_DETAILS = "get_account_details";
    String ACTION_EDIT_ACCOUNT = "edit_account";
    String ACTION_DELETE_ACCOUNT = "delete_account";
    String ACTION_GET_MATERIAL_CENTRE_GROUP_LIST = "get_material_centre_group_list";
    String ACTION_CREATE_MATERIAL_CENTRE_GROUP = "create_material_centre_group";
    String ACTION_EDIT_MATERIAL_CENTRE_GROUP = "edit_material_centre_group";
    String ACTION_DELETE_MATERIAL_CENTRE_GROUP = "delete_material_centre_group";
    String ACTION_GET_MATERIAL_CENTRE_GROUP_DETAILS = "get_material_centre_group_details";
    String ACTION_GET_MATERIAL_CENTRE_LIST = "get_material_centre_list";
    String ACTION_CREATE_MATERIAL_CENTRE = "create_material_centre";
    String ACTION_EDIT_MATERIAL_CENTRE = "edit_material_centre";
    String ACTION_DELETE_MATERIAL_CENTRE = "delete_material_centre";
    String ACTION_GET_MATERIAL_CENTRE_DETAILS = "get_material_centre_details";
    String ACTION_GET_UNIT_LIST = "get_unit_list";
    String ACTION_CREATE_UNIT = "create_unit";
    String ACTION_EDIT_UNIT = "edit_unit";
    String ACTION_DELETE_UNIT = "delete_unit";
    String ACTION_GET_UNIT_DETAILS = "get_unit_details";
    String ACTION_GET_UNIT_CONVERSION_LIST = "get_unit_conversion_list";
    String ACTION_CREATE_UNIT_CONVERSION = "create_unit_conversion";
    String ACTION_EDIT_UNIT_CONVERSION = "edit_unit_conversion";
    String ACTION_DELETE_UNIT_CONVERSION = "delete_unit_conversion";
    String ACTION_GET_UNIT_CONVERSION_DETAILS = "get_unit_conversion_details";
    String ACTION_GET_STOCK = "get_stock";
    String ACTION_GET_UQC = "get_uqc";
    String ACTION_GET_ITEM = "action_get_item";
    String ACTION_GET_ITEM_MATERRIAL_CENTRE = "action_get_item_material_centre";
    String ACTION_CREATE_ITEM = "action_create_item";
    String ACTION_EDIT_ITEM = "action_edit_account";
    String ACTION_DELETE_ITEM = "action_delete_item";
    String ACTION_GET_ITEM_DETAILS = "action_get_item_details";
    String ACTION_GET_ITEM_GROUP = "action_get_item_group";
    String ACTION_CREATE_ITEM_GROUP = "action_create_item_group";
    String ACTION_DELETE_ITEM_GROUP = "action_delete_item_group";
    String ACTION_GET_ITEM_GROUP_DETAILS = "action_get_item_group_details";
    String ACTION_EDIT_ITEM_GROUP = "action_edit_item_group";
    String ACTION_GET_BILL_SUNDRY_LIST = "action_get_bill_sundry_list";
    String ACTION_CREATE_BILL_SUNDRY = "action_create_bill_sundry";
    String ACTION_DELETE_BILL_SUNDRY = "action_delete_bill_sundry";
    String ACTION_GET_BILL_SUNDRY_DETAILS = "action_get_bill_sundry_details";
    String ACTION_EDIT_BILL_SUNDRY = "action_edit_bill_sundry";
    String ACTION_GET_BILL_SUNDRY_NATURE = "action_get_bill_sundry_nature";
    String ACTION_COMPANY_AUTHENTICATE = "company_authenticate";
    String ACTION_GET_COMPANY = "get_company";
    String ACTION_SEARCH_COMPANY = "search_company";
    String ACTION_GET_COMPANY_USER = "get_company_user";
    String SERVICE_NAME = "NetworkingService";
    String TIMEOUT = "Something went wrong. Please try again";
    String KEY_FB_PARAMS = "fields";
    String FB_PARAMETERS = "id,email,first_name,last_name,birthday";
    String ACTION_GET_PURCHASE_TYPE = "action_get_purchase_type";
    String ACTION_GET_SALE_TYPE = "action_get_sale_type";
    String ACTION_GET_TAX_CATEGORY = "action_get_tax_category";
    String ACTION_CREATE_SALE_VOUCHER = "action_create_sale_voucher";
    String ACTION_GET_SALE_VOUCHER_LIST = "action_get_sale_voucher_list";
    String ACTION_DELETE_SALE_VOUCHER = "action_delete_sale_voucher";
    String ACTION_CREATE_BANK_CASH_DEPOSIT = "action_create_bank_cash_deposit";
    String ACTION_GET_BANK_CASH_DEPOSIT = "action_get_bank_cash_deposit";
    String ACTION_DELETE_BANK_CASH_DEPOSIT = "action_delete_bank_cash_deposit";
    String ACTION_GET_BANK_CASH_DEPOSIT_DETAILS = "action_get_bank_cash_deposit_details";
    String ACTION_EDIT_BANK_CASH_DEPOSIT = "action_edit_bank_cash_deposit";

    String ACTION_CREATE_PURCHASE = "action_create_purchase";
    String ACTION_ADD_ITEM_PURCHASE = "action_add_item_purchase";

    String ACTION_CREATE_BANK_CASH_WITHDRAW = "action_create_bank_cash_withdraw";
    String ACTION_GET_BANK_CASH_WITHDRAW = "action_get_bank_cash_withdraw";
    String ACTION_DELETE_BANK_CASH_WITHDRAW = "action_delete_bank_cash_withdraw";
    String ACTION_GET_BANK_CASH_WITHDRAW_DETAILS = "action_get_bank_cash_withdraw_details";
    String ACTION_EDIT_BANK_CASH_WITHDRAW = "action_edit_bank_cash_withdraw";

    String ACTION_CREATE_INCOME = "action_create_income";
    String ACTION_GET_INCOME = "action_get_income";
    String ACTION_DELETE_INCOME = "action_delete_income";
    String ACTION_GET_INCOME_DETAILS = "action_get_income_details";
    String ACTION_EDIT_INCOME = "action_edit_income";

    String ACTION_CREATE_EXPENCE = "action_create_expence";
    String ACTION_GET_EXPENCE = "action_get_expence";
    String ACTION_DELETE_EXPENCE = "action_delete_expence";
    String ACTION_GET_EXPENCE_DETAILS = "action_get_expence_details";
    String ACTION_EDIT_EXPENCE = "action_edit_expence";

    String ACTION_CREATE_PAYMENT = "action_create_payment";
    String ACTION_GET_PAYMENT = "action_get_payment";
    String ACTION_DELETE_PAYMENT = "action_delete_payment";
    String ACTION_GET_PAYMENT_DETAILS = "action_get_payment_details";
    String ACTION_EDIT_PAYMENT = "action_edit_payment";

    String ACTION_CREATE_JOURNAL_VOUCHER = "action_create_journal_voucher";
    String ACTION_GET_JOURNAL_VOUCHER = "action_get_journal_voucher";
    String ACTION_DELETE_JOURNAL_VOUCHER = "action_delete_journal_voucher";
    String ACTION_GET_JOURNAL_VOUCHER_DETAILS = "action_get_journal_voucher_details";
    String ACTION_EDIT_JOURNAL_VOUCHER = "action_edit_journal_voucher";

    String ACTION_CREATE_RECEIPT_VOUCHER = "action_create_receipt_voucher";
    String ACTION_GET_RECEIPT_VOUCHER = "action_get_receipt_voucher";
    String ACTION_DELETE_RECEIPT_VOUCHER = "action_delete_receipt_voucher";
    String ACTION_GET_RECEIPT_VOUCHER_DETAILS = "action_get_receipt_voucher_details";
    String ACTION_EDIT_RECEIPT_VOUCHER = "action_edit_receipt_voucher";

    String ACTION_CREATE_CREDIT_NOTE = "action_create_credit_note";
    String ACTION_GET_CREDIT_NOTE = "action_get_credit_note";
    String ACTION_DELETE_CREDIT_NOTE = "action_delete_credit_note";
    String ACTION_GET_CREDIT_NOTE_DETAILS = "action_get_credit_note_details";
    String ACTION_EDIT_CREDIT_NOTE = "action_edit_credit_note";

    String ACTION_CREATE_DEBIT_NOTE = "action_create_debit_note";
    String ACTION_GET_DEBIT_NOTE = "action_get_debit_note";
    String ACTION_DELETE_DEBIT_NOTE = "action_delete_debit_note";
    String ACTION_GET_DEBIT_NOTE_DETAILS = "action_get_debit_note_details";
    String ACTION_EDIT_DEBIT_NOTE = "action_edit_debit_note";
    String ACTION_CREATE_SALE_RETURN = "action_create_sale_return";
    String ACTION_GET_SALE_RETURN_VOUCHER = "action_get_sale_return_voucher";
    String ACTION_DELETE_SALE_RETURN_VOUCHER = "action_delete_sale_return_voucher";
    String ACTION_CREATE_PURCHASE_RETURN = "action_create_purchase_return";
    String ACTION_GET_PURCHASE_RETURN_VOUCHER = "action_get_purchase_return_voucher";
    String ACTION_DELETE_PURCHASE_RETURN_VOUCHER = "action_delete_purchase_return_voucher";
    String ACTION_GET_COMPANY_DASHBOARD_INFO = "action_get_company_dashboard_details";
    String ACTION_GET_TRANSACTION_PDF = "action_get_transaction_pdf";
    String ACTION_GET_ITEM_WISE_REPORT = "action_get_item_wise_report";
    String ACTION_GET_VOUCHER_NUMBERS= "action_get_voucher_numbers";

    String  ACTION_GET_SALE_VOUCHER="action_get_sale_report";
    String  ACTION_GET_PURCHASE_VOUCHER="action_get_purchase_report";
    String  ACTION_DELETE_PURCHASE_VOUCHER="action_delete_purchase_voucher";
    String  ACTION_GET_PDC="action_get_pdc";
    String ACTION_GET_DEFAULT_ITEMS = "action_get_default_items";
    String ACTION_GET_SALE_VOUCHER_DETAILS = "action_get_sale_voucher_details";
    String ACTION_GET_SALE_RETURN_VOUCHER_DETAILS = "action_get_sale_return_voucher_details";
    String ACTION_GET_PURCHASE_VOUCHER_DETAILS = "action_get_purchase_voucher_details";
    String ACTION_GET_PURCHASE_RETURN_VOUCHER_DETAILS = "action_get_purchase_return_voucher_details";
    String ACTION_CREATE_DEFAULT_ITEMS = "action_create_default_items";
    String ACTION_GET_GST_REPORT = "action_get_gst_report";
    String ACTION_GET_GST_REPORT_PURCHASE= "action_get_gst_report_purchase";
    String ACTION_UPDATE_SALE_VOUCHER_DETAILS = "action_update_sale_voucher_details";
    String ACTION_UPDATE_SALE_RETURN_VOUCHER_DETAILS = "action_update_sale_return_voucher_details";
    String ACTION_UPDATE_PURCHASE_VOUCHER_DETAILS = "action_update_purchase_voucher_details";
    String ACTION_UPDATE_PURCHASE_RETURN_VOUCHER_DETAILS = "action_update_purchase_return_voucher_details";
    String ACTION_GET_PROFIT_AND_LOSS = "action_get_profit_and_loss";
    String ACTION_GET_GSTR3B = "action_get_gstr3b";
    String ACTION_GET_GSTR_1 = "action_get_gstr_1";
    String ACTION_GET_GSTR_2 = "action_get_gstr_2";
    String ACTION_GET_CHECK_BARCODE = "action_get_check_barcode";
    String ACTION_CREATE_STOCK_TRANSFER = "action_create_stock_transfer";
    String ACTION_CREATE_AUTHORIZATION_SETTINGS = "action_create_authorization_settings";
   // String ACTION_UPDATE_AUTHORIZATION_SETTINGS = "action_update_authorization_settings";
    String ACTION_GET_STOCK_TRANSFER = "action_get_stock_transfer";
    String ACTION_DELETE_STOCK_TRANSFER = "action_delete_stock_transfer";
    String ACTION_GET_STOCK_TRANSFER_DETAILS = "action_get_stock_transfer_details";
    String ACTION_UPDATE_STOCK_TRANSFER = "action_update_stock_transfer";
    String ACTION_GET_SERIAL_NUMBER_REFERENCE= "action_get_serial_number_reference";
    String ACTION_GET_PDF= "action_get_pdf";
    String ACTION_GET_BALANCE_SHEET_PDF = "action_get_balance_sheet_pdf";
    String ACTION_GET_SALE_VOUCHERS_ITEM = "action_get_sale_vouchers_item";
    String ACTION_GET_SALE_VOUCHERS_ITEM_DETAILS= "action_get_sale_voucher_item_details";
    String ACTION_GET_PURCHASE_VOUCHERS_ITEM = "action_get_purchase_vouchers_item";
    String ACTION_GET_PURCHASE_VOUCHERS_ITEM_DETAILS= "action_get_purchase_voucher_item_details";

    int REQUEST_CAMERA = 0xABBA;
    int REQUEST_GALLERY = 0xBABA;

}
