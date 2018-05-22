package com.lkintechnology.mBilling.fragments.transaction.purchase;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentre.MaterialCentreListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.purchasetype.PurchaseTypeListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.saletype.SaleTypeListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.activities.company.transaction.PurchaseVouchersItemDetailsListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.GetPurchaseListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ReceiptActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransportActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.purchase.CreatePurchaseResponce;
import com.lkintechnology.mBilling.networks.api_response.purchase.UpdatePurchaseResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.GetPurchaseVoucherDetails;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.ImagePicker;
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
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by BerylSystems on 11/22/2017.
 */

public class CreatePurchaseFragment extends Fragment {
    @Bind(R.id.date)
    TextView mDate;
    @Bind(R.id.series)
    Spinner mSeries;
    @Bind(R.id.vch_number)
    EditText mVchNumber;
    @Bind(R.id.purchase_type)
    TextView mPurchaseType;
    @Bind(R.id.store)
    TextView mStore;
    @Bind(R.id.party_name)
    TextView mPartyName;
    @Bind(R.id.shipped_to)
    TextView mShippedTo;
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
    @Bind(R.id.gst_nature_purchase)
    Spinner mGSTNature;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    private SimpleDateFormat dateFormatter;
    Animation blinkOnClick;
    String party_id;
    public static int intStartActivityForResult = 0;
    public Boolean boolForPartyName = false;
    public Boolean boolForStore = false;
    Snackbar snackbar;
    String encodedString;
    Bitmap photo;
    WebView mPdf_webview;
    private Uri imageToUploadUri;
    private FirebaseAnalytics mFirebaseAnalytics;
    public Boolean fromedit=false;

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_create, container, false);
        ButterKnife.bind(this, view);
        appUser = LocalRepositories.getAppUser(getActivity());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        if (CreatePurchaseActivity.fromsalelist) {
            submit.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getActivity(), Cv.ACTION_GET_PURCHASE_VOUCHER_DETAILS);
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

        if (!Preferences.getInstance(getActivity()).getUpdate().equals("")) {
            update.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        } else {
            submit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
        }

        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        String date1 = dateFormatter.format(newCalendar.getTime());
        if (!Preferences.getInstance(getContext()).getVoucher_date().equals("")) {
            mDate.setText(Preferences.getInstance(getContext()).getVoucher_date());
        } else {
            Preferences.getInstance(getContext()).setVoucher_date(date1);
            mDate.setText(Preferences.getInstance(getContext()).getVoucher_date());
        }
        if (!Preferences.getInstance(getContext()).getPurchase_gst_nature().equals("")) {
            String group_type = Preferences.getInstance(getContext()).getPurchase_gst_nature().trim();
            Timber.i("GROUPINDEX" + group_type);
            // insert code here
            int groupindex = -1;
            int symbolindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.gst_nature_purchase).length; i++) {
                if (getResources().getStringArray(R.array.gst_nature_purchase)[i].equals(group_type)) {
                    symbolindex = i;
                    break;
                }
            }
            Timber.i("INDEX" + symbolindex);
            mGSTNature.setSelection(symbolindex);
        }
        mGSTNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Preferences.getInstance(getApplicationContext()).setPurchase_gst_nature(mGSTNature.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mPurchaseType.setText(Preferences.getInstance(getContext()).getPurchase_type_name());
        mDate.setText(Preferences.getInstance(getContext()).getVoucher_date());
        mStore.setText(Preferences.getInstance(getContext()).getStore());
        mPartyName.setText(Preferences.getInstance(getContext()).getParty_name());
        mShippedTo.setText(Preferences.getInstance(getContext()).getShipped_to());
        mVchNumber.setText(Preferences.getInstance(getContext()).getVoucher_number());
        mMobileNumber.setText(Preferences.getInstance(getContext()).getMobile());
        mNarration.setText(Preferences.getInstance(getContext()).getNarration());
        if (!Preferences.getInstance(getContext()).getAttachment().equals("")){
            mSelectedImage.setImageBitmap(Helpers.base64ToBitmap(Preferences.getInstance(getContext()).getAttachment()));
            mSelectedImage.setVisibility(View.VISIBLE);
        }
        if (Preferences.getInstance(getContext()).getCash_credit().equals("CASH")) {
            cash.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setTextColor(Color.parseColor("#ffffff"));
            credit.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#000000"));
        } else if (Preferences.getInstance(getContext()).getCash_credit().equals("Credit")) {
            credit.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#ffffff"));//white
            cash.setTextColor(Color.parseColor("#000000"));//black
        } else {
            cash.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setTextColor(Color.parseColor("#ffffff"));
            credit.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#000000"));
        }

        if (!mDate.getText().toString().equals("")) {
            appUser.purchase_date = mDate.getText().toString();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
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
        mStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intStartActivityForResult = 1;
                ParameterConstant.checkStartActivityResultForAccount = 2;
                MaterialCentreListActivity.isDirectForMaterialCentre = false;
                ParameterConstant.checkStartActivityResultForMaterialCenter = 2;
                startActivityForResult(new Intent(getContext(), MaterialCentreListActivity.class), 11);
            }
        });
        mPurchaseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PurchaseTypeListActivity.isDirectForPurchaseTypeList = false;
                ParameterConstant.checkForPurchaseTypeList = 1;
                ParameterConstant.checkStartActivityResultForAccount = 2;
                startActivityForResult(new Intent(getContext(), PurchaseTypeListActivity.class), 22);
            }
        });
        mPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParameterConstant.forAccountIntentBool = false;
                ParameterConstant.forAccountIntentName = "";
                ParameterConstant.forAccountIntentId = "";
                ParameterConstant.forAccountIntentMobile = "";
                intStartActivityForResult = 2;
                //ParameterConstant.checkStartActivityResultForAccount = 2;
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors,Cash-in-hand";
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView = 0;
                startActivityForResult(new Intent(getContext(), ExpandableAccountListActivity.class), 33);
            }
        });
        mShippedTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParameterConstant.forAccountIntentBool = false;
                ParameterConstant.forAccountIntentName = "";
                ParameterConstant.forAccountIntentId = "";
                ParameterConstant.forAccountIntentMobile = "";
                intStartActivityForResult = 2;
                //ParameterConstant.checkStartActivityResultForAccount = 2;
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors";
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView = 0;
                startActivityForResult(new Intent(getContext(), ExpandableAccountListActivity.class), 44);
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
                if (imageToUploadUri == null) {
                   /* Bitmap bitmap=((GlideBitmapDrawable)mSelectedImage.getDrawable()).getBitmap();
                    String encodedString=Helpers.bitmapToBase64(bitmap);*/
                    Intent intent = new Intent(getApplicationContext(), ImageOpenActivity.class);
                    intent.putExtra("iEncodedString", true);
                    intent.putExtra("encodedString", encodedString);
                    intent.putExtra("bitmapPhotos", true);
                    intent.putExtra("booleAttachment", false);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ImageOpenActivity.class);
                    intent.putExtra("encodedString", imageToUploadUri.toString());
                    intent.putExtra("booleAttachment", false);
                    startActivity(intent);
                }
            }
        });
        mTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransportActivity.voucher_type="purchase";
                Intent intent=new Intent(getApplicationContext(),TransportActivity.class);
                intent.putExtra("fromedit",fromedit);
                startActivity(intent);
            }
        });
        mReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Preferences.getInstance(getActivity()).getPurchase_type_name().equals("")) {
                    if (!Preferences.getInstance(getActivity()).getStore().equals("")) {
                        startActivity(new Intent(getActivity(), ReceiptActivity.class));
                    } else {
                        alertdialogstore();
                    }
                } else {
                    alertdialogtype();
                }

            }
        });
        Preferences.getInstance(getContext()).setCash_credit(cash.getText().toString());
        appUser.sale_cash_credit = cash.getText().toString();
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

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
                appUser = LocalRepositories.getAppUser(getActivity());
                if (appUser.mListMapForItemPurchase.size() > 0) {
                    if (!mSeries.getSelectedItem().toString().equals("")) {
                        if (!mDate.getText().toString().equals("")) {
                            if (!mVchNumber.getText().toString().equals("")) {
                                if (!mPurchaseType.getText().toString().equals("")) {
                                    if (!mStore.getText().toString().equals("")) {
                                        if (!mPartyName.getText().toString().equals("")) {
                                           /* if (!mMobileNumber.getText().toString().equals("")) {*/
                                            appUser.purchase_voucher_series = mSeries.getSelectedItem().toString();
                                            appUser.purchase_voucher_number = mVchNumber.getText().toString();
                                            appUser.purchase_mobile_number = mMobileNumber.getText().toString();
                                            appUser.purchase_narration = mNarration.getText().toString();
                                            appUser.purchase_attachment = encodedString;
                                            // Preferences.getInstance(getApplicationContext()).setPurchase_gst_nature(mGSTNature.getSelectedItem().toString());
                                            LocalRepositories.saveAppUser(getActivity(), appUser);
                                            Boolean isConnected = ConnectivityReceiver.isConnected();
                                            if(appUser.sale_partyEmail!=null&&!appUser.sale_partyEmail.equalsIgnoreCase("null")&&!appUser.sale_partyEmail.equals("")) {
                                                new AlertDialog.Builder(getActivity())
                                                        .setTitle("Email")
                                                        .setMessage(R.string.btn_send_email)
                                                        .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                                            appUser.email_yes_no = "true";
                                                            LocalRepositories.saveAppUser(getActivity(), appUser);
                                                            if (isConnected) {
                                                                mProgressDialog = new ProgressDialog(getActivity());
                                                                mProgressDialog.setMessage("Info...");
                                                                mProgressDialog.setIndeterminate(false);
                                                                mProgressDialog.setCancelable(true);
                                                                mProgressDialog.show();
                                                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_PURCHASE);
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
                                                            LocalRepositories.saveAppUser(getActivity(), appUser);
                                                            if (isConnected) {
                                                                mProgressDialog = new ProgressDialog(getActivity());
                                                                mProgressDialog.setMessage("Info...");
                                                                mProgressDialog.setIndeterminate(false);
                                                                mProgressDialog.setCancelable(true);
                                                                mProgressDialog.show();
                                                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_PURCHASE);
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
                                                appUser.email_yes_no = "false";
                                                LocalRepositories.saveAppUser(getActivity(), appUser);
                                                if (isConnected) {
                                                    mProgressDialog = new ProgressDialog(getActivity());
                                                    mProgressDialog.setMessage("Info...");
                                                    mProgressDialog.setIndeterminate(false);
                                                    mProgressDialog.setCancelable(true);
                                                    mProgressDialog.show();
                                                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_PURCHASE);
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
                                            Snackbar.make(coordinatorLayout, "Please select party name", Snackbar.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Snackbar.make(coordinatorLayout, "Please select store ", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(coordinatorLayout, "Please select purchase type", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please enter vch number", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select the date", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select the series", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please add item", Snackbar.LENGTH_LONG).show();
                }

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser = LocalRepositories.getAppUser(getActivity());
                if (appUser.mListMapForItemPurchase.size() > 0) {
                    if (!mSeries.getSelectedItem().toString().equals("")) {
                        if (!mDate.getText().toString().equals("")) {
                            if (!mVchNumber.getText().toString().equals("")) {
                                if (!mPurchaseType.getText().toString().equals("")) {
                                    if (!mStore.getText().toString().equals("")) {
                                        if (!mPartyName.getText().toString().equals("")) {
                                           /* if (!mMobileNumber.getText().toString().equals("")) {*/
                                            appUser.purchase_voucher_series = mSeries.getSelectedItem().toString();
                                            appUser.purchase_voucher_number = mVchNumber.getText().toString();
                                            appUser.purchase_mobile_number = mMobileNumber.getText().toString();
                                            appUser.purchase_narration = mNarration.getText().toString();
                                            appUser.purchase_attachment = encodedString;
                                            //  Preferences.getInstance(getApplicationContext()).setPurchase_gst_nature(mGSTNature.getSelectedItem().toString());
                                            LocalRepositories.saveAppUser(getActivity(), appUser);
                                            Boolean isConnected = ConnectivityReceiver.isConnected();
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle("Email")
                                                    .setMessage(R.string.btn_send_email)
                                                    .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                                        appUser.email_yes_no = "true";
                                                        LocalRepositories.saveAppUser(getActivity(), appUser);
                                                        if (isConnected) {
                                                            mProgressDialog = new ProgressDialog(getActivity());
                                                            mProgressDialog.setMessage("Info...");
                                                            mProgressDialog.setIndeterminate(false);
                                                            mProgressDialog.setCancelable(true);
                                                            mProgressDialog.show();
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_UPDATE_PURCHASE_VOUCHER_DETAILS);
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
                                                        LocalRepositories.saveAppUser(getActivity(), appUser);
                                                        if (isConnected) {
                                                            mProgressDialog = new ProgressDialog(getActivity());
                                                            mProgressDialog.setMessage("Info...");
                                                            mProgressDialog.setIndeterminate(false);
                                                            mProgressDialog.setCancelable(true);
                                                            mProgressDialog.show();
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_UPDATE_PURCHASE_VOUCHER_DETAILS);
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
                                            Snackbar.make(coordinatorLayout, "Please select party name", Snackbar.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Snackbar.make(coordinatorLayout, "Please select store ", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(coordinatorLayout, "Please select purchase type", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please enter vch number", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select the date", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Please select the series", Snackbar.LENGTH_LONG).show();
                    }
                } else {
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
            party_id = ParameterConstant.forAccountIntentGroupId;
            appUser.sale_partyName = ParameterConstant.forAccountIntentId;
            appUser.sale_party_group = ParameterConstant.forAccountIntentGroupId;
            appUser.purchase_account_master_id = ParameterConstant.forAccountIntentGroupId;
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
                    Bitmap im = scaleDownBitmap(photo, 100, getApplicationContext());
                    mSelectedImage.setVisibility(View.VISIBLE);
                    mSelectedImage.setImageBitmap(im);
                    encodedString = Helpers.bitmapToBase64(im);
                    Preferences.getInstance(getContext()).setAttachment(encodedString);

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
                        TransactionDashboardActivity.bitmapPhoto = photo;
                        Preferences.getInstance(getContext()).setAttachment(encodedString);
                        mSelectedImage.setImageBitmap(photo);
                        break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
        }

        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.purchase_material_center_id = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                mStore.setText(name[0]);
                boolForStore = true;
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
                Timber.i("ID" + id);
                appUser.purchase_puchase_type_id = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mPurchaseType.setText(result);
                appUser.mListMapForItemPurchase.clear();
                appUser.mListMapForBillPurchase.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(AddItemPurchaseFragment.context).attach(AddItemPurchaseFragment.context).commit();
                Preferences.getInstance(getContext()).setPurchase_type_name(result);
                Preferences.getInstance(getContext()).setPurchase_type_id(id);

                appUser.purchase_type_name = result;
                LocalRepositories.saveAppUser(getActivity(), appUser);
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
                    appUser.sale_partyName = ParameterConstant.id;
                    appUser.purchase_account_master_id = ParameterConstant.id;
                    appUser.sale_partyEmail = ParameterConstant.email;
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    Preferences.getInstance(getContext()).setParty_id(ParameterConstant.id);
                    Preferences.getInstance(getContext()).setParty_name(ParameterConstant.name);
                    Preferences.getInstance(getContext()).setMobile(ParameterConstant.mobile);
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    party_id = id;
                    appUser.sale_partyName = id;
                    appUser.sale_party_group = group;
                    appUser.purchase_account_master_id = id;
                    String[] strArr = result.split(",");
                    mPartyName.setText(strArr[0]);
                    mMobileNumber.setText(mobile);
                    appUser.sale_partyEmail = strArr[3];
                    boolForPartyName = true;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Preferences.getInstance(getContext()).setMobile(mobile);
                    Preferences.getInstance(getContext()).setParty_name(strArr[0]);
                    Preferences.getInstance(getContext()).setParty_id(id);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
        if (requestCode == 44) {
            if (resultCode == Activity.RESULT_OK) {

                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    boolForPartyName = true;
                    mShippedTo.setText(ParameterConstant.name);
                    Preferences.getInstance(getContext()).setShipped_to_id(ParameterConstant.id);
                    Preferences.getInstance(getContext()).setShipped_to(ParameterConstant.name);
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    mShippedTo.setText(strArr[0]);
                    boolForPartyName = true;
                    Preferences.getInstance(getContext()).setShipped_to(strArr[0]);
                    Preferences.getInstance(getContext()).setShipped_to_id(id);
                }

            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getActivity().getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        if (bool) {

            if (intStartActivityForResult == 1) {
                boolForPartyName = true;
            } else if (intStartActivityForResult == 2) {
                boolForStore = true;
            }
            /*if (!boolForPartyName) {
                // Toast.makeText(getContext(), "Resume Party", Toast.LENGTH_SHORT).show();
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String mobile = intent.getStringExtra("mobile");
                String group = intent.getStringExtra("group");
                appUser.sale_party_group = group;
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

    @Subscribe
    public void createpurchase(CreatePurchaseResponce response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "purchase_voucher");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,appUser.company_name);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            Preferences.getInstance(getActivity()).setUpdate("");
            submit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
           /* if(Preferences.getInstance(getApplicationContext()).getCash_credit().equals("Cash")) {
                if(!appUser.sale_party_group.equals("Cash-in-hand")) {
                    Intent intent = new Intent(getApplicationContext(), CreateReceiptVoucherActivity.class);
                    intent.putExtra("account", mPartyName.getText().toString());
                    intent.putExtra("account_id", appUser.sale_partyName);
                    intent.putExtra("from", "purchase");
                    startActivity(intent);
                }
                else{
                    mPartyName.setText("");
                    mMobileNumber.setText("");
                    mNarration.setText("");
                    appUser.mListMapForItemPurchase.clear();
                    appUser.mListMapForBillPurchase.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(AddItemPurchaseFragment.context).attach(AddItemPurchaseFragment.context).commit();
                    // startActivity(new Intent(getApplicationContext(), TransactionDashboardActivity.class));
                }
            }
            else{*/
            mPartyName.setText("");
            mMobileNumber.setText("");
            mNarration.setText("");
            mVchNumber.setText("");
            encodedString = "";
            Preferences.getInstance(getContext()).setAttachment("");
            mSelectedImage.setImageDrawable(null);
            mSelectedImage.setVisibility(View.GONE);
            appUser.mListMapForItemPurchase.clear();
            appUser.mListMapForBillPurchase.clear();
            appUser.transport_details.clear();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(AddItemPurchaseFragment.context).attach(AddItemPurchaseFragment.context).commit();
            // startActivity(new Intent(getApplicationContext(), TransactionDashboardActivity.class));
            // }
            new AlertDialog.Builder(getActivity())
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(getActivity(), TransactionPdfActivity.class);
                        intent.putExtra("company_report", response.getHtml());
                        startActivity(intent);

                       /* String htmlString = response.getHtml();
                        Spanned htmlAsSpanned = Html.fromHtml(htmlString);
                        mPdf_webview = new WebView(getApplicationContext());
                        mPdf_webview.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
                        mPdf_webview.getSettings().setBuiltInZoomControls(true);
                        ProgressDialog progressDialog=new ProgressDialog(getActivity());
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
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
                        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        getIntent.setType("image/*");
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("image/*");
                        startActivityForResult(pickIntent, Cv.REQUEST_GALLERY);
                    }
                });
        myAlertDialog.show();
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
    public void getSaleVoucherDetails(GetPurchaseVoucherDetails response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(AddItemPurchaseFragment.context).attach(AddItemPurchaseFragment.context).commit();
            fromedit=true;
            mDate.setText(response.getPurchase_voucher().getData().getAttributes().getDate());
            mVchNumber.setText(response.getPurchase_voucher().getData().getAttributes().getVoucher_number());
            mPurchaseType.setText(response.getPurchase_voucher().getData().getAttributes().getPurchase_type());
            Preferences.getInstance(getApplicationContext()).setPurchase_type_name(response.getPurchase_voucher().getData().getAttributes().getPurchase_type());
            mStore.setText(response.getPurchase_voucher().getData().getAttributes().getMaterial_center());
            mPartyName.setText(response.getPurchase_voucher().getData().getAttributes().getAccount_master());
            mShippedTo.setText(response.getPurchase_voucher().getData().getAttributes().getShipped_to_name());
            mMobileNumber.setText(Helpers.mystring(response.getPurchase_voucher().getData().getAttributes().getMobile_number()));
            mNarration.setText(Helpers.mystring(response.getPurchase_voucher().getData().getAttributes().getNarration()));
            Preferences.getInstance(getContext()).setStore(response.getPurchase_voucher().getData().getAttributes().getMaterial_center());
            Preferences.getInstance(getContext()).setStoreId(String.valueOf(response.getPurchase_voucher().getData().getAttributes().getMaterial_center_id()));
            Preferences.getInstance(getContext()).setPurchase_type_name(response.getPurchase_voucher().getData().getAttributes().getPurchase_type());
            Preferences.getInstance(getContext()).setPurchase_type_id(String.valueOf(response.getPurchase_voucher().getData().getAttributes().getPurchase_type_id()));
            Preferences.getInstance(getContext()).setParty_id(String.valueOf(response.getPurchase_voucher().getData().getAttributes().getAccount_master_id()));
            Preferences.getInstance(getContext()).setParty_name(response.getPurchase_voucher().getData().getAttributes().getAccount_master());
            Preferences.getInstance(getContext()).setShipped_to(response.getPurchase_voucher().getData().getAttributes().getShipped_to_name());
            Preferences.getInstance(getContext()).setShipped_to_id(response.getPurchase_voucher().getData().getAttributes().getShipped_to_id());
            Preferences.getInstance(getContext()).setMobile(Helpers.mystring(response.getPurchase_voucher().getData().getAttributes().getMobile_number()));
            appUser.totalamount = String.valueOf(response.getPurchase_voucher().getData().getAttributes().getTotal_amount());
            appUser.items_amount = String.valueOf(response.getPurchase_voucher().getData().getAttributes().getItems_amount());
            appUser.bill_sundries_amount = String.valueOf(response.getPurchase_voucher().getData().getAttributes().getBill_sundries_amount());
            LocalRepositories.saveAppUser(getActivity(), appUser);
            if (!Helpers.mystring(response.getPurchase_voucher().getData().getAttributes().getAttachment()).equals("")) {
                mSelectedImage.setVisibility(View.VISIBLE);
                Preferences.getInstance(getContext()).setAttachment(response.getPurchase_voucher().getData().getAttributes().getAttachment());
                Glide.with(this).load(Helpers.mystring(response.getPurchase_voucher().getData().getAttributes().getAttachment())).into(mSelectedImage);
            } else {
                mSelectedImage.setVisibility(View.GONE);
            }
            if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().size() > 0) {
                for (int i = 0; i < response.getPurchase_voucher().getData().getAttributes().getVoucher_items().size(); i++) {
                    Map mMap = new HashMap<>();
                    mMap.put("id", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getId()));
                    mMap.put("item_id", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_id()));
                    mMap.put("item_name", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getItem());
                    mMap.put("description", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_description());
                    mMap.put("quantity", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getQuantity()));
                    mMap.put("unit", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_unit());
                    mMap.put("discount", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getDiscount()));
                    mMap.put("value", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPrice()));
                    mMap.put("default_unit", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getDefault_unit_for_sales());
                    mMap.put("packaging_unit", Helpers.mystring(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit()));
                    mMap.put("purchase_price_alternate", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getSales_price_alternate()));
                    mMap.put("purchase_price_main", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getSales_price_main()));
                    mMap.put("alternate_unit", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getAlternate_unit());
                    mMap.put("packaging_unit_sales_price", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit_sales_price()));
                    mMap.put("main_unit", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_unit());
                    mMap.put("batch_wise", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getBatch_wise_detail());
                    mMap.put("serial_wise", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getSerial_number_wise_detail());
                    appUser.purchase_item_serail_arr = response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getVoucher_barcode();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    /* appUser.purchase_item_serail_arr = response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getVoucher_barcode();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    Timber.i("zzzzz "+appUser.purchase_item_serail_arr.toString());
                    StringBuilder sb = new StringBuilder();
                    for (String str : response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getVoucher_barcode()) {
                        sb.append(str).append(","); //separating contents using semi colon
                    }
                    String strfromArrayList = sb.toString();*/
                    mMap.put("serial_number", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getVoucher_barcode());
                    mMap.put("purchase_unit", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit());
                    ArrayList<String> mUnitList = new ArrayList<>();
                    mUnitList.add("Main Unit : " + response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_unit());
                    mUnitList.add("Alternate Unit :" + response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getAlternate_unit());
                    if (!Helpers.mystring(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit()).equals("")) {
                        mUnitList.add("Packaging Unit :" + Helpers.mystring(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit()));
                    }
                    mMap.put("total", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPrice_after_discount()));
                    if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit() != null) {
                        if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit().equals(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getItem_unit())) {
                            if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item() != null) {
                                if (!String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()).equals("")) {
                                    mMap.put("rate", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()));
                                } else {

                                    mMap.put("rate", "0.0");
                                }
                            } else {
                                if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_main() != null) {
                                    mMap.put("rate", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_main()));
                                } else {
                                    mMap.put("rate", "0.0");
                                }
                            }
                            mMap.put("price_selected_unit", "main");
                        } else if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit().equals(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getAlternate_unit())) {
                            mMap.put("price_selected_unit", "alternate");
                            if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item() != null) {
                                if (!String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()).equals("")) {
                                    mMap.put("rate", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()));
                                } else {

                                    mMap.put("rate", "0.0");
                                }
                            } else {
                                mMap.put("rate", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_alternate()));
                            }
                        } else if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_unit().equals(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit())) {
                            if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item() != null) {
                                if (!String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()).equals("")) {
                                    mMap.put("rate", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()));
                                } else {

                                    mMap.put("rate", "0.0");
                                }
                            } else {
                                mMap.put("rate", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_unit_sales_price()));
                            }
                            mMap.put("price_selected_unit", "packaging");
                        } else {
                            if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item() != null) {
                                mMap.put("rate", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getRate_item()));
                            } else {
                                mMap.put("rate", "0.0");
                            }
                            mMap.put("price_selected_unit", "main");
                        }
                    }

                    mMap.put("alternate_unit_con_factor", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getConversion_factor()));
                    mMap.put("packaging_unit_con_factor", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPackaging_conversion_factor()));
                    mMap.put("mrp", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getMrp()));
                    mMap.put("tax", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getTax_category());
                    if (response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_applied_on() != null) {
                        mMap.put("applied", response.getPurchase_voucher().getData().getAttributes().getVoucher_items().get(i).getPurchase_price_applied_on());
                    } else {
                        mMap.put("applied", "Main Unit");
                    }
                    //   mMap.put("serial_number", appUser.sale_item_serial_arr);
                    mMap.put("unit_list", mUnitList);
                    appUser.mListMapForItemPurchase.add(mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
            }
            if (response.getPurchase_voucher().getData().getAttributes().getTransport_details()!=null) {
                TransportActivity.purchasedata=response.getPurchase_voucher().getData().getAttributes().getTransport_details();
            }
            if (response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries() != null) {
                if (response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().size() > 0) {
                    for (int i = 0; i < response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().size(); i++) {
                        Map mMap = new HashMap<>();
                        mMap.put("id", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getId()));
                        mMap.put("courier_charges", response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry());
                        mMap.put("bill_sundry_id", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_id()));
                        mMap.put("percentage", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPercentage()));
                        mMap.put("percentage_value", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPercentage()));
                        mMap.put("default_unit", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getDefault_value()));
                        mMap.put("fed_as", response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getAmount_of_bill_sundry_fed_as());
                        mMap.put("fed_as_percentage", response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_of_percentage());
                        mMap.put("type", response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_type());
                        mMap.put("amount", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPercentage()));
                        mMap.put("previous", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPrevious_amount()));
                      /*  if(String.valueOf(2)!=null) {*/
                        mMap.put("number_of_bill", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getNumber_of_bill_sundry()));
                        // }
                        if (response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPrevious_amount() != 0.0){
                            mMap.put("fed_as_percentage", "valuechange");
                            mMap.put("changeamount", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPrevious_amount()));
                        }
                      /*  if(String.valueOf(true)!=null) {*/
                        mMap.put("consolidated", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getConsolidate_bill_sundry()));
                        // }
                      /*  if(billSundryFedAsPercentage!=null){*/
                      /*  if (response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_of_percentage().equals("valuechange")) {
                            mMap.put("changeamount", String.valueOf(response.getPurchase_voucher().getData().getAttributes().getVoucher_bill_sundries().get(i).getPrevious_amount()));
                        }*/
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
                        appUser.mListMapForBillPurchase.add(mMap);
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
    public void updatepurchasevoucher(UpdatePurchaseResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            Preferences.getInstance(getActivity()).setUpdate("");
            Preferences.getInstance(getContext()).setMobile("");
            Preferences.getInstance(getContext()).setNarration("");
            Preferences.getInstance(getContext()).setAttachment("");
            mPartyName.setText("");
            mMobileNumber.setText("");
            mNarration.setText("");
            mVchNumber.setText("");
            encodedString = "";
            mSelectedImage.setImageDrawable(null);
            mSelectedImage.setVisibility(View.GONE);
            appUser.mListMapForItemPurchase.clear();
            appUser.mListMapForBillPurchase.clear();
            appUser.transport_details.clear();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(AddItemPurchaseFragment.context).attach(AddItemPurchaseFragment.context).commit();
            if(PurchaseVouchersItemDetailsListActivity.isFromTransactionSaleActivity){
                startActivity(new Intent(getApplicationContext(), PurchaseVouchersItemDetailsListActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), GetPurchaseListActivity.class));
            }
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h = (int) (newHeight * densityMultiplier);
        int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

        photo = Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    public void alertdialogtype(){
        new android.support.v7.app.AlertDialog.Builder(getContext())
                .setTitle("Purchase Voucher")
                .setMessage("Please add purchase type in create voucher")
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
