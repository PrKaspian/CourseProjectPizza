package com.example.pizza.network;

import com.example.pizza.entity.Pizza;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PizzaApi {
    @GET("/pizzas")
    Call<List<Pizza>>getAll();

}
