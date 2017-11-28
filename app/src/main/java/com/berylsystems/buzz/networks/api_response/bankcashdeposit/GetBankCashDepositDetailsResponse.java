package com.berylsystems.buzz.networks.api_response.bankcashdeposit;

public class GetBankCashDepositDetailsResponse {

    public String message;
    public int status;
    public BankCashDepositsDetails bank_cash_deposit;

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

    public BankCashDepositsDetails getBank_cash_deposit() {
        return bank_cash_deposit;
    }

    public void setBank_cash_deposit(BankCashDepositsDetails bank_cash_deposit) {
        this.bank_cash_deposit = bank_cash_deposit;
    }
}