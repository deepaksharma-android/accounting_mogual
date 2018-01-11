package com.lkintechnology.mBilling.networks.api_response.company;

public class CompanyListResponse {
    public Company getCompanies() {
        return companies;
    }

    public void setCompanies(Company companies) {
        this.companies = companies;
    }

    public Company companies;
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

    public String message;
    public int status;
}