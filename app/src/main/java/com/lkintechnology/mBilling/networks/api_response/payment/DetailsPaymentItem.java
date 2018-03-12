package com.lkintechnology.mBilling.networks.api_response.payment;

import java.util.ArrayList;

/**
 * Created by abc on 3/12/2018.
 */

public class DetailsPaymentItem {
    public ArrayList<DetailsPaymentItemData> data;

    public ArrayList<DetailsPaymentItemData> getData() {
        return data;
    }

    public void setData(ArrayList<DetailsPaymentItemData> data) {
        this.data = data;
    }
}
