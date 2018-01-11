package com.lkintechnology.mBilling.events;

public class EventFbAuthResponse {

    public String first_name;
    public String last_name;
    public String email;
    public String id;
    public String mobile_phone;
    public String avatarUrl;
    public String token;


    public void composeAvatarUrl() {
        avatarUrl = "https://graph.facebook.com/" + id + "/picture?type=large";
    }
}
