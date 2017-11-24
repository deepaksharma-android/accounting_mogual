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
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillSundryToBeFedAsActivity extends AppCompatActivity {
    @Bind(R.id.radioGroup)
    RadioGroup mRadioGroup;
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

        if (!Preferences.getInstance(getApplicationContext()).getbill_sundry_amount_of_bill_sundry_fed_as().equals("")) {
            String radiostring = Preferences.getInstance(getApplicationContext()).getbill_sundry_amount_of_bill_sundry_fed_as();
            if (radiostring.equals("Absolute Amount")) {
                mRadioGroup.check(R.id.radioButtonAbsoluteAmount);
            } else if (radiostring.equals("Percentage")) {
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
                    startActivity(new Intent(getApplicationContext(),BillSundryFedAsPercentageActivity.class));
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
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