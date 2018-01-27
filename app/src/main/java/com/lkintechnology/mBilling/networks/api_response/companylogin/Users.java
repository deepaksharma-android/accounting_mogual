package com.lkintechnology.mBilling.networks.api_response.companylogin;

public class Users {
    public int id;
    public String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getAllow_delete_voucher() {
        return allow_delete_voucher;
    }

    public void setAllow_delete_voucher(Boolean allow_delete_voucher) {
        this.allow_delete_voucher = allow_delete_voucher;
    }

    public Boolean getAllow_edit_voucher() {
        return allow_edit_voucher;
    }

    public void setAllow_edit_voucher(Boolean allow_edit_voucher) {
        this.allow_edit_voucher = allow_edit_voucher;
    }

    public Boolean admin;
    public int company_id;
    public String phone_number;
    public Boolean enable;
    public Boolean allow_delete_voucher;
    public Boolean allow_edit_voucher;








}