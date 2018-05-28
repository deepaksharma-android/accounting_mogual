package com.lkintechnology.mBilling.fragments.company;

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

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.EditCompanyActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.CreateCompanyResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CompanyDetailsFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.address)
    EditText mAddress;
    @Bind(R.id.city)
    EditText mCity;
    @Bind(R.id.zip_code)
    EditText mzip_code;
/*    @Bind(R.id.country_spinner)
    Spinner mCountrySpinner;*/
    @Bind(R.id.state_spinner)
    Spinner mStateSpinner;
    @Bind(R.id.industry_spinner)
    Spinner mIndustrySpinner;
    @Bind(R.id.fax)
    EditText mFax;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.ward)
    EditText mWard;

    @Bind(R.id.submit)
    LinearLayout mSubmit;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    int pos;
    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter<String> spinnerCountryAdapter;
    ArrayAdapter<String> spinnerStateAdapter;

    public CompanyDetailsFragment() {
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
        View v= inflater.inflate(R.layout.fragment_company_details, container, false);
        ButterKnife.bind(this,v);
        appUser = LocalRepositories.getAppUser(getActivity());


      /*  spinnerCountryAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_trademark_type_spinner_dropdown_item,getResources().getStringArray(R.array.country));
        spinnerCountryAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mCountrySpinner.setAdapter(spinnerCountryAdapter);*/



        spinnerStateAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_trademark_type_spinner_dropdown_item, getResources().getStringArray(R.array.state));
        spinnerStateAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mStateSpinner.setAdapter(spinnerStateAdapter);
        //Timber.i("INDUSTRY"+appUser.industry_type);
        spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_trademark_type_spinner_dropdown_item,EditCompanyActivity.industry_type);
        spinnerAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mIndustrySpinner.setAdapter(spinnerAdapter);

        mAddress.setText(Preferences.getInstance(getActivity()).getCaddress());
        mCity.setText(Preferences.getInstance(getActivity()).getCcity());
        mWard.setText(Preferences.getInstance(getActivity()).getCward());
        mFax.setText(Preferences.getInstance(getActivity()).getCfax());
        mEmail.setText(Preferences.getInstance(getActivity()).getCemail());
        mzip_code.setText((Preferences.getInstance(getActivity()).getCZipcode()));
        if(!Preferences.getInstance(getActivity()).getCindustrytype().equals("")) {
            String industryname = Preferences.getInstance(getActivity()).getCindustrytype().trim();// insert code here
            int index = -1;
            for (int i = 0; i </* appUser.industry_type*/EditCompanyActivity.industry_type.size(); i++) {
                if (/*appUser.industry_type*/EditCompanyActivity.industry_type.get(i).equals(industryname)) {
                    index = i;
                    break;
                }
            }
            Timber.i("INDEX" + index);
            mIndustrySpinner.setSelection(index);
        }
       /* if(!Preferences.getInstance(getActivity()).getCcountry().equals("")) {
            String country = Preferences.getInstance(getActivity()).getCcountry().trim();// insert code here
            int countryindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.country).length; i++) {
                if (getResources().getStringArray(R.array.country)[i].equals(country)) {
                    countryindex = i;
                    break;
                }
            }
            Timber.i("INDEX" + countryindex);
            mCountrySpinner.setSelection(countryindex);
        }*/

        if(!Preferences.getInstance(getActivity()).getCstate().equals("")) {
            String state = Preferences.getInstance(getActivity()).getCstate().trim();// insert code here
            int stateindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.state).length; i++) {
                if (getResources().getStringArray(R.array.state)[i].equals(state)) {
                    stateindex = i;
                    break;
                }
            }
            Timber.i("INDEX" + stateindex);
            mStateSpinner.setSelection(stateindex);
        }

        mIndustrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                if(!mAddress.getText().toString().equals("")) {
                    if (!mCity.getText().toString().equals("")) {
                        if (!mzip_code.getText().toString().equals("")) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                appUser.fax = mFax.getText().toString();
                                appUser.company_email = mEmail.getText().toString();
                                appUser.address = mAddress.getText().toString();
                                // appUser.country=mCountrySpinner.getSelectedItem().toString();
                                appUser.state = mStateSpinner.getSelectedItem().toString();
                                appUser.industryId = String.valueOf(EditCompanyActivity.industry_id.get(pos));
                                appUser.city = mCity.getText().toString();
                                appUser.ward = mWard.getText().toString();
                                LocalRepositories.saveAppUser(getActivity(), appUser);
                                mProgressDialog = new ProgressDialog(getActivity());
                                mProgressDialog.setMessage("Info...");
                                mProgressDialog.setIndeterminate(false);
                                mProgressDialog.setCancelable(true);
                                mProgressDialog.show();
                                LocalRepositories.saveAppUser(getActivity(), appUser);
                                ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_DETAILS);
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
                        }
                        else{
                            Snackbar
                                    .make(coordinatorLayout, "Please enter pincode", Snackbar.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Snackbar
                                .make(coordinatorLayout, "Please enter your city", Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Snackbar
                            .make(coordinatorLayout, "Please enter your address", Snackbar.LENGTH_LONG).show();
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
            tabhost.getTabAt(2).select();
            //startActivity(new Intent(getApplicationContext(),CompanyDashboardActivity.class));
            snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();

        }
        else {
           // snackbar = Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
           // snackbar.show();
            Helpers.dialogMessage(getContext(),response.getMessage());
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