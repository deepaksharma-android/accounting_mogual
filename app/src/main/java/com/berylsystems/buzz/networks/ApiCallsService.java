package com.berylsystems.buzz.networks;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.berylsystems.buzz.ThisApp;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_request.RequestBasic;
import com.berylsystems.buzz.networks.api_request.RequestCompanyAdditional;
import com.berylsystems.buzz.networks.api_request.RequestCompanyAuthenticate;
import com.berylsystems.buzz.networks.api_request.RequestCompanyDetails;
import com.berylsystems.buzz.networks.api_request.RequestCompanyGst;
import com.berylsystems.buzz.networks.api_request.RequestCompanyLogin;
import com.berylsystems.buzz.networks.api_request.RequestCompanyLogo;
import com.berylsystems.buzz.networks.api_request.RequestCompanySignature;
import com.berylsystems.buzz.networks.api_request.RequestCreateAccount;
import com.berylsystems.buzz.networks.api_request.RequestCreateAccountGroup;
import com.berylsystems.buzz.networks.api_request.RequestCreateCompany;
import com.berylsystems.buzz.networks.api_request.RequestEditLogin;
import com.berylsystems.buzz.networks.api_request.RequestForgotPassword;
import com.berylsystems.buzz.networks.api_request.RequestLoginEmail;
import com.berylsystems.buzz.networks.api_request.RequestNewPassword;
import com.berylsystems.buzz.networks.api_request.RequestRegister;
import com.berylsystems.buzz.networks.api_request.RequestResendOtp;
import com.berylsystems.buzz.networks.api_request.RequestUpdateMobileNumber;
import com.berylsystems.buzz.networks.api_request.RequestVerification;
import com.berylsystems.buzz.networks.api_response.account.CreateAccountResponse;
import com.berylsystems.buzz.networks.api_response.account.DeleteAccountResponse;
import com.berylsystems.buzz.networks.api_response.account.EditAccountResponse;
import com.berylsystems.buzz.networks.api_response.account.GetAccountDetailsResponse;
import com.berylsystems.buzz.networks.api_response.account.GetAccountResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.CreateAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.DeleteAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.EditAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupDetailsResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.company.CompanyAuthenticateResponse;
import com.berylsystems.buzz.networks.api_response.company.CompanyListResponse;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.networks.api_response.company.DeleteCompanyResponse;
import com.berylsystems.buzz.networks.api_response.company.IndustryTypeResponse;
import com.berylsystems.buzz.networks.api_response.companylogin.CompanyLoginResponse;
import com.berylsystems.buzz.networks.api_response.companylogin.CompanyUserResponse;
import com.berylsystems.buzz.networks.api_response.getcompany.CompanyResponse;
import com.berylsystems.buzz.networks.api_response.otp.OtpResponse;
import com.berylsystems.buzz.networks.api_response.packages.PackageResponse;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.networks.api_response.userexist.UserExistResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ADMIN on 3/6/2017.
 */
public class ApiCallsService extends IntentService {
    private Api api;

    public ApiCallsService() {
        super(Cv.SERVICE_NAME);
    }

