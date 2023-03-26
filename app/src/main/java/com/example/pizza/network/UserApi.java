package com.example.pizza.network;

import com.example.pizza.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/users/phone/{phone}")
    Call<User> getUserByPhone(@Path("phone") String phone);

    @POST("/users")
    Call<User> saveUser(@Body User user);
}
