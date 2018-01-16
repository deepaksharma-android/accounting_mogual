package com.lkintechnology.mBilling.networks.api_response.expence;

public class GetExpenceDetailsResponse {
    public String message;
    public int status;

    public ExpenceDeatails getExpense() {
        return expense;
    }

    public void setExpense(ExpenceDeatails expense) {
        this.expense = expense;
    }

    public ExpenceDeatails expense;

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