package com.berylsystems.buzz.networks.api_response.materialcentre;

public class GetMaterialCentreDetailAttributes {
    public int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMaterial_center_group_name() {
        return material_center_group_name;
    }

    public void setMaterial_center_group_name(String material_center_group_name) {
        this.material_center_group_name = material_center_group_name;
    }

    public String name;
    public String address;
    public String city;
    public String material_center_group_name;
}