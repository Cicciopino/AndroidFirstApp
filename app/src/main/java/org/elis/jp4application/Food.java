package org.elis.jp4application;

import org.json.JSONObject;

public class Food {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if(quantity == 0) return;
        this.quantity--;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private String name;
    private int quantity = 0;
    private float price;


    public Food(String name, float price){

        this.name = name;
        this.price = price;
    }
    public Food(JSONObject jsonFood){

    }



}
