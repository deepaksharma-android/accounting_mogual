package com.lkintechnology.mBilling.networks.api_response.bankcashdeposit;

public class GetBankCashDepositResponse {

    public String message;
    public int status;
    public BankCashDeposits bank_cash_deposits;

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

    public BankCashDeposits getBank_cash_deposits() {
        return bank_cash_deposits;
    }

    public void setBank_cash_deposits(BankCashDeposits bank_cash_deposits) {
        this.bank_cash_deposits = bank_cash_deposits;
    }
}