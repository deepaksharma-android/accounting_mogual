package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.billsundry;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class OtherBillSundryActivity extends AppCompatActivity {
    @Bind(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    @Bind(R.id.spinnerCalculatedOn)
    Spinner mSpinnerCalculatedOn;
    AppUser appUser;
    String value;
    ArrayAdapter mCalculateOnAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_bill_sundry);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();

        mCalculateOnAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_billSundryName);
        mCalculateOnAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSpinnerCalculatedOn.setAdapter(mCalculateOnAdapter);



        ;

        if (!Preferences.getInstance(getApplicationContext()).getbill_sundry_calculated_on().equals("")) {
            String radiostring = Preferences.getInstance(getApplicationContext()).getbill_sundry_calculated_on();
            if (radiostring.equals("Bill Sundry Amount")) {
                mRadioGroup.check(R.id.radioButtonBillSundryAmount);
            } else if (radiostring.equals("Bill Sundry Applied On")) {
                mRadioGroup.check(R.id.radioButtonBillSundryAppliedOn);
            }

        }
        if(!Preferences.getInstance(getApplicationContext()).getcalculated_on_bill_sundry_id().equals("")){
            int groupindex = -1;
            for (int i = 0; i<appUser.arr_billSundryName.size(); i++) {
                if (appUser.arr_billSundryName.get(i).equals(Preferences.getInstance(getApplicationContext()).getcalculated_on_bill_sundry_id())) {
                    groupindex = i;
                    break;
                }
            }
            Timber.i("GROUPINDEX"+groupindex);
            mSpinnerCalculatedOn.setSelection(groupindex);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                value= radioButton.getText().toString();
                Preferences.getInstance(getApplicationContext()).setbill_sundry_calculated_on(value);
                String selected=mSpinnerCalculatedOn.getSelectedItem().toString();
                for(int i=0;i<appUser.arr_billSundryName.size();i++){
                    if(appUser.arr_billSundryName.get(i).equals(selected)){
                        String id=appUser.arr_billSundryId.get(i);
                        Preferences.getInstance(getApplicationContext()).setcalculated_on_bill_sundry_id(appUser.arr_billSundryId.get(i));
                        break;
                    }
                }
                finish();
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
        actionbarTitle.setText("BILL SUNDRY CALCULATED ON");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
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