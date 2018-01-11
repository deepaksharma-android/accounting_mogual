package com.lkintechnology.mBilling.networks.api_response.payment;

public class GetPaymentResponse {

    public String message;
    public int status;
    public Payments payments;

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

    public Payments getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }
}