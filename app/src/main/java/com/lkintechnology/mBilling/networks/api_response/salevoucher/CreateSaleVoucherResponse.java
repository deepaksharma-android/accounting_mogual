package com.lkintechnology.mBilling.networks.api_response.salevoucher;

/**
 * Created by BerylSystems on 11/23/2017.
 */

public class CreateSaleVoucherResponse {
    public String message;
    public String html;

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
