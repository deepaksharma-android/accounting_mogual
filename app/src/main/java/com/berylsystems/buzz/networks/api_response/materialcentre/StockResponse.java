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

    public StockInHandAccounts getStock_in_hand_accounts() {
        return stock_in_hand_accounts;
    }

    public void setStock_in_hand_accounts(StockInHandAccounts stock_in_hand_accounts) {
        this.stock_in_hand_accounts = stock_in_hand_accounts;
    }

    public int status;
    public StockInHandAccounts stock_in_hand_accounts;
}