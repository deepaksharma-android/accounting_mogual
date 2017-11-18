package com.berylsystems.buzz.networks.api_response.item;



import java.util.ArrayList;

public class GetItemResponse {
    public int status;
    public String message;
    public ArrayList<OrderedItems> ordered_items;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<OrderedItems> getOrdered_items() {
        return ordered_items;
    }

    public void setOrdered_items(ArrayList<OrderedItems> ordered_items) {
        this.ordered_items = ordered_items;
    }
}