package com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem;

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
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.CreateCreditNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.EditCreditNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.GetCreditNoteDetailsResponse;
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
import java.util.List;
import java.util.Locale;
import butterknife.Bind;
import butterknife.ButterKnife;
public class CreateCreditNoteWoItemActivity extends RegisterAbstractActivity implements View.OnClickListener {

    @Bind(R.id.account_name_credit)
    TextView account_name_credit;
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
    Boolean fromCreditNote;
    String encodedString;
    String title;
    AppUser appUser;
    public Boolean boolForGroupName=false;
    Bitmap photo;
    WebView mPdf_webview;
    private Uri imageToUploadUri;;
   // public static boolean creditNoteStatic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_bank_case_deposit);
        ButterKnife.bind(this);
       gst_nature_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               appUser.mListMapForItemCreditNote.clear();
               LocalRepositories.saveAppUser(getApplicationContext(),appUser);
               if (position==1){
                   if (!account_name_credit.getText().toString().equals("")){
                       if (!transaction_amount.getText().toString().equals("")){
                           Intent intent=new Intent(CreateCreditNoteWoItemActivity.this,CreditNoteItemDetailActivity.class);
                           intent.putExtra("amount",transaction_amount.getText().toString());
                           intent.putExtra("fromsp2",String.valueOf(position));
                           startActivity(intent);
                       }else{
                           Snackbar.make(coordinatorLayout, "please enter amount", Snackbar.LENGTH_LONG).show();
                       }
                   }

               }else if(position==2) {
                   if (!account_name_credit.getText().toString().equals("")){
                       if (!transaction_amount.getText().toString().equals("")){
                           Intent intent=new Intent(CreateCreditNoteWoItemActivity.this,CreditNoteItemDetailActivity.class);
                           intent.putExtra("fromsp2",String.valueOf(position));
                           intent.putExtra("amount",transaction_amount.getText().toString());
                           startActivity(intent);
                       }else{
                           Snackbar.make(coordinatorLayout, "please enter amount", Snackbar.LENGTH_LONG).show();
                       }
                   }
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();

        appUser.voucher_type = "Credit Note";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatter.format(date);
        set_date.setText(dateString);

        Boolean isConnected = ConnectivityReceiver.isConnected();

        title="CREATE CREDIT NOTE";
        fromCreditNote = getIntent().getBooleanExtra("fromCreditNote",false);
        if (fromCreditNote == true) {
            title="EDIT CREDIT NOTE";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_credit_note_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateCreditNoteWoItemActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_CREDIT_NOTE_DETAILS);
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
        }else {
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateCreditNoteWoItemActivity.this);
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

        account_name_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParameterConstant.forAccountIntentBool=false;
                ParameterConstant.forAccountIntentName="";
                ParameterConstant.forAccountIntentId="";
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors";
                //ParameterConstant.checkStartActivityResultForAccount =12;
                ExpandableAccountListActivity.isDirectForAccount=false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
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

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!voucher_no.getText().toString().equals("")){
                    if (!set_date.getText().toString().equals("")) {
                    if (!account_name_credit.getText().toString().equals("")) {
                        if (!transaction_amount.getText().toString().equals("")) {
                            appUser.credit_note_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                            appUser.credit_note_date = set_date.getText().toString();
                            appUser.credit_note_voucher_no = voucher_no.getText().toString();
                            appUser.credit_note_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                            appUser.account_name_debit_note = account_name_debit.getText().toString();

                            if (!transaction_amount.getText().toString().equals("")) {
                                appUser.credit_note_amount = Double.parseDouble(transaction_amount.getText().toString());
                            }
                            appUser.credit_note_narration = transaction_narration.getText().toString();
                            appUser.credit_note_attachment = encodedString;
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            new AlertDialog.Builder(CreateCreditNoteWoItemActivity.this)
                                    .setTitle("Email")
                                    .setMessage(R.string.btn_send_email)
                                    .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                        appUser.email_yes_no = "true";
                                        LocalRepositories.saveAppUser(CreateCreditNoteWoItemActivity.this, appUser);
                                        if (isConnected) {
                                            mProgressDialog = new ProgressDialog(CreateCreditNoteWoItemActivity.this);
                                            mProgressDialog.setMessage("Info...");
                                            mProgressDialog.setIndeterminate(false);
                                            mProgressDialog.setCancelable(true);
                                            mProgressDialog.show();
                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_CREDIT_NOTE);
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
                                        LocalRepositories.saveAppUser(CreateCreditNoteWoItemActivity.this, appUser);
                                        if (isConnected) {
                                            mProgressDialog = new ProgressDialog(CreateCreditNoteWoItemActivity.this);
                                            mProgressDialog.setMessage("Info...");
                                            mProgressDialog.setIndeterminate(false);
                                            mProgressDialog.setCancelable(true);
                                            mProgressDialog.show();
                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_CREDIT_NOTE);
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
                        Snackbar.make(coordinatorLayout, "Please select account name credit", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                }
            }else {
                Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateCreditNoteWoItemActivity.this);
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
                        if (!account_name_credit.getText().toString().equals("")) {
                            if (!transaction_amount.getText().toString().equals("")) {
                                appUser.credit_note_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                                appUser.credit_note_date = set_date.getText().toString();
                                appUser.credit_note_voucher_no = voucher_no.getText().toString();
                                appUser.credit_note_gst_nature = gst_nature_spinner.getSelectedItem().toString();
                                appUser.account_name_debit_note = account_name_debit.getText().toString();

                                if (!transaction_amount.getText().toString().equals("")) {
                                    appUser.credit_note_amount = Double.parseDouble(transaction_amount.getText().toString());
                                }
                                appUser.credit_note_narration = transaction_narration.getText().toString();
                                appUser.credit_note_attachment = encodedString;
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                Boolean isConnected = ConnectivityReceiver.isConnected();
                                if (isConnected) {
                                    mProgressDialog = new ProgressDialog(CreateCreditNoteWoItemActivity.this);
                                    mProgressDialog.setMessage("Info...");
                                    mProgressDialog.setIndeterminate(false);
                                    mProgressDialog.setCancelable(true);
                                    mProgressDialog.show();
                                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_CREDIT_NOTE);
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
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateCreditNoteWoItemActivity.this);
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

        if (ParameterConstant.forAccountIntentBool) {
                String result = ParameterConstant.forAccountIntentName;
                appUser.account_name_credit_note_id = ParameterConstant.forAccountIntentId;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                account_name_credit.setText(name[0]);
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
               if (ParameterConstant.handleAutoCompleteTextView==1){
                   boolForGroupName=true;
                   appUser.account_name_credit_note_id = ParameterConstant.id;
                   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                   account_name_credit.setText(ParameterConstant.name);
               }else {
                   boolForGroupName=true;
                   String result = data.getStringExtra("name");
                   String id = data.getStringExtra("id");
                   appUser.account_name_credit_note_id = id;
                   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                   String[] name = result.split(",");
                   account_name_credit.setText(name[0]);
               }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /*if (bool) {
            if (!boolForGroupName) {
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.account_name_credit_note_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                account_name_credit.setText(name[0]);
            }
        }*/
    }

    private void startDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CreateCreditNoteWoItemActivity.this);
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

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_credit_note_wo_item;
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
    public void createcreditnoteresponse(CreateCreditNoteResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
           // voucher_no.setText("");
            transaction_amount.setText("");
            transaction_narration.setText("");
            account_name_credit.setText("");
            // account_name_debit.setText("");
            gst_nature_spinner.setSelection(0);
            encodedString="";
            mSelectedImage.setImageDrawable(null);
            mSelectedImage.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();

            new AlertDialog.Builder(CreateCreditNoteWoItemActivity.this)
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(CreateCreditNoteWoItemActivity.this, TransactionPdfActivity.class);
                        intent.putExtra("company_report",response.getHtml());
                        startActivity(intent);

                       /* ProgressDialog progressDialog = new ProgressDialog(CreateCreditNoteWoItemActivity.this);
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
    public void getCreditNoteDetails(GetCreditNoteDetailsResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            set_date.setText(response.getCredit_note().getData().getAttributes().getDate());
            voucher_no.setText(response.getCredit_note().getData().getAttributes().getVoucher_number());
            //account_name_debit.setText(response.getCredit_note().getData().getAttributes().getAccount_name_debit());
            account_name_credit.setText(response.getCredit_note().getData().getAttributes().getAccount_name_credit());
            transaction_amount.setText(String.valueOf(response.getCredit_note().getData().getAttributes().getAmount()));
            transaction_narration.setText(response.getCredit_note().getData().getAttributes().getNarration());
            if(!response.getCredit_note().getData().getAttributes().getAttachment().equals("")){
                Glide.with(this).load(Uri.parse(response.getCredit_note().getData().getAttributes().getAttachment()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(mSelectedImage);
                mSelectedImage.setVisibility(View.VISIBLE);
            }
            else{
                mSelectedImage.setVisibility(View.GONE);
            }
            String group_type = response.getCredit_note().getData().getAttributes().getGst_nature().trim();
            int groupindex = -1;
            for (int i = 0; i<getResources().getStringArray(R.array.gst_nature_credit).length; i++) {
                if (getResources().getStringArray(R.array.gst_nature_credit)[i].equals(group_type)) {
                    groupindex = i;
                    break;
                }
            }
            gst_nature_spinner.setSelection(groupindex);
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editCreditNote(EditCreditNoteResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            //appUser.forAccountIntentBool=false;
           // appUser.forAccountIntentId="";
            //appUser.forAccountIntentName="";
            Intent intent = new Intent(this, CreditNoteWoItemActivity.class);
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
    }

     @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater menuInflater = getMenuInflater();
       menuInflater.inflate(R.menu.activity_list_button_action,menu);
         if(fromCreditNote==true){
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
               Intent i = new Intent(getApplicationContext(),CreditNoteWoItemActivity.class);
               startActivity(i);
       }
       return super.onOptionsItemSelected(item);
   }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_id:
                Intent i = new Intent(getApplicationContext(),CreditNoteWoItemActivity.class);
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