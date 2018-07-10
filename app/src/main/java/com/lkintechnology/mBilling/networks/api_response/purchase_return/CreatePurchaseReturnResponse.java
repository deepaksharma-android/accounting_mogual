package com.lkintechnology.mBilling.networks.api_response.purchase_return;

/**
 * Created by BerylSystems on 11/23/2017.
 */

public class CreatePurchaseReturnResponse {
    public String message;
    public int id;
    public String html;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int status;
}
