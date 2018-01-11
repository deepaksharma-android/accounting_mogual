package com.lkintechnology.mBilling.networks.api_response.materialcentre;

public class Attributes {
    public int id;
    public String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMaterial_center_group_name() {
        return material_center_group_name;
    }

    public void setMaterial_center_group_name(String material_center_group_name) {
        this.material_center_group_name = material_center_group_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String address;
    public String material_center_group_name;

    public Boolean getUndefined() {
        return undefined;
    }

    public void setUndefined(Boolean undefined) {
        this.undefined = undefined;
    }

    public Boolean undefined;

}