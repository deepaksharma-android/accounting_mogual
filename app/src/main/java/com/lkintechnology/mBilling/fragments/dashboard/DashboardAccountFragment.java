package com.lkintechnology.mBilling.fragments.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionBankActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionCashInHandActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionCustomerActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionStockInHandActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionSupplierActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.companydashboardinfo.CompanyDetails;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import com.lkintechnology.mBilling.utils.Preferences;

public class DashboardAccountFragment extends Fragment{
    @Bind(R.id.transaction_button)
    LinearLayout transactionButton;
    @Bind(R.id.layout_cash_in_hand)
    LinearLayout mlayout_cash_in_hand;
    @Bind(R.id.layout_bank)
    LinearLayout mlayout_bank;
    @Bind(R.id.layout_customer)
    LinearLayout mlayout_customer;
    @Bind(R.id.layout_supplier)
    LinearLayout mlayout_supplier;
    @Bind(R.id.layout_stock_in_hand)
    LinearLayout mlayout_stock_in_hand;
    @Bind(R.id.textview_cash_in_hand)
    TextView mtextview_cash_in_hand;
    @Bind(R.id.textview_bank)
    TextView mtextview_bank;
    @Bind(R.id.date)
    TextView mDate;
    @Bind(R.id.textview_customer)
    TextView mtextview_customer;
    @Bind(R.id.textview_supplier)
    TextView mtextview_supplier;
   /* @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;*/
    @Bind(R.id.textview_stock_in_hand)
    TextView mtextview_stock_in_hand;

    ProgressDialog mProgressDialog;
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
    public static Boolean isDirectForFirstPage = true;
    private SimpleDateFormat dateFormatter;
    AppUser appUser;
    public static CompanyDetails data;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_accounts, container, false);
        ButterKnife.bind(this, view);

       /* if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }*/



        appUser=LocalRepositories.getAppUser(getActivity());
        final Calendar newCalendar = Calendar.getInstance();
        String dayNumberSuffix = getDayNumberSuffix(newCalendar.get(Calendar.DAY_OF_MONTH));
        dateFormatter = new SimpleDateFormat(" d'" + dayNumberSuffix + "' MMM yy", Locale.US);
        String date1 = dateFormatter.format(newCalendar.getTime());
        Timber.i("DATE1"+date1);
        String arr[]=date1.split(" ");
        String apos=arr[2]+"\'";
        String datenew=arr[1]+" "+apos+" "+arr[3];
        mDate.setText("As On "+datenew);
        mtextview_cash_in_hand.setText("₹ " +String.format("%.2f",data.getData().getAttributes().getCash_in_hand()));
        mtextview_bank.setText("₹ " +String.format("%.2f", data.getData().getAttributes().getBank_account()));
        mtextview_customer.setText("₹ " +String.format("%.2f", data.getData().getAttributes().getCustomer()));
        mtextview_supplier.setText("₹ " +String.format("%.2f", data.getData().getAttributes().getSupplier()));
        mtextview_stock_in_hand.setText("₹ " +String.format("%.2f",data.getData().getAttributes().getStock_in_hand()));

        mlayout_cash_in_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Cash-in-hand";
                LocalRepositories.saveAppUser(getActivity(),appUser);
                Intent i = new Intent(getActivity(), TransactionCashInHandActivity.class);
                DashboardAccountFragment.isDirectForFirstPage=false;
                startActivity(i);
            }
        });

        mlayout_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Bank Accounts";
                LocalRepositories.saveAppUser(getActivity(),appUser);
                Intent i = new Intent(getActivity(), TransactionBankActivity.class);
                DashboardAccountFragment.isDirectForFirstPage=false;
                startActivity(i);
            }
        });

        mlayout_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Sundry Debtors";
                LocalRepositories.saveAppUser(getActivity(),appUser);
                Intent i = new Intent(getActivity(), TransactionCustomerActivity.class);
                DashboardAccountFragment.isDirectForFirstPage=false;
                startActivity(i);
            }
        });

        mlayout_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Sundry Creditors";
                LocalRepositories.saveAppUser(getActivity(),appUser);
                Intent i = new Intent(getActivity(), TransactionSupplierActivity.class);
                DashboardAccountFragment.isDirectForFirstPage=false;
                startActivity(i);
            }
        });
        mlayout_stock_in_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group ="";
                LocalRepositories.saveAppUser(getActivity(),appUser);
                Intent i = new Intent(getActivity(), TransactionStockInHandActivity.class);
              /*  FirstPageActivity.isDirectForFirstPage=false;*/
                startActivity(i);
            }
        });

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TransactionDashboardActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

 /*   private boolean isFirstTime() {
        SharedPreferences preferences = getActivity().getPreferences(MODE_PRIVATE);
        //SharedPreferences preferences1=getP
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
            mOverlayLayout.setVisibility(View.VISIBLE);
            mOverlayLayout.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mOverlayLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

            });


        }
        return ranBefore;

    }*/

    private String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

  /*  private boolean isFirstTime()
    {
        SharedPreferences preferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
            mOverlayLayout.setVisibility(View.VISIBLE);
            mOverlayLayout.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mOverlayLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

            });


        }
        return ranBefore;

    }*/
}