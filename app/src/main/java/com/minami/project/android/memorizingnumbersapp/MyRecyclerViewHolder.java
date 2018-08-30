package com.minami.project.android.memorizingnumbersapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Minami on 2018/08/24.
 */

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView category_holder;
    private TextView item_holder;
    private TextView org_holder;
    private TextView code_holder;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        category_holder = itemView.findViewById(R.id.category_holder);
        item_holder = itemView.findViewById(R.id.item_holder);
        org_holder = itemView.findViewById(R.id.org_holder);
        code_holder = itemView.findViewById(R.id.code_holder);
    }

    public TextView getCategory_holder() {
        return category_holder;
    }

    public TextView getItem_holder() {
        return item_holder;
    }

    public TextView getOrg_holder() {
        return org_holder;
    }

    public TextView getCode_holder() {
        return code_holder;
    }
}