    public static void action(Context ctx, String action) {
        Intent intent = new Intent(ctx, ApiCallsService.class);
        intent.setAction(action);
        ctx.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        api = ThisApp.getApi(this);
        if (Cv.ACTION_REGISTER_USER.equals(action)) {
            handleRegister();
        } else if (Cv.ACTION_FORGOT_PASSWORD.equals(action)) {
            handleForgotPassword();
        } else if (Cv.ACTION_VERIFICATION.equals(action)) {
            handleVerifyOtp();
        } else if (Cv.ACTION_NEW_PASSWORD.equals(action)) {
            handleNewPassword();
        } else if (Cv.ACTION_UPDATE_MOBILE_NUMBER.equals(action)) {
            handleUpdateMobileNumber();
        } else if (Cv.ACTION_RESEND_OTP.equals(action)) {
            handleResendOtp();
        } else if (Cv.ACTION_LOGIN.equals(action)) {
            handleLogin();
        }else if (Cv.ACTION_FACEBOOK_CHECK.equals(action)) {
            handlefacebookcheck();
        }else if (Cv.ACTION_CREATE_COMPANY.equals(action)) {
            handleCreateCompany();
        } else if (Cv.ACTION_CREATE_BASIC.equals(action)) {
            handleBasic();
        } else if (Cv.ACTION_CREATE_DETAILS.equals(action)) {
            handleCompanyDetails();
        }else if (Cv.ACTION_CREATE_GST.equals(action)) {
            handleCompanyGst();
        } else if (Cv.ACTION_CREATE_ADDITIONAL.equals(action)) {
            handleCompanyAdditional();
        }else if (Cv.ACTION_CREATE_LOGO.equals(action)) {
            handleCompanyLogo();
        }else if (Cv.ACTION_CREATE_SIGNATURE.equals(action)) {
            handleCompanySignature();
        }else if (Cv.ACTION_CREATE_LOGIN.equals(action)) {
            handleCompanyLogin();
        } else if (Cv.ACTION_COMPANY_LIST.equals(action)) {
            handleCompanyList();
        }else if (Cv.ACTION_DELETE_COMPANY.equals(action)) {
            handleDeleteCompany();
        }else if (Cv.ACTION_GET_INDUSTRY.equals(action)) {
            handleGetIndustry();
        } else if (Cv.ACTION_COMPANY_AUTHENTICATE.equals(action)) {
            handleAuthenticateCompany();
        }else if (Cv.ACTION_GET_COMPANY.equals(action)) {
            handleGetCompany();
        }else if (Cv.ACTION_SEARCH_COMPANY.equals(action)) {
            handleSearchCompany();
        }else if (Cv.ACTION_GET_COMPANY_USER.equals(action)) {
            handleGetCompanyUser();
        }else if (Cv.ACTION_GET_PACKAGES.equals(action)) {
            handleGetPackages();
        }else if (Cv.ACTION_EDIT_LOGIN.equals(action)) {
            handleEditLogin();
        }else if (Cv.ACTION_CREATE_ACCOUNT_GROUP.equals(action)) {
            handleCreateAccountGroup();
        }else if (Cv.ACTION_GET_ACCOUNT_GROUP.equals(action)) {
            handleGetAccountGroup();
        }else if (Cv.ACTION_GET_ACCOUNT_GROUP_DETAILS.equals(action)) {
            handleGetAccountGroupDetails();
        }else if (Cv.ACTION_EDIT_ACCOUNT_GROUP.equals(action)) {
            handleEditAccountGroup();
        }else if (Cv.ACTION_CREATE_ACCOUNT.equals(action)) {
            handleCreateAccount();
        }else if (Cv.ACTION_GET_ACCOUNT.equals(action)) {
            handleGetAccount();
        }else if (Cv.ACTION_GET_ACCOUNT_DETAILS.equals(action)) {
            handleGetAccountDetails();
        }else if (Cv.ACTION_EDIT_ACCOUNT.equals(action)) {
            handleEditAccount();
        }else if (Cv.ACTION_DELETE_ACCOUNT.equals(action)) {
            handleDeleteAccount();
        }else if (Cv.ACTION_DELETE_ACCOUNT_GROUP.equals(action)) {
            handleDeleteAccountGroup();
        }


    }

