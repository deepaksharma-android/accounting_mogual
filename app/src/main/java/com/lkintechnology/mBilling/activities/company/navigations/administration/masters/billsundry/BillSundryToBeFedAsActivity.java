package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.billsundry;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillSundryToBeFedAsActivity extends AppCompatActivity {
    @Bind(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.percentagetext)
    EditText mPercentage;
    @Bind(R.id.percentage_layout)
    LinearLayout mPercentageLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    AppUser appUser;
    String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_bill_sundry_fed_as);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String val=radioGroup.toString();
                if(i==R.id.radioButtonPercentage){
                    mPercentageLayout.setVisibility(View.VISIBLE);
                }
                else{
                    mPercentageLayout.setVisibility(View.GONE);
                    mPercentage.setText("");
                }
            }
        });

        if (!Preferences.getInstance(getApplicationContext()).getbill_sundry_amount_of_bill_sundry_fed_as().equals("")) {
            String radiostring = Preferences.getInstance(getApplicationContext()).getbill_sundry_amount_of_bill_sundry_fed_as();
            if (radiostring.equals("Absolute Amount")) {
                mRadioGroup.check(R.id.radioButtonAbsoluteAmount);
            } else if (radiostring.equals("Percentage")) {
                mPercentageLayout.setVisibility(View.VISIBLE);
                mPercentage.setText(Preferences.getInstance(getApplicationContext()).getbill_sundry_amount_of_bill_sundry_fed_as_percent());
                mRadioGroup.check(R.id.radioButtonPercentage);
            } else if (radiostring.equals("Per Main Qty.")) {
                mRadioGroup.check(R.id.radioButtonPerMainQty);
            } else if (radiostring.equals("Per Alt. Qty.")) {
                mRadioGroup.check(R.id.radioButtonPerAltQty);
            } else if (radiostring.equals("Per Packaging Qty.")) {
                mRadioGroup.check(R.id.radioButtonPerPackagingQty);
            }

        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                value= radioButton.getText().toString();
                Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_of_bill_sundry_fed_as(value);
                if (value.equals("Percentage")) {
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_of_bill_sundry_fed_as_percent(mPercentage.getText().toString());
                    Intent intent=new Intent(getApplicationContext(),BillSundryFedAsPercentageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else{
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_calculated_on("");
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_of_percentage("");
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_number_of_bill_sundry("");
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_consolidate_bill_sundry("");
                    finish();
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("BILL SUNDRY FED AS");
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