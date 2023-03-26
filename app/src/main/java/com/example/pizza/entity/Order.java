package com.example.pizza.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("items")
    @Expose
    private List<OrderItem> items;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("status")
    @Expose
    private String status;

    public Order() {
        price = 0;
    }

    public Order(String phone, User user, double price, String status) {
        this.phone = phone;
        this.user = user;
        this.items = new ArrayList<>();
        this.price = price;
        this.status = status;
    }

    public void addItem(OrderItem item){
        items.add(item);
    }

    public void deleteItem(int id){
        items.remove(id);
    }



    public int getId() {
        return id;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
