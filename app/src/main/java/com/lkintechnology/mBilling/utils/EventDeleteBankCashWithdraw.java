package com.lkintechnology.mBilling.utils;

public class EventDeleteBankCashWithdraw {

    private final String position;

    public EventDeleteBankCashWithdraw(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}