package com.example.pizza.entity;

import com.example.pizza.network.OrderItemApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class OrderItem implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("pizza")
    @Expose
    private Pizza pizza;
    @SerializedName("size")
    @Expose
    private Size size;
    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("additives")
    @Expose
    private Set<Additive> additives;
    private double price;

    public OrderItem() {
    }

    public OrderItem(Pizza pizza, Size size, Set<Additive> additives, double price) {
        this.pizza = pizza;
        this.size = size;
        this.additives = additives;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Set<Additive> getAdditives() {
        return additives;
    }

    public void setAdditives(Set<Additive> additives) {
        this.additives = additives;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
