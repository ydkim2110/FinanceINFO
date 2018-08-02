package com.example.anti2110.financeinfo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.MainActivity;
import com.example.anti2110.financeinfo.Model.Post;
import com.example.anti2110.financeinfo.Post.PostActivity;
import com.example.anti2110.financeinfo.Post.PostDetailActivity;
import com.example.anti2110.financeinfo.R;
import com.example.anti2110.financeinfo.ViewHolder.PostListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter<Post, PostListViewHolder> adapter;

    private FloatingActionButton fab;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        dbRef = FirebaseDatabase.getInstance().getReference();

        recyclerView = (RecyclerView) view.findViewById(R.id.post_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadPostList();

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(getActivity(), PostActivity.class);
                startActivity(postIntent);
            }
        });

        return view;
    }

    private void loadPostList() {
        adapter = new FirebaseRecyclerAdapter<Post, PostListViewHolder>(
                Post.class,
                R.layout.post_list,
                PostListViewHolder.class,
                dbRef.child("financeInfoPost")
        ) {
            @Override
            protected void populateViewHolder(final PostListViewHolder viewHolder, Post model, int position) {

                viewHolder.title.setText(model.getTitle());
                viewHolder.userEmail.setText(model.getId().substring(0, model.getId().indexOf("@")));
                viewHolder.enrollDate.setText(model.getDate().split(" ")[0]);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent postDetailIntent = new Intent(getActivity(), PostDetailActivity.class);
                        postDetailIntent.putExtra("pushID", adapter.getRef(position).getKey());
                        startActivity(postDetailIntent);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
