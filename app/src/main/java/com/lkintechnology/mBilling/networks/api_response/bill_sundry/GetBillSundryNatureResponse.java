package com.lkintechnology.mBilling.networks.api_response.bill_sundry;

public class GetBillSundryNatureResponse {
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

    public BillSundryNature getBill_sundry_nature() {
        return bill_sundry_nature;
    }

    public void setBill_sundry_nature(BillSundryNature bill_sundry_nature) {
        this.bill_sundry_nature = bill_sundry_nature;
    }

    public BillSundryNature bill_sundry_nature;
}