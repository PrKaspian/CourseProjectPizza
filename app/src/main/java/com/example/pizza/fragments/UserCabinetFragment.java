package com.example.pizza.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pizza.MainActivity;
import com.example.pizza.R;
import com.example.pizza.adapters.OrderAdapter;
import com.example.pizza.entity.Order;
import com.example.pizza.network.NetworkService;
import com.example.pizza.network.OrderApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserCabinetFragment extends Fragment {
    private TextView tvName;
    private TextView tvPhone;
    private RecyclerView rvOrderOfUser;
    private List<Order> userOrders;
    private OrderApi orderApi;


    public UserCabinetFragment() {
        super(R.layout.fragment_user_cabinet);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.tvUserName);
        tvPhone = view.findViewById(R.id.tvUserPhone);
        tvName.setText(MainActivity.USER.getName());
        tvPhone.setText(MainActivity.USER.getPhone());
        rvOrderOfUser = view.findViewById(R.id.rvOrderOfUser);
        orderApi = NetworkService.getInstance().getOrderApi();;

        orderApi.getOrdersByUser(MainActivity.USER.getId()).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                userOrders = response.body();
                OrderAdapter orderAdapter = new OrderAdapter(getContext(), R.layout.order_item, userOrders);
                rvOrderOfUser.setAdapter(orderAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        getContext(),
                        RecyclerView.VERTICAL,
                        false
                );
                rvOrderOfUser.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }
}