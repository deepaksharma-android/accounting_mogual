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
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
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
    EditText tvCGST;
    @Bind(R.id.tv_igst)
    EditText tvIGST;
    @Bind(R.id.rl_journal_voucher_root)
    LinearLayout rootLayout;
    @Bind(R.id.tv_sgst)
    EditText tvSgst;
    @Bind(R.id.sp_itc_eligibility)
    Spinner spITCEligibility;
    String account_id;
    String party_id;
   public Boolean fromjournal;
    private String itempos;


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
        spPos1 = getIntent().getStringExtra("gst_pos1");
        spPos2 = getIntent().getStringExtra("gst_pos2");
        fromjournal=getIntent().getExtras().getBoolean("fromjournal");
        llSubmit.setOnClickListener(this);
        if(fromjournal){
            itempos=getIntent().getExtras().getString("pos");
            Map map=appUser.mListMapForItemJournalVoucherNote.get(Integer.parseInt(itempos));
            String voucher_number= (String)map.get("inv_num");
            String acount_name=(String)map.get("acount_name");
            String party_name= (String)map.get("party_name");
            String difference_amount=(String)map.get("difference_amount");
            String sp1= (String)map.get("gst_pos1");
            String sp2= (String)map.get("gst_pos2");
            String rate= (String)map.get("rate");
            String igst=(String)map.get("igst");
            String cgst= (String)map.get("cgst");
            String sgst=(String)map.get("sgst");
            String spRCNItem= (String)map.get("spRCNItem");
            String ITCEligibility=(String)map.get("spITCEligibility");
            etIVNNo.setText(voucher_number);
            tvAccountName.setText(acount_name);
            account_id=(String)map.get("account_id");
            tvPartyName.setText(party_name);
            party_id=(String)map.get("party_id");
            etDiffAmount.setText(difference_amount);
            etRate.setText(rate);
            tvIGST.setText(igst);
            tvCGST.setText(cgst);
            tvSgst.setText(sgst);
            String group_type = spRCNItem.trim();
            int groupindex = -1;
            for (int i = 0; i<chooseRCN.length; i++) {
                if (chooseRCN[i].equals(group_type)) {
                    groupindex = i;
                    break;
                }
            }
            spRCNNature.setSelection(groupindex);

            String group_type_spITCEligibility = ITCEligibility.trim();
            int groupindexspITCEligibility = -1;
            for (int i = 0; i<chooseGoods.length; i++) {
                if (chooseGoods[i].equals(group_type_spITCEligibility)) {
                    groupindexspITCEligibility = i;
                    break;
                }
            }
            spITCEligibility.setSelection(groupindexspITCEligibility);
            spPos1=sp1;
            spPos2=sp2;

        }
        /*etRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    double percentage = ((Double.parseDouble(etDiffAmount.getText().toString()) * Double.parseDouble(etRate.getText().toString())) / 100);
                    double halfPer = percentage / 2.0;
                    tvSgst.setText(String.valueOf(halfPer));
                    tvCGST.setText(String.valueOf(halfPer));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        appUser.account_master_group="";
        LocalRepositories.saveAppUser(this,appUser);
        llAccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                ExpandableAccountListActivity.isDirectForAccount=false;
                startActivityForResult(intent, 1);
            }
        });
        llPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                ExpandableAccountListActivity.isDirectForAccount=false;
                startActivityForResult(intent, 2);
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
                if (spPos1 != null) {
                    if (spPos1.equals("1")) {
                        if (!etIVNNo.getText().toString().equals("")) {
                           /* if (!tvAccountName.getText().toString().equals("")) {
                                if (!tvPartyName.getText().toString().equals("")) {*/
                            if (!etDiffAmount.getText().toString().equals("")) {
                                if (!etRate.getText().toString().equals("")) {
                                    if (!spRCNNature.getSelectedItem().toString().equals("")) {
                                        if (!spITCEligibility.getSelectedItem().toString().equals("")) {
                                            mMap.put("account_id", account_id);
                                            mMap.put("party_id", party_id);
                                            mMap.put("inv_num", etIVNNo.getText().toString());
                                            mMap.put("acount_name", tvAccountName.getText().toString());
                                            mMap.put("party_name", tvPartyName.getText().toString());
                                            mMap.put("difference_amount", etDiffAmount.getText().toString());
                                            mMap.put("rate", etRate.getText().toString());
                                            mMap.put("igst", tvIGST.getText().toString());
                                            mMap.put("cgst", tvCGST.getText().toString());
                                            mMap.put("sgst", tvSgst.getText().toString());
                                            mMap.put("gst_pos1", spPos1);
                                            mMap.put("gst_pos2", spPos1);
                                            mMap.put("spRCNItem", spRCNNature.getSelectedItem().toString());
                                            mMap.put("spITCEligibility", spITCEligibility.getSelectedItem().toString());
                                            if (!fromjournal) {
                                                appUser.mListMapForItemJournalVoucherNote.add(mMap);
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            } else {
                                                appUser.mListMapForItemJournalVoucherNote.remove(Integer.parseInt(itempos));
                                                appUser.mListMapForItemJournalVoucherNote.add(Integer.parseInt(itempos), mMap);
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            }
                                            Intent intent = new Intent(getApplicationContext(), AddJournalItemActivity.class);
                                            intent.putExtra("gst_pos1", spPos1);
                                            startActivity(intent);
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
                          /*      } else {
                                    Snackbar.make(rootLayout, "please select party name", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(rootLayout, "please select account name", Snackbar.LENGTH_LONG).show();
                            }*/
                        } else {
                            Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();

                        }

                    }

                }
                if (spPos2 != null) {
                    if (spPos2.equals("2")) {
                        if (!etIVNNo.getText().toString().equals("")) {
                          /*  if (!tvAccountName.getText().toString().equals("")) {
                                if (!tvPartyName.getText().toString().equals("")) {*/
                            if (!etDiffAmount.getText().toString().equals("")) {
                                if (!etRate.getText().toString().equals("")) {
                                    if (!spRCNNature.getSelectedItem().toString().equals("")) {
                                        if (!spITCEligibility.getSelectedItem().toString().equals("")) {
                                            mMap.put("account_id", account_id);
                                            mMap.put("party_id", party_id);
                                            mMap.put("inv_num", etIVNNo.getText().toString());
                                            mMap.put("acount_name", tvAccountName.getText().toString());
                                            mMap.put("party_name", tvPartyName.getText().toString());
                                            mMap.put("difference_amount", etDiffAmount.getText().toString());
                                            mMap.put("rate", etRate.getText().toString());
                                            mMap.put("igst", tvIGST.getText().toString());
                                            mMap.put("cgst", tvCGST.getText().toString());
                                            mMap.put("sgst", tvSgst.getText().toString());
                                            mMap.put("gst_pos1", spPos1);
                                            mMap.put("gst_pos2", spPos1);
                                            mMap.put("spRCNItem", spRCNNature.getSelectedItem().toString());
                                            mMap.put("spITCEligibility", spITCEligibility.getSelectedItem().toString());
                                            if (!fromjournal) {
                                                appUser.mListMapForItemJournalVoucherNote.add(mMap);
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            } else {
                                                appUser.mListMapForItemJournalVoucherNote.remove(Integer.parseInt(itempos));
                                                appUser.mListMapForItemJournalVoucherNote.add(Integer.parseInt(itempos), mMap);
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            }
                                            // Intent intent = new Intent(AddCreditNoteItemActivity.this, CreditNoteItemDetailActivity.class);
                                            Intent intent = new Intent(getApplicationContext(), AddJournalItemActivity.class);
                                            intent.putExtra("gst_pos2", spPos2);
                                            startActivity(intent);
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
                             /*   } else {
                                    Snackbar.make(rootLayout, "please select party name", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(rootLayout, "please select account name", Snackbar.LENGTH_LONG).show();
                            }*/
                        } else {
                            Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();

                        }

                    }
                }
                break;
            case R.id.et_rate:

                break;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                String[] name = result.split(",");
                tvAccountName.setText(name[0]);
                account_id=id;

            }
            else if(requestCode == 2){
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                String[] name = result.split(",");
                tvPartyName.setText(name[0]);
                party_id=id;
            }
        }
    }
}
