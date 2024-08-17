package com.example.orderfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.orderfood.Domain.Foods;
import com.example.orderfood.Helper.ChangeNumberItemsListener;
import com.example.orderfood.Helper.ManagmentCart;
import com.example.orderfood.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {
    ArrayList<Foods> list;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;
    public CartAdapter(ArrayList<Foods> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View infalte = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new viewHolder(infalte);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.feeEachItem.setText("$"+list.get(position).getPrice());
        holder.totalEachItem.setText(list.get(position).getNumberInCart() + " * $" + (
                list.get(position).getPrice()));
        holder.num.setText(list.get(position).getNumberInCart()+"");  // "" để ép kiểu về string

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        holder.plusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.plusNumberItem(list, position, () -> {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                });
            }
        });
        holder.minusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.minusNumberItem(list,position, () -> {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem, plusCartBtn, minusCartBtn;
        ImageView pic;
        TextView totalEachItem, num;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tilteTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            plusCartBtn = itemView.findViewById(R.id.plusCartBtn);
            minusCartBtn = itemView.findViewById(R.id.minusCartBtn);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItem);
        }
    }
}
