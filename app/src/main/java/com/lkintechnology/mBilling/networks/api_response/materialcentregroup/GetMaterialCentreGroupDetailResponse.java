package com.lkintechnology.mBilling.networks.api_response.materialcentregroup;

public class GetMaterialCentreGroupDetailResponse {
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

    public MaterialCentreGroupDetails getMaterial_center_group() {
        return material_center_group;
    }

    public void setMaterial_center_group(MaterialCentreGroupDetails material_center_group) {
        this.material_center_group = material_center_group;
    }

    public MaterialCentreGroupDetails material_center_group;
}