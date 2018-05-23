package com.lkintechnology.mBilling.activities.company.navigations.reports;

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
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.account_group.CashBankActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.account_group.LedgerActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.account_group.PdcActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.financialreports.BalanceSheetActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.financialreports.ProfitAndLossActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.gstr3b.Gstr3bReportActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.gstreturn.PurchaseRegisterActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.gstreturn.SaleRegisterActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.outstanding.AmountReceivablesListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.purchaseanalysis.AnalysisPurchaseReportActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.saleanalysis.AnalysisSaleReportActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.stock_status.ItemWiseReportActicity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.stock_status.SerialNumberReferenceActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.stock_status.StocksReportsActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportsActivity extends AppCompatActivity {

    @Bind(R.id.ledger)
    LinearLayout ledger;
    @Bind(R.id.cash_bank)
    LinearLayout casgBank;
    @Bind(R.id.opening_stock_group)
    LinearLayout openingStockGroup;
    @Bind(R.id.serial_number_reference)
    LinearLayout mSerialNumberReference;
    @Bind(R.id.item_wise_report)
    LinearLayout mItemWiseReport;
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
    @Bind(R.id.gstrb3)
    LinearLayout mGstr3B;
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
        mSerialNumberReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SerialNumberReferenceActivity.class));
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
                new AlertDialog.Builder(ReportsActivity.this)
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
                new AlertDialog.Builder(ReportsActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();


            }
        });

        mGstr3B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Gstr3bReportActivity.class);
                startActivity(intent);


            }
        });

        mGstr_e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SaleRegisterActivity.class);
                startActivity(intent);
            }
        });

        mGstr_e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PurchaseRegisterActivity.class);
                startActivity(intent);
            }
        });

        mBalance_sheet_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BalanceSheetActivity.class));

            }
        });

        mProfit_loss_report_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfitAndLossActivity.class);
                startActivity(intent);
            }
        });

        mItemWiseReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ItemWiseReportActicity.class));
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
