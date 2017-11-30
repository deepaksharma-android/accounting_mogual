package com.berylsystems.buzz.networks.api_response.bankcashwithdraw;

public class GetBankCashWithdrawResponse {

    public String message;
    public int status;
    public BankCashWithdraws bank_cash_withdraws;

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

    public BankCashWithdraws getBank_cash_withdraws() {
        return bank_cash_withdraws;
    }

    public void setBank_cash_withdraws(BankCashWithdraws bank_cash_withdraws) {
        this.bank_cash_withdraws = bank_cash_withdraws;
    }
}