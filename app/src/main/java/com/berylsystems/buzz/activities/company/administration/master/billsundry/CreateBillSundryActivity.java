package com.berylsystems.buzz.activities.company.administration.master.billsundry;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateBillSundryActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Boolean frommbillsundrylist;
    String value="";
    String valuepercentage="";
    String valuepercentagecal="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill_sundry);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        frommbillsundrylist = getIntent().getExtras().getBoolean("frommbillsundrylist");
        if (frommbillsundrylist) {
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
        actionbarTitle.setText("CREATE BILL SUNDRY");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void billsundryaffect(View v) {
        Dialog dialog = new Dialog(CreateBillSundryActivity.this);
        dialog.setContentView(R.layout.dialog_affect_of_bill_sundry);
        dialog.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        Spinner spinner1 = (Spinner) dialog.findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) dialog.findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) dialog.findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) dialog.findViewById(R.id.spinner4);
        Spinner spinner5 = (Spinner) dialog.findViewById(R.id.spinner5);
        if (appUser.cost_goods_in_sale.equals("No")) {
            spinner1.setSelection(1);
        }
        if (appUser.cost_goods_in_purchase.equals("No")) {
            spinner2.setSelection(1);
        }
        if (appUser.cost_material_issue.equals("No")) {
            spinner3.setSelection(1);
        }
        if (appUser.cost_material_receipt.equals("No")) {
            spinner4.setSelection(1);
        }
        if (appUser.cost_stock_transfer.equals("No")) {
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
                }
                else{
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
                }
                else{
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
                appUser.sale_account_head_to_post_party_amount = spinner5.getSelectedItem().toString();
                appUser.sale_post_over_above = spinner6.getSelectedItem().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void accountinginpurchase(View v) {
        Dialog dialog = new Dialog(CreateBillSundryActivity.this);
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
                }
                else{
                    specifyin.setVisibility(View.VISIBLE);
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
                }
                else{
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
                appUser.purchase_account_head_to_post_purchase_amount = spinner3.getSelectedItem().toString();
                appUser.purchase_adjust_in_party_amount = spinner4.getSelectedItem().toString();
                appUser.purchase_party_amount_specify_in = spinner41.getSelectedItem().toString();
                appUser.purchase_account_head_to_post_party_amount = spinner5.getSelectedItem().toString();
                appUser.purchase_post_over_above = spinner6.getSelectedItem().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void amountfed(View v) {
        Dialog dialog = new Dialog(CreateBillSundryActivity.this);
        dialog.setContentView(R.layout.dialog_amount_of_bill_sundry_to_be_fed_as);
        dialog.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup1);


        if (!appUser.bill_sundry_amount_of_bill_sundry_fed_as.equals("")) {
            String radiostring = appUser.bill_sundry_amount_of_bill_sundry_fed_as;
            if(radiostring.equals("Absolute Amount")) {
                radioGroup.check(R.id.radioButtonAbsoluteAmount);
            }
            else if(radiostring.equals("Percentage")) {
                radioGroup.check(R.id.radioButtonPercentage);
            }
            else if(radiostring.equals("Per Main Qty")) {
                radioGroup.check(R.id.radioButtonPerMainQty);
            }
            else if(radiostring.equals("Per Alt. Qty.")) {
                radioGroup.check(R.id.radioButtonPerAltQty);
            }
            else if(radiostring.equals("Per Packaging Qty.")) {
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
                RadioButton radioButton=(RadioButton)radioGroup.findViewById(i);
                value=radioButton.getText().toString();
                if (value.equals("Percentage")) {
                    Dialog dialogpercentage = new Dialog(CreateBillSundryActivity.this);
                    dialogpercentage.setContentView(R.layout.dialog_amount_of_bill_sundry_to_be_fed_as_percentage);
                    dialogpercentage.setCancelable(true);
                    LinearLayout submitpercentage = (LinearLayout) dialogpercentage.findViewById(R.id.submit);
                    LinearLayout closepercentage = (LinearLayout) dialogpercentage.findViewById(R.id.close);
                    RadioGroup radioGrouppercentage = (RadioGroup) dialogpercentage.findViewById(R.id.radioGroup1);
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
                    radioGrouppercentage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup1, int j) {
                            RadioButton radioButton1=(RadioButton)radioGroup1.findViewById(j);
                            valuepercentage=radioButton1.getText().toString();

                            if (valuepercentage.equals("Other Bill Sundry")) {
                                Dialog dialogcalculated = new Dialog(CreateBillSundryActivity.this);
                                dialogcalculated.setContentView(R.layout.dialog_percentage_bill_sundry_to_be_calculated_on);
                                dialogcalculated.setCancelable(true);
                                LinearLayout submitpercentagecal = (LinearLayout) dialogcalculated.findViewById(R.id.submit);
                                LinearLayout closepercentagecal = (LinearLayout) dialogcalculated.findViewById(R.id.close);
                                RadioGroup radioGrouppercentagecal = (RadioGroup) dialogcalculated.findViewById(R.id.radioGroup);
                                radioGrouppercentagecal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                        RadioButton radioButton=(RadioButton)radioGroup.findViewById(i);
                                        valuepercentagecal=radioButton.getText().toString();

                                    }
                                });
                               /* if (!appUser.bill_sundry_calculated_on.equals("")) {
                                    String radiostringpercentagecal = appUser.bill_sundry_calculated_on;
                                    if(radiostringpercentagecal.equals("Bill Sundry Amount")) {
                                        radioGroup.check(R.id.radioButton1);
                                    }
                                    else if(radioGrouppercentagecal.equals("Bill Sundry Applied On")) {
                                        radioGroup.check(R.id.radioButton2);
                                    }
                                }*/
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

                            else if (valuepercentage.equals("Previous Bill Sundry(s) Amount")) {
                                Dialog dialogprevious = new Dialog(CreateBillSundryActivity.this);
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
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        dialogprevious.dismiss();
                                    }
                                });
                                dialogprevious.show();

                            }

                        }
                    });

              /*      if (!appUser.bill_sundry_of_percentage.equals("")) {
                        String radiostringpercentage = appUser.bill_sundry_of_percentage;
                        if(radiostringpercentage.equals("Nett Bill Amount")) {
                            radioGroup.check(R.id.radiobutton1);
                        }
                        else if(radiostringpercentage.equals("Item Basic Amt.")) {
                            radioGroup.check(R.id.radiobutton2);
                        }
                        else if(radiostringpercentage.equals("Total MRP of Items")) {
                            radioGroup.check(R.id.radioButton3);
                        }
                        else if(radiostringpercentage.equals("Taxable Amount")) {
                            radioGroup.check(R.id.radiobutton3);
                        }
                        else if(radiostringpercentage.equals("Previous Bill Sundry(s) Amount")) {
                            radioGroup.check(R.id.radiobutton4);
                        }
                        else if(radiostringpercentage.equals("Other Bill Sundry")) {
                            radioGroup.check(R.id.radiobutton5);
                        }


                    }*/
                    dialogpercentage.show();







                }


            }
        });
        dialog.show();


    }

    public void roundof(View v) {
        Dialog dialogbal = new Dialog(CreateBillSundryActivity.this);
        dialogbal.setContentView(R.layout.dialog_bill_sundry_amount_round_off);
        dialogbal.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialogbal.findViewById(R.id.close);
        Spinner spinner1=(Spinner)dialogbal.findViewById(R.id.spinner1);
        Spinner spinner2=(Spinner)dialogbal.findViewById(R.id.spinner2);

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

    public void hideSoftKeyboard(View v) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


}