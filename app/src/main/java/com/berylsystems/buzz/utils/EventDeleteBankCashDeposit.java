package com.berylsystems.buzz.utils;

public class EventDeleteBankCashDeposit {

    private final String position;

    public EventDeleteBankCashDeposit(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}