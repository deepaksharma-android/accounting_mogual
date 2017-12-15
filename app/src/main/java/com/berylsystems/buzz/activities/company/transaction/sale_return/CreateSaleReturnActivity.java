package com.berylsystems.buzz.activities.company.transaction.sale_return;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.fragments.transaction.sale_return.AddItemSaleReturnFragment;
import com.berylsystems.buzz.fragments.transaction.sale_return.CreateSaleReturnFragment;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateSaleReturnActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    // public static CompanyData data;
    @Bind(R.id.viewpager)
    ViewPager mHeaderViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sale_type);

        ButterKnife.bind(this);
        appUser= LocalRepositories.getAppUser(this);
        initActionbar();
        setupViewPager(mHeaderViewPager);
        mTabLayout.setupWithViewPager(mHeaderViewPager);
        Intent intent=getIntent();
        boolean b=intent.getBooleanExtra("is",false);
        if (b){
            mHeaderViewPager.setCurrentItem(1, true);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
      ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreateSaleReturnFragment(), "CREATE SALE RETURN FRAGMENT");
        adapter.addFragment(new AddItemSaleReturnFragment(), "ADD ITEM SALE RETURN");
        viewPager.setAdapter(adapter);
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
        actionbarTitle.setText("CREATE SALE RETURN VOUCHER");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }
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