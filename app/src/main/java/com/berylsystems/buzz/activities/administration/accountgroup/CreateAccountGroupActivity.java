package com.berylsystems.buzz.activities.administration.accountgroup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateAccountGroupActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.under_group_spinner)
    Spinner mSpinnerUnderGroup;
    @Bind(R.id.under_group_layout)
    LinearLayout mUnderGroupLayout;
    @Bind(R.id.primary_spinner)
    Spinner mSpinnerPrimary;
    ArrayAdapter<String> mPrimaryGroupAdapter;
    ArrayAdapter<String> mUnderGroupAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_group);
        ButterKnife.bind(this);
        initActionbar();
        mPrimaryGroupAdapter = new ArrayAdapter<String>(this,
                R.layout.layout_trademark_type_spinner_dropdown_item,getResources().getStringArray(R.array.primary_group));
        mPrimaryGroupAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSpinnerPrimary.setAdapter(mPrimaryGroupAdapter);
        mSpinnerPrimary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    mUnderGroupLayout.setVisibility(View.VISIBLE);
                    mUnderGroupAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.layout_trademark_type_spinner_dropdown_item,getResources().getStringArray(R.array.country));
                    mUnderGroupAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
                    mSpinnerUnderGroup.setAdapter(mUnderGroupAdapter);
                }
                else{
                    mUnderGroupLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
}