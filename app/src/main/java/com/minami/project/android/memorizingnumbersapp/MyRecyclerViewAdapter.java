package com.minami.project.android.memorizingnumbersapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Minami on 2018/08/24.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<ShopItem> mItem;
    private Context context;
    private OnRecyclerListner mListener;

    public MyRecyclerViewAdapter(ArrayList<ShopItem> mItems, Context context, OnRecyclerListner listener) {
        this.mItem = mItems;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_view_holder, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



    }




    @Override
    public int getItemCount() {
        if (mItem != null) {
            return mItem.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView category_holder;
        TextView item_holder;
        TextView org_holder;
        TextView code_holder;


        public ViewHolder(View itemView) {
            super(itemView);
            category_holder = itemView.findViewById(R.id.category_holder);
            item_holder = itemView.findViewById(R.id.item_holder);
            org_holder = itemView.findViewById(R.id.org_holder);
            code_holder = itemView.findViewById(R.id.code_holder);
        }
    }
}
