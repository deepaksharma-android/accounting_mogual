package com.berylsystems.buzz.activities.company.navigation.reports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;

import com.berylsystems.buzz.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionPdfActivity extends AppCompatActivity {

    @Bind(R.id.webView)
    WebView mPdf_webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_pdf);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String company_report = intent.getStringExtra("company_report");
        Spanned htmlAsSpanned = Html.fromHtml(company_report);
        mPdf_webview.loadDataWithBaseURL(null, company_report, "text/html", "utf-8", null);
        mPdf_webview.getSettings().setBuiltInZoomControls(true);
    }
}