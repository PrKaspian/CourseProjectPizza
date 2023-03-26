package com.example.pizza.network;

import com.example.pizza.entity.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApi {
    @GET("/orders/user/{id}")
    Call<List<Order>> getOrdersByUser(@Path("id") int id);

    @POST("/orders")
    Call<Order> saveOrder(@Body Order order);
}
