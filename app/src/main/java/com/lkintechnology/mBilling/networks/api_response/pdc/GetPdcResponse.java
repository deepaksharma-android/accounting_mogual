package com.lkintechnology.mBilling.networks.api_response.pdc;

/**
 * Created by BerylSystems on 27-Dec-17.
 */

public class GetPdcResponse {
    public String message;
    public int status;

    public PdcDetails getPdc_details() {
        return pdc_details;
    }

    public void setPdc_details(PdcDetails pdc_details) {
        this.pdc_details = pdc_details;
    }

    public PdcDetails pdc_details;

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


}
