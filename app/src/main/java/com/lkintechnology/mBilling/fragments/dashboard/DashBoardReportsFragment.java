package com.lkintechnology.mBilling.fragments.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionExpensesActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionSalesActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.companydashboardinfo.CompanyDetails;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DashBoardReportsFragment extends Fragment {
    @Bind(R.id.transaction_button)
    LinearLayout transactionButton;

    @Bind(R.id.layout_sale)
    LinearLayout mSales_layout;
    @Bind(R.id.layout_expense)
    LinearLayout mExpenses_layout;
    @Bind(R.id.textview_sale)
    TextView mtextview_sales;
    @Bind(R.id.textview_expenses)
    TextView mtextview_expenses;
    @Bind(R.id.profit_loss_layout)
    LinearLayout mProfit_loss_layout;
    @Bind(R.id.profit_loss_textview)
    TextView mProfit_loss_textview;
    @Bind(R.id.profit_loss_textview1)
    TextView mProfit_loss_textview1;
    @Bind(R.id.profit_loss_image)
    ImageView profit_loss_image;
    ProgressDialog mProgressDialog;
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
    AppUser appUser;
    public static Boolean isDirectForFirstPage = true;
    public static CompanyDetails data;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_reports, container, false);
        ButterKnife.bind(this, view);
        appUser= LocalRepositories.getAppUser(getActivity());
        mtextview_sales.setText("₹ " +String.format("%.2f",DashboardAccountFragment.data.getData().getAttributes().getSales()));
        mtextview_expenses.setText("₹ " +String.format("%.2f",DashboardAccountFragment.data.getData().getAttributes().getExpenses()));
        mProfit_loss_textview1.setText("₹ " +String.format("%.2f",DashboardAccountFragment.data.getData().getAttributes().getProfit_loss()));

        if(DashboardAccountFragment.data.getData().getAttributes().getProfit_loss()<0){
            //mProfit_lose_layout.setBackgroundColor(Color.RED);
            mProfit_loss_textview.setText("Loss");
            mProfit_loss_textview.setTextColor(Color.RED);
            mProfit_loss_textview1.setTextColor(Color.RED);
            // mProfit_loss_layout.setBackgroundResource(R.drawable.curve_backgroung_red);
            profit_loss_image.setImageResource(R.drawable.icon_loss);
        }else {
            mProfit_loss_textview.setText("Profit");
            profit_loss_image.setImageResource(R.drawable.icon_profits);
            // mProfit_loss_layout.setBackgroundResource(R.drawable.curve_backgroung_green);
            // mProfit_lose_layout.setBackgroundColor(Color.GREEN);
        }
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TransactionDashboardActivity.class);
                startActivity(intent);
            }
        });

        mSales_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appUser.duration_spinner ="Today";
                Intent intent = new Intent(getActivity(), TransactionSalesActivity.class);
                DashBoardReportsFragment.isDirectForFirstPage=false;
                startActivity(intent);
            }
        });
        mExpenses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TransactionExpensesActivity.class);
                DashBoardReportsFragment.isDirectForFirstPage=false;
                startActivity(intent);
            }
        });
        return view;
    }
}