package com.berylsystems.buzz.activities.company.administration.master.item;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.administration.master.account.AccountDetailsActivity;
import com.berylsystems.buzz.activities.company.administration.master.item_group.ItemGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.unit.UnitListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.account.GetAccountDetailsResponse;
import com.berylsystems.buzz.networks.api_response.item.CreateItemResponse;
import com.berylsystems.buzz.networks.api_response.item.EditItemResponse;
import com.berylsystems.buzz.networks.api_response.item.GetItemDetailsResponse;
import com.berylsystems.buzz.networks.api_response.item.GetItemResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.EditItemGroupResponse;
import com.berylsystems.buzz.networks.api_response.taxcategory.GetTaxCategoryResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.Helpers;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateNewItemActivity extends RegisterAbstractActivity {

    ProgressDialog mProgressDialog;

    @Bind(R.id.opening_stc_btn)
    Button opening_stc_btn;

    @Bind(R.id.item_price_info)
    Button itemPriceInfoBtn;

    @Bind(R.id.alternate_unit_detail)
    Button alternateUnitDetailBtn;

    @Bind(R.id.pck_unit_detail)
    Button pkgUnitDetailBtn;
    @Bind(R.id.hsn_layout)
    LinearLayout mHsnLayout;
    @Bind(R.id.hsn_number)
    EditText mHsnNumber;


    @Bind(R.id.submit)
    LinearLayout mSubmitButton;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.group_layout)
    LinearLayout mGroupLayout;

    @Bind(R.id.unit_layout)
    LinearLayout mUnitLayout;

    @Bind(R.id.item_group)
    TextView mItemGroup;

    @Bind(R.id.item_name)
    TextView mItemName;

    @Bind(R.id.item_unit)
    TextView mItemUnit;

    @Bind(R.id.update)
    LinearLayout mUpdateButton;
    @Bind(R.id.tax_category)
    Spinner mTaxCategory;

    Snackbar snackbar;

    Animation blinkOnClick;
    AppUser appUser;
    Double first, second;
    LinearLayout group_layout;

    TextView item_group;
    LinearLayout mConFactorLinear, mStockLinear, mConTypeLinear;
    ArrayList<String> mArrayList;
    Boolean fromitemlist;
    String title;
    Spinner spinner;
    ArrayAdapter<String> mTaxCategoryArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);
        fromitemlist=getIntent().getExtras().getBoolean("fromitemlist");
        if(fromitemlist){
            title="EDIT ITEM";
            mSubmitButton.setVisibility(View.GONE);
            mUpdateButton.setVisibility(View.VISIBLE);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateNewItemActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM_DETAILS);
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
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(CreateNewItemActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_TAX_CATEGORY);
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



        appUser.item_stock_quantity = "";
        appUser.item_stock_amount = "";
        appUser.item_stock_value = "";

        appUser.item_conversion_factor = "";
        appUser.item_opening_stock_quantity_alternate = "";
        appUser.item_conversion_factor_pkg_unit = "";
        appUser.item_salse_price = "";

        appUser.item_description = "";
        appUser.item_alternate_unit_name = "";
        appUser.item_package_unit_detail_id = "";
        appUser.item_package_unit_detail_name = "";
        appUser.item_specify_purchase_account = "";

        appUser.item_serial_number_wise_detail = "";
        appUser.item_set_critical_level = "";
        appUser.item_specify_sales_account = "";
        appUser.item_specify_purchase_account = "";
        appUser.item_dont_maintain_stock_balance = "";

        appUser.item_default_unit_for_sales="";
        appUser.item_default_unit_for_purchase="";
        appUser.item_hsn_number="";
        appUser.item_batch_wise_detail="";

        LocalRepositories.saveAppUser(this, appUser);

        mTaxCategoryArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_tax_category_name);
        mTaxCategoryArrayAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mTaxCategory.setAdapter(mTaxCategoryArrayAdapter);
        mTaxCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    mHsnLayout.setVisibility(View.GONE);
                    appUser.item_hsn_number="";
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                }else {
                    mHsnLayout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSubmitButton.startAnimation(blinkOnClick);
                appUser.item_name = mItemName.getText().toString();
                appUser.item_hsn_number=mHsnNumber.getText().toString();
                for(int i=0;i<appUser.arr_tax_category_name.size();i++){
                    if(mTaxCategory.getSelectedItem().toString().equals(appUser.arr_tax_category_name.get(i))){
                        appUser.item_tax_category= Integer.parseInt(appUser.arr_tax_category_id.get(i));
                        break;
                    }
                }
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);

                if (mItemName.getText().toString().equals("")||mItemUnit.getText().toString().equals("")||mItemGroup.getText().toString().equals("")) {
                    Toast.makeText(CreateNewItemActivity.this, "Name, Group And Unit is mendetory", Toast.LENGTH_SHORT).show();
                    return;
                }

                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(CreateNewItemActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_ITEM);
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
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mItemName.getText().toString().equals("")) {
                    if (!mItemUnit.getText().toString().equals("")) {
                        if (!mItemGroup.getText().toString().equals("")) {
                            appUser.item_name = mItemName.getText().toString();

                            for (int i = 0; i < appUser.arr_item_group_id.size(); i++) {
                                if (appUser.arr_item_group_name.get(i).equals(mItemGroup.getText().toString())) {
                                    appUser.item_group_id = String.valueOf(appUser.arr_item_group_id.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    break;
                                }
                            }
                            for (int i = 0; i < appUser.arr_unitId.size(); i++) {
                                if (appUser.arr_unitName.get(i).equals(mItemUnit.getText().toString())) {
                                    appUser.item_unit_id = String.valueOf(appUser.arr_unitId.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    break;
                                }
                            }

                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                mProgressDialog = new ProgressDialog(CreateNewItemActivity.this);
                                mProgressDialog.setMessage("Info...");
                                mProgressDialog.setIndeterminate(false);
                                mProgressDialog.setCancelable(true);
                                mProgressDialog.show();
                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_ITEM);

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
                        } else {
                            Snackbar.make(coordinatorLayout, "Enter group name", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Enter mobile number", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Enter account name", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemGroupListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 1);
            }
        });

        mUnitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UnitListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 2);
            }
        });


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_new_item;
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
        actionbarTitle.setText("CREATE ITEM");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void openingStock(View view) {
        startActivity(new Intent(getApplicationContext(),ItemOpeningStockActivity.class));
   /*     view.startAnimation(blinkOnClick);
        Dialog dialog = new Dialog(CreateNewItemActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_opening_stock);
        dialog.setCancelable(true);

        EditText stock_quantity = (EditText) dialog.findViewById(R.id.stock_quantity);
        EditText stock_price = (EditText) dialog.findViewById(R.id.stock_price);
        TextView stock_value = (TextView) dialog.findViewById(R.id.stock_value);

        stock_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!stock_quantity.getText().toString().isEmpty()) {
                    first = Double.valueOf(stock_quantity.getText().toString());
                    if (!stock_price.getText().toString().isEmpty()) {
                        second = Double.valueOf(stock_price.getText().toString());
                        stock_value.setText("" + (first * second));
                    }
                } else {
                    stock_value.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        stock_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!stock_quantity.getText().toString().isEmpty()) {
                    if (!stock_price.getText().toString().isEmpty()) {
                        second = Double.valueOf(stock_price.getText().toString());
                        if (!stock_quantity.getText().toString().isEmpty()) {
                            first = Double.valueOf(stock_quantity.getText().toString());
                            stock_value.setText("" + (first * second));
                        }
                    } else {
                        stock_value.setText("");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (!appUser.item_stock_quantity.equals("")) {
            stock_quantity.setText(appUser.item_stock_quantity);
        }
        if (!appUser.item_stock_amount.equals("")) {
            stock_price.setText(appUser.item_stock_amount);
        }
        if (!appUser.item_stock_value.equals("")) {
            stock_value.setText(appUser.item_stock_value);
        }

        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.item_stock_quantity = stock_quantity.getText().toString();
                appUser.item_stock_amount = stock_price.getText().toString();
                if (!stock_price.getText().toString().isEmpty() && !stock_quantity.getText().toString().isEmpty()) {
                    appUser.item_stock_value = String.valueOf(Double.valueOf(appUser.item_stock_quantity) * Double.valueOf(appUser.item_stock_amount));
                } else {
                    appUser.item_stock_value = "";
                }

                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });
        dialog.show();

        LinearLayout cancelImageLayout = (LinearLayout) dialog.findViewById(R.id.imageCancel);
        cancelImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(view);
                dialog.dismiss();
            }
        });*/
    }

    public void alternateUnitDetails(View view) {
        if(!mItemUnit.getText().toString().equals("")) {
            Intent intent = new Intent(getApplicationContext(), ItemAlternateUnitDetails.class);
            intent.putExtra("unit", mItemUnit.getText().toString());
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please enter the unit",Toast.LENGTH_LONG).show();
        }

      /*  view.startAnimation(blinkOnClick);
        Dialog dialog = new Dialog(CreateNewItemActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alternate_unit_details);
        dialog.setCancelable(true);
        dialog.show();

        item_group = (TextView) dialog.findViewById(R.id.item_group);
        group_layout = (LinearLayout) dialog.findViewById(R.id.group_layout);
        group_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UnitListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 3);
            }
        });


        EditText item_conversion_factor = (EditText) dialog.findViewById(R.id.con_factor);
        EditText item_opening_stock_quantity_alternate = (EditText) dialog.findViewById(R.id.stock_quantity);
        spinner = (Spinner) dialog.findViewById(R.id.spinner);


        mConFactorLinear = (LinearLayout) dialog.findViewById(R.id.conFactorLinear);
        mStockLinear = (LinearLayout) dialog.findViewById(R.id.stockLinear);
        mConTypeLinear = (LinearLayout) dialog.findViewById(R.id.conTypeLinear);


        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);

        if (!appUser.item_conversion_factor.equals("")) {
            item_conversion_factor.setText(appUser.item_conversion_factor);
        }
        if (!appUser.item_opening_stock_quantity_alternate.equals("")) {
            item_opening_stock_quantity_alternate.setText(appUser.item_opening_stock_quantity_alternate);
        }
        if (!appUser.item_alternate_unit_name.equals("")) {
            item_group.setText(appUser.item_alternate_unit_name);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.item_conversion_factor = item_conversion_factor.getText().toString();
                appUser.item_opening_stock_quantity_alternate = item_opening_stock_quantity_alternate.getText().toString();
                if (!appUser.item_conversion_factor.equals("")) {
                    item_conversion_factor.setText(appUser.item_conversion_factor);
                }
                if (!appUser.item_opening_stock_quantity_alternate.equals("")) {
                    item_opening_stock_quantity_alternate.setText(appUser.item_opening_stock_quantity_alternate);
                }
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });

        LinearLayout cancleImageLayout = (LinearLayout) dialog.findViewById(R.id.imageCancel);
        cancleImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(view);
                dialog.dismiss();
            }
        });*/
    }

    public void itemPriceInfo(View view) {

        if(!mItemUnit.getText().toString().equals("")) {
            if(appUser.item_alternate_unit_name!=null) {
                Intent intent = new Intent(getApplicationContext(), ItemPriceInfoActivity.class);
                intent.putExtra("unit", mItemUnit.getText().toString());
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Please enter the alternate unit",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Please enter the unit",Toast.LENGTH_LONG).show();
        }
        /*view.startAnimation(blinkOnClick);
        Dialog dialog = new Dialog(CreateNewItemActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_item_price_info);
        dialog.setCancelable(true);
        dialog.show();

        LinearLayout cancleImageLayout = (LinearLayout) dialog.findViewById(R.id.imageCancel);
        cancleImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(view);
                dialog.dismiss();
            }
        });

        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.startAnimation(blinkOnClick);
            }
        });*/
    }

    public void packagingUnitDetail(View view) {
        startActivity(new Intent(getApplicationContext(),ItemPackagingUnitDetailsActivity.class));
       /* view.startAnimation(blinkOnClick);
        Dialog dialog = new Dialog(CreateNewItemActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_packaging_unit_detail);
        dialog.setCancelable(true);
        dialog.show();

        item_group = (TextView) dialog.findViewById(R.id.item_group);
        group_layout = (LinearLayout) dialog.findViewById(R.id.group_layout);
        group_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UnitListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 4);
            }
        });

        EditText item_conversion_factor_pkg_unit = (EditText) dialog.findViewById(R.id.con_factor);
        EditText item_sales_price = (EditText) dialog.findViewById(R.id.Sales_price);
        EditText specify_purchase_account = (EditText) dialog.findViewById(R.id.specify_purchase_account);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);

        if (!appUser.item_conversion_factor_pkg_unit.equals("")) {
            item_conversion_factor_pkg_unit.setText(appUser.item_conversion_factor_pkg_unit);
        }
        if (!appUser.item_salse_price.equals("")) {
            item_sales_price.setText(appUser.item_salse_price);
        }
        if (!appUser.item_package_unit_detail_id.equals("")) {
            item_group.setText(appUser.item_package_unit_detail_name);
        }
        if (!appUser.item_specify_purchase_account.equals("")) {
            specify_purchase_account.setText(appUser.item_specify_purchase_account);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.item_conversion_factor_pkg_unit = item_conversion_factor_pkg_unit.getText().toString();
                appUser.item_salse_price = item_sales_price.getText().toString();
                appUser.item_specify_purchase_account = specify_purchase_account.getText().toString();

                if (!appUser.item_conversion_factor_pkg_unit.equals("")) {
                    item_conversion_factor_pkg_unit.setText(appUser.item_conversion_factor_pkg_unit);
                }
                if (!appUser.item_salse_price.equals("")) {
                    item_sales_price.setText(appUser.item_salse_price);
                }
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });


        LinearLayout cancleImageLayout = (LinearLayout) dialog.findViewById(R.id.imageCancel);
        cancleImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(view);
                dialog.dismiss();
            }
        });*/
    }

    public void itemDescription(View view) {
        view.startAnimation(blinkOnClick);
        Dialog dialog = new Dialog(CreateNewItemActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_item_description);
        dialog.setCancelable(true);
        dialog.show();
        EditText item_description = (EditText) dialog.findViewById(R.id.item_description);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        if (!appUser.item_description.equals("")) {
            item_description.setText(appUser.item_description);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.item_description = item_description.getText().toString();
                if (!appUser.item_description.equals("")) {
                    item_description.setText(appUser.item_description);
                }
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });
        LinearLayout cancleImageLayout = (LinearLayout) dialog.findViewById(R.id.imageCancel);
        cancleImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(view);
                dialog.dismiss();
            }
        });


    }

    public void setting(View view) {
        startActivity(new Intent(getApplicationContext(),ItemSettingsActivity.class));
     /*   view.startAnimation(blinkOnClick);

        Dialog dialog = new Dialog(CreateNewItemActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting);
        dialog.setCancelable(true);
        dialog.show();

        LinearLayout cancleImageLayout = (LinearLayout) dialog.findViewById(R.id.imageCancel);
        cancleImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(view);
                dialog.dismiss();
            }
        });
        Spinner set_critical_level = (Spinner) dialog.findViewById(R.id.set_critical_level);
        Spinner serial_number_wise_detail = (Spinner) dialog.findViewById(R.id.serial_number_wise_detail);
        Spinner batch_wise_detail = (Spinner) dialog.findViewById(R.id.batch_wise_detail);

        Spinner specify_sales_account = (Spinner) dialog.findViewById(R.id.specify_sales_account);
        Spinner dont_maintain_stock_balance = (Spinner) dialog.findViewById(R.id.dont_maintain_stock_balance);

        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);


        if (appUser.item_set_critical_level.equals("Yes")) {
            set_critical_level.setSelection(1);
        }
        if (appUser.item_serial_number_wise_detail.equals("Yes")) {
            serial_number_wise_detail.setSelection(1);
        }
        if (appUser.item_batch_wise_detail.equals("Yes")) {
            batch_wise_detail.setSelection(1);
        }
        if (appUser.item_specify_sales_account.equals("Yes")) {
            specify_sales_account.setSelection(1);
        }
        if (appUser.item_dont_maintain_stock_balance.equals("Yes")) {
            dont_maintain_stock_balance.setSelection(1);
        }

        set_critical_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    Dialog dialog = new Dialog(CreateNewItemActivity.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_critical_level);
                    dialog.setCancelable(true);
                    dialog.show();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    LinearLayout cancelImage = (LinearLayout) dialog.findViewById(R.id.imageCancel);
                    cancelImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    //spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        serial_number_wise_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        batch_wise_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        specify_sales_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dont_maintain_stock_balance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser.item_set_critical_level = set_critical_level.getSelectedItem().toString();
                appUser.item_serial_number_wise_detail = serial_number_wise_detail.getSelectedItem().toString();
                appUser.item_batch_wise_detail=batch_wise_detail.getSelectedItem().toString();
                appUser.item_specify_sales_account = specify_sales_account.getSelectedItem().toString();
                appUser.item_dont_maintain_stock_balance = dont_maintain_stock_balance.getSelectedItem().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                dialog.dismiss();
            }
        });
*/

    }

   /* public void defaultOptionDialog(View view){
        view.startAnimation(blinkOnClick);

        Dialog dialog = new Dialog(CreateNewItemActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_default_declaration);
        dialog.setCancelable(true);
        dialog.show();

        Spinner default_unit_for_sales= (Spinner) dialog.findViewById(R.id.default_unit_for_sales);
        Spinner default_unit_for_purchase= (Spinner) dialog.findViewById(R.id.default_unit_for_purchase);




        LinearLayout submit= (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout cancelImageLayout = (LinearLayout) dialog.findViewById(R.id.imageCancel);

        if (!appUser.item_default_unit_for_sales.equals("")){
            default_unit_for_sales.setSelection(Integer.parseInt(appUser.item_default_unit_for_sales));
        }
        if (!appUser.item_default_unit_for_purchase.equals("")){
            default_unit_for_purchase.setSelection(Integer.parseInt(appUser.item_default_unit_for_purchase));
        }


        default_unit_for_sales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appUser.item_default_unit_for_sales=String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        default_unit_for_purchase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appUser.item_default_unit_for_purchase=String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancelImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(view);
                dialog.dismiss();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }
*/

    @Subscribe
    public void createitem(CreateItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),ExpandableItemListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.item_group_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mItemGroup.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.item_unit_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mItemUnit.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemUnit.setText("");
            }
        }

        if (requestCode == 3) {
            mArrayList=new ArrayList<>();
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.item_alternate_unit_name = result;
                appUser.item_alternate_unit_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                item_group.setText(result);
                mArrayList.clear();
                mArrayList.add(mItemUnit.getText().toString() + "/" + item_group.getText().toString());
                mArrayList.add(item_group.getText().toString() + "/" + mItemUnit.getText().toString());
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, mArrayList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
                if (result.equals(mItemUnit.getText().toString())) {
                    mConFactorLinear.setVisibility(View.GONE);
                    mStockLinear.setVisibility(View.GONE);
                    mConTypeLinear.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, "same", Toast.LENGTH_SHORT).show();
                    mConFactorLinear.setVisibility(View.VISIBLE);
                    mStockLinear.setVisibility(View.VISIBLE);
                    mConTypeLinear.setVisibility(View.VISIBLE);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                item_group.setText("");
            }
        }


        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                Timber.i("MYID" + id);
                appUser.item_package_unit_detail_id = id;
                appUser.item_package_unit_detail_name = result;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                item_group.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                item_group.setText("");
            }
        }
    }

    private void hideKeyBoard(View view) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Subscribe
    public void editItem(EditItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(this, ExpandableItemListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fromcreategroup", true);
            startActivity(intent);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getedititem(GetItemDetailsResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.edit_item_id=String.valueOf(response.getItem().getData().getAttributes().getId());
            LocalRepositories.saveAppUser(this,appUser);
            mItemName.setText(response.getItem().getData().getAttributes().getName());
            mItemGroup.setText(response.getItem().getData().getAttributes().getItem_group());
            mItemUnit.setText(response.getItem().getData().getAttributes().getItem_unit());
            LocalRepositories.saveAppUser(this,appUser);
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void gettaxcategory(GetTaxCategoryResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.arr_tax_category_name.clear();
            appUser.arr_tax_category_id.clear();
            LocalRepositories.saveAppUser(this,appUser);
            for(int i=0;i<response.getTax_category().getData().size();i++){
                appUser.arr_tax_category_name.add(response.getTax_category().getData().get(i).getAttributes().getName());
                appUser.arr_tax_category_id.add(response.getTax_category().getData().get(i).getId());
                LocalRepositories.saveAppUser(this,appUser);
            }

            mTaxCategoryArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_tax_category_name);
            mTaxCategoryArrayAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mTaxCategory.setAdapter(mTaxCategoryArrayAdapter);
            mTaxCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position==0){
                        mHsnLayout.setVisibility(View.GONE);
                        appUser.item_hsn_number="";
                        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    }else {
                        mHsnLayout.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

}
