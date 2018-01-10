package com.berylsystems.buzz.activities.company.navigation.reports.account_group;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.FirstPageActivity;
import com.berylsystems.buzz.activities.company.navigation.reports.outstanding.AmountReceivablesListActivity;
import com.berylsystems.buzz.activities.company.navigation.reports.purchaseanalysis.AnalysisPurchaseReportActivity;
import com.berylsystems.buzz.activities.company.navigation.reports.saleanalysis.AnalysisSaleReportActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AcountGroupActivity extends AppCompatActivity {

    @Bind(R.id.ledger)
    LinearLayout ledger;
    @Bind(R.id.cash_bank)
    LinearLayout casgBank;
    @Bind(R.id.opening_stock_group)
    LinearLayout openingStockGroup;
    @Bind(R.id.pdc_detail)
    LinearLayout pdcDetails;
    @Bind(R.id.amount_receivable)
    LinearLayout amountReceivable;
    @Bind(R.id.amount_payble)
    LinearLayout amountPayable;
    @Bind(R.id.sale_report_layout)
    LinearLayout mSaleReportLayout;
    @Bind(R.id.sale_report)
    TextView mSaleReport;
    @Bind(R.id.purchase_report_layout)
    LinearLayout mPurchaseReportLayout;
    @Bind(R.id.purchase_report)
    TextView mPurchaseReport;
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
    @Bind(R.id.gstr1)
    LinearLayout mGstr1;
    @Bind(R.id.gstr2)
    LinearLayout mGstr2;
    @Bind(R.id.gstr_e1)
    LinearLayout mGstr_e1;
    @Bind(R.id.gstr_e2)
    LinearLayout mGstr_e2;
    @Bind(R.id.balance_sheet_layout)
    LinearLayout mBalance_sheet_layout;
    @Bind(R.id.profit_loss_report_layout)
    LinearLayout mProfit_loss_report_layout;




    AppUser appUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_group);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();

        ledger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledger_txt.setTextColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(), LedgerActivity.class));
            }
        });

        openingStockGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledger_txt.setTextColor(Color.WHITE);
                Intent intent=new Intent(getApplicationContext(), StocksReportsActivity.class);
                startActivity(intent);
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
                appUser.account_master_group = "Sundry Debtors";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent intent=new Intent(getApplicationContext(),AmountReceivablesListActivity.class);
                intent.putExtra("amounReceivabletForDirect",true);
                startActivity(intent);
            }
        });
        amountPayable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount_payble_txt.setTextColor(Color.WHITE);
                appUser.account_master_group = "Sundry Creditors";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent intent=new Intent(getApplicationContext(),AmountReceivablesListActivity.class);
                intent.putExtra("amountPaybleForDirect",true);
                startActivity(intent);
            }
        });
        mSaleReportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaleReport.setTextColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(), AnalysisSaleReportActivity.class));
            }
        });
        mPurchaseReportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPurchaseReport.setTextColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(), AnalysisPurchaseReportActivity.class));
            }
        });

        mGstr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AcountGroupActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();


            }
        });

        mGstr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AcountGroupActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();


            }
        });

        mGstr_e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AcountGroupActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();


            }
        });

        mGstr_e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AcountGroupActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();


            }
        });

        mBalance_sheet_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AcountGroupActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();


            }
        });

        mProfit_loss_report_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AcountGroupActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();


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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("REPORTS ");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, FirstPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FirstPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
