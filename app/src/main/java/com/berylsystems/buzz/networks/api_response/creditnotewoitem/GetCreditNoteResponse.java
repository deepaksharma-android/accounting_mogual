package com.berylsystems.buzz.networks.api_response.creditnotewoitem;

public class GetCreditNoteResponse {

    public String message;
    public int status;
    public CreditNotes credit_notes;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CreditNotes getCredit_notes() {
        return credit_notes;
    }

    public void setCredit_notes(CreditNotes credit_notes) {
        this.credit_notes = credit_notes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}