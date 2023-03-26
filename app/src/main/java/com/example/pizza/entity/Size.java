package com.example.pizza.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Size implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("default")
    @Expose
    private boolean isDefault;

    public Size(String name, double price, boolean isDefault) {
        this.name = name;
        this.price = price;
        this.isDefault = isDefault;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return name + "\n " +
                "( " + price +
                " грн)";
    }
}
