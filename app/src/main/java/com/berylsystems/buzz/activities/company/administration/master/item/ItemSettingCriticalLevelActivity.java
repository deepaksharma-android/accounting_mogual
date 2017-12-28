package com.berylsystems.buzz.activities.company.administration.master.item;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemSettingCriticalLevelActivity extends AppCompatActivity {
    @Bind(R.id.minimum_level_qty)
    EditText minimum_level_qty;
    @Bind(R.id.recorded_level_qty)
    EditText recorded_level_qty;
    @Bind(R.id.maximum_level_qty)
    EditText maximum_level_qty;
    @Bind(R.id.minimum_level_days)
    EditText minimum_level_days;
    @Bind(R.id.recorded_level_days)
    EditText recorded_level_days;
    @Bind(R.id.maximum_level_days)
    EditText maximum_level_days;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    AppUser appUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_critical_level);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        minimum_level_qty.setText(Preferences.getInstance(getApplicationContext()).getitem_setting_critical_min_level_qty());
        recorded_level_qty.setText(Preferences.getInstance(getApplicationContext()).getitem_setting_critical_recorded_level_qty());
        maximum_level_qty.setText(Preferences.getInstance(getApplicationContext()).getitem_setting_critical_max_level_qty());
        minimum_level_days.setText(Preferences.getInstance(getApplicationContext()).getitem_setting_critical_min_level_days());
        recorded_level_days.setText(Preferences.getInstance(getApplicationContext()).getitem_setting_critical_recorded_level_days());
        maximum_level_days.setText(Preferences.getInstance(getApplicationContext()).getitem_setting_critical_max_level_days());

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_qty(minimum_level_qty.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_qty(recorded_level_qty.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_qty(maximum_level_qty.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_days(minimum_level_days.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_days(recorded_level_days.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_days(maximum_level_days.getText().toString());
                finish();

            }
        });
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("CRITICAL LEVEL SETTING");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}