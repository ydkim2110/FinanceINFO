package com.example.anti2110.financeinfo.Post;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.anti2110.financeinfo.Model.Post;
import com.example.anti2110.financeinfo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView postDetailTitle, postDetailContent;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        dbRef = FirebaseDatabase.getInstance().getReference();
        postDetailTitle = (TextView) findViewById(R.id.post_detail_title);
        postDetailContent = (TextView) findViewById(R.id.post_detail_content);

        loadToolbar();
        String pushID = getIntent().getStringExtra("pushID");
        if(pushID != null) {
            showPostDetail(pushID);
        }

    }

    private void showPostDetail(String pushID) {
        dbRef.child("financeInfoPost").child(pushID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);

                postDetailTitle.setText(post.getTitle());
                postDetailContent.setText(post.getContent());
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar_layout);
        toolbar.setTitle("Post Detail");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
