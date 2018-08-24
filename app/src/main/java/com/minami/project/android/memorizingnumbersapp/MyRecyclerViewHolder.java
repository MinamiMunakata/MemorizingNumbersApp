package com.minami.project.android.memorizingnumbersapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Minami on 2018/08/24.
 */

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout holder;
    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        holder = itemView.findViewById(R.id.holder);
        
    }
}
