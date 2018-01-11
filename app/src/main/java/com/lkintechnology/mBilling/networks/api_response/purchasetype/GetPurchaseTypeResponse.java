package com.lkintechnology.mBilling.networks.api_response.purchasetype;

public class GetPurchaseTypeResponse {

    public String message;
    public int status;
    public Purchase_Type purchase_type;

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

    public Purchase_Type getPurchase_type() {
        return purchase_type;
    }

    public void setPurchase_type(Purchase_Type purchase_type) {
        this.purchase_type = purchase_type;
    }
}