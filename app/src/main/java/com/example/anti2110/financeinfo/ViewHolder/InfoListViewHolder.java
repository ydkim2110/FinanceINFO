package com.example.anti2110.financeinfo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.Model.Info;
import com.example.anti2110.financeinfo.R;

import java.util.List;

public class InfoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView companyName, title, startDate, date;
    public ImageView imageUrl;

    private ItemClickListener itemClickListener;

    public InfoListViewHolder(View itemView) {
        super(itemView);

        companyName = (TextView) itemView.findViewById(R.id.companyName);
        title = (TextView) itemView.findViewById(R.id.title);
        startDate = (TextView) itemView.findViewById(R.id.startDate);
        date = (TextView) itemView.findViewById(R.id.date);
        imageUrl = (ImageView) itemView.findViewById(R.id.image_sub);

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
