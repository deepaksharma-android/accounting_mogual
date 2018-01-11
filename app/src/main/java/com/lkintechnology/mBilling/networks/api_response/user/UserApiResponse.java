package com.lkintechnology.mBilling.networks.api_response.user;

/**
 * Created by pc on 6/26/2017.
 */
public class UserApiResponse {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User user;

    public String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int status;
}
