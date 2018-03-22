package com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateDebitNoteItemActivity extends AppCompatActivity implements View.OnClickListener{
   private EditText etIVNNo;
    private TextView tvSubmit,tvDate,etGST,etIGST,etCGST,etSGST,tvSGST,tvCGST,tvIGST,etDifferenceAmount,tvITC,tv_gst;
    private DatePicker datePicker;
    private Calendar calendar;
    private RelativeLayout rootLayout;
    private int year, month, day;
    String id="";


     Map mMap;
     AppUser appUser;
     String amount,spGoodsKey1,state,journalVoucherPosition,journalVoucherDiffAmount;
    private Spinner spChooseGoods;
    private LinearLayout ll_submit,rootSP;
    private double percentage,halfIC;
    private String chooseGoods[]={" Input Goods","Input Services","Capital Goods","None"};

    public String itempos;
    public Boolean fromdebit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_note_item);
        initView();
        initActionbarSetup();

        initialpageSetup();
        mMap=new HashMap();
        tvDate.setOnClickListener(this);
        ll_submit.setOnClickListener(this);
        fromdebit=getIntent().getExtras().getBoolean("fromdebit");
        if(fromdebit){
            itempos=getIntent().getExtras().getString("pos");
            Boolean journal=getIntent().getExtras().getBoolean("journal");
            Map map;
            if(journal){
                map=appUser.mListMapForItemJournalVoucherNote.get(Integer.parseInt(itempos));
            }
            else{
                map=appUser.mListMapForItemDebitNote.get(Integer.parseInt(itempos));
            }
            id=(String)map.get("id");
            etIVNNo.setText((String)map.get("inv_num"));
            tvDate.setText((String)map.get("date"));
            etDifferenceAmount.setText((String)map.get("difference_amount"));
            etGST.setText((String)map.get("gst"));
             etCGST.setText((String)map.get("cgst"));
            etIGST.setText((String)map.get("igst"));
            etSGST.setText((String)map.get("sgst"));
            amount=(String) map.get("difference_amount");
            journalVoucherDiffAmount=(String) map.get("difference_amount");
            state=(String) map.get("state");
            journalVoucherPosition=((String)map.get("gst_pos7"));
            spGoodsKey1=((String)map.get("sp_position"));
            String goods=((String)map.get("goodsItem"));
            tvDate.setText((String)map.get("date"));
            if(spGoodsKey1!=null) {
                if (spGoodsKey1.equals("2")) {
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
                else{
                    tvITC.setVisibility(View.GONE);
                }
            }
            else {
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

        }else {
            spGoodsKey1=getIntent().getStringExtra("sp_position");
            journalVoucherPosition=getIntent().getExtras().getString("gst_pos7");
            amount=getIntent().getStringExtra("amount");
            journalVoucherDiffAmount=getIntent().getExtras().getString("diff_amount");
            if (journalVoucherPosition!=null){
                etDifferenceAmount.setText(journalVoucherDiffAmount);
            }else {
                etDifferenceAmount.setText(amount);
            }

            state=getIntent().getExtras().getString("state");
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            showDate(year, month+1, day);
        }
        if(state==null){
            state="Haryana";
        }

        if (spGoodsKey1!=null) {
            if (spGoodsKey1.equals("2")) {
                spChooseGoods.setVisibility(View.VISIBLE);
                rootSP.setVisibility(View.VISIBLE);
                tvITC.setVisibility(View.VISIBLE);
            } else {
                tvITC.setVisibility(View.GONE);
            }
        }
        if (journalVoucherPosition!=null) {
            if (journalVoucherPosition.equals("7")) {
                spChooseGoods.setVisibility(View.VISIBLE);
                etDifferenceAmount.setText(journalVoucherDiffAmount);
                rootSP.setVisibility(View.VISIBLE);
                tvITC.setVisibility(View.VISIBLE);
            }
            else{
                tvITC.setVisibility(View.GONE);
            }
        }
        if(state.equals(appUser.company_state)){
            tvCGST.setVisibility(View.VISIBLE);
            etCGST.setVisibility(View.VISIBLE);
            tvSGST.setVisibility(View.VISIBLE);
            etCGST.setVisibility(View.VISIBLE);
            etSGST.setVisibility(View.VISIBLE);
            tvIGST.setVisibility(View.GONE);
            etIGST.setVisibility(View.GONE);
            tv_gst.setText("GST %");

        }
        else{
            tvCGST.setVisibility(View.GONE);
            etCGST.setVisibility(View.GONE);
            tvSGST.setVisibility(View.GONE);
            etCGST.setVisibility(View.GONE);
            etSGST.setVisibility(View.GONE);
            tvIGST.setVisibility(View.VISIBLE);
            etIGST.setVisibility(View.VISIBLE);
            tv_gst.setText("IGST %");
        }

        etGST.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    percentage= (Double.parseDouble(etDifferenceAmount.getText().toString())*Double.parseDouble(etGST.getText().toString())/100);
                    etIGST.setText(String.valueOf(percentage));
                    halfIC= (float) (percentage/2.0);
                    etCGST.setText(String.valueOf(halfIC));
                    etSGST.setText(String.valueOf(halfIC));
                }else if (s.length()<=0){
                    etSGST.setText("");
                    etCGST.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initialpageSetup() {
        appUser = LocalRepositories.getAppUser(this);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,chooseGoods);
        spChooseGoods.setAdapter(arrayAdapter);
        spChooseGoods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
  // to define id here
    private void initView() {
        rootLayout= (RelativeLayout) findViewById(R.id.rl_add_credit_note_item);
        etIVNNo= (EditText) findViewById(R.id.et_invoice);
        etCGST= (TextView) findViewById(R.id.et_cgst);
        etGST= (EditText) findViewById(R.id.et_gst);
        etDifferenceAmount= (TextView) findViewById(R.id.et_difference_amount);
        tvSGST= (TextView) findViewById(R.id.tv_sgst);
        tvCGST= (TextView) findViewById(R.id.tv_cgst);
        tvIGST= (TextView) findViewById(R.id.tv_igst);
        etSGST= (TextView) findViewById(R.id.et_sgst);
        tvITC= (TextView) findViewById(R.id.tv_itc);
        tv_gst= (TextView) findViewById(R.id.tv_gst);
        etIGST= (TextView) findViewById(R.id.et_igst);
        spChooseGoods= (Spinner) findViewById(R.id.sp_choose_goods);
        tvDate= (TextView) findViewById(R.id.tv_date_select);
        ll_submit= (LinearLayout) findViewById(R.id.tv_submit);
        rootSP= (LinearLayout) findViewById(R.id.root_sp);

    }

    //  add action bar title here
    private void initActionbarSetup() {
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
        actionbarTitle.setText("Create Debit Note Item");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_submit:
                if (spGoodsKey1!=null) {
                    if (spGoodsKey1.equals("1")) {
                        spChooseGoods.setVisibility(View.INVISIBLE);
                        if (!etIVNNo.getText().toString().equals("")) {
                            if (!etGST.getText().toString().equals("")) {
                                if (!tvDate.getText().toString().equals("")) {
                                    mMap.put("id",id);
                                    mMap.put("inv_num", etIVNNo.getText().toString());
                                    mMap.put("difference_amount", etDifferenceAmount.getText().toString());
                                    mMap.put("gst", etGST.getText().toString());
                                    if (state.equals(appUser.company_state)) {
                                        mMap.put("cgst", etCGST.getText().toString());
                                        mMap.put("sgst", etSGST.getText().toString());
                                        mMap.put("state", state);
                                    } else {
                                        mMap.put("igst", etIGST.getText().toString());
                                        mMap.put("state", state);
                                    }
                                    mMap.put("date", tvDate.getText().toString());
                                    mMap.put("goodsItem", "");
                                    mMap.put("sp_position",spGoodsKey1);
                                    mMap.put("date",tvDate.getText().toString());
                                    mMap.put("state",state);
                                    if (!fromdebit) {
                                        appUser.mListMapForItemDebitNote.add(mMap);
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    } else {
                                        appUser.mListMapForItemDebitNote.remove(Integer.parseInt(itempos));
                                        appUser.mListMapForItemDebitNote.add(Integer.parseInt(itempos), mMap);
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    }
                                    Intent intent = new Intent(CreateDebitNoteItemActivity.this, AddDebitNoteItemActivity.class);
                                    intent.putExtra("amount",amount);
                                    intent.putExtra("sp_position",spGoodsKey1);
                                    intent.putExtra("state",state);
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


                    } else if (spGoodsKey1.equals("2")) {
                        spChooseGoods.setVisibility(View.VISIBLE);
                        if (!etIVNNo.getText().toString().equals("")) {
                            if (!etGST.getText().toString().equals("")) {
                                if (!tvDate.getText().toString().equals("")) {
                                    if (!spChooseGoods.getSelectedItem().toString().equals("")) {
                                        mMap.put("id",id);
                                        mMap.put("inv_num", etIVNNo.getText().toString());
                                        mMap.put("difference_amount", etDifferenceAmount.getText().toString());
                                        mMap.put("gst", etGST.getText().toString());
                                        if (state.equals(appUser.company_state)) {
                                            mMap.put("cgst", etCGST.getText().toString());
                                            mMap.put("sgst", etSGST.getText().toString());
                                            mMap.put("state", state);
                                        } else {
                                            mMap.put("igst", etIGST.getText().toString());
                                            mMap.put("state", state);
                                        }
                                        mMap.put("date", tvDate.getText().toString());
                                        mMap.put("goodsItem", spChooseGoods.getSelectedItem().toString());
                                        mMap.put("sp_position",spGoodsKey1);
                                        mMap.put("date",tvDate.getText().toString());
                                        mMap.put("state",state);
                                        if (!fromdebit) {
                                            appUser.mListMapForItemDebitNote.add(mMap);
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        } else {
                                            appUser.mListMapForItemDebitNote.remove(Integer.parseInt(itempos));
                                            appUser.mListMapForItemDebitNote.add(Integer.parseInt(itempos), mMap);
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        }
                                        Intent intent = new Intent(CreateDebitNoteItemActivity.this, AddDebitNoteItemActivity.class);
                                        intent.putExtra("amount", amount);
                                        intent.putExtra("sp_position", spGoodsKey1);
                                        intent.putExtra("state", state);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Snackbar.make(rootLayout, "please choose goods item", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(rootLayout, "please select date", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(rootLayout, "please enter GST", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();
                        }


                    }
                }else if (journalVoucherPosition!=null){
                    if (journalVoucherPosition.equals("7")){
                        spChooseGoods.setVisibility(View.VISIBLE);
                        if (!etIVNNo.getText().toString().equals("")) {
                            if (!etGST.getText().toString().equals("")) {
                                if (!tvDate.getText().toString().equals("")) {
                                    if (!spChooseGoods.getSelectedItem().toString().equals("")) {
                                        mMap.put("id",id);
                                        mMap.put("inv_num", etIVNNo.getText().toString());
                                        mMap.put("difference_amount", etDifferenceAmount.getText().toString());
                                        mMap.put("gst", etGST.getText().toString());
                                        if (state.equals(appUser.company_state)) {
                                            mMap.put("cgst", etCGST.getText().toString());
                                            mMap.put("sgst", etSGST.getText().toString());
                                            mMap.put("state", state);
                                        } else {
                                            mMap.put("igst", etIGST.getText().toString());
                                            mMap.put("state", state);
                                        }
                                        mMap.put("date", tvDate.getText().toString());
                                        mMap.put("goodsItem", spChooseGoods.getSelectedItem().toString());
                                        mMap.put("gst_pos7", journalVoucherPosition);
                                        mMap.put("date",tvDate.getText().toString());
                                        mMap.put("state",state);
                                        if (!fromdebit) {
                                            appUser.mListMapForItemJournalVoucherNote.add(mMap);
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        } else {
                                            appUser.mListMapForItemJournalVoucherNote.remove(Integer.parseInt(itempos));
                                            appUser.mListMapForItemJournalVoucherNote.add(Integer.parseInt(itempos), mMap);
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        }
                                        Intent intent = new Intent(CreateDebitNoteItemActivity.this, AddDebitNoteItemActivity.class);
                                        intent.putExtra("diff_amount", journalVoucherDiffAmount);
                                        intent.putExtra("gst_pos7", journalVoucherPosition);
                                        intent.putExtra("state", state);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Snackbar.make(rootLayout, "please choose goods item", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(rootLayout, "please select date", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(rootLayout, "please enter GST", Snackbar.LENGTH_LONG).show();
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
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        tvDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

}
