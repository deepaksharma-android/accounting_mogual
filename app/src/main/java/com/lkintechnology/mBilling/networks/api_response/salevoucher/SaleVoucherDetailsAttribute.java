package com.lkintechnology.mBilling.networks.api_response.salevoucher;

import java.util.ArrayList;

public class SaleVoucherDetailsAttribute {
    public String voucher_series;
    public int company_id;
    public String sale_type;
    public String account_master;
    public String material_center;
    public ArrayList<SaleVoucherDetailsItemList> voucher_items;
    public ArrayList<SaleVoucherDetailsBillSundry> voucher_bill_sundries;
}