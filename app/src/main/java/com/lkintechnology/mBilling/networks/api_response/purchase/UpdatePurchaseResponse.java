package com.lkintechnology.mBilling.networks.api_response.purchase;

public class UpdatePurchaseResponse {

    public String message;
    public int status;

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
}