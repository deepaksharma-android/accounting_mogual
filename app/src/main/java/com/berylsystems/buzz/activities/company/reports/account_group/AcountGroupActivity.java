package com.berylsystems.buzz.activities.company.reports.account_group;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.autstanding.AmountPaybleActivity;
import com.berylsystems.buzz.activities.company.autstanding.AmountReceivableActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AcountGroupActivity extends AppCompatActivity {

    @Bind(R.id.ledger)
    LinearLayout ledger;
    @Bind(R.id.cash_bank)
    LinearLayout casgBank;
    @Bind(R.id.pdc_detail)
    LinearLayout pdcDetails;
    @Bind(R.id.amount_receivable)
    LinearLayout amountReceivable;
    @Bind(R.id.amount_payble)
    LinearLayout amountPayable;
    @Bind(R.id.ledger_txt)
    TextView ledger_txt;
    @Bind(R.id.cash_bank_txt)
    TextView cash_bank_txt;
    @Bind(R.id.pdc_detail_txt)
    TextView pcd_detail_txt;
    @Bind(R.id.amount_receivable_txt)
    TextView amount_receivable_txt;
    @Bind(R.id.amount_payble_txt)
    TextView amount_payble_txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_group);
        ButterKnife.bind(this);

        initActionbar();

        ledger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledger_txt.setTextColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(), LedgerActivity.class));
            }
        });

        casgBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cash_bank_txt.setTextColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(), CashBankActivity.class));
            }
        });
        pdcDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pcd_detail_txt.setTextColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(), PdcActivity.class));
            }
        });
        amountReceivable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount_receivable_txt.setTextColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(), AmountReceivableActivity.class));
            }
        });
        amountPayable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount_payble_txt.setTextColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(), AmountPaybleActivity.class));
            }
        });

        ledger_txt.setTextColor(Color.BLACK);
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("ACCOUNT GROUP ");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ledger_txt.setTextColor(Color.BLACK);
        cash_bank_txt.setTextColor(Color.BLACK);
        pcd_detail_txt.setTextColor(Color.BLACK);
        amount_receivable_txt.setTextColor(Color.BLACK);
        amount_payble_txt.setTextColor(Color.BLACK);
    }
}
