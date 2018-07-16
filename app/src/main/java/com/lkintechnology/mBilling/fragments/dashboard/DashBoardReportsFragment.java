package com.lkintechnology.mBilling.fragments.dashboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.CompanyListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionExpensesActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionPurchaseActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionSalesActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.activities.printerintegration.AEMScrybeDevice;
import com.lkintechnology.mBilling.activities.printerintegration.CardReader;
import com.lkintechnology.mBilling.activities.printerintegration.IAemCardScanner;
import com.lkintechnology.mBilling.activities.printerintegration.IAemScrybe;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.companydashboardinfo.CompanyDetails;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class DashBoardReportsFragment extends Fragment implements IAemCardScanner, IAemScrybe {
    @Bind(R.id.transaction_button)
    LinearLayout transactionButton;
    @Bind(R.id.layout_sale)
    LinearLayout mSales_layout;
    @Bind(R.id.layout_purchase)
    LinearLayout mPurchase_layout;
    @Bind(R.id.layout_expense)
    LinearLayout mExpenses_layout;
    @Bind(R.id.textview_purchase)
    TextView mtextview_purchase;
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
    public AEMScrybeDevice m_AemScrybeDevice;
    public CardReader m_cardReader = null;
/*

    @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;
*/

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

        /*if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }*/


        appUser = LocalRepositories.getAppUser(getActivity());
        mtextview_sales.setText("₹ " + String.format("%.2f", DashboardAccountFragment.data.getData().getAttributes().getSales()));
        mtextview_purchase.setText("₹ " + String.format("%.2f", DashboardAccountFragment.data.getData().getAttributes().getPurchases()));
        mtextview_expenses.setText("₹ " + String.format("%.2f", DashboardAccountFragment.data.getData().getAttributes().getExpenses()));
        mProfit_loss_textview1.setText("₹ " + String.format("%.2f", DashboardAccountFragment.data.getData().getAttributes().getProfit_loss()));

        m_AemScrybeDevice = new AEMScrybeDevice(this);
        registerForContextMenu(transactionButton);
        if (DashboardAccountFragment.data.getData().getAttributes().getProfit_loss() < 0) {
            //mProfit_lose_layout.setBackgroundColor(Color.RED);
            mProfit_loss_textview.setText("Loss");
            mProfit_loss_textview.setTextColor(Color.RED);
            mProfit_loss_textview1.setTextColor(Color.RED);
            // mProfit_loss_layout.setBackgroundResource(R.drawable.curve_backgroung_red);
            profit_loss_image.setImageResource(R.drawable.icon_loss);
        } else {
            mProfit_loss_textview.setText("Profit");
            profit_loss_image.setImageResource(R.drawable.icon_profits);
            // mProfit_loss_layout.setBackgroundResource(R.drawable.curve_backgroung_green);
            // mProfit_lose_layout.setBackgroundColor(Color.GREEN);
        }
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardAccountFragment.printerList.clear();
                if (CompanyListActivity.boolForInvoiceFormat) {
                    if (DashboardAccountFragment.m_AemPrinter == null) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Connect Printer")
                            .setMessage(R.string.btn_connect_printer)
                            .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {

                                DashboardAccountFragment.printerList = m_AemScrybeDevice.getPairedPrinters();

                                if (DashboardAccountFragment.printerList.size() > 0) {
                                   // if (DashboardAccountFragment.m_AemPrinter == null) {
                                        getActivity().openContextMenu(view);
                                   /* } else {
                                        Toast.makeText(getActivity(), "Already connected!!!", Toast.LENGTH_SHORT).show();
                                        //  showAlert("Already connected!!!\n\nDo you want to disconnect press no!!!");
                                        Intent intent = new Intent(getActivity(), TransactionDashboardActivity.class);
                                        startActivity(intent);
                                    }*/
                                } else
                                    showAlert("No Paired Printers found");
                            })

                            .setNegativeButton(R.string.btn_no, (dialogInterface, i) -> {
                                if (DashboardAccountFragment.m_AemPrinter != null) {
                                    try {
                                        m_AemScrybeDevice.disConnectPrinter();
                                        DashboardAccountFragment.m_AemPrinter = null;
                                        Toast.makeText(getActivity(), "disconnected", Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Intent intent = new Intent(getActivity(), TransactionDashboardActivity.class);
                                startActivity(intent);

                            })
                            .show();
                    }else {
                        Intent intent = new Intent(getActivity(), TransactionDashboardActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Intent intent = new Intent(getActivity(), TransactionDashboardActivity.class);
                    startActivity(intent);
                }
            }
        });

        mSales_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appUser.duration_spinner ="Today";
                Intent intent = new Intent(getActivity(), TransactionSalesActivity.class);
                DashBoardReportsFragment.isDirectForFirstPage = false;
                appUser.start_date = "";
                appUser.end_date = "";
                LocalRepositories.saveAppUser(getActivity(), appUser);
                startActivity(intent);
            }
        });
        mPurchase_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appUser.duration_spinner ="Today";
                Intent intent = new Intent(getActivity(), TransactionPurchaseActivity.class);
                DashBoardReportsFragment.isDirectForFirstPage = false;
                appUser.start_date = "";
                appUser.end_date = "";
                LocalRepositories.saveAppUser(getActivity(), appUser);
                startActivity(intent);
            }
        });

        mExpenses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TransactionExpensesActivity.class);
                DashBoardReportsFragment.isDirectForFirstPage = false;
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select Printer to connect");

        for (int i = 0; i < DashboardAccountFragment.printerList.size(); i++) {
            menu.add(0, v.getId(), 0, DashboardAccountFragment.printerList.get(i));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        String printerName = item.getTitle().toString();
        try {
            m_AemScrybeDevice.connectToPrinter(printerName);
            m_cardReader = m_AemScrybeDevice.getCardReader(this);
            DashboardAccountFragment.m_AemPrinter = m_AemScrybeDevice.getAemPrinter();
            Toast.makeText(getActivity(), "Connected with " + printerName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), TransactionDashboardActivity.class);
            startActivity(intent);
            //  m_cardReader.readMSR();
        } catch (IOException e) {
            if (e.getMessage().contains("Service discovery failed")) {
                Toast.makeText(getActivity(), "Not Connected" + printerName + " is unreachable or off otherwise it is connected with other device", Toast.LENGTH_SHORT).show();
            } else if (e.getMessage().contains("Device or resource busy")) {
                Toast.makeText(getActivity(), "the device is already connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public void showAlert(String alertMsg) {
        AlertDialog.Builder alertBox = new AlertDialog.Builder(getActivity());

        alertBox.setMessage(alertMsg).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        return;
                    }
                });

        AlertDialog alert = alertBox.create();
        alert.show();
    }

    @Override
    public void onScanMSR(String buffer, CardReader.CARD_TRACK cardtrack) {

    }

    @Override
    public void onScanDLCard(String buffer) {

    }

    @Override
    public void onScanRCCard(String buffer) {

    }

    @Override
    public void onScanRFD(String buffer) {

    }

    @Override
    public void onScanPacket(String buffer) {

    }

    @Override
    public void onDiscoveryComplete(ArrayList<String> aemPrinterList) {
        DashboardAccountFragment.printerList = aemPrinterList;
        for (int i = 0; i < aemPrinterList.size(); i++) {
            String Device_Name = aemPrinterList.get(i);
            String status = m_AemScrybeDevice.pairPrinter(Device_Name);
            Log.e("STATUS", status);
        }
    }
}

