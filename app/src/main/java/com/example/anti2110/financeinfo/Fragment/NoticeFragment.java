package com.example.anti2110.financeinfo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anti2110.financeinfo.InfoDetailActivity;
import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.Model.Info;
import com.example.anti2110.financeinfo.R;
import com.example.anti2110.financeinfo.ViewHolder.InfoListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NoticeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Info, InfoListViewHolder> mAdapter;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new FirebaseRecyclerAdapter<Info, InfoListViewHolder>(
                Info.class,
                R.layout.info_list,
                InfoListViewHolder.class,
                dbRef.child("financeInfoList")
        ) {
            @Override
            protected void populateViewHolder(final InfoListViewHolder viewHolder, final Info model, int position) {
                viewHolder.category.setText("["+model.getCategory()+"]");
                viewHolder.title.setText(model.getTitle());
                viewHolder.startDate.setText(model.getStartDate());
                viewHolder.date.setText(model.getEndDate());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                        intent.putExtra("categoryId", mAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
