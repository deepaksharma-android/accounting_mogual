package com.berylsystems.buzz.networks.api_response.account;

import java.util.ArrayList;

public class OrderedAccounts {
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String group_name;
    public ArrayList<Data> data;
}