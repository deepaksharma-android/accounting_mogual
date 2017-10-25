package com.berylsystems.buzz.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.berylsystems.buzz.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomePageActivity extends Activity {
    @Bind(R.id.terms)
    TextView mTerms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        mTerms.setClickable(true);
        mTerms.setMovementMethod(LinkMovementMethod.getInstance());
        String text ="By continuing, you are indicating that you have read and agree to the <a href='https://www.trademarks411.com/trademark/terms'> Terms of Use</a> and <a href='https://www.trademarks411.com/trademark/privacy'> Privacy Policy</a> ";
        mTerms.setText(Html.fromHtml(text));
    }

    public void login(View v){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

    }
    public void signup(View v){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }
}