package com.lkintechnology.mBilling.networks.api_response.creditnotewoitem;

public class CreditNoteDetailsData {

    public String type;
    public String id;
    public CreditNoteDetailsAttributes attributes;

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

    public CreditNoteDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(CreditNoteDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}