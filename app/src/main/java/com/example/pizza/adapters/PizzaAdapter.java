package com.example.pizza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza.R;
import com.example.pizza.entity.Pizza;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private Context context;
    private int resource;
    private List<Pizza> pizzas;
    private final ClickListener clickListener;

    public PizzaAdapter(Context context, int resource, List<Pizza> pizzas, ClickListener clickListener) {
        this.context = context;
        this.resource = resource;
        this.pizzas = pizzas;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(resource, parent, false);
        PizzaAdapter.PizzaViewHolder pizzaViewHolder = new PizzaAdapter.PizzaViewHolder(container);
        return pizzaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        String name = pizzas.get(position).getName();
        String price = String.valueOf(pizzas.get(position).getPrice());
        int image = pizzas.get(position).getImage();
        //int i = 2131165407;
        holder.tvName.setText(name);
        holder.tvPrice.setText(price);
        holder.ivImage.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    class PizzaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvPrice;
        ImageView ivImage;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvName = itemView.findViewById(R.id.tvNamePizza);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivImage = itemView.findViewById(R.id.ivImage);

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
