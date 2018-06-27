package com.lkintechnology.mBilling.activities.company.transaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.GetPurchaseListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.CreateSaleActivity;
import com.lkintechnology.mBilling.adapters.GetPurchaseListAdapter;
import com.lkintechnology.mBilling.adapters.GetSaleVoucherListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.pdf.PdfResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.DeletePurchaseVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.GetPurchaseVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.DeleteSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.GetSaleVoucherListResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeletePurchaseVoucher;
import com.lkintechnology.mBilling.utils.EventDeleteSaleVoucher;
import com.lkintechnology.mBilling.utils.EventShowPdf;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PurchaseVouchersItemDetailsListActivity extends RegisterAbstractActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    GetPurchaseListAdapter mAdapter;
    @Bind(R.id.sale_type_list_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.total)
    TextView mTotal;
    ArrayList<String> cashInHand = new ArrayList<>();
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    String title;
    public Dialog dialog;
    private DatePickerDialog DatePickerDialog1,DatePickerDialog2;
    private SimpleDateFormat dateFormatter;
    String dateString;
    public static boolean isFromTransactionSaleActivity;
    Boolean forMainLayoutClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_transaction_month_year);

        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);
        isFromTransactionSaleActivity =true;
        appUser.mListMapForItemPurchase.clear();
        appUser.mListMapForBillPurchase.clear();
        LocalRepositories.saveAppUser(this,appUser);

    }

    @Override
    protected void onResume() {
        appUser.mListMapForItemPurchase.clear();
        appUser.mListMapForBillPurchase.clear();
        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(PurchaseVouchersItemDetailsListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PURCHASE_VOUCHERS_ITEM_DETAILS);
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                snackbar.dismiss();
                            }
                        }
                    });
            snackbar.show();
        }
        super.onResume();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_sale_vouchers_item_details_list;
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
        //Spinner spinner = (Spinner) viewActionBar.findViewById(R.id.dashboard_spinner1);
       // spinner.setVisibility(View.VISIBLE);
        actionbarTitle.setText("Purchase Vouchers Item Details");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, TransactionPurchaseActivity.class);
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
        CreateSaleActivity.isForEdit=false;
        Intent intent = new Intent(this, TransactionPurchaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void getPurchaseVouchersItemDetails(GetPurchaseVoucherListResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200) {
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new GetPurchaseListAdapter(this,response.getPurchase_vouchers().data,forMainLayoutClick);
            mRecyclerView.setAdapter(mAdapter);
            Double total=0.0;
            for(int i=0;i<response.getPurchase_vouchers().getData().size();i++){
                total=total+response.getPurchase_vouchers().getData().get(i).getAttributes().getTotal_amount();

            }
            mTotal.setText("Total: "+String.format("%.2f",total));
        }
        else{
            //Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void deletepurchasevoucher(EventDeletePurchaseVoucher pos){
        appUser.delete_purchase_voucher_id= pos.getPosition();
        LocalRepositories.saveAppUser(this,appUser);
        new AlertDialog.Builder(PurchaseVouchersItemDetailsListActivity.this)
                .setTitle("Delete Purchase Voucher")
                .setMessage("Are you sure you want to delete this Record ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(PurchaseVouchersItemDetailsListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_PURCHASE_VOUCHER);
                    }
                    else{
                        snackbar = Snackbar
                                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Boolean isConnected = ConnectivityReceiver.isConnected();
                                        if(isConnected){
                                            snackbar.dismiss();
                                        }
                                    }
                                });
                        snackbar.show();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    @Subscribe
    public void deletepurchasevoucherresponse(DeletePurchaseVoucherResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PURCHASE_VOUCHER);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
           // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void getpdf(EventShowPdf pos){
        String arr[]=pos.getPosition().split(",");
        String type=arr[0];
        if(type.equals("purchase-vouchers")){
            type="Purchase";
        }
        String id=arr[1];
        appUser.serial_voucher_id=id;
        appUser.serial_voucher_type=type;
        LocalRepositories.saveAppUser(this,appUser);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(PurchaseVouchersItemDetailsListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PDF);
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                snackbar.dismiss();
                            }
                        }
                    });
            snackbar.show();
        }
    }


    @Subscribe
    public void showpdf(PdfResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Intent intent = new Intent(getApplicationContext(), TransactionPdfActivity.class);
            intent.putExtra("company_report", response.getHtml());
            startActivity(intent);
        }else {
            Helpers.dialogMessage(this,response.getMessage());
        }
    }
}
