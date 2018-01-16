package com.lkintechnology.mBilling.networks.api_response.defaultitems;

import java.util.ArrayList;

public class Attributes {
    public String name;
    public ArrayList<Items> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }
}