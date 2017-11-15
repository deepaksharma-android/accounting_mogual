package com.berylsystems.buzz.networks.api_response.materialcentregroup;

import java.util.ArrayList;

public class MaterialCentreGroupsList {
    public ArrayList<MaterialCentreGroupListData> getData() {
        return data;
    }

    public void setData(ArrayList<MaterialCentreGroupListData> data) {
        this.data = data;
    }

    public ArrayList<MaterialCentreGroupListData> data;
}