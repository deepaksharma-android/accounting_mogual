package com.lkintechnology.mBilling.networks.api_response.user;

/**
 * Created by pc on 6/26/2017.
 */
public class Attributes {
    public String email;
    public String name;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String mobile;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String image;
    public Boolean active;
    public String auth_token;
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String postal_code;

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String qr_code;

    public int getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(int wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public int wallet_amount;

    public String getUser_plan() {
        return user_plan;
    }

    public void setUser_plan(String user_plan) {
        this.user_plan = user_plan;
    }

    public String user_plan;
}
