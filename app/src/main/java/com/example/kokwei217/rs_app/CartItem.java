package com.example.kokwei217.rs_app;

public class CartItem {
    private String name;
    private int quantity;

    public CartItem() {

    }

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

    public boolean exist(String name, int quantity){
        if(this.name.equals(name)){
            this.quantity = quantity;
            return true;
        } else{
            return false;
        }
    }
}


