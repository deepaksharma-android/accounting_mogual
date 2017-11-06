package com.berylsystems.buzz.activities.company.administration.master.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ExpandableListView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.adapters.AccountExpandableListAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExpandableAccountListActivity extends BaseActivityCompany {
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    AccountExpandableListAdapter listAdapter;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_expandabl_list);
        ButterKnife.bind(this);
    }
}