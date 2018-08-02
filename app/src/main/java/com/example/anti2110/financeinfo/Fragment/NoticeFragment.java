package com.example.anti2110.financeinfo.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anti2110.financeinfo.Detail.InfoDetailActivity;
import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.Model.Info;
import com.example.anti2110.financeinfo.R;
import com.example.anti2110.financeinfo.ViewHolder.InfoListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;


public class NoticeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager;
    private InfoListViewHolder infoListViewHolder;
    private FirebaseRecyclerAdapter<Info, InfoListViewHolder> mAdapter;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private ProgressBar notice_progressbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notice, container, false);
        notice_progressbar = (ProgressBar) view.findViewById(R.id.notice_progressbar);

        initWidgets(view);
        loadInfoList();

        return view;
    }


    private void loadInfoList() {
        mAdapter = new FirebaseRecyclerAdapter<Info, InfoListViewHolder>(
                Info.class,
                R.layout.info_list,
                InfoListViewHolder.class,
                dbRef.child("financeInfoList")
        ) {
            @Override
            protected void populateViewHolder(final InfoListViewHolder viewHolder, final Info model, int position) {

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()>0){
                            notice_progressbar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                viewHolder.companyName.setText("["+model.getCompanyName()+"]");
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
    }

    private void initWidgets(View view) {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


}
