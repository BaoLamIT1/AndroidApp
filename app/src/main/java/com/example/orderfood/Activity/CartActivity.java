package com.example.orderfood.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Adapter.CartAdapter;
import com.example.orderfood.Helper.ChangeNumberItemsListener;
import com.example.orderfood.Helper.ManagmentCart;
import com.example.orderfood.R;
import com.example.orderfood.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;

    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        calculateCart();
        initList();
    }
    private void initList(){
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        }else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        binding.cardView.setAdapter(adapter);
    }
    private void setVariable(){
        binding.backBtn.setOnClickListener(v -> finish());

    }
    private void calculateCart(){
        double percentTax = 0.02;    // Tax = 2%
        double delivery = 10;       // Delivery = 10 $

        tax = Math.round(managmentCart.getTotalFee() * percentTax *100.0)/100;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) *100.0)/100;
        double itemTotal = Math.round(managmentCart.getTotalFee() *100.0)/100;
        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.taxTxt.setText("$"+tax);
        binding.deliverytxt.setText("$"+delivery);
        binding.totalTxt.setText("$"+total);

    }

}