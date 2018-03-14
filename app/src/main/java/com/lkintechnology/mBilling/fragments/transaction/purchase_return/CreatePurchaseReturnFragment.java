package com.lkintechnology.mBilling.fragments.transaction.purchase_return;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentre.MaterialCentreListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.saletype.SaleTypeListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.CreatePurchaseReturnActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.GetPurchaseReturnListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ReceiptActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransportActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.CreatePurchaseReturnResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.GetPurchaseReturnVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.UpdatePurchaseReturnVoucher;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by BerylSystems on 11/22/2017.
 */

public class CreatePurchaseReturnFragment extends Fragment {

    @Bind(R.id.date)
    TextView mDate;
    @Bind(R.id.series)
    Spinner mSeries;
    @Bind(R.id.vch_number)
    TextView mVchNumber;
    @Bind(R.id.purchase_type)
    TextView mPurchaseType;
    @Bind(R.id.store)
    TextView mStore;
    @Bind(R.id.party_name)
    TextView mPartyName;
    @Bind(R.id.mobile_number)
    EditText mMobileNumber;
    @Bind(R.id.cash)
    Button cash;
    @Bind(R.id.credit)
    Button credit;
    @Bind(R.id.submit)
    LinearLayout submit;
    @Bind(R.id.update)
    LinearLayout update;
    @Bind(R.id.narration)
    EditText mNarration;
    @Bind(R.id.browse_image)
    LinearLayout mBrowseImage;
    @Bind(R.id.transport)
    LinearLayout mTransport;
    @Bind(R.id.receipt)
    LinearLayout mReceipt;
    @Bind(R.id.selected_image)
    ImageView mSelectedImage;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    private SimpleDateFormat dateFormatter;
    Animation blinkOnClick;
    Snackbar snackbar;
    String encodedString;
    Bitmap photo;
    public static int intStartActivityForResult=0;
    public Boolean boolForPartyName=false;
    public Boolean boolForStore=false;
    String party_id;
    WebView mPdf_webview;
    private Uri imageToUploadUri;;

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_return_create, container, false);
        ButterKnife.bind(this, view);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        appUser = LocalRepositories.getAppUser(getActivity());
        appUser.voucher_type = "Purchase Return";
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        if (CreatePurchaseReturnActivity.fromsalelist) {
            if(!Preferences.getInstance(getContext()).getVoucher_date().equals("")){
                mDate.setText(Preferences.getInstance(getContext()).getVoucher_date());
            }
            submit.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getActivity(), Cv.ACTION_GET_PURCHASE_RETURN_VOUCHER_DETAILS);
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
        if(CreatePurchaseReturnActivity.fromdashboard) {
            String date1 = dateFormatter.format(newCalendar.getTime());
            Preferences.getInstance(getContext()).setVoucher_date(date1);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
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
        if(!Preferences.getInstance(getActivity()).getUpdate().equals("")){
            update.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }
        else{
            submit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
        }
        mPurchaseType.setText(Preferences.getInstance(getContext()).getSale_type_name());
        mDate.setText(Preferences.getInstance(getContext()).getVoucher_date());
        mStore.setText(Preferences.getInstance(getContext()).getStore());
        mPartyName.setText(Preferences.getInstance(getContext()).getParty_name());
        mVchNumber.setText(Preferences.getInstance(getContext()).getVoucher_number());
        mMobileNumber.setText(Preferences.getInstance(getContext()).getMobile());
        mNarration.setText(Preferences.getInstance(getContext()).getNarration());
        if(Preferences.getInstance(getContext()).getCash_credit().equals("CASH")){
            cash.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setTextColor(Color.parseColor("#ffffff"));
            credit.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#000000"));
        }
        else if(Preferences.getInstance(getContext()).getCash_credit().equals("Credit")){
            credit.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#ffffff"));//white
            cash.setTextColor(Color.parseColor("#000000"));//black
        }
        else{
            cash.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setTextColor(Color.parseColor("#ffffff"));
            credit.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#000000"));
        }
        if (!mDate.getText().toString().equals("")){
            appUser.purchase_date=mDate.getText().toString();
            LocalRepositories.saveAppUser(getApplicationContext(),appUser);
        }
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormatter.format(newDate.getTime());
                        mDate.setText(date);
                        Preferences.getInstance(getContext()).setVoucher_date(date);
                        appUser.purchase_date = date;
                        LocalRepositories.saveAppUser(getActivity(), appUser);
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        mStore .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intStartActivityForResult=1;
                ParameterConstant.checkStartActivityResultForAccount =9;
                ParameterConstant.checkStartActivityResultForMaterialCenter=4;
                MaterialCentreListActivity.isDirectForMaterialCentre=false;
                startActivityForResult(new Intent(getContext(), MaterialCentreListActivity.class), 11);
            }
        });
        mPurchaseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleTypeListActivity.isDirectForSaleType=false;
                startActivityForResult(new Intent(getContext(), SaleTypeListActivity.class), 22);
            }
        });
        mPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParameterConstant.forAccountIntentBool=false;
                ParameterConstant.forAccountIntentName="";
                ParameterConstant.forAccountIntentId="";
                ParameterConstant.forAccountIntentMobile="";
                intStartActivityForResult=2;
                //ParameterConstant.checkStartActivityResultForAccount =9;
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors,Cash-in-hand";
                ExpandableAccountListActivity.isDirectForAccount=false;
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                ParameterConstant.handleAutoCompleteTextView=0;
                startActivityForResult(new Intent(getContext(), ExpandableAccountListActivity.class), 33);
            }
        });

        mBrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i.createChooser(i, "Select Picture"), SELECT_PICTURE);*/
                startDialog();

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

        mTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransportActivity.voucher_type="purchase_return";
                startActivity(new Intent(getActivity(), TransportActivity.class));
            }
        });
        mReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Preferences.getInstance(getActivity()).getSale_type_name().equals("")) {
                    if (!Preferences.getInstance(getActivity()).getStore().equals("")) {
                        startActivity(new Intent(getActivity(), ReceiptActivity.class));
                    }else {
                        alertdialogstore();
                    }
                }else {
                    alertdialogtype();
                }

            }
        });

        Preferences.getInstance(getContext()).setCash_credit(cash.getText().toString());
        appUser.sale_cash_credit=cash.getText().toString();
        LocalRepositories.saveAppUser(getApplicationContext(),appUser);

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preferences.getInstance(getContext()).setCash_credit(cash.getText().toString());
                appUser.sale_cash_credit = cash.getText().toString();
                LocalRepositories.saveAppUser(getActivity(), appUser);
                cash.setBackgroundColor(Color.parseColor("#ababab"));
                credit.setBackgroundColor(0);
                cash.setTextColor(Color.parseColor("#ffffff"));//white
                credit.setTextColor(Color.parseColor("#000000"));//black
            }
        });
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.getInstance(getContext()).setCash_credit(credit.getText().toString());
                appUser.sale_cash_credit = credit.getText().toString();
                LocalRepositories.saveAppUser(getActivity(), appUser);
                credit.setBackgroundColor(Color.parseColor("#ababab"));
                cash.setBackgroundColor(0);
                credit.setTextColor(Color.parseColor("#ffffff"));//white
                cash.setTextColor(Color.parseColor("#000000"));//black
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.startAnimation(blinkOnClick);
                appUser=LocalRepositories.getAppUser(getActivity());
                if(appUser.mListMapForItemPurchaseReturn.size()>0) {
                    if (!mSeries.getSelectedItem().toString().equals("")) {
                        if (!mDate.getText().toString().equals("")) {
                            if (!mVchNumber.getText().toString().equals("")) {
                                if (!mPurchaseType.getText().toString().equals("")) {
                                    if (!mStore.getText().toString().equals("")) {
                                        if (!mPartyName.getText().toString().equals("")) {
                                          /*  if (!mMobileNumber.getText().toString().equals("")) {*/
                                                appUser.purchase_voucher_series = mSeries.getSelectedItem().toString();
                                                appUser.purchase_voucher_number = mVchNumber.getText().toString();
                                                appUser.purchase_mobile_number = mMobileNumber.getText().toString();
                                                appUser.purchase_narration = mNarration.getText().toString();
                                                appUser.purchase_return_attachment = encodedString;
                                                LocalRepositories.saveAppUser(getActivity(), appUser);
                                            Boolean isConnected = ConnectivityReceiver.isConnected();
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle("Email")
                                                    .setMessage(R.string.btn_send_email)
                                                    .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                                        appUser.email_yes_no="true";
                                                        LocalRepositories.saveAppUser(getActivity(),appUser);
                                                        if (isConnected) {
                                                            mProgressDialog = new ProgressDialog(getActivity());
                                                            mProgressDialog.setMessage("Info...");
                                                            mProgressDialog.setIndeterminate(false);
                                                            mProgressDialog.setCancelable(true);
                                                            mProgressDialog.show();
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_PURCHASE_RETURN);
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
                                                        }else {
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

                                                        appUser.email_yes_no="false";
                                                        LocalRepositories.saveAppUser(getActivity(),appUser);
                                                        if (isConnected) {
                                                            mProgressDialog = new ProgressDialog(getActivity());
                                                            mProgressDialog.setMessage("Info...");
                                                            mProgressDialog.setIndeterminate(false);
                                                            mProgressDialog.setCancelable(true);
                                                            mProgressDialog.show();
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_PURCHASE_RETURN);
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
                                                        }
                                                        else {
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
                                            Snackbar.make(coordinatorLayout, "Please select party name", Snackbar.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Snackbar.make(coordinatorLayout, "Please select store ", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(coordinatorLayout, "Please select purchase return type", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please enter vch number", Snackbar.LENGTH_LONG).show();
                                Boolean isConnected = ConnectivityReceiver.isConnected();
                                if (isConnected) {
                                    mProgressDialog = new ProgressDialog(getActivity());
                                    mProgressDialog.setMessage("Info...");
                                    mProgressDialog.setIndeterminate(false);
                                    mProgressDialog.setCancelable(true);
                                    mProgressDialog.show();
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
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select the date", Snackbar.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Snackbar.make(coordinatorLayout, "Please select the series", Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Snackbar.make(coordinatorLayout, "Please add item", Snackbar.LENGTH_LONG).show();
                }


            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser=LocalRepositories.getAppUser(getActivity());
                if(appUser.mListMapForItemPurchaseReturn.size()>0) {
                    if (!mSeries.getSelectedItem().toString().equals("")) {
                        if (!mDate.getText().toString().equals("")) {
                            if (!mVchNumber.getText().toString().equals("")) {
                                if (!mPurchaseType.getText().toString().equals("")) {
                                    if (!mStore.getText().toString().equals("")) {
                                        if (!mPartyName.getText().toString().equals("")) {
                                          /*  if (!mMobileNumber.getText().toString().equals("")) {*/
                                            appUser.purchase_voucher_series = mSeries.getSelectedItem().toString();
                                            appUser.purchase_voucher_number = mVchNumber.getText().toString();
                                            appUser.purchase_mobile_number = mMobileNumber.getText().toString();
                                            appUser.purchase_narration = mNarration.getText().toString();
                                            appUser.purchase_return_attachment = encodedString;
                                            LocalRepositories.saveAppUser(getActivity(), appUser);
                                            Boolean isConnected = ConnectivityReceiver.isConnected();
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle("Email")
                                                    .setMessage(R.string.btn_send_email)
                                                    .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                                        appUser.email_yes_no="true";
                                                        LocalRepositories.saveAppUser(getActivity(),appUser);
                                                        if (isConnected) {
                                                            mProgressDialog = new ProgressDialog(getActivity());
                                                            mProgressDialog.setMessage("Info...");
                                                            mProgressDialog.setIndeterminate(false);
                                                            mProgressDialog.setCancelable(true);
                                                            mProgressDialog.show();
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_UPDATE_PURCHASE_RETURN_VOUCHER_DETAILS);
                                                          //  ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
                                                        }else {
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

                                                        appUser.email_yes_no="false";
                                                        LocalRepositories.saveAppUser(getActivity(),appUser);
                                                        if (isConnected) {
                                                            mProgressDialog = new ProgressDialog(getActivity());
                                                            mProgressDialog.setMessage("Info...");
                                                            mProgressDialog.setIndeterminate(false);
                                                            mProgressDialog.setCancelable(true);
                                                            mProgressDialog.show();
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_UPDATE_PURCHASE_RETURN_VOUCHER_DETAILS);
                                                         //   ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
                                                        }
                                                        else {
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
                                            Snackbar.make(coordinatorLayout, "Please select party name", Snackbar.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Snackbar.make(coordinatorLayout, "Please select store ", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(coordinatorLayout, "Please select purchase return type", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please enter vch number", Snackbar.LENGTH_LONG).show();
                                Boolean isConnected = ConnectivityReceiver.isConnected();
                                if (isConnected) {
                                    mProgressDialog = new ProgressDialog(getActivity());
                                    mProgressDialog.setMessage("Info...");
                                    mProgressDialog.setIndeterminate(false);
                                    mProgressDialog.setCancelable(true);
                                    mProgressDialog.show();
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
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select the date", Snackbar.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Snackbar.make(coordinatorLayout, "Please select the series", Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Snackbar.make(coordinatorLayout, "Please add item", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (ParameterConstant.forAccountIntentBool) {
            String result = ParameterConstant.forAccountIntentName;
            appUser.sale_partyName = ParameterConstant.forAccountIntentId;
            appUser.sale_party_group = ParameterConstant.forAccountIntentGroupId;
            appUser.purchase_account_master_id=ParameterConstant.forAccountIntentId;
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            String[] name = result.split(",");
            mPartyName.setText(name[0]);
            mMobileNumber.setText(ParameterConstant.forAccountIntentMobile);
            Preferences.getInstance(getContext()).setMobile(ParameterConstant.forAccountIntentMobile);
            Preferences.getInstance(getContext()).setParty_name(name[0]);
            Preferences.getInstance(getContext()).setParty_id(ParameterConstant.forAccountIntentId);
        }
        photo = null;
        switch (requestCode) {
            case Cv.REQUEST_CAMERA:
                try {
                    photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageToUploadUri);
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
                    photo = MediaStore.Images.Thumbnails.getThumbnail(getActivity().getContentResolver(),
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

        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.purchase_material_center_id=String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name=result.split(",");
                mStore.setText(name[0]);
                boolForStore=true;
                Preferences.getInstance(getContext()).setStore(name[0]);
                Preferences.getInstance(getContext()).setStoreId(id);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
        if (requestCode == 22) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.purchase_puchase_type_id=String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mPurchaseType.setText(result);
                appUser.mListMapForItemPurchaseReturn.clear();
                appUser.mListMapForBillPurchaseReturn.clear();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(AddItemPurchaseReturnFragment.context).attach(AddItemPurchaseReturnFragment.context).commit();
                Preferences.getInstance(getContext()).setSale_type_name(result);
                Preferences.getInstance(getContext()).setSale_type_id(id);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }

        if (requestCode == 33) {
            if (resultCode == Activity.RESULT_OK) {

                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForPartyName = true;
                    mPartyName.setText(ParameterConstant.name);
                    mMobileNumber.setText(ParameterConstant.mobile);
                    party_id=ParameterConstant.id;
                    appUser.sale_partyName = ParameterConstant.id;
                    appUser.purchase_account_master_id=ParameterConstant.id;
                    Preferences.getInstance(getContext()).setParty_id(ParameterConstant.id);
                    Preferences.getInstance(getContext()).setParty_name(ParameterConstant.name);
                    Preferences.getInstance(getContext()).setMobile(ParameterConstant.mobile);

                }
                else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    party_id=id;
                    appUser.sale_partyName = id;
                    appUser.sale_party_group=group;
                    appUser.purchase_account_master_id=id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    String[] strArr=result.split(",");
                    mPartyName.setText(strArr[0]);
                    mMobileNumber.setText(mobile);
                    boolForPartyName=true;
                    Preferences.getInstance(getContext()).setMobile(mobile);
                    Preferences.getInstance(getContext()).setParty_name(strArr[0]);
                    Preferences.getInstance(getContext()).setParty_id(id);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mPartyName.setText("");
            }
        }
    }

    @Subscribe
    public void createpurchase(CreatePurchaseReturnResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Preferences.getInstance(getActivity()).setUpdate("");
            submit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
           /* if(Preferences.getInstance(getApplicationContext()).getCash_credit().equals("Cash")) {
                if(!appUser.sale_party_group.equals("Cash-in-hand")) {
                    Intent intent = new Intent(getApplicationContext(), CreateReceiptVoucherActivity.class);
                    appUser.voucher_type = "Receipt";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    intent.putExtra("account", mPartyName.getText().toString());
                    intent.putExtra("account_id", appUser.sale_partyName);
                    intent.putExtra("from", "purchase_return");
                    startActivity(intent);
                }
                else{

                    mPartyName.setText("");
                    mMobileNumber.setText("");
                    mNarration.setText("");
                    appUser.mListMapForItemPurchaseReturn.clear();
                    appUser.mListMapForBillPurchaseReturn.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(AddItemPurchaseReturnFragment.context).attach(AddItemPurchaseReturnFragment.context).commit();
                    // startActivity(new Intent(getApplicationContext(), TransactionDashboardActivity.class));
                }
            }
            else{*/

                mPartyName.setText("");
                mMobileNumber.setText("");
                mNarration.setText("");
                encodedString="";
                mSelectedImage.setImageDrawable(null);
                mSelectedImage.setVisibility(View.GONE);
                appUser.mListMapForItemPurchaseReturn.clear();
                appUser.mListMapForBillPurchaseReturn.clear();
                appUser.transport_details.clear();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(AddItemPurchaseReturnFragment.context).attach(AddItemPurchaseReturnFragment.context).commit();

            new AlertDialog.Builder(getActivity())
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(getActivity(), TransactionPdfActivity.class);
                        intent.putExtra("company_report",response.getHtml());
                        startActivity(intent);

                        /*ProgressDialog progressDialog=new ProgressDialog(getActivity());
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                        String htmlString=response.getHtml();
                        Spanned htmlAsSpanned = Html.fromHtml(htmlString);
                        mPdf_webview=new WebView(getApplicationContext());
                        mPdf_webview.loadDataWithBaseURL(null,htmlString, "text/html", "utf-8", null);
                        mPdf_webview.getSettings().setBuiltInZoomControls(true);
                        createWebPrintJob(mPdf_webview);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        },5*1000);

*/
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
            mVchNumber.setText(response.getVoucher_number());

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            // set_date.setOnClickListener(this);
        }
    }



    public void onPause() {
        Preferences.getInstance(getContext()).setVoucher_number(mVchNumber.getText().toString());
        Preferences.getInstance(getContext()).setNarration(mNarration.getText().toString());
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void startDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
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

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getActivity().getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        if (bool) {

            if (intStartActivityForResult==1){
                boolForPartyName=true;
            }else if (intStartActivityForResult==2){
                boolForStore=true;
            }
            /*if (!boolForPartyName) {
                // Toast.makeText(getContext(), "Resume Party", Toast.LENGTH_SHORT).show();
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String mobile = intent.getStringExtra("mobile");
                String group = intent.getStringExtra("group");
                appUser.sale_party_group=group;
                appUser.sale_partyName = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] strArr = result.split(",");
                mPartyName.setText(strArr[0]);
                mMobileNumber.setText(mobile);
                Preferences.getInstance(getContext()).setMobile(mobile);
                Preferences.getInstance(getContext()).setParty_name(strArr[0]);
                Preferences.getInstance(getContext()).setParty_id(id);

            }*/
            if (!boolForStore) {
                //Toast.makeText(getContext(), "Resume Store", Toast.LENGTH_SHORT).show();
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.sale_store = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                mStore.setText(name[0]);
                Preferences.getInstance(getContext()).setStore(name[0]);
                Preferences.getInstance(getContext()).setStoreId(id);

            }
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

        if (path.exists()){
            path.delete();
            path.mkdir();
        }
        File pathPrint = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/m_Billing_PDF/a.pdf");

        PdfPrint pdfPrint = new PdfPrint(attributes);
        pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, "a.pdf");
        previewPdf(pathPrint);
    }

    private void previewPdf(File path) {
        PackageManager packageManager = getActivity().getPackageManager();
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
    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

    @Subscribe
    public void getPurchaseReturnVoucherDetails(GetPurchaseReturnVoucherDetails response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(AddItemPurchaseReturnFragment.context).attach(AddItemPurchaseReturnFragment.context).commit();
            mDate.setText(response.getPurchase_return_voucher().getData().getAttributes().getDate());
            mVchNumber.setText(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_number());
            mPurchaseType.setText(response.getPurchase_return_voucher().getData().getAttributes().getSale_type());
            mStore.setText(response.getPurchase_return_voucher().getData().getAttributes().getMaterial_center());
            mPartyName.setText(response.getPurchase_return_voucher().getData().getAttributes().getAccount_master());
            mMobileNumber.setText(Helpers.mystring(response.getPurchase_return_voucher().getData().getAttributes().getMobile_number()));
            mNarration.setText(Helpers.mystring(response.getPurchase_return_voucher().getData().getAttributes().getNarration()));
            Preferences.getInstance(getContext()).setStore(response.getPurchase_return_voucher().getData().getAttributes().getMaterial_center());
            Preferences.getInstance(getContext()).setStoreId(String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getMaterial_center_id()));
            Preferences.getInstance(getContext()).setSale_type_name(response.getPurchase_return_voucher().getData().getAttributes().getSale_type());
            Preferences.getInstance(getContext()).setSale_type_id(String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getSale_type_id()));
            Preferences.getInstance(getContext()).setParty_id(String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getAccount_master_id()));
            Preferences.getInstance(getContext()).setParty_name(response.getPurchase_return_voucher().getData().getAttributes().getAccount_master());
            Preferences.getInstance(getContext()).setMobile(Helpers.mystring(response.getPurchase_return_voucher().getData().getAttributes().getMobile_number()));
            appUser.totalamount=String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getTotal_amount());
            appUser.items_amount=String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getItems_amount());
            appUser.bill_sundries_amount=String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getBill_sundries_amount());
            LocalRepositories.saveAppUser(getActivity(),appUser);
            if (!Helpers.mystring(response.getPurchase_return_voucher().getData().getAttributes().getAttachment()).equals("")) {
                mSelectedImage.setVisibility(View.VISIBLE);
                Glide.with(this).load(Helpers.mystring(response.getPurchase_return_voucher().getData().getAttributes().getAttachment())).into(mSelectedImage);
            } else {
                mSelectedImage.setVisibility(View.GONE);
            }
            if (response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().size() > 0){
                for (int i = 0; i < response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().size(); i++) {
                    Map mMap = new HashMap<>();
                    mMap.put("id", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getId()));
                    mMap.put("item_id", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_id()));
                    mMap.put("item_name", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getItem());
                    mMap.put("description", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_description());
                    mMap.put("quantity", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getQuantity()));
                    mMap.put("unit", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_unit());
                    mMap.put("discount",  String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getDiscount()));
                    mMap.put("value", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPrice()));
                    mMap.put("default_unit", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getDefault_unit_for_sales());
                    mMap.put("packaging_unit", Helpers.mystring(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit()));
                    mMap.put("purchase_price_alternate", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getSales_price_alternate()));
                    mMap.put("purchase_price_main", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getSales_price_main()));
                    mMap.put("alternate_unit", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getAlternate_unit());
                    mMap.put("packaging_unit_sales_price", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit_sales_price()));
                    mMap.put("main_unit",response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_unit());
                    mMap.put("batch_wise", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getBatch_wise_detail());
                    mMap.put("serial_wise", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getSerial_number_wise_detail());
                    StringBuilder sb=new StringBuilder();
                    for(String str : response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getBarcode()){
                        sb.append(str).append(";"); //separating contents using semi colon
                    }
                    String strfromArrayList = sb.toString();
                    mMap.put("barcode",strfromArrayList);
                    mMap.put("serial_number",strfromArrayList);
                    mMap.put("purchase_unit", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit());
                    ArrayList<String> mUnitList=new ArrayList<>();
                    mUnitList.add("Main Unit : " + response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_unit());
                    mUnitList.add("Alternate Unit :" + response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getAlternate_unit());
                    if (!Helpers.mystring(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit()).equals("")) {
                        mUnitList.add("Packaging Unit :" + Helpers.mystring(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit()));
                    }
                    mMap.put("total",String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPrice_after_discount()));
                    if(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit()!=null) {
                        if (response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit().equals(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_unit())) {
                            if(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()!=null) {
                                if(!String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()).equals("")){
                                    mMap.put("rate", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()));
                                }
                                else{

                                    mMap.put("rate", "0.0");
                                }
                            }
                            else {
                                if(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_main()!=null) {
                                    mMap.put("rate", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_main()));
                                }
                                else{
                                    mMap.put("rate", "0.0");
                                }
                            }
                            mMap.put("price_selected_unit", "main");
                        } else if(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit().equals(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getAlternate_unit())) {
                            mMap.put("price_selected_unit", "alternate");
                            mMap.put("rate",String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_alternate()));
                        }
                        else if(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit().equals(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit())){
                            mMap.put("rate",String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit_sales_price()));
                            mMap.put("price_selected_unit", "packaging");
                        }
                        if(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()!=null) {
                            mMap.put("rate", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()));
                        }
                        else{
                            mMap.put("rate", "0.0");
                        }
                        mMap.put("price_selected_unit", "main");
                    }

                    mMap.put("alternate_unit_con_factor", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getConversion_factor()));
                    mMap.put("packaging_unit_con_factor",  String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_conversion_factor()));
                    mMap.put("mrp",  String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getMrp()));
                    mMap.put("tax", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getTax_category());
                    if(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_applied_on()!=null) {
                        mMap.put("applied", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_items().get(i).getSale_price_applied_on());
                    }
                    else{
                        mMap.put("applied","Main Unit");
                    }
                    //   mMap.put("serial_number", appUser.sale_item_serial_arr);
                    mMap.put("unit_list", mUnitList);
                    appUser.mListMapForItemPurchaseReturn.add(mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }

            }
            if(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries()!=null) {
                if (response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().size() > 0) {
                    for (int i = 0; i < response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().size(); i++) {
                        Map mMap = new HashMap<>();
                        mMap.put("id", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getId());
                        mMap.put("courier_charges", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry());
                        mMap.put("bill_sundry_id", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_id()));
                        mMap.put("percentage", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPercentage()));
                        mMap.put("percentage_value", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPercentage()));
                        mMap.put("default_unit", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getDefault_value()));
                        mMap.put("fed_as", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getAmount_of_bill_sundry_fed_as());
                        mMap.put("fed_as_percentage", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_of_percentage());
                        mMap.put("type", response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_type());
                        mMap.put("amount", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPercentage()));
                        mMap.put("previous", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPrevious_amount()));
                      /*  if(String.valueOf(2)!=null) {*/
                        mMap.put("number_of_bill", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getNumber_of_bill_sundry()));
                        // }
                      /*  if(String.valueOf(true)!=null) {*/
                        mMap.put("consolidated", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getConsolidate_bill_sundry()));
                        // }
                      /*  if(billSundryFedAsPercentage!=null){*/
                        if (response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_of_percentage().equals("valuechange")) {
                            mMap.put("changeamount", String.valueOf(response.getPurchase_return_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPrevious_amount()));
                        }
                        // }

                  /*      if(data.getAttributes().getBill_sundry_id()String.valueOf(billSundryId)!=null) {
                            int size=appUser.arr_billSundryId.size();
                            for(int i=0;i<size;i++){
                                String id=appUser.arr_billSundryId.get(i);
                                if(id.equals(String.valueOf(data.getAttributes().getBill_sundry_id()billSundryId))){
                                    billsundryothername=appUser.arr_billSundryName.get(i);
                                    break;
                                }
                            }
                            mMap.put("other", billsundryothername);
                        }*/
                        appUser.mListMapForBillPurchaseReturn.add(mMap);
                        // appUser.mListMap = mListMap;
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }
                }
            }

        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    @Subscribe
    public void updatepurchasereturnvoucher(UpdatePurchaseReturnVoucher response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            startActivity(new Intent(getApplicationContext(),GetPurchaseReturnListActivity.class));
        }
        else {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public  Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }
    public void alertdialogtype(){
        new android.support.v7.app.AlertDialog.Builder(getContext())
                .setTitle("Purchase Voucher")
                .setMessage("Please add sale type in create voucher")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    return;

                })
                .show();
    }
    public void alertdialogstore(){
        new android.support.v7.app.AlertDialog.Builder(getContext())
                .setTitle("Purchase Voucher")
                .setMessage("Please add store in create voucher")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    return;

                })
                .show();
    }
}
