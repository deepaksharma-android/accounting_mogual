package com.lkintechnology.mBilling.networks.api_response.checkbarcode;

public class CheckBarcodeResponse {

    public String message;

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

    public int status;
}