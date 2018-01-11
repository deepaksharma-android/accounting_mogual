package com.lkintechnology.mBilling.networks.api_response.debitnotewoitem;

public class GetDebitNoteDetailsResponse {
    public String message;
    public int status;
    public DebitNoteDetails debit_note;

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

    public DebitNoteDetails getDebit_note() {
        return debit_note;
    }

    public void setDebit_note(DebitNoteDetails debit_note) {
        this.debit_note = debit_note;
    }
}