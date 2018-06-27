package com.lkintechnology.mBilling.activities.company.transaction.receiptvoucher;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PdfPrint;
import android.print.PrintAttributes;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.account_group.PdcActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.CreatePurchaseReturnActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.CreateSaleActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale_return.CreateSaleReturnActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.CreateReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.DeleteReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.EditReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.GetReceiptVoucherDetailsResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeleteReceipt;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.ImagePicker;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

public class
CreateReceiptVoucherActivity extends RegisterAbstractActivity implements View.OnClickListener {

    @Bind(R.id.date_pdc_textview)
    TextView date_pdc_textview;
    @Bind(R.id.date_pdc_layout)
    LinearLayout date_pdc_layout;
    @Bind(R.id.date_pdc)
    TextView set_date_pdc;
    @Bind(R.id.received_from_layout)
    LinearLayout received_from_layout;
    @Bind(R.id.received_by_layout)
    LinearLayout received_by_layout;
    @Bind(R.id.received_from)
    TextView received_from;
    @Bind(R.id.received_by)
    TextView received_by;
    @Bind(R.id.date)
    TextView set_date;
    @Bind(R.id.browse_image)
    LinearLayout mBrowseImage;
    @Bind(R.id.selected_image)
    ImageView mSelectedImage;
    @Bind(R.id.submit_layout)
    LinearLayout submit_layout;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    @Bind(R.id.transaction_spinner)
    Spinner voucher_series_spinner;
    @Bind(R.id.transaction_spinner1)
    Spinner type_spinner;
    @Bind(R.id.transaction_spinner2)
    Spinner gst_nature_spinner;
    @Bind(R.id.vouchar_no)
    TextView voucher_no;
    @Bind(R.id.transaction_amount)
    EditText transaction_amount;
    @Bind(R.id.transaction_narration)
    EditText transaction_narration;
    Snackbar snackbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.arrow)
    LinearLayout arrow;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1, DatePickerDialog2;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    InputStream inputStream = null;
    ProgressDialog mProgressDialog;
    Boolean fromReceiptVoucher;
    Boolean fromPdcReceiptVoucher;
    String encodedString;
    String title;
    AppUser appUser;
    public Boolean boolForReceivedFrom = false;
    public Boolean boolForReceivedBy = false;
    String account_id, account, from;
    public static int intStartActivityForResult = 0;
    public Bundle bundle;
    Bitmap photo;
    public static int iconHandlerVariable = 0;
    WebView mPdf_webview;
    private Uri imageToUploadUri;
    String attachemnt;
    public String spinnergstnature,state;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_bank_case_deposit);
        ButterKnife.bind(this);
        iconHandlerVariable = 0;
        initActionbar();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        appUser = LocalRepositories.getAppUser(this);
        appUser.mListMapForItemReceipt.clear();
        LocalRepositories.saveAppUser(this,appUser);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            account = bundle.getString("account");
            account_id = bundle.getString("account_id");
            appUser.receipt_received_from_id = account_id;
            from = bundle.getString("from");
            LocalRepositories.saveAppUser(this, appUser);
            received_from.setText(account);
        }
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();
        appUser.voucher_type = "Receipt";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

       /* received_from.setText(appUser.receipt_received_from_name);
        received_by.setText(appUser.receipt_received_by_name);*/
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setLogo(R.drawable.icon_delete);
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);


        long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatter.format(date);
        set_date.setText(dateString);
        set_date_pdc.setText(dateString);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        title = "CREATE RECEIPT VOUCHER";
        iconHandlerVariable = 1;
        fromReceiptVoucher = getIntent().getBooleanExtra("fromReceipt", false);
        // fromPdcReceiptVoucher = getIntent().getBooleanExtra("fromPdcReceipt",false);
        if (fromReceiptVoucher == true) {

          /*  if (from.equals("pdcDetailsReceipt")) {
                from = "pdcdetail";
                // Toast.makeText(CreateReceiptVoucherActivity.this, "i am here", Toast.LENGTH_SHORT).show();
            } else {
                from = "receipt";
            }*/
            title = "EDIT RECEIPT VOUCHER";
            iconHandlerVariable = 2;
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            getIntent().getExtras().getString("id");
            appUser.edit_receipt_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            //received_from_layout.setEnabled(false);
           // received_by_layout.setEnabled(false);
            //transaction_amount.setEnabled(false);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_RECEIPT_VOUCHER_DETAILS);
            } else if (fromReceiptVoucher == false) {

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
        }/*else if(fromPdcReceiptVoucher==true){

        }*/ else {
            mSelectedImage.setImageDrawable(null);
            mSelectedImage.setVisibility(View.GONE);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
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
        initActionbar();

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gst_nature_spinner.getSelectedItem().toString().equals("Advance Receipt")) {
                    arrow.setVisibility(View.VISIBLE);

                    if (!transaction_amount.getText().toString().equals("")) {
                        if (!received_from.getText().toString().equals("")) {
                            if (!received_by.getText().toString().equals("")) {
                                Intent intent = new Intent(getApplicationContext(), AddReceiptItemActivity.class);
                                intent.putExtra("amount", transaction_amount.getText().toString());
                                intent.putExtra("state", state);
                                startActivity(intent);
                            }else {
                                Snackbar.make(coordinatorLayout, "Please select Received By", Snackbar.LENGTH_LONG).show();

                            }
                        }else {
                            Snackbar.make(coordinatorLayout, "Please select Received From", Snackbar.LENGTH_LONG).show();

                        }
                        } else {
                            //gst_nature_spinner.setSelection(0);
                            Snackbar.make(coordinatorLayout, "Enter the amount", Snackbar.LENGTH_LONG).show();

                    }
                }else {
                    Snackbar.make(coordinatorLayout, "Please select GST Nature", Snackbar.LENGTH_LONG).show();

                }
            }
        });

        gst_nature_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(fromReceiptVoucher){
                    if(!gst_nature_spinner.getSelectedItem().toString().equals(spinnergstnature)){
                        appUser.mListMapForItemReceipt.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }
                }
                else{
                    appUser.mListMapForItemReceipt.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
                if (position==0){
                    arrow.setVisibility(View.GONE);

                }else {
                    arrow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i.createChooser(i, "Select Picture"), SELECT_PICTURE);*/
                startDialog();
            }
        });

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    date_pdc_layout.setVisibility(View.GONE);
                    date_pdc_textview.setVisibility(View.GONE);
                    set_date_pdc.setText("");
                } else {
                    date_pdc_layout.setVisibility(View.VISIBLE);
                    date_pdc_textview.setVisibility(View.VISIBLE);
                    if (set_date_pdc.getText().toString().equals("")) {
                        set_date_pdc.setText(dateString);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        received_from_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParameterConstant.forAccountIntentBool=false;
                ParameterConstant.forAccountIntentName="";
                ParameterConstant.forAccountIntentId="";
                ParameterConstant.accountSwitching=1;
                //intStartActivityForResult = 1;
               // ParameterConstant.checkStartActivityResultForAccount = 1;
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount = false;
                ParameterConstant.handleAutoCompleteTextView=0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });


        received_by_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParameterConstant.forAccountIntentBool=false;
                ParameterConstant.forAccountIntentName="";
                ParameterConstant.forAccountIntentId="";
                ParameterConstant.accountSwitching=2;
                //intStartActivityForResult = 2;
               // ParameterConstant.checkStartActivityResultForAccount = 1;
                appUser.account_master_group = "Cash-in-hand,Bank Accounts";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount = false;
                ParameterConstant.handleAutoCompleteTextView=0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 3);
            }
        });

        mSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fromReceiptVoucher){
                Intent intent = new Intent(getApplicationContext(), ImageOpenActivity.class);
                intent.putExtra("encodedString", imageToUploadUri.toString());
                intent.putExtra("booleAttachment", false);
                startActivity(intent);
            }

                else{
                    if(attachemnt.equals("")){
                        Intent intent = new Intent(getApplicationContext(), ImageOpenActivity.class);
                        intent.putExtra("encodedString", imageToUploadUri.toString());
                        intent.putExtra("booleAttachment", false);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), ImageOpenActivity.class);
                        intent.putExtra("attachment", attachemnt);
                        intent.putExtra("booleAttachment", true);
                        startActivity(intent);
                    }
                }
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!voucher_no.getText().toString().equals("")) {
                    if (!set_date.getText().toString().equals("")) {
                        if (!received_by.getText().toString().equals("")) {
                            if (!received_from.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.receipt_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                    appUser.receipt_date = set_date.getText().toString();
                                    appUser.receipt_voucher_no = voucher_no.getText().toString();
                                    appUser.receipt_type = type_spinner.getSelectedItem().toString();
                                    appUser.receipt_date_pdc = set_date_pdc.getText().toString();
                                    appUser.receipt_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.receipt_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.receipt_narration = transaction_narration.getText().toString();
                                    appUser.receipt_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();

                                    if(appUser.receipt_received_from_email!=null&&!appUser.receipt_received_from_email.equalsIgnoreCase("null") && !appUser.receipt_received_from_email.equals("")) {
                                        new AlertDialog.Builder(CreateReceiptVoucherActivity.this)
                                                .setTitle("Email")
                                                .setMessage(R.string.btn_send_email)
                                                .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                                    appUser.email_yes_no = "true";
                                                    LocalRepositories.saveAppUser(CreateReceiptVoucherActivity.this, appUser);
                                                    if (isConnected) {
                                                        mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                                                        mProgressDialog.setMessage("Info...");
                                                        mProgressDialog.setIndeterminate(false);
                                                        mProgressDialog.setCancelable(true);
                                                        mProgressDialog.show();
                                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_RECEIPT_VOUCHER);
                                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
                                                    } else {
                                                        snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
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
                                                .setNegativeButton(R.string.btn_no, (dialogInterface, i) -> {

                                                    appUser.email_yes_no = "false";
                                                    LocalRepositories.saveAppUser(CreateReceiptVoucherActivity.this, appUser);
                                                    if (isConnected) {
                                                        mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                                                        mProgressDialog.setMessage("Info...");
                                                        mProgressDialog.setIndeterminate(false);
                                                        mProgressDialog.setCancelable(true);
                                                        mProgressDialog.show();
                                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_RECEIPT_VOUCHER);
                                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
                                                    } else {
                                                        snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
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
                                                .show();
                                    }else {
                                        appUser.email_yes_no = "false";
                                        LocalRepositories.saveAppUser(CreateReceiptVoucherActivity.this, appUser);
                                        if (isConnected) {
                                            mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                                            mProgressDialog.setMessage("Info...");
                                            mProgressDialog.setIndeterminate(false);
                                            mProgressDialog.setCancelable(true);
                                            mProgressDialog.show();
                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_RECEIPT_VOUCHER);
                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
                                        } else {
                                            snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
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

                                } else {
                                    Snackbar.make(coordinatorLayout, "Please enter Amount", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please select received from", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select received by", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
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
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!voucher_no.getText().toString().equals("")) {
                    if (!set_date.getText().toString().equals("")) {
                        if (!received_by.getText().toString().equals("")) {
                            if (!received_from.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.receipt_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                    appUser.receipt_date = set_date.getText().toString();
                                    appUser.receipt_voucher_no = voucher_no.getText().toString();
                                    appUser.receipt_type = type_spinner.getSelectedItem().toString();
                                    appUser.receipt_date_pdc = set_date_pdc.getText().toString();
                                    appUser.receipt_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.receipt_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.receipt_narration = transaction_narration.getText().toString();
                                    appUser.receipt_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                                        mProgressDialog.setMessage("Info...");
                                        mProgressDialog.setIndeterminate(false);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();
                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_RECEIPT_VOUCHER);
                                    } else {
                                        snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
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
                                } else {
                                    Snackbar.make(coordinatorLayout, "Please enter Amount", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please select received from", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select received by", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
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
            }
        });

    }

    private void startDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CreateReceiptVoucherActivity.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
                intCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                imageToUploadUri = Uri.fromFile(f);
                startActivityForResult(intCamera, Cv.REQUEST_CAMERA);


            }
        });

        myAlertDialog.setNegativeButton("Gallary",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        getIntent.setType("image/*");
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("image/*");
                        startActivityForResult(pickIntent, Cv.REQUEST_GALLERY);
                    }
                });
        myAlertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_list_button_action, menu);
        if(fromReceiptVoucher==true){
            MenuItem item = menu.findItem(R.id.icon_id);
            item.setVisible(false);
        }else{
            MenuItem item = menu.findItem(R.id.icon_id);
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.icon_id:
                Intent i = new Intent(getApplicationContext(),ReceiptVoucherActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
*/

    private void setDateField() {
        set_date.setOnClickListener(this);
        set_date_pdc.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();

        //set_date.setText("22 Nov 2017");
        // set_date_pdc.setText("22 Nov 2017");
        DatePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                set_date.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                set_date_pdc.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if (view == set_date) {
            DatePickerDialog1.show();
        } else if (view == set_date_pdc) {
            DatePickerDialog2.show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (ParameterConstant.forAccountIntentBool && ParameterConstant.accountSwitching==1) {
            String result = ParameterConstant.forAccountIntentName;
            String[] name = result.split(",");
            appUser.receipt_received_from_id = ParameterConstant.forAccountIntentId;
            appUser.receipt_received_from_name=name[0];
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            received_from.setText(name[0]);
        }else if (ParameterConstant.forAccountIntentBool && ParameterConstant.accountSwitching==2) {
            String result = ParameterConstant.forAccountIntentName;
            String[] name = result.split(",");
            appUser.receipt_received_by_id = ParameterConstant.forAccountIntentId;
            appUser.receipt_received_by_name=name[0];
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            received_by.setText(name[0]);
        }

        if (resultCode == RESULT_OK) {
            photo = null;
            switch (requestCode) {

                case Cv.REQUEST_CAMERA:
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageToUploadUri);
                        Bitmap im=scaleDownBitmap(photo,100,getApplicationContext());
                        mSelectedImage.setVisibility(View.VISIBLE);
                        mSelectedImage.setImageBitmap(im);
                        encodedString = Helpers.bitmapToBase64(im);
                        if(fromReceiptVoucher){
                            fromReceiptVoucher=false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case Cv.REQUEST_GALLERY:
                    try {
                        imageToUploadUri = data.getData();
                        photo = ImagePicker.getImageFromResult(getApplicationContext(), resultCode, data);
                        if (photo != null) {
                            mSelectedImage.setVisibility(View.VISIBLE);
                            encodedString = Helpers.bitmapToBase64(photo);
                            mSelectedImage.setImageBitmap(photo);
                            break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }

        /*if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                mSelectedImage.setVisibility(View.VISIBLE);
                mSelectedImage.setImageURI(selectedImageUri);
                try {
                    inputStream = new FileInputStream(selectedImagePath);
                    byte[] bytes;
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    try {
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bytes = output.toByteArray();
                    encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }*/


            if (requestCode == 2) {

                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForReceivedFrom = true;
                    appUser.receipt_received_from_id = ParameterConstant.id;
                    appUser.receipt_received_from_name = ParameterConstant.name;
                    appUser.receipt_received_from_email = ParameterConstant.email;
                    state = ParameterConstant.state;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    received_from.setText(ParameterConstant.name);
                } else {
                    boolForReceivedFrom = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String[] name = result.split(",");
                    state=data.getStringExtra("state");
                    appUser.receipt_received_from_id = id;
                    appUser.receipt_received_from_name = name[0];
                    appUser.receipt_received_from_email = name[3];
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    received_from.setText(name[0]);
                }

            }
            if (requestCode == 3) {
                if (ParameterConstant.handleAutoCompleteTextView==1){
                    boolForReceivedBy = true;
                    appUser.receipt_received_by_id = ParameterConstant.id;
                    appUser.receipt_received_by_name = ParameterConstant.name;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    received_by.setText(ParameterConstant.name);
                }else {
                    boolForReceivedBy = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String[] name = result.split(",");
                    appUser.receipt_received_by_id = id;
                    appUser.receipt_received_by_name = name[0];
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    received_by.setText(name[0]);
                }

            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        appUser=LocalRepositories.getAppUser(this);
       /* Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        if (bool) {

            if (intStartActivityForResult == 1) {
                boolForReceivedBy = true;

            } else if (intStartActivityForResult == 2) {
                boolForReceivedFrom = true;
            }
            if (!boolForReceivedFrom) {
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String mobile = intent.getStringExtra("mobile");
                String[] name = result.split(",");
                boolForReceivedFrom = true;
                appUser.receipt_received_from_id = id;
                appUser.receipt_received_from_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                received_from.setText(name[0]);
            }
            if (!boolForReceivedBy) {
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String[] name = result.split(",");
                appUser.receipt_received_by_id = id;
                appUser.receipt_received_by_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                received_by.setText(name[0]);

            }
        }*/

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_receipt;
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText(title);
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createreceiptresponse(CreateReceiptVoucherResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "receipt");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,appUser.company_name);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            if (from != null) {
                Preferences.getInstance(this).setParty_name("");
                Preferences.getInstance(this).setParty_id("");
                Preferences.getInstance(this).setMobile("");
                Preferences.getInstance(this).setNarration("");
                if (from.equals("sale")) {
                    appUser.mListMapForItemSale.clear();
                    appUser.mListMapForBillSale.clear();
                    appUser.voucher_type = "Sales";
                    LocalRepositories.saveAppUser(this, appUser);
                    Intent intent = new Intent(this, CreateSaleActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else if (from.equals("sale_return")) {
                    appUser.mListMapForItemSaleReturn.clear();
                    appUser.mListMapForBillSaleReturn.clear();
                    appUser.voucher_type = "Sale Return";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Intent intent = new Intent(this, CreateSaleReturnActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else if (from.equals("purchase")) {
                    appUser.mListMapForItemPurchase.clear();
                    appUser.mListMapForBillPurchase.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Intent intent = new Intent(this, CreatePurchaseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else if (from.equals("purchase_return")) {
                    appUser.mListMapForItemPurchaseReturn.clear();
                    appUser.mListMapForBillPurchaseReturn.clear();
                    appUser.voucher_type = "Purchase Return";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Intent intent = new Intent(this, CreatePurchaseReturnActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            } else {
                // voucher_no.setText("");
                transaction_amount.setText("");
                transaction_narration.setText("");
                received_by.setText("");
                received_from.setText("");
                type_spinner.setSelection(0);
                gst_nature_spinner.setSelection(0);
                encodedString="";
                mSelectedImage.setImageDrawable(null);
                mSelectedImage.setVisibility(View.GONE);
                new AlertDialog.Builder(CreateReceiptVoucherActivity.this)
                        .setTitle("Print/Preview").setMessage("")
                        .setMessage(R.string.print_preview_mesage)
                        .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                            Intent intent = new Intent(CreateReceiptVoucherActivity.this, TransactionPdfActivity.class);
                            intent.putExtra("company_report", response.getHtml());
                            startActivity(intent);

                           /* ProgressDialog progressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            String htmlString = response.getHtml();
                            Spanned htmlAsSpanned = Html.fromHtml(htmlString);
                            mPdf_webview = new WebView(getApplicationContext());
                            mPdf_webview.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
                            mPdf_webview.getSettings().setBuiltInZoomControls(true);
                            createWebPrintJob(mPdf_webview);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            }, 5 * 1000);*/


                        })
                        .setNegativeButton(R.string.btn_cancel, null)
                        .show();
            }
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void getReceiptVoucherDetails(GetReceiptVoucherDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            if (response.getReceipt_voucher().getData().getAttributes().getIs_payment_settlement()!=null) {
                if (response.getReceipt_voucher().getData().getAttributes().getIs_payment_settlement().equals("true")) {
                    voucher_series_spinner.setEnabled(false);
                    voucher_no.setEnabled(false);
                    type_spinner.setEnabled(false);
                    gst_nature_spinner.setEnabled(false);
                    arrow.setClickable(false);
                    set_date.setClickable(false);
                    set_date_pdc.setClickable(false);
                    transaction_amount.setInputType(InputType.TYPE_NULL);
                    transaction_narration.setInputType(InputType.TYPE_NULL);
                    received_from_layout.setClickable(false);
                    received_by_layout.setClickable(false);
                    mBrowseImage.setClickable(false);
                    mSelectedImage.setClickable(false);
                    submit_layout.setVisibility(View.GONE);
                }
            }
            set_date.setText(response.getReceipt_voucher().getData().getAttributes().getDate());
            appUser.receipt_date = response.getReceipt_voucher().getData().getAttributes().getDate();
            voucher_no.setText(response.getReceipt_voucher().getData().getAttributes().getVoucher_number());
            //set_date_pdc.setText(response.getReceipt_voucher().getData().getAttributes().getPdc_date());
            received_from.setText(response.getReceipt_voucher().getData().getAttributes().getReceived_from().getName());
            received_by.setText(response.getReceipt_voucher().getData().getAttributes().getReceived_by());
            appUser.receipt_received_from_id = String.valueOf(response.getReceipt_voucher().getData().getAttributes().getReceived_from().getId());
            appUser.receipt_received_by_id=String.valueOf(response.getReceipt_voucher().getData().getAttributes().getReceived_by_id());
            LocalRepositories.saveAppUser(this,appUser);
            transaction_amount.setText(String.valueOf(response.getReceipt_voucher().getData().getAttributes().getAmount()));

            transaction_narration.setText(response.getReceipt_voucher().getData().getAttributes().getNarration());
            if (!Helpers.mystring(response.getReceipt_voucher().getData().getAttributes().getAttachment()).equals("")) {
                attachemnt=response.getReceipt_voucher().getData().getAttributes().getAttachment();
                Glide.with(this).load(Uri.parse(response.getReceipt_voucher().getData().getAttributes().getAttachment()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(mSelectedImage);
                mSelectedImage.setVisibility(View.VISIBLE);
            } else {
                mSelectedImage.setVisibility(View.GONE);
                attachemnt="";
            }
            String type_pdc_regular = response.getReceipt_voucher().getData().getAttributes().getPayment_type().trim();
            if (type_pdc_regular.equals("PDC")) {
                type_spinner.setSelection(1);
                set_date_pdc.setText(response.getReceipt_voucher().getData().getAttributes().getPdc_date());
            } else {
                type_spinner.setSelection(0);
                set_date_pdc.setText("");
            }
            String group_type = response.getReceipt_voucher().getData().getAttributes().getGst_nature().trim();
            spinnergstnature=group_type;
            int groupindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.gst_nature_receipt).length; i++) {
                if (getResources().getStringArray(R.array.gst_nature_receipt)[i].equals(group_type)) {
                    groupindex = i;
                    break;
                }

            }
            if(!response.getReceipt_voucher().getData().getAttributes().getReceived_from().getState().equals("")||response.getReceipt_voucher().getData().getAttributes().getReceived_from().getState()!=null) {
                state=response.getReceipt_voucher().getData().getAttributes().getReceived_from().getState();
            }
            List<Map> myList=new ArrayList<>();
            gst_nature_spinner.setSelection(groupindex);
            Map mMap;
            appUser.mListMapForItemReceipt.clear();
            LocalRepositories.saveAppUser(getApplicationContext(),appUser);
           for(int i=0; i<response.getReceipt_voucher().getData().getAttributes().getReceipt_item().size();i++){
               mMap=new HashMap<>();
               mMap.put("voucher_id", response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getVoucher_id());
               mMap.put("voucher_type", response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getVoucher_type());
               mMap.put("ref_num", response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getReference_no());
               mMap.put("amount", String.valueOf(response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getAmount()));
               mMap.put("taxrate", String.valueOf(response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getTax_rate()));
               mMap.put("total", String.valueOf(response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getTotal_amount()));
               mMap.put("cgst", String.valueOf(response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getCgst()));
               mMap.put("sgst", String.valueOf(response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getSgst()));
               mMap.put("igst", String.valueOf(response.getReceipt_voucher().getData().getAttributes().getReceipt_item().get(i).getIgst()));
               appUser.mListMapForItemReceipt.add(mMap);
           }
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void editExpence(EditReceiptVoucherResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            if (from != null) {
                if (from.equals("pdcDetailsReceipt")) {
                    Timber.i("ooooo pdcDetailsReceipt"+from);
                    Intent intent = new Intent(this, PdcActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                } else {
                    Timber.i("ooooo Receipt"+from);
                    Intent intent = new Intent(this, ReceiptVoucherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("forDate",true);
                    startActivity(intent);
                    finish();
                }
            }
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void getVoucherNumber(GetVoucherNumbersResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            voucher_no.setText(response.getVoucher_number());

        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            // set_date.setOnClickListener(this);
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_id:
                /*if (iconHandlerVariable == 2) {
                    String receipt_voucher_id = appUser.edit_receipt_id;
                    EventBus.getDefault().post(new EventDeleteReceipt(receipt_voucher_id));
                } else {*/
                    Intent i = new Intent(getApplicationContext(), ReceiptVoucherActivity.class);
                    startActivity(i);
                    finish();
              //  }

                return true;
            case android.R.id.home:
                if (from != null) {
                    if (from.equals("sale")) {
                        appUser.mListMapForItemSale.clear();
                        appUser.mListMapForBillSale.clear();
                        LocalRepositories.saveAppUser(this, appUser);
                        Intent intent = new Intent(this, CreateSaleActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else if (from.equals("sale_return")) {
                        appUser.mListMapForItemSaleReturn.clear();
                        appUser.mListMapForBillSaleReturn.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        Intent intent = new Intent(this, CreateSaleReturnActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else if (from.equals("purchase")) {
                        appUser.mListMapForItemPurchase.clear();
                        appUser.mListMapForBillPurchase.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        Intent intent = new Intent(this, CreatePurchaseActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else if (from.equals("purchase_return")) {
                        appUser.mListMapForItemPurchaseReturn.clear();
                        appUser.mListMapForBillPurchaseReturn.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        Intent intent = new Intent(this, CreatePurchaseReturnActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else if (from.equals("pdcDetailsReceipt")) {
                        Intent intent = new Intent(this, PdcActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else if (from.equals("receipt")) {
                        finish();
                    }
                } else {
                    Intent intent = new Intent(this, TransactionDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (from != null) {
            if (from.equals("sale")) {
                appUser.mListMapForItemSale.clear();
                appUser.mListMapForBillSale.clear();
                LocalRepositories.saveAppUser(this, appUser);
                Intent intent = new Intent(this, CreateSaleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (from.equals("sale_return")) {
                appUser.mListMapForItemSaleReturn.clear();
                appUser.mListMapForBillSaleReturn.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent = new Intent(this, CreateSaleReturnActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            } else if (from.equals("purchase")) {
                appUser.mListMapForItemPurchase.clear();
                appUser.mListMapForBillPurchase.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent = new Intent(this, CreatePurchaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            } else if (from.equals("purchase_return")) {
                appUser.mListMapForItemPurchaseReturn.clear();
                appUser.mListMapForBillPurchaseReturn.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent = new Intent(this, CreatePurchaseReturnActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (from.equals("pdcDetailsReceipt")) {
                Intent intent = new Intent(this, PdcActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (from.equals("receipt")) {
               Intent intent = new Intent(this, ReceiptVoucherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("forDate",true);
                    startActivity(intent);
                    finish();
            }
        } else {
            Intent intent = new Intent(this, TransactionDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Subscribe
    public void deletereceipt(EventDeleteReceipt pos) {
        appUser.delete_receipt_id = pos.getPosition();
        LocalRepositories.saveAppUser(this, appUser);
        new android.support.v7.app.AlertDialog.Builder(CreateReceiptVoucherActivity.this)
                .setTitle("Delete Expence Item")
                .setMessage("Are you sure you want to delete this Record ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateReceiptVoucherActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_RECEIPT_VOUCHER);
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

    @Subscribe
    public void deletereceiptvoucherresponse(DeleteReceiptVoucherResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_RECEIPT_VOUCHER);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(CreateReceiptVoucherActivity.this, ReceiptVoucherActivity.class));
            finish();
        } else {
           // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createWebPrintJob(WebView webView) {

        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/m_Billing_PDF/");
        if (path.exists()) {
            path.delete();
            path.mkdir();
        }
        File pathPrint = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/m_Billing_PDF/a.pdf");

        PdfPrint pdfPrint = new PdfPrint(attributes);
        pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, "a.pdf");
        previewPdf(pathPrint);
    }

    private void previewPdf(File path) {
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(path);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        } else {
//            Toast.makeText(this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }



    public  Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }
}