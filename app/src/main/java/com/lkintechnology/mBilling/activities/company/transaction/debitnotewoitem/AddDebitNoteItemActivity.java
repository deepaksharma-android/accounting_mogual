package com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class AddDebitNoteItemActivity extends AppCompatActivity implements View.OnClickListener{
   private EditText etIVNNo,etDifferenceAmount,etGST,etIGST,etCGST,etSGST;
   private TextView tvSubmit,tvDate;
     DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
     Map mMap;
     AppUser appUser;
    String amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debit_note_item);
        initActionbarSetup();
        initView();
        initialpageSetup();
        tvDate.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        amount=getIntent().getStringExtra("amount");
        etDifferenceAmount.setText(amount);
    }


    private void initialpageSetup() {
    }
  // to define id here
    private void initView() {
        etIVNNo= (EditText) findViewById(R.id.et_invoice);
        etCGST= (EditText) findViewById(R.id.et_cgst);
        etGST= (EditText) findViewById(R.id.et_gst);
        etSGST= (EditText) findViewById(R.id.et_sgst);
        etIGST= (EditText) findViewById(R.id.et_igst);

        tvDate= (TextView) findViewById(R.id.tv_date_select);
        tvSubmit= (TextView) findViewById(R.id.tv_submit);
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
                mMap.put("inv_num",etIVNNo.getText().toString());
                mMap.put("difference_amount",etDifferenceAmount.getText().toString());
                mMap.put("gst",etGST.getText().toString());
                mMap.put("igst",etIGST.getText().toString());
                mMap.put("cgst",etCGST.getText().toString());
                mMap.put("sgst",etSGST.getText().toString());
                mMap.put("date",tvDate.getText().toString());
                appUser.mListMapForItemDebitNote.add(mMap);
                LocalRepositories.saveAppUser(this,appUser);

                break;
            case R.id.tv_date_select:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);

                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                showDate(year, month+1, day);

                break;
        }

    }



    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
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
