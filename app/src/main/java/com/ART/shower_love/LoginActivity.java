package com.ART.shower_love;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
     private EditText leEmail;
     private EditText lepassword;
     private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginbutton = findViewById(R.id.loginButton);
        TextView new_member = findViewById(R.id.nmember);
        leEmail = findViewById(R.id.leEmailAddress);
        lepassword= findViewById(R.id.lePassword);
        fAuth=FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            if (fAuth.getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                finish();
            } else if(!(fAuth.getCurrentUser().isEmailVerified())) {
                Toast.makeText(getApplicationContext(), "verify your email", Toast.LENGTH_SHORT).show();

            }


        }
        final ProgressBar lprogressbar = findViewById(R.id.Login_loading);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lEnter_email = leEmail.getText().toString();
                String lEnter_password = lepassword.getText().toString();
                if(lEnter_email.isEmpty() || lEnter_password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please Enter the Email and Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!lEnter_email.contains("@")|| Char_Count(lEnter_email, '@')>=2){
                    Toast.makeText(LoginActivity.this,"Please enter proper format of email",Toast.LENGTH_SHORT).show();
                    return;
                }
                lprogressbar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(lEnter_email,lEnter_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(fAuth.getCurrentUser().isEmailVerified()){
                            //login authentication
                            lprogressbar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"log in is successful ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));}
                            else{
                                Toast.makeText(LoginActivity.this,"Please verify your email ",Toast.LENGTH_SHORT).show();
                            }
                        }
                        //catching error
                        else {
                            Toast.makeText(getApplicationContext(),"Error!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            lprogressbar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
        new_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Registration.class));
                finish();
            }
        });
    }
    //to count is email contain more the one '@'
    private int Char_Count(String Incount,char tocount){
        char[] strArray = Incount.toCharArray();
        int count=0;
        for(char c: strArray){
            if(c==tocount){
                count++;
            }
        }
        return count;

    }


}