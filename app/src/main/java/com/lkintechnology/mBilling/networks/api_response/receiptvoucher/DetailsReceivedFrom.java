package com.lkintechnology.mBilling.networks.api_response.receiptvoucher;

/**
 * Created by abc on 3/12/2018.
 */

public class DetailsReceivedFrom {
    public int id;
    public String name;
    public String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
