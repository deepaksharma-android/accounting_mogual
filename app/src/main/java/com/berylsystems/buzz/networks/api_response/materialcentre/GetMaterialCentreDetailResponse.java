package com.berylsystems.buzz.networks.api_response.materialcentre;

public class GetMaterialCentreDetailResponse {
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

    public MaterialCentre getMaterial_center() {
        return material_center;
    }

    public void setMaterial_center(MaterialCentre material_center) {
        this.material_center = material_center;
    }

    public MaterialCentre material_center;

}