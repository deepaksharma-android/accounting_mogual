package com.lkintechnology.mBilling.activities.company.navigations;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.os.Handler;
import android.print.PdfPrint;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.CompanyListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.GetPurchaseReturnVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.PurchaseReturnVoucherDetailsData;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.GetPurchaseVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.PurchaseVoucherDetailsData;
import com.lkintechnology.mBilling.networks.api_response.sale_return.GetSaleReturnVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.sale_return.SaleReturnVoucherDetailsData;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.GetSaleVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.SaleVoucherDetailsData;
import com.lkintechnology.mBilling.utils.BluPrinterHelper;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionPdfActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.webView)
    WebView mPdf_webview;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    public SaleVoucherDetailsData dataForPrinterSale;
    public SaleReturnVoucherDetailsData dataForPrinterSaleReturn;
    public PurchaseVoucherDetailsData dataForPrinterPurchase;
    public PurchaseReturnVoucherDetailsData dataForPrinterPurchaseReturn;

    private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    String htmlString;
    ProgressDialog progressDialog;
    AppUser appUser;
    String type;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_pdf);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(TransactionPdfActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        appUser = LocalRepositories.getAppUser(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                initActionbar();
                Intent intent = getIntent();
                String company_report = intent.getStringExtra("company_report");
                if (intent.getStringExtra("type")!=null){
                    type = intent.getStringExtra("type");
                }else {
                    type = "else";
                }

                htmlString = company_report;
                Spanned htmlAsSpanned = Html.fromHtml(company_report);
                htmlString = htmlAsSpanned.toString();
                mPdf_webview.loadDataWithBaseURL(null, company_report, "text/html", "utf-8", null);
                mPdf_webview.getSettings().setBuiltInZoomControls(true);
                progressDialog.dismiss();
            }
        },3*1000);

        EventBus.getDefault().register(this);


    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
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
        actionbarTitle.setText("Transaction PDF");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createWebPrintJob(WebView webView) {
        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 500, 500))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/m_Billing_PDF/");
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/m_Billing_PDF/");
        File pathPrint = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/m_Billing_PDF/a.pdf");

        PdfPrint pdfPrint = new PdfPrint(attributes);

        PrintDocumentAdapter adapter;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            adapter = webView.createPrintDocumentAdapter(jobName);
        } else {
            adapter = webView.createPrintDocumentAdapter();
        }
        pdfPrint.print(adapter, path, "a.pdf");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                previewPdf(pathPrint);
            }
        }, 3 * 1000);
    }


    private void previewPdf(File path) {
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(path);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        } else {
            Toast.makeText(this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_print_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.icon_id) {
            if (CompanyListActivity.boolForInvoiceFormat){
                if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        if (type.equals("sale_voucher")){
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SALE_VOUCHER_DETAILS);
                        }else if (type.equals("sale_return_voucher")){
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SALE_RETURN_VOUCHER_DETAILS);
                        }else if (type.equals("purchase_voucher")){
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PURCHASE_VOUCHER_DETAILS);
                        }else if (type.equals("purchase_return_voucher")){
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PURCHASE_RETURN_VOUCHER_DETAILS);
                        }
                        System.out.println("");
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
                }else {
                    Helpers.dialogMessage(TransactionPdfActivity.this,"Please go to dashboard and connect printer!!");
                  //  Toast.makeText(this, "Please go to dashboard and connect printer!!", Toast.LENGTH_SHORT).show();
                }

            }else {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/m_Billing_PDF/");
                if (path.isDirectory()) {
                    String[] children = path.list();
                    for (int i = 0; i < children.length; i++) {
                        new File(path, children[i]).delete();
                    }
                }
                createWebPrintJob(mPdf_webview);
            }

        } else {
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Subscribe
    public void getSaleVoucherDetails(GetSaleVoucherDetails response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

            dataForPrinterSale = new SaleVoucherDetailsData();
            dataForPrinterSale = response.getSale_voucher().getData();
            BluPrinterHelper.saleVoucherReceipt(this,dataForPrinterSale);
        } else {
            Helpers.dialogMessage(TransactionPdfActivity.this, response.getMessage());
        }
    }

    @Subscribe
    public void getSaleReturnVoucherDetails(GetSaleReturnVoucherDetails response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

            dataForPrinterSaleReturn = new SaleReturnVoucherDetailsData();
            dataForPrinterSaleReturn = response.getSale_return_voucher().getData();
            BluPrinterHelper.saleReturnVoucherReceipt(this,dataForPrinterSaleReturn);
        } else {
            Helpers.dialogMessage(TransactionPdfActivity.this, response.getMessage());
        }
    }

    @Subscribe
    public void getPurchaseVoucherDetails(GetPurchaseVoucherDetails response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

            dataForPrinterPurchase = new PurchaseVoucherDetailsData();
            dataForPrinterPurchase = response.getPurchase_voucher().getData();
            BluPrinterHelper.purchaseVoucherReceipt(this,dataForPrinterPurchase);
        } else {
            Helpers.dialogMessage(TransactionPdfActivity.this, response.getMessage());
        }
    }

    @Subscribe
    public void getPurchaseReturnVoucherDetails(GetPurchaseReturnVoucherDetails response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

            dataForPrinterPurchaseReturn = new PurchaseReturnVoucherDetailsData();
            dataForPrinterPurchaseReturn = response.getPurchase_return_voucher().getData();
            BluPrinterHelper.purchaseReturnVoucherReceipt(this,dataForPrinterPurchaseReturn);
        } else {
            Helpers.dialogMessage(TransactionPdfActivity.this, response.getMessage());
        }
    }
    
}