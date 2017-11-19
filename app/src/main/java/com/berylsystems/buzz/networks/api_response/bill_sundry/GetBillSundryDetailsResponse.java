package com.berylsystems.buzz.networks.api_response.bill_sundry;

public class GetBillSundryDetailsResponse {
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

    public BillSundoryDetails getBill_sundry_info() {
        return bill_sundry_info;
    }

    public void setBill_sundry_info(BillSundoryDetails bill_sundry_info) {
        this.bill_sundry_info = bill_sundry_info;
    }

    public BillSundoryDetails bill_sundry_info;
}