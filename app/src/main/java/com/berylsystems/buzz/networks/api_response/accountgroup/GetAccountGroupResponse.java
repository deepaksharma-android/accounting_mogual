package com.berylsystems.buzz.networks.api_response.accountgroup;

public class GetAccountGroupResponse {
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

    public AccountGroups getAccount_groups() {
        return account_groups;
    }

    public void setAccount_groups(AccountGroups account_groups) {
        this.account_groups = account_groups;
    }

    public AccountGroups account_groups;

}