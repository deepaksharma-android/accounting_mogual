package com.lkintechnology.mBilling.networks.api_response.stocktransfer;

public class UpdateStockTrasferResponse {
    public String message;
    public int status;
    public String html;

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

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}