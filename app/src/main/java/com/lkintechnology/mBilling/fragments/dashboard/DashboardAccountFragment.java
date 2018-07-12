package com.lkintechnology.mBilling.fragments.dashboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.CompanyListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionBankActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionCashInHandActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionCustomerActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionStockInHandActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionSupplierActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.activities.printerintegration.AEMPrinter;
import com.lkintechnology.mBilling.activities.printerintegration.AEMScrybeDevice;
import com.lkintechnology.mBilling.activities.printerintegration.BluetoothActivity;
import com.lkintechnology.mBilling.activities.printerintegration.CardReader;
import com.lkintechnology.mBilling.activities.printerintegration.IAemCardScanner;
import com.lkintechnology.mBilling.activities.printerintegration.IAemScrybe;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.companydashboardinfo.CompanyDetails;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

import com.lkintechnology.mBilling.utils.Preferences;

public class DashboardAccountFragment extends Fragment implements IAemCardScanner, IAemScrybe {
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
    @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;
    @Bind(R.id.textview_stock_in_hand)
    TextView mtextview_stock_in_hand;
    ProgressDialog mProgressDialog;
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
    public static Boolean isDirectForFirstPage = true;
    private SimpleDateFormat dateFormatter;
    AppUser appUser;
    public static CompanyDetails data;
    public static ArrayList<String> printerList;
    AEMScrybeDevice m_AemScrybeDevice;
    CardReader m_cardReader = null;
    public static AEMPrinter m_AemPrinter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_accounts, container, false);
        ButterKnife.bind(this, view);
   /*     if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }*/
        printerList = new ArrayList<String>();
        m_AemScrybeDevice = new AEMScrybeDevice(this);

        appUser = LocalRepositories.getAppUser(getActivity());
        final Calendar newCalendar = Calendar.getInstance();
        String dayNumberSuffix = getDayNumberSuffix(newCalendar.get(Calendar.DAY_OF_MONTH));
        dateFormatter = new SimpleDateFormat(" d'" + dayNumberSuffix + "' MMM yy", Locale.US);
        String date1 = dateFormatter.format(newCalendar.getTime());
        Timber.i("DATE1" + date1);
        String arr[] = date1.split(" ");
        String apos = arr[2] + "\'";
        String datenew = arr[1] + " " + apos + " " + arr[3];
        mDate.setText("As On " + datenew);
        mtextview_cash_in_hand.setText("₹ " + String.format("%.2f", data.getData().getAttributes().getCash_in_hand()));
        mtextview_bank.setText("₹ " + String.format("%.2f", data.getData().getAttributes().getBank_account()));
        mtextview_customer.setText("₹ " + String.format("%.2f", data.getData().getAttributes().getCustomer()));
        mtextview_supplier.setText("₹ " + String.format("%.2f", data.getData().getAttributes().getSupplier()));
        mtextview_stock_in_hand.setText("₹ " + String.format("%.2f", data.getData().getAttributes().getStock_in_hand()));
        registerForContextMenu(transactionButton);
        mlayout_cash_in_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Cash-in-hand";
                LocalRepositories.saveAppUser(getActivity(), appUser);
                Intent i = new Intent(getActivity(), TransactionCashInHandActivity.class);
                DashboardAccountFragment.isDirectForFirstPage = false;
                startActivity(i);
            }
        });

        mlayout_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Bank Accounts";
                LocalRepositories.saveAppUser(getActivity(), appUser);
                Intent i = new Intent(getActivity(), TransactionBankActivity.class);
                DashboardAccountFragment.isDirectForFirstPage = false;
                startActivity(i);
            }
        });

        mlayout_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Sundry Debtors";
                LocalRepositories.saveAppUser(getActivity(), appUser);
                Intent i = new Intent(getActivity(), TransactionCustomerActivity.class);
                DashboardAccountFragment.isDirectForFirstPage = false;
                startActivity(i);
            }
        });

        mlayout_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Sundry Creditors";
                LocalRepositories.saveAppUser(getActivity(), appUser);
                Intent i = new Intent(getActivity(), TransactionSupplierActivity.class);
                DashboardAccountFragment.isDirectForFirstPage = false;
                startActivity(i);
            }
        });
        mlayout_stock_in_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "";
                LocalRepositories.saveAppUser(getActivity(), appUser);
                Intent i = new Intent(getActivity(), TransactionStockInHandActivity.class);
              /*  FirstPageActivity.isDirectForFirstPage=false;*/
                startActivity(i);
            }
        });

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CompanyListActivity.boolForInvoiceFormat) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Connect Printer")
                            .setMessage(R.string.btn_connect_printer)
                            .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {
                                if (m_AemPrinter==null){
                                    printerList.clear();
                                }
                                printerList = m_AemScrybeDevice.getPairedPrinters();

                                if (printerList.size() > 0) {
                                    if (m_AemPrinter == null) {
                                        getActivity().openContextMenu(view);
                                    } else {
                                        Toast.makeText(getActivity(), "Already connected!!!", Toast.LENGTH_SHORT).show();
                                        //  showAlert("Already connected!!!\n\nDo you want to disconnect press no!!!");
                                        Intent intent = new Intent(getActivity(), TransactionDashboardActivity.class);
                                        startActivity(intent);
                                    }
                                } else
                                    showAlert("No Paired Printers found");
                            })

                            .setNegativeButton(R.string.btn_no, (dialogInterface, i) -> {
                                if (m_AemPrinter != null) {
                                    try {
                                        m_AemScrybeDevice.disConnectPrinter();
                                        m_AemPrinter = null;
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

    private boolean isFirstTime() {
        SharedPreferences preferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
            mOverlayLayout.setVisibility(View.VISIBLE);
            mOverlayLayout.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mOverlayLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

            });


        }
        return ranBefore;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select Printer to connect");

        for (int i = 0; i < printerList.size(); i++) {
            menu.add(0, v.getId(), 0, printerList.get(i));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        String printerName = item.getTitle().toString();
        try {
            m_AemScrybeDevice.connectToPrinter(printerName);
            m_cardReader = m_AemScrybeDevice.getCardReader(this);
            m_AemPrinter = m_AemScrybeDevice.getAemPrinter();
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
    public void onDiscoveryComplete(ArrayList<String> aemPrinterList) {
        printerList = aemPrinterList;
        for (int i = 0; i < aemPrinterList.size(); i++) {
            String Device_Name = aemPrinterList.get(i);
            String status = m_AemScrybeDevice.pairPrinter(Device_Name);
            Log.e("STATUS", status);
        }
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


}