package com.berylsystems.buzz.activities.company.transaction;

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

public class TransactionCustomerActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.account_group_layout)
    LinearLayout mAccount_group_layout;
    @Bind(R.id.account_group_textview)
    TextView mAccount_group_textview;
    @Bind(R.id.start_date_layout)
    LinearLayout mStart_date_layout;
    @Bind(R.id.end_date_layout)
    LinearLayout mEnd_date_layout;

    @Bind(R.id.start_date)
    TextView mStart_date;
    @Bind(R.id.end_date)
    TextView mEnd_date;
    AppUser appUser;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1,DatePickerDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_costomer);

        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        mStart_date.setText(dateString);
        mEnd_date.setText(dateString);

        mAccount_group_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Sundry Debtors";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == 2) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                //appUser.payment_paid_to_id = id;
                // LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                mAccount_group_textview.setText(name[0]);
            }
        }
    }

    private void setDateField() {
        mStart_date.setOnClickListener(this);
        mEnd_date.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();

        // set_date.setText("22 Nov 2017");
        // set_date_pdc.setText("22 Nov 2017");

        DatePickerDialog1 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                mStart_date.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                mEnd_date.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if (view == mStart_date) {
            DatePickerDialog1.show();
        }
        else if(view == mEnd_date){
            DatePickerDialog2.show();
        }
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
        actionbarTitle.setText("Customer");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}
