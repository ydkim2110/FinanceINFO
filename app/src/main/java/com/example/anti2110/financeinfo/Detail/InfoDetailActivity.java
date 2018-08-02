package com.example.anti2110.financeinfo.Detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.financeinfo.Model.Info;
import com.example.anti2110.financeinfo.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class InfoDetailActivity extends AppCompatActivity {

    private TextView companyName, title;
    private ImageView imageUrl;
    private Toolbar toolbar;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseUser currentUser;

    String categoryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        loadToolbar();
        initWidgets();

        if(getIntent() != null) {
            categoryId = getIntent().getStringExtra("categoryId");
        }
        if(!categoryId.isEmpty()) {
            loadDetailInfo(categoryId);
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_bookmark:
                showBookmarkDialog();
                break;
        }
        return true;
    }

    private void showBookmarkDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("북마크 추가");
        alertDialog.setMessage("북마크 추가하시겠습니까?");

        alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                addBookmark();
            }
        });
        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    private void addBookmark() {
        Map<String, String> bookmarkId = new HashMap<>();
        bookmarkId.put("bookmarkId", getIntent().getStringExtra("categoryId"));
        dbRef.child("financeInfoBookmark").child(currentUser.getUid()).child(getIntent().getStringExtra("categoryId"))
                .setValue(bookmarkId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(InfoDetailActivity.this, "북마크 추가 성공", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDetailInfo(String categoryId) {
        dbRef.child("financeInfoList").child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Info info = dataSnapshot.getValue(Info.class);

                companyName.setText(info.getCompanyName());
                title.setText(info.getTitle());
                Picasso.get().load(info.getImageUrl()).placeholder(R.drawable.ic_launcher_background).into(imageUrl);

                imageUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent fullScreenIntent = new Intent(InfoDetailActivity.this, FullScreenImageActivity.class);
                        fullScreenIntent.setData(Uri.parse(info.getImageUrl()));
                        startActivity(fullScreenIntent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initWidgets() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        companyName = (TextView) findViewById(R.id.companyName);
        title = (TextView) findViewById(R.id.title);
        imageUrl = (ImageView) findViewById(R.id.image_sub);
    }

}
