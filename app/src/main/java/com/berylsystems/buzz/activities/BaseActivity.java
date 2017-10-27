package com.berylsystems.buzz.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.List;

/**
 * Created by suraj on 11/25/2015.
 */
public class BaseActivity extends AppCompatActivity{
    private Menu menu;
    private MenuItem searchItem;
    private SearchView searchView;
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    AppUser appUser;
    Spinner mSpinner;
    ImageView mAddIcon;
    Toolbar toolbar;


    @Override
    public void setContentView(int layoutResID) {
        appUser=LocalRepositories.getAppUser(this);
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.navigation_drawer_frame, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        mSpinner=(Spinner)findViewById(R.id.spinner_cname);
        mAddIcon=(ImageView)findViewById(R.id.icn_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_left_carat_selected);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_container);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        View header=navigationView.getHeaderView(0);
      //  RelativeLayout header=(RelativeLayout) navigationView.findViewById(R.id.header);
        TextView name=(TextView)header.findViewById(R.id.username);
        TextView email=(TextView)header.findViewById(R.id.email);
        name.setText(appUser.name);
        email.setText(appUser.email);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {

                    case R.id.catalog:
                       /* Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);*/
                        return true;

                        case R.id.expense:
                       /* Intent intent1 = new Intent(getApplicationContext(), ExpenseActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent1);*/
                        return true;

                    case R.id.claims:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logout:
                        new AlertDialog.Builder(BaseActivity.this)
                                .setTitle(getString(R.string.dialog_titel_logout))
                                .setMessage(getString(R.string.dialog_msg_logout))
                                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                                    Preferences.getInstance(getApplicationContext()).setLogin(false);
                                    appUser.fb_id="";
                                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));

                                })
                                .setNegativeButton(R.string.btn_cancel, null)
                                .show();
                        /*Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent2);*/
                        return true;

                    default:
                        return true;
                }

            }
        });

        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer) {
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
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        mSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, R.layout.spinner_text_layout);

        adapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    startActivity(new Intent(getApplicationContext(),CreateCompantActivity.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    protected void setHeading(int resId) {
        if(resId == 1){
        mSpinner.setVisibility(View.VISIBLE);
            mAddIcon.setVisibility(View.VISIBLE);

        }
        else{
            mSpinner.setVisibility(View.GONE);
            mAddIcon.setVisibility(View.GONE);
        }


    }
    protected void setNavigation(int id){
        if(id==2){
            toolbar.setNavigationIcon(null);
        }
    }





}
