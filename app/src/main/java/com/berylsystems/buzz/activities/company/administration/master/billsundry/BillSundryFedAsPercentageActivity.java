package com.berylsystems.buzz.activities.company.administration.master.billsundry;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillSundryFedAsPercentageActivity extends AppCompatActivity {
    @Bind(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    AppUser appUser;
    String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_sundry_fed_as_percentage);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        if (!appUser.bill_sundry_of_percentage.equals("")) {
            String radiostringpercentage = appUser.bill_sundry_of_percentage;

            if(radiostringpercentage.equals("Nett Bill Amount")) {
                mRadioGroup.check(R.id.radioButtonNetBillAmount);
            }
            else if(radiostringpercentage.equals("Item Basic Amt.")) {
                mRadioGroup.check(R.id.radioButtonBasicAmount);
            }
            else if(radiostringpercentage.equals("Total MRP of Items")) {
                mRadioGroup.check(R.id.radioButtonMrp);
            }
            else if(radiostringpercentage.equals("Total MRP of Items")) {
                mRadioGroup.check(R.id.radioButtonMrp);
            }
            else if(radiostringpercentage.equals("Taxable Amount")) {
                mRadioGroup.check(R.id.radioButtonTaxAmount);
            }
            else if(radiostringpercentage.equals("Previous Bill Sundry(s) Amount")) {
                mRadioGroup.check(R.id.radioButtonPreviousAmount);

            }
            else if(radiostringpercentage.equals("Other Bill Sundry")) {
                mRadioGroup.check(R.id.radioButtonOther);

            }


        }
       /* mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                value = radioButton.getText().toString();



            }
        });*/
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                value= radioButton.getText().toString();
                appUser.bill_sundry_of_percentage =value;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                if (value.equals("Other Bill Sundry")) {
                    startActivity(new Intent(getApplicationContext(),OtherBillSundryActivity.class));
                } else if (value.equals("Previous Bill Sundry(s) Amount")) {
                    startActivity(new Intent(getApplicationContext(),PreviousBillSundryActivity.class));
                }
                else{
                    Intent intent=new Intent(getApplicationContext(),CreateBillSundryActivity.class);
                    intent.putExtra("frommbillsundrylist",false);
                    startActivity(intent);
                }
            }
        });
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
        actionbarTitle.setText("BILL SUNDRY FED AS PERCENTAGE");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}