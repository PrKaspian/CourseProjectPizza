package com.example.pizza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza.R;
import com.example.pizza.entity.OrderItem;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>{
    private Context context;
    private int resource;
    private List<OrderItem> items;
    private final ClickListener clickListener;

    public OrderItemAdapter(Context context, int resource, List<OrderItem> items, ClickListener clickListener) {
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(resource, parent, false);
        OrderItemAdapter.OrderItemViewHolder orderItemViewHolder = new OrderItemAdapter.OrderItemViewHolder(container);
        return orderItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        String name = items.get(position).getPizza().getName();
        String size = items.get(position).getSize().getName();
        String price = String.valueOf(items.get(position).getPrice());
        holder.tvPizzaName.setText(name);
        holder.tvPizzaSize.setText(size);
        holder.tvPizzaPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvPizzaName, tvPizzaSize, tvPizzaPrice;
        public OrderItemViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            tvPizzaName = itemView.findViewById(R.id.tvPizzaName);
            tvPizzaSize = itemView.findViewById(R.id.tvPizzaSize);
            tvPizzaPrice = itemView.findViewById(R.id.tvPizzaPrice);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if(position >= 0){
                clickListener.onItemClick(position, view);
            }
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
