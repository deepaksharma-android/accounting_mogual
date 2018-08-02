package com.lkintechnology.mBilling.networks.api_response.voucherseries;

/**
 * Created by SAMSUNG on 8/2/2018.
 */

public class Attributes {
    public String name;
    public boolean defaults;
    public boolean auto_increment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefaults() {
        return defaults;
    }

    public void setDefaults(boolean defaults) {
        this.defaults = defaults;
    }

    public boolean isAuto_increment() {
        return auto_increment;
    }

    public void setAuto_increment(boolean auto_increment) {
        this.auto_increment = auto_increment;
    }

    public String getVoucher_number() {
        return voucher_number;
    }

    public void setVoucher_number(String voucher_number) {
        this.voucher_number = voucher_number;
    }

    public String voucher_number;



}
