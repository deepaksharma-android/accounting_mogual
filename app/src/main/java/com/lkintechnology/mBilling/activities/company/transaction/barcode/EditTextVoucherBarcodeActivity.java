package com.lkintechnology.mBilling.activities.company.transaction.barcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

public class EditTextVoucherBarcodeActivity extends AppCompatActivity {
    AppUser appUser;
    String serial;
    String listString,quantity;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_barcode);

        appUser = LocalRepositories.getAppUser(getApplicationContext());
        initActionbar();
        serial = getIntent().getStringExtra("serial");
        quantity = getIntent().getStringExtra("quantity");
        pos = getIntent().getIntExtra("businessType",0);

        LinearLayout serialLayout = (LinearLayout) findViewById(R.id.main_layout);
        LinearLayout submit = (LinearLayout) findViewById(R.id.submit);
        LinearLayout serial_layout = (LinearLayout) findViewById(R.id.serial_layout);
        int width = getWidth();
        int height = getHeight();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        lp.setMargins(20, 10, 20, 0);
        EditText[] pairs = new EditText[Integer.parseInt(serial)];
        for (int l = 0; l < Integer.parseInt(serial); l++) {
            pairs[l] = new EditText(getApplicationContext());
            pairs[l].setPadding(20, 10, 10, 0);
            pairs[l].setWidth(width);
            pairs[l].setHeight(height);
            pairs[l].setBackgroundResource(R.drawable.grey_stroke_rect);
            pairs[l].setTextSize(18);
           // pairs[l].setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
            if (appUser.serial_arr.size() > 0) {
                if (appUser.serial_arr.size() > l) {
                    pairs[l].setText(appUser.serial_arr.get(l));
                } else {
                    pairs[l].setText("");
                }
            }
            pairs[l].setHint("Enter Serial Number" + " " + (l + 1));
            pairs[l].setHintTextColor(Color.GRAY);
            pairs[l].setTextColor(Color.BLACK);
            pairs[l].setLayoutParams(lp);
            pairs[l].setId(l);
            //pairs[l].setText((l + 1) + ": something");
            serialLayout.addView(pairs[l]);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser.serial_arr.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                boolean isbool = false;
                int count=0;
                for (int i = 0; i < Integer.parseInt(serial); i++) {
                    if (pos==0) {
                        if (pairs[i].getText().toString().length() == 15) {
                            if (appUser.serial_arr.contains(pairs[i].getText().toString())) {
                                pairs[i].setText("");
                               try {
                                   appUser.serial_arr.add(i, "");
                               }catch (IndexOutOfBoundsException e){

                               }
                               LocalRepositories.saveAppUser(getApplicationContext(), appUser);
//                                        Toast.makeText(PurchaseAddItemActivity.this, pairs[i].getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!pairs[i].getText().toString().equals("")) {

                                    if ((appUser.serial_arr.size() - 1) == i) {
                                        appUser.serial_arr.set(i, pairs[i].getText().toString());
                                    } else {
                                        appUser.serial_arr.add(pairs[i].getText().toString());
                                    }

//                                            appUser.serial_arr.add(i, pairs[i].getText().toString());
                                    //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                } else {
                                    appUser.serial_arr.add(i, "");
                                    //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                }
                            }

                            appUser.purchase_item_serail_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            for (int j = 0; j < appUser.serial_arr.size(); j++) {
                                if (!appUser.serial_arr.get(j).equals("")) {
                                    appUser.purchase_item_serail_arr.add(appUser.serial_arr.get(j));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                                }
                            }
                            appUser.serial_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            for (int k = 0; k < appUser.purchase_item_serail_arr.size(); k++) {
                                appUser.serial_arr.add(appUser.purchase_item_serail_arr.get(k));
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            }

                            listString = "";

                            for (String s : appUser.purchase_item_serail_arr) {
                                listString += s + ",";
                            }
                           // mSr_no.setText(listString);
                            isbool = true;
                        } else {
                            if (pairs[i].getText().toString().equals("")) {
                                isbool = true;
                            } else {
                                isbool = false;
                                Toast.makeText(EditTextVoucherBarcodeActivity.this, pairs[i].getText().toString() + " is not a IMEI number", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    } else {
                        if (pairs[i].getText().toString().length() > 0) {
                            if (appUser.serial_arr.contains(pairs[i].getText().toString())) {
                                pairs[i].setText("");
                                try {
                                    appUser.serial_arr.add(i, "");
                                }catch (IndexOutOfBoundsException e){

                                }
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
//                                        Toast.makeText(PurchaseAddItemActivity.this, pairs[i].getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!pairs[i].getText().toString().equals("")) {

                                    if ((appUser.serial_arr.size() - 1) == i) {
                                        appUser.serial_arr.set(i, pairs[i].getText().toString());
                                    } else {
                                        appUser.serial_arr.add(pairs[i].getText().toString());
                                    }

//                                            appUser.serial_arr.add(i, pairs[i].getText().toString());
                                    //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                } else {
                                    appUser.serial_arr.add(i, "");
                                    //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                }
                            }

                            appUser.purchase_item_serail_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            for (int j = 0; j < appUser.serial_arr.size(); j++) {
                                if (!appUser.serial_arr.get(j).equals("")) {
                                    appUser.purchase_item_serail_arr.add(appUser.serial_arr.get(j));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                                }
                            }
                            appUser.serial_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            for (int k = 0; k < appUser.purchase_item_serail_arr.size(); k++) {
                                appUser.serial_arr.add(appUser.purchase_item_serail_arr.get(k));
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            }

                            listString = "";

                            for (String s : appUser.purchase_item_serail_arr) {
                                listString += s + ",";
                            }
                           // mSr_no.setText(listString);
                            isbool = true;
                        } else {
                            count++;
                        }
                    }
                }
                /*if (Integer.parseInt(serial)==count){
                    isbool = true;
                   // mSr_no.setText("");
                    listString = "";
                    appUser.purchase_item_serail_arr.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                }*/
                if (isbool) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("serial", listString);
                    returnIntent.putExtra("quantity", quantity);
                    returnIntent.putExtra("boolForBarcode",false);
                    returnIntent.putExtra("textChange",false);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    private int getWidth() {
        int density = getResources().getDisplayMetrics().densityDpi;
        int size = 0;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                size = 500;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                size = 900;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                size = 1200;
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                size = 1000;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                size = 1200;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                size = 1000;
                break;

        }

        return size;
    }

    private int getHeight() {
        int density = getResources().getDisplayMetrics().densityDpi;
        int height = 150;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                height = 150;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                height = 150;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                height = 250;
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                height = 100;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                height = 150;
                break;

        }

        return height;
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
        actionbarTitle.setText("SERIAL NUMBER");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
