package com.lkintechnology.mBilling.fragments.transaction.stock_transfer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.administration.master.materialcentre.MaterialCentreListActivity;
import com.lkintechnology.mBilling.activities.company.administration.master.purchasetype.PurchaseTypeListActivity;
import com.lkintechnology.mBilling.activities.company.administration.master.saletype.SaleTypeListActivity;
import com.lkintechnology.mBilling.activities.company.navigation.reports.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.fragments.transaction.purchase.AddItemPurchaseFragment;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase.CreatePurchaseResponce;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by BerylSystems on 11/22/2017.
 */

public class CreateStockTransferFragment extends Fragment {
    @Bind(R.id.date)
    TextView mDate;
    @Bind(R.id.series)
    Spinner mSeries;
    @Bind(R.id.vch_number)
    TextView mVchNumber;
    @Bind(R.id.stock_type)
    TextView mStockType;
    @Bind(R.id.store_from)
    TextView mStoreFrom;
    @Bind(R.id.store_to)
    TextView mStoreTo;
    @Bind(R.id.mobile_number)
    EditText mMobileNumber;
    @Bind(R.id.cash)
    Button cash;
    @Bind(R.id.credit)
    Button credit;
    @Bind(R.id.submit)
    LinearLayout submit;
    @Bind(R.id.narration)
    EditText mNarration;
    @Bind(R.id.browse_image)
    LinearLayout mBrowseImage;
    @Bind(R.id.selected_image)
    ImageView mSelectedImage;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
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

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_stock_transfer, container, false);
        ButterKnife.bind(this, view);

        appUser = LocalRepositories.getAppUser(getActivity());
        appUser.voucher_type = "Sales";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

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

        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        String date1 = dateFormatter.format(newCalendar.getTime());
        Preferences.getInstance(getContext()).setVoucher_date(date1);
        mStockType.setText(Preferences.getInstance(getContext()).getPurchase_type_name());
        mDate.setText(Preferences.getInstance(getContext()).getVoucher_date());
        mStoreFrom.setText(Preferences.getInstance(getContext()).getStore());
        mStoreTo.setText(Preferences.getInstance(getContext()).getStore_to());
        //mPartyName.setText(Preferences.getInstance(getContext()).getParty_name());
        mVchNumber.setText(Preferences.getInstance(getContext()).getVoucher_number());
        mMobileNumber.setText(Preferences.getInstance(getContext()).getMobile());
        mNarration.setText(Preferences.getInstance(getContext()).getNarration());
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
        mStoreFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intStartActivityForResult = 1;
                ParameterConstant.checkStartActivityResultForMaterialCenter=5;
                MaterialCentreListActivity.isDirectForMaterialCentre = false;

                startActivityForResult(new Intent(getContext(), MaterialCentreListActivity.class), 11);
            }
        });
        mStoreTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intStartActivityForResult = 2;
                MaterialCentreListActivity.isDirectForMaterialCentre = false;
                ParameterConstant.checkStartActivityResultForMaterialCenter=5;
                startActivityForResult(new Intent(getContext(), MaterialCentreListActivity.class), 12);
            }
        });
        mStockType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaleTypeListActivity.isDirectForSaleType = false;
                ParameterConstant.checkForPurchaseTypeList=1;
                ParameterConstant.checkStartActivityResultForAccount = 2;
                startActivityForResult(new Intent(getContext(), PurchaseTypeListActivity.class), 22);
            }
        });
       /* mPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intStartActivityForResult = 2;
                ParameterConstant.checkStartActivityResultForAccount = 2;
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors,Cash-in-hand";
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView=0;
                startActivityForResult(new Intent(getContext(), ExpandableAccountListActivity.class), 33);
            }
        });*/

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
                intent.putExtra("encodedString",encodedString);
                intent.putExtra("booleAttachment",false);
                startActivity(intent);
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
                appUser=LocalRepositories.getAppUser(getActivity());
                if (appUser.mListMapForItemPurchase.size() > 0) {
                    if (!mSeries.getSelectedItem().toString().equals("")) {
                        if (!mDate.getText().toString().equals("")) {
                            if (!mVchNumber.getText().toString().equals("")) {
                                if (!mStockType.getText().toString().equals("")) {
                                    if (!mStoreFrom.getText().toString().equals("")) {
                                      //  if (!mPartyName.getText().toString().equals("")) {
                                           /* if (!mMobileNumber.getText().toString().equals("")) {*/
                                            appUser.purchase_voucher_series = mSeries.getSelectedItem().toString();
                                            appUser.purchase_voucher_number = mVchNumber.getText().toString();
                                            appUser.purchase_mobile_number = mMobileNumber.getText().toString();
                                            appUser.purchase_narration = mNarration.getText().toString();
                                            appUser.purchase_attachment = encodedString;
                                            LocalRepositories.saveAppUser(getActivity(), appUser);
                                            Boolean isConnected = ConnectivityReceiver.isConnected();
                                                        if (isConnected) {
                                                            mProgressDialog = new ProgressDialog(getActivity());
                                                            mProgressDialog.setMessage("Info...");
                                                            mProgressDialog.setIndeterminate(false);
                                                            mProgressDialog.setCancelable(true);
                                                            mProgressDialog.show();
                                                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_STOCK_TRANSFER);
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
                                        Snackbar.make(coordinatorLayout, "Please select store ", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(coordinatorLayout, "Please select purchase type", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please enter vch number", Snackbar.LENGTH_LONG).show();
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
                appUser.stock_from_material_center_id = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                mStoreFrom.setText(name[0]);
                boolForStore = true;
                Preferences.getInstance(getContext()).setStore(name[0]);
                Preferences.getInstance(getContext()).setStoreId(id);
              //  Toast.makeText(getActivity(), "result code 11", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
        if (requestCode == 12) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.stock_to_material_center_id = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                mStoreTo.setText(name[0]);
                boolForStore = true;
                Preferences.getInstance(getContext()).setStore_to(name[0]);
                Preferences.getInstance(getContext()).setStore_to_id(id);
               // Toast.makeText(getActivity(), "result code 12", Toast.LENGTH_SHORT).show();
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
               // appUser.purchase_puchase_type_id = String.valueOf(id);
                appUser.stock_type_id = String.valueOf(id);
                appUser.stock_type_name = result;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mStockType.setText(result);
                Preferences.getInstance(getContext()).setPurchase_type_name(result);
                Preferences.getInstance(getContext()).setPurchase_type_id(id);
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
                    //mPartyName.setText(ParameterConstant.name);
                    mMobileNumber.setText(ParameterConstant.mobile);
                    appUser.sale_partyName = ParameterConstant.id;
                    appUser.purchase_account_master_id = ParameterConstant.id;
                    Preferences.getInstance(getContext()).setParty_id(ParameterConstant.id);
                    Preferences.getInstance(getContext()).setParty_name(ParameterConstant.name);
                    Preferences.getInstance(getContext()).setMobile(ParameterConstant.mobile);
                }
                else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    party_id = id;
                    appUser.sale_partyName = id;
                    appUser.sale_party_group = group;
                    appUser.purchase_account_master_id = id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    String[] strArr = result.split(",");
                    //mPartyName.setText(strArr[0]);
                    mMobileNumber.setText(mobile);
                    boolForPartyName = true;
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
            if (!boolForPartyName) {
               // Toast.makeText(getActivity(), "resume party", Toast.LENGTH_SHORT).show();
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.sale_store = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                mStoreFrom.setText(name[0]);
                Preferences.getInstance(getContext()).setStore(name[0]);
                Preferences.getInstance(getContext()).setStoreId(id);

            }
            if (!boolForStore) {
              //  Toast.makeText(getContext(), "Resume Store", Toast.LENGTH_SHORT).show();
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.sale_store_to = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                mStoreTo.setText(name[0]);
                Preferences.getInstance(getContext()).setStore_to(name[0]);
                Preferences.getInstance(getContext()).setStore_to_id(id);
            }
        }

    }

    @Subscribe
    public void createpurchase(CreatePurchaseResponce response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
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
            //mPartyName.setText("");
            mMobileNumber.setText("");
            mNarration.setText("");
            mVchNumber.setText("");
            mStoreFrom.setText("");
            mStoreTo.setText("");
            mSelectedImage.setImageResource(0);
            mSelectedImage.setVisibility(View.GONE);
            appUser.mListMapForItemPurchase.clear();
            appUser.mListMapForBillPurchase.clear();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(AddItemPurchaseFragment.context).attach(AddItemPurchaseFragment.context).commit();
            // startActivity(new Intent(getApplicationContext(), TransactionDashboardActivity.class));
            // }
            new AlertDialog.Builder(getActivity())
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        Intent intent = new Intent(getActivity(), TransactionPdfActivity.class);
                        intent.putExtra("company_report",response.getHtml());
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
                intCamera.putExtra("android.intent.extras.CAMERA_FACING", 1);

                if (intCamera.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intCamera, Cv.REQUEST_CAMERA);
                }

            }
        });

        myAlertDialog.setNegativeButton("Gallary",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intGallery = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intGallery, Cv.REQUEST_GALLERY);

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
    public void getVoucherNumber(GetVoucherNumbersResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mVchNumber.setText(response.getVoucher_number());

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
        mProgressDialog.dismiss();

    }
}
