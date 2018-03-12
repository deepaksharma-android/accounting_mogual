package com.lkintechnology.mBilling.networks.api_response.creditnotewoitem;

public class CreditNoteItemData {
    public String id;
    public String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CreditNoteItemAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(CreditNoteItemAttribute attributes) {
        this.attributes = attributes;
    }

    public CreditNoteItemAttribute attributes;
}