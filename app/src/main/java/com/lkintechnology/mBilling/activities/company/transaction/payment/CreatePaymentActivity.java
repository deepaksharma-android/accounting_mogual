package com.lkintechnology.mBilling.activities.company.transaction.payment;

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
import android.widget.AdapterView;
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
import com.lkintechnology.mBilling.activities.company.navigation.reports.account_group.PdcActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.activities.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.CreatePaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.EditPaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.GetPaymentDetailsResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeletePayment;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.TypefaceCache;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreatePaymentActivity extends RegisterAbstractActivity implements View.OnClickListener {

    @Bind(R.id.date_pdc_textview)
    TextView date_pdc_textview;
    @Bind(R.id.date_pdc_layout)
    LinearLayout date_pdc_layout;
    @Bind(R.id.date_pdc)
    TextView set_date_pdc;
    @Bind(R.id.paid_from_layout)
    LinearLayout paid_from_layout;
    @Bind(R.id.paid_to_layout)
    LinearLayout paid_to_layout;
    @Bind(R.id.paid_from)
    TextView paid_from;
    @Bind(R.id.paid_to)
    TextView paid_to;
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
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1, DatePickerDialog2;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    InputStream inputStream = null;
    ProgressDialog mProgressDialog;
    Boolean fromPayment;
    String encodedString;
    String title, from;
    AppUser appUser;
    public Boolean boolForReceivedFrom = false;
    public Boolean boolForReceivedBy = false;
    public static int intStartActivityForResult = 0;
    public Bundle bundle;
    Bitmap photo;

    public static int iconHandlerVariable = 0;
    WebView mPdf_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_bank_case_deposit);
        ButterKnife.bind(this);
        iconHandlerVariable = 0;
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            from = bundle.getString("from");
        }

        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();

        appUser.voucher_type = "Payment";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        paid_to.setText("" + appUser.payment_paid_to_name);
        paid_from.setText("" + appUser.payment_paid_from_name);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatter.format(date);
        set_date.setText(dateString);
        set_date_pdc.setText(dateString);

        Boolean isConnected = ConnectivityReceiver.isConnected();

        title = "CREATE PAYMENT";
        iconHandlerVariable = 1;
        fromPayment = getIntent().getBooleanExtra("fromPayment", false);
        if (fromPayment == true) {
            if (from.equals("pdcdetail")) {
                from = "pdcdetail";
                // Toast.makeText(CreateReceiptVoucherActivity.this, "i am here", Toast.LENGTH_SHORT).show();
            } else {
                from = "payment";
            }
            title = "EDIT PAYMENT";
            iconHandlerVariable = 2;
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_payment_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreatePaymentActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PAYMENT_DETAILS);
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
                mProgressDialog = new ProgressDialog(CreatePaymentActivity.this);
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

        paid_to_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intStartActivityForResult = 1;
                ParameterConstant.checkStartActivityResultForAccount = 3;
                //appUser.account_master_group = "Sundry Debtors,Sundry Creditors";
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount = false;
                ParameterConstant.handleAutoCompleteTextView=0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });
        paid_from_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intStartActivityForResult = 2;
                ParameterConstant.checkStartActivityResultForAccount = 3;
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
                        if (!paid_to.getText().toString().equals("")) {
                            if (!paid_from.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.payment_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                    appUser.payment_date = set_date.getText().toString();
                                    appUser.payment_voucher_no = voucher_no.getText().toString();
                                    appUser.payment_type = type_spinner.getSelectedItem().toString();
                                    appUser.payment_date_pdc = set_date_pdc.getText().toString();
                                    appUser.payment_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.payment_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.payment_narration = transaction_narration.getText().toString();
                                    appUser.payment_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    new AlertDialog.Builder(CreatePaymentActivity.this)
                                            .setTitle("Email")
                                            .setMessage(R.string.btn_send_email)
                                            .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                                appUser.email_yes_no = "true";
                                                LocalRepositories.saveAppUser(CreatePaymentActivity.this, appUser);
                                                if (isConnected) {
                                                    mProgressDialog = new ProgressDialog(CreatePaymentActivity.this);
                                                    mProgressDialog.setMessage("Info...");
                                                    mProgressDialog.setIndeterminate(false);
                                                    mProgressDialog.setCancelable(true);
                                                    mProgressDialog.show();
                                                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_PAYMENT);
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
                                                LocalRepositories.saveAppUser(CreatePaymentActivity.this, appUser);
                                                if (isConnected) {
                                                    mProgressDialog = new ProgressDialog(CreatePaymentActivity.this);
                                                    mProgressDialog.setMessage("Info...");
                                                    mProgressDialog.setIndeterminate(false);
                                                    mProgressDialog.setCancelable(true);
                                                    mProgressDialog.show();
                                                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_PAYMENT);
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
                                Snackbar.make(coordinatorLayout, "Please enter Amount", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select paid from", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select paid to", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                }
            }else
                {
                Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreatePaymentActivity.this);
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
                        if (!paid_to.getText().toString().equals("")) {
                            if (!paid_from.getText().toString().equals("")) {
                                if (!transaction_amount.getText().toString().equals("")) {

                                    appUser.payment_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                    appUser.payment_date = set_date.getText().toString();
                                    appUser.payment_voucher_no = voucher_no.getText().toString();
                                    appUser.payment_type = type_spinner.getSelectedItem().toString();
                                    appUser.payment_date_pdc = set_date_pdc.getText().toString();
                                    appUser.payment_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                                    if (!transaction_amount.getText().toString().equals("")) {
                                        appUser.payment_amount = Double.parseDouble(transaction_amount.getText().toString());
                                    }
                                    appUser.payment_narration = transaction_narration.getText().toString();
                                    appUser.payment_attachment = encodedString;
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        mProgressDialog = new ProgressDialog(CreatePaymentActivity.this);
                                        mProgressDialog.setMessage("Info...");
                                        mProgressDialog.setIndeterminate(false);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();

                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_PAYMENT);
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
                                Snackbar.make(coordinatorLayout, "Please select paid from", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select paid to", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreatePaymentActivity.this);
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
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CreatePaymentActivity.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_list_button_action, menu);
        if(fromPayment==true){
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
                Intent i = new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void setDateField() {
        set_date.setOnClickListener(this);
        set_date_pdc.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();

        // set_date.setText("22 Nov 2017");
        // set_date_pdc.setText("22 Nov 2017");

        DatePickerDialog1 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                set_date.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

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
               if (ParameterConstant.handleAutoCompleteTextView==1){
                   boolForReceivedFrom = true;
                   appUser.payment_paid_to_id = ParameterConstant.id;
                   appUser.payment_paid_to_name = ParameterConstant.name;
                   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                   paid_to.setText(ParameterConstant.name);
               }else {
                   boolForReceivedFrom = true;
                   String result = data.getStringExtra("name");
                   String id = data.getStringExtra("id");
                   String[] name = result.split(",");
                   appUser.payment_paid_to_id = id;
                   appUser.payment_paid_to_name = name[0];
                   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                   paid_to.setText(name[0]);
               }
            }
            if (requestCode == 3) {
               if (ParameterConstant.handleAutoCompleteTextView==1){
                   boolForReceivedBy = true;
                   appUser.payment_paid_from_id = ParameterConstant.id;
                   appUser.payment_paid_from_name = ParameterConstant.name;
                   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                   paid_from.setText(ParameterConstant.name);
               }else {
                   boolForReceivedBy = true;
                   String result = data.getStringExtra("name");
                   String id = data.getStringExtra("id");
                   String[] name = result.split(",");
                   appUser.payment_paid_from_id = id;
                   appUser.payment_paid_from_name = name[0];
                   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                   paid_from.setText(name[0]);
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
                appUser.payment_paid_to_id = id;
                appUser.payment_paid_to_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                paid_to.setText(name[0]);
            }
            if (!boolForReceivedBy) {
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String[] name = result.split(",");
                appUser.payment_paid_from_id = id;
                appUser.payment_paid_from_name = name[0];
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                paid_from.setText(name[0]);
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
        return R.layout.activity_create_payment;
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
    public void createpaymentresponse(CreatePaymentResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            //voucher_no.setText("");
            transaction_amount.setText("");
            transaction_narration.setText("");
            paid_from.setText("");
            paid_to.setText("");
            type_spinner.setSelection(0);
            gst_nature_spinner.setSelection(0);
            mSelectedImage.setImageResource(0);
            mSelectedImage.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();

            new AlertDialog.Builder(CreatePaymentActivity.this)
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(CreatePaymentActivity.this, TransactionPdfActivity.class);
                        intent.putExtra("company_report",response.getHtml());
                        startActivity(intent);

                       /* ProgressDialog progressDialog = new ProgressDialog(CreatePaymentActivity.this);
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
    public void getPaymentDetails(GetPaymentDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            set_date.setText(response.getPayment().getData().getAttributes().getDate());
            voucher_no.setText(response.getPayment().getData().getAttributes().getVoucher_number());
            //set_date_pdc.setText(response.getPayment().getData().getAttributes().getPdc_date());
            paid_from.setText(response.getPayment().getData().getAttributes().getPaid_from());
            paid_to.setText(response.getPayment().getData().getAttributes().getPaid_to());
            transaction_amount.setText(String.valueOf(response.getPayment().getData().getAttributes().getAmount()));
            transaction_narration.setText(response.getPayment().getData().getAttributes().getNarration());
            if (!response.getPayment().getData().getAttributes().getAttachment().equals("")) {
                Glide.with(this).load(Uri.parse(response.getPayment().getData().getAttributes().getAttachment()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(mSelectedImage);
                mSelectedImage.setVisibility(View.VISIBLE);
            } else {
                mSelectedImage.setVisibility(View.GONE);
            }

            String type_pdc_regular = response.getPayment().getData().getAttributes().getPayment_type().trim();
            if (type_pdc_regular.equals("PDC")) {
                type_spinner.setSelection(1);
                set_date_pdc.setText(response.getPayment().getData().getAttributes().getPdc_date());
            } else {
                type_spinner.setSelection(0);
                set_date_pdc.setText("");
            }

            String group_type = response.getPayment().getData().getAttributes().getGst_nature().trim();
            int groupindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.gst_nature_payment).length; i++) {
                if (getResources().getStringArray(R.array.gst_nature_payment)[i].equals(group_type)) {
                    groupindex = i;
                    break;
                }

            }
            gst_nature_spinner.setSelection(groupindex);
            // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editPayment(EditPaymentResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            if (from != null) {
                if (from.equals("pdcdetail")) {
                    Intent intent = new Intent(this, PdcActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
                    startActivity(intent);
                    Snackbar
                            .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(this, PaymentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
                    startActivity(intent);
                    Snackbar
                            .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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
                if (iconHandlerVariable == 2) {
                    String receipt_payment_id=  appUser.edit_payment_id;
                    EventBus.getDefault().post(new EventDeletePayment(receipt_payment_id));
                } else {
                    Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
                return true;
            case android.R.id.home:
                if (from != null) {
                    if (from.equals("pdcdetail")) {
                        Intent intent = new Intent(this, PdcActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
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
            if (from.equals("pdcdetail")) {
                Intent intent = new Intent(this, PdcActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (from.equals("Payment")) ;
            {
                finish();
            }
        } else {
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

}