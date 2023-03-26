package com.example.pizza.network;

import com.example.pizza.entity.OrderItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface OrderItemApi {
    @POST("/items")
    Call<OrderItem> add(@Body OrderItem orderItem);
    @PUT("/items")
    Call<OrderItem> update(@Body OrderItem orderItem);
}
