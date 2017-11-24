package com.berylsystems.buzz.activities.company.administration.master.billsundry;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_response.bill_sundry.EditBillSundryResponse;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PreviousBillSundryActivity extends AppCompatActivity {

    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    @Bind(R.id.spinnerConsolidatedAmount)
    Spinner mSpinnerConsolidatedAmount;
    @Bind(R.id.number_of_sundry)
    EditText mNumberOfSundry;
    @Bind(R.id.consolidated_layout)
    LinearLayout mConsolidatedLayout;
    AppUser appUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_bill_sundry);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();

        if(!Preferences.getInstance(getApplicationContext()).getbill_sundry_number_of_bill_sundry().equals("")){
            mNumberOfSundry.setText(Preferences.getInstance(getApplicationContext()).getbill_sundry_number_of_bill_sundry());
            if ((Integer.parseInt(mNumberOfSundry.getText().toString())) > 1) {
                mConsolidatedLayout.setVisibility(View.VISIBLE);
            } else {
                mConsolidatedLayout.setVisibility(View.GONE);
            }
        }
        if( Preferences.getInstance(getApplicationContext()).getbill_sundry_consolidate_bill_sundry().equals("No")){
            mSpinnerConsolidatedAmount.setSelection(1);
        }
        mNumberOfSundry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!mNumberOfSundry.getText().toString().equals("")) {
                    if ((Integer.parseInt(mNumberOfSundry.getText().toString())) > 1) {
                        mConsolidatedLayout.setVisibility(View.VISIBLE);
                    } else {
                        mConsolidatedLayout.setVisibility(View.GONE);
                    }
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mNumberOfSundry.getText().toString().equals("")) {
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_number_of_bill_sundry(mNumberOfSundry.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setbill_sundry_consolidate_bill_sundry(mSpinnerConsolidatedAmount.getSelectedItem().toString());
                    Intent intent = new Intent(getApplicationContext(), CreateBillSundryActivity.class);
                    intent.putExtra("frommbillsundrylist", false);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Number of sundries can't be zero", Toast.LENGTH_LONG).show();
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