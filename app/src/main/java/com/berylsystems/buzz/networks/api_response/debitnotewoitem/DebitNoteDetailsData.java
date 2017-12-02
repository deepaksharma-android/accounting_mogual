package com.berylsystems.buzz.networks.api_response.debitnotewoitem;

public class DebitNoteDetailsData {

    public String type;
    public String id;
    public DebitNoteDetailsAttributes attributes;

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

    public DebitNoteDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(DebitNoteDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}