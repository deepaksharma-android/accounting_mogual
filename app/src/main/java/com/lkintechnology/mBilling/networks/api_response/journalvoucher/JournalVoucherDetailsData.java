package com.lkintechnology.mBilling.networks.api_response.journalvoucher;

public class JournalVoucherDetailsData {

    public String type;
    public String id;
    public JournalVoucherDetailsAttributes attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JournalVoucherDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(JournalVoucherDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}