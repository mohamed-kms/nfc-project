package com.example.mohamed.tchololbane.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.tchololbane.Interface.ItemClickListener;
import com.example.mohamed.tchololbane.R;

/**
 * Created by mohamed-kms on 25/06/18
 **/

// Class to be used with FirebaseUI
public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        textMenuName = (TextView)itemView.findViewById(R.id.menu_name);
        imageView = (ImageView)itemView.findViewById(R.id.menu_imageView);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
