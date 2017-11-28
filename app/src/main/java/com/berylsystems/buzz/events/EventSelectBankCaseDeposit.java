package com.berylsystems.buzz.events;

public class EventSelectBankCaseDeposit {

    String position;

    public EventSelectBankCaseDeposit(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}