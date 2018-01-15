package com.lkintechnology.mBilling.activities.company.transaction.bankcasedeposit;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
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

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigation.reports.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.activities.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.CreateBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.EditBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.GetBankCashDepositDetailsResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.TypefaceCache;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CreateBankCaseDepositActivity extends RegisterAbstractActivity implements View.OnClickListener {

    @Bind(R.id.date)
    TextView set_date;
    @Bind(R.id.browse_image)
    LinearLayout mBrowseImage;
    @Bind(R.id.selected_image)
    ImageView mSelectedImage;
    @Bind(R.id.deposit_to)
    TextView deposit_to;
    @Bind(R.id.deposit_by)
    TextView deposit_by;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    @Bind(R.id.transaction_spinner)
    Spinner transaction_spinner;
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
    private DatePickerDialog DatePickerDialog1;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    InputStream inputStream = null;
    String encodedString;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Boolean fromBankcashDeposit;
    String title;
    public Boolean boolForReceivedFrom = false;
    public Boolean boolForReceivedBy = false;
    public static int intStartActivityForResult = 0;
    Bitmap photo;
    WebView mPdf_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_bank_case_deposit);
        ButterKnife.bind(this);

        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();
        appUser.voucher_type = "Bank Cash Deposit";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatter.format(date);
        set_date.setText(dateString);
        deposit_to.setText("" + appUser.deposit_to_name);
        deposit_by.setText("" + appUser.deposit_by_name);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        title = "CREATE BANK CASH DEPOSIT";
        fromBankcashDeposit = getIntent().getBooleanExtra("fromBankCashDeposit", false);
        if (fromBankcashDeposit == true) {
            title = "EDIT BANK CASH DEPOSIT";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_bank_cash_deposit_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateBankCaseDepositActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BANK_CASH_DEPOSIT_DETAILS);
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
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateBankCaseDepositActivity.this);
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
        mBrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i.createChooser(i, "Select Picture"), SELECT_PICTURE);*/
                startDialog();

            }
        });

        deposit_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intStartActivityForResult = 1;
                appUser.account_master_group = "Bank Accounts";
                ParameterConstant.checkStartActivityResultForAccount = 4;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount = false;
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });

        deposit_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intStartActivityForResult = 2;
                appUser.account_master_group = "Cash-in-hand";
                ParameterConstant.checkStartActivityResultForAccount = 4;
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 3);
            }
        });
        mSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ImageOpenActivity.class);
                intent.putExtra("encodedString",encodedString);
                intent.putExtra("booleAttachment",false);
                startActivity(intent);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!voucher_no.getText().toString().equals("")) {
                    if (!set_date.getText().toString().equals("")) {
                        if (!deposit_to.getText().toString().equals("")) {
                            if (!deposit_by.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.bank_cash_deposit_voucher_series = transaction_spinner.getSelectedItem().toString();
                                    appUser.bank_cash_deposit_date = set_date.getText().toString();
                                    appUser.bank_cash_deposit_voucher_no = voucher_no.getText().toString();
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.bank_cash_deposit_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.bank_cash_deposit_narration = transaction_narration.getText().toString();
                                    appUser.bank_cash_deposit_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        mProgressDialog = new ProgressDialog(CreateBankCaseDepositActivity.this);
                                        mProgressDialog.setMessage("Info...");
                                        mProgressDialog.setIndeterminate(false);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();
                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_BANK_CASH_DEPOSIT);
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
                                Snackbar.make(coordinatorLayout, "Please select deposit by", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select deposit to", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateBankCaseDepositActivity.this);
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
                        if (!deposit_to.getText().toString().equals("")) {
                            if (!deposit_by.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.bank_cash_deposit_voucher_series = transaction_spinner.getSelectedItem().toString();
                                    appUser.bank_cash_deposit_date = set_date.getText().toString();
                                    appUser.bank_cash_deposit_voucher_no = voucher_no.getText().toString();
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.bank_cash_deposit_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.bank_cash_deposit_narration = transaction_narration.getText().toString();
                                    appUser.bank_cash_deposit_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        mProgressDialog = new ProgressDialog(CreateBankCaseDepositActivity.this);
                                        mProgressDialog.setMessage("Info...");
                                        mProgressDialog.setIndeterminate(false);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();
                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_BANK_CASH_DEPOSIT);
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
                                Snackbar.make(coordinatorLayout, "Please select deposit by", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select deposit to", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateBankCaseDepositActivity.this);
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

    //Image open on popip

   /* private void loadPhoto() {
        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = getLayoutInflater().inflate(R.layout.activity_image_open, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.fullimage);
        imageView.setImageResource(R.drawable.app_bg);
        imageDialog.setPositiveButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        imageDialog.create();
        imageDialog.show();
    }
*/
    private void startDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CreateBankCaseDepositActivity.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intCamera.putExtra("android.intent.extras.CAMERA_FACING", 1);

                if (intCamera.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intCamera, Cv.REQUEST_CAMERA);
                }

            }
        });

        myAlertDialog.setNegativeButton("Gallary",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intGallery = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intGallery, Cv.REQUEST_GALLERY);

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
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }*/

        if (resultCode == RESULT_OK) {
            photo = null;
            switch (requestCode) {

                case Cv.REQUEST_CAMERA:

                    photo = (Bitmap) data.getExtras().get("data");
                    encodedString = Helpers.bitmapToBase64(photo);
                    mSelectedImage.setVisibility(View.VISIBLE);
                    mSelectedImage.setImageBitmap(photo);
                    break;

                case Cv.REQUEST_GALLERY:

                    try {
                        photo = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(),
                                ContentUris.parseId(data.getData()),
                                MediaStore.Images.Thumbnails.MINI_KIND, null);
                        encodedString = Helpers.bitmapToBase64(photo);
                        mSelectedImage.setVisibility(View.VISIBLE);
                        mSelectedImage.setImageBitmap(photo);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            if (requestCode == 2) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForReceivedFrom = true;
                    appUser.deposit_to_id = String.valueOf(ParameterConstant.id);
                    appUser.deposit_to_name = ParameterConstant.name;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    deposit_to.setText(ParameterConstant.name);
                } else {
                    boolForReceivedFrom = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String[] name = result.split(",");
                    appUser.deposit_to_id = String.valueOf(id);
                    appUser.deposit_to_name = name[0];
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    deposit_to.setText(name[0]);
                }
            }
            if (requestCode == 3) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForReceivedBy = true;
                    appUser.deposit_by_id = String.valueOf(ParameterConstant.id);
                    appUser.deposit_by_name = ParameterConstant.name;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    deposit_by.setText(ParameterConstant.name);
                } else {
                    boolForReceivedBy = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String[] name = result.split(",");
                    appUser.deposit_by_id = String.valueOf(id);
                    appUser.deposit_by_name = name[0];
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    deposit_by.setText(name[0]);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
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
                appUser.deposit_to_id = String.valueOf(id);
                appUser.deposit_to_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                deposit_to.setText(name[0]);
            }
            if (!boolForReceivedBy) {

                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String[] name = result.split(",");
                appUser.deposit_by_id = String.valueOf(id);
                appUser.deposit_by_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                deposit_by.setText(name[0]);

            }
        }

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
        return R.layout.activity_create_bank_case_deposit;
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
    public void createbankcashdepositresponse(CreateBankCashDepositResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            //Toast.makeText(CreateBankCaseDepositActivity.this, ""+ response.getMessage(), Toast.LENGTH_SHORT).show();
            // voucher_no.setText("");
            transaction_amount.setText("");
            transaction_narration.setText("");
            deposit_by.setText("");
            deposit_to.setText("");
            mSelectedImage.setImageResource(0);
            mSelectedImage.setVisibility(View.GONE);
            //mSelectedImage.setImageDrawable(null);

            new AlertDialog.Builder(CreateBankCaseDepositActivity.this)
                    .setTitle("Print/Preview")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(CreateBankCaseDepositActivity.this, TransactionPdfActivity.class);
                        intent.putExtra("company_report", response.getHtml());
                        startActivity(intent);

                      /*  ProgressDialog progressDialog = new ProgressDialog(CreateBankCaseDepositActivity.this);
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
                        }, 5 * 1000);
*/

                    })
                    .setNegativeButton(R.string.btn_cancel, null)
                    .show();

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            //Toast.makeText(CreateBankCaseDepositActivity.this, ""+ response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void getBankCashDepositDetails(GetBankCashDepositDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            set_date.setText(response.getBank_cash_deposit().getData().getAttributes().getDate());
            voucher_no.setText(response.getBank_cash_deposit().getData().getAttributes().getVoucher_number());
            deposit_to.setText(response.getBank_cash_deposit().getData().getAttributes().getDeposit_to());
            deposit_by.setText(response.getBank_cash_deposit().getData().getAttributes().getDeposit_by());
            transaction_amount.setText(String.valueOf(response.getBank_cash_deposit().getData().getAttributes().getAmount()));
            transaction_narration.setText(response.getBank_cash_deposit().getData().getAttributes().getNarration());
            if (!response.getBank_cash_deposit().getData().getAttributes().getAttachment().equals("")) {
                // Glide.with(this).load(response.getBank_cash_deposit().getData().getAttributes().getAttachment()).into(mSelectedImage);
                Glide.with(this).load(Uri.parse(response.getBank_cash_deposit().getData().getAttributes().getAttachment()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(mSelectedImage);
                mSelectedImage.setVisibility(View.VISIBLE);
            } else {
                mSelectedImage.setVisibility(View.GONE);
            }
            // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editBankCashDeposit(EditBankCashDepositResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Intent intent = new Intent(this, BankCaseDepositListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            startActivity(intent);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getVoucherNumber(GetVoucherNumbersResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            voucher_no.setText(response.getVoucher_number());

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            // set_date.setOnClickListener(this);
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        //mProgressDialog.dismiss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_list_button_action, menu);
        if (fromBankcashDeposit == true) {
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
                Intent i = new Intent(getApplicationContext(), BankCaseDepositListActivity.class);
                startActivity(i);
                finish();
                return true;
            case android.R.id.home:
                Intent intent = new Intent(this, TransactionDashboardActivity.class);
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

        Intent intent = new Intent(this, TransactionDashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

   /* public Bitmap toBitmap(String encodedString) {
        byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
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
}
