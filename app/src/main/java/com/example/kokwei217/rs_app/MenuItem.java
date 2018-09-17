package com.example.kokwei217.rs_app;

public class MenuItem {
    private String Name;
    private int Quantity;

    public MenuItem(){

    }

    public MenuItem(int Quantity, String Name ){
        this.Name = Name;
        this.Quantity = Quantity;
    }

    public String getName(){
        return Name;
    }

    public int getQuantity(){
        return Quantity;
    }

}
