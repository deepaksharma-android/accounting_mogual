package com.berylsystems.buzz.networks.api_response.item;

import android.content.ClipData;

/**
 * Created by BerylSystems on 11/20/2017.
 */

public class GetItemDetailsResponse {
    public String message;
    public int status;
    public Item item;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
