package com.example.pizza.network;

import com.example.pizza.entity.Additive;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdditiveApi {
    @GET("/additives")
    Call<List<Additive>> getAll();
}
