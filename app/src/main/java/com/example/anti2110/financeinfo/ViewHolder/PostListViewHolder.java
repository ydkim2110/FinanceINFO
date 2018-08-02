package com.example.anti2110.financeinfo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.R;

public class PostListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title;
    public TextView userEmail;
    public TextView enrollDate;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PostListViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.post_list_title);
        userEmail = (TextView) itemView.findViewById(R.id.post_list_email);
        enrollDate = (TextView) itemView.findViewById(R.id.post_list_date);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
