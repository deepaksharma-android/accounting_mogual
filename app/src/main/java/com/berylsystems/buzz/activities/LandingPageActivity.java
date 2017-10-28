package com.berylsystems.buzz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.adapters.LandingPageGridAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LandingPageActivity extends BaseActivity{
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    LandingPageGridAdapter mAdapter;
    int[] myImageList = new int[]{R.drawable.icn_open_company, R.drawable.icon_administration,R.drawable.icon_transaction,R.drawable.icon_display,R.drawable.icon_printer,R.drawable.icon_favorites};
    private String[] title = {
            "Company",
            "Administation",
            "Transaction",
            "Display",
            "Print/Email/SMS",
            "Favourites"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHeading(1);
        setNavigation(1);
        ButterKnife.bind(this);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new LandingPageGridAdapter(this,title,myImageList);
        mRecyclerView.setAdapter(mAdapter);

    }
    public void add(View v){
        startActivity(new Intent(getApplicationContext(),CreateCompantActivity.class));
    }
}
