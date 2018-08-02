package com.lkintechnology.mBilling.networks.api_response.voucherseries;

/**
 * Created by SAMSUNG on 8/2/2018.
 */

public class VoucherSeriesResponse {
    public int status;
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

    public VoucherSeries getVoucher_series() {
        return voucher_series;
    }

    public void setVoucher_series(VoucherSeries voucher_series) {
        this.voucher_series = voucher_series;
    }

    public VoucherSeries voucher_series;
}
