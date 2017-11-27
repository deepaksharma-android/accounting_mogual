package com.berylsystems.buzz.activities.company.administration.master.billsundry;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.administration.master.unit.UnitListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.bill_sundry.CreateBillSundryResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.EditBillSundryResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.GetBillSundryDetailsResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.GetBillSundryNatureResponse;
import com.berylsystems.buzz.networks.api_response.unit.EditUnitResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.Helpers;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateBillSundryActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    @Bind(R.id.sundry_name)
    EditText mSundryName;
    @Bind(R.id.bill_sundry_type_spinner)
    Spinner mBillSundryTypeSpinner;
    @Bind(R.id.bill_sundry_nature_spinner)
    Spinner mBillSundryNatureSpinner;
    @Bind(R.id.default_value)
    EditText mDefaultText;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Boolean frommbillsundrylist, fromList;
    String value = "";
    String valuepercentage = "";
    String valuepercentagecal = "";
    String title;
    public ArrayAdapter<String> mNatureAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        title = "CREATE BILL SUNDRY";
        fromList = getIntent().getExtras().getBoolean("fromlist");
        if (fromList) {
            Preferences.getInstance(getApplicationContext()).setbill_sundry_number_of_bill_sundry("");
            Preferences.getInstance(getApplicationContext()).setbill_sundry_consolidate_bill_sundry("");
            Preferences.getInstance(getApplicationContext()).setbill_sundry_rouding_off_nearest("");
            Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_round_off("");
            Preferences.getInstance(getApplicationContext()).setbill_sundry_rounding_off_limit("");
            Preferences.getInstance(getApplicationContext()).setbill_sundry_calculated_on("");
            Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_of_bill_sundry_fed_as("");
            Preferences.getInstance(getApplicationContext()).setbill_sundry_of_percentage("");
            Preferences.getInstance(getApplicationContext()).setcost_goods_in_sale("");
            Preferences.getInstance(getApplicationContext()).setcost_goods_in_purchase("");
            Preferences.getInstance(getApplicationContext()).setcost_material_issue("");
            Preferences.getInstance(getApplicationContext()).setcost_material_receipt("");
            Preferences.getInstance(getApplicationContext()).setcost_stock_transfer("");
            Preferences.getInstance(getApplicationContext()).setsale_affect_accounting("");
            Preferences.getInstance(getApplicationContext()).setsale_affect_sale_amount("");
            Preferences.getInstance(getApplicationContext()).setsale_affect_sale_amount_specify_in("");
            Preferences.getInstance(getApplicationContext()).setsale_account_head_to_post_sale_amount("");
            Preferences.getInstance(getApplicationContext()).setsale_adjust_in_party_amount("");
            Preferences.getInstance(getApplicationContext()).setsale_party_amount_specify_in("");
            Preferences.getInstance(getApplicationContext()).setsale_account_head_to_post_party_amount("");
            Preferences.getInstance(getApplicationContext()).setsale_post_over_above("");
            Preferences.getInstance(getApplicationContext()).setpurchase_affect_accounting("");
            Preferences.getInstance(getApplicationContext()).setpurchase_affect_purchase_amount("");
            Preferences.getInstance(getApplicationContext()).setpurchase_affect_purchase_amount_specify_in("");
            Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_purchase_amount("");
            Preferences.getInstance(getApplicationContext()).setpurchase_adjust_in_party_amount("");
            Preferences.getInstance(getApplicationContext()).setpurchase_party_amount_specify_in("");
            Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_party_amount("");
            Preferences.getInstance(getApplicationContext()).setpurchase_post_over_above("");
            Preferences.getInstance(getApplicationContext()).setcalculated_on_bill_sundry_id("");
        }
        frommbillsundrylist = getIntent().getExtras().getBoolean("frommbillsundrylist");
        if (frommbillsundrylist) {
            mNatureAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_bill_sundry_nature_name);
            mNatureAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mBillSundryNatureSpinner.setAdapter(mNatureAdapter);
            title = "EDIT BILL SUNDRY";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_bill_sundry_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            Timber.i("I am here");
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateBillSundryActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BILL_SUNDRY_DETAILS);
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

            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateBillSundryActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BILL_SUNDRY_NATURE);
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
        mNatureAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_bill_sundry_nature_name);
        mNatureAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mBillSundryNatureSpinner.setAdapter(mNatureAdapter);
        mBillSundryNatureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                appUser.bill_sundry_nature = appUser.arr_bill_sundry_nature_id.get(i);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mSundryName.getText().toString().equals("")) {
                    appUser.bill_sundry_name = mSundryName.getText().toString();
                    appUser.bill_sundry_type = mBillSundryTypeSpinner.getSelectedItem().toString();
                    if (!mDefaultText.getText().toString().equals("")) {
                        appUser.bill_sundry_default_value = Double.valueOf(mDefaultText.getText().toString());
                    }

                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateBillSundryActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_BILL_SUNDRY);
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
                    Snackbar.make(coordinatorLayout, "Enter the bill sundry name", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mSundryName.getText().toString().equals("")) {
                    appUser.bill_sundry_name = mSundryName.getText().toString();
                    appUser.bill_sundry_type = mBillSundryTypeSpinner.getSelectedItem().toString();
                    if (!mDefaultText.getText().toString().equals("")) {
                        appUser.bill_sundry_default_value = Double.valueOf(mDefaultText.getText().toString());
                    }
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateBillSundryActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_BILL_SUNDRY);
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
                    Snackbar.make(coordinatorLayout, "Enter the bill sundry name", Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_bill_sundry;
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    public void billsundryaffect(View v) {
        startActivity(new Intent(getApplicationContext(), AffectOfBillSundryActivity.class));
    }

    public void accountinginsale(View v) {
        startActivity(new Intent(getApplicationContext(), AccountingInSaleActivity.class));

    }

    public void accountinginpurchase(View v) {
        startActivity(new Intent(getApplicationContext(), AccountingInPurchaseActivity.class));
    }

    public void amountfed(View v) {
        startActivity(new Intent(getApplicationContext(), BillSundryToBeFedAsActivity.class));
    }

    public void roundof(View v) {
        startActivity(new Intent(getApplicationContext(), RoundOffActivity.class));
    }

    @Subscribe
    public void createbillsundry(CreateBillSundryResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), BillSundryListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    public void hideSoftKeyboard(View v) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Subscribe
    public void getbillsundrydetails(GetBillSundryDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mSundryName.setText(response.getBill_sundry_info().getData().getAttributes().getName());
            String sundrytype = response.getBill_sundry_info().getData().getAttributes().getBill_sundry_type();
            int sundrytypeindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.bill_sundry_type).length; i++) {
                if (getResources().getStringArray(R.array.bill_sundry_type)[i].equals(sundrytype)) {
                    sundrytypeindex = i;
                    break;
                }
            }
            mBillSundryTypeSpinner.setSelection(sundrytypeindex);
            String sundrynature = response.getBill_sundry_info().getData().getAttributes().getBill_sundry_nature();
            int sundrynatureindex = -1;
            for (int i = 0; i < appUser.arr_bill_sundry_nature_name.size(); i++) {
                if (appUser.arr_bill_sundry_nature_name.get(i).equals(sundrynature)) {
                    sundrynatureindex = i;
                    break;
                }
            }


            mBillSundryNatureSpinner.setSelection(sundrynatureindex);
            mDefaultText.setText(String.valueOf(response.getBill_sundry_info().getData().getAttributes().getDefault_value()));
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getGoods_in_sale()!= null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getGoods_in_sale() == false) {
                    Preferences.getInstance(getApplicationContext()).setcost_goods_in_sale("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setcost_goods_in_sale("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getGoods_in_purchase() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getGoods_in_purchase() == false) {
                    Preferences.getInstance(getApplicationContext()).setcost_goods_in_purchase("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setcost_goods_in_purchase("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getMaterial_issue() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getMaterial_issue() == false) {
                    Preferences.getInstance(getApplicationContext()).setcost_material_issue("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setcost_material_issue("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getMaterial_receipt() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getMaterial_receipt() == false) {
                    Preferences.getInstance(getApplicationContext()).setcost_material_receipt("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setcost_material_receipt("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getStock_transfer() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getStock_transfer() == false) {
                    Preferences.getInstance(getApplicationContext()).setcost_stock_transfer("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setcost_stock_transfer("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAffect_accounting() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAffect_accounting() == false) {
                    Preferences.getInstance(getApplicationContext()).setsale_affect_accounting("No");
                }
                Preferences.getInstance(getApplicationContext()).setsale_affect_accounting("Yes");
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAffect_sale_amount() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAffect_sale_amount() == false) {
                    Preferences.getInstance(getApplicationContext()).setsale_affect_sale_amount("No");
                    Preferences.getInstance(getApplicationContext()).setsale_affect_sale_amount_specify_in(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAffect_sale_amount_specify_in());
                    Preferences.getInstance(getApplicationContext()).setsale_account_head_to_post_sale_amount(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAccount_head_to_post_sale_amount());
                } else {
                    Preferences.getInstance(getApplicationContext()).setsale_affect_sale_amount("No");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAdjust_in_party_amount() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAdjust_in_party_amount() == false) {
                    Preferences.getInstance(getApplicationContext()).setsale_adjust_in_party_amount("No");
                    Preferences.getInstance(getApplicationContext()).setsale_party_amount_specify_in(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getParty_amount_specify_in());
                    Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_party_amount(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAccount_head_to_post_party_amount());

                } else {
                    Preferences.getInstance(getApplicationContext()).setsale_adjust_in_party_amount("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getPost_over_above() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getPost_over_above() == false) {
                    Preferences.getInstance(getApplicationContext()).setsale_post_over_above("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setsale_post_over_above("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAffect_accounting() != null) {

                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAffect_accounting() == false) {
                    Preferences.getInstance(getApplicationContext()).setpurchase_affect_accounting("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setpurchase_affect_accounting("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAffect_purchase_amount() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAffect_purchase_amount() == false) {
                    Preferences.getInstance(getApplicationContext()).setpurchase_affect_purchase_amount("No");
                    Preferences.getInstance(getApplicationContext()).setpurchase_affect_purchase_amount_specify_in(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAffect_purchase_amount_specify_in());
                    Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_purchase_amount(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAccount_head_to_post_party_amount());
                } else {
                    Preferences.getInstance(getApplicationContext()).setpurchase_affect_purchase_amount("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAdjust_in_party_amount() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAdjust_in_party_amount() == false) {
                    Preferences.getInstance(getApplicationContext()).setpurchase_adjust_in_party_amount("No");
                    if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getParty_amount_specify_in() != null) {
                        Preferences.getInstance(getApplicationContext()).setpurchase_party_amount_specify_in(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getParty_amount_specify_in());
                    }
                    if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAccount_head_to_post_party_amount() != null)
                        Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_party_amount(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAccount_head_to_post_party_amount());
                } else {
                    Preferences.getInstance(getApplicationContext()).setpurchase_adjust_in_party_amount("Yes");
                }

            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getPost_over_above() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getPost_over_above() == false) {
                    Preferences.getInstance(getApplicationContext()).setpurchase_post_over_above("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setpurchase_post_over_above("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_calculated_on() != null) {
                Preferences.getInstance(getApplicationContext()).setbill_sundry_calculated_on(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_calculated_on());
            }
            if (response.getBill_sundry_info().getData().getAttributes().getAmount_of_bill_sundry_fed_as() != null) {
                Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_of_bill_sundry_fed_as(response.getBill_sundry_info().getData().getAttributes().getAmount_of_bill_sundry_fed_as());
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_of_percentage() != null) {
                Preferences.getInstance(getApplicationContext()).setbill_sundry_of_percentage(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_of_percentage());
            }
            if (response.getBill_sundry_info().getData().getAttributes().getRounding_off_nearest() != null) {
                Preferences.getInstance(getApplicationContext()).setbill_sundry_rouding_off_nearest(String.valueOf(response.getBill_sundry_info().getData().getAttributes().getRounding_off_nearest()));
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_amount_round_off() != null) {
                if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_amount_round_off() == false) {
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_round_off("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_round_off("Yes");
                }
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_round_off_limit() != null) {
                Preferences.getInstance(getApplicationContext()).setbill_sundry_rounding_off_limit(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_round_off_limit());
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_id() != null) {
                Preferences.getInstance(getApplicationContext()).setcalculated_on_bill_sundry_id(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_round_off_limit());
            }


        }
    }

    @Subscribe
    public void editunit(EditBillSundryResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), BillSundryListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getbillsundrynature(GetBillSundryNatureResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.arr_bill_sundry_nature_id.clear();
            appUser.arr_bill_sundry_nature_name.clear();
            for (int i = 0; i < response.getBill_sundry_nature().getData().size(); i++) {
                appUser.arr_bill_sundry_nature_id.add(response.getBill_sundry_nature().getData().get(i).getId());
                appUser.arr_bill_sundry_nature_name.add(response.getBill_sundry_nature().getData().get(i).getAttributes().getName());
                LocalRepositories.saveAppUser(this, appUser);
            }

            mNatureAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_bill_sundry_nature_name);
            mNatureAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mBillSundryNatureSpinner.setAdapter(mNatureAdapter);

        }
    }
}