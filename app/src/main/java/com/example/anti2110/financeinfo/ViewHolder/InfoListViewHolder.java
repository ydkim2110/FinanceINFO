package com.example.anti2110.financeinfo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.R;

public class InfoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView category, title, startDate, date;

    private ItemClickListener itemClickListener;

    public InfoListViewHolder(View itemView) {
        super(itemView);

        category = (TextView) itemView.findViewById(R.id.category);
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
