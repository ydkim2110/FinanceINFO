package com.example.anti2110.financeinfo.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.financeinfo.MainActivity;
import com.example.anti2110.financeinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button loginBtn, registerBtn;

    private ProgressBar progressBar;
    private TextView progressBarTextView;

    private FirebaseAuth auth;

    private long lastTimeBackPressed;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        progressBarTextView = (TextView) findViewById(R.id.login_textview);

        progressBar.setVisibility(View.INVISIBLE);
        progressBarTextView.setVisibility(View.INVISIBLE);

        editTextEmail = (EditText) findViewById(R.id.login_email_text);
        editTextPassword = (EditText) findViewById(R.id.login_password_text);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        registerBtn = (Button) findViewById(R.id.login_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

    private void loginUser() {

        progressBar.setVisibility(View.VISIBLE);
        progressBarTextView.setVisibility(View.VISIBLE);

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

       auth.signInWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()) {
                           progressBar.setVisibility(View.INVISIBLE);
                           progressBarTextView.setVisibility(View.INVISIBLE);

                           Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(intent);
                           finish();

                       } else {
                           progressBar.setVisibility(View.INVISIBLE);
                           progressBarTextView.setVisibility(View.INVISIBLE);
                           Toast.makeText(LoginActivity.this, "로그인 실패!", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

    }

}
