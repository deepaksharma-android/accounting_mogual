package com.berylsystems.buzz.networks.api_response.debitnotewoitem;

public class GetDebitNoteResponse {

    public String message;
    public int status;
    public DebitNotes debit_notes;

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

    public DebitNotes getDebit_notes() {
        return debit_notes;
    }

    public void setDebit_notes(DebitNotes debit_notes) {
        this.debit_notes = debit_notes;
    }
}