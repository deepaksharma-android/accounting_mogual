package com.berylsystems.buzz.activities.company.administration.master.item;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.method.TimeKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.AccountGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.item_group.ItemGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.unit.UnitListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.account.CreateAccountResponse;
import com.berylsystems.buzz.networks.api_response.item.CreateItemResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.EditItemGroupResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.Subscribe;

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

    @Bind(R.id.item_unit)
    TextView mItemUnit;

    @Bind(R.id.item_name)
    EditText itemName;

    Animation blinkOnClick;

    AppUser appUser;

    Double first, second, third;


    LinearLayout group_layout;

    TextView item_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();

        mProgressDialog = new ProgressDialog(this);
        appUser = LocalRepositories.getAppUser(this);
        appUser.item_stock_quantity = "";
        appUser.item_stock_amount = "";
        appUser.item_stock_value = "";

        appUser.item_conversion_factor = "";
        appUser.item_stock_quantity = "";

        appUser.item_conversion_factor_pkg_unit = "";
        appUser.item_salse_price = "";

        appUser.item_description = "";

        LocalRepositories.saveAppUser(this, appUser);


        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSubmitButton.startAnimation(blinkOnClick);
                appUser.item_name = itemName.getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_ITEM);
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
        view.startAnimation(blinkOnClick);

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
        });
    }

    public void alternateUnitDetails(View view) {
        view.startAnimation(blinkOnClick);
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
        EditText item_stock_quantity = (EditText) dialog.findViewById(R.id.stock_quantity);

        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);

        if (!appUser.item_conversion_factor.equals("")) {
            item_conversion_factor.setText(appUser.item_conversion_factor);
        }
        if (!appUser.item_stock_quantity.equals("")) {
            item_stock_quantity.setText(appUser.item_stock_quantity);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.item_conversion_factor = item_conversion_factor.getText().toString();
                appUser.item_stock_quantity = item_stock_quantity.getText().toString();
                if (!appUser.item_conversion_factor.equals("")) {
                    item_conversion_factor.setText(appUser.item_conversion_factor);
                }
                if (!appUser.item_stock_quantity.equals("")) {
                    item_stock_quantity.setText(appUser.item_stock_quantity);
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

    public void itemPriceInfo(View view) {
        view.startAnimation(blinkOnClick);
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
        });
    }

    public void packagingUnitDetail(View view) {
        view.startAnimation(blinkOnClick);
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

        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);

        if (!appUser.item_conversion_factor_pkg_unit.equals("")) {
            item_conversion_factor_pkg_unit.setText(appUser.item_conversion_factor_pkg_unit);
        }
        if (!appUser.item_salse_price.equals("")) {
            item_sales_price.setText(appUser.item_salse_price);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.item_conversion_factor_pkg_unit = item_conversion_factor_pkg_unit.getText().toString();
                appUser.item_salse_price = item_sales_price.getText().toString();

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
        });
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

        view.startAnimation(blinkOnClick);

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

        Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {

                } else {
                    Dialog dialog = new Dialog(CreateNewItemActivity.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_critical_level);
                    dialog.setCancelable(true);
                    dialog.show();
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
                mItemGroup.setText("");
            }
        }

        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.item_alternate_unit_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                item_group.setText(result);
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
    public void editItem(EditItemGroupResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(this, ItemGroupListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fromcreategroup", true);
            startActivity(intent);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
}
