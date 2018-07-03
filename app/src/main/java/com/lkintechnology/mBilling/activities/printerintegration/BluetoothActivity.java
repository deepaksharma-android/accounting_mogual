package com.lkintechnology.mBilling.activities.printerintegration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class BluetoothActivity extends AppCompatActivity implements IAemCardScanner, IAemScrybe {
    AEMScrybeDevice m_AemScrybeDevice;
    ArrayList<String> printerList;
    CardReader m_cardReader = null;
    public static AEMPrinter m_AemPrinter = null;
    Button onPrint;
    Button onPrintBill;
    EditText editText;
    int glbPrinterWidth = 48;
    String encoding = "US-ASCII";
    private static int nFontSize, nTextAlign, nScaleTimesWidth,
            nScaleTimesHeight, nFontStyle, nLineHeight = 32, nRightSpace;
    MyClient mcl = null;
    private PrintWriter printOut;
    String htmlString;
    Spanned ni;
    WebView mPdf_webview;
    Bitmap bm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }*/
        setContentView(R.layout.activity_bluetooth);

        printerList = new ArrayList<String>();
        m_AemScrybeDevice = new AEMScrybeDevice(this);
        Button discoverButton = (Button) findViewById(R.id.pairing);
        // Button click = (Button) findViewById(R.id.click);
        onPrint = (Button) findViewById(R.id.onPrint);
        editText = (EditText) findViewById(R.id.edit_text);
        onPrintBill = (Button) findViewById(R.id.bill);
        mPdf_webview = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        htmlString = intent.getStringExtra("company_report");

      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPdf_webview.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
            }
        },3*1000);

        mPdf_webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                mPdf_webview.measure(View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                mPdf_webview.layout(0, 0, mPdf_webview.getMeasuredWidth(),
                        mPdf_webview.getMeasuredHeight());
                mPdf_webview.setDrawingCacheEnabled(true);
                mPdf_webview.buildDrawingCache();
                bm = Bitmap.createBitmap(mPdf_webview.getWidth(),
                        mPdf_webview.getHeight(), Bitmap.Config.ARGB_8888);

                Canvas bigcanvas = new Canvas(bm);
                Paint paint = new Paint();
                int iHeight = bm.getHeight();
                bigcanvas.drawBitmap(bm, 0, iHeight, paint);
                mPdf_webview.draw(bigcanvas);
                System.out.println("1111111111111111111111="
                        + bigcanvas.getWidth());
                System.out.println("22222222222222222222222="
                        + bigcanvas.getHeight());

                if (bm != null) {
                    try {
                        String path = Environment.getExternalStorageDirectory()
                                .toString();
                        OutputStream fOut = null;
                        File file = new File(path, "/aaaa.png");
                        fOut = new FileOutputStream(file);

                        bm.compress(Bitmap.CompressFormat.PNG, 50, fOut);
                        fOut.flush();
                        fOut.close();
                        bm.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/


        registerForContextMenu(discoverButton);


        // ni = Html.fromHtml(" <div class=main_div> <div class=table-responsive> <table class=text-center width100 cellpadding=0 cellspacing=0> <thead> <tr> <th> TAX INVOICE </th> </tr> <tr> <th> amit </th> </tr> <tr> <th> sec-78 </th> </tr> <tr> <th> FARIDABAD (Haryana), PIN NO 121006 </th> </tr> <tr> <th> 9632154870 </th> </tr> <tr> <th> GSTIN: </th> </tr> </thead></table>        <table class=text-center width100 cellpadding=0 cellspacing=0><thead><tr class=border_bottom><th colspan=2> INVOICE DETAILS </th></tr></thead><tbody><tr><td>Seema</td><td> Date: 19 Jun 2018 </td></tr><tr class=border_bottom><td> Bill No: INV12 </td><td> Time: 12:22:56 PM </td> </tr> </tbody></table> </div><h4> Terms And Conditions:- </h4><p> 1. All the prices are inclusive of GST at applicable rates. </p><p> 2. No exchange is allowed for bags and accessories. </p><p> 3. No Warranty, No Exchange, And No Return. </p><p> 4. Exchange till 7 days with bill. </p></div>  ");
        //  editText.setText(Html.fromHtml("<div class=\"main_div\"> <div class=\"table-responsive\"> <table class=\"text-center width100\" cellpadding=\"0\" cellspacing=\"0\"> <thead> <tr> <th> TAX INVOICE </th> </tr> <tr> <th> amit </th> </tr> <tr> <th> sec-78 </th> </tr> <tr> <th> FARIDABAD (Haryana), PIN NO 121006 </th> </tr> <tr> <th> 9632154870 </th> </tr> <tr> <th> GSTIN: </th> </tr> </thead></table>        <table class=\"text-center width100\" cellpadding=\"0\" cellspacing=\"0\"><thead><tr class=\"border_bottom\"><th colspan=\"2\"> INVOICE DETAILS </th></tr></thead><tbody><tr><td>Seema</td><td> Date: 19 Jun 2018 </td></tr><tr class=\"border_bottom\"><td> Bill No: INV12 </td><td> Time: 12:22:56 PM </td> </tr> </tbody></table> </div><h4> Terms And Conditions:- </h4><p> 1. All the prices are inclusive of GST at applicable rates. </p><p> 2. No exchange is allowed for bags and accessories. </p><p> 3. No Warranty, No Exchange, And No Return. </p><p> 4. Exchange till 7 days with bill. </p></div> "));
        // System.out.println("*******ni**** 1 " + ni);
      /*  // String photoHeading = this.getString(R.string.photo_heading);
        SpannableStringBuilder builder = new SpannableStringBuilder(ni);
        //  If you need to convert an HTML to String (remove the tags) you can use this solution:
        System.out.println("*******ni**** builder "+ ni);
        htmlString = stripHtml(builder.toString());
        System.out.println("*******ni**** htmlString "+ ni);*/

        onPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                if (m_AemPrinter == null) {
                    Toast.makeText(BluetoothActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();
                    return;
                } else if (data.isEmpty()) {
                    showAlert("Write Text");
                } else if (m_AemPrinter != null) {
                    try {
                        if (glbPrinterWidth == 32) {
                            m_AemPrinter.print(data);
                            // m_AemPrinter.PrintHindi(data);
                            m_AemPrinter.setCarriageReturn();
                            m_AemPrinter.setCarriageReturn();
                            m_AemPrinter.setCarriageReturn();
                            m_AemPrinter.setCarriageReturn();
                        } else {
                            m_AemPrinter.POS_S_TextOutThreeInch(data, encoding, 0, nScaleTimesWidth, nScaleTimesHeight, nFontSize, nFontStyle);
                            // m_AemPrinter.printThreeInch(data);
                            // m_AemPrinter.PrintHindi(data);
                            m_AemPrinter.setLineFeed(4);
                        }

                    } catch (IOException e) {
                        if (e.getMessage().contains("socket closed"))
                            Toast.makeText(BluetoothActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    mcl.sendDataOnSocket(data, printOut);
                    mcl.sendDataOnSocket("", printOut);
                    //mcl.sendDataOnSocket("", printOut);
                    //mcl.sendDataOnSocket("", printOut);
                }

            }
        });

        onPrintBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPrintBillBluetooth(48);
            }
        });
    }

    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            return String.valueOf(Html.fromHtml(html));
        }

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
            Toast.makeText(BluetoothActivity.this, "Connected with " + printerName, Toast.LENGTH_SHORT).show();

            //  m_cardReader.readMSR();
        } catch (IOException e) {
            if (e.getMessage().contains("Service discovery failed")) {
                Toast.makeText(BluetoothActivity.this, "Not Connected" + printerName + " is unreachable or off otherwise it is connected with other device", Toast.LENGTH_SHORT).show();
            } else if (e.getMessage().contains("Device or resource busy")) {
                Toast.makeText(BluetoothActivity.this, "the device is already connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BluetoothActivity.this, "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (m_AemScrybeDevice != null) {
            try {
                m_AemScrybeDevice.disConnectPrinter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    public void onShowPairedPrinters(View v) {

        String p = m_AemScrybeDevice.pairPrinter("BTprinter0314");
        //  showAlert(p);
        printerList = m_AemScrybeDevice.getPairedPrinters();

        if (printerList.size() > 0)
            openContextMenu(v);
        else
            showAlert("No Paired Printers found");
    }

    public void onPrintImageRaster(View v) {
        if (m_AemPrinter == null) {
            Toast.makeText(BluetoothActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            InputStream is = getAssets().open("aadharAEM.jpg");
            Bitmap inputBitmap = BitmapFactory.decodeStream(is);
            Bitmap resizedBitmap = null;
           /*if(glbPrinterWidth == 32)
                 resizedBitmap = Bitmap.createScaledBitmap(inputBitmap, 350, 140, false);
           else*/
            resizedBitmap = Bitmap.createScaledBitmap(inputBitmap, 384, 384, false);

            // Bitmap b1 = screenshot2(mPdf_webview);
            RasterBT(resizedBitmap);
        } catch (IOException e) {
            showAlert("IO Exception: " + e.toString());
        }
    }

    protected void RasterBT(Bitmap image) {
        try {
            if (glbPrinterWidth == 32) {
                m_AemPrinter.setCarriageReturn();
                m_AemPrinter.setCarriageReturn();
                m_AemPrinter.printImage(image);
                m_AemPrinter.setCarriageReturn();
                m_AemPrinter.setCarriageReturn();
            } else {
                m_AemPrinter.setCarriageReturn();
                m_AemPrinter.setCarriageReturn();
                m_AemPrinter.printImageThreeInch(image);
                m_AemPrinter.setCarriageReturn();
                m_AemPrinter.setCarriageReturn();
            }
        } catch (IOException e) {
            showAlert("IO EX:  " + e.toString());
        }
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

    public void showAlert(String alertMsg) {
        AlertDialog.Builder alertBox = new AlertDialog.Builder(
                BluetoothActivity.this);

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

    public void onPrintBillBluetooth(int numChars) {

        if (m_AemPrinter == null) {
            Toast.makeText(BluetoothActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String data ="";
            String line;
            line = "\n_______________________________________________\n";
            m_AemPrinter.setFontTypeArray(AEMPrinter.bb);
            m_AemPrinter.setFontType(AEMPrinter.DOUBLE_HEIGHT);
            m_AemPrinter.setFontType(AEMPrinter.DOUBLE_WIDTH);
            m_AemPrinter.setFontType(AEMPrinter.TEXT_ALIGNMENT_LEFT);
            data = "              TAX INVOICE\n";
            m_AemPrinter.printThreeInch(data);
            data = "                   amit\n";
            m_AemPrinter.printThreeInch(data);

            data = "                  Gurgaon\n";
            m_AemPrinter.printThreeInch(data);

            data = "                  Sect 78\n";
            m_AemPrinter.printThreeInch(data);

            data = "      FARIDABAD (Haryana), PIN NO 121006\n";
            m_AemPrinter.printThreeInch(data);

            data = "                 7206301203\n";
            m_AemPrinter.printThreeInch(data);

            data = "                  GSTIN:\n";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.setLineFeed(1);

            data = "            INVOICE DETAILS:";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.printThreeInch(line);
            data = "   Seema              Date: 19 Jun 2018\n"+
                    "   Bill No:INV12      Time: 12:22:56 PM";
            m_AemPrinter.printThreeInch(data);
            line = "\n_______________________________________________\n";
            m_AemPrinter.printThreeInch(line);
           /* data = "ITEM NAME/HSN Code\t   Qty    Price     Net Amt";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.printThreeInch(line);
            data = "   car                 1    1000.0      1280.0\n" +
                    "   a/c                 1    180.0       208.0";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.printThreeInch(line);
            data = "Net Sale Qty                             2\n"+
                    "Gross Amount                       1481.6\n"+
                    "SGST Tax                            150.8\n"+
                    "CGST Tax                            150.8";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.printThreeInch(line);
            data = "Final Bill Amount                   1481.6";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.printThreeInch(line);

            data = "Tax Detail\n";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.setLineFeed(1);
            data = " ITEM NAME  Taxable Amount CGST  SGST  Total Tax";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.printThreeInch(line);
            data = "  car         1000.0       150.8  150.8   301.6\n" +
                    "  a/c          180.0       150.8  50.8    301.6";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.printThreeInch(line);
            m_AemPrinter.setLineFeed(1);

            data = "Terms And Conditions:-\n";
            m_AemPrinter.printThreeInch(data);
            data = "1. All the prices are inclusive of GST at applicable rates.\n" +
                    "2. No exchange is allowed for bags and accessories.\n" +
                    "3. No Warranty, No Exchange, And No Return.\n" +
                    "4. Exchange till 7 days with bill.";
            m_AemPrinter.printThreeInch(data);
            m_AemPrinter.setLineFeed(1);
            data = "Thank you! \n";
            m_AemPrinter.printThreeInch(data);*/
            m_AemPrinter.setLineFeed(2);
        } catch (IOException e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(BluetoothActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();
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

    public static Bitmap screenshot2(WebView webView) {
        webView.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), webView.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int iHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap, 0, iHeight, paint);
        webView.draw(canvas);
        return bitmap;
    }
}
