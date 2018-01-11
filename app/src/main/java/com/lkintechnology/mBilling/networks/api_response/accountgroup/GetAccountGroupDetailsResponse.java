package com.lkintechnology.mBilling.networks.api_response.accountgroup;

public class GetAccountGroupDetailsResponse {
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

    public AccountGroupDetails getAccount_group_details() {
        return account_group_details;
    }

    public void setAccount_group_details(AccountGroupDetails account_group_details) {
        this.account_group_details = account_group_details;
    }

    public AccountGroupDetails account_group_details;
}