    private void handleRegister() {
        api.register(new RequestRegister(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);
            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleForgotPassword() {

        api.forgotpassword(new RequestForgotPassword(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleVerifyOtp() {

        api.verifyotp(new RequestVerification(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleNewPassword() {

        api.newpassword(new RequestNewPassword(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleUpdateMobileNumber() {

        api.updatemobile(new RequestUpdateMobileNumber(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }
    private void handlefacebookcheck() {
        AppUser appUser= LocalRepositories.getAppUser(this);
        api.exist(appUser.fb_id).enqueue(new Callback<UserExistResponse>() {
            @Override
            public void onResponse(Call<UserExistResponse> call, Response<UserExistResponse> r) {
                if (r.code() == 200) {
                    UserExistResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UserExistResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleResendOtp() {

        api.resendotp(new RequestResendOtp(this)).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> r) {
                if (r.code() == 200) {
                    OtpResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleLogin() {

        api.login(new RequestLoginEmail(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateCompany() {
        api.createcompany(new RequestCreateCompany(this)).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleBasic() {
        api.cbasiccompany(new RequestBasic(this),Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCompanyDetails() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.cdetails(new RequestCompanyDetails(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCompanyGst() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.cgst(new RequestCompanyGst(this),Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCompanyAdditional() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.cadditional(new RequestCompanyAdditional(this),Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCompanyLogo() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.clogo(new RequestCompanyLogo(this),Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCompanySignature() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.csignature(new RequestCompanySignature(this),Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleEditLogin() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.ceditlogin(new RequestEditLogin(this),appUser.company_user_id).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCompanyLogin() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.clogin(new RequestCompanyLogin(this),Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CompanyLoginResponse>() {
            @Override
            public void onResponse(Call<CompanyLoginResponse> call, Response<CompanyLoginResponse> r) {
                if (r.code() == 200) {
                    CompanyLoginResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyLoginResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCompanyList() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.getCompanyList(appUser.user_id).enqueue(new Callback<CompanyListResponse>() {
            @Override
            public void onResponse(Call<CompanyListResponse> call, Response<CompanyListResponse> r) {
                if (r.code() == 200) {
                    CompanyListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleDeleteCompany() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.cdelete(appUser.company_id).enqueue(new Callback<DeleteCompanyResponse>() {
            @Override
            public void onResponse(Call<DeleteCompanyResponse> call, Response<DeleteCompanyResponse> r) {
                if (r.code() == 200) {
                    DeleteCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetIndustry() {
        api.getIndustry().enqueue(new Callback<IndustryTypeResponse>() {
            @Override
            public void onResponse(Call<IndustryTypeResponse> call, Response<IndustryTypeResponse> r) {
                if (r.code() == 200) {
                    IndustryTypeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<IndustryTypeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleAuthenticateCompany() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.cauthenticate(new RequestCompanyAuthenticate(this),appUser.company_id).enqueue(new Callback<CompanyAuthenticateResponse>() {
            @Override
            public void onResponse(Call<CompanyAuthenticateResponse> call, Response<CompanyAuthenticateResponse> r) {
                if (r.code() == 200) {
                    CompanyAuthenticateResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyAuthenticateResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetCompany() {
        api.getcompany(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> r) {
                if (r.code() == 200) {
                    CompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleSearchCompany() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.searchcompany(appUser.search_company_id).enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> r) {
                if (r.code() == 200) {
                    CompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetCompanyUser() {
        api.getcompanyusers(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CompanyUserResponse>() {
            @Override
            public void onResponse(Call<CompanyUserResponse> call, Response<CompanyUserResponse> r) {
                if (r.code() == 200) {
                    CompanyUserResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyUserResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCreateAccountGroup() {
        api.createaccountgroup(new RequestCreateAccountGroup(this)).enqueue(new Callback<CreateAccountGroupResponse>() {
            @Override
            public void onResponse(Call<CreateAccountGroupResponse> call, Response<CreateAccountGroupResponse> r) {
                if (r.code() == 200) {
                    CreateAccountGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateAccountGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetAccountGroup() {
        api.getaccountgroup(Preferences.getInstance(this).getCid()).enqueue(new Callback<GetAccountGroupResponse>() {
            @Override
            public void onResponse(Call<GetAccountGroupResponse> call, Response<GetAccountGroupResponse> r) {
                if (r.code() == 200) {
                    GetAccountGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetAccountGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetAccountGroupDetails() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.getaccountgroupdetails(appUser.edit_group_id).enqueue(new Callback<GetAccountGroupDetailsResponse>() {
            @Override
            public void onResponse(Call<GetAccountGroupDetailsResponse> call, Response<GetAccountGroupDetailsResponse> r) {
                if (r.code() == 200) {
                    GetAccountGroupDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetAccountGroupDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleEditAccountGroup() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.editaccountgroup(new RequestCreateAccountGroup(this),appUser.edit_group_id).enqueue(new Callback<EditAccountGroupResponse>() {
            @Override
            public void onResponse(Call<EditAccountGroupResponse> call, Response<EditAccountGroupResponse> r) {
                if (r.code() == 200) {
                    EditAccountGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditAccountGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCreateAccount() {
        api.createaccount(new RequestCreateAccount(this)).enqueue(new Callback<CreateAccountResponse>() {
            @Override
            public void onResponse(Call<CreateAccountResponse> call, Response<CreateAccountResponse> r) {
                if (r.code() == 200) {
                    CreateAccountResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateAccountResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetAccount() {
        api.getaccount(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetAccountResponse>() {
            @Override
            public void onResponse(Call<GetAccountResponse> call, Response<GetAccountResponse> r) {
                if (r.code() == 200) {
                    GetAccountResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetAccountResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }
    private void handleGetAccountDetails() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.getaccountdetails(appUser.edit_account_id).enqueue(new Callback<GetAccountDetailsResponse>() {
            @Override
            public void onResponse(Call<GetAccountDetailsResponse> call, Response<GetAccountDetailsResponse> r) {
                if (r.code() == 200) {
                    GetAccountDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetAccountDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditAccount() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.editaccount( new RequestCreateAccount(this),appUser.edit_account_id).enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(Call<EditAccountResponse> call, Response<EditAccountResponse> r) {
                if (r.code() == 200) {
                    EditAccountResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteAccount() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.deleteaccount(appUser.delete_account_id).enqueue(new Callback<DeleteAccountResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> r) {
                if (r.code() == 200) {
                    DeleteAccountResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleDeleteAccountGroup() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.deleteaccountgroup(appUser.delete_group_id).enqueue(new Callback<DeleteAccountGroupResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountGroupResponse> call, Response<DeleteAccountGroupResponse> r) {
                if (r.code() == 200) {
                    DeleteAccountGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteAccountGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetPackages() {
        api.getpackage().enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(Call<PackageResponse> call, Response<PackageResponse> r) {
                if (r.code() == 200) {
                    PackageResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<PackageResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void processUserApiResponse(Response<UserApiResponse> response) {
        if (response.code() == 200) {
            UserApiResponse body = response.body();
            EventBus.getDefault().post(body);
        } else {
            EventBus.getDefault().post(Cv.TIMEOUT);
        }

    }


}



