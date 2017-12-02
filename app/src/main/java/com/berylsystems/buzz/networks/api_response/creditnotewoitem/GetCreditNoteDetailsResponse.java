package com.berylsystems.buzz.networks.api_response.creditnotewoitem;

public class GetCreditNoteDetailsResponse {
    public String message;
    public int status;
    public CreditNoteDetails credit_note;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CreditNoteDetails getCredit_note() {
        return credit_note;
    }

    public void setCredit_note(CreditNoteDetails credit_note) {
        this.credit_note = credit_note;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}