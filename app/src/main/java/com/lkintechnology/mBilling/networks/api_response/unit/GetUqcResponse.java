package com.lkintechnology.mBilling.networks.api_response.unit;

public class GetUqcResponse {
    public String message;

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

    public Uqc getUqc() {
        return uqc;
    }

    public void setUqc(Uqc uqc) {
        this.uqc = uqc;
    }

    public Uqc uqc;
}