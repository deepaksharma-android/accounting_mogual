package com.berylsystems.buzz.networks.api_response.bill_sundry;

public class GetBillSundryListResponse {
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

    public BillSundry getBill_sundries() {
        return bill_sundries;
    }

    public void setBill_sundries(BillSundry bill_sundries) {
        this.bill_sundries = bill_sundries;
    }

    public BillSundry bill_sundries;
}