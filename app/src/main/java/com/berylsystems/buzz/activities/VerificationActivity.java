package com.berylsystems.buzz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.berylsystems.buzz.R;

public class VerificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
    }
    public void resend(View v){
        startActivity(new Intent(getApplicationContext(),NewPasswordActivity.class));
    }
    public void changeMobileNumber(View v){
        startActivity(new Intent(getApplicationContext(),ChangeMobileActivity.class));
    }
    public void submit(View v){
        startActivity(new Intent(getApplicationContext(),LandingPageActivity.class));
    }
}