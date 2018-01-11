package com.lkintechnology.mBilling.utils;

public class EventDeleteBankCashDeposit {

    private final String position;

    public EventDeleteBankCashDeposit(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}