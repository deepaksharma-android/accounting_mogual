package com.lkintechnology.mBilling.networks.api_response.expence;

public class GetExpenceResponse {

    public String message;
    public int status;
    public Expenses expenses;

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

    public Expenses getExpenses() {
        return expenses;
    }

    public void setExpenses(Expenses expenses) {
        this.expenses = expenses;
    }
}