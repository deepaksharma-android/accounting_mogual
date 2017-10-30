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
import com.berylsystems.buzz.fragments.CompanyPasswordFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddCompanyActivity extends BaseActivity{
    @Bind(R.id.viewpager)
    ViewPager mHeaderViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        ButterKnife.bind(this);
        setAdd(2);
        setHeading(1);
        setupViewPager(mHeaderViewPager);
        mTabLayout.setupWithViewPager(mHeaderViewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CompanyBasicFragment(), "BASIC");
        adapter.addFragment(new CompanyDetailsFragment(), "DETAILS");
        adapter.addFragment(new CompanyAdditionalFragment(), "ADDITIONAL");
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