package com.example.pizza.network;

import com.example.pizza.entity.Size;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SizeApi {
    @GET("/sizes")
    Call<List<Size>> getAll();
}
