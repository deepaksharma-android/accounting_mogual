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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem.AddCreditNoteItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem.CreditNoteItemDetailActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddDebitNoteItemActivity extends AppCompatActivity implements View.OnClickListener{
   private EditText etIVNNo,etDifferenceAmount,etGST,etIGST,etCGST,etSGST;
   private TextView tvDate;
    private DatePicker datePicker;
    private Calendar calendar;
    private RelativeLayout rootLayout;
    private int year, month, day;

     Map mMap;
     AppUser appUser;
     String amount,spGoodsKey1;
    private Spinner spChooseGoods;
    private LinearLayout ll_submit,rootSP;
    private double percentage,halfIC;
    private String chooseGoods[]={"ITC Eligibility"," Input Goods","Input Services","Capital Goods","None"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debit_note_item);
        initActionbarSetup();
        initView();
        initialpageSetup();
        mMap=new HashMap();
        tvDate.setOnClickListener(this);
        ll_submit.setOnClickListener(this);
        amount=getIntent().getStringExtra("amount");
        spGoodsKey1=getIntent().getStringExtra("spKey");
        etDifferenceAmount.setText(amount);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        if (spGoodsKey1.equals("2")){
            spChooseGoods.setVisibility(View.VISIBLE);
            rootSP.setVisibility(View.VISIBLE);
        }
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
        etGST.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              if (s.length()>0){
                percentage= (Double.parseDouble(etDifferenceAmount.getText().toString())*Double.parseDouble(etGST.getText().toString())/100);
                halfIC= (float) (percentage/2.0);
                etCGST.setText(String.valueOf(halfIC));
                  etSGST.setText(String.valueOf(halfIC));
              }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
  // to define id here
    private void initView() {
        rootLayout= (RelativeLayout) findViewById(R.id.rl_root_add_debit_note_item);
        etIVNNo= (EditText) findViewById(R.id.et_invoice);
        etCGST= (EditText) findViewById(R.id.et_cgst);
        etGST= (EditText) findViewById(R.id.et_gst);
        etDifferenceAmount= (EditText) findViewById(R.id.et_difference_amount);
        etSGST= (EditText) findViewById(R.id.et_sgst);
        etIGST= (EditText) findViewById(R.id.et_igst);
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
                if (spGoodsKey1.equals("1")){
                    spChooseGoods.setVisibility(View.INVISIBLE);
                    if (!etIVNNo.getText().toString().equals("")){
                        if (!etGST.getText().toString().equals("")){
                            if (!tvDate.getText().toString().equals("")){
                                mMap.put("inv_num", etIVNNo.getText().toString());
                                mMap.put("difference_amount", etDifferenceAmount.getText().toString());
                                mMap.put("gst", etGST.getText().toString());
                                mMap.put("igst", etIGST.getText().toString());
                                mMap.put("cgst", etCGST.getText().toString());
                                mMap.put("sgst", etSGST.getText().toString());
                                mMap.put("date", tvDate.getText().toString());
                                mMap.put("goodsItem", "");
                                appUser.mListMapForItemDebitNote.add(mMap);
                                LocalRepositories.saveAppUser(this, appUser);
                                setResult(RESULT_OK);
                                finish();
                            }else {
                                Snackbar.make(rootLayout, "please select date", Snackbar.LENGTH_LONG).show();
                            }
                        }else {
                            Snackbar.make(rootLayout, "please enter GST", Snackbar.LENGTH_LONG).show();
                        }
                    }else {
                        Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();
                    }


                   }else if (spGoodsKey1.equals("2")){
                    spChooseGoods.setVisibility(View.VISIBLE);
                    if (!etIVNNo.getText().toString().equals("")){
                        if (!etGST.getText().toString().equals("")){
                            if (!tvDate.getText().toString().equals("")){
                                if (!spChooseGoods.getSelectedItem().toString().equals("")){
                                    mMap.put("inv_num", etIVNNo.getText().toString());
                                    mMap.put("difference_amount", etDifferenceAmount.getText().toString());
                                    mMap.put("gst", etGST.getText().toString());
                                    mMap.put("igst", etIGST.getText().toString());
                                    mMap.put("cgst", etCGST.getText().toString());
                                    mMap.put("sgst", etSGST.getText().toString());
                                    mMap.put("date", tvDate.getText().toString());
                                    mMap.put("goodsItem", spChooseGoods.getSelectedItem().toString());
                                    appUser.mListMapForItemDebitNote.add(mMap);
                                    LocalRepositories.saveAppUser(this, appUser);
                                    setResult(RESULT_OK);
                                    finish();
                                }else {
                                    Snackbar.make(rootLayout, "please choose goods item", Snackbar.LENGTH_LONG).show();
                                }
                            }else {
                                Snackbar.make(rootLayout, "please select date", Snackbar.LENGTH_LONG).show();
                            }
                        }else {
                            Snackbar.make(rootLayout, "please enter GST", Snackbar.LENGTH_LONG).show();
                        }
                    }else {
                        Snackbar.make(rootLayout, "please enter invoice number", Snackbar.LENGTH_LONG).show();
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
