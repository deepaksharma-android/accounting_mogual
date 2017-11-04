package com.berylsystems.buzz.fragments.company;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CompanyGstFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.gst)
    EditText mGst;
    @Bind(R.id.dealer_spinner)
    Spinner mDealerSpinner;
    @Bind(R.id.default1)
    EditText mDefaultTax1;
    @Bind(R.id.default2)
    EditText mDefaultTax2;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.tax2)
    LinearLayout mTax2;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    ArrayAdapter<String> spinnerAdapter;
    public CompanyGstFragment() {
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
        View v= inflater.inflate(R.layout.company_fragment_gst, container, false);
        ButterKnife.bind(this,v);
        appUser = LocalRepositories.getAppUser(getActivity());
        spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_trademark_type_spinner_dropdown_item,getResources().getStringArray(R.array.dealer));
        spinnerAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mDealerSpinner.setAdapter(spinnerAdapter);
        mGst.setText(Preferences.getInstance(getActivity()).getCgst());
        mDefaultTax1.setText(Preferences.getInstance(getActivity()).getCtax1());
        mDefaultTax2.setText(Preferences.getInstance(getActivity()).getCtax2());
        if(!Preferences.getInstance(getActivity()).getCdealer().equals("")) {
            String dealername = Preferences.getInstance(getActivity()).getCdealer().trim();
            if(dealername.equals("Regular")){
                mTax2.setVisibility(View.GONE);
            }
            else{
                mTax2.setVisibility(View.VISIBLE);
            }
            int index = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.dealer).length; i++) {
                if (getResources().getStringArray(R.array.dealer)[i].equals(dealername)) {
                    index = i;
                    break;
                }
            }
            Timber.i("INDEX" + index);
            mDealerSpinner.setSelection(index);
        }
        mDealerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    mDefaultTax2.setText("");
                    mTax2.setVisibility(View.GONE);
                }
                else{
                    mTax2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if(isConnected) {
                    appUser.gst=mGst.getText().toString();
                    appUser.type_of_dealer=mDealerSpinner.getSelectedItem().toString();
                    appUser.default_tax_rate1=mDefaultTax1.getText().toString();
                    appUser.default_tax_rate2=mDefaultTax2.getText().toString();
                    LocalRepositories.saveAppUser(getActivity(),appUser);
                    mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getActivity(), appUser);
                    ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_GST);
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
            tabhost.getTabAt(3).select();
            //startActivity(new Intent(getApplicationContext(),LandingPageActivity.class));
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
    @Subscribe
    public void timout(String msg){
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }
   public void hideSoftKeyboard() {
       if(getActivity().getCurrentFocus()!=null) {
           InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
           inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
       }
   }
}