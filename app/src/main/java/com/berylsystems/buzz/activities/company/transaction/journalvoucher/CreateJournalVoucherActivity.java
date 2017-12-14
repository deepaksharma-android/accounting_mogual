package com.berylsystems.buzz.activities.company.transaction.journalvoucher;

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
import com.berylsystems.buzz.networks.api_response.journalvoucher.CreateJournalVoucherResponse;
import com.berylsystems.buzz.networks.api_response.journalvoucher.EditJournalVoucherResponse;
import com.berylsystems.buzz.networks.api_response.journalvoucher.GetJournalVoucherDetailsResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;
import com.bumptech.glide.Glide;

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
public class CreateJournalVoucherActivity extends RegisterAbstractActivity implements View.OnClickListener {

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
    EditText voucher_no;
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
    Boolean fromJournalVoucher;
    String encodedString;
    AppUser appUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.list_button);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        set_date.setText(dateString);

        fromJournalVoucher = getIntent().getExtras().getBoolean("fromJournalVoucher");
        if (fromJournalVoucher == true) {
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_journal_voucher_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            Boolean isConnected = ConnectivityReceiver.isConnected();
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
        }

        mBrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

        account_name_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appUser.account_master_group = "Sundry Debtors,Sundry Creditors";
                appUser.account_master_group = "";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });


        account_name_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Cash-in-hand,Bank Accounts";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 3);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!transaction_amount.getText().toString().equals("") &&
                        !account_name_credit.getText().toString().equals("") && !account_name_debit.getText().toString().equals("")) {

                    appUser.journal_voucher_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                    appUser.journal_voucher_date = set_date.getText().toString();
                    appUser.journal_voucher_voucher_no = voucher_no.getText().toString();
                    appUser.journal_voucher_gst_nature = gst_nature_spinner.getSelectedItem().toString();

                    if (!transaction_amount.getText().toString().equals("")) {
                        appUser.journal_voucher_amount = Double.parseDouble(transaction_amount.getText().toString());
                    }
                    appUser.journal_voucher_narration = transaction_narration.getText().toString();
                    appUser.journal_voucher_attachment = encodedString;

                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_JOURNAL_VOUCHER);
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
                    voucher_no.setText("");
                    transaction_amount.setText("");
                    transaction_narration.setText("");
                    account_name_credit.setText("");
                    account_name_debit.setText("");
                    gst_nature_spinner.setSelection(0);
                    mSelectedImage.setImageResource(0);
                    mSelectedImage.setVisibility(View.GONE);
                    //mSelectedImage.setImageDrawable(null);
                }
            }

        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!transaction_amount.getText().toString().equals("") &&
                        !account_name_credit.getText().toString().equals("") && !account_name_debit.getText().toString().equals("")) {

                    appUser.journal_voucher_voucher_series = voucher_series_spinner.getSelectedItem().toString();
                    appUser.journal_voucher_date = set_date.getText().toString();
                    appUser.journal_voucher_voucher_no = voucher_no.getText().toString();
                    appUser.journal_voucher_gst_nature = gst_nature_spinner.getSelectedItem().toString();

                    if (!transaction_amount.getText().toString().equals("")) {
                        appUser.journal_voucher_amount = Double.parseDouble(transaction_amount.getText().toString());
                    }
                    appUser.payment_narration = transaction_narration.getText().toString();
                    appUser.journal_voucher_attachment = encodedString;

                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateJournalVoucherActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
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
                }
            }

        });


    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_list_button_action,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.icon_id:
                Intent i = new Intent(getApplicationContext(),JournalVoucherActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
*/
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
                appUser.account_name_debit_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                account_name_debit.setText(name[0]);
            }
            if (requestCode == 3) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.account_name_credit_id =id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                account_name_credit.setText(name[0]);
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
        return R.layout.activity_create_journal_voucher;
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
        actionbarTitle.setText("JOURNAL VOUCHER");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createjournalvoucherresponse(CreateJournalVoucherResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    @Subscribe
    public void getJournalVoucherDetails(GetJournalVoucherDetailsResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            set_date.setText(response.getJournal_voucher().getData().getAttributes().getDate());
            voucher_no.setText(response.getJournal_voucher().getData().getAttributes().getVoucher_number());
            account_name_debit.setText(response.getJournal_voucher().getData().getAttributes().getAccount_name_debit());
            account_name_credit.setText(response.getJournal_voucher().getData().getAttributes().getAccount_name_credit());
            transaction_amount.setText(String.valueOf(response.getJournal_voucher().getData().getAttributes().getAmount()));
            transaction_narration.setText(response.getJournal_voucher().getData().getAttributes().getNarration());
            Glide.with(this).load(response.getJournal_voucher().getData().getAttributes().getAttachment()).into(mSelectedImage);
            mSelectedImage.setVisibility(View.VISIBLE);

            String group_type = response.getJournal_voucher().getData().getAttributes().getGst_nature().trim();
            int groupindex = -1;
            for (int i = 0; i<getResources().getStringArray(R.array.gst_nature_journal_voucher).length; i++) {
                if (getResources().getStringArray(R.array.gst_nature_journal_voucher)[i].equals(group_type)) {
                    groupindex = i;
                    break;
                }

            }
            gst_nature_spinner.setSelection(groupindex);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editExpence(EditJournalVoucherResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Intent intent = new Intent(this, JournalVoucherActivity.class);
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}