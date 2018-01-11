package com.lkintechnology.mBilling.networks.api_response.materialcentregroup;

public class MaterialCentreGroupDetailsAttributes {
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

    public String getMaterial_center_group() {
        return material_center_group;
    }

    public void setMaterial_center_group(String material_center_group) {
        this.material_center_group = material_center_group;
    }

    public String name;
    public int id;
    public String material_center_group;

}