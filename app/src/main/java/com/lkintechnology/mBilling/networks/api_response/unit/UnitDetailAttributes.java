package com.lkintechnology.mBilling.networks.api_response.unit;

public class UnitDetailAttributes {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUqc() {
        return uqc;
    }

    public void setUqc(String uqc) {
        this.uqc = uqc;
    }

    public String name;
    public String uqc;

    public int getUqc_id() {
        return uqc_id;
    }

    public void setUqc_id(int uqc_id) {
        this.uqc_id = uqc_id;
    }

    public int uqc_id;

}