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
import com.berylsystems.buzz.utils.Preferences;

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
    Boolean fromitemlist,fromList;
    String title;
    Spinner spinner;
    ArrayAdapter<String> mTaxCategoryArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);
        fromList = getIntent().getExtras().getBoolean("fromlist");
        if(fromList){
           /* appUser.item_name="";
            appUser.item_group_id="";
            appUser.item_unit_id="";
            appUser.item_tax_category=-1;
            appUser.item_hsn_number="";
            Preferences.getInstance(getApplicationContext()).setItem_stock_quantity("");
            Preferences.getInstance(getApplicationContext()).setItem_stock_amount("");
            appUser.item_alternate_unit_id="";
            appUser.item_conversion_factor="";
            appUser.item_conversion_type="";
            appUser.item_opening_stock_quantity_alternate="";
            appUser.item_price_info_sale_price_applied_on="";
            appUser.item_price_info_purchase_price_applied_on="";
            appUser.item_price_info_sales_price_edittext="";
            appUser.item_price_info_sale_price_alt_unit_edittext="";
            appUser.item_price_info_purchase_price_min_edittext="";
            appUser.item_price_mrp="";
            appUser.item_price_info_min_sale_price_main_edittext="";
            appUser.item_price_info_min_sale_price_alt_edittext="";
            appUser.item_price_info_self_val_price="";
            appUser.item_package_unit_detail_id="";
            appUser.item_conversion_factor_pkg_unit="";
            appUser.item_salse_price="";
            appUser.item_specify_purchase_account="";
            appUser.item_default_unit_for_sales="";
            appUser.item_default_unit_for_purchase="";
            appUser.item_setting_critical_min_level_qty="";
            appUser.item_setting_critical_recorded_level_qty="";
            appUser.item_setting_critical_max_level_qty="";
            appUser.item_setting_critical_max_level_days="";
            appUser.item_setting_critical_recorded_level_days="";
            appUser.item_setting_critical_max_level_days="";
            appUser.item_set_critical_level="";
            appUser.item_serial_number_wise_detail="";
            appUser.item_batch_wise_detail="";
            appUser.item_specify_sales_account="";
            appUser.item_specify_purchase_account="";
            appUser.item_dont_maintain_stock_balance="";
            appUser.item_settings_alternate_unit="";
            appUser.item_description="";
            LocalRepositories.saveAppUser(this,appUser);*/
        }
        fromitemlist=getIntent().getExtras().getBoolean("fromitemlist");
        if(fromitemlist){
            title="EDIT ITEM";
           /* appUser.item_name="";
            appUser.item_group_id="";
            appUser.item_unit_id="";
            appUser.item_tax_category=-1;
            appUser.item_hsn_number="";
            Preferences.getInstance(getApplicationContext()).setItem_stock_quantity("");
            Preferences.getInstance(getApplicationContext()).setItem_stock_amount("");
            appUser.item_alternate_unit_id="";
            appUser.item_conversion_factor="";
            appUser.item_conversion_type="";
            appUser.item_opening_stock_quantity_alternate="";
            appUser.item_price_info_sale_price_applied_on="";
            appUser.item_price_info_purchase_price_applied_on="";
            appUser.item_price_info_sales_price_edittext="";
            appUser.item_price_info_sale_price_alt_unit_edittext="";
            appUser.item_price_info_purchase_price_min_edittext="";
            appUser.item_price_mrp="";
            appUser.item_price_info_min_sale_price_main_edittext="";
            appUser.item_price_info_min_sale_price_alt_edittext="";
            appUser.item_price_info_self_val_price="";
            appUser.item_package_unit_detail_id="";
            appUser.item_conversion_factor_pkg_unit="";
            appUser.item_salse_price="";
            appUser.item_specify_purchase_account="";
            appUser.item_default_unit_for_sales="";
            appUser.item_default_unit_for_purchase="";
            appUser.item_setting_critical_min_level_qty="";
            appUser.item_setting_critical_recorded_level_qty="";
            appUser.item_setting_critical_max_level_qty="";
            appUser.item_setting_critical_max_level_days="";
            appUser.item_setting_critical_recorded_level_days="";
            appUser.item_setting_critical_max_level_days="";
            appUser.item_set_critical_level="";
            appUser.item_serial_number_wise_detail="";
            appUser.item_batch_wise_detail="";
            appUser.item_specify_sales_account="";
            appUser.item_specify_purchase_account="";
            appUser.item_dont_maintain_stock_balance="";
            appUser.item_settings_alternate_unit="";
            appUser.item_description="";
            LocalRepositories.saveAppUser(this,appUser);*/
            mSubmitButton.setVisibility(View.GONE);
            mUpdateButton.setVisibility(View.VISIBLE);
            mTaxCategoryArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_tax_category_name);
            mTaxCategoryArrayAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mTaxCategory.setAdapter(mTaxCategoryArrayAdapter);
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
        else {
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
                            appUser.item_hsn_number=mHsnNumber.getText().toString();

                            for (int i = 0; i < appUser.arr_item_group_name.size(); i++) {
                                if (appUser.arr_item_group_name.get(i).equals(mItemGroup.getText().toString())) {
                                    appUser.item_group_id = String.valueOf(appUser.arr_item_group_id.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    break;
                                }
                            }
                            for (int i = 0; i < appUser.arr_unitName.size(); i++) {
                                if (appUser.arr_unitName.get(i).equals(mItemUnit.getText().toString())) {
                                    appUser.item_unit_id = String.valueOf(appUser.arr_unitId.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    break;
                                }
                            }
                            for (int i = 0; i < appUser.arr_tax_category_name.size(); i++) {
                                if (appUser.arr_tax_category_name.get(i).equals(mTaxCategory.getSelectedItem().toString())) {
                                    appUser.item_tax_category = Integer.parseInt(appUser.arr_tax_category_id.get(i));
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

    }

    public void itemPriceInfo(View view) {

        if(!mItemUnit.getText().toString().equals("")) {
            if(appUser.item_alternate_unit_id!=null) {
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
    }

    public void packagingUnitDetail(View view) {
        startActivity(new Intent(getApplicationContext(),ItemPackagingUnitDetailsActivity.class));

    }

    public void itemDescription(View view) {
        startActivity(new Intent(getApplicationContext(),ItemDescriptionActivity.class));

    }

    public void setting(View view) {
        startActivity(new Intent(getApplicationContext(),ItemSettingsActivity.class));

    }


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
                mItemGroup.setText("");
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
                mItemUnit.setText("");
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
            String taxcat = response.getItem().getData().getAttributes().getTax_category().trim();// insert code here
            int taxcatindex = -1;
            for (int i = 0; i <appUser.arr_tax_category_name.size(); i++) {
                if (appUser.arr_tax_category_name.get(i).equals(taxcat)) {
                    taxcatindex = i;
                    break;
                }
            }
            mTaxCategory.setSelection(taxcatindex);
            if(!response.getItem().getData().getAttributes().getTax_category().equals("None")){
                mHsnLayout.setVisibility(View.VISIBLE);
                mHsnNumber.setText(response.getItem().getData().getAttributes().getHsn_number());
            }
            if(String.valueOf(response.getItem().getData().getAttributes().getStock_quantity())!=null){
                Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(String.valueOf(response.getItem().getData().getAttributes().getStock_quantity()));
            }
            if(String.valueOf(response.getItem().getData().getAttributes().getStock_amount())!=null){
                Preferences.getInstance(getApplicationContext()).setItem_stock_amount(String.valueOf(response.getItem().getData().getAttributes().getStock_amount()));
            }
            appUser.item_stock_value= String.valueOf(response.getItem().getData().getAttributes().getStock_quantity()*response.getItem().getData().getAttributes().getStock_amount());
            if(String .valueOf(response.getItem().getData().getAttributes().getConversion_factor())!=null){
                appUser.item_conversion_factor=String .valueOf(response.getItem().getData().getAttributes().getConversion_factor());
            }
            if(String.valueOf(response.getItem().getData().getAttributes().getOpening_stock_quantity_alternate())!=null){
                appUser.item_opening_stock_quantity_alternate=String .valueOf(response.getItem().getData().getAttributes().getOpening_stock_quantity_alternate());
            }
            if(response.getItem().getData().getAttributes().getConversion_type()!=null){
                appUser.item_conversion_type=response.getItem().getData().getAttributes().getConversion_type();
            }

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
