package com.berylsystems.buzz.activities.company.purchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.activities.company.sale.CreateSaleActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PurchaseAddItemActivity extends AppCompatActivity {


    @Bind(R.id.item_name)
    EditText mItemName;
    @Bind(R.id.description)
    EditText mDescription;
    @Bind(R.id.quantity)
    EditText mQuantity;
    @Bind(R.id.unit)
    EditText mUnit;
    @Bind(R.id.sr_no)
    EditText mSr_no;
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
    @Bind(R.id.heading)
    TextView heading;
    AppUser appUser;
    List<Map<String, String>> mListMapForItemPurchase;
    Map<String, String> mMap;
    Double first, second, third;
    Intent intent;
    Animation blinkOnClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_add_item);

        appUser = LocalRepositories.getAppUser(this);
        ButterKnife.bind(this);
        mListMapForItemPurchase = new ArrayList<>();
        mMap = new HashMap<>();
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);
        intent = getIntent();
        boolean b = intent.getBooleanExtra("bool", false);
        if (b) {
            heading.setText("EDIT ITEM");
            Integer position = intent.getIntExtra("id", 0);
            appUser = LocalRepositories.getAppUser(this);
            Map m = appUser.mListMapForItemPurchase.get(position);
            Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
            mItemName.setText("" + m.get("item_name"));
            mDescription.setText("" + m.get("description"));
            mQuantity.setText("" + m.get("quantity"));
            mUnit.setText("" + m.get("unit"));
            mSr_no.setText("" + m.get("sr_no"));
            mRate.setText("" + m.get("rate"));
            mDiscount.setText("" + m.get("discount"));
            mValue.setText("" + m.get("value"));
            if (m.get("value")!=null&&m.get("quantity")!=null){
                mTotal.setText("" + m.get("total"));
            }
            mValue.setEnabled(false);
            mTotal.setEnabled(false);
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSubmit.startAnimation(blinkOnClick);
                    mMap.put("item_name", mItemName.getText().toString());
                    mMap.put("description", mDescription.getText().toString());
                    mMap.put("quantity", mQuantity.getText().toString());
                    mMap.put("unit", mUnit.getText().toString());
                    mMap.put("sr_no", mSr_no.getText().toString());
                    mMap.put("rate", mRate.getText().toString());
                    mMap.put("discount", mDiscount.getText().toString());
                    mMap.put("value", mValue.getText().toString());
                    mMap.put("total", mTotal.getText().toString());

                    appUser.mListMapForItemPurchase.set(position, mMap);

                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                    Intent in = new Intent(getApplicationContext(), CreatePurchaseActivity.class);
                    in.putExtra("is", true);
                    startActivity(in);
                    finish();
                }
            });
        } else {
            heading.setText("ADD ITEM");
            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
            String name = intent.getStringExtra("name");
            mItemName.setText(name);
            mItemName.setEnabled(false);
            mValue.setEnabled(false);
            mTotal.setEnabled(false);
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSubmit.startAnimation(blinkOnClick);
                    mMap.put("item_name", mItemName.getText().toString());
                    mMap.put("description", mDescription.getText().toString());
                    mMap.put("quantity", mQuantity.getText().toString());
                    mMap.put("unit", mUnit.getText().toString());
                    mMap.put("sr_no", mSr_no.getText().toString());
                    mMap.put("rate", mRate.getText().toString());
                    mMap.put("discount", mDiscount.getText().toString());
                    mMap.put("value", mValue.getText().toString());
                    mMap.put("total", mTotal.getText().toString());
                    mListMapForItemPurchase.add(mMap);
                    appUser.mListMapForItemPurchase.add(mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Intent in = new Intent(getApplicationContext(), CreatePurchaseActivity.class);
                    in.putExtra("is", true);
                    startActivity(in);
                    finish();
                }
            });
        }

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
                } else {
                    mTotal.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        boolean b = intent.getBooleanExtra("bool", false);
        if (b && ExpandableItemListActivity.comingFrom == 0) {
            Intent intent1 = new Intent(this, CreateSaleActivity.class);
            intent1.putExtra("is", true);
            startActivity(intent1);
            finish();
        } else if (b && ExpandableItemListActivity.comingFrom == 1) {
            finish();
        }
    }

}
