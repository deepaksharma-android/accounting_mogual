package com.berylsystems.buzz.networks.api_response.materialcentre;

public class StockResponse {
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

    public StockInHandAccounts getCompany_account_groups() {
        return company_account_groups;
    }

    public void setCompany_account_groups(StockInHandAccounts company_account_groups) {
        this.company_account_groups = company_account_groups;
    }

    public StockInHandAccounts company_account_groups;
}