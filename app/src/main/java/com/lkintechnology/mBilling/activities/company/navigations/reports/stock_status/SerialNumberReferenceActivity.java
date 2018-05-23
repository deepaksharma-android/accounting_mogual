package com.lkintechnology.mBilling.activities.company.navigations.reports.stock_status;

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
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.adapters.SerialNumberReferenceAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.pdf.PdfResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.DeletePurchaseReturnVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.DeletePurchaseVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.sale_return.DeleteSaleReturnVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.DeleteSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.serialnumber.SerialNumberReferenceResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeleteSerialNumber;
import com.lkintechnology.mBilling.utils.EventShowPdf;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SerialNumberReferenceActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.serial_number)
    EditText mSerialNumber;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.main_layout)
    LinearLayout main_layout;
    @Bind(R.id.error_layout)
    LinearLayout error_layout;
    RecyclerView.LayoutManager layoutManager;
    SerialNumberReferenceAdapter mAdapter;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        appUser= LocalRepositories.getAppUser(this);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.serial_ref_no=mSerialNumber.getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(SerialNumberReferenceActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SERIAL_NUMBER_REFERENCE);
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
        });


    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("SERIAL NUMBER REFERENCE");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_serial_number_reference;
    }

    @Subscribe
    public void getSerialNumberReference(SerialNumberReferenceResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            if (response.getItem().getData().size()==0){
                main_layout.setVisibility(View.GONE);
                error_layout.setVisibility(View.VISIBLE);
            }else {
                main_layout.setVisibility(View.VISIBLE);
                error_layout.setVisibility(View.GONE);
            }
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new SerialNumberReferenceAdapter(this,response.getItem().data);
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(),Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void deletevoucher(EventDeleteSerialNumber pos){
        String arr[]=pos.getPosition().split(",");
        String type=arr[0];
        String id=arr[1];
        if(type.equalsIgnoreCase("Sale")){
            appUser.delete_sale_voucher_id= id;
            LocalRepositories.saveAppUser(this,appUser);
            new AlertDialog.Builder(SerialNumberReferenceActivity.this)
                    .setTitle("Delete Sale Voucher")
                    .setMessage("Are you sure you want to delete this Record ?")
                    .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if(isConnected) {
                            mProgressDialog = new ProgressDialog(SerialNumberReferenceActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_SALE_VOUCHER);
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
        else if(type.equalsIgnoreCase("Sale Return")){
            appUser.delete_sale_return_voucher_id= id;
            LocalRepositories.saveAppUser(this,appUser);
            new AlertDialog.Builder(SerialNumberReferenceActivity.this)
                    .setTitle("Delete Sale Return Voucher")
                    .setMessage("Are you sure you want to delete this Record ?")
                    .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if(isConnected) {
                            mProgressDialog = new ProgressDialog(SerialNumberReferenceActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_SALE_RETURN_VOUCHER);
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
        else if(type.equalsIgnoreCase("Purchase")){
            appUser.delete_purchase_voucher_id= id;
            LocalRepositories.saveAppUser(this,appUser);
            new AlertDialog.Builder(SerialNumberReferenceActivity.this)
                    .setTitle("Delete Purchase Voucher")
                    .setMessage("Are you sure you want to delete this Record ?")
                    .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if(isConnected) {
                            mProgressDialog = new ProgressDialog(SerialNumberReferenceActivity.this);
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
        else if(type.equalsIgnoreCase("Purchase Return")){
            appUser.delete_purchase_return_voucher_id = id;
            LocalRepositories.saveAppUser(this, appUser);
            new AlertDialog.Builder(SerialNumberReferenceActivity.this)
                    .setTitle("Delete Purchase Return Voucher")
                    .setMessage("Are you sure you want to delete this Record ?")
                    .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(SerialNumberReferenceActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_PURCHASE_RETURN_VOUCHER);
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
                    })
                    .setNegativeButton(R.string.btn_cancel, null)
                    .show();
        }

    }

    @Subscribe
    public void deletesalevoucherresponse(DeleteSaleVoucherResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SERIAL_NUMBER_REFERENCE);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    @Subscribe
    public void deletesalereturnvoucherresponse(DeleteSaleReturnVoucherResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SERIAL_NUMBER_REFERENCE);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void deletepurchasevoucherresponse(DeletePurchaseVoucherResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SERIAL_NUMBER_REFERENCE);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    @Subscribe
    public void deletepurchasereturnvoucherresponse(DeletePurchaseReturnVoucherResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SERIAL_NUMBER_REFERENCE);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getpdf(EventShowPdf pos){
        String arr[]=pos.getPosition().split(",");
        String type=arr[0];
        String id=arr[1];
        appUser.serial_voucher_id=id;
        appUser.serial_voucher_type=type;
        LocalRepositories.saveAppUser(this,appUser);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(SerialNumberReferenceActivity.this);
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
        }
    }
}