package com.berylsystems.buzz.networks.api_response.expence;

public class DeleteExpenceResponse {
    public String message;
    public int status;

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
}