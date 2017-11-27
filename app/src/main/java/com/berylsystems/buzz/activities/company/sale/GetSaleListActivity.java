package com.berylsystems.buzz.activities.company.sale;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.berylsystems.buzz.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GetSaleListActivity extends AppCompatActivity {

    @Bind(R.id.floating_button)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sale_list);
        ButterKnife.bind(this);
    }

   public void add(View view){
        startActivity(new Intent(getApplicationContext(),CreateSaleActivity.class));
    }

}
