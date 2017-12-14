package com.berylsystems.buzz.networks.api_response.companydashboardinfo;

public class GetCompanyDashboardInfoResponse {

    public String message;
    public int status;
    public CompanyDetails company_details;

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

    public CompanyDetails getCompany_details() {
        return company_details;
    }

    public void setCompany_details(CompanyDetails company_details) {
        this.company_details = company_details;
    }
}