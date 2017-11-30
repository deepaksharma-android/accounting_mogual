package com.berylsystems.buzz.networks.api_response.income;

public class GetIncomeDetailsResponse {
    public String message;
    public int status;
    public IncomeDeatails income;

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

    public IncomeDeatails getIncome() {
        return income;
    }

    public void setIncome(IncomeDeatails income) {
        this.income = income;
    }
}

