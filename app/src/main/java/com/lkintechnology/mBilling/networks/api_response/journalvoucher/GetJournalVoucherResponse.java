package com.lkintechnology.mBilling.networks.api_response.journalvoucher;

public class GetJournalVoucherResponse {

    public String message;
    public int status;
    public JournalVoucher journal_vouchers;

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

    public JournalVoucher getJournal_vouchers() {
        return journal_vouchers;
    }

    public void setJournal_vouchers(JournalVoucher journal_vouchers) {
        this.journal_vouchers = journal_vouchers;
    }
}