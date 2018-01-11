package com.lkintechnology.mBilling.networks.api_response.payment;

public class GetPaymentDetailsResponse {

    public String message;
    public int status;
    public PaymentsDetails payment;

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

    public PaymentsDetails getPayment() {
        return payment;
    }

    public void setPayment(PaymentsDetails payment) {
        this.payment = payment;
    }
}