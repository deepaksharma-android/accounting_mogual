package com.lkintechnology.mBilling.networks.api_response.journalvoucher;

import com.lkintechnology.mBilling.networks.api_response.payment.PaymentItemAttributes;

/**
 * Created by abc on 3/12/2018.
 */

public class DetailsJournalItemData {
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public JournalItemAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(JournalItemAttribute attributes) {
        this.attributes = attributes;
    }

    public void setId(String id) {
        this.id = id;
    }

 public JournalItemAttribute attributes;
    public String id;
}
