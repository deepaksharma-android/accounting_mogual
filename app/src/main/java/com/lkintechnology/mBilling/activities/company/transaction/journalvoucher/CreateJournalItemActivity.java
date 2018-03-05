package com.lkintechnology.mBilling.activities.company.transaction.journalvoucher;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateJournalItemActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.et_ivn_no)
    EditText etIVNNo;
    @Bind(R.id.tv_submit)
    LinearLayout llSubmit;
    @Bind(R.id.sp_rcn_nature)
    Spinner spRCNNature;
    @Bind(R.id.ll_party_name)
    LinearLayout llPartyName;
    @Bind(R.id.ll_account_name)
    LinearLayout llAccountName;
    @Bind(R.id.tv_account_name)
    TextView tvAccountName;
    @Bind(R.id.tv_party_name)
    TextView tvPartyName;
    @Bind(R.id.et_diff_amount)
    EditText etDiffAmount;
    @Bind(R.id.et_rate)
    EditText etRate;
    @Bind(R.id.tv_cgst)
    TextView tvCGST;
    @Bind(R.id.tv_igst)
    TextView tvIGST;
    @Bind(R.id.rl_journal_voucher_root)
    LinearLayout rootLayout;
    @Bind(R.id.tv_sgst)
    TextView tvSgst;
    @Bind(R.id.sp_itc_eligibility)
    Spinner spITCEligibility;


    private String chooseGoods[] = {"ITC Eligibility", " Input Goods", "Input Services", "Capital Goods", "None"};
    private String chooseRCN[] = {"Choose RCN", "Based on daily limit", " Compulsary (Reg.Dealer)", "Compulsary (UnReg.Dealer)", "Service Import"};
    AppUser appUser;
    Map mMap;
    private String spPos1, spPos2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_journal_item);
        ButterKnife.bind(this);
        mMap = new HashMap();
        appUser = LocalRepositories.getAppUser(getApplicationContext());
        setActionBarLayout();
        initialpageSetup();
        spPos1 = getIntent().getStringExtra("pos1");
        spPos2 = getIntent().getStringExtra("pos2");
        llSubmit.setOnClickListener(this);
        etRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    double percentage=((Double.parseDouble(etDiffAmount.getText().toString())*Double.parseDouble(etRate.getText().toString()))/100);
                    double halfPer=percentage/2.0;
                    tvSgst.setText(String.valueOf(halfPer));
                    tvCGST.setText(String.valueOf(halfPer));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initialpageSetup() {
        ArrayAdapter arrayAdapterChooseGoods = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, chooseGoods);
        spITCEligibility.setAdapter(arrayAdapterChooseGoods);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, chooseRCN);
        spRCNNature.setAdapter(arrayAdapter);
    }

    private void setActionBarLayout() {
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
        actionbarTitle.setText("Create Journal Item");
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
                if (spPos1.equals("1")) {
                   /* if (!etIVNNo.getText().toString().equals("")) {
                        if (!tvAccountName.getText().toString().equals("")) {
                            if (!tvPartyName.getText().toString().equals("")) {
                                if (!etDiffAmount.getText().toString().equals("")) {
                                    if (!etRate.getText().toString().equals("")) {
                                        if (!spRCNNature.getSelectedItem().toString().equals("")) {
                                            if (!spITCEligibility.getSelectedItem().toString().equals("")) {
                                                mMap.put("inv_num", etIVNNo.getText().toString());
                                                mMap.put("acount_name", tvAccountName.getText().toString());
                                                mMap.put("party_name", tvPartyName.getText().toString());
                                                mMap.put("difference_amount", etDiffAmount.getText().toString());
                                                mMap.put("rate", etRate.getText().toString());
                                                mMap.put("igst", tvIGST.getText().toString());
                                                mMap.put("cgst", tvCGST.getText().toString());
                                                mMap.put("sgst", tvSgst.getText().toString());
                                                mMap.put("spRCNItem", spRCNNature.getSelectedItem().toString());
                                                mMap.put("spITCEligibility", spITCEligibility.getSelectedItem().toString());
                                                appUser.mListMapForItemJournalVoucherNote.add(mMap);
                                                LocalRepositories.saveAppUser(this, appUser);
                                                // Intent intent = new Intent(AddCreditNoteItemActivity.this, CreditNoteItemDetailActivity.class);
                                                setResult(RESULT_OK);
                                                finish();
                                            } else {
                                                Snackbar.make(rootLayout, "please select ITC Eligibility", Snackbar.LENGTH_LONG).show();

                                            }
                                        } else {
                                            Snackbar.make(rootLayout, "please select RCN  Nature", Snackbar.LENGTH_LONG).show();

                                        }
                                    } else {
                                        Snackbar.make(rootLayout, "please enter rate", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(rootLayout, "please enter difference amount", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(rootLayout, "please select party name", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(rootLayout, "please select account name", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();

                    }*/
                    mMap.put("inv_num", etIVNNo.getText().toString());
                    mMap.put("acount_name", tvAccountName.getText().toString());
                    mMap.put("party_name", tvPartyName.getText().toString());
                    mMap.put("difference_amount", etDiffAmount.getText().toString());
                    mMap.put("rate", etRate.getText().toString());
                    mMap.put("igst", tvIGST.getText().toString());
                    mMap.put("cgst", tvCGST.getText().toString());
                    mMap.put("sgst", tvSgst.getText().toString());
                    mMap.put("spRCNItem", spRCNNature.getSelectedItem().toString());
                    mMap.put("spITCEligibility", spITCEligibility.getSelectedItem().toString());
                    appUser.mListMapForItemJournalVoucherNote.add(mMap);
                    LocalRepositories.saveAppUser(this, appUser);
                    setResult(RESULT_OK);
                    finish();

                } else if (spPos2.equals("2")) {
                    /*if (!etIVNNo.getText().toString().equals("")) {
                        if (!tvAccountName.getText().toString().equals("")) {
                            if (!tvPartyName.getText().toString().equals("")) {
                                if (!etDiffAmount.getText().toString().equals("")) {
                                    if (!etRate.getText().toString().equals("")) {
                                        if (!spRCNNature.getSelectedItem().toString().equals("")) {
                                            if (!spITCEligibility.getSelectedItem().toString().equals("")) {
                                                mMap.put("inv_num", etIVNNo.getText().toString());
                                                mMap.put("acount_name", tvAccountName.getText().toString());
                                                mMap.put("party_name", tvPartyName.getText().toString());
                                                mMap.put("difference_amount", etDiffAmount.getText().toString());
                                                mMap.put("rate", etRate.getText().toString());
                                                mMap.put("igst", tvIGST.getText().toString());
                                                mMap.put("cgst", tvCGST.getText().toString());
                                                mMap.put("sgst", tvSgst.getText().toString());
                                                mMap.put("spRCNItem", spRCNNature.getSelectedItem().toString());
                                                mMap.put("spITCEligibility", spITCEligibility.getSelectedItem().toString());
                                                appUser.mListMapForItemJournalVoucherNote.add(mMap);
                                                LocalRepositories.saveAppUser(this, appUser);
                                                // Intent intent = new Intent(AddCreditNoteItemActivity.this, CreditNoteItemDetailActivity.class);
                                                setResult(RESULT_OK);
                                                finish();
                                            } else {
                                                Snackbar.make(rootLayout, "please select ITC Eligibility", Snackbar.LENGTH_LONG).show();

                                            }
                                        } else {
                                            Snackbar.make(rootLayout, "please select RCN  Nature", Snackbar.LENGTH_LONG).show();

                                        }
                                    } else {
                                        Snackbar.make(rootLayout, "please enter rate", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(rootLayout, "please enter difference amount", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(rootLayout, "please select party name", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(rootLayout, "please select account name", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();

                    }*/
                    mMap.put("inv_num", etIVNNo.getText().toString());
                    mMap.put("acount_name", tvAccountName.getText().toString());
                    mMap.put("party_name", tvPartyName.getText().toString());
                    mMap.put("difference_amount", etDiffAmount.getText().toString());
                    mMap.put("rate", etRate.getText().toString());
                    mMap.put("igst", tvIGST.getText().toString());
                    mMap.put("cgst", tvCGST.getText().toString());
                    mMap.put("sgst", tvSgst.getText().toString());
                    mMap.put("spRCNItem", spRCNNature.getSelectedItem().toString());
                    mMap.put("spITCEligibility", spITCEligibility.getSelectedItem().toString());
                    appUser.mListMapForItemJournalVoucherNote.add(mMap);
                    LocalRepositories.saveAppUser(this, appUser);
                    // Intent intent = new Intent(AddCreditNoteItemActivity.this, CreditNoteItemDetailActivity.class);
                    setResult(RESULT_OK);
                    finish();
                }
                break;
            case R.id.et_rate:

                break;
        }

    }
}
