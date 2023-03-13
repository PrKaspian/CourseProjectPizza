package com.example.pizza.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pizza {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("composition")
    @Expose
    private String composition;
    @SerializedName("actual")
    @Expose
    private boolean isActual;
    @SerializedName("image")
    @Expose
    private int image;


    public Pizza(String name, double price, String composition, boolean isActual, int image) {
        this.name = name;
        this.price = price;
        this.composition = composition;
        this.isActual = isActual;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public boolean isActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
