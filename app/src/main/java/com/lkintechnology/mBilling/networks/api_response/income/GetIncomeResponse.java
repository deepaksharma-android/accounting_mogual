package com.lkintechnology.mBilling.networks.api_response.income;

public class GetIncomeResponse {
    public String message;
    public int status;
    public Incomes incomes;

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

    public Incomes getIncomes() {
        return incomes;
    }

    public void setIncomes(Incomes incomes) {
        this.incomes = incomes;
    }
}