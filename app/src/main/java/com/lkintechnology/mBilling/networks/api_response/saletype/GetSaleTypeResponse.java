package com.lkintechnology.mBilling.networks.api_response.saletype;

public class GetSaleTypeResponse {

    public String message;
    public int status;
    public Sale_Type sale_type;

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

    public Sale_Type getSale_type() {
        return sale_type;
    }

    public void setSale_type(Sale_Type sale_type) {
        this.sale_type = sale_type;
    }
}
