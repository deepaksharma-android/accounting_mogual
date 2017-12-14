package com.berylsystems.buzz.activities.app;

import android.content.Intent;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.user.UpdateUserActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

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
    ImageView mAddIcon;
    TextView mTitleText;
    Toolbar toolbar;
    NavigationView navigationViewcompany,navigationViewapp;


    @Override
    public void setContentView(int layoutResID) {
        appUser=LocalRepositories.getAppUser(this);
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.navigation_drawer_frame, null);
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
         navigationViewapp = (NavigationView) findViewById(R.id.navigationView);
        View headerapp=navigationViewapp.getHeaderView(0);
      //  RelativeLayout header=(RelativeLayout) navigationView.findViewById(R.id.header);
        TextView name=(TextView)headerapp.findViewById(R.id.username);
        TextView email=(TextView)headerapp.findViewById(R.id.email);
        name.setText(appUser.name);
        email.setText(appUser.email);
        navigationViewapp.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
                        Intent intent1 = new Intent(getApplicationContext(), UpdateUserActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent1);

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
                                    appUser.companyLoginArray.clear();
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





    }

    protected void setNavigation(int id){
        if(id==2){
            toolbar.setNavigationIcon(null);
        }
    }

    protected void setAdd(int id){
        if(id==1){
            mAddIcon.setVisibility(View.VISIBLE);
        }
        else {
            mAddIcon.setVisibility(View.GONE);
        }
    }
    protected void setAppBarTitle(int id,String str){
        if(id==1){
           mTitleText.setVisibility(View.VISIBLE);
            mTitleText.setText(str);
        }
        else {
            mTitleText.setVisibility(View.GONE);
        }
    }







}
