package com.berylsystems.buzz.activities.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.dashboard.TransactionDashboardActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FirstPageActivity extends BaseActivityCompany {

    @Bind(R.id.transaction_button)
    Button transactionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        setAddCompany(0);
        setAppBarTitleCompany(1, "BAHI KHATA");

        ButterKnife.bind(this);
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), TransactionDashboardActivity.class);
                startActivity(intent);
            }
        });

    }
}
