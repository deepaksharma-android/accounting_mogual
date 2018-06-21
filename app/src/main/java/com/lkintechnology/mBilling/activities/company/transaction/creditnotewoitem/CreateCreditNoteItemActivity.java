package com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.GetPurchaseListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.GetSaleVoucherListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

import static android.media.CamcorderProfile.get;

public class CreateCreditNoteItemActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etIVNNo;
    private TextView tvDiffAmount;
    private TextView mVoucher;
    private TextView mVoucherTitle;
    private TextView tvDate, etGST, etIGST, etCGST, etSGST, tvSGST, tvCGST, tvIGST, tvITC, tv_gst;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    AppUser appUser;
    Map mMap;
    private RelativeLayout rootLayout;
    private LinearLayout ll_submit, rootSP;
    private String amount, position, state, journalVoucherPosition, journalDiffAmount, state_for_credit;
    private Spinner spChooseGoods;
    private String chooseGoods[] = {"Input Goods", "Input Services", "Capital Goods", "None"};
    private String itempos;
    public Boolean fromcredit;
    String id = "", title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_note_item);
        initActionBar();
        appUser = LocalRepositories.getAppUser(this);
        initView();
        initialpageSetup();
        title = "Purchase Voucher";
        mMap = new HashMap();
        tvDate.setOnClickListener(this);
        ll_submit.setOnClickListener(this);
        fromcredit = getIntent().getExtras().getBoolean("fromcredit");
        state = getIntent().getExtras().getString("state");
        state_for_credit = getIntent().getStringExtra("state_for_credit");


        if (fromcredit) {
            itempos = getIntent().getExtras().getString("pos");
            Boolean journal = getIntent().getExtras().getBoolean("journal");
            Map map;
            if (journal) {
                journalDiffAmount = getIntent().getExtras().getString("diff_amount");
                tvDiffAmount.setText(journalDiffAmount);
                map = appUser.mListMapForItemJournalVoucherNote.get(Integer.parseInt(itempos));
            } else {
                map = appUser.mListMapForItemCreditNote.get(Integer.parseInt(itempos));
                amount = getIntent().getExtras().getString("amount");
                tvDiffAmount.setText(amount);
            }
            id = (String) map.get("id");
            etIVNNo.setText((String) map.get("inv_num"));
            tvDate.setText((String) map.get("date"));
            // tvDiffAmount.setText((String)map.get("difference_amount"));
            etGST.setText((String) map.get("rate"));
            double percentage = ((Double.parseDouble(tvDiffAmount.getText().toString()) * Double.parseDouble(etGST.getText().toString())) / 100);
            double halfPer = percentage / 2.0;
            etSGST.setText(String.valueOf(halfPer));
            etCGST.setText(String.valueOf(halfPer));
            etIGST.setText(String.valueOf(percentage));
           /* etCGST.setText((String)map.get("cgst"));
            etIGST.setText((String)map.get("igst"));
            etSGST.setText((String)map.get("sgst"));*/
            // amount=(String) map.get("difference_amount");
            //journalDiffAmount=(String) map.get("difference_amount");
            // state=(String) map.get("state");
            journalVoucherPosition = ((String) map.get("gst_pos6"));
            position = ((String) map.get("sp_position"));
            String goods = ((String) map.get("spITCEligibility"));
            tvDate.setText((String) map.get("date"));

            if (journalVoucherPosition != null) {
                if (journalVoucherPosition.equals("6")) {
                    Preferences.getInstance(getApplicationContext()).setVoucher_name((String) map.get("purchase_name"));
                    Preferences.getInstance(getApplicationContext()).setVoucher_id((String) map.get("purchase_id"));
                }
            }
            if (position != null) {
                if (position.equals("2")) {
                    Preferences.getInstance(getApplicationContext()).setVoucher_name((String) map.get("purchase_name"));
                    Preferences.getInstance(getApplicationContext()).setVoucher_id((String) map.get("purchase_id"));
                    tvITC.setVisibility(View.VISIBLE);
                    String group_type = goods.trim();
                    int groupindex = -1;
                    for (int i = 0; i < chooseGoods.length; i++) {
                        if (chooseGoods[i].equals(group_type)) {
                            groupindex = i;
                            break;
                        }
                    }
                    spChooseGoods.setSelection(groupindex);
                } else {
                    Preferences.getInstance(getApplicationContext()).setVoucher_name((String) map.get("sale_name"));
                    Preferences.getInstance(getApplicationContext()).setVoucher_id((String) map.get("sale_id"));
                    tvITC.setVisibility(View.GONE);
                }
            } else {
                tvITC.setVisibility(View.VISIBLE);
                String group_type = goods.trim();
                int groupindex = -1;
                for (int i = 0; i < chooseGoods.length; i++) {
                    if (chooseGoods[i].equals(group_type)) {
                        groupindex = i;
                        break;
                    }
                }
                spChooseGoods.setSelection(groupindex);

            }

        } else {
            amount = getIntent().getExtras().getString("amount");
            tvDiffAmount.setText(amount);
            etGST.setText("0.0");
            etIGST.setText("0.0");
            etCGST.setText("0.0");
            etSGST.setText("0.0");
            position = getIntent().getExtras().getString("sp_position");
            journalVoucherPosition = getIntent().getExtras().getString("gst_pos6");
            journalDiffAmount = getIntent().getExtras().getString("diff_amount");
            state = getIntent().getExtras().getString("state");
            state_for_credit = getIntent().getExtras().getString("state_for_credit");
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            showDate(year, month + 1, day);
        }
        mVoucher.setText(Preferences.getInstance(getApplicationContext()).getVoucher_name());

     /*   if (state == null || state.equals("")) {
            state = "Haryana";

        }*/

        if (position != null) {
            if (position.equals("2")) {
                spChooseGoods.setVisibility(View.VISIBLE);
                rootSP.setVisibility(View.VISIBLE);
                tvITC.setVisibility(View.VISIBLE);
                mVoucherTitle.setText(title);
            } else {
                tvITC.setVisibility(View.GONE);
            }
        }
        if (journalVoucherPosition != null) {
            if (journalVoucherPosition.equals("6")) {
                spChooseGoods.setVisibility(View.VISIBLE);
                tvDiffAmount.setText(journalDiffAmount);
                rootSP.setVisibility(View.VISIBLE);
                tvITC.setVisibility(View.VISIBLE);
                mVoucherTitle.setText(title);
            } else {
                tvITC.setVisibility(View.GONE);
            }
        }

        if (position != null) {
            if (state.equals(appUser.company_state)) {
                tvCGST.setVisibility(View.VISIBLE);
                etCGST.setVisibility(View.VISIBLE);
                tvSGST.setVisibility(View.VISIBLE);
                etCGST.setVisibility(View.VISIBLE);
                etSGST.setVisibility(View.VISIBLE);
                tvIGST.setVisibility(View.GONE);
                etIGST.setVisibility(View.GONE);
                tv_gst.setText("GST %");

            } else {
                tvCGST.setVisibility(View.GONE);
                etCGST.setVisibility(View.GONE);
                tvSGST.setVisibility(View.GONE);
                etCGST.setVisibility(View.GONE);
                etSGST.setVisibility(View.GONE);
                tvIGST.setVisibility(View.VISIBLE);
                etIGST.setVisibility(View.VISIBLE);
                tv_gst.setText("IGST %");
            }
        } else {
            if ((state != null && !state.equals("") && (appUser.account_name_debit_name.equals("CREDIT NOTE")))) {
                if ((state_for_credit != null && !state_for_credit.equals("")) && state_for_credit.equals(appUser.company_state)) {
                    tvCGST.setVisibility(View.VISIBLE);
                    etCGST.setVisibility(View.VISIBLE);
                    tvSGST.setVisibility(View.VISIBLE);
                    etCGST.setVisibility(View.VISIBLE);
                    etSGST.setVisibility(View.VISIBLE);
                    tvIGST.setVisibility(View.GONE);
                    etIGST.setVisibility(View.GONE);
                    tv_gst.setText("GST %");
                } else {
                    tvCGST.setVisibility(View.GONE);
                    etCGST.setVisibility(View.GONE);
                    tvSGST.setVisibility(View.GONE);
                    etCGST.setVisibility(View.GONE);
                    etSGST.setVisibility(View.GONE);
                    tvIGST.setVisibility(View.VISIBLE);
                    etIGST.setVisibility(View.VISIBLE);
                    tv_gst.setText("IGST %");
                }
            } else if ((state_for_credit != null && !state_for_credit.equals("") && (appUser.account_name_credit_name.equals("CREDIT NOTE")))) {
                if ((state != null && !state.equals("")) && state.equals(appUser.company_state)) {
                    tvCGST.setVisibility(View.VISIBLE);
                    etCGST.setVisibility(View.VISIBLE);
                    tvSGST.setVisibility(View.VISIBLE);
                    etCGST.setVisibility(View.VISIBLE);
                    etSGST.setVisibility(View.VISIBLE);
                    tvIGST.setVisibility(View.GONE);
                    etIGST.setVisibility(View.GONE);
                    tv_gst.setText("GST %");
                } else {
                    tvCGST.setVisibility(View.GONE);
                    etCGST.setVisibility(View.GONE);
                    tvSGST.setVisibility(View.GONE);
                    etCGST.setVisibility(View.GONE);
                    etSGST.setVisibility(View.GONE);
                    tvIGST.setVisibility(View.VISIBLE);
                    etIGST.setVisibility(View.VISIBLE);
                    tv_gst.setText("IGST %");
                }
            } else {
                if ((state != null && !state.equals("")) && state.equals(appUser.company_state)) {
                    tvCGST.setVisibility(View.VISIBLE);
                    etCGST.setVisibility(View.VISIBLE);
                    tvSGST.setVisibility(View.VISIBLE);
                    etCGST.setVisibility(View.VISIBLE);
                    etSGST.setVisibility(View.VISIBLE);
                    tvIGST.setVisibility(View.GONE);
                    etIGST.setVisibility(View.GONE);
                    tv_gst.setText("GST %");
                } else if ((state_for_credit != null && !state_for_credit.equals("")) && state_for_credit.equals(appUser.company_state)) {
                    tvCGST.setVisibility(View.VISIBLE);
                    etCGST.setVisibility(View.VISIBLE);
                    tvSGST.setVisibility(View.VISIBLE);
                    etCGST.setVisibility(View.VISIBLE);
                    etSGST.setVisibility(View.VISIBLE);
                    tvIGST.setVisibility(View.GONE);
                    etIGST.setVisibility(View.GONE);
                    tv_gst.setText("GST %");
                } else {
                    tvCGST.setVisibility(View.GONE);
                    etCGST.setVisibility(View.GONE);
                    tvSGST.setVisibility(View.GONE);
                    etCGST.setVisibility(View.GONE);
                    etSGST.setVisibility(View.GONE);
                    tvIGST.setVisibility(View.VISIBLE);
                    etIGST.setVisibility(View.VISIBLE);
                    tv_gst.setText("IGST %");
                }
            }
        }

        mVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(getApplicationContext());
                if (position != null) {
                    if (position.equals("2")) {
                        Intent intent = new Intent(getApplicationContext(), GetPurchaseListActivity.class);
                        intent.putExtra("purchase_return", true);
                        startActivityForResult(intent, 1);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), GetSaleVoucherListActivity.class);
                        intent.putExtra("sale_return", true);
                        startActivityForResult(intent, 1);
                    }
                }
                if (journalVoucherPosition != null) {
                    if (journalVoucherPosition.equals("6")) {
                        Intent intent = new Intent(getApplicationContext(), GetPurchaseListActivity.class);
                        intent.putExtra("purchase_return", true);
                        startActivityForResult(intent, 1);
                    }
                }
            }
        });


        tvDiffAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 == 0) {
                    // tvDiffAmount.setText("0.0");
                    etGST.setText("0.0");
                    etIGST.setText("0.0");
                    etCGST.setText("0.0");
                    etSGST.setText("0.0");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etGST.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etIGST.setText("0.0");
                    etCGST.setText("0.0");
                    etSGST.setText("0.0");
                }
                if (s.length() > 0) {
                    Double amount = 0.0, gst = 0.0;
                    if (!tvDiffAmount.getText().toString().equals("")) {
                        amount = Double.parseDouble(tvDiffAmount.getText().toString());
                    }
                    if (!etGST.getText().toString().equals("")) {
                        gst = Double.parseDouble(etGST.getText().toString());
                    }
                    double percentage = ((amount * gst) / 100);
                    etIGST.setText(String.valueOf(percentage));
                    double halfIC = (float) (percentage / 2.0);
                    etCGST.setText(String.valueOf(halfIC));
                    etSGST.setText(String.valueOf(halfIC));
                } else if (s.length() <= 0) {
                    etSGST.setText("");
                    etCGST.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvDiffAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!tvDiffAmount.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(tvDiffAmount.getText().toString());
                        if (aDouble == 0) {
                            tvDiffAmount.setText("");
                        }
                    }
                }
            }
        });

        etGST.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!etGST.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(etGST.getText().toString());
                        if (aDouble == 0) {
                            etGST.setText("");
                        }
                    }
                }
            }
        });

        etIGST.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!etIGST.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(etIGST.getText().toString());
                        if (aDouble == 0) {
                            etIGST.setText("");
                        }
                    }
                }
            }
        });

        etCGST.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!etCGST.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(etCGST.getText().toString());
                        if (aDouble == 0) {
                            etCGST.setText("");
                        }
                    }
                }
            }
        });

        etSGST.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!etSGST.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(etSGST.getText().toString());
                        if (aDouble == 0) {
                            etSGST.setText("");
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        rootLayout = (RelativeLayout) findViewById(R.id.rl_add_credit_note_item);
        etIVNNo = (EditText) findViewById(R.id.et_invoice);
        etCGST = (TextView) findViewById(R.id.et_cgst);
        etGST = (EditText) findViewById(R.id.et_gst);
        tvDiffAmount = (TextView) findViewById(R.id.et_difference_amount);
        tvSGST = (TextView) findViewById(R.id.tv_sgst);
        tvCGST = (TextView) findViewById(R.id.tv_cgst);
        tvIGST = (TextView) findViewById(R.id.tv_igst);
        etSGST = (TextView) findViewById(R.id.et_sgst);
        etIGST = (TextView) findViewById(R.id.et_igst);
        tvITC = (TextView) findViewById(R.id.tv_itc);
        tv_gst = (TextView) findViewById(R.id.tv_gst);
        spChooseGoods = (Spinner) findViewById(R.id.sp_choose_goods);
        tvDate = (TextView) findViewById(R.id.tv_date_select);
        ll_submit = (LinearLayout) findViewById(R.id.tv_submit);
        rootSP = (LinearLayout) findViewById(R.id.root_sp);
        mVoucher = (TextView) findViewById(R.id.voucher);
        mVoucherTitle = (TextView) findViewById(R.id.voucher_title);
    }

    private void initialpageSetup() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, chooseGoods);
        spChooseGoods.setAdapter(arrayAdapter);
    }

    // set action bar title and layout here
    private void initActionBar() {
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
        actionbarTitle.setText("Create Credit Note Item");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_submit:
                if (position != null) {
                    if (position.equals("1")) {
                        spChooseGoods.setVisibility(View.INVISIBLE);
                        if (!etIVNNo.getText().toString().equals("")) {
                            if (!etGST.getText().toString().equals("")) {
                                if (!tvDate.getText().toString().equals("")) {
                                    mMap.put("id", id);
                                    mMap.put("inv_num", etIVNNo.getText().toString());
                                    mMap.put("difference_amount", tvDiffAmount.getText().toString());
                                    mMap.put("rate", etGST.getText().toString());

                                    if ((state!=null && !state.equals("")) && (state_for_credit!=null && !state_for_credit.equals(""))){
                                        if (state.equals(appUser.company_state)) {
                                            mMap.put("cgst", etCGST.getText().toString());
                                            mMap.put("sgst", etSGST.getText().toString());
                                        }else if(state_for_credit.equals(appUser.company_state)) {
                                            mMap.put("cgst", etCGST.getText().toString());
                                            mMap.put("sgst", etSGST.getText().toString());
                                        } else{
                                            mMap.put("igst", etIGST.getText().toString());
                                        }
                                    }else if(state!=null && !state.equals("")){
                                        if (state.equals(appUser.company_state)) {
                                            mMap.put("cgst", etCGST.getText().toString());
                                            mMap.put("sgst", etSGST.getText().toString());
                                        }else {
                                            mMap.put("igst", etIGST.getText().toString());
                                        }
                                    }else if(state_for_credit!=null && !state_for_credit.equals("")) {
                                        if (state_for_credit.equals(appUser.company_state)) {
                                            mMap.put("cgst", etCGST.getText().toString());
                                            mMap.put("sgst", etSGST.getText().toString());
                                        } else {
                                            mMap.put("igst", etIGST.getText().toString());
                                        }
                                    }else {
                                        mMap.put("igst", etIGST.getText().toString());
                                    }

                                    mMap.put("date", tvDate.getText().toString());
                                    mMap.put("spITCEligibility", "");
                                    mMap.put("state", state);
                                    mMap.put("sp_position", position);
                                    mMap.put("amount", amount);
                                    mMap.put("date", tvDate.getText().toString());
                                    mMap.put("sale_name", Preferences.getInstance(getApplicationContext()).getVoucher_name());
                                    mMap.put("sale_id", Preferences.getInstance(getApplicationContext()).getVoucher_id());
                                    if (!fromcredit) {
                                        appUser.mListMapForItemCreditNote.add(mMap);
                                        LocalRepositories.saveAppUser(this, appUser);
                                    } else {
                                        appUser.mListMapForItemCreditNote.remove(Integer.parseInt(itempos));
                                        appUser.mListMapForItemCreditNote.add(Integer.parseInt(itempos), mMap);
                                        LocalRepositories.saveAppUser(this, appUser);
                                    }
                                    Intent intent = new Intent(this, AddCreditNoteItemActivity.class);
                                    intent.putExtra("amount", amount);
                                    intent.putExtra("sp_position", position);
                                    intent.putExtra("state", state);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Snackbar.make(rootLayout, "please select date", Snackbar.LENGTH_LONG).show();

                                }
                            } else {
                                Snackbar.make(rootLayout, "please enter GST", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();

                        }


                    } else if (position.equals("2")) {
                        spChooseGoods.setVisibility(View.VISIBLE);
                        if (!etIVNNo.getText().toString().equals("")) {
                            if (!etGST.getText().toString().equals("")) {
                                if (!tvDate.getText().toString().equals("")) {
                                    if (!spChooseGoods.getSelectedItem().toString().equals("")) {
                                        mMap.put("id", id);
                                        mMap.put("inv_num", etIVNNo.getText().toString());
                                        mMap.put("difference_amount", tvDiffAmount.getText().toString());
                                        mMap.put("rate", etGST.getText().toString());
                                        if (state.equals(appUser.company_state)) {
                                            mMap.put("cgst", etCGST.getText().toString());
                                            mMap.put("sgst", etSGST.getText().toString());
                                        } else {
                                            mMap.put("igst", etIGST.getText().toString());
                                        }
                                        mMap.put("sp_position", position);
                                        mMap.put("date", tvDate.getText().toString());
                                        mMap.put("state", state);
                                        mMap.put("spITCEligibility", spChooseGoods.getSelectedItem().toString());
                                        mMap.put("purchase_name", Preferences.getInstance(getApplicationContext()).getVoucher_name());
                                        mMap.put("purchase_id", Preferences.getInstance(getApplicationContext()).getVoucher_id());
                                        if (!fromcredit) {
                                            appUser.mListMapForItemCreditNote.add(mMap);
                                            LocalRepositories.saveAppUser(this, appUser);

                                        } else {
                                            appUser.mListMapForItemCreditNote.remove(Integer.parseInt(itempos));
                                            appUser.mListMapForItemCreditNote.add(Integer.parseInt(itempos), mMap);
                                            LocalRepositories.saveAppUser(this, appUser);

                                        }
                                        Intent intent = new Intent(CreateCreditNoteItemActivity.this, AddCreditNoteItemActivity.class);
                                        intent.putExtra("amount", amount);
                                        intent.putExtra("sp_position", position);
                                        intent.putExtra("state", state);
                                        startActivity(intent);
                                        finish();


                                    } else {
                                        Snackbar.make(rootLayout, "please select goods", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(rootLayout, "please select date", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(rootLayout, "please enter gst", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();

                        }

                    }
                } else if (journalVoucherPosition != null) {
                    if (journalVoucherPosition.equals("6")) {
                        spChooseGoods.setVisibility(View.VISIBLE);
                        if (!etIVNNo.getText().toString().equals("")) {
                            if (!etGST.getText().toString().equals("")) {
                                if (!tvDate.getText().toString().equals("")) {
                                    if (!spChooseGoods.getSelectedItem().toString().equals("")) {
                                        mMap.put("id", id);
                                        mMap.put("inv_num", etIVNNo.getText().toString());
                                        mMap.put("difference_amount", journalDiffAmount);
                                        mMap.put("rate", etGST.getText().toString());
                                        if (state != null) {
                                            if (state.equals(appUser.company_state)) {
                                                mMap.put("cgst", etCGST.getText().toString());
                                                mMap.put("sgst", etSGST.getText().toString());
                                            } else {
                                                mMap.put("igst", etIGST.getText().toString());
                                            }
                                        }
                                        if (state_for_credit != null) {
                                            if (state_for_credit.equals(appUser.company_state)) {
                                                mMap.put("cgst", etCGST.getText().toString());
                                                mMap.put("sgst", etSGST.getText().toString());
                                            } else {
                                                mMap.put("igst", etIGST.getText().toString());
                                            }
                                        }

                                        mMap.put("gst_pos6", journalVoucherPosition);
                                        mMap.put("date", tvDate.getText().toString());
                                        mMap.put("state", state);
                                        mMap.put("state_for_credit", state_for_credit);
                                        mMap.put("spITCEligibility", spChooseGoods.getSelectedItem().toString());
                                        mMap.put("purchase_name", Preferences.getInstance(getApplicationContext()).getVoucher_name());
                                        mMap.put("purchase_id", Preferences.getInstance(getApplicationContext()).getVoucher_id());
                                        if (!fromcredit) {
                                            appUser.mListMapForItemJournalVoucherNote.add(mMap);
                                            LocalRepositories.saveAppUser(this, appUser);

                                        } else {
                                            appUser.mListMapForItemJournalVoucherNote.remove(Integer.parseInt(itempos));
                                            appUser.mListMapForItemJournalVoucherNote.add(Integer.parseInt(itempos), mMap);
                                            LocalRepositories.saveAppUser(this, appUser);

                                        }
                                        Intent intent = new Intent(CreateCreditNoteItemActivity.this, AddCreditNoteItemActivity.class);
                                        intent.putExtra("diff_amount", journalDiffAmount);
                                        intent.putExtra("gst_pos6", journalVoucherPosition);
                                        intent.putExtra("state", state);
                                        intent.putExtra("statstate_for_credite", state_for_credit);
                                        startActivity(intent);
                                        finish();


                                    } else {
                                        Snackbar.make(rootLayout, "please select goods", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(rootLayout, "please select date", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(rootLayout, "please enter gst", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();

                        }
                    }
                }
                break;

            case R.id.tv_date_select:
                showDialog(999);

                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        tvDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                Preferences.getInstance(getApplicationContext()).setVoucher_name(result);
                Preferences.getInstance(getApplicationContext()).setVoucher_id(id);
                mVoucher.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //
            }
        }
    }
}
