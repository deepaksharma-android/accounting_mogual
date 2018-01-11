package com.lkintechnology.mBilling.networks.api_response.company;

import java.util.ArrayList;

public class Company {


    public ArrayList<CompanyData> getData() {
        return data;
    }

    public void setData(ArrayList<CompanyData> data) {
        this.data = data;
    }

    public ArrayList<CompanyData>data;
}