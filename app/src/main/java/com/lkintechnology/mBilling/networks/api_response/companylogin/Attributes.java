package com.lkintechnology.mBilling.networks.api_response.companylogin;

import java.util.ArrayList;

public class Attributes {
    public ArrayList<UserName> getUsername() {
        return username;
    }

    public void setUsername(ArrayList<UserName> username) {
        this.username = username;
    }

    public ArrayList<UserName> username;
}