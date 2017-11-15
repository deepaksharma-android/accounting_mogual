package com.berylsystems.buzz.networks.api_response.materialcentre;

import java.util.ArrayList;

public class OrderedMaterialCentre {
    public String group_name;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public ArrayList<Data> data;
}