package com.lkintechnology.mBilling.events;

public class EventSelectBankCaseDeposit {

    String position;

    public EventSelectBankCaseDeposit(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}