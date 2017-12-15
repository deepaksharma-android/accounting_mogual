package com.berylsystems.buzz.activities.company.transaction.bankcasedeposit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.CreateBankCashDepositResponse;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.EditBankCashDepositResponse;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.GetBankCashDepositDetailsResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.Subscribe;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    EditText voucher_no;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_bank_case_deposit);
        ButterKnife.bind(this);

        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();

        android.support.v7.app.ActionBar actionBar =getSupportActionBar();
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatter.format(date);
        set_date.setText(dateString);

        title="CREATE BANK CASH DEPOSIT";
        fromBankcashDeposit=getIntent().getExtras().getBoolean("fromBankCashDeposit");
        if(fromBankcashDeposit==true){
            title="EDIT BANK CASH DEPOSIT";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_bank_cash_deposit_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this,appUser);
            Boolean isConnected = ConnectivityReceiver.isConnected();
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
        }
        initActionbar();
        mBrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

        deposit_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Cash-in-hand";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount=false;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });

        deposit_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Bank Accounts";
                ExpandableAccountListActivity.isDirectForAccount=false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 3);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!voucher_no.getText().toString().equals("")){
                    if(!set_date.getText().toString().equals("")) {
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
                    }else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
                }
            }

        });
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!voucher_no.getText().toString().equals("")){
                    if(!set_date.getText().toString().equals("")) {
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
                    }else {
                        Snackbar.make(coordinatorLayout, "Please select date", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(coordinatorLayout, "Please enter voucher number", Snackbar.LENGTH_LONG).show();
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
        if (resultCode == RESULT_OK) {
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
            if (requestCode == 2) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.deposit_to_id = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                deposit_to.setText(name[0]);
            }
            if (requestCode == 3) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.deposit_by_id = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
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
    public void createbankcashdepositresponse(CreateBankCashDepositResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            //Toast.makeText(CreateBankCaseDepositActivity.this, ""+ response.getMessage(), Toast.LENGTH_SHORT).show();
            voucher_no.setText("");
            transaction_amount.setText("");
            transaction_narration.setText("");
            deposit_by.setText("");
            deposit_to.setText("");
            mSelectedImage.setImageResource(0);
            mSelectedImage.setVisibility(View.GONE);
            //mSelectedImage.setImageDrawable(null);
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            //Toast.makeText(CreateBankCaseDepositActivity.this, ""+ response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void getBankCashDepositDetails(GetBankCashDepositDetailsResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            set_date.setText(response.getBank_cash_deposit().getData().getAttributes().getDate());
            voucher_no.setText(response.getBank_cash_deposit().getData().getAttributes().getVoucher_number());
            deposit_to.setText(response.getBank_cash_deposit().getData().getAttributes().getDeposit_to());
            deposit_by.setText(response.getBank_cash_deposit().getData().getAttributes().getDeposit_by());
            transaction_amount.setText(String.valueOf(response.getBank_cash_deposit().getData().getAttributes().getAmount()));
            transaction_narration.setText(response.getBank_cash_deposit().getData().getAttributes().getNarration());
            if(!response.getBank_cash_deposit().getData().getAttributes().getAttachment().equals("")){
               // Glide.with(this).load(response.getBank_cash_deposit().getData().getAttributes().getAttachment()).into(mSelectedImage);
                Glide.with(this).load(Uri.parse(response.getBank_cash_deposit().getData().getAttributes().getAttachment()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(mSelectedImage);
                mSelectedImage.setVisibility(View.VISIBLE);
            }
            else{
                mSelectedImage.setVisibility(View.GONE);
            }
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editBankCashDeposit(EditBankCashDepositResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Intent intent = new Intent(this, BankCaseDepositListActivity.class);
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
        //mProgressDialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


   /* public Bitmap toBitmap(String encodedString) {
        byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }*/
}