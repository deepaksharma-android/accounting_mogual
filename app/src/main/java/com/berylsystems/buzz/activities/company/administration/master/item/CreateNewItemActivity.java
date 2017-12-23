package com.berylsystems.buzz.activities.company.administration.master.item;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
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
import com.berylsystems.buzz.activities.company.administration.master.item_group.ItemGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.taxcategory.TaxCategoryeListActivity;
import com.berylsystems.buzz.activities.company.administration.master.unit.UnitListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.item.CreateItemResponse;
import com.berylsystems.buzz.networks.api_response.item.EditItemResponse;
import com.berylsystems.buzz.networks.api_response.item.GetItemDetailsResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.ParameterConstant;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    @Bind(R.id.tax_category_layout)
    LinearLayout mTaxCategoryLayout;
    @Bind(R.id.item_group)
    TextView mItemGroup;
    @Bind(R.id.item_name)
    TextView mItemName;
    @Bind(R.id.item_unit)
    TextView mItemUnit;
    @Bind(R.id.update)
    LinearLayout mUpdateButton;
    @Bind(R.id.tax_category)
    TextView mTaxCategory;
    Snackbar snackbar;
    Animation blinkOnClick;
    AppUser appUser;
    Double first, second;
    LinearLayout group_layout;

    TextView item_group;
    LinearLayout mConFactorLinear, mStockLinear, mConTypeLinear;
    ArrayList<String> mArrayList;
    Boolean fromitemlist, fromList;
    String title;
    Spinner spinner;
    ArrayAdapter<String> mTaxCategoryArrayAdapter;

    public Boolean boolForItemGroup = false;
    public Boolean boolForUnit = false;
    public static int intStartActivityForResult = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);

        title = "CREATE ITEM";
        fromList = getIntent().getExtras().getBoolean("fromlist");
        if (fromList) {
            title = "EDIT ITEM";
            Preferences.getInstance(getApplicationContext()).setItem_stock_quantity("");
            Preferences.getInstance(getApplicationContext()).setItem_stock_amount("");
            Preferences.getInstance(getApplicationContext()).setItem_stock_value("");
            Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_id("");
            Preferences.getInstance(getApplicationContext()).setitem_conversion_factor("");
            Preferences.getInstance(getApplicationContext()).setitem_conversion_type("");
            Preferences.getInstance(getApplicationContext()).setitem_opening_stock_quantity_alternate("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_applied_on("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_applied_on("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_sales_price_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_alt_unit_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_min_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_mrp("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_main_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_alt_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_self_val_price("");
            Preferences.getInstance(getApplicationContext()).setitem_package_unit_detail_id("");
            Preferences.getInstance(getApplicationContext()).setitem_conversion_factor_pkg_unit("");
            Preferences.getInstance(getApplicationContext()).setitem_salse_price("");
            Preferences.getInstance(getApplicationContext()).setitem_default_unit_for_sales("");
            Preferences.getInstance(getApplicationContext()).setitem_default_unit_for_purchase("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_days("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_days("");
            Preferences.getInstance(getApplicationContext()).setitem_set_critical_level("");
            Preferences.getInstance(getApplicationContext()).setitem_serial_number_wise_detail("");
            Preferences.getInstance(getApplicationContext()).setitem_batch_wise_detail("");
            Preferences.getInstance(getApplicationContext()).setitem_specify_sales_account("");
            Preferences.getInstance(getApplicationContext()).setitem_specify_purchase_account("");
            Preferences.getInstance(getApplicationContext()).setitem_dont_maintain_stock_balance("");
            Preferences.getInstance(getApplicationContext()).setitem_settings_alternate_unit("");
            Preferences.getInstance(getApplicationContext()).setitem_description("");
            Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_name("");
            Preferences.getInstance(getApplicationContext()).setitem_purchase_price("");
            Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_id("");

        }
        fromitemlist = getIntent().getExtras().getBoolean("fromitemlist");
        if (fromitemlist) {
            title = "EDIT ITEM";
            Preferences.getInstance(getApplicationContext()).setItem_stock_quantity("");
            Preferences.getInstance(getApplicationContext()).setItem_stock_amount("");
            Preferences.getInstance(getApplicationContext()).setItem_stock_value("");
            Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_id("");
            Preferences.getInstance(getApplicationContext()).setitem_conversion_factor("");
            Preferences.getInstance(getApplicationContext()).setitem_conversion_type("");
            Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_name("");
            Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_id("");
            Preferences.getInstance(getApplicationContext()).setitem_purchase_price("");
            Preferences.getInstance(getApplicationContext()).setitem_opening_stock_quantity_alternate("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_applied_on("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_applied_on("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_sales_price_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_alt_unit_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_min_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_mrp("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_main_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_alt_edittext("");
            Preferences.getInstance(getApplicationContext()).setitem_price_info_self_val_price("");
            Preferences.getInstance(getApplicationContext()).setitem_package_unit_detail_id("");
            Preferences.getInstance(getApplicationContext()).setitem_conversion_factor_pkg_unit("");
            Preferences.getInstance(getApplicationContext()).setitem_salse_price("");
            Preferences.getInstance(getApplicationContext()).setitem_default_unit_for_sales("");
            Preferences.getInstance(getApplicationContext()).setitem_default_unit_for_purchase("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_days("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_days("");
            Preferences.getInstance(getApplicationContext()).setitem_set_critical_level("");
            Preferences.getInstance(getApplicationContext()).setitem_serial_number_wise_detail("");
            Preferences.getInstance(getApplicationContext()).setitem_batch_wise_detail("");
            Preferences.getInstance(getApplicationContext()).setitem_specify_sales_account("");
            Preferences.getInstance(getApplicationContext()).setitem_specify_purchase_account("");
            Preferences.getInstance(getApplicationContext()).setitem_dont_maintain_stock_balance("");
            Preferences.getInstance(getApplicationContext()).setitem_settings_alternate_unit("");
            Preferences.getInstance(getApplicationContext()).setitem_description("");
            mSubmitButton.setVisibility(View.GONE);
            mUpdateButton.setVisibility(View.VISIBLE);
           /* mTaxCategoryArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_tax_category_name);
            mTaxCategoryArrayAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mTaxCategory.setAdapter(mTaxCategoryArrayAdapter);*/

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

        } /*else {
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
        }
*/

      /*  mTaxCategoryArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_tax_category_name);
        mTaxCategoryArrayAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mTaxCategory.setAdapter(mTaxCategoryArrayAdapter);
        mTaxCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mHsnLayout.setVisibility(View.GONE);
                    appUser.item_hsn_number = "";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    mHsnLayout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_on_click);
        initActionbar();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSubmitButton.startAnimation(blinkOnClick);
                appUser.item_name = mItemName.getText().toString();
                appUser.item_hsn_number = mHsnNumber.getText().toString();
              /*  for (int i = 0; i < appUser.arr_tax_category_name.size(); i++) {
                    if (mTaxCategory.getSelectedItem().toString().equals(appUser.arr_tax_category_name.get(i))) {
                        appUser.item_tax_category = Integer.parseInt(appUser.arr_tax_category_id.get(i));
                        break;
                    }
                }*/
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                if (mItemName.getText().toString().equals("") || mItemUnit.getText().toString().equals("") || mItemGroup.getText().toString().equals("")) {
                    Toast.makeText(CreateNewItemActivity.this, "Name, Group And Unit is mendatory", Toast.LENGTH_SHORT).show();
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
                            appUser.item_hsn_number = mHsnNumber.getText().toString();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                           /* for (int i = 0; i < CreateItemGroupActivity.data.getData().size(); i++) {
                                if (CreateItemGroupActivity.data.getData().get(i).getAttributes().getName().equals(mItemGroup.getText().toString())) {
                                    appUser.item_group_id = String.valueOf(CreateItemGroupActivity.data.getData().get(i).getAttributes().getId());
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    break;
                                }
                            }*/
                            //   for (int i = 0; i < UnitListActivity.data.getData().size(); i++) {
                               /* if (appUser.arr_unitName.get(i).equals(mItemUnit.getText().toString())) {
                                    appUser.item_unit_id = String.valueOf(appUser.arr_unitId.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    break;
                                }*/

                            /*for (int i = 0; i < appUser.arr_tax_category_name.size(); i++) {
                                if (appUser.arr_tax_category_name.get(i).equals(mTaxCategory.getSelectedItem().toString())) {
                                    appUser.item_tax_category = Integer.parseInt(appUser.arr_tax_category_id.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    break;
                                }
                            }*/

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
                intStartActivityForResult = 1;
                ParameterConstant.checkStartActivityResultForItemGroupOfItem = 1;
                ItemGroupListActivity.isDirectForItemGroup = false;
                Intent intent = new Intent(getApplicationContext(), ItemGroupListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 1);
            }
        });

        mUnitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intStartActivityForResult = 2;
                ParameterConstant.checkStartActivityResultForUnitList = 1;
                UnitListActivity.isDirectForUnitList = false;
                Intent intent = new Intent(getApplicationContext(), UnitListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 2);
            }
        });
        mTaxCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TaxCategoryeListActivity.isDirectForTaxCategoryList = false;
                Intent intent = new Intent(getApplicationContext(), TaxCategoryeListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 3);
            }
        });


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_new_item;
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
        actionbarTitle.setText(title);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void openingStock(View view) {
        startActivity(new Intent(getApplicationContext(), ItemOpeningStockActivity.class));
    }

    public void alternateUnitDetails(View view) {
        if (!mItemUnit.getText().toString().equals("")) {
            Intent intent = new Intent(getApplicationContext(), ItemAlternateUnitDetails.class);
            intent.putExtra("unit", mItemUnit.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the unit", Toast.LENGTH_LONG).show();
        }

    }

    public void itemPriceInfo(View view) {

        if (!mItemUnit.getText().toString().equals("")) {
            if (!Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name().equals("")) {
                Intent intent = new Intent(getApplicationContext(), ItemPriceInfoActivity.class);
                intent.putExtra("unit", mItemUnit.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Please enter the alternate unit", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the unit", Toast.LENGTH_LONG).show();
        }
    }

    public void packagingUnitDetail(View view) {
        if (!mItemUnit.getText().toString().equals("")) {
            if (!Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name().equals("")) {
                Intent intent = new Intent(getApplicationContext(), ItemPackagingUnitDetailsActivity.class);
                intent.putExtra("unit", mItemUnit.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Please enter the alternate unit", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the unit", Toast.LENGTH_LONG).show();
        }

    }

    public void itemDescription(View view) {
        startActivity(new Intent(getApplicationContext(), ItemDescriptionActivity.class));

    }

    public void setting(View view) {
        startActivity(new Intent(getApplicationContext(), ItemSettingsActivity.class));

    }


    @Subscribe
    public void createitem(CreateItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), ExpandableItemListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                boolForItemGroup = true;
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.item_group_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mItemGroup.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mItemGroup.setText("");
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                boolForUnit = true;
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.item_unit_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mItemUnit.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mItemUnit.setText("");
            }
        }
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                if (result.equals("None")) {
                    mHsnLayout.setVisibility(View.GONE);
                    appUser.item_hsn_number = "";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    mHsnLayout.setVisibility(View.VISIBLE);
                }
                appUser.item_tax_category = Integer.parseInt(id);
                mTaxCategory.setText(result);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
               // if(!mTaxCategory.getText().toString().equals("")){
                    mTaxCategory.setText(result);
               // }

                /*if(!mTaxCategory.getText().toString().equals("")){
                    mTaxCategory.setText(result);
                }
                else {
                    Snackbar.make(coordinatorLayout,"Add Tax Catagory",Snackbar.LENGTH_LONG).show();
                }*/
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mItemUnit.setText("");
            }
        }
    }

    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        Toast.makeText(this, "resume "+bool, Toast.LENGTH_SHORT).show();
        if (bool) {

            if (intStartActivityForResult == 1) {
                boolForUnit = true;
            } else if (intStartActivityForResult == 2) {
                boolForItemGroup=true;

            }
            if (!boolForItemGroup) {
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.item_group_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mItemGroup.setText(result);

            }
            else if (!boolForUnit) {
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.item_unit_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mItemUnit.setText(result);
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
            finish();
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getedititem(GetItemDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.edit_item_id = String.valueOf(response.getItem().getData().getAttributes().getId());
            LocalRepositories.saveAppUser(this, appUser);

            mItemName.setText(response.getItem().getData().getAttributes().getName());
            mItemGroup.setText(response.getItem().getData().getAttributes().getItem_group());
            mItemUnit.setText(response.getItem().getData().getAttributes().getItem_unit());
            mTaxCategory.setText(response.getItem().getData().getAttributes().getTax_category());
            appUser.item_tax_category = response.getItem().getData().getAttributes().getTax_category_id();
            LocalRepositories.saveAppUser(this, appUser);
           /* String taxcat = response.getItem().getData().getAttributes().getTax_category().trim();// insert code here
            int taxcatindex = -1;*/
          /*  for (int i = 0; i < appUser.arr_tax_category_name.size(); i++) {
                if (appUser.arr_tax_category_name.get(i).equals(taxcat)) {
                    taxcatindex = i;
                    break;
                }
            }
            mTaxCategory.setSelection(taxcatindex);*/

            appUser.item_group_id = String.valueOf(response.getItem().getData().getAttributes().getItem_group_id());
            appUser.item_unit_id = String.valueOf(response.getItem().getData().getAttributes().getItem_unit_id());
            appUser.item_alternate_unit_id = String.valueOf(response.getItem().getData().getAttributes().getAlternate_unit_id());
            if (!response.getItem().getData().getAttributes().getTax_category().equals("None")) {
                mHsnLayout.setVisibility(View.VISIBLE);
                mHsnNumber.setText(response.getItem().getData().getAttributes().getHsn_number());
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getStock_quantity()) != null) {
                Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(String.valueOf(response.getItem().getData().getAttributes().getStock_quantity()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getStock_amount()) != null) {
                Preferences.getInstance(getApplicationContext()).setItem_stock_amount(String.valueOf(response.getItem().getData().getAttributes().getStock_amount()));
            }
            appUser.item_stock_value = String.valueOf(response.getItem().getData().getAttributes().getStock_quantity() * response.getItem().getData().getAttributes().getStock_amount());
            if (String.valueOf(response.getItem().getData().getAttributes().getConversion_factor()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_conversion_factor(String.valueOf(response.getItem().getData().getAttributes().getConversion_factor()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getOpening_stock_quantity_alternate()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_opening_stock_quantity_alternate(String.valueOf(response.getItem().getData().getAttributes().getOpening_stock_quantity_alternate()));

            }
            if (response.getItem().getData().getAttributes().getConversion_type() != null) {
                Preferences.getInstance(getApplicationContext()).setitem_conversion_type(response.getItem().getData().getAttributes().getConversion_type());
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getConversion_factor_package()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_conversion_type(String.valueOf(response.getItem().getData().getAttributes().getConversion_factor_package()));
            }
            if (response.getItem().getData().getAttributes().getItem_package_unit() != null) {
                Preferences.getInstance(getApplicationContext()).setitem_package_unit_detail_name(response.getItem().getData().getAttributes().getItem_package_unit());
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getPurchase_price()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_purchase_price(String.valueOf(response.getItem().getData().getAttributes().getPurchase_price()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getSales_price()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_salse_price(String.valueOf(response.getItem().getData().getAttributes().getSales_price()));
            }
            if (response.getItem().getData().getAttributes().getDefault_unit_for_sales() != null) {
                Preferences.getInstance(getApplicationContext()).setitem_default_unit_for_sales(response.getItem().getData().getAttributes().getDefault_unit_for_sales());
            }
            if (response.getItem().getData().getAttributes().getDefault_unit_for_purchase() != null) {
                Preferences.getInstance(getApplicationContext()).setitem_default_unit_for_purchase(response.getItem().getData().getAttributes().getDefault_unit_for_purchase());
            }
            if (response.getItem().getData().getAttributes().getSales_price_applied_on() != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_applied_on(response.getItem().getData().getAttributes().getSales_price_applied_on());
            }
            if (response.getItem().getData().getAttributes().getPurchase_price_applied_on() != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_applied_on(response.getItem().getData().getAttributes().getPurchase_price_applied_on());
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getSales_price_main()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_sales_price_edittext(String.valueOf(response.getItem().getData().getAttributes().getSales_price_main()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getSales_price_alternate()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_alt_unit_edittext(String.valueOf(response.getItem().getData().getAttributes().getSales_price_alternate()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getMin_sales_price_main()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_main_edittext(String.valueOf(response.getItem().getData().getAttributes().getMin_sales_price_main()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getMin_sale_price_alternate()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_alt_edittext(String.valueOf(response.getItem().getData().getAttributes().getMin_sale_price_alternate()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getPurchase_price_main()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_min_edittext(String.valueOf(response.getItem().getData().getAttributes().getPurchase_price_main()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getPurchase_price_alternate()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_alt_edittext(String.valueOf(response.getItem().getData().getAttributes().getPurchase_price_alternate()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getMrp()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_mrp(String.valueOf(response.getItem().getData().getAttributes().getMrp()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getSelf_value_price()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_self_val_price(String.valueOf(response.getItem().getData().getAttributes().getSelf_value_price()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().isSet_critical_level()) != null) {
                if (!response.getItem().getData().getAttributes().isSet_critical_level()) {
                    Preferences.getInstance(getApplicationContext()).setitem_set_critical_level("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_set_critical_level("Yes");
                }

            }
            if (String.valueOf(response.getItem().getData().getAttributes().isSerial_number_wise_detail()) != null) {
                if (!response.getItem().getData().getAttributes().isSerial_number_wise_detail()) {
                    Preferences.getInstance(getApplicationContext()).setitem_serial_number_wise_detail("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_serial_number_wise_detail("Yes");
                }

            }
            if (String.valueOf(response.getItem().getData().getAttributes().isBatch_wise_detail()) != null) {
                if (!response.getItem().getData().getAttributes().isBatch_wise_detail()) {
                    Preferences.getInstance(getApplicationContext()).setitem_batch_wise_detail("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_batch_wise_detail("Yes");
                }

            }
            if (String.valueOf(response.getItem().getData().getAttributes().isAlternate_unit_detail()) != null) {
                if (!response.getItem().getData().getAttributes().isAlternate_unit_detail()) {
                    Preferences.getInstance(getApplicationContext()).setitem_settings_alternate_unit("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_settings_alternate_unit("Yes");
                }

            }
            if (String.valueOf(response.getItem().getData().getAttributes().isSpecify_sales_account()) != null) {
                if (!response.getItem().getData().getAttributes().isSpecify_sales_account()) {
                    Preferences.getInstance(getApplicationContext()).setitem_specify_sales_account("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_specify_sales_account("Yes");
                }

            }
            if (String.valueOf(response.getItem().getData().getAttributes().isSpecify_purchase_account()) != null) {
                if (!response.getItem().getData().getAttributes().isSpecify_purchase_account()) {
                    Preferences.getInstance(getApplicationContext()).setitem_specify_purchase_account("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_specify_purchase_account("Yes");
                }

            }
            if (String.valueOf(response.getItem().getData().getAttributes().isDont_maintain_stock_balance()) != null) {
                if (!response.getItem().getData().getAttributes().isDont_maintain_stock_balance()) {
                    Preferences.getInstance(getApplicationContext()).setitem_dont_maintain_stock_balance("No");
                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_dont_maintain_stock_balance("Yes");
                }
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getMinimum_level_quantity()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_qty(String.valueOf(response.getItem().getData().getAttributes().getMinimum_level_quantity()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getReorder_level_quantity()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_qty(String.valueOf(response.getItem().getData().getAttributes().getReorder_level_quantity()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getMaximum_level_quantity()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_qty(String.valueOf(response.getItem().getData().getAttributes().getMaximum_level_quantity()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getMinimum_level_days()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_days(String.valueOf(response.getItem().getData().getAttributes().getMinimum_level_days()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getReorder_level_days()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_days(String.valueOf(response.getItem().getData().getAttributes().getReorder_level_days()));
            }
            if (String.valueOf(response.getItem().getData().getAttributes().getMaximum_level_days()) != null) {
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_days(String.valueOf(response.getItem().getData().getAttributes().getMaximum_level_days()));
            }
            if (response.getItem().getData().getAttributes().getItem_description() != null) {
                Preferences.getInstance(getApplicationContext()).setitem_description(String.valueOf(response.getItem().getData().getAttributes().getItem_description()));
            }
            if (response.getItem().getData().getAttributes().getAlternate_unit() != null) {
                Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_name(response.getItem().getData().getAttributes().getAlternate_unit());
            }

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

  /*  @Subscribe
    public void gettaxcategory(GetTaxCategoryResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.arr_tax_category_name.clear();
            appUser.arr_tax_category_id.clear();
            LocalRepositories.saveAppUser(this, appUser);
            for (int i = 0; i < response.getTax_category().getData().size(); i++) {
                appUser.arr_tax_category_name.add(response.getTax_category().getData().get(i).getAttributes().getName());
                appUser.arr_tax_category_id.add(response.getTax_category().getData().get(i).getId());
                LocalRepositories.saveAppUser(this, appUser);
            }

            *//*mTaxCategoryArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_tax_category_name);
            mTaxCategoryArrayAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mTaxCategory.setAdapter(mTaxCategoryArrayAdapter);
            mTaxCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        mHsnLayout.setVisibility(View.GONE);
                        appUser.item_hsn_number = "";
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    } else {
                        mHsnLayout.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*//*
        }
    }*/


}
