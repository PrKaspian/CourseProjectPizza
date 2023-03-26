package com.example.pizza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza.R;
import com.example.pizza.entity.Order;
import com.example.pizza.entity.OrderItem;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private int resource;
    private List<Order> orders;
    private int countOrder = 0;

    public OrderAdapter(Context context, int resource, List<Order> orders) {
        this.context = context;
        this.resource = resource;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(resource, parent, false);
        OrderAdapter.OrderViewHolder orderViewHolder = new OrderAdapter.OrderViewHolder(container);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        String price = String.valueOf(orders.get(position).getPrice());
        holder.priceOrder.setText(price);
        holder.numberOrder.setText("#" + ++countOrder);

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView numberOrder, priceOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            numberOrder = itemView.findViewById(R.id.tvNumberOrder);
            priceOrder = itemView.findViewById(R.id.tvPriceOrder);
        }
    }
}
