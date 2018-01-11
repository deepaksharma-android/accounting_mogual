package com.lkintechnology.mBilling.activities.company.administration.master.billsundry;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RoundOffActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    @Bind(R.id.round_off_amount_spinner)
    Spinner mSpinnerRounfOffAmount;
    @Bind(R.id.round_off_spinner)
    Spinner mSpinnerRounfOff;
    @Bind(R.id.round_off_layout)
    LinearLayout mRoundOffLayout;
    @Bind(R.id.round_off_value)
    EditText mRoundOffValue;
    AppUser appUser;
    boolean roundOffAmount =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_off);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        mSpinnerRounfOffAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    mRoundOffLayout.setVisibility(View.VISIBLE);
                    roundOffAmount=true;
                }
                else{
                    mRoundOffLayout.setVisibility(View.GONE);
                    roundOffAmount=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(!Preferences.getInstance(getApplicationContext()).getbill_sundry_rouding_off_nearest().equals("")){
            mRoundOffValue.setText(Preferences.getInstance(getApplicationContext()).getbill_sundry_rouding_off_nearest());
        }

        if(  Preferences.getInstance(getApplicationContext()).getbill_sundry_amount_round_off().equals("Yes")){
            mSpinnerRounfOffAmount.setSelection(1);
        }

        if( ! Preferences.getInstance(getApplicationContext()).getbill_sundry_rounding_off_limit().equals("")){
            String state =  Preferences.getInstance(getApplicationContext()).getbill_sundry_rounding_off_limit().trim();// insert code here
            int stateindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.round_off_spinner).length; i++) {
                if (getResources().getStringArray(R.array.round_off_spinner)[i].equals(state)) {
                    stateindex = i;
                    break;
                }
            }
            mSpinnerRounfOff.setSelection(stateindex);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roundOffAmount){
                    if(!mRoundOffValue.getText().toString().equals("")) {
                        Preferences.getInstance(getApplicationContext()).setbill_sundry_rouding_off_nearest(mRoundOffValue.getText().toString());
                        Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_round_off(mSpinnerRounfOffAmount.getSelectedItem().toString());
                        Preferences.getInstance(getApplicationContext()).setbill_sundry_rounding_off_limit(mSpinnerRounfOff.getSelectedItem().toString());
                        finish();
                    }else {
                        Snackbar.make(coordinatorLayout,"Enter Nearest Round of Amount",Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_rouding_off_nearest(mRoundOffValue.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_amount_round_off(mSpinnerRounfOffAmount.getSelectedItem().toString());
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_rounding_off_limit(mSpinnerRounfOff.getSelectedItem().toString());
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
        actionbarTitle.setText("PREVIOUS BILL SUNDRY");
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