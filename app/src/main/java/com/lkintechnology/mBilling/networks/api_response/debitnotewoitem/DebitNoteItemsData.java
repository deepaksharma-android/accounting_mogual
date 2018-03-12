package com.lkintechnology.mBilling.networks.api_response.debitnotewoitem;

public class DebitNoteItemsData {
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String type;



    public String getType() {
        return type;
    }

    public DebitNoteItemsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(DebitNoteItemsAttributes attributes) {
        this.attributes = attributes;
    }

    public void setType(String type) {

        this.type = type;
    }

    public DebitNoteItemsAttributes attributes;
}