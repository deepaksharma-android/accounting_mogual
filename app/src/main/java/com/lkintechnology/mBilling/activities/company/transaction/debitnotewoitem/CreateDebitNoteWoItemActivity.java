package com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem;

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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem.AddCreditNoteItemActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.debitnotewoitem.CreateDebitNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.debitnotewoitem.EditDebitNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.debitnotewoitem.GetDebitNoteDetailsResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
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
public class CreateDebitNoteWoItemActivity extends RegisterAbstractActivity implements View.OnClickListener {

    @Bind(R.id.account_name_credit)
    TextView account_name_credit;
    @Bind(R.id.ll_spiner_item)
    LinearLayout llSpItem;
    @Bind(R.id.account_name_debit)
    TextView account_name_debit;
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
    private DatePickerDialog DatePickerDialog1,DatePickerDialog2;
    private static final int SELECT_PICTURE=1;
    private String selectedImagePath;
    InputStream inputStream = null;
    ProgressDialog mProgressDialog;
    Boolean fromDebitNote;
    String encodedString;
    String title;
    AppUser appUser;
    String state;
    public Boolean boolForGroupName=false;
    Bitmap photo;
    WebView mPdf_webview;
    private Uri imageToUploadUri;
     String spinnergstnature;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        appUser = LocalRepositories.getAppUser(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();

        appUser.voucher_type = "Debit Note";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        title="CREATE DEBIT NOTE";
        long date = System.currentTimeMillis();
       // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatter.format(date);
        set_date.setText(dateString);

        Boolean isConnected = ConnectivityReceiver.isConnected();

        fromDebitNote = getIntent().getBooleanExtra("fromDebitNote",false);
        if (fromDebitNote == true) {
            title="EDIT DEBIT NOTE";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_debit_note_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateDebitNoteWoItemActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_DEBIT_NOTE_DETAILS);
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
        }else{
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateDebitNoteWoItemActivity.this);
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
              /*  Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i.createChooser(i, "Select Picture"), SELECT_PICTURE);*/
              startDialog();
            }
        });

        account_name_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParameterConstant.forAccountIntentBool=false;
                ParameterConstant.forAccountIntentName="";
                ParameterConstant.forAccountIntentId="";
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors";
                //ParameterConstant.checkStartActivityResultForAccount =11;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount=false;
                ParameterConstant.handleAutoCompleteTextView=0;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });

        mSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ImageOpenActivity.class);
                intent.putExtra("encodedString",imageToUploadUri.toString());
                intent.putExtra("booleAttachment",false);
                startActivity(intent);
            }
        });
        llSpItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gst_nature_spinner.getSelectedItem().toString().equals("Cr. Note Issued Against Sale")) {
                    if (!account_name_credit.getText().toString().equals("")) {
                        if (!transaction_amount.getText().toString().equals("")) {
                            Intent intent = new Intent(CreateDebitNoteWoItemActivity.this, AddDebitNoteItemActivity.class);
                            intent.putExtra("amount", transaction_amount.getText().toString());
                            intent.putExtra("sp_position", "1");
                            intent.putExtra("state", state);
                            startActivity(intent);
                        } else {
                            gst_nature_spinner.setSelection(0);
                            Snackbar.make(coordinatorLayout, "Please enter amount", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        gst_nature_spinner.setSelection(0);
                        Snackbar.make(coordinatorLayout, "Please select party name", Snackbar.LENGTH_LONG).show();
                    }
                }else if(gst_nature_spinner.getSelectedItem().toString().equals("Dr. Note Received Against Purchase")){
                    if (!account_name_credit.getText().toString().equals("")) {
                        if (!transaction_amount.getText().toString().equals("")) {
                            Intent intent = new Intent(CreateDebitNoteWoItemActivity.this, AddDebitNoteItemActivity.class);
                            intent.putExtra("sp_position", "2");
                            intent.putExtra("amount", transaction_amount.getText().toString());
                            intent.putExtra("state", state);
                            startActivity(intent);
                        } else {
                            gst_nature_spinner.setSelection(0);
                            Snackbar.make(coordinatorLayout, "Please enter amount", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        gst_nature_spinner.setSelection(0);
                        Snackbar.make(coordinatorLayout, "Please select party name", Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Snackbar.make(coordinatorLayout, "Please select GST Nature", Snackbar.LENGTH_LONG).show();

                }
            }
        });

        gst_nature_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(fromDebitNote){
                    if(!gst_nature_spinner.getSelectedItem().toString().equals(spinnergstnature)){
                        appUser.mListMapForItemDebitNote.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }
                }
                else{
                    appUser.mListMapForItemDebitNote.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!voucher_no.getText().toString().equals("")){
                    if (!set_date.getText().toString().equals("")) {
                        if (!account_name_debit.getText().toString().equals("")) {
                            if (!transaction_amount.getText().toString().equals("")) {
                                appUser.debit_note_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                appUser.debit_note_date = set_date.getText().toString();
                                appUser.debit_note_voucher_no = voucher_no.getText().toString();
                                appUser.debit_note_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                                appUser.account_name_credit_note=account_name_credit.getText().toString();

                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.debit_note_amount = Double.parseDouble(transaction_amount.getText().toString());
                                }
                                appUser.debit_note_narration = transaction_narration.getText().toString();
                                appUser.debit_note_attachment = encodedString;

                                Boolean isConnected = ConnectivityReceiver.isConnected();
                                new AlertDialog.Builder(CreateDebitNoteWoItemActivity.this)
                                        .setTitle("Email")
                                        .setMessage(R.string.btn_send_email)
                                        .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                            appUser.email_yes_no = "true";
                                            LocalRepositories.saveAppUser(CreateDebitNoteWoItemActivity.this, appUser);
                                            if (isConnected) {
                                                mProgressDialog = new ProgressDialog(CreateDebitNoteWoItemActivity.this);
                                                mProgressDialog.setMessage("Info...");
                                                mProgressDialog.setIndeterminate(false);
                                                mProgressDialog.setCancelable(true);
                                                mProgressDialog.show();
                                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_DEBIT_NOTE);
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
                                            LocalRepositories.saveAppUser(CreateDebitNoteWoItemActivity.this, appUser);
                                            if (isConnected) {
                                                mProgressDialog = new ProgressDialog(CreateDebitNoteWoItemActivity.this);
                                                mProgressDialog.setMessage("Info...");
                                                mProgressDialog.setIndeterminate(false);
                                                mProgressDialog.setCancelable(true);
                                                mProgressDialog.show();
                                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_DEBIT_NOTE);
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
                                Snackbar.make(coordinatorLayout, "Please enter Amount", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select account name debit", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateDebitNoteWoItemActivity.this);
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
                if(!voucher_no.getText().toString().equals("")){
                    if (!set_date.getText().toString().equals("")) {
                        if (!account_name_debit.getText().toString().equals("")) {
                            if (!transaction_amount.getText().toString().equals("")) {
                                appUser.debit_note_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                appUser.debit_note_date = set_date.getText().toString();
                                appUser.debit_note_voucher_no = voucher_no.getText().toString();
                                appUser.debit_note_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                                appUser.account_name_credit_note=account_name_credit.getText().toString();

                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.debit_note_amount = Double.parseDouble(transaction_amount.getText().toString());
                                }
                                appUser.debit_note_narration = transaction_narration.getText().toString();
                                appUser.debit_note_attachment = encodedString;
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                Boolean isConnected = ConnectivityReceiver.isConnected();
                                if (isConnected) {
                                    mProgressDialog = new ProgressDialog(CreateDebitNoteWoItemActivity.this);
                                    mProgressDialog.setMessage("Info...");
                                    mProgressDialog.setIndeterminate(false);
                                    mProgressDialog.setCancelable(true);
                                    mProgressDialog.show();
                                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_DEBIT_NOTE);
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
                            }else {
                                Snackbar.make(coordinatorLayout, "Please enter Amount", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select account name debit", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateDebitNoteWoItemActivity.this);
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
        // click on spinner item

    }


    private void startDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CreateDebitNoteWoItemActivity.this);
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

        if (ParameterConstant.forAccountIntentBool) {
            String result = ParameterConstant.forAccountIntentName;
            appUser.account_name_debit_note_id = ParameterConstant.forAccountIntentId;
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            String[] name = result.split(",");
            account_name_debit.setText(name[0]);
            state=ParameterConstant.forAccountIntentState;
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
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
*/

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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

                case Cv.REQUEST_GALLERY:

                    try {
                        imageToUploadUri= data.getData();
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
                if (ParameterConstant.handleAutoCompleteTextView==1) {
                    boolForGroupName=true;
                    appUser.account_name_debit_note_id =ParameterConstant.id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    account_name_debit.setText(ParameterConstant.name);
                }else {
                    boolForGroupName=true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    state = data.getStringExtra("state");
                    appUser.account_name_debit_note_id =id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    String[] name = result.split(",");
                    account_name_debit.setText(name[0]);
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        appUser=LocalRepositories.getAppUser(this);
      /*  Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        if (bool) {
            if (!boolForGroupName) {
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.account_name_credit_note_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                account_name_debit.setText(name[0]);
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
        return R.layout.activity_create_debit_note_wo_item;
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
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createdebitnoteresponse(CreateDebitNoteResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "debit_note");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,appUser.company_name);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
           // voucher_no.setText("");
            transaction_amount.setText("");
            transaction_narration.setText("");
            account_name_debit.setText("");
            // account_name_credit.setText("");
            gst_nature_spinner.setSelection(0);
            encodedString="";
            mSelectedImage.setImageDrawable(null);
            mSelectedImage.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();

            new AlertDialog.Builder(CreateDebitNoteWoItemActivity.this)
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(CreateDebitNoteWoItemActivity.this, TransactionPdfActivity.class);
                        intent.putExtra("company_report",response.getHtml());
                        startActivity(intent);
                        /*ProgressDialog progressDialog = new ProgressDialog(CreateDebitNoteWoItemActivity.this);
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
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getDebitNoteDetails(GetDebitNoteDetailsResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            set_date.setText(response.getDebit_note().getData().getAttributes().getDate());
            voucher_no.setText(response.getDebit_note().getData().getAttributes().getVoucher_number());
            //account_name_credit.setText(response.getDebit_note().getData().getAttributes().getAccount_name_credit());
            account_name_debit.setText(response.getDebit_note().getData().getAttributes().getAccount_dedit().getName());
            appUser.account_name_debit_note_id=String.valueOf(response.getDebit_note().getData().getAttributes().getAccount_dedit().getId());
            LocalRepositories.saveAppUser(this,appUser);
            transaction_amount.setText(String.valueOf(response.getDebit_note().getData().getAttributes().getAmount()));
            transaction_narration.setText(response.getDebit_note().getData().getAttributes().getNarration());
            if(!response.getDebit_note().getData().getAttributes().getAttachment().equals("")){
                Glide.with(this).load(Uri.parse(response.getDebit_note().getData().getAttributes().getAttachment()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(mSelectedImage);
                mSelectedImage.setVisibility(View.VISIBLE);
            }
            else{
                mSelectedImage.setVisibility(View.GONE);
            }
            String group_type = response.getDebit_note().getData().getAttributes().getGst_nature().trim();
             spinnergstnature=group_type;
            int groupindex = -1;
            for (int i = 0; i<getResources().getStringArray(R.array.gst_nature_debit).length; i++) {
                if (getResources().getStringArray(R.array.gst_nature_debit)[i].equals(group_type)) {
                    groupindex = i;
                    break;
                }
            }
            gst_nature_spinner.setSelection(groupindex);
            Map mMap = new HashMap<>();
            for (int i = 0; i < response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().size(); i++) {
                mMap.put("id",response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getId());
                mMap.put("inv_num", response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getInvoice_no());
                mMap.put("difference_amount", String.valueOf(response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getAmount()));
                mMap.put("gst",String.valueOf(response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getTax_rate()));
                if(groupindex==1){
                    mMap.put("sp_position","1");
                }
                else if(groupindex==2){
                    mMap.put("sp_position","2");
                }

                if (response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getCgst_amount()!=null) {
                    mMap.put("cgst", String.valueOf(response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getCgst_amount()));
                    mMap.put("sgst", String.valueOf(response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getSgst_amount()));

                } else {
                    mMap.put("igst", String.valueOf(response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getIgst_amount()));

                }
                mMap.put("date", response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getDate());
                mMap.put("goodsItem", response.getDebit_note().getData().getAttributes().getDebit_note_item().getData().get(i).getAttributes().getItc_eligibility());

                mMap.put("state",response.getDebit_note().getData().getAttributes().getAccount_dedit().getState());
                appUser.mListMapForItemDebitNote.add(mMap);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }

        }
        else{
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
    public void editExpence(EditDebitNoteResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Intent intent = new Intent(this, DebitNoteWoItemActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            startActivity(intent);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_list_button_action,menu);
           if(fromDebitNote==true){
               MenuItem item = menu.findItem(R.id.icon_id);
               item.setVisible(false);
           }else{
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
                Intent i = new Intent(getApplicationContext(),DebitNoteWoItemActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_id:
                Intent i = new Intent(getApplicationContext(),DebitNoteWoItemActivity.class);
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