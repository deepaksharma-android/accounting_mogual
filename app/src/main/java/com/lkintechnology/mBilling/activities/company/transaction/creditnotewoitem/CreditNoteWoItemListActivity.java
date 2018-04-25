package com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.adapters.CreditNoteListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.DeleteCreditNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.GetCreditNoteResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventClickAlertForCreditNote;
import com.lkintechnology.mBilling.utils.EventDeleteCreditNote;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreditNoteWoItemListActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    CreditNoteListAdapter mAdapter;
    @Bind(R.id.sale_type_list_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.dashboard_spinner_layout)
    LinearLayout dashboardSpinnerLayout;
    @Bind(R.id.dashboard_spinner)
    Spinner dashboardSpinner;
    ArrayList<String> cashInHand = new ArrayList<>();
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    @Bind(R.id.total)
    TextView mTotal;
    @Bind(R.id.date_from_to)
    LinearLayout date_from_to;
    @Bind(R.id.start_date)
    TextView start_date;
    @Bind(R.id.end_date)
    TextView end_date;
    public Dialog dialog;
    private DatePickerDialog DatePickerDialog1,DatePickerDialog2;
    private SimpleDateFormat dateFormatter;
    String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_note_wo_item);

        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        dateString = dateFormatter.format(date);
        start_date.setText(dateString);
        end_date.setText(dateString);
        appUser.start_date = start_date.getText().toString();
        appUser.end_date = end_date.getText().toString();
        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
        String fixMonth = "Apr";
        int inputMonthPosition = inputMonthPosition(fixMonth);
        int currentMonthPosition = currentMonth();
        int currentYear = currentYear();
        cashInHand.add("Today");
        cashInHand.add("Last 7 days");

        if(currentMonthPosition<inputMonthPosition)
        {
            for(int i = currentMonthPosition; i>=0; i--){
                cashInHand.add(monthName[i] + " " + currentYear);
            }
            for(int j=11;j>=inputMonthPosition;j--){
                cashInHand.add(monthName[j] + " " + (currentYear-1));
            }
        }else {
            for (int i = currentMonthPosition; i >=inputMonthPosition; i--) {

                cashInHand.add(monthName[i] + " " + currentYear);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.layout_date_spinner_textview, cashInHand);
        dataAdapter.setDropDownViewResource(R.layout.layout_date_spinner_dropdown_item);
        dashboardSpinner.setAdapter(dataAdapter);



        dashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                appUser.credit_note_duration_spinner=selectedItemText;
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);

                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(CreditNoteWoItemListActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_CREDIT_NOTE);
                } else {
                    snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        snackbar.dismiss();
                                    }
                                }
                            });
                    snackbar.show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        date_from_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopup();
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
        actionbarTitle.setText("CREDIT NOTE");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec"};

    private int currentMonth() {
        int monthPosition = -1;
        Calendar cal = Calendar.getInstance();
        String currentMonth = monthName[cal.get(Calendar.MONTH)];
        //String currentYear = monthName[cal.get(Calendar.YEAR)];

        for (int i = 0; i < 12; i++) {
            if (currentMonth == (monthName[i])) {
                monthPosition = i;
            }
        }
        return monthPosition;
    }

    private int currentYear() {

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        return currentYear;
    }

    private int inputMonthPosition(String month) {
        int inputMonth = -1;
        for (int i = 0; i < 12; i++) {
            if (month.equals(monthName[i])) {
                inputMonth = i;
            }
        }
        return inputMonth;
    }
    @Subscribe
    public void getcreditnote(GetCreditNoteResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200) {
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new CreditNoteListAdapter(this,response.getCredit_notes().data);
            mRecyclerView.setAdapter(mAdapter);
            Double total=0.0;
            for(int i=0;i<response.getCredit_notes().getData().size();i++){
                total=total+response.getCredit_notes().getData().get(i).getAttributes().getAmount();

            }
            mTotal.setText("Total: "+String.format("%.2f",total));
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void event_click_alert(EventClickAlertForCreditNote response) {
        mProgressDialog.dismiss();
        response.getPosition();
        Intent intent = new Intent(CreditNoteWoItemListActivity.this, CreateCreditNoteWoActivity.class);
        intent.putExtra("fromCreditNote", true);
        intent.putExtra("id", response.getPosition());
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void deletecreditnote(EventDeleteCreditNote pos){
        appUser.delete_credit_note_id= pos.getPosition();
        LocalRepositories.saveAppUser(this,appUser);
        new AlertDialog.Builder(CreditNoteWoItemListActivity.this)
                .setTitle("Delete Credit Note Item")
                .setMessage("Are you sure you want to delete this Record ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(CreditNoteWoItemListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_CREDIT_NOTE);
                    }
                    else{
                        snackbar = Snackbar
                                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Boolean isConnected = ConnectivityReceiver.isConnected();
                                        if(isConnected){
                                            snackbar.dismiss();
                                        }
                                    }
                                });
                        snackbar.show();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }
    @Subscribe
    public void deletecreditnote(DeleteCreditNoteResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_CREDIT_NOTE);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        appUser.start_date = start_date.getText().toString();
        appUser.end_date = end_date.getText().toString();
        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(CreditNoteWoItemListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_CREDIT_NOTE);
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                snackbar.dismiss();
                            }
                        }
                    });
            snackbar.show();
        }
        super.onResume();
    }
    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
       if(mProgressDialog!=null){
           mProgressDialog.dismiss();
       }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, CreateCreditNoteWoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("fromCreditNote",false);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, CreateCreditNoteWoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("fromCreditNote",false);
        startActivity(intent);
        finish();
    }

    public void showpopup(){
        dialog = new Dialog(CreditNoteWoItemListActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_pick_dialog);
        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button
        TextView date1 = (TextView) dialog.findViewById(R.id.date1);
        TextView date2 = (TextView) dialog.findViewById(R.id.date2);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);

        date1.setOnClickListener(this);
        date2.setOnClickListener(this);
        final Calendar newCalendar = Calendar.getInstance();

        date1.setText(dateString);
        date2.setText(dateString);

        DatePickerDialog1 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date = dateFormatter.format(newDate.getTime());
                ((TextView) dialog.findViewById(R.id.date1)).setText(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date = dateFormatter.format(newDate.getTime());
                ((TextView) dialog.findViewById(R.id.date2)).setText(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // if button is clicked, close the custom dialog
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appUser.start_date = ((TextView) dialog.findViewById(R.id.date1)).getText().toString();
                appUser.end_date = ((TextView) dialog.findViewById(R.id.date2)).getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                start_date.setText(((TextView) dialog.findViewById(R.id.date1)).getText().toString());
                end_date.setText(((TextView) dialog.findViewById(R.id.date2)).getText().toString());
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(CreditNoteWoItemListActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_CREDIT_NOTE);
                } else {
                    snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                        snackbar.dismiss();
                                    }
                                }
                            });
                    snackbar.show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view == dialog.findViewById(R.id.date1)) {
            DatePickerDialog1.show();
        }else if (view == dialog.findViewById(R.id.date2)){
            DatePickerDialog2.show();
        }
    }
}
