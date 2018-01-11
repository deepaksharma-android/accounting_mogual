package com.lkintechnology.mBilling.networks.api_response.account;

import java.util.ArrayList;

public class GetAccountResponse {
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

    public ArrayList<OrderedAccounts> getOrdered_accounts() {
        return ordered_accounts;
    }

    public void setOrdered_accounts(ArrayList<OrderedAccounts> ordered_accounts) {
        this.ordered_accounts = ordered_accounts;
    }

    public ArrayList<OrderedAccounts> ordered_accounts;
}