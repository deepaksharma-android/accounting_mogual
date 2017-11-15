package com.berylsystems.buzz.networks.api_response.materialcentregroup;

public class MaterialCentreGroupListAttributes {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int id;
    public String name;
    public Boolean primary_group;

    public Boolean getPrimary_group() {
        return primary_group;
    }

    public void setPrimary_group(Boolean primary_group) {
        this.primary_group = primary_group;
    }
}