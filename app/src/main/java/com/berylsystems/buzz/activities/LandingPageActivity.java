package com.berylsystems.buzz.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.adapters.LandingPageGridAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CompanyAuthenticateResponse;
import com.berylsystems.buzz.networks.api_response.company.CompanyData;
import com.berylsystems.buzz.networks.api_response.company.DeleteCompanyResponse;
import com.berylsystems.buzz.networks.api_response.company.IndustryTypeResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LandingPageActivity extends BaseActivityCompany {
    public static CompanyData data;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    LandingPageGridAdapter mAdapter;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;
    @Bind(R.id.layout_open)
    LinearLayout mOpen;
    @Bind(R.id.layout_edit)
    LinearLayout mEdit;
    @Bind(R.id.layout_delete)
    LinearLayout mDelete;
    @Bind(R.id.layout_backup)
    LinearLayout mBackup;
    @Bind(R.id.openImage)
    ImageView mOPenImage;
    @Bind(R.id.openText)
    TextView mOpenText;
    @Bind(R.id.editImage)
    ImageView mEditImage;
    @Bind(R.id.editText)
    TextView mEditText;
    @Bind(R.id.deleteImage)
    ImageView mDeleteImage;
    @Bind(R.id.deleteText)
    TextView mDeleteText;
    Dialog dialog;


    int[] myImageList = new int[]{R.drawable.icon_administration, R.drawable.icon_transaction, R.drawable.icon_display, R.drawable.icon_printer, R.drawable.icon_favorites};
    private String[] title = {
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
        setNavigationCompany(1);
        setAddCompany(2);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        setAppBarTitleCompany(1,appUser.titlecname);
        mOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AddCompanyActivity.class);
                AddCompanyActivity.data = data;
                startActivity(intent);
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(LandingPageActivity.this)
                        .setTitle("Delete Company")
                        .setMessage("Are you sure you want to delete this company ?")
                        .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                mProgressDialog = new ProgressDialog(LandingPageActivity.this);
                                mProgressDialog.setMessage("Info...");
                                mProgressDialog.setIndeterminate(false);
                                mProgressDialog.setCancelable(true);
                                mProgressDialog.show();
                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_COMPANY);
                            } else {
                                snackbar = Snackbar
                                        .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                        .setAction("RETRY", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Boolean isConnected = ConnectivityReceiver.isConnected();
                                                if (isConnected) {
                                                    snackbar.dismiss();
                                                }
                                            }
                                        });
                                snackbar.show();
                            }

                        })
                        .setNegativeButton(R.string.btn_cancel, null)
                        .show();

            }
        });
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new LandingPageGridAdapter(this, title, myImageList);
        mRecyclerView.setAdapter(mAdapter);



    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }



    @Subscribe
    public void deletecompany(DeleteCompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),ComapanyListActivity.class));
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),ComapanyListActivity.class));
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(LandingPageActivity.this)
                .setTitle("Exit Company")
                .setMessage("Do you want to exit this company ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), ComapanyListActivity.class));

                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }






    @Subscribe
    public void timout(String msg){
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }
}
