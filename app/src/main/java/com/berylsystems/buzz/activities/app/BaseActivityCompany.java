package com.berylsystems.buzz.activities.app;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.CompanyListActivity;
import com.berylsystems.buzz.activities.company.navigation.reports.CompanyAboutActivity;
import com.berylsystems.buzz.activities.company.navigation.reports.account_group.AcountGroupActivity;
import com.berylsystems.buzz.activities.company.FirstPageActivity;
import com.berylsystems.buzz.activities.dashboard.CompanyDashboardActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.activities.dashboard.TransactionDashboardActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BaseActivityCompany extends AppCompatActivity {
    private Menu menu;
    private MenuItem searchItem;
    private SearchView searchView;
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    AppUser appUser;
    ImageView mAddIcon;
    TextView mTitleText;
    Toolbar toolbar;
    NavigationView navigationViewcompany,navigationViewapp;
    LinearLayout mMasterLayout;
    ImageView mArrow;
    int count;


    @Override
    public void setContentView(int layoutResID) {
        count=0;
        appUser= LocalRepositories.getAppUser(this);
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.navigation_drawer_frame_company, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleText=(TextView)findViewById(R.id.titletextbase);
        mAddIcon=(ImageView)findViewById(R.id.icn_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_left_carat_selected);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_container);

        navigationViewcompany = (NavigationView) findViewById(R.id.navigationViewCompany);
        View headercompany = navigationViewcompany.getHeaderView(0);
        ImageView company_logo=(ImageView)headercompany.findViewById(R.id.company_logo);
        TextView company_name=(TextView) headercompany.findViewById(R.id.company_name);
        company_name.setText(appUser.company_name.toUpperCase());
        if(!appUser.company_logo.equals("")){
            company_logo.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .load(appUser.company_logo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(company_logo);
        }
        else{
            company_logo.setVisibility(View.GONE);
        }
         mMasterLayout=(LinearLayout)navigationViewcompany.findViewById(R.id.administration_sub_layout);
         mArrow=(ImageView)navigationViewcompany.findViewById(R.id.arrow);

       
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer); /*{
            @Override
            public void onDrawerClosed(View drawerView) {

                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };*/

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();






    }
    public void dashboard(View view){
        drawerLayout.closeDrawers();
        Intent intent=new Intent(getApplicationContext(),FirstPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();

    }
    public void administration(View v){
        drawerLayout.closeDrawers();
        count++;
        if(count%2==0){
            mMasterLayout.setVisibility(View.GONE);
            mArrow.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
        }
        else{
            mMasterLayout.setVisibility(View.VISIBLE);
            mArrow.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow));
        }


    }
    public void master(View v){
        drawerLayout.closeDrawers();
        Intent intent=new Intent(getApplicationContext(),MasterDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();


    }
    public void transaction(View v){
        drawerLayout.closeDrawers();
        Intent intent=new Intent(getApplicationContext(),TransactionDashboardActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();
    }
    public void reports(View v){
        drawerLayout.closeDrawers();
        Intent intent=new Intent(getApplicationContext(),AcountGroupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();
    }
    public void settings(View v){
        drawerLayout.closeDrawers();
        Intent intent=new Intent(getApplicationContext(),CompanyDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();


    }
    public void help(View v){
        drawerLayout.closeDrawers();
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:support@mbilling.in"));
        email.putExtra(Intent.EXTRA_SUBJECT, "Support m-Billing");
        email.putExtra(Intent.EXTRA_TEXT, "Sent from m-Billing app");
        startActivity(email);

    }
    public void contactus(View v){
        drawerLayout.closeDrawers();
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:contact@mbilling.in"));
        email.putExtra(Intent.EXTRA_SUBJECT, "Contact m-Billing");
        email.putExtra(Intent.EXTRA_TEXT, "Sent from m-Billing app");
        startActivity(email);
    }
    public void share(View v){
        drawerLayout.closeDrawers();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sent from m-Billing app");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Share the m-Billing App with your friends to spread goodness-Get the app-https://play.google.com/store/apps/details?id=com.berylsystems.buzz");
        startActivity(Intent.createChooser(shareIntent, "Share via"));

    }
    public void about(View v){
        drawerLayout.closeDrawers();
        Intent intent=new Intent(getApplicationContext(),CompanyAboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();
    }
    public void exit(View v){
        new AlertDialog.Builder(BaseActivityCompany.this)
                .setTitle("Exit Company")
                .setMessage("Do you want to exit this company ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), CompanyListActivity.class));
                    finish();
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }


    protected void setNavigationCompany(int id){
        if(id==2){
            toolbar.setNavigationIcon(null);
        }
    }

    protected void setAddCompany(int id){
        if(id==1){
            mAddIcon.setVisibility(View.VISIBLE);
        }
        else {
            mAddIcon.setVisibility(View.GONE);
        }
    }
    protected void setAppBarTitleCompany(int id,String str){
        if(id==1){
            mTitleText.setVisibility(View.VISIBLE);
            mTitleText.setText(str);
        }
        else {
            mTitleText.setVisibility(View.GONE);
        }
    }
}