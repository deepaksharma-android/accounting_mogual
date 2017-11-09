package com.berylsystems.buzz.activities.company.administration.master.account;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountDetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        ButterKnife.bind(this);
        initActionbar();
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
        actionbarTitle.setText("CREATE ACCOUNT");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    public void openingbalance(View v){
        Dialog dialogbal = new Dialog(AccountDetailsActivity.this);
        dialogbal.setContentView(R.layout.dialog_opening_balance);
        dialogbal.setTitle("Opening Balance");
        dialogbal.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbal.dismiss();
            }
        });
        // set the custom dialog components - text, image and button
        dialogbal.show();
    }
    public void address(View v){
        Dialog dialogaddress = new Dialog(AccountDetailsActivity.this);
        dialogaddress.setContentView(R.layout.dialog_address);
        dialogaddress.setTitle("Address");
        dialogaddress.setCancelable(true);
        // set the custom dialog components - text, image and button
        LinearLayout submit = (LinearLayout) dialogaddress.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogaddress.dismiss();
            }
        });
        dialogaddress.show();
    }
    public void gstin(View v){
       Dialog dialoggst = new Dialog(AccountDetailsActivity.this);
        dialoggst.setContentView(R.layout.dialog_gstin);
        dialoggst.setTitle("GSTIN Info");
        dialoggst.setCancelable(true);
        // set the custom dialog components - text, image and button
        LinearLayout submit = (LinearLayout) dialoggst.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoggst.dismiss();
            }
        });
        dialoggst.show();

    }
    public void creaditinfo(View v){
        Dialog dialogcredit = new Dialog(AccountDetailsActivity.this);
        dialogcredit.setContentView(R.layout.dialog_credit);
        dialogcredit.setTitle("Credit Info");
        dialogcredit.setCancelable(true);
        LinearLayout submit = (LinearLayout) dialogcredit.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcredit.dismiss();
            }
        });
        // set the custom dialog components - text, image and button
        dialogcredit.show();
    }

}