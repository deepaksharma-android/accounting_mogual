package com.berylsystems.buzz.activities.app;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

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


    @Override
    public void setContentView(int layoutResID) {
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