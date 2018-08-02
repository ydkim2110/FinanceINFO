package com.example.anti2110.financeinfo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.R;

public class BookmarkListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView companyName, title, startDate, date;

    private ItemClickListener itemClickListener;

    public BookmarkListViewHolder(View itemView) {
        super(itemView);

        companyName = (TextView) itemView.findViewById(R.id.companyName);
        title = (TextView) itemView.findViewById(R.id.title);
        startDate = (TextView) itemView.findViewById(R.id.startDate);
        date = (TextView) itemView.findViewById(R.id.date);

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
