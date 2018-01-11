package com.lkintechnology.mBilling.networks.api_response.account;

import java.util.ArrayList;

public class OrderedAccounts {
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }


    public String group_name;

    public ArrayList<AccountData> getData() {
        return data;
    }

    public void setData(ArrayList<AccountData> data) {
        this.data = data;
    }

    public ArrayList<AccountData> data;
}