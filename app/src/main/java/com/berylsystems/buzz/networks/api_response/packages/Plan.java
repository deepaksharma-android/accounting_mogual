package com.berylsystems.buzz.networks.api_response.packages;

import java.util.ArrayList;

public class Plan {

    public ArrayList<PlanData> data;

    public ArrayList<PlanData> getData() {
        return data;
    }

    public void setData(ArrayList<PlanData> data) {
        this.data = data;
    }
}