package com.berylsystems.buzz.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.fragments.CompanyAdditionalFragment;
import com.berylsystems.buzz.fragments.CompanyBasicFragment;
import com.berylsystems.buzz.fragments.CompanyDetailsFragment;
import com.berylsystems.buzz.fragments.CompanyGstFragment;
import com.berylsystems.buzz.fragments.CompanyLogoFragment;
import com.berylsystems.buzz.fragments.CompanyPasswordFragment;
import com.berylsystems.buzz.fragments.CompanySignatureFragment;
import com.berylsystems.buzz.networks.api_response.company.CompanyData;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddCompanyActivity extends BaseActivity{
    public static CompanyData data;
    @Bind(R.id.viewpager)
    ViewPager mHeaderViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        ButterKnife.bind(this);
        setHeading(2);
        setNavigation(1);
        setAdd(2);
        setAppBarTitle(1,"CREATE COMPANY");
        setupViewPager(mHeaderViewPager);
        mTabLayout.setupWithViewPager(mHeaderViewPager);
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
        public void replaceFragment(Fragment fragment, String title, int index) {
            mFragmentList.remove(index);
            mFragmentList.add(index, fragment);
            // do the same for the title
            notifyDataSetChanged();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}