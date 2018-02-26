package com.lkintechnology.mBilling.activities.company.transaction.receiptvoucher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.adapters.GetSaleVoucherListAdapter;
import com.lkintechnology.mBilling.adapters.ReceiptAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReceiptActivity extends AppCompatActivity {

    @Bind(R.id.total_ammount)
    TextView total_ammount;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt2);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        total_ammount.setText(appUser.totalamount);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        List<String> list=new ArrayList();


        ReceiptAdapter mAdapter = new ReceiptAdapter(this,list);
        mRecyclerView.setAdapter(mAdapter);
    }
}
