package com.berylsystems.buzz.activities.company.administration.master.unit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.company.administration.master.materialcentregroup.CreateMaterialCentreGroupActivity;
import com.berylsystems.buzz.adapters.UnitConversionListAdapter;
import com.berylsystems.buzz.adapters.UnitListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UnitListActivity  extends BaseActivityCompany {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.unit_list_recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    UnitListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);
        ButterKnife.bind(this);
        setAddCompany(1);
        setAppBarTitleCompany(1,"UNIT LIST");
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new UnitListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void add(View v) {
        Intent intent=new Intent(getApplicationContext(), CreateUnitActivity.class);
        startActivity(intent);
    }
}