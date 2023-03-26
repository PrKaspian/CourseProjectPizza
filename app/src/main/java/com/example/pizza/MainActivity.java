package com.example.pizza;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pizza.adapters.PizzaAdapter;
import com.example.pizza.entity.Additive;
import com.example.pizza.entity.Order;
import com.example.pizza.entity.OrderItem;
import com.example.pizza.entity.Pizza;
import com.example.pizza.entity.User;
import com.example.pizza.network.NetworkService;
import com.example.pizza.network.OrderApi;
import com.example.pizza.network.OrderItemApi;
import com.example.pizza.network.PizzaApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static User USER; //= new User("max", "095-000-00-00", "password", false);
    public static final String KEY_ITEM = "item";
    public static final String KEY_ORDER = "order";
    private PizzaAdapter adapter;
    private PizzaApi pizzaApi;
    private RecyclerView rvPizzas;
    private Button btnOrder;
    private List<OrderItem> items;
    private Order order;

    List<OrderItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        pizzaApi = NetworkService.getInstance().getPizzaApi();
        rvPizzas = findViewById(R.id.rvPizzas);
        btnOrder = findViewById(R.id.btnOrder);

        initialPizzaListView();
        btnOrder.setOnClickListener(view -> {
            if (items.size() > 0){
                if (order == null){
                    order = new Order();
                    order.setItems(items);
                }
                openActivityOrder();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cabinet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sigIn:
                Intent intent = new Intent(MainActivity.this, PersonalAccountActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openActivityOrder() {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(KEY_ORDER, order);
        activityOrderLauncher.launch(intent);
    }

    private void initialPizzaListView() {
        pizzaApi.getAll().enqueue(new Callback<List<Pizza>>() {
            @Override
            public void onResponse(Call<List<Pizza>> call, Response<List<Pizza>> response) {
                List<Pizza> pizzas = response.body();
                adapter = new PizzaAdapter(MainActivity.this, R.layout.pizza_item, pizzas, new PizzaAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Pizza pizza = pizzas.get(position);
                        OrderItem item = new OrderItem();
                        item.setPizza(pizza);
                        Intent intent = new Intent(MainActivity.this, InfoPizzaActivity.class);
                        intent.putExtra(KEY_ITEM, item);
                        activityInfoLauncher.launch(intent);
                    }
                });
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
    ActivityResultLauncher<Intent> activityInfoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    OrderItem item = (OrderItem) intent.getSerializableExtra(InfoPizzaActivity.KEY_ITEM);
                    items.add(item);
                    Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                } else if (result.getResultCode() == RESULT_CANCELED) {

                }

            });
    ActivityResultLauncher<Intent> activityOrderLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    order = (Order) intent.getSerializableExtra(OrderActivity.KEY_ORDER);
                    orderItems = order.getItems();
                    OrderApi orderApi = NetworkService.getInstance().getOrderApi();
                    OrderItemApi orderItemApi =NetworkService.getInstance().getOrderItemApi();
                    order.setItems(null);
                    for (OrderItem item : orderItems) {
                        item.setOrder(order);
                    }

                    for (OrderItem item : orderItems) {
                        Set<Additive> additive = item.getAdditives();
                        item.setAdditives(null);
                        orderItemApi.add(item).enqueue(new Callback<OrderItem>() {
                            @Override
                            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                                OrderItem saveItem = response.body();
                                saveItem.setAdditives(additive);
                                orderItemApi.update(saveItem).enqueue(new Callback<OrderItem>() {
                                    @Override
                                    public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<OrderItem> call, Throwable t) {

                                    }
                                });
                                Toast.makeText(MainActivity.this, "ORDER SAVE", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<OrderItem> call, Throwable t) {

                            }
                        });
                    }




                    order = null;
                    items.clear();
                } else if (result.getResultCode() == RESULT_FIRST_USER) {
                    Intent intent = result.getData();
                    order = (Order) intent.getSerializableExtra(OrderActivity.KEY_ORDER);
                    openActivityOrder();
                    Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
                } else if (result.getResultCode() == RESULT_CANCELED){
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                }

            });


}
