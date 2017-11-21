package com.berylsystems.buzz.networks.api_response.taxcategory;

import java.util.ArrayList;

public class Tax_Category {

    public ArrayList<TaxCategoryData> data;

    public ArrayList<TaxCategoryData> getData() {
        return data;
    }

    public void setData(ArrayList<TaxCategoryData> data) {
        this.data = data;
    }
}