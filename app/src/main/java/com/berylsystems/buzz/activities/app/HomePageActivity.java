package com.berylsystems.buzz.activities.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.user.LoginActivity;
import com.berylsystems.buzz.activities.user.RegisterActivity;
import com.berylsystems.buzz.adapters.MyPagerAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomePageActivity extends Activity {
    @Bind(R.id.terms)
    TextView mTerms;
    @Bind(R.id.viewpager)
    ViewPager mPager;
    @Bind(R.id.radiogroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.radioButton)
    RadioButton mRadioButton1;
    @Bind(R.id.radioButton2)
    RadioButton mRadioButton2;
    @Bind(R.id.radioButton3)
    RadioButton mRadioButton3;
   MyPagerAdapter adapter;
    AppUser appUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        appUser=LocalRepositories.getAppUser(this);
        appUser.fb_id="";
        LocalRepositories.saveAppUser(this,appUser);
        mTerms.setClickable(true);
        mTerms.setMovementMethod(LinkMovementMethod.getInstance());
        String text ="By continuing, you are indicating that you have read and agree to the <a href='https://www.google.com'> Terms of Use</a> and <a href='https://google.com'> Privacy Policy</a> ";
        mTerms.setText(Html.fromHtml(text));
        adapter = new MyPagerAdapter();
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mRadioGroup.check(R.id.radioButton);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.radioButton2);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.radioButton3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);
    }

    public void login(View v){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

    }
    public void signup(View v){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}