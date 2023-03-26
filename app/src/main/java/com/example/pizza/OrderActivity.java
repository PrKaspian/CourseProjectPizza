package com.example.pizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza.adapters.OrderItemAdapter;
import com.example.pizza.databinding.ActivityOrderBinding;
import com.example.pizza.entity.Order;
import com.example.pizza.entity.OrderItem;
import com.example.pizza.network.NetworkService;
import com.example.pizza.network.OrderApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    public static final String KEY_ORDER = "order";
    public static final String KEY_ITEM = "item";

    private ActivityOrderBinding binding;
    private RecyclerView rvItems;
    private OrderItemAdapter adapter;
    private List<OrderItem> items;
    private Order order;
    private double price;
    private int positionEditItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        order = (Order) getIntent().getSerializableExtra(MainActivity.KEY_ORDER);
        items = order.getItems();
        for (OrderItem item : items) {
            price += item.getPrice();
        }
        binding.tvOrderPrice.setText(String.valueOf(price));
        initialOrderItemListView();

        if (MainActivity.USER != null){
            binding.etUserName.setText(MainActivity.USER.getName());
            binding.etUserPhone.setText(MainActivity.USER.getPhone());
        }

        binding.btnSaveOrder.setOnClickListener(view -> {
            if(items.size() > 0){
                if(!binding.etUserPhone.getText().toString().equals("") && !binding.etUserName.getText().toString().equals("")){
                    if (MainActivity.USER != null){
                        order.setUser(MainActivity.USER);
                    }
                    order.setPhone(binding.etUserPhone.getText().toString());
                    order.setPrice(price);
                    order.setStatus("Сохранен");
                    OrderApi orderApi = NetworkService.getInstance().getOrderApi();
                    orderApi.saveOrder(order).enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            order = response.body();
                            order.setItems(items);

                            Intent intent = new Intent();
                            intent.putExtra(KEY_ORDER, order);
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {

                        }
                    });

                }
            }
        });
    }

    private void initialOrderItemListView (){
        adapter = new OrderItemAdapter(OrderActivity.this, R.layout.order_item_item, items, new OrderItemAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(OrderActivity.this, InfoPizzaActivity.class);
                OrderItem selectedItem = items.get(position);
                intent.putExtra(KEY_ITEM, selectedItem);
                positionEditItem = position;
                activityInfoLauncher.launch(intent);
            }
        });
        binding.rvOrderItems.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                OrderActivity.this,
                RecyclerView.VERTICAL,
                false
        );
        binding.rvOrderItems.setLayoutManager(layoutManager);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                items.remove(position);
                Intent intent = new Intent();
                intent.putExtra(KEY_ORDER, order);
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        }).attachToRecyclerView(binding.rvOrderItems);
    }

    ActivityResultLauncher<Intent> activityInfoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    OrderItem item = (OrderItem) intent.getSerializableExtra(InfoPizzaActivity.KEY_ITEM);
                    items.set(positionEditItem, item);
                    intent = new Intent();
                    intent.putExtra(KEY_ORDER, order);
                    setResult(RESULT_FIRST_USER, intent);
                    finish();
                } else if (result.getResultCode() == RESULT_CANCELED) {

                }

            });


}