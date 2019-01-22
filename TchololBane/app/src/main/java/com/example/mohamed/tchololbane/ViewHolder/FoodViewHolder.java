package com.example.mohamed.tchololbane.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.tchololbane.Interface.ItemClickListener;
import com.example.mohamed.tchololbane.R;

/**
 * Created by mohamed-kms on 16/01/2018
 **/

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public ImageView food_image;

    private ItemClickListener itemClickListener;
    public FoodViewHolder(View itemView) {
        super(itemView);
        food_name = itemView.findViewById(R.id.food_name);
        food_image = itemView.findViewById(R.id.food_imageView);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
