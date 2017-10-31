package com.berylsystems.buzz.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.fragments.CompanyAdditionalFragment;
import com.berylsystems.buzz.fragments.CompanyBasicFragment;
import com.berylsystems.buzz.fragments.CompanyDetailsFragment;
import com.berylsystems.buzz.fragments.CompanyGstFragment;
import com.berylsystems.buzz.fragments.CompanyLogoFragment;
import com.berylsystems.buzz.fragments.CompanyPasswordFragment;
import com.berylsystems.buzz.fragments.CompanySignatureFragment;
import com.berylsystems.buzz.networks.api_response.company.CompanyData;
import com.berylsystems.buzz.utils.Helpers;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddCompanyActivity extends AppCompatActivity{
    public static CompanyData data;
    @Bind(R.id.viewpager)
    ViewPager mHeaderViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    AppUser appUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        ButterKnife.bind(this);
        appUser= LocalRepositories.getAppUser(this);
        initActionbar();
      /*  setHeading(2);
        setNavigation(1);
        setAdd(2);
        setAppBarTitle(1,"CREATE COMPANY");*/
        setupViewPager(mHeaderViewPager);
        mTabLayout.setupWithViewPager(mHeaderViewPager);
        Preferences.getInstance(getApplicationContext()).setCname("");
        Preferences.getInstance(getApplicationContext()).setCprintname("");
        Preferences.getInstance(getApplicationContext()).setCshortname("");
        Preferences.getInstance(getApplicationContext()).setCphonenumber("");
        Preferences.getInstance(getApplicationContext()).setCfinancialyear("");
        Preferences.getInstance(getApplicationContext()).setCbookyear("");
        Preferences.getInstance(getApplicationContext()).setCcin("");
        Preferences.getInstance(getApplicationContext()).setCpan("");
        Preferences.getInstance(getApplicationContext()).setCaddress("");
        Preferences.getInstance(getApplicationContext()).setCcity("");
        Preferences.getInstance(getApplicationContext()).setCcountry("");
        Preferences.getInstance(getApplicationContext()).setCstate("");
        Preferences.getInstance(getApplicationContext()).setCindustrytype("");
        Preferences.getInstance(getApplicationContext()).setCward("");
        Preferences.getInstance(getApplicationContext()).setCfax("");
        Preferences.getInstance(getApplicationContext()).setCemail("");
        Preferences.getInstance(getApplicationContext()).setCgst("");
        Preferences.getInstance(getApplicationContext()).setCdealer("");
        Preferences.getInstance(getApplicationContext()).setCtax1("");
        Preferences.getInstance(getApplicationContext()).setCtax2("");
        Preferences.getInstance(getApplicationContext()).setCsymbol("");
        Preferences.getInstance(getApplicationContext()).setCstring("");
        Preferences.getInstance(getApplicationContext()).setCsubstring("");
        Preferences.getInstance(getApplicationContext()).setClogo("");
        Preferences.getInstance(getApplicationContext()).setCsign("");
        Preferences.getInstance(getApplicationContext()).setCusername("");



        if(data!=null) {
            Preferences.getInstance(getApplicationContext()).setCname(Helpers.mystring(data.getAttributes().getName()));
            Preferences.getInstance(getApplicationContext()).setCprintname(Helpers.mystring(data.getAttributes().getPrint_name()));
            Preferences.getInstance(getApplicationContext()).setCshortname(Helpers.mystring(data.getAttributes().getShort_name()));
            Preferences.getInstance(getApplicationContext()).setCphonenumber(Helpers.mystring(data.getAttributes().getPhone_number()));
            Preferences.getInstance(getApplicationContext()).setCfinancialyear(Helpers.mystring(data.getAttributes().getFinancial_year_from()));
            Preferences.getInstance(getApplicationContext()).setCbookyear(Helpers.mystring(data.getAttributes().getBooks_commencing_from()));
            Preferences.getInstance(getApplicationContext()).setCcin(Helpers.mystring(data.getAttributes().getCin()));
            Preferences.getInstance(getApplicationContext()).setCpan(Helpers.mystring(data.getAttributes().getIt_pin()));
            Preferences.getInstance(getApplicationContext()).setCaddress(Helpers.mystring(data.getAttributes().getAddress()));
            Preferences.getInstance(getApplicationContext()).setCcountry(Helpers.mystring(data.getAttributes().getCountry()));
            Preferences.getInstance(getApplicationContext()).setCstate(Helpers.mystring(data.getAttributes().getState()));
            Preferences.getInstance(getApplicationContext()).setCindustrytype(Helpers.mystring(data.getAttributes().getIndustry_type()));
            Preferences.getInstance(getApplicationContext()).setCward(Helpers.mystring(data.getAttributes().getWard()));
            Preferences.getInstance(getApplicationContext()).setCfax(Helpers.mystring(data.getAttributes().getFax()));
            Preferences.getInstance(getApplicationContext()).setCemail(Helpers.mystring(data.getAttributes().getEmail()));
            Preferences.getInstance(getApplicationContext()).setCfax(Helpers.mystring(data.getAttributes().getFax()));
            Preferences.getInstance(getApplicationContext()).setCemail(Helpers.mystring(data.getAttributes().getEmail()));
            Preferences.getInstance(getApplicationContext()).setCgst(Helpers.mystring(data.getAttributes().getGst()));
            Preferences.getInstance(getApplicationContext()).setCdealer(Helpers.mystring(data.getAttributes().getType_of_dealer()));
            Preferences.getInstance(getApplicationContext()).setCtax1(Helpers.mystring(data.getAttributes().getDefault_tax_rate1()));
            Preferences.getInstance(getApplicationContext()).setCtax2(Helpers.mystring(data.getAttributes().getDefault_tax_rate2()));
            Preferences.getInstance(getApplicationContext()).setCsymbol(Helpers.mystring(data.getAttributes().getCurrency_symbol()));
            Preferences.getInstance(getApplicationContext()).setCstring(Helpers.mystring(data.getAttributes().getCurrency_string()));
            Preferences.getInstance(getApplicationContext()).setCsubstring(Helpers.mystring(data.getAttributes().getCurrency_sub_string()));
            Preferences.getInstance(getApplicationContext()).setClogo(Helpers.mystring(data.getAttributes().getLogo()));
            Preferences.getInstance(getApplicationContext()).setCsign(Helpers.mystring(data.getAttributes().getSignature()));
            Preferences.getInstance(getApplicationContext()).setCusername(Helpers.mystring(data.getAttributes().getUsername()));







        }
    }
    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("CREATE COMPANY");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CompanyBasicFragment(), "BASIC");
        adapter.addFragment(new CompanyDetailsFragment(), "DETAILS");
        adapter.addFragment(new CompanyGstFragment(), "GST INFO");
        adapter.addFragment(new CompanyAdditionalFragment(), "ADDITIONAL");
        adapter.addFragment(new CompanyLogoFragment(), "LOGO");
        adapter.addFragment(new CompanySignatureFragment(), "SIGNATURE");
        adapter.addFragment(new CompanyPasswordFragment(), "PASSWORD");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
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
}