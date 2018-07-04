package com.lkintechnology.mBilling.activities.company;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.CompanyDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.fragments.company.CompanyBankDetailsFragment;
import com.lkintechnology.mBilling.fragments.company.CompanyBasicFragment;
import com.lkintechnology.mBilling.fragments.company.CompanyDetailsFragment;
import com.lkintechnology.mBilling.fragments.company.CompanyGstFragment;
import com.lkintechnology.mBilling.fragments.company.CompanyLogoFragment;
import com.lkintechnology.mBilling.fragments.company.CompanyPasswordFragment;
import com.lkintechnology.mBilling.fragments.company.CompanySignatureFragment;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.Industry;
import com.lkintechnology.mBilling.networks.api_response.company.IndustryTypeResponse;
import com.lkintechnology.mBilling.networks.api_response.getcompany.CompanyResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditCompanyActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
   // public static CompanyData data;
   /* @Bind(R.id.viewpager)
     ViewPager mHeaderViewPager;*/
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    public static ArrayList<String> industry_type = new ArrayList<>();
    public static ArrayList<Integer> industry_id = new ArrayList<>();
    public static IndustryTypeResponse response;
    public  ViewPager mHeaderViewPager;
    public static int fragPos;
    public static Boolean frompass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);
        ButterKnife.bind(this);
        frompass=getIntent().getExtras().getBoolean("frompass");
        mHeaderViewPager= (ViewPager) findViewById(R.id.viewpager);
        appUser= LocalRepositories.getAppUser(this);
        initActionbar();
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(EditCompanyActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(this, Cv.ACTION_GET_COMPANY);
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

       /* Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(EditCompanyActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(this, Cv.ACTION_GET_INDUSTRY);
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
*/
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), CompanyDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), CompanyDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("EDIT COMPANY");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CompanyBasicFragment(), "BASIC");
        adapter.addFragment(new CompanyDetailsFragment(), "DETAILS");
        adapter.addFragment(new CompanyBankDetailsFragment(), "BANK DETAILS");
        adapter.addFragment(new CompanyGstFragment(), "GST INFO");
       // adapter.addFragment(new CompanyAdditionalFragment(), "ADDITIONAL");
        adapter.addFragment(new CompanyLogoFragment(), "LOGO");
        adapter.addFragment(new CompanySignatureFragment(), "SIGNATURE");
        adapter.addFragment(new CompanyPasswordFragment(), "USER");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        public  final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
          //  fragPos=position;
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onResume() {

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
    public void getcompanydetail(CompanyResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            LocalRepositories.saveAppUser(this, appUser);
            Preferences.getInstance(getApplicationContext()).setCname(Helpers.mystring(response.getCompany().getData().getAttributes().getName()));
            Preferences.getInstance(getApplicationContext()).setCprintname(Helpers.mystring(response.getCompany().getData().getAttributes().getPrint_name()));
            Preferences.getInstance(getApplicationContext()).setCshortname(Helpers.mystring(response.getCompany().getData().getAttributes().getShort_name()));
            Preferences.getInstance(getApplicationContext()).setCphonenumber(Helpers.mystring(response.getCompany().getData().getAttributes().getPhone_number()));
            Preferences.getInstance(getApplicationContext()).setCfinancialyear(Helpers.mystring(response.getCompany().getData().getAttributes().getFinancial_year_from()));
            Preferences.getInstance(getApplicationContext()).setCbookyear(Helpers.mystring(response.getCompany().getData().getAttributes().getBooks_commencing_from()));
            Preferences.getInstance(getApplicationContext()).setCcin(Helpers.mystring(response.getCompany().getData().getAttributes().getCin()));
            Preferences.getInstance(getApplicationContext()).setCpan(Helpers.mystring(response.getCompany().getData().getAttributes().getIt_pin()));
            Preferences.getInstance(getApplicationContext()).setCaddress(Helpers.mystring(response.getCompany().getData().getAttributes().getAddress()));
            Preferences.getInstance(getApplicationContext()).setCcountry(Helpers.mystring(response.getCompany().getData().getAttributes().getCountry()));
            Preferences.getInstance(getApplicationContext()).setCstate(Helpers.mystring(response.getCompany().getData().getAttributes().getState()));
            Preferences.getInstance(getApplicationContext()).setCcity(Helpers.mystring(response.getCompany().getData().getAttributes().getCity()));
            Preferences.getInstance(getApplicationContext()).setCZipcode(Helpers.mystring(response.getCompany().getData().getAttributes().getZipcode()));
            Preferences.getInstance(getApplicationContext()).setCindustrytype(Helpers.mystring(response.getCompany().getData().getAttributes().getIndustry_type()));
            Preferences.getInstance(getApplicationContext()).setCward(Helpers.mystring(response.getCompany().getData().getAttributes().getWard()));
            Preferences.getInstance(getApplicationContext()).setCfax(Helpers.mystring(response.getCompany().getData().getAttributes().getFax()));
            Preferences.getInstance(getApplicationContext()).setCemail(Helpers.mystring(response.getCompany().getData().getAttributes().getEmail()));
            Preferences.getInstance(getApplicationContext()).setCgst(Helpers.mystring(response.getCompany().getData().getAttributes().getGst()));
            Preferences.getInstance(getApplicationContext()).setCdealer(Helpers.mystring(response.getCompany().getData().getAttributes().getType_of_dealer()));
            Preferences.getInstance(getApplicationContext()).setCtax1(Helpers.mystring(response.getCompany().getData().getAttributes().getDefault_tax_rate1()));
            Preferences.getInstance(getApplicationContext()).setCtax2(Helpers.mystring(response.getCompany().getData().getAttributes().getDefault_tax_rate2()));
            Preferences.getInstance(getApplicationContext()).setCsymbol(Helpers.mystring(response.getCompany().getData().getAttributes().getCurrency_symbol()));
            Preferences.getInstance(getApplicationContext()).setCstring(Helpers.mystring(response.getCompany().getData().getAttributes().getCurrency_string()));
            Preferences.getInstance(getApplicationContext()).setCsubstring(Helpers.mystring(response.getCompany().getData().getAttributes().getCurrency_sub_string()));
            Preferences.getInstance(getApplicationContext()).setClogo(Helpers.mystring(response.getCompany().getData().getAttributes().getLogo()));
            Preferences.getInstance(getApplicationContext()).setCsign(Helpers.mystring(response.getCompany().getData().getAttributes().getSignature()));
            Preferences.getInstance(getApplicationContext()).setCusername(Helpers.mystring(response.getCompany().getData().getAttributes().getUsername()));
            Preferences.getInstance(getApplicationContext()).setCbankname(Helpers.mystring(response.getCompany().getData().getAttributes().getBank_name()));
            Preferences.getInstance(getApplicationContext()).setCbankaccount(Helpers.mystring(response.getCompany().getData().getAttributes().getBank_account()));
            Preferences.getInstance(getApplicationContext()).setCifsccode(Helpers.mystring(response.getCompany().getData().getAttributes().getIfsc_code()));
            Preferences.getInstance(getApplicationContext()).setCmicrcode(Helpers.mystring(response.getCompany().getData().getAttributes().getMicr_code()));
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(EditCompanyActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(this, Cv.ACTION_GET_INDUSTRY);
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
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void getIndustryType(IndustryTypeResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.dismiss();
                }
            },2*1000);*/
            //appUser.industry_type.clear();
            industry_type.clear();
            industry_id.clear();
            EditCompanyActivity.response=response;
            for(int i=0;i<response.getIndustry().getData().size();i++){
                //appUser.industry_type.add(response.getIndustry().getData().get(i).getAttributes().getName());
                industry_type.add(response.getIndustry().getData().get(i).getAttributes().getName());
                industry_id.add(response.getIndustry().getData().get(i).getAttributes().getId());
//                LocalRepositories.saveAppUser(this,appUser);
                setupViewPager(mHeaderViewPager);
                if(frompass){
                    mHeaderViewPager.setCurrentItem(5);

                }
                mTabLayout.setupWithViewPager(mHeaderViewPager);
//                mTabLayout.clearOnTabSelectedListeners();
                LinearLayout tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));
                for(int j = 0; j < tabStrip.getChildCount(); j++) {
                    tabStrip.getChildAt(j).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                }

               /* mHeaderViewPager.setOffscreenPageLimit(1);*/
            }
        } else {
          // Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
   public void timout(String msg) {
       snackbar = Snackbar
               .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
       snackbar.show();
       mProgressDialog.dismiss();

   }
}