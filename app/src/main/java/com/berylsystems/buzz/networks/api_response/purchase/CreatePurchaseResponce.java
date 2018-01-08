package com.berylsystems.buzz.networks.api_response.purchase;

/**
 * Created by BerylSystems on 11/29/2017.
 */

public class CreatePurchaseResponce {

    public String message;
    public int status;
    public String html;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
