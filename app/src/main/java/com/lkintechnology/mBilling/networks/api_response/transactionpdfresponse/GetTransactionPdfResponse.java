package com.lkintechnology.mBilling.networks.api_response.transactionpdfresponse;

public class GetTransactionPdfResponse {

    public String message;
    public int status;
    public String company_report;
    public String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getCompany_report() {
        return company_report;
    }

    public void setCompany_report(String company_report) {
        this.company_report = company_report;
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