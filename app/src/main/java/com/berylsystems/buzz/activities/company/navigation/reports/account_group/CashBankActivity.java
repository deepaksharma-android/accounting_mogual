package com.berylsystems.buzz.activities.company.navigation.reports.account_group;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CashBankActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.account_group_layout)
    LinearLayout account_group_layout;
    @Bind(R.id.submit_layout)
    LinearLayout submit_layout;
    @Bind(R.id.account_group_txt)
    TextView account_group_txt;
    @Bind(R.id.start_date_txt)
    TextView start_date_txt;
    @Bind(R.id.end_date_txt)
    TextView end_date_txt;


    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialogStart;
    private DatePickerDialog datePickerDialogEnd;

    AppUser appUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_bank);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);

        account_group_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUser appUser=LocalRepositories.getAppUser(getApplicationContext());
                appUser.account_master_group="Bank Accounts,Cash-in-hand";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                ExpandableAccountListActivity.isDirectForAccount=false;
                startActivityForResult(new Intent(getApplicationContext(), ExpandableAccountListActivity.class), 1);
            }
        });
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setStartDateField();
        setEndDateField();
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
        actionbarTitle.setText("CASH BANK ACTIVITY");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                //appUser.purchase_account_master_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] strArr = result.split(",");
                account_group_txt.setText(strArr[0]);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //account_group_txt.setText("");
            }
        }
    }

    private void setStartDateField() {
        start_date_txt.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();

        datePickerDialogStart= new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                start_date_txt.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setEndDateField() {
        end_date_txt.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();

        datePickerDialogEnd = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                end_date_txt.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if (view == start_date_txt) {
            datePickerDialogStart.show();
        }
        if (view == end_date_txt) {
            datePickerDialogEnd.show();
        }
    }


}
