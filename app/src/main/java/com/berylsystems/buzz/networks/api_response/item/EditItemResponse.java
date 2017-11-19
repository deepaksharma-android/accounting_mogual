package com.berylsystems.buzz.networks.api_response.item;

/**
 * Created by BerylSystems on 11/19/2017.
 */

public class EditItemResponse {
    public int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String message;
}
