package com.minami.project.android.memorizingnumbersapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Minami on 2018/08/24.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<ShopItem> shopItems;
    private Context context;

    public MyRecyclerViewAdapter(ArrayList<ShopItem> shopItems) {
        this.shopItems = shopItems;
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.list_view_holder, viewGroup, false);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        if (holder != null){
            holder.getCategory_holder().setText(shopItems.get(position).getCategory());
            holder.getItem_holder().setText(shopItems.get(position).getItem());
            holder.getOrg_holder().setText(shopItems.get(position).getOrg());
            holder.getCode_holder().setText(shopItems.get(position).getCode());
        }
    }

    @Override
    public int getItemCount() {
        if (shopItems != null) {
            return shopItems.size();
        } else {
            return 0;
        }
    }

}
