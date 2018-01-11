package com.lkintechnology.mBilling.networks.api_response.materialcentre;

import java.util.ArrayList;

public class GetMaterialCentreListResponse {
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

    public ArrayList<OrderedMaterialCentre> getOrdered_material_centers() {
        return ordered_material_centers;
    }

    public void setOrdered_material_centers(ArrayList<OrderedMaterialCentre> ordered_material_centers) {
        this.ordered_material_centers = ordered_material_centers;
    }

    public ArrayList<OrderedMaterialCentre> ordered_material_centers;
}