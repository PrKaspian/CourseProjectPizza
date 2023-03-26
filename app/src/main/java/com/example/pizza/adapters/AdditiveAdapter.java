package com.example.pizza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza.R;
import com.example.pizza.entity.Additive;
import com.example.pizza.entity.OrderItem;

import java.util.List;
import java.util.Set;

public class AdditiveAdapter extends RecyclerView.Adapter<AdditiveAdapter.AdditiveViewHolder>{

    private Context context;
    private int resource;
    private List<Additive> additives;
    private ClickListener clickListener;
    private OrderItem item;

    public AdditiveAdapter(Context context, int resource, List<Additive> additives, OrderItem item, ClickListener clickListener) {
        this.context = context;
        this.resource = resource;
        this.additives = additives;
        this.item = item;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public AdditiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(resource, parent, false);
        AdditiveAdapter.AdditiveViewHolder additiveViewHolder = new AdditiveAdapter.AdditiveViewHolder(container);
        return additiveViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdditiveViewHolder holder, int position) {
        String additive = additives.get(position).toString();
        holder.cbAdditive.setText(additive);
        if (item.getSize() != null && item.getAdditives().size() > 0){
            Set<Additive> itemAdditives = item.getAdditives();
            for (Additive itemAdditive : itemAdditives) {
                if (holder.cbAdditive.getText().toString().equals(itemAdditive.toString())){
                    holder.cbAdditive.setChecked(true);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return additives.size();
    }



    class AdditiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox cbAdditive;

        public AdditiveViewHolder(@NonNull View itemView) {
            super(itemView);
            cbAdditive = itemView.findViewById(R.id.cbAdditive);
            cbAdditive.setOnClickListener(this);
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
