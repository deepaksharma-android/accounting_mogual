package com.lkintechnology.mBilling.activities.company.transaction.bankcasewithdraw;

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
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.CreateBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.EditBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.GetBankCashWithdrawDetailsResponse;
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
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateBankCaseWithdrawActivity extends RegisterAbstractActivity implements View.OnClickListener {

    @Bind(R.id.date)
    TextView set_date;
    @Bind(R.id.browse_image)
    LinearLayout mBrowseImage;
    @Bind(R.id.selected_image)
    ImageView mSelectedImage;
    @Bind(R.id.withdraw_from)
    TextView withdraw_from;
    @Bind(R.id.withdraw_by)
    TextView withdraw_by;
    @Bind(R.id.transaction_spinner)
    Spinner transaction_spinner;
    @Bind(R.id.vouchar_no)
    TextView voucher_no;
    @Bind(R.id.transaction_amount)
    EditText transaction_amount;
    @Bind(R.id.transaction_narration)
    EditText transaction_narration;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    Snackbar snackbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    InputStream inputStream = null;
    String encodedString;
    String title;
    Boolean fromBankcashWithdraw;
    ProgressDialog mProgressDialog;
    AppUser appUser;

    public Boolean boolForReceivedFrom = false;
    public Boolean boolForReceivedBy = false;
    public static int intStartActivityForResult = 0;
    Bitmap photo;
    WebView mPdf_webview;
    private Uri imageToUploadUri;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_bank_case_deposit);
        ButterKnife.bind(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();
        appUser.voucher_type = "Bank Cash Withdraw";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        withdraw_by.setText(appUser.withdraw_by_name);
        withdraw_from.setText(appUser.withdraw_from_name);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        set_date.setText(dateString);

        Boolean isConnected = ConnectivityReceiver.isConnected();

        title = "CREATE BANK CASH WITHDRAW";
        fromBankcashWithdraw = getIntent().getBooleanExtra("fromBankCashWithdraw", false);
        if (fromBankcashWithdraw == true) {
            title = "EDIT BANK CASH WITHDRAW";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_bank_cash_withdraw_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateBankCaseWithdrawActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BANK_CASH_WITHDRAW_DETAILS);
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
                mProgressDialog = new ProgressDialog(CreateBankCaseWithdrawActivity.this);
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

        withdraw_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParameterConstant.forAccountIntentBool = false;
                ParameterConstant.forAccountIntentName = "";
                ParameterConstant.forAccountIntentId = "";
                ParameterConstant.accountSwitching = 1;
                //intStartActivityForResult = 1;
                //ParameterConstant.checkStartActivityResultForAccount = 5;
                appUser.account_master_group = "Bank Accounts";
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 4);
            }
        });

        withdraw_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParameterConstant.forAccountIntentBool = false;
                ParameterConstant.forAccountIntentName = "";
                ParameterConstant.forAccountIntentId = "";
                ParameterConstant.accountSwitching = 2;
                //intStartActivityForResult = 2;
                //ParameterConstant.checkStartActivityResultForAccount = 5;
                appUser.account_master_group = "Cash-in-hand";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount = false;
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 5);
            }
        });

        mSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageToUploadUri == null) {
                   /* Bitmap bitmap = ((GlideBitmapDrawable)mSelectedImage.getDrawable()).getBitmap();
                    String encodedString = Helpers.bitmapToBase64(bitmap);*/
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
                        if (!withdraw_from.getText().toString().equals("")) {
                            if (!withdraw_by.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.bank_cash_withdraw_voucher_series = transaction_spinner.getSelectedItem().toString();
                                    appUser.bank_cash_withdraw_date = set_date.getText().toString();
                                    appUser.bank_cash_withdraw_voucher_no = voucher_no.getText().toString();
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.bank_cash_withdraw_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.bank_cash_withdraw_narration = transaction_narration.getText().toString();
                                    appUser.bank_cash_withdraw_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        mProgressDialog = new ProgressDialog(CreateBankCaseWithdrawActivity.this);
                                        mProgressDialog.setMessage("Info...");
                                        mProgressDialog.setIndeterminate(false);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();

                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_BANK_CASH_WITHDRAW);
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
                                } else {
                                    Snackbar.make(coordinatorLayout, "Please enter Amount", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please select withdraw by", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select withdraw from", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateBankCaseWithdrawActivity.this);
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
                        if (!withdraw_from.getText().toString().equals("")) {
                            if (!withdraw_by.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.bank_cash_withdraw_voucher_series = transaction_spinner.getSelectedItem().toString();
                                    appUser.bank_cash_withdraw_date = set_date.getText().toString();
                                    appUser.bank_cash_withdraw_voucher_no = voucher_no.getText().toString();
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.bank_cash_withdraw_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.bank_cash_withdraw_narration = transaction_narration.getText().toString();
                                    appUser.bank_cash_withdraw_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        mProgressDialog = new ProgressDialog(CreateBankCaseWithdrawActivity.this);
                                        mProgressDialog.setMessage("Info...");
                                        mProgressDialog.setIndeterminate(false);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();
                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_BANK_CASH_WITHDRAW);
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
                                Snackbar.make(coordinatorLayout, "Please select withdraw by", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select withdraw from", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateBankCaseWithdrawActivity.this);
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
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CreateBankCaseWithdrawActivity.this);
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
            appUser.withdraw_from_id = ParameterConstant.forAccountIntentId;
            appUser.withdraw_from_name = name[0];
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            withdraw_from.setText(name[0]);
        } else if (ParameterConstant.forAccountIntentBool && ParameterConstant.accountSwitching == 2) {
            String result = ParameterConstant.forAccountIntentName;
            String[] name = result.split(",");
            appUser.withdraw_by_id = ParameterConstant.forAccountIntentId;
            appUser.withdraw_by_name = name[0];
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            withdraw_by.setText(name[0]);
        }

       /* if (resultCode == RESULT_OK) {
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

                    //  encodedString=compressImage(Base64.encodeToString(bytes, Base64.DEFAULT));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }*/

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

            if (requestCode == 4) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForReceivedFrom = true;
                    appUser.withdraw_from_id = ParameterConstant.id;
                    appUser.withdraw_from_name = ParameterConstant.name;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    withdraw_from.setText(ParameterConstant.name);
                } else {
                    boolForReceivedFrom = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String[] name = result.split(",");
                    Timber.i("MY IDIDID" + id);
                    appUser.withdraw_from_id = id;
                    appUser.withdraw_from_name = name[0];
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    withdraw_from.setText(name[0]);
                }
            }
            if (requestCode == 5) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForReceivedBy = true;
                    appUser.withdraw_by_id = ParameterConstant.id;
                    appUser.withdraw_by_name = ParameterConstant.name;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    withdraw_by.setText(ParameterConstant.name);
                } else {
                    boolForReceivedBy = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String[] name = result.split(",");
                    Timber.i("MY IDIDID" + id);
                    appUser.withdraw_by_id = id;
                    appUser.withdraw_by_name = name[0];
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                    withdraw_by.setText(name[0]);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
                String[] name = result.split(",");
                Timber.i("MY IDIDID" + id);
                appUser.withdraw_from_id = id;
                appUser.withdraw_from_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                withdraw_from.setText(name[0]);
            }
            if (!boolForReceivedBy) {

                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String[] name = result.split(",");
                Timber.i("MY IDIDID" + id);
                appUser.withdraw_by_id = id;
                appUser.withdraw_by_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                withdraw_by.setText(name[0]);

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
        return R.layout.activity_create_bank_case_withdraw;
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
    public void createbankcashwithdrawresponse(CreateBankCashWithdrawResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "bank_cash_withdraw");
            ;
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, appUser.company_name);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            transaction_amount.setText("");
            transaction_narration.setText("");
            withdraw_from.setText("");
            withdraw_by.setText("");
            //set_date.setText( appUser.bank_cash_withdraw_date);
            encodedString = "";
            mSelectedImage.setImageDrawable(null);
            mSelectedImage.setVisibility(View.GONE);
            Preferences.getInstance(getApplicationContext()).setAttachment("");
            Preferences.getInstance(getApplicationContext()).setUrlAttachment("");
            new AlertDialog.Builder(CreateBankCaseWithdrawActivity.this)
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(CreateBankCaseWithdrawActivity.this, TransactionPdfActivity.class);
                        intent.putExtra("company_report", response.getHtml());
                        startActivity(intent);
                       /* ProgressDialog progressDialog = new ProgressDialog(CreateBankCaseWithdrawActivity.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                        String htmlString = response.getHtml();
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
    public void getBankCashWithdrawDetails(GetBankCashWithdrawDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            set_date.setText(response.getBank_cash_withdraw().getData().getAttributes().getDate());
            appUser.bank_cash_withdraw_date = response.getBank_cash_withdraw().getData().getAttributes().getDate();
            voucher_no.setText(response.getBank_cash_withdraw().getData().getAttributes().getVoucher_number());
            withdraw_from.setText(response.getBank_cash_withdraw().getData().getAttributes().getWithdraw_from());
            withdraw_by.setText(response.getBank_cash_withdraw().getData().getAttributes().getWithdraw_by());
            transaction_amount.setText(String.valueOf(response.getBank_cash_withdraw().getData().getAttributes().getAmount()));
            transaction_narration.setText(response.getBank_cash_withdraw().getData().getAttributes().getNarration());
            Preferences.getInstance(getApplicationContext()).setAttachment("");
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            if (!Helpers.mystring(response.getBank_cash_withdraw().getData().getAttributes().getAttachment()).equals("")) {
                Preferences.getInstance(getApplicationContext()).setUrlAttachment(response.getBank_cash_withdraw().getData().getAttributes().getAttachment());
                Glide.with(this).load(Helpers.mystring(response.getBank_cash_withdraw().getData().getAttributes().getAttachment())).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).into(mSelectedImage);
                mSelectedImage.setVisibility(View.VISIBLE);
            } else {
                mSelectedImage.setVisibility(View.GONE);
            }

            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    /* @Subscribe
    public void getBankCashWithdraw(GetBankCashWithdrawResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

                Intent intent = new Intent(this, BankCaseWithdrawActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }*/

    @Subscribe
    public void editBankCashWithdraw(EditBankCashWithdrawResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Preferences.getInstance(getApplicationContext()).setAttachment("");
            Preferences.getInstance(getApplicationContext()).setUrlAttachment("");
            Intent intent = new Intent(this, BankCaseWithdrawActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            startActivity(intent);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            // set_date.setOnClickListener(this);
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_list_button_action, menu);
        if (fromBankcashWithdraw == true) {
            MenuItem item = menu.findItem(R.id.icon_id);
            item.setVisible(false);
        } else {
            MenuItem item = menu.findItem(R.id.icon_id);
            item.setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_id:
                Intent i = new Intent(getApplicationContext(), BankCaseWithdrawActivity.class);
                startActivity(i);
                finish();
                return true;
            case android.R.id.home:
                if (fromBankcashWithdraw) {
                    finish();
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
        if (fromBankcashWithdraw) {
            finish();
        } else {
            Intent intent = new Intent(this, TransactionDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }


/*
    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }*/

  /*  public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }       final float totalPixels = width * height;       final float totalReqPixelsCap = reqWidth * reqHeight * 2;       while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
*/
  /*  public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }*/

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