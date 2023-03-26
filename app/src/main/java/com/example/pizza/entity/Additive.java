package com.example.pizza.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Additive implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("actual")
    @Expose
    private boolean isActual;

    public Additive(String name, double price, boolean isActual) {
        this.name = name;
        this.price = price;
        this.isActual = isActual;
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

    public boolean isActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }

    @Override
    public String toString() {
        return name + " (" +
               price +
                "грн)";
    }
}
