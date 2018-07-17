package com.example.anti2110.financeinfo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anti2110.financeinfo.Model.Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoDetailActivity extends AppCompatActivity {

    private ImageView backArrow;
    private ImageView bookmark;

    private TextView companyName, title;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    String categoryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        backArrow = (ImageView) findViewById(R.id.detail_toolbar_backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bookmark = (ImageView) findViewById(R.id.detail_bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookmarkDialog();
            }
        });

        initWidgets();

        if(getIntent() != null) {
            categoryId = getIntent().getStringExtra("categoryId");
        }
        if(!categoryId.isEmpty()) {
            loadDetailInfo(categoryId);
        }

    }

    private void showBookmarkDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("북마크 추가");
        alertDialog.setMessage("북마크 추가하시겠습니까?");

        alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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

    private void loadDetailInfo(String categoryId) {
        dbRef.child("financeInfoList").child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Info info = dataSnapshot.getValue(Info.class);

                companyName.setText(info.getCompanyName());
                title.setText(info.getTitle());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initWidgets() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        companyName = (TextView) findViewById(R.id.companyName);
        title = (TextView) findViewById(R.id.title);
    }
}
