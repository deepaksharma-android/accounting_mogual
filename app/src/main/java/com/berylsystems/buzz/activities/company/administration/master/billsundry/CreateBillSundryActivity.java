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
    Boolean frommbillsundrylist;
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
        title="CREATE BILL SUNDRY";

        frommbillsundrylist = getIntent().getExtras().getBoolean("frommbillsundrylist");
        if (frommbillsundrylist) {
            mNatureAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_bill_sundry_nature_name);
            mNatureAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mBillSundryNatureSpinner.setAdapter(mNatureAdapter);
            title="EDIT BILL SUNDRY";
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
        }
        else{
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
                appUser.bill_sundry_nature=appUser.arr_bill_sundry_nature_id.get(i);
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
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
                    if(!mDefaultText.getText().toString().equals("")) {
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
                    if(!mDefaultText.getText().toString().equals("")) {
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
        Dialog dialog = new Dialog(CreateBillSundryActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_affect_of_bill_sundry);
        dialog.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        Spinner spinner1 = (Spinner) dialog.findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) dialog.findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) dialog.findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) dialog.findViewById(R.id.spinner4);
        Spinner spinner5 = (Spinner) dialog.findViewById(R.id.spinner5);
        if (appUser.cost_goods_in_sale.equals("Yes")) {
            spinner1.setSelection(1);
        }
        if (appUser.cost_goods_in_purchase.equals("Yes")) {
            spinner2.setSelection(1);
        }
        if (appUser.cost_material_issue.equals("Yes")) {
            spinner3.setSelection(1);
        }
        if (appUser.cost_material_receipt.equals("Yes")) {
            spinner4.setSelection(1);
        }
        if (appUser.cost_stock_transfer.equals("Yes")) {
            spinner5.setSelection(1);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialog.dismiss();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.cost_goods_in_sale = spinner1.getSelectedItem().toString();
                appUser.cost_goods_in_purchase = spinner2.getSelectedItem().toString();
                appUser.cost_material_issue = spinner3.getSelectedItem().toString();
                appUser.cost_material_receipt = spinner4.getSelectedItem().toString();
                appUser.cost_stock_transfer = spinner5.getSelectedItem().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void accountinginsale(View v) {
        Dialog dialog = new Dialog(CreateBillSundryActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_accounting_in_sale);
        dialog.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        LinearLayout specifyin = (LinearLayout) dialog.findViewById(R.id.specify_in_layout);
        LinearLayout specifypartyin = (LinearLayout) dialog.findViewById(R.id.specify_in_party_amount_layout);
        Spinner spinner1 = (Spinner) dialog.findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) dialog.findViewById(R.id.spinner2);
        Spinner spinner21 = (Spinner) dialog.findViewById(R.id.spinner21);
        // Spinner spinner3 = (Spinner) dialog.findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) dialog.findViewById(R.id.spinner4);
        Spinner spinner41 = (Spinner) dialog.findViewById(R.id.spinner41);
        Spinner spinner5 = (Spinner) dialog.findViewById(R.id.spinner5);
        Spinner spinner6 = (Spinner) dialog.findViewById(R.id.spinner6);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    specifyin.setVisibility(View.VISIBLE);
                } else {
                    specifyin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    specifypartyin.setVisibility(View.VISIBLE);
                } else {
                    specifypartyin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (appUser.sale_affect_accounting.equals("No")) {
            spinner1.setSelection(1);
        }
        if (appUser.sale_affect_sale_amount.equals("No")) {
            spinner2.setSelection(1);
            specifyin.setVisibility(View.VISIBLE);
        }
        if (appUser.sale_affect_sale_amount_specify_in.equals("Specify Acc. in Voucher")) {
            spinner21.setSelection(1);
        }
     /*   if (appUser.sale_account_head_to_post_sale_amount.equals("No")) {
            spinner3.setSelection(1);
        }*/
        if (appUser.sale_adjust_in_party_amount.equals("No")) {
            spinner4.setSelection(1);
        }
        if (appUser.sale_party_amount_specify_in.equals("Specify Acc. in Voucher")) {
            spinner41.setSelection(1);
        }
        if (appUser.sale_account_head_to_post_party_amount.equals("No")) {
            spinner5.setSelection(1);
        }
        if (appUser.sale_post_over_above.equals("No")) {
            spinner6.setSelection(1);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialog.dismiss();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.sale_affect_accounting = spinner1.getSelectedItem().toString();
                appUser.sale_affect_sale_amount = spinner2.getSelectedItem().toString();
                appUser.sale_affect_sale_amount_specify_in = spinner21.getSelectedItem().toString();
//                appUser.sale_account_head_to_post_sale_amount = spinner3.getSelectedItem().toString();
                appUser.sale_adjust_in_party_amount = spinner4.getSelectedItem().toString();
                appUser.sale_party_amount_specify_in = spinner41.getSelectedItem().toString();
//                appUser.sale_account_head_to_post_party_amount = spinner5.getSelectedItem().toString();
                appUser.sale_post_over_above = spinner6.getSelectedItem().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void accountinginpurchase(View v) {
        Dialog dialog = new Dialog(CreateBillSundryActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_accounting_in_purchase);
        dialog.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        LinearLayout specifyin = (LinearLayout) dialog.findViewById(R.id.specify_in_layout);
        LinearLayout specifypartyin = (LinearLayout) dialog.findViewById(R.id.specify_in_party_amount_layout);
        Spinner spinner1 = (Spinner) dialog.findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) dialog.findViewById(R.id.spinner2);
        Spinner spinner21 = (Spinner) dialog.findViewById(R.id.spinner21);
        Spinner spinner3 = (Spinner) dialog.findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) dialog.findViewById(R.id.spinner4);
        Spinner spinner41 = (Spinner) dialog.findViewById(R.id.spinner41);
        Spinner spinner5 = (Spinner) dialog.findViewById(R.id.spinner5);
        Spinner spinner6 = (Spinner) dialog.findViewById(R.id.spinner6);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    specifyin.setVisibility(View.VISIBLE);
                } else {
                    specifyin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    specifypartyin.setVisibility(View.VISIBLE);
                } else {
                    specifypartyin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (appUser.purchase_affect_accounting.equals("No")) {
            spinner1.setSelection(1);
        }
        if (appUser.purchase_affect_purchase_amount.equals("No")) {
            spinner2.setSelection(1);
            specifyin.setVisibility(View.VISIBLE);
        }
        if (appUser.purchase_affect_purchase_amount_specify_in.equals("Specify Acc. in Voucher")) {
            spinner21.setSelection(1);
        }
        if (appUser.purchase_account_head_to_post_purchase_amount.equals("No")) {
            spinner3.setSelection(1);
        }
        if (appUser.purchase_adjust_in_party_amount.equals("No")) {
            spinner4.setSelection(1);
        }
        if (appUser.purchase_party_amount_specify_in.equals("Specify Acc. in Voucher")) {
            spinner41.setSelection(1);
        }
        if (appUser.purchase_account_head_to_post_party_amount.equals("No")) {
            spinner5.setSelection(1);
        }
        if (appUser.purchase_post_over_above.equals("No")) {
            spinner6.setSelection(1);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialog.dismiss();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.purchase_affect_accounting = spinner1.getSelectedItem().toString();
                appUser.purchase_affect_purchase_amount = spinner2.getSelectedItem().toString();
                appUser.purchase_affect_purchase_amount_specify_in = spinner21.getSelectedItem().toString();
//                appUser.purchase_account_head_to_post_purchase_amount = spinner3.getSelectedItem().toString();
                appUser.purchase_adjust_in_party_amount = spinner4.getSelectedItem().toString();
                appUser.purchase_party_amount_specify_in = spinner41.getSelectedItem().toString();
              //  appUser.purchase_account_head_to_post_party_amount = spinner5.getSelectedItem().toString();
                appUser.purchase_post_over_above = spinner6.getSelectedItem().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void amountfed(View v) {
        Dialog dialog = new Dialog(CreateBillSundryActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_amount_of_bill_sundry_to_be_fed_as);
        dialog.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup1);


        if (!appUser.bill_sundry_amount_of_bill_sundry_fed_as.equals("")) {
            String radiostring = appUser.bill_sundry_amount_of_bill_sundry_fed_as;
            if (radiostring.equals("Absolute Amount")) {
                radioGroup.check(R.id.radioButtonAbsoluteAmount);
            } else if (radiostring.equals("Percentage")) {
                radioGroup.check(R.id.radioButtonPercentage);
                dialogpercentage();
            } else if (radiostring.equals("Per Main Qty")) {
                radioGroup.check(R.id.radioButtonPerMainQty);
            } else if (radiostring.equals("Per Alt. Qty.")) {
                radioGroup.check(R.id.radioButtonPerAltQty);
            } else if (radiostring.equals("Per Packaging Qty.")) {
                radioGroup.check(R.id.radioButtonPerPackagingQty);
            }


        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialog.dismiss();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.bill_sundry_amount_of_bill_sundry_fed_as = value;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                value = radioButton.getText().toString();
                if (value.equals("Percentage")) {
                    dialogpercentage();
                }


            }
        });
        dialog.show();


    }

    public void roundof(View v) {
        Dialog dialogbal = new Dialog(CreateBillSundryActivity.this);
        dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogbal.setContentView(R.layout.dialog_bill_sundry_amount_round_off);
        dialogbal.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialogbal.findViewById(R.id.close);
        Spinner spinner1 = (Spinner) dialogbal.findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) dialogbal.findViewById(R.id.spinner2);
        EditText editText = (EditText) dialogbal.findViewById(R.id.edit_text1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    spinner2.setVisibility(View.VISIBLE);
                } else {
                    spinner2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialogbal.dismiss();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialogbal.dismiss();
            }
        });
        dialogbal.show();
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
    public void getbillsundrydetails(GetBillSundryDetailsResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            mSundryName.setText(response.getBill_sundry_info().getData().getAttributes().getName());
            String sundrytype=response.getBill_sundry_info().getData().getAttributes().getBill_sundry_type();
            int sundrytypeindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.bill_sundry_type).length; i++) {
                if (getResources().getStringArray(R.array.bill_sundry_type)[i].equals(sundrytype)) {
                    sundrytypeindex = i;
                    break;
                }
            }
            mBillSundryTypeSpinner.setSelection(sundrytypeindex);
            String sundrynature=response.getBill_sundry_info().getData().getAttributes().getBill_sundry_nature();
            int sundrynatureindex = -1;
            for (int i = 0; i <appUser.arr_bill_sundry_nature_name.size(); i++) {
                if (appUser.arr_bill_sundry_nature_name.get(i).equals(sundrynature)) {
                    sundrynatureindex = i;
                    break;
                }
            }
            mBillSundryNatureSpinner.setSelection(sundrynatureindex);
          //  mDefaultText.setText(String.valueOf(response.getBill_sundry_info().getData().getAttributes().getDefault_value()));
            if(response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getGoods_in_sale()==false) {
                appUser.cost_goods_in_sale = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.cost_goods_in_sale = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }

            if (!response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getGoods_in_purchase()==false){
                appUser.cost_goods_in_purchase = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.cost_goods_in_purchase = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }

            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getMaterial_issue()==false){
                appUser.cost_material_issue = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.cost_material_issue = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getMaterial_receipt()==false){
                appUser.cost_material_receipt = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.cost_material_receipt = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_cost().getStock_transfer()==false){
                appUser.cost_stock_transfer = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.cost_stock_transfer = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAffect_accounting()==false){
                appUser.sale_affect_accounting = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.sale_affect_accounting = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAffect_sale_amount()==false){
                appUser.sale_affect_sale_amount = "No";
                appUser.sale_affect_sale_amount_specify_in=response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAffect_sale_amount_specify_in();
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.sale_affect_sale_amount = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getAdjust_in_party_amount()==false){
                appUser.sale_adjust_in_party_amount = "No";
                appUser.sale_party_amount_specify_in=response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getParty_amount_specify_in();
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.sale_adjust_in_party_amount = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_sale().getPost_over_above()==false){
                appUser.sale_post_over_above = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.sale_post_over_above = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }

            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAffect_accounting()==false){
                appUser.purchase_affect_accounting = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.purchase_affect_accounting = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAffect_purchase_amount()==false){
                appUser.purchase_affect_purchase_amount = "No";
                appUser.purchase_affect_purchase_amount_specify_in=response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAffect_purchase_amount_specify_in();
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.purchase_affect_purchase_amount = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getAdjust_in_party_amount()==false){
                appUser.purchase_adjust_in_party_amount = "No";
                appUser.purchase_party_amount_specify_in=response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getParty_amount_specify_in();
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.purchase_adjust_in_party_amount = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            if (response.getBill_sundry_info().getData().getAttributes().getBill_sundry_affects_purchase().getPost_over_above()==false){
                appUser.purchase_post_over_above = "No";
                LocalRepositories.saveAppUser(this, appUser);
            }
            else{
                appUser.purchase_post_over_above = "Yes";
                LocalRepositories.saveAppUser(this, appUser);
            }
            appUser.bill_sundry_of_percentage=response.getBill_sundry_info().getData().getAttributes().getBill_sundry_of_percentage();
            appUser.bill_sundry_amount_of_bill_sundry_fed_as=response.getBill_sundry_info().getData().getAttributes().getAmount_of_bill_sundry_fed_as();
            LocalRepositories.saveAppUser(this, appUser);


        }
    }

    @Subscribe
    public void editunit(EditBillSundryResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),BillSundryListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getbillsundrynature(GetBillSundryNatureResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.arr_bill_sundry_nature_id.clear();
            appUser.arr_bill_sundry_nature_name.clear();
            for(int i=0;i<response.getBill_sundry_nature().getData().size();i++){
                appUser.arr_bill_sundry_nature_id.add(response.getBill_sundry_nature().getData().get(i).getId());
                appUser.arr_bill_sundry_nature_name.add(response.getBill_sundry_nature().getData().get(i).getAttributes().getName());
                LocalRepositories.saveAppUser(this,appUser);
            }

            mNatureAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_bill_sundry_nature_name);
            mNatureAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mBillSundryNatureSpinner.setAdapter(mNatureAdapter);

        }
    }

    public void dialogpercentage(){
        Dialog dialogpercentage = new Dialog(CreateBillSundryActivity.this);
        dialogpercentage.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogpercentage.setContentView(R.layout.dialog_amount_of_bill_sundry_to_be_fed_as_percentage);
        dialogpercentage.setCancelable(true);
        LinearLayout submitpercentage = (LinearLayout) dialogpercentage.findViewById(R.id.submit);
        LinearLayout closepercentage = (LinearLayout) dialogpercentage.findViewById(R.id.close);
        RadioGroup radioGrouppercentage = (RadioGroup) dialogpercentage.findViewById(R.id.radioGroup1);
        radioGrouppercentage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                valuepercentage=radioButton.getText().toString();
            }
        });
        closepercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialogpercentage.dismiss();

            }
        });
        submitpercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.bill_sundry_of_percentage = valuepercentage;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialogpercentage.dismiss();
            }
        });
       /* if (!appUser.bill_sundry_of_percentage.equals("")) {
            String radiostringpercentage = appUser.bill_sundry_of_percentage;

            if(radiostringpercentage.equals("Nett Bill Amount")) {
                radioGrouppercentage.check(R.id.radiobutton1);
            }
            else if(radiostringpercentage.equals("Item Basic Amt.")) {
                radioGrouppercentage.check(R.id.radiobutton2);
            }
            else if(radiostringpercentage.equals("Total MRP of Items")) {
                radioGrouppercentage.check(R.id.radioButton3);
            }
            else if(radiostringpercentage.equals("Taxable Amount")) {
                radioGrouppercentage.check(R.id.radiobutton3);
            }
            else if(radiostringpercentage.equals("Previous Bill Sundry(s) Amount")) {
                radioGrouppercentage.check(R.id.radiobutton4);
               // previous();
            }
            else if(radiostringpercentage.equals("Other Bill Sundry")) {
                radioGrouppercentage.check(R.id.radiobutton5);
               // calculatedon();
            }


        }
        radioGrouppercentage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup1, int j) {
                RadioButton radioButton1 = (RadioButton) radioGroup1.findViewById(j);
                valuepercentage = radioButton1.getText().toString();
                if (valuepercentage.equals("Other Bill Sundry")) {
                    calculatedon();
                } else if (valuepercentage.equals("Previous Bill Sundry(s) Amount")) {
                    previous();
                }

            }
        });*/
        dialogpercentage.show();
    }

    /*public void calculatedon(){
        Dialog dialogcalculated = new Dialog(CreateBillSundryActivity.this);
        dialogcalculated.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogcalculated.setContentView(R.layout.dialog_percentage_bill_sundry_to_be_calculated_on);
        dialogcalculated.setCancelable(true);
        LinearLayout submitpercentagecal = (LinearLayout) dialogcalculated.findViewById(R.id.submit);
        LinearLayout closepercentagecal = (LinearLayout) dialogcalculated.findViewById(R.id.close);
        RadioGroup radioGrouppercentagecal = (RadioGroup) dialogcalculated.findViewById(R.id.radioGroup);
        radioGrouppercentagecal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                valuepercentagecal = radioButton.getText().toString();

            }
        });
                               *//* if (!appUser.bill_sundry_calculated_on.equals("")) {
                                    String radiostringpercentagecal = appUser.bill_sundry_calculated_on;
                                    if(radiostringpercentagecal.equals("Bill Sundry Amount")) {
                                        radioGroup.check(R.id.radioButton1);
                                    }
                                    else if(radioGrouppercentagecal.equals("Bill Sundry Applied On")) {
                                        radioGroup.check(R.id.radioButton2);
                                    }
                                }*//*
        closepercentagecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialogcalculated.dismiss();

            }
        });
        submitpercentagecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                appUser.bill_sundry_calculated_on = valuepercentagecal;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialogcalculated.dismiss();
            }
        });
        dialogcalculated.show();
    }

    public void previous(){
        Dialog dialogprevious = new Dialog(CreateBillSundryActivity.this);
        dialogprevious.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogprevious.setContentView(R.layout.dialog_percentage_previous_bill_sundry_s_details);
        dialogprevious.setCancelable(true);
        LinearLayout submitpercentageprev = (LinearLayout) dialogprevious.findViewById(R.id.submit);
        LinearLayout closepercentageprev = (LinearLayout) dialogprevious.findViewById(R.id.close);
        Spinner spinner1 = (Spinner) dialogprevious.findViewById(R.id.spinner1);
        EditText editText1 = (EditText) dialogprevious.findViewById(R.id.edit_text1);

        closepercentageprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialogprevious.dismiss();

            }
        });
        submitpercentageprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialogprevious.dismiss();
            }
        });
        dialogprevious.show();
    }*/


}