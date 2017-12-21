package com.berylsystems.buzz.fragments.company;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.SignatureActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.Helpers;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CompanySignatureFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.add_sign)
    LinearLayout mAddSign;
    @Bind(R.id.submit)
    TextView mSubmit;
    @Bind(R.id.image_signature)
    ImageView mSignatureImage;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    public CompanySignatureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_company_signature, container, false);
        ButterKnife.bind(this,v);
        appUser = LocalRepositories.getAppUser(getActivity());
       /* if(appUser.signature!=null) {
            Bitmap image = Helpers.base64ToBitmap(appUser.signature);
            mSignatureImage.setImageBitmap(image);
        }*/
        if(!Preferences.getInstance(getActivity()).getCsign().equals("")){
            Glide.with(getActivity())
                    .load(Preferences.getInstance(getActivity()).getCsign())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(mSignatureImage);
        }
        mAddSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SignatureActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if(isConnected) {
                    mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getActivity(), appUser);
                    ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_SIGNATURE);
                }
                else{
                    snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if(isConnected){
                                        snackbar.dismiss();
                                    }
                                }
                            });
                    snackbar.show();
                }


            }
        });

        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra("image");
            Timber.i("Result"+result);
            Bitmap image=Helpers.base64ToBitmap(result);
            mSignatureImage.setImageBitmap(image);
            appUser.signature= result;;
            LocalRepositories.saveAppUser(getActivity(),appUser);
        }
    }
    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void createCompany(CreateCompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){

            appUser.cid= String.valueOf(response.getId());
            LocalRepositories.saveAppUser(getActivity(),appUser);
            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(6).select();
            //startActivity(new Intent(getApplicationContext(),CompanyDashboardActivity.class));
            snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();

        }
        else {
            snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}