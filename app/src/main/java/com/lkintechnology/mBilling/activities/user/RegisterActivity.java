package com.lkintechnology.mBilling.activities.user;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.HomePageActivity;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.CompanyListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.events.EventFbAuthResponse;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.user.UserApiResponse;
import com.lkintechnology.mBilling.networks.api_response.userexist.UserExistResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;
import com.lkintechnology.mBilling.utils.Validation;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.name)
    EditText mName;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.mobile)
    EditText mMobile;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.confirm_password)
    EditText mConfirmPassword;
    @Bind(R.id.fb_login_button)
    LinearLayout fbLoginBtn;
    @Bind(R.id.zip_code)
    EditText mzip_code;
    @Bind(R.id.salesman_mobile)
    EditText mSalesman_mobile;
    @Bind(R.id.terms)
    TextView mTerms;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        callbackManager = CallbackManager.Factory.create();
        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("email"));
                Helpers.facebookLogin(callbackManager, fbLoginBtn);
            }
        });
        mTerms.setClickable(true);
        mTerms.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "By continuing, you are indicating that you have read and agree to the <a href='https://www.mbilling.in/terms-condition'> Terms of Use</a> and <a href='https://www.mbilling.in/privacy-policy'> Privacy Policy</a> ";
        mTerms.setText(Html.fromHtml(text));
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
        actionbarTitle.setText("SIGN UP");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }


    @Subscribe
    public void fbAuthResponse(EventFbAuthResponse resp) {
        appUser.fb_id = resp.id;
        appUser.name = resp.first_name + " " + resp.last_name;
        appUser.email = resp.email;
        appUser.password = "qwopaskl23";
        LocalRepositories.saveAppUser(this, appUser);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(RegisterActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();

            ApiCallsService.action(this, Cv.ACTION_FACEBOOK_CHECK);
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                snackbar.dismiss();
                            }
                        }
                    });
            snackbar.show();
        }


    }

    @Subscribe
    public void userexists(UserExistResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            if (response.is_present.equals("true")) {
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), FacebookHandlerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("fromregisterpage", true);
                startActivity(intent);
                finish();
            }
        } else {
            //snackbar = Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            //snackbar.show();
            Helpers.dialogMessage(getApplicationContext(), response.getMessage());
        }
    }

    public void register(View v) {

        boolean cancel = false;
        View focusView = null;

        if (!mName.getText().toString().equals("")) {
            if (Validation.isPhoneFormatValid(mMobile.getText().toString())) {
                if (Validation.isPwdFormatValid(mPassword.getText().toString())) {
                    if (Validation.isPwdFormatValid(mConfirmPassword.getText().toString())) {
                        if (mPassword.getText().toString().matches(mConfirmPassword.getText().toString())) {
                            if (!mzip_code.getText().toString().equals("")) {
                                if (validate()) {

                                    appUser.name = mName.getText().toString();
                                    appUser.email = mEmail.getText().toString();
                                    appUser.mobile = mMobile.getText().toString();
                                    appUser.password = mPassword.getText().toString();
                                    appUser.zipcode = mzip_code.getText().toString();
                                    appUser.salesmanmobile = mSalesman_mobile.getText().toString();
                                    LocalRepositories.saveAppUser(this, appUser);
                                    logInRequest();
                                }
                            } else {
                                snackbar = Snackbar
                                        .make(coordinatorLayout, "Enter Pincode", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } else {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, "Password does not match", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    } else {
                        snackbar = Snackbar
                                .make(coordinatorLayout, getString(R.string.err_invalid_pwd_format), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                } else {
                    snackbar = Snackbar
                            .make(coordinatorLayout, getString(R.string.err_invalid_pwd_format), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } else {
                snackbar = Snackbar
                        .make(coordinatorLayout, getString(R.string.err_invalid_mobile), Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, getString(R.string.err_invalid_name), Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    //   check validation on zipcode and email mobile
    private boolean validate() {
        if (mzip_code.getText().toString().trim().length() < 6) {
            snackbar = Snackbar
                    .make(coordinatorLayout, "Enter Valid Pincode", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        } else if (mEmail.getText().toString().length() == 0) {
            snackbar = Snackbar
                    .make(coordinatorLayout, "Enter Email", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString().trim()).matches()) {
            snackbar = Snackbar
                    .make(coordinatorLayout, "Invalid Email", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }/*else if (mSalesman_mobile.getText().toString().length()==0){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Enter Salesman Mobile Number", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }else if (mSalesman_mobile.getText().toString().trim().length()< 10 | mSalesman_mobile.getText().toString().trim().length()> 12){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Enter Valid Mobile Number", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }*/ else {
            return true;
        }
    }


    private void logInRequest() {
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(RegisterActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(this, appUser);
            ApiCallsService.action(this, Cv.ACTION_REGISTER_USER);
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                snackbar.dismiss();
                            }
                        }
                    });
            snackbar.show();
        }
    }

    @Subscribe
    public void registerResponse(UserApiResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.user_id = response.getUser().getData().getId();
            appUser.name = response.getUser().getData().getAttributes().getName();
            appUser.mobile = response.getUser().getData().getAttributes().getMobile();
            appUser.email = response.getUser().getData().getAttributes().getEmail();
            appUser.auth_token = response.getUser().getData().getAttributes().getAuth_token();
            LocalRepositories.saveAppUser(this, appUser);
            if (response.getUser().getData().getAttributes().getActive() == false) {
                Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                intent.putExtra("fromRegisterPage", true);
                intent.putExtra("mobile", mMobile.getText().toString());
                startActivity(intent);
                finish();
            }

        } else if (response.getStatus() == 412) {
            String mobile = "+917015860006";
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Call us")
                    .setMessage(R.string.call_us)
                    .setPositiveButton(R.string.click_me, (dialogInterface, i) -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mobile, null));
                        startActivity(intent);

                    })
                    .setNegativeButton(R.string.btn_cancel, null)
                    .show();

        } else {
            //snackbar = Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            //snackbar.show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}