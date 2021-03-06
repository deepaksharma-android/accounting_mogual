package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.unit.UnitListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemAlternateUnitDetails extends AppCompatActivity {
    @Bind(R.id.alternate_unit)
    TextView mAlternateUnit;
    @Bind(R.id.con_factor)
    EditText mConFactor;
    @Bind(R.id.spinner_con_factor)
    Spinner mSpinnerConFactor;
    @Bind(R.id.stock_quantity)
    EditText mStockQuantity;
    @Bind(R.id.alternate_unit_layout)
    LinearLayout mAlternateUnitLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    @Bind(R.id.conFactorLinear)
    LinearLayout mConTypeLayout;
    AppUser appUser;
    ArrayList<String> mArrayList;
    String item_unit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_alternate_unit);
        ButterKnife.bind(this);
        appUser= LocalRepositories.getAppUser(this);
        item_unit=getIntent().getStringExtra("unit");
        initActionbar();
        mAlternateUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnitListActivity.isDirectForUnitList=false;
                Intent intent = new Intent(getApplicationContext(), UnitListActivity.class);
                intent.putExtra("frommaster", false);
               // ParameterConstant.checkStartActivityResultForUnitList=3;
                startActivityForResult(intent, 3);
            }
        });
        if(appUser.arr_con_type!=null) {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, appUser.arr_con_type);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerConFactor.setAdapter(dataAdapter);

            if (!Preferences.getInstance(getApplicationContext()).getitem_conversion_type().equals("")) {
                String contype =Preferences.getInstance(getApplicationContext()).getitem_conversion_type();// insert code here
                int contypeindex = -1;
                for (int i = 0; i < appUser.arr_con_type.size(); i++) {
                    if (appUser.arr_con_type.get(i).equals(contype)) {
                        contypeindex = i;
                        break;
                    }
                }
                mSpinnerConFactor.setSelection(contypeindex);
            }
        }




        if(!Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name().equals("")){
            mAlternateUnit.setText(Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name());
            if(Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name().equals(item_unit)){
                mConTypeLayout.setVisibility(View.GONE);
            }
            else{
                mConTypeLayout.setVisibility(View.VISIBLE);
            }
        }
        if( !Preferences.getInstance(getApplicationContext()).getitem_opening_stock_quantity_alternate().equals("")){
            mStockQuantity.setText(Preferences.getInstance(getApplicationContext()).getitem_opening_stock_quantity_alternate());
        }

        if( !Preferences.getInstance(getApplicationContext()).getitem_conversion_factor().equals("")){
            mConFactor.setText(Preferences.getInstance(getApplicationContext()).getitem_conversion_factor());
        }




        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!item_unit.equals(Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name())) {
                    if (!mAlternateUnit.getText().toString().equals("")) {
                        Preferences.getInstance(getApplicationContext()).setitem_conversion_factor(mConFactor.getText().toString());
                        Preferences.getInstance(getApplicationContext()).setitem_conversion_type(mSpinnerConFactor.getSelectedItem().toString());
                        Preferences.getInstance(getApplicationContext()).setitem_opening_stock_quantity_alternate(mStockQuantity.getText().toString());
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Enter the alternate unit", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    finish();
                }
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("ALTERNATE UNIT DETAILS");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == 3) {
            mArrayList=new ArrayList<>();
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                //appUser.item_alternate_unit_name = result;
                Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_name(result);
                Preferences.getInstance(getApplicationContext()).setitem_alternate_unit_id(id);
                mAlternateUnit.setText(result);
                mArrayList.clear();
                appUser.arr_con_type.clear();
                mArrayList.add(mAlternateUnit.getText().toString() + "/" + item_unit);
                mArrayList.add(item_unit + "/" + mAlternateUnit.getText().toString());
                appUser.arr_con_type=mArrayList;
                LocalRepositories.saveAppUser(this,appUser);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, mArrayList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerConFactor.setAdapter(dataAdapter);
                if (result.equals(item_unit)) {
                   mConTypeLayout.setVisibility(View.GONE);
                } else {
                    mConTypeLayout.setVisibility(View.VISIBLE);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mAlternateUnit.setText("");
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }



}