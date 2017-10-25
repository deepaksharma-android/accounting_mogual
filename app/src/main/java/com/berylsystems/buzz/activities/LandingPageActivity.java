package com.berylsystems.buzz.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.adapters.LandingPageGridAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LandingPageActivity extends BaseActivity{
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    LandingPageGridAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new LandingPageGridAdapter(this,null);
        mRecyclerView.setAdapter(mAdapter);

    }
}
