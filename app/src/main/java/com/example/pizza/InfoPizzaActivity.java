package com.example.pizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.pizza.adapters.AdditiveAdapter;
import com.example.pizza.databinding.ActivityInfoPizzaBinding;
import com.example.pizza.entity.Additive;
import com.example.pizza.entity.OrderItem;
import com.example.pizza.entity.Pizza;
import com.example.pizza.entity.Size;
import com.example.pizza.network.AdditiveApi;
import com.example.pizza.network.NetworkService;
import com.example.pizza.network.SizeApi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoPizzaActivity extends AppCompatActivity {

    public static final String KEY_ITEM = "item";
    private ActivityInfoPizzaBinding binding;
    private SizeApi sizeApi;
    private AdditiveApi additiveApi;
    private List<Size> sizes;
    private List<Additive> additives;
    private Set<Additive> checkAdditive;
    private OrderItem item;
    private double price;
    private double sizePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoPizzaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        item = (OrderItem) getIntent().getSerializableExtra(MainActivity.KEY_ITEM);
        Pizza selectedPizza = item.getPizza();

        sizeApi = NetworkService.getInstance().getSizeApi();
        additiveApi = NetworkService.getInstance().getAdditiveApi();

        if (item.getSize()!= null){
            sizePrice = item.getSize().getPrice();
            price = item.getPrice();
            checkAdditive = item.getAdditives();
            binding.btnAddItem.setText("Сохранить изминения");
        } else {
            sizePrice = 0;
            price = selectedPizza.getPrice();
            checkAdditive = new HashSet<>();
        }

        binding.tvNamePizza.setText(selectedPizza.getName());


        binding.tvItemPrice.setText(String.valueOf(price));
        String composition = "Состав: " + selectedPizza.getComposition();
        binding.tvComposition.setText(composition);

        initialSizes();
        initialAdditives();
        binding.btnAddItem.setOnClickListener(view -> {
            RadioButton radioButton = findViewById(binding.rgSizesPizza.getCheckedRadioButtonId());
            Size selectedSize = null;
            for (Size size : sizes) {
                if(radioButton.getText().toString().equals(size.toString())){
                    selectedSize = size;
                }
            }

            item = new OrderItem(selectedPizza, selectedSize, checkAdditive, price);
            Intent intent = new Intent();
            intent.putExtra(KEY_ITEM, item);
            setResult(RESULT_OK, intent);
            finish();
        });

    }

    private void initialAdditives() {
        additiveApi.getAll().enqueue(new Callback<List<Additive>>() {
            @Override
            public void onResponse(Call<List<Additive>> call, Response<List<Additive>> response) {
                additives = response.body();
                AdditiveAdapter adapter = new AdditiveAdapter(InfoPizzaActivity.this, R.layout.additive_item, additives, item, new AdditiveAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        boolean check = false;
                        Additive coincideAdditive = null;
                        for (Additive additive : checkAdditive) {
                            if(additive.getId() == additives.get(position).getId()){
                                price-= additive.getPrice();
                                coincideAdditive = additive;
                                check = true;
                            }
                        }
                        if (check){
                            checkAdditive.remove(coincideAdditive);
                        }
                        if(!check) {
                            checkAdditive.add(additives.get(position));
                            price+=additives.get(position).getPrice();
                        }
                        binding.tvItemPrice.setText(String.valueOf(price));
                    }
                });
                binding.rvAdditives.setAdapter(adapter);
                LinearLayoutManager layoutManager = new GridLayoutManager(InfoPizzaActivity.this, 2);
                binding.rvAdditives.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<Additive>> call, Throwable t) {

            }
        });
    }

    private void initialSizes() {
        sizeApi.getAll().enqueue(new Callback<List<Size>>() {
            @Override
            public void onResponse(Call<List<Size>> call, Response<List<Size>> response) {
                sizes = response.body();
                for (Size size : sizes) {
                    RadioButton rb = new RadioButton(InfoPizzaActivity.this);
                    rb.setText(size.toString());

                    binding.rgSizesPizza.addView(rb);
                    if(item.getSize() == null){
                        if(size.isDefault()){
                            rb.setChecked(true);
                        }
                    } else {
                        if(rb.getText().toString().equals(item.getSize().toString())){
                            rb.setChecked(true);
                        }
                    }

                    rb.setOnClickListener(view -> {
                        price -= sizePrice;
                        price += size.getPrice();
                        sizePrice = size.getPrice();
                        binding.tvItemPrice.setText(String.valueOf(price));
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Size>> call, Throwable t) {

            }
        });
    }
}