package com.lkintechnology.mBilling.activities.company.transaction.journalvoucher;

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
import android.os.Environment;
import android.os.StrictMode;
import android.print.PdfPrint;
import android.print.PrintAttributes;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
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
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem.AddCreditNoteItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem.AddDebitNoteItemActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.CreateJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.EditJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.GetJournalVoucherDetailsResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.ImagePicker;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateJournalVoucherActivity extends RegisterAbstractActivity implements View.OnClickListener {

    @Bind(R.id.account_name_credit)
    TextView account_name_credit;
    @Bind(R.id.account_name_debit)
    TextView account_name_debit;
    @Bind(R.id.ll_spinerItem)
    LinearLayout llSpiner;
    @Bind(R.id.date)
    TextView set_date;
    @Bind(R.id.browse_image)
    LinearLayout mBrowseImage;
    @Bind(R.id.selected_image)
    ImageView mSelectedImage;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    @Bind(R.id.transaction_spinner)
    Spinner voucher_series_spinner;
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
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1, DatePickerDialog2;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    InputStream inputStream = null;
    ProgressDialog mProgressDialog;
    Boolean fromJournalVoucher;
    String encodedString;
    String title;
    AppUser appUser;
    public Boolean boolForReceivedFrom = false;
    public Boolean boolForReceivedBy = false;
    public static int intStartActivityForResult = 0;
    Bitmap photo;
    WebView mPdf_webview;
    private Uri imageToUploadUri;
    private String gst_nature_position;
    String state, state_for_credit;
    private FirebaseAnalytics mFirebaseAnalytics;
    String gstnaturespinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        appUser.voucher_type = "Journal Voucher";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatter.format(date);
        set_date.setText(dateString);
        account_name_credit.setText(appUser.account_name_credit_name);
        account_name_debit.setText(appUser.account_name_debit_name);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        title = "CREATE JOURNAL VOUCHER";
        fromJournalVoucher = getIntent().getBooleanExtra("fromJournalVoucher", false);
        if (fromJournalVoucher == true) {
            title = "EDIT JOURNAL VOUCHER";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_journal_voucher_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_JOURNAL_VOUCHER_DETAILS);
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
        } else {
            mSelectedImage.setImageDrawable(null);
            mSelectedImage.setVisibility(View.GONE);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
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
        if (!Preferences.getInstance(getApplicationContext()).getAttachment().equals("")) {
            mSelectedImage.setImageBitmap(Helpers.base64ToBitmap(Preferences.getInstance(getApplicationContext()).getAttachment()));
            mSelectedImage.setVisibility(View.VISIBLE);
        }
        if (!Preferences.getInstance(getApplicationContext()).getUrl_attachment().equals("")) {
            Glide.with(this).load(Helpers.mystring(Preferences.getInstance(getApplicationContext()).getUrl_attachment())).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(mSelectedImage);
            mSelectedImage.setVisibility(View.VISIBLE);
        }
        mBrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i.createChooser(i, "Select Picture"), SELECT_PICTURE);*/
                startDialog();
            }
        });

        account_name_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParameterConstant.forAccountIntentBool = false;
                ParameterConstant.forAccountIntentName = "";
                ParameterConstant.forAccountIntentId = "";
                ParameterConstant.accountSwitching = 1;
                //intStartActivityForResult=1;
                // ParameterConstant.checkStartActivityResultForAccount =10;
                appUser.account_master_group = "";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount = false;
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });


        account_name_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParameterConstant.forAccountIntentBool = false;
                ParameterConstant.forAccountIntentName = "";
                ParameterConstant.forAccountIntentId = "";
                ParameterConstant.accountSwitching = 2;
                // intStartActivityForResult=2;
                //  ParameterConstant.checkStartActivityResultForAccount =10;
                appUser.account_master_group = "";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount = false;
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 3);
            }
        });

        mSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageToUploadUri == null) {
                   /* Bitmap bitmap=((GlideBitmapDrawable)mSelectedImage.getDrawable()).getBitmap();
                    String encodedString=Helpers.bitmapToBase64(bitmap);*/
                    Intent intent = new Intent(getApplicationContext(), ImageOpenActivity.class);
                    intent.putExtra("iEncodedString", true);
                    intent.putExtra("encodedString", "");
                    intent.putExtra("booleAttachment", false);
                    intent.putExtra("bitmapPhotos", true);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ImageOpenActivity.class);
                    intent.putExtra("encodedString", imageToUploadUri.toString());
                    intent.putExtra("booleAttachment", false);
                    startActivity(intent);
                }
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!voucher_no.getText().toString().equals("")) {
                    if (!set_date.getText().toString().equals("")) {
                        if (!account_name_debit.getText().toString().equals("")) {
                            if (!account_name_credit.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.journal_voucher_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                    appUser.journal_voucher_date = set_date.getText().toString();
                                    appUser.journal_voucher_voucher_no = voucher_no.getText().toString();
                                    appUser.journal_voucher_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                                    for (int i = 0; i < appUser.mListMapForItemJournalVoucherNote.size(); i++) {
                                        Map map = appUser.mListMapForItemJournalVoucherNote.get((i));
                                        map.put("account_id", appUser.account_name_credit_id);
                                        map.put("party_id", appUser.account_name_debit_id);
                                    }
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.journal_voucher_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.journal_voucher_narration = transaction_narration.getText().toString();
                                    appUser.journal_voucher_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (appUser.account_name_debit_email != null && !appUser.account_name_debit_email.equalsIgnoreCase("null") && !appUser.account_name_debit_email.equals("")) {
                                        new AlertDialog.Builder(CreateJournalVoucherActivity.this)
                                                .setTitle("Email")
                                                .setMessage(R.string.btn_send_email)
                                                .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                                    appUser.email_yes_no = "true";
                                                    LocalRepositories.saveAppUser(CreateJournalVoucherActivity.this, appUser);
                                                    if (isConnected) {
                                                        mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
                                                        mProgressDialog.setMessage("Info...");
                                                        mProgressDialog.setIndeterminate(false);
                                                        mProgressDialog.setCancelable(true);
                                                        mProgressDialog.show();
                                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_JOURNAL_VOUCHER);
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
                                                    LocalRepositories.saveAppUser(CreateJournalVoucherActivity.this, appUser);
                                                    if (isConnected) {
                                                        mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
                                                        mProgressDialog.setMessage("Info...");
                                                        mProgressDialog.setIndeterminate(false);
                                                        mProgressDialog.setCancelable(true);
                                                        mProgressDialog.show();
                                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_JOURNAL_VOUCHER);
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
                                    } else {
                                        appUser.email_yes_no = "false";
                                        LocalRepositories.saveAppUser(CreateJournalVoucherActivity.this, appUser);
                                        if (isConnected) {
                                            mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
                                            mProgressDialog.setMessage("Info...");
                                            mProgressDialog.setIndeterminate(false);
                                            mProgressDialog.setCancelable(true);
                                            mProgressDialog.show();
                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_JOURNAL_VOUCHER);
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
                                Snackbar.make(coordinatorLayout, "Please select account name credit", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select account name debit", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
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
                        if (!account_name_debit.getText().toString().equals("")) {
                            if (!account_name_credit.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.journal_voucher_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                    appUser.journal_voucher_date = set_date.getText().toString();
                                    appUser.journal_voucher_voucher_no = voucher_no.getText().toString();
                                    appUser.journal_voucher_gst_nature = gst_nature_spinner.getSelectedItem().toString();

                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.journal_voucher_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.journal_voucher_narration = transaction_narration.getText().toString();
                                    appUser.journal_voucher_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
                                        mProgressDialog.setMessage("Info...");
                                        mProgressDialog.setIndeterminate(false);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();
                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_JOURNAL_VOUCHER);
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
                                Snackbar.make(coordinatorLayout, "Please select account name credit", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select account name debit", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
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
        llSpiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gst_nature_spinner.getSelectedItem().toString().equals("Rcm/Unreg. Expense/Consolidated RCM Payable")) {
                    llSpiner.setVisibility(View.VISIBLE);

                    if (!transaction_amount.getText().toString().equals("")) {
                        if (!account_name_credit.getText().toString().equals("")) {
                            if (!account_name_debit.getText().toString().equals("")) {
                                appUser.journalreason = "";
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                Intent intent1 = new Intent(CreateJournalVoucherActivity.this, AddJournalItemActivity.class);
                                intent1.putExtra("diff_amount", transaction_amount.getText().toString());
                                intent1.putExtra("gst_pos1", "1");
                                intent1.putExtra("state", state);
                                intent1.putExtra("state_for_credit", state_for_credit);
                                startActivity(intent1);
                            } else {
                                Snackbar.make(coordinatorLayout, "Please select account name debit ", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select account name credit ", Snackbar.LENGTH_LONG).show();

                        }
                    } else {
                        //   gst_nature_spinner.setSelection(0);
                        Snackbar.make(coordinatorLayout, "Please enter the amount", Snackbar.LENGTH_LONG).show();
                    }
                } else if (gst_nature_spinner.getSelectedItem().toString().equals("Registered Expenses (B2B)")) {
                    llSpiner.setVisibility(View.VISIBLE);
                    if (!transaction_amount.getText().toString().equals("")) {
                        if (!account_name_credit.getText().toString().equals("")) {
                            if (!account_name_debit.getText().toString().equals("")) {
                                Intent intent2 = new Intent(CreateJournalVoucherActivity.this, AddJournalItemActivity.class);
                                intent2.putExtra("diff_amount", transaction_amount.getText().toString());
                                intent2.putExtra("gst_pos2", "2");
                                intent2.putExtra("state", state);
                                intent2.putExtra("state_for_credit", state_for_credit);
                                startActivity(intent2);
                            } else {
                                Snackbar.make(coordinatorLayout, "Please select account name debit ", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select account name credit ", Snackbar.LENGTH_LONG).show();

                        }
                    } else {
                        // gst_nature_spinner.setSelection(0);
                        Snackbar.make(coordinatorLayout, "Please enter the amount", Snackbar.LENGTH_LONG).show();
                    }
                } else if (gst_nature_spinner.getSelectedItem().toString().equals("Cr. Note Received Against Purchase")) {
                    llSpiner.setVisibility(View.VISIBLE);
                    if (!transaction_amount.getText().toString().equals("")) {
                        if (!account_name_credit.getText().toString().equals("")) {
                            if (!account_name_debit.getText().toString().equals("")) {
                                Intent intent3 = new Intent(CreateJournalVoucherActivity.this, AddCreditNoteItemActivity.class);
                                intent3.putExtra("gst_pos6", "6");
                                intent3.putExtra("diff_amount", transaction_amount.getText().toString());
                                intent3.putExtra("state", state);
                                intent3.putExtra("state_for_credit", state_for_credit);
                                startActivity(intent3);
                            } else {
                                Snackbar.make(coordinatorLayout, "Please select account name debit ", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select account name credit ", Snackbar.LENGTH_LONG).show();

                        }
                    } else {
                        // gst_nature_spinner.setSelection(0);
                        Snackbar.make(coordinatorLayout, "Please enter the amount", Snackbar.LENGTH_LONG).show();
                    }
                } else if (gst_nature_spinner.getSelectedItem().toString().equals("Dr. Note Issued Against Sale")) {
                    llSpiner.setVisibility(View.VISIBLE);
                    if (!transaction_amount.getText().toString().equals("")) {
                        if (!account_name_credit.getText().toString().equals("")) {
                            if (!account_name_debit.getText().toString().equals("")) {
                                Intent intent3 = new Intent(CreateJournalVoucherActivity.this, AddDebitNoteItemActivity.class);
                                intent3.putExtra("gst_pos7", "7");
                                intent3.putExtra("diff_amount", transaction_amount.getText().toString());
                                intent3.putExtra("state", state);
                                intent3.putExtra("state_for_credit", state_for_credit);
                                startActivity(intent3);
                            } else {
                                Snackbar.make(coordinatorLayout, "Please select account name debit ", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select account name credit ", Snackbar.LENGTH_LONG).show();

                        }
                    } else {
                        //gst_nature_spinner.setSelection(0);
                        Snackbar.make(coordinatorLayout, "Please enter the amount", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please select GST Nature", Snackbar.LENGTH_LONG).show();

                }
            }
        });
        gst_nature_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (fromJournalVoucher) {
                    if (!gst_nature_spinner.getSelectedItem().toString().equals(gstnaturespinner)) {
                        appUser.mListMapForItemJournalVoucherNote.clear();
                        Preferences.getInstance(getApplicationContext()).setReason("");
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }
                } else {
                    appUser.mListMapForItemJournalVoucherNote.clear();
                    Preferences.getInstance(getApplicationContext()).setReason("");
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
                if (position == 0 | position == 3 | position == 4 | position == 5 | position == 8 | position == 9) {
                    llSpiner.setVisibility(View.GONE);

                } else {
                    llSpiner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void startDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CreateJournalVoucherActivity.this);
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


    private void setDateField() {
        set_date.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();

        set_date.setText("22 Nov 2017");

        DatePickerDialog1 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                set_date.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if (view == set_date) {
            DatePickerDialog1.show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (ParameterConstant.forAccountIntentBool && ParameterConstant.accountSwitching == 1) {
            String result = ParameterConstant.forAccountIntentName;
            String[] name = result.split(",");
            appUser.account_name_debit_id = ParameterConstant.forAccountIntentId;
            appUser.account_name_debit_name = name[0];
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            account_name_debit.setText(name[0]);
        } else if (ParameterConstant.forAccountIntentBool && ParameterConstant.accountSwitching == 2) {
            String result = ParameterConstant.forAccountIntentName;
            String[] name = result.split(",");
            appUser.account_name_credit_id = ParameterConstant.forAccountIntentId;
            appUser.account_name_credit_name = name[0];
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            account_name_credit.setText(name[0]);
        }


        if (resultCode == RESULT_OK) {
            photo = null;
            switch (requestCode) {

                case Cv.REQUEST_CAMERA:

                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageToUploadUri);
                        Bitmap im = scaleDownBitmap(photo, 100, getApplicationContext());
                        mSelectedImage.setVisibility(View.VISIBLE);
                        mSelectedImage.setImageBitmap(im);
                        encodedString = Helpers.bitmapToBase64(im);
                        Preferences.getInstance(getApplicationContext()).setUrlAttachment("");
                        Preferences.getInstance(getApplicationContext()).setAttachment(encodedString);
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
                            Preferences.getInstance(getApplicationContext()).setUrlAttachment("");
                            Preferences.getInstance(getApplicationContext()).setAttachment(encodedString);
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

            if (requestCode == 2) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForReceivedFrom = true;
                    appUser.account_name_debit_id = ParameterConstant.id;
                    appUser.account_name_debit_name = ParameterConstant.name;
                    appUser.account_name_debit_email = ParameterConstant.email;
                    state = ParameterConstant.state;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    account_name_debit.setText(ParameterConstant.name);
                } else {
                    boolForReceivedFrom = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    state = data.getStringExtra("state");
                    String[] name = result.split(",");
                    appUser.account_name_debit_id = id;
                    appUser.account_name_debit_name = name[0];
                    appUser.account_name_debit_email = name[3];
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    account_name_debit.setText(name[0]);
                }
            }
            if (requestCode == 3) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForReceivedBy = true;
                    appUser.account_name_credit_id = ParameterConstant.id;
                    appUser.account_name_credit_name = ParameterConstant.name;
                    state_for_credit = ParameterConstant.state;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    account_name_credit.setText(ParameterConstant.name);
                } else {
                    boolForReceivedBy = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    state_for_credit = data.getStringExtra("state");
                    String[] name = result.split(",");
                    appUser.account_name_credit_id = id;
                    appUser.account_name_credit_name = name[0];
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    account_name_credit.setText(name[0]);
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        appUser = LocalRepositories.getAppUser(this);
        /*Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        if (bool) {

            if (intStartActivityForResult==1){
                boolForReceivedBy=true;

            }else if (intStartActivityForResult==2){
                boolForReceivedFrom=true;
            }
            if (!boolForReceivedFrom) {

                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String[] name = result.split(",");
                appUser.account_name_debit_id = id;
                appUser.account_name_debit_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                account_name_debit.setText(name[0]);
            }
            if (!boolForReceivedBy) {

                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String[] name = result.split(",");
                appUser.account_name_credit_id =id;
                appUser.account_name_credit_name =name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                account_name_credit.setText(name[0]);
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
        return R.layout.activity_create_journal_voucher;
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
    public void createjournalvoucherresponse(CreateJournalVoucherResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "journal_voucher");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, appUser.company_name);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            //voucher_no.setText("");
            transaction_amount.setText("");
            transaction_narration.setText("");
            account_name_credit.setText("");
            account_name_debit.setText("");
            gst_nature_spinner.setSelection(0);
            encodedString = "";
            Preferences.getInstance(getApplicationContext()).setAttachment("");
            Preferences.getInstance(getApplicationContext()).setUrlAttachment("");
            mSelectedImage.setImageDrawable(null);
            mSelectedImage.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();

            new AlertDialog.Builder(CreateJournalVoucherActivity.this)
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(CreateJournalVoucherActivity.this, TransactionPdfActivity.class);
                        intent.putExtra("company_report", response.getHtml());
                        startActivity(intent);

                       /* ProgressDialog progressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
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
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this, response.getMessage());
        }
    }


    @Subscribe
    public void getJournalVoucherDetails(GetJournalVoucherDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            set_date.setText(response.getJournal_voucher().getData().getAttributes().getDate());
            appUser.journal_voucher_date = response.getJournal_voucher().getData().getAttributes().getDate();
            voucher_no.setText(response.getJournal_voucher().getData().getAttributes().getVoucher_number());
            account_name_debit.setText(response.getJournal_voucher().getData().getAttributes().getAccount_debit().getName());
            account_name_credit.setText(response.getJournal_voucher().getData().getAttributes().getAccount_credit().getName());
            transaction_amount.setText(String.valueOf(response.getJournal_voucher().getData().getAttributes().getAmount()));
            transaction_narration.setText(response.getJournal_voucher().getData().getAttributes().getNarration());
            appUser.account_name_debit_id = "" + response.getJournal_voucher().getData().getAttributes().getAccount_debit().getId();
            appUser.account_name_credit_id = "" + response.getJournal_voucher().getData().getAttributes().getAccount_credit().getId();
            LocalRepositories.saveAppUser(this, appUser);
            Preferences.getInstance(getApplicationContext()).setAttachment("");
            if (!Helpers.mystring(response.getJournal_voucher().getData().getAttributes().getAttachment()).equals("")) {
                Preferences.getInstance(getApplicationContext()).setUrlAttachment(response.getJournal_voucher().getData().getAttributes().getAttachment());
                Glide.with(this).load(Helpers.mystring(response.getJournal_voucher().getData().getAttributes().getAttachment())).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).into(mSelectedImage);
                mSelectedImage.setVisibility(View.VISIBLE);
            } else {
                mSelectedImage.setVisibility(View.GONE);
            }

            String group_type = response.getJournal_voucher().getData().getAttributes().getGst_nature().trim();
            gstnaturespinner = group_type;
            int groupindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.gst_nature_journal_voucher).length; i++) {
                if (getResources().getStringArray(R.array.gst_nature_journal_voucher)[i].equals(group_type)) {
                    groupindex = i;
                    break;
                }

            }
            gst_nature_spinner.setSelection(groupindex);
            Preferences.getInstance(getApplicationContext()).setReason(response.getJournal_voucher().getData().getAttributes().getReason());
            Map mMap;
            for (int i = 0; i < response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().size(); i++) {
                mMap = new HashMap();
                if (groupindex == 1) {
                    mMap.put("gst_pos1", "1");
                } else if (groupindex == 2) {
                    mMap.put("gst_pos2", "2");
                } else if (groupindex == 6) {
                    mMap.put("gst_pos6", "6");
                } else if (groupindex == 7) {
                    mMap.put("gst_pos7", "7");
                }
                mMap.put("id", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getId());
                mMap.put("account_id", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getAccount_id());
                mMap.put("party_id", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getParty_id());
                mMap.put("inv_num", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getInvoice_no());
                mMap.put("diff_amount", String.valueOf(response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getAmount()));
                mMap.put("difference_amount", String.valueOf(response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getAmount()));
                mMap.put("rate", String.valueOf(response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getTax_rate()));
                mMap.put("gst", String.valueOf(response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getTax_rate()));
                mMap.put("cgst", String.valueOf(response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getCgst_amount()));
                mMap.put("sgst", String.valueOf(response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getSgst_amount()));
                mMap.put("igst", String.valueOf(response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getIgst_amount()));
                mMap.put("date", String.valueOf(response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getDate()));


                mMap.put("state", response.getJournal_voucher().getData().getAttributes().getAccount_debit().getState());
                state = response.getJournal_voucher().getData().getAttributes().getAccount_debit().getState();
                mMap.put("state_for_credit", response.getJournal_voucher().getData().getAttributes().getAccount_credit().getState());
                state_for_credit = response.getJournal_voucher().getData().getAttributes().getAccount_credit().getState();
                mMap.put("spRCNItem", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getRcm_nature());
                mMap.put("goodsItem", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getItc_eligibility());
                mMap.put("spITCEligibility", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getItc_eligibility());
                if (response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getSale_name() != null
                        && response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getSale_id() != null) {
                    mMap.put("sale_name", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getSale_name());
                    mMap.put("sale_id", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getSale_id());
                } else {
                    mMap.put("purchase_name", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getPurchase_name());
                    mMap.put("purchase_id", response.getJournal_voucher().getData().getAttributes().getJournal_item().getData().get(i).getAttributes().getPurchase_id());
                }
                appUser.mListMapForItemJournalVoucherNote.add(mMap);
            }
            LocalRepositories.saveAppUser(this, appUser);

            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    @Subscribe
    public void getVoucherNumber(GetVoucherNumbersResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            voucher_no.setText(response.getVoucher_number());

        } else {
            // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            // set_date.setOnClickListener(this);
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    @Subscribe
    public void editExpence(EditJournalVoucherResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Preferences.getInstance(getApplicationContext()).setAttachment("");
            Preferences.getInstance(getApplicationContext()).setUrlAttachment("");
            Intent intent = new Intent(this, JournalVoucherActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("forDate",true);
            startActivity(intent);
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_list_button_action, menu);
        if (fromJournalVoucher == true) {
            MenuItem item = menu.findItem(R.id.icon_id);
            item.setVisible(false);
        } else {
            MenuItem item = menu.findItem(R.id.icon_id);
            item.setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    /* @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId())
         {
             case R.id.icon_id:
                 Intent i = new Intent(getApplicationContext(),JournalVoucherActivity.class);
                 startActivity(i);
         }
         return super.onOptionsItemSelected(item);
     }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_id:
                Intent i = new Intent(getApplicationContext(), JournalVoucherActivity.class);
                startActivity(i);
                finish();
                return true;
            case android.R.id.home:
                if (fromJournalVoucher){
                    finish();
                }else {
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

        if (fromJournalVoucher){
            finish();
        }else {
            Intent intent = new Intent(this, TransactionDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
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

    public Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h = (int) (newHeight * densityMultiplier);
        int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

        photo = Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }


}