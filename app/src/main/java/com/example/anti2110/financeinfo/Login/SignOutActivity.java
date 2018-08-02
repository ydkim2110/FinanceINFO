package com.example.anti2110.financeinfo.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.anti2110.financeinfo.MainActivity;
import com.example.anti2110.financeinfo.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignOutActivity extends AppCompatActivity {

    private Button signOutBtn;

    private ProgressBar progressBar;
    private TextView progressBarText;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);

        auth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.sign_out_progressbar);
        progressBarText = (TextView) findViewById(R.id.sign_out_progressbar_text);
        progressBar.setVisibility(View.INVISIBLE);
        progressBarText.setVisibility(View.INVISIBLE);

        signOutBtn = (Button) findViewById(R.id.sign_out_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBarText.setVisibility(View.VISIBLE);
                auth.signOut();
                Intent loginIntent = new Intent(SignOutActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
            }
        });
    }
}
