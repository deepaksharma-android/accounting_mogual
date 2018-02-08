package com.lkintechnology.mBilling.activities.company.administration.master.account;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.administration.master.accountgroup.AccountGroupListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.account.CreateAccountResponse;
import com.lkintechnology.mBilling.networks.api_response.account.EditAccountResponse;
import com.lkintechnology.mBilling.networks.api_response.account.GetAccountDetailsResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AccountDetailsActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.account_name)
    EditText mAccountName;
    @Bind(R.id.mobile_number)
    EditText mMobileNumber;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.contact_list)
    LinearLayout mContactList;
    @Bind(R.id.group_name)
    TextView mGroupName;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    @Bind(R.id.update)
    LinearLayout mUpdateButton;
    @Bind(R.id.group_layout)
    LinearLayout mGroupLayout;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    Boolean fromaccountlist;
    String title;
    public static AccountDetailsActivity context;
    private static final String TAG = AccountDetailsActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 9;
    private Uri uriContact;
    private String contactID;

    public Boolean boolForGroupName=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context=this;
        title="CREATE ACCOUNT";
        fromaccountlist=getIntent().getExtras().getBoolean("fromaccountlist");
        appUser = LocalRepositories.getAppUser(this);
        /*appUser.account_amount_receivable = "";
        appUser.account_amount_payable = "";
        appUser.account_address = "";
        appUser.account_city = "";
        appUser.account_state = "";
        appUser.account_gst = "";
        appUser.account_aadhaar = "";
        appUser.account_pan = "";
        appUser.account_credit_limit = "";
        appUser.account_credit_sale = "";
        appUser.account_credit_purchase = "";
        LocalRepositories.saveAppUser(this, appUser);*/

        mAccountName.setText(appUser.account_name);
        mMobileNumber.setText(appUser.account_mobile_number);
        mEmail.setText(appUser.account_email);

        if(fromaccountlist){
            title="EDIT ACCOUNT";
            mSubmitButton.setVisibility(View.GONE);
            mUpdateButton.setVisibility(View.VISIBLE);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(AccountDetailsActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ACCOUNT_DETAILS);
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
        mGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                AccountGroupListActivity.isDirectForAccountGroup=false;
                ParameterConstant.checkStartActivityResultForAccountGroup =0;
                appUser.account_name = mAccountName.getText().toString();
                appUser.account_mobile_number = mMobileNumber.getText().toString();
                appUser.account_email=mEmail.getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent intent = new Intent(getApplicationContext(), AccountGroupListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 1);
            }
        });

        mContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                if (!mAccountName.getText().toString().equals("")) {
                    /*if (!mMobileNumber.getText().toString().equals("")) {*/
                        if (!mGroupName.getText().toString().equals("")) {
                            appUser.account_name = mAccountName.getText().toString();
                            appUser.account_mobile_number = mMobileNumber.getText().toString();
                            appUser.account_email=mEmail.getText().toString();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                                    if (isConnected) {
                                                        mProgressDialog = new ProgressDialog(AccountDetailsActivity.this);
                                                        mProgressDialog.setMessage("Info...");
                                                        mProgressDialog.setIndeterminate(false);
                                                        mProgressDialog.setCancelable(true);
                                                        mProgressDialog.show();
                                                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_ACCOUNT);
                                                    } else {
                                                        snackbar = Snackbar
                                                                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                                                .setAction("RETRY", new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {

                                            }
                                        });
                                snackbar.show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Enter group name", Snackbar.LENGTH_LONG).show();
                        }
                    /*} else {
                        Snackbar.make(coordinatorLayout, "Enter mobile number", Snackbar.LENGTH_LONG).show();
                    }*/
                } else {
                    Snackbar.make(coordinatorLayout, "Enter account name", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                if (!mAccountName.getText().toString().equals("")) {
                        if (!mGroupName.getText().toString().equals("")) {
                            appUser.account_name = mAccountName.getText().toString();
                            appUser.account_mobile_number = mMobileNumber.getText().toString();
                            appUser.account_email=mEmail.getText().toString();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                mProgressDialog = new ProgressDialog(AccountDetailsActivity.this);
                                mProgressDialog.setMessage("Info...");
                                mProgressDialog.setIndeterminate(false);
                                mProgressDialog.setCancelable(true);
                                mProgressDialog.show();
                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_ACCOUNT);
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
                            Snackbar.make(coordinatorLayout, "Enter group name", Snackbar.LENGTH_LONG).show();
                        }
                } else {
                    Snackbar.make(coordinatorLayout, "Enter account name", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactName();
            retrieveContactNumber();

        }
    }*/

    @Override
    protected int layoutId() {
        return R.layout.activity_account_details;
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText(title);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void openingbalance(View v) {
        Dialog dialogbal = new Dialog(AccountDetailsActivity.this);
        dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogbal.setContentView(R.layout.dialog_opening_balance);
        dialogbal.setCancelable(true);
        EditText amount_receivable = (EditText) dialogbal.findViewById(R.id.receive);
        EditText amount_payable = (EditText) dialogbal.findViewById(R.id.payable);
        LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialogbal.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialogbal.dismiss();

            }
        });
        if (!appUser.account_amount_receivable.equals("")) {
            amount_receivable.setText(appUser.account_amount_receivable);
        }
        if (!appUser.account_amount_payable.equals("")) {
            amount_payable.setText(appUser.account_amount_payable);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.account_amount_receivable = amount_receivable.getText().toString();
                appUser.account_amount_payable = amount_payable.getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialogbal.dismiss();
            }
        });
        // set the custom dialog components - text, image and button
        dialogbal.show();
    }

    public void address(View v) {
        Dialog dialogaddress = new Dialog(AccountDetailsActivity.this);
        dialogaddress.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogaddress.setContentView(R.layout.dialog_address);
        dialogaddress.setCancelable(true);
        // set the custom dialog components - text, image and button
        EditText account_address = (EditText) dialogaddress.findViewById(R.id.address);
        EditText account_city = (EditText) dialogaddress.findViewById(R.id.city);
        EditText account_pinCode = (EditText) dialogaddress.findViewById(R.id.pincode);
        Spinner spinner_state = (Spinner) dialogaddress.findViewById(R.id.state_spinner);
        LinearLayout submit = (LinearLayout) dialogaddress.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialogaddress.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialogaddress.dismiss();
            }
        });
        if (!appUser.account_address.equals("")) {
            account_address.setText(appUser.account_address);
        }
        if (!appUser.account_pinCode.equals("")) {
            account_pinCode.setText(appUser.account_pinCode);
        }
        if (!appUser.account_city.equals("")) {
            account_city.setText(appUser.account_city);
        }
        if (!appUser.account_state.equals("")) {
            String state = appUser.account_state.trim();// insert code here
            int stateindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.state).length; i++) {
                if (getResources().getStringArray(R.array.state)[i].equals(state)) {
                    stateindex = i;
                    break;
                }
            }
            spinner_state.setSelection(stateindex);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                String pin = account_pinCode.getText().toString();
                int counter = pin.length();
                if(counter>=6 || account_pinCode.getText().toString().equals("")){
                    appUser.account_pinCode = account_pinCode.getText().toString();
                    appUser.account_address = account_address.getText().toString();
                    appUser.account_city = account_city.getText().toString();
                    appUser.account_state = spinner_state.getSelectedItem().toString();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    dialogaddress.dismiss();
                }else {
                    Toast.makeText(AccountDetailsActivity.this, "Enter minimum 6 digit pin code", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogaddress.show();
    }

    public void gstin(View v) {
        Dialog dialoggst = new Dialog(AccountDetailsActivity.this);
        dialoggst.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialoggst.setContentView(R.layout.dialog_gstin);
        dialoggst.setCancelable(true);
        // set the custom dialog components - text, image and button
        Spinner account_type_of_dealer = (Spinner) dialoggst.findViewById(R.id.type_of_dealer_spinner);
        EditText account_gst = (EditText) dialoggst.findViewById(R.id.gst);
        EditText account_aadhaar = (EditText) dialoggst.findViewById(R.id.aadhaar);
        EditText account_pan = (EditText) dialoggst.findViewById(R.id.pan);
        LinearLayout submit = (LinearLayout) dialoggst.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialoggst.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialoggst.dismiss();
            }
        });
        if (!appUser.account_type_of_dealer.equals("")) {
            String state = appUser.account_type_of_dealer.trim();// insert code here
            int stateindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.type_of_dealer).length; i++) {
                if (getResources().getStringArray(R.array.type_of_dealer)[i].equals(state)) {
                    stateindex = i;
                    break;
                }
            }
            account_type_of_dealer.setSelection(stateindex);
        }

        if (!appUser.account_gst.equals("")) {
            account_gst.setText(appUser.account_gst);
        }
        if (!appUser.account_aadhaar.equals("")) {
            account_aadhaar.setText(appUser.account_aadhaar);
        }
        if (!appUser.account_pan.equals("")) {
            account_pan.setText(appUser.account_pan);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.account_type_of_dealer = account_type_of_dealer.getSelectedItem().toString();
                appUser.account_gst = account_gst.getText().toString();
                appUser.account_aadhaar = account_aadhaar.getText().toString();
                appUser.account_pan = account_pan.getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialoggst.dismiss();
            }
        });
        dialoggst.show();

    }

    public void creaditinfo(View v) {
        Dialog dialogcredit = new Dialog(AccountDetailsActivity.this);
        dialogcredit.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogcredit.setContentView(R.layout.dialog_credit);
        dialogcredit.setCancelable(true);
        EditText account_credit_limit = (EditText) dialogcredit.findViewById(R.id.credit_limit);
        EditText account_credit_sale = (EditText) dialogcredit.findViewById(R.id.credit_sale);
        EditText account_credit_purchase = (EditText) dialogcredit.findViewById(R.id.credit_purchase);
        LinearLayout submit = (LinearLayout) dialogcredit.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialogcredit.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialogcredit.dismiss();
            }
        });
        if (!appUser.account_credit_limit.equals("")) {
            account_credit_limit.setText(appUser.account_credit_limit);
        }
        if (!appUser.account_credit_sale.equals("")) {
            account_credit_sale.setText(appUser.account_credit_sale);
        }
        if (!appUser.account_credit_purchase.equals("")) {
            account_credit_purchase.setText(appUser.account_credit_purchase);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.account_credit_limit = account_credit_limit.getText().toString();
                appUser.account_credit_sale = account_credit_sale.getText().toString();
                appUser.account_credit_purchase = account_credit_purchase.getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialogcredit.dismiss();
            }
        });
        // set the custom dialog components - text, image and button
        dialogcredit.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CONTACTS) {
            if( resultCode == RESULT_OK) {
                Log.d(TAG, "Response: " + data.toString());
                uriContact = data.getData();

                retrieveContactName();
                retrieveContactNumber();
            }

        }
       else if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                boolForGroupName=true;

                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.create_account_group_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mGroupName.setText(result);
                return;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mGroupName.setText("");
            }
        }
    }
    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            mMobileNumber.setText(contactNumber);
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }

    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            mAccountName.setText(contactName);
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);

        if (bool) {
            if (!boolForGroupName) {
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.create_account_group_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mGroupName.setText(result);
            }

        }

    }


    @Subscribe
    public void createaccount(CreateAccountResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            //ExpandableAccountListActivity.isDirectForAccount=true;
            Intent intent=new Intent(getApplicationContext(),ExpandableAccountListActivity.class);
            intent.putExtra("bool",true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getaccountaccount(GetAccountDetailsResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.edit_account_id=response.getAccount().getData().getAttributes().getId();
            LocalRepositories.saveAppUser(this,appUser);
            mAccountName.setText(response.getAccount().getData().getAttributes().getName());
            mGroupName.setText(response.getAccount().getData().getAttributes().getAccount_group());
            mMobileNumber.setText(response.getAccount().getData().getAttributes().getMobile_number());
            mEmail.setText(response.getAccount().getData().getAttributes().getEmail());
            appUser.create_account_group_id=String.valueOf(response.getAccount().getData().getAttributes().getAccount_master_group_id());
            appUser.account_amount_receivable= Helpers.mystring(response.getAccount().getData().getAttributes().getAmount_receivable());
            appUser.account_amount_payable= Helpers.mystring(response.getAccount().getData().getAttributes().getAmount_payable());
            appUser.account_address= Helpers.mystring(response.getAccount().getData().getAttributes().getAddress());
            appUser.account_pinCode= Helpers.mystring(response.getAccount().getData().getAttributes().getPincode());
            appUser.account_city= Helpers.mystring(response.getAccount().getData().getAttributes().getCity());
            appUser.account_state= Helpers.mystring(response.getAccount().getData().getAttributes().getState());
            appUser.account_type_of_dealer= Helpers.mystring(response.getAccount().getData().getAttributes().getType_of_dealer());
            appUser.account_gst= Helpers.mystring(response.getAccount().getData().getAttributes().getGstin_number());
            appUser.account_aadhaar= Helpers.mystring(response.getAccount().getData().getAttributes().getAdhar_number());
            appUser.account_pan= Helpers.mystring(response.getAccount().getData().getAttributes().getPan_number());
            appUser.account_credit_limit= Helpers.mystring(response.getAccount().getData().getAttributes().getCredit_limit());
            appUser.account_credit_sale= Helpers.mystring(response.getAccount().getData().getAttributes().getCredit_days_for_sale());
            appUser.account_credit_purchase= Helpers.mystring(response.getAccount().getData().getAttributes().getCredit_days_for_purchase());

            LocalRepositories.saveAppUser(this,appUser);
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editaccount(EditAccountResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
           // ExpandableAccountListActivity.isDirectForAccount=true;
            Intent intent=new Intent(getApplicationContext(),ExpandableAccountListActivity.class);
            intent.putExtra("bool",true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    public void hideSoftKeyboard(View v) {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();
    }
    
}