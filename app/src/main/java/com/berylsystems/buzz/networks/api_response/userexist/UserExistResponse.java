package com.berylsystems.buzz.networks.api_response.userexist;

import com.berylsystems.buzz.networks.api_response.user.User;

public class UserExistResponse {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User user;

    public String getIs_present() {
        return is_present;
    }

    public void setIs_present(String is_present) {
        this.is_present = is_present;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int status;
    public String is_present;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String message;

}