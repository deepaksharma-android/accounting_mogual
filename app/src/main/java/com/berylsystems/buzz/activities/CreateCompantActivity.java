package com.berylsystems.buzz.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;

public class CreateCompantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company);
    }
}