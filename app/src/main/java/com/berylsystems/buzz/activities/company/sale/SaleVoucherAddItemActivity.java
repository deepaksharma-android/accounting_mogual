package com.berylsystems.buzz.activities.company.sale;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SaleVoucherAddItemActivity extends AppCompatActivity {

    @Bind(R.id.item_name)
    TextView mItemName;
    @Bind(R.id.item_layout)
    LinearLayout mItemLayout;
    @Bind(R.id.description)
    TextView mDescription;
    @Bind(R.id.quantity)
    EditText mQuantity;
    @Bind(R.id.spinner_unit)
    Spinner mSpinnerUnit;
    @Bind(R.id.sr_no)
    TextView mSr_no;
    @Bind(R.id.serail_number_layout)
    LinearLayout mSerialNumberLayout;
    @Bind(R.id.rate)
    EditText mRate;
    @Bind(R.id.discount)
    EditText mDiscount;
    @Bind(R.id.value)
    EditText mValue;
    @Bind(R.id.total)
    EditText mTotal;
    @Bind(R.id.submit)
    LinearLayout mSubmit;

    AppUser appUser;
    List<Map<String, String>> mListMap;
    Map<String, String> mMap;
    Double first, second, third;
    Intent intent;
    Animation blinkOnClick;
    ArrayList<String> mUnitList;
    ArrayAdapter<String> mUnitAdapter;
    String serial = "";
    String sales_price_applied_on;
    String price_selected_unit;
    String alternate_unit_con_factor;
    String packaging_unit_con_factor;
    String mrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_voucher_add_item);
        appUser = LocalRepositories.getAppUser(this);
        ButterKnife.bind(this);
        initActionbar();
        mListMap = new ArrayList<>();
        mMap = new HashMap<>();
        mUnitList = new ArrayList<>();
        mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ExpandableItemListActivity.class));
            }
        });




       /* intent = getIntent();
        boolean b = intent.getBooleanExtra("bool", false);
        if (b) {
            heading.setText("EDIT ITEM");
        } else {
            heading.setText("ADD ITEM");
        }*/
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);

        CreateSaleActivity.hideKeyPad(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        String main_unit = intent.getStringExtra("main_unit");
        String alternate_unit = intent.getStringExtra("alternate_unit");
        String sales_price_main = intent.getStringExtra("sales_price_main");
        String sales_price_alternate = intent.getStringExtra("sales_price_alternate");
        Boolean serailwise = intent.getExtras().getBoolean("serial_wise");
        Boolean batchwise = intent.getExtras().getBoolean("batch_wise");
        String packaging_unit = intent.getStringExtra("packaging_unit");
        String default_unit = intent.getStringExtra("default_unit");
        String packaging_unit_sales_price = intent.getStringExtra("packaging_unit_sales_price");
        mrp = intent.getStringExtra("mrp");
        packaging_unit_con_factor = intent.getStringExtra("packaging_unit_con_factor");
        sales_price_applied_on = intent.getStringExtra("applied");
        alternate_unit_con_factor = intent.getStringExtra("alternate_unit_con_factor");


        mSerialNumberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (batchwise && !serailwise) {
                    serial = "1";
                } else if (!batchwise && serailwise) {
                    if (mQuantity.getText().toString().equals("")) {
                        serial = "0";
                    } else {
                        serial = mQuantity.getText().toString();
                    }

                } else {
                    serial = "0";
                }
                if (!serial.equals("0")) {
                    Dialog dialogbal = new Dialog(SaleVoucherAddItemActivity.this);
                    dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialogbal.setContentView(R.layout.dialog_serail);
                    dialogbal.setCancelable(true);
                    LinearLayout serialLayout = (LinearLayout) dialogbal.findViewById(R.id.main_layout);
                    LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogbal.dismiss();
                        }
                    });
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    EditText[] pairs = new EditText[Integer.parseInt(serial)];
                    for (int l = 0; l < Integer.parseInt(serial); l++) {
                        pairs[l] = new EditText(getApplicationContext());
                        pairs[l].setHeight(50);
                        pairs[l].setPadding(10, 10, 10, 0);
                        pairs[l].setInputType(InputType.TYPE_CLASS_NUMBER);
                        pairs[l].setWidth(500);
                        pairs[l].setTextSize(15);
                        pairs[l].setLayoutParams(lp);
                        pairs[l].setId(l);
                        //pairs[l].setText((l + 1) + ": something");
                        serialLayout.addView(pairs[l]);
                    }
                    dialogbal.show();

                }
            }

        });
        mItemName.setText(name);
        mDescription.setText(desc);
        mUnitList.add("Main Unit : " + main_unit);
        mUnitList.add("Alternate Unit :" + alternate_unit);
        if (!packaging_unit.equals("")) {
            mUnitList.add("Packaging Unit :" + packaging_unit);
        }


        mItemName.setEnabled(false);
        mValue.setEnabled(false);
        mTotal.setEnabled(false);
        mUnitAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mUnitList);
        mUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerUnit.setAdapter(mUnitAdapter);
        if (!packaging_unit.equals("")) {
            if (default_unit.equals("Main Unit")) {
                mSpinnerUnit.setSelection(0);
            } else if (default_unit.equals("Alt. Unit")) {
                mSpinnerUnit.setSelection(1);
            } else if (default_unit.equals("Pckg. Unit")) {
                mSpinnerUnit.setSelection(2);
            } else {
                mSpinnerUnit.setSelection(0);
            }
        } else {
            if (default_unit.equals("Main Unit")) {
                mSpinnerUnit.setSelection(0);
            } else if (default_unit.equals("Alt. Unit")) {
                mSpinnerUnit.setSelection(1);
            } else {
                mSpinnerUnit.setSelection(0);
            }
        }
        if (!packaging_unit.equals("")) {
            mSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (i == 2) {
                        price_selected_unit = "packaging";
                        if (default_unit.equals("Pckg. Unit")) {
                            mRate.setText(String.valueOf(packaging_unit_sales_price));
                        }

                           /* if (sales_price_applied_on.equals("Main Unit")) {
                                Double main_unit_price = Double.parseDouble(packaging_unit_sales_price) / Double.parseDouble(packaging_unit_con_factor);
                                mRate.setText(String.valueOf(main_unit_price));

                            } else if (sales_price_applied_on.equals("Alternate Unit")) {
                                Double main_unit_price = Double.parseDouble(packaging_unit_sales_price) / Double.parseDouble(packaging_unit_con_factor);
                                Double alternate_unit_price = main_unit_price / Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(alternate_unit_price));
                            }*/

                    }
                    if (i == 0) {
                        price_selected_unit = "main";
                        if (default_unit.equals("Pckg. Unit")) {
                            Double main_unit_price = Double.parseDouble(packaging_unit_sales_price) / Double.parseDouble(packaging_unit_con_factor);
                            mRate.setText(String.valueOf(main_unit_price));
                        } else {
                            if (sales_price_applied_on.equals("Alternate Unit")) {
                                Double main_unit_price = Double.parseDouble(sales_price_alternate) * Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(main_unit_price));
                            } else {
                                mRate.setText(sales_price_main);
                            }
                        }
                    } else if (i == 1) {
                        price_selected_unit = "alternate";
                        if (default_unit.equals("Pckg. Unit")) {
                            Double main_unit_price = Double.parseDouble(packaging_unit_sales_price) / Double.parseDouble(packaging_unit_con_factor);
                            Double alternate_unit_price = main_unit_price / Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(alternate_unit_price));
                        } else {
                            if (sales_price_applied_on.equals("Main Unit")) {
                                Double alternate_unit_price = Double.parseDouble(sales_price_main) / Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(alternate_unit_price));
                            } else {
                                mRate.setText(sales_price_alternate);
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else {
            mSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        price_selected_unit = "main";
                        if (sales_price_applied_on.equals("Alternate Unit")) {
                            Double main_unit_price = Double.parseDouble(sales_price_alternate) * Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(main_unit_price));
                        } else {
                            mRate.setText(sales_price_main);
                        }
                    } else if (i == 1) {
                        price_selected_unit = "alternate";
                        if (sales_price_applied_on.equals("Main Unit")) {
                            Double alternate_unit_price = Double.parseDouble(sales_price_main) / Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(alternate_unit_price));
                        } else {
                            mRate.setText(sales_price_alternate);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubmit.startAnimation(blinkOnClick);
                mMap.put("item_name", mItemName.getText().toString());
                mMap.put("description", mDescription.getText().toString());
                mMap.put("quantity", mQuantity.getText().toString());
                mMap.put("unit", mSpinnerUnit.getSelectedItem().toString());
                mMap.put("sr_no", mSr_no.getText().toString());
                mMap.put("rate", mRate.getText().toString());
                mMap.put("discount", mDiscount.getText().toString());
                mMap.put("value", mValue.getText().toString());
                mMap.put("total", mTotal.getText().toString());
                mMap.put("applied", sales_price_applied_on);
                mMap.put("price_selected_unit", price_selected_unit);
                mMap.put("alternate_unit_con_factor", alternate_unit_con_factor);
                mMap.put("packaging_unit_con_factor", packaging_unit_con_factor);
                mMap.put("mrp", mrp);
                // mListMap.add(mMap);
                appUser.mListMapForItemSale.add(mMap);
                // appUser.mListMap = mListMap;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                Intent in = new Intent(getApplicationContext(), CreateSaleActivity.class);
                in.putExtra("is", true);
                startActivity(in);
                finish();
            }
        });

        mRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mDiscount.getText().toString().isEmpty()) {
                    if (!mRate.getText().toString().isEmpty()) {
                        second = Double.valueOf(mRate.getText().toString());
                        if (!mDiscount.getText().toString().isEmpty()) {
                            first = Double.valueOf(mDiscount.getText().toString());
                            mValue.setText("" + (first * second));
                        }
                    } else {
                        mValue.setText("");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mDiscount.getText().toString().isEmpty()) {
                    first = Double.valueOf(mDiscount.getText().toString());
                    if (!mRate.getText().toString().isEmpty()) {
                        second = Double.valueOf(mRate.getText().toString());
                        mValue.setText("" + (first * second) / 100);
                    }
                } else {
                    mValue.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mValue.getText().toString().isEmpty()) {
                    if (!mQuantity.getText().toString().isEmpty()) {
                        second = Double.valueOf(mQuantity.getText().toString());
                        if (!mValue.getText().toString().isEmpty()) {
                            first = Double.valueOf(mValue.getText().toString());
                            third = Double.valueOf(mRate.getText().toString());
                            mTotal.setText("" + (third - first) * second);
                        }
                    } else {
                        mTotal.setText("");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mValue.getText().toString().isEmpty()) {
                    if (!mQuantity.getText().toString().isEmpty()) {
                        second = Double.valueOf(mQuantity.getText().toString());
                        if (!mValue.getText().toString().isEmpty()) {
                            first = Double.valueOf(mValue.getText().toString());
                            third = Double.valueOf(mRate.getText().toString());
                            mTotal.setText("" + (third - first) * second);
                        }
                    } else {
                        mTotal.setText("");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
        actionbarTitle.setText("SALE VOUCHER ADD ITEM");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        boolean b = intent.getBooleanExtra("bool", false);
        if (b) {
            Intent intent1 = new Intent(this, CreateSaleActivity.class);
            intent1.putExtra("is", true);
            startActivity(intent1);
            finish();
        } else {
            Intent intent2 = new Intent(this, ExpandableItemListActivity.class);
            startActivity(intent2);
            finish();
        }
    }

}
