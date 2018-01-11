package com.lkintechnology.mBilling.networks.api_response.bankcashdeposit;

public class CreateBankCashDepositResponse {

    public String message;
    public int status;
    public String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

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