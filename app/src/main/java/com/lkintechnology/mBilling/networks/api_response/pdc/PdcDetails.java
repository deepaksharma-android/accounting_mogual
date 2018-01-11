package com.lkintechnology.mBilling.networks.api_response.pdc;

import java.util.ArrayList;

/**
 * Created by BerylSystems on 27-Dec-17.
 */

public class PdcDetails {
    ArrayList<Data> data=new ArrayList<>();

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
