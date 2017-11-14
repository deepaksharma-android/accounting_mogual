package com.berylsystems.buzz.activities.company.administration.master.materialcentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.company.administration.master.item.CreateItemActivity;
import com.berylsystems.buzz.adapters.ItemListAdapter;
import com.berylsystems.buzz.adapters.MaterialCentreListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaterialCentreListActivity extends BaseActivityCompany {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.material_centre_list_recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    MaterialCentreListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_centre_list);
        ButterKnife.bind(this);
        setAddCompany(1);
        setAppBarTitleCompany(1,"MATERIAL CENTRE LIST");
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MaterialCentreListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void add(View v) {
        Intent intent=new Intent(getApplicationContext(), CreateMaterialCentreActivity.class);
        startActivity(intent);
    }
}