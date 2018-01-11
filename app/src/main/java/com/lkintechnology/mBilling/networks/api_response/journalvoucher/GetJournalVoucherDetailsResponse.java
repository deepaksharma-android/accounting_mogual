package com.lkintechnology.mBilling.networks.api_response.journalvoucher;

public class GetJournalVoucherDetailsResponse {

    public String message;
    public int status;
    public JournalVoucherDetails journal_voucher;

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

    public JournalVoucherDetails getJournal_voucher() {
        return journal_voucher;
    }

    public void setJournal_voucher(JournalVoucherDetails journal_voucher) {
        this.journal_voucher = journal_voucher;
    }
}