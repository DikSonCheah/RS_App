package com.example.kokwei217.rs_app;

public class CartItem {
    private String name;
    private int quantity;

    public CartItem(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public int getQuantity(){
        return quantity;
    }
}


