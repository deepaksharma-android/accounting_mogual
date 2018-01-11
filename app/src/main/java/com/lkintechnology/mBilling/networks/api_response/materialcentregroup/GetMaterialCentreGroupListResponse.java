package com.lkintechnology.mBilling.networks.api_response.materialcentregroup;

public class GetMaterialCentreGroupListResponse {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String message;
    public int status;

    public MaterialCentreGroupsList getMaterial_center_groups() {
        return material_center_groups;
    }

    public void setMaterial_center_groups(MaterialCentreGroupsList material_center_groups) {
        this.material_center_groups = material_center_groups;
    }

    public MaterialCentreGroupsList material_center_groups;
}