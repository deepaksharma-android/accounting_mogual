package com.lkintechnology.mBilling.networks.api_response.company;

public class CompanyAuthenticateResponse {
    public String address;
    public String city;
    public String pincode;
    public String state;
    public String phone;
    public String gst;
    public String invoice_format;
    public String[] tnc;

    public String[] getTnc() {
        return tnc;
    }

    public void setTnc(String[] tnc) {
        this.tnc = tnc;
    }

    public String getInvoice_format() {
        return invoice_format;
    }

    public void setInvoice_format(String invoice_format) {
        this.invoice_format = invoice_format;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
}