package com.berylsystems.buzz.networks.api_response.bankcashwithdraw;

public class GetBankCashWithdrawDetailsResponse {

    public String message;
    public int status;
    public BankCashWithdrawDetails bank_cash_withdraw;

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

    public BankCashWithdrawDetails getBank_cash_withdraw() {
        return bank_cash_withdraw;
    }

    public void setBank_cash_withdraw(BankCashWithdrawDetails bank_cash_withdraw) {
        this.bank_cash_withdraw = bank_cash_withdraw;
    }
}