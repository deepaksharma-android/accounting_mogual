package com.lkintechnology.mBilling.networks.api_response.item;

import java.util.ArrayList;

public class OrderedItems {

    public String group_name;
    public ArrayList<Data> data;

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
}