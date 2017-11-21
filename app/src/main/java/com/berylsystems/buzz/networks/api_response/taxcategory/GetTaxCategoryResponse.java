package com.berylsystems.buzz.networks.api_response.taxcategory;

public class GetTaxCategoryResponse {

    public String message;
    public int status;
    public Tax_Category tax_category;

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

    public Tax_Category getTax_category() {
        return tax_category;
    }

    public void setTax_category(Tax_Category tax_category) {
        this.tax_category = tax_category;
    }
}