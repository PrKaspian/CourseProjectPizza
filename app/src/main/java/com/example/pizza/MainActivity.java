package com.example.pizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.pizza.entity.Pizza;
import com.example.pizza.network.NetworkService;
import com.example.pizza.network.PizzaApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PizzaAdapter adapter;
    private PizzaApi pizzaApi;
    private RecyclerView rvPizzas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("xxx", String.valueOf(R.drawable.chees));
        Log.d("xxx", String.valueOf(R.drawable.chees_m));
        Log.d("xxx", String.valueOf(R.drawable.chicken));
        Log.d("xxx", String.valueOf(R.drawable.chicken_mushrooms));
        Log.d("xxx", String.valueOf(R.drawable.gavai));

        pizzaApi = NetworkService.getInstance().getPizzaApi();
        rvPizzas = findViewById(R.id.rvPizzas);
//        List<Pizza> pizzas = new ArrayList<>();
//        pizzas.add(new Pizza("BBQ", 200.00, "asdfasdf", true, R.drawable.bbq));
//        adapter = new PizzaAdapter(MainActivity.this, R.layout.pizza_item, pizzas);
//        rvPizzas.setAdapter(adapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(
//                MainActivity.this,
//                RecyclerView.VERTICAL,
//                false
//        );
//        rvPizzas.setLayoutManager(layoutManager);

        initialPizzaListView();
    }

    private void initialPizzaListView() {
        pizzaApi.getAll().enqueue(new Callback<List<Pizza>>() {
            @Override
            public void onResponse(Call<List<Pizza>> call, Response<List<Pizza>> response) {
                List<Pizza> pizzas = response.body();
                adapter = new PizzaAdapter(MainActivity.this, R.layout.pizza_item, pizzas);
                rvPizzas.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        MainActivity.this,
                        RecyclerView.VERTICAL,
                        false
                );
                rvPizzas.setLayoutManager(layoutManager);


            }

            @Override
            public void onFailure(Call<List<Pizza>> call, Throwable t) {

            }
        });

    }
}