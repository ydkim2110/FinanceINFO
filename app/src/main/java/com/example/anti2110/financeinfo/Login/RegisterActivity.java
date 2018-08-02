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

import com.example.anti2110.financeinfo.Model.User;
import com.example.anti2110.financeinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextName;
    private Button buttonRegister;


    private ProgressBar progressBar;
    private TextView progressBarTextview;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        progressBar = (ProgressBar) findViewById(R.id.register_progressbar);
        progressBarTextview = (TextView) findViewById(R.id.register_progressbar_text);

        progressBar.setVisibility(View.INVISIBLE);
        progressBarTextview.setVisibility(View.INVISIBLE);

        editTextEmail = (EditText) findViewById(R.id.register_email_text);
        editTextPassword = (EditText) findViewById(R.id.register_password_text);
        editTextConfirmPassword = (EditText) findViewById(R.id.register_password_confirm_text);
        editTextName = (EditText) findViewById(R.id.register_name_text);

        buttonRegister = (Button) findViewById(R.id.register_btn);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBarTextview.setVisibility(View.VISIBLE);

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if(password.length() > 7) {
                    if(password.equals(confirmPassword)) {
                        registerUser(email, password);
                    } else {
                        Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBarTextview.setVisibility(View.INVISIBLE);
                    }
                } else  {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 8자리 이상 입력하세요.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBarTextview.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void registerUser(String email, String password) {
       auth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()) {
                           progressBar.setVisibility(View.INVISIBLE);
                           progressBarTextview.setVisibility(View.INVISIBLE);

                           sendVerificationEmail();

                           FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                           String userId  = currentUser.getUid();

                           saveUserInfoDatabase(userId);

                       } else {
                           progressBar.setVisibility(View.INVISIBLE);
                           progressBarTextview.setVisibility(View.INVISIBLE);
                           Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

    }

    private void sendVerificationEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null) {
            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {

                    } else {

                    }
                }
            });
        }

    }

    private void saveUserInfoDatabase(String userId) {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String name = editTextName.getText().toString();

        User user = new User(email, password, name);

        dbRef.child("financeInfoUsers").child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다. 이메일 인증을 해주세요.", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });
    }

}
