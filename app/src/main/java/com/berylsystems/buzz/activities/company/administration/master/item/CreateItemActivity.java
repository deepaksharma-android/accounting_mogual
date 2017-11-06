package com.berylsystems.buzz.activities.company.administration.master.item;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.view.View;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.fragments.item.AlternateUnitDetailsFragment;
import com.berylsystems.buzz.fragments.item.DiscountAndMarkupFragment;
import com.berylsystems.buzz.fragments.item.GeneralInfoFragment;
import com.berylsystems.buzz.fragments.item.ItemDescriptionFragment;
import com.berylsystems.buzz.fragments.item.ItemPriceInfoFragment;
import com.berylsystems.buzz.fragments.item.MainUnitDetailsFragment;
import com.berylsystems.buzz.fragments.item.PackagingUnitDetailsFragment;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateItemActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.viewpager)
    ViewPager mHeaderViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        ButterKnife.bind(this);
        appUser= LocalRepositories.getAppUser(this);
        initActionbar();
        setupViewPager(mHeaderViewPager);
        mTabLayout.setupWithViewPager(mHeaderViewPager);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        actionbarTitle.setText("CREATE ITEM");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GeneralInfoFragment(), "GENERAL INFO");
        adapter.addFragment(new MainUnitDetailsFragment(), "MAIN UNIT DETAILS");
        adapter.addFragment(new AlternateUnitDetailsFragment(), "ALTERNATE UNIT DETAILS");
        adapter.addFragment(new ItemPriceInfoFragment(), "ITEM PRICE INFO");
        adapter.addFragment(new PackagingUnitDetailsFragment(), "PACKAGING UNIT DETAILS");
        adapter.addFragment(new DiscountAndMarkupFragment(), "DISCOUNT & MARKUP DET.");
        adapter.addFragment(new ItemDescriptionFragment(), "ITEM DESCRIPTION");



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