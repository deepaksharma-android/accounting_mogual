package com.lkintechnology.mBilling.activities.company.navigations.administration.configuration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.lkintechnology.mBilling.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfigurationActivity extends AppCompatActivity {
    @Bind(R.id.voucher_settlement_config)
    LinearLayout mVoucherSettlementLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        ButterKnife.bind(this);


    }
}