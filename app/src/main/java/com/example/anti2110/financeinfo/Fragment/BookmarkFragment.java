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
import android.widget.ProgressBar;

import com.example.anti2110.financeinfo.Detail.InfoDetailActivity;
import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.Model.Info;
import com.example.anti2110.financeinfo.R;
import com.example.anti2110.financeinfo.ViewHolder.BookmarkListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BookmarkFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter<Info, BookmarkListViewHolder> adapter;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseUser currentUser;

    private ProgressBar bookmark_progressbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        bookmark_progressbar = (ProgressBar) view.findViewById(R.id.bookmark_progressbar);

        initWidgets(view);
        loadInfoList();

        return view;
    }

    private void loadInfoList() {
        adapter = new FirebaseRecyclerAdapter<Info, BookmarkListViewHolder>(
                Info.class,
                R.layout.info_list,
                BookmarkListViewHolder.class,
                dbRef.child("financeInfoList").orderByChild("id").equalTo("02")
        ) {
            @Override
            protected void populateViewHolder(BookmarkListViewHolder viewHolder, Info model, int position) {

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()>0){
                            bookmark_progressbar.setVisibility(View.INVISIBLE);
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
                        intent.putExtra("categoryId", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void initWidgets(View view) {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = (RecyclerView) view.findViewById(R.id.bookmark_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    }

}
