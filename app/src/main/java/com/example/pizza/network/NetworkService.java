package com.example.pizza.network;

import com.example.pizza.entity.Order;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService networkService;
    private  static final String BASE_URL = "http://192.168.0.109:8080/";
    private Retrofit retrofit;

    private NetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static NetworkService getInstance() {
        if (networkService == null) {
            networkService = new NetworkService();
        }
        return networkService;
    }

    public PizzaApi getPizzaApi(){
        return retrofit.create(PizzaApi.class);
    }
    public AdditiveApi getAdditiveApi(){ return retrofit.create(AdditiveApi.class);}
    public SizeApi getSizeApi(){ return  retrofit.create(SizeApi.class);}
    public UserApi getUserApi(){ return  retrofit.create(UserApi.class);}
    public OrderApi getOrderApi(){return  retrofit.create(OrderApi.class);}
    public OrderItemApi getOrderItemApi(){ return  retrofit.create(OrderItemApi.class);}
}
