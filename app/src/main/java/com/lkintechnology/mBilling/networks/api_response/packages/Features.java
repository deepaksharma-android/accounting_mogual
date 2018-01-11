package com.lkintechnology.mBilling.networks.api_response.packages;

public class Features {

    public String id;
    public String name;
    public String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHelper_desc() {
        return helper_desc;
    }

    public void setHelper_desc(String helper_desc) {
        this.helper_desc = helper_desc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String helper_desc;
    public boolean active;
}