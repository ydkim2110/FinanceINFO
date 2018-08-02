package com.example.anti2110.financeinfo.Post;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anti2110.financeinfo.Model.User;
import com.example.anti2110.financeinfo.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FirebaseUser currentUser;
    private DatabaseReference dbRef;

    private EditText editTextTile, editTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();

        editTextTile = (EditText) findViewById(R.id.post_title);
        editTextContent = (EditText) findViewById(R.id.post_content);

        loadToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.post_save) {
            showConfirmSaveMessage();
        }
        return true;
    }

    private void showConfirmSaveMessage() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("글 저장");
        alertDialog.setMessage("글을 저장할까요?");

        alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                String title = editTextTile.getText().toString();
                String content = editTextContent.getText().toString();

                savePost(title, content);

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

    private void savePost(String title, String content) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = s.format(new Date());

        Map<String, String> postMap = new HashMap<>();
        postMap.put("title", title);
        postMap.put("content", content);
        postMap.put("id", currentUser.getEmail());
        postMap.put("date", format);

        dbRef.child("financeInfoPost").push().setValue(postMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PostActivity.this, "글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
        });
    }

    private void loadToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar_layout);
        toolbar.setTitle("Post Your Think");
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
