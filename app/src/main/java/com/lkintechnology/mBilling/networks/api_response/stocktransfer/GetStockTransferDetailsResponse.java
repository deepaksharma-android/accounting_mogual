package com.lkintechnology.mBilling.networks.api_response.stocktransfer;

public class GetStockTransferDetailsResponse {
    public String message;
    public int status;
    public StockTransferVoucherDetails stock_transfer_voucher;

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

    public StockTransferVoucherDetails getStock_transfer_voucher() {
        return stock_transfer_voucher;
    }

    public void setStock_transfer_voucher(StockTransferVoucherDetails stock_transfer_voucher) {
        this.stock_transfer_voucher = stock_transfer_voucher;
    }
}