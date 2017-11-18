package com.berylsystems.buzz.networks;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

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
import com.berylsystems.buzz.networks.api_request.RequestCreateMaterialCentre;
import com.berylsystems.buzz.networks.api_request.RequestCreateMaterialCentreGroup;
import com.berylsystems.buzz.networks.api_request.RequestCreateUnit;
import com.berylsystems.buzz.networks.api_request.RequestCreateUnitConversion;
import com.berylsystems.buzz.networks.api_request.RequestEditLogin;
import com.berylsystems.buzz.networks.api_request.RequestForgotPassword;
import com.berylsystems.buzz.networks.api_request.RequestLoginEmail;
import com.berylsystems.buzz.networks.api_request.RequestNewPassword;
import com.berylsystems.buzz.networks.api_request.RequestPlan;
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
import com.berylsystems.buzz.networks.api_response.materialcentre.CreateMaterialCentreResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.DeleteMaterialCentreResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.EditMaterialCentreReponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.GetMaterialCentreDetailResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.GetMaterialCentreListResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.StockResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.CreateMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.DeleteMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.EditMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.GetMaterialCentreGroupDetailResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.GetMaterialCentreGroupListResponse;
import com.berylsystems.buzz.networks.api_response.otp.OtpResponse;
import com.berylsystems.buzz.networks.api_response.packages.GetPackageResponse;
import com.berylsystems.buzz.networks.api_response.packages.PlanResponse;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.networks.api_response.userexist.UserExistResponse;
import com.berylsystems.buzz.networks.api_response.unit.CreateUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.DeleteUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.EditUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUnitDetailsResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUnitListResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.CreateUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.DeleteUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.EditUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.GetUnitConversionDetailsResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.GetUnitConversionListResponse;
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
        }else if (Cv.ACTION_GET_PACKAGES.equals(action)) {
            handleGetPackages();
        }else if (Cv.ACTION_PLANS.equals(action)) {
            handlePlans();
        }else if (Cv.ACTION_GET_MATERIAL_CENTRE_GROUP_LIST.equals(action)) {
            handleGetMaterialCentreGroupList();
        }else if (Cv.ACTION_CREATE_MATERIAL_CENTRE_GROUP.equals(action)) {
            handleCreateMaterialCentreGroup();
        }else if (Cv.ACTION_EDIT_MATERIAL_CENTRE_GROUP.equals(action)) {
            handleEditMaterialCentreGroup();
        }else if (Cv.ACTION_DELETE_MATERIAL_CENTRE_GROUP.equals(action)) {
            handleDeleteMaterialCentreGroup();
        }else if (Cv.ACTION_GET_MATERIAL_CENTRE_GROUP_DETAILS.equals(action)) {
            handleGetMaterialCentreGroupDetails();
        }else if (Cv.ACTION_GET_MATERIAL_CENTRE_LIST.equals(action)) {
            handleGetMaterialCentreList();
        }else if (Cv.ACTION_CREATE_MATERIAL_CENTRE.equals(action)) {
            handleCreateMaterialCentre();
        }else if (Cv.ACTION_EDIT_MATERIAL_CENTRE.equals(action)) {
            handleEditMaterialCentre();
        }else if (Cv.ACTION_DELETE_MATERIAL_CENTRE.equals(action)) {
            handleDeleteMaterialCentre();
        }else if (Cv.ACTION_GET_MATERIAL_CENTRE_DETAILS.equals(action)) {
            handleGetMaterialCentreDetails();
        }else if (Cv.ACTION_GET_STOCK.equals(action)) {
            handleGetStock();
        }else if (Cv.ACTION_GET_UNIT_LIST.equals(action)) {
            handleGetUnitList();
        }else if (Cv.ACTION_CREATE_UNIT.equals(action)) {
            handleCreateUnit();
        }else if (Cv.ACTION_EDIT_UNIT.equals(action)) {
            handleEditUnit();
        }else if (Cv.ACTION_DELETE_UNIT.equals(action)) {
            handleDeleteUnit();
        }else if (Cv.ACTION_GET_UNIT_DETAILS.equals(action)) {
            handleGetUnitDetails();
        }else if (Cv.ACTION_GET_UNIT_CONVERSION_LIST.equals(action)) {
            handleGetUnitConversionList();
        }else if (Cv.ACTION_CREATE_UNIT_CONVERSION.equals(action)) {
            handleCreateUnitConversion();
        }else if (Cv.ACTION_EDIT_UNIT_CONVERSION.equals(action)) {
            handleEditUnitConversion();
        }else if (Cv.ACTION_DELETE_UNIT_CONVERSION.equals(action)) {
            handleDeleteUnitConversion();
        }else if (Cv.ACTION_GET_UNIT_CONVERSION_DETAILS.equals(action)) {
            handleGetUnitConversionDetails();
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
        api.getpackage().enqueue(new Callback<GetPackageResponse>() {
            @Override
            public void onResponse(Call<GetPackageResponse> call, Response<GetPackageResponse> r) {
                if (r.code() == 200) {
                    GetPackageResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPackageResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handlePlans() {
        api.plan(new RequestPlan(this)).enqueue(new Callback<PlanResponse>() {
            @Override
            public void onResponse(Call<PlanResponse> call, Response<PlanResponse> r) {
                if (r.code() == 200) {
                    PlanResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetMaterialCentreGroupList() {
        api.getmaterialcentregrouplist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetMaterialCentreGroupListResponse>() {
            @Override
            public void onResponse(Call<GetMaterialCentreGroupListResponse> call, Response<GetMaterialCentreGroupListResponse> r) {
                if (r.code() == 200) {
                    GetMaterialCentreGroupListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetMaterialCentreGroupListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCreateMaterialCentreGroup() {
        api.creatematerialcentregroup(new RequestCreateMaterialCentreGroup(this)).enqueue(new Callback<CreateMaterialCentreGroupResponse>() {
            @Override
            public void onResponse(Call<CreateMaterialCentreGroupResponse> call, Response<CreateMaterialCentreGroupResponse> r) {
                if (r.code() == 200) {
                    CreateMaterialCentreGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateMaterialCentreGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleEditMaterialCentreGroup() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.editmaterialcentregroup(new RequestCreateMaterialCentreGroup(this),appUser.edit_material_centre_group_id).enqueue(new Callback<EditMaterialCentreGroupResponse>() {
            @Override
            public void onResponse(Call<EditMaterialCentreGroupResponse> call, Response<EditMaterialCentreGroupResponse> r) {
                if (r.code() == 200) {
                    EditMaterialCentreGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditMaterialCentreGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleDeleteMaterialCentreGroup() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.deletematerialcentregroup(appUser.delete_material_centre_group_id).enqueue(new Callback<DeleteMaterialCentreGroupResponse>() {
            @Override
            public void onResponse(Call<DeleteMaterialCentreGroupResponse> call, Response<DeleteMaterialCentreGroupResponse> r) {
                if (r.code() == 200) {
                    DeleteMaterialCentreGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteMaterialCentreGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetMaterialCentreGroupDetails() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.getmaterialcentregroupdetails(appUser.edit_material_centre_group_id).enqueue(new Callback<GetMaterialCentreGroupDetailResponse>() {
            @Override
            public void onResponse(Call<GetMaterialCentreGroupDetailResponse> call, Response<GetMaterialCentreGroupDetailResponse> r) {
                if (r.code() == 200) {
                    GetMaterialCentreGroupDetailResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetMaterialCentreGroupDetailResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetMaterialCentreList() {
        api.getmaterialcentrelist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetMaterialCentreListResponse>() {
            @Override
            public void onResponse(Call<GetMaterialCentreListResponse> call, Response<GetMaterialCentreListResponse> r) {
                if (r.code() == 200) {
                    GetMaterialCentreListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetMaterialCentreListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCreateMaterialCentre() {
        api.creatematerialcentre(new RequestCreateMaterialCentre(this)).enqueue(new Callback<CreateMaterialCentreResponse>() {
            @Override
            public void onResponse(Call<CreateMaterialCentreResponse> call, Response<CreateMaterialCentreResponse> r) {
                if (r.code() == 200) {
                    CreateMaterialCentreResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateMaterialCentreResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleEditMaterialCentre() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.editmaterialcentre(new RequestCreateMaterialCentre(this),appUser.edit_material_centre_id).enqueue(new Callback<EditMaterialCentreReponse>() {
            @Override
            public void onResponse(Call<EditMaterialCentreReponse> call, Response<EditMaterialCentreReponse> r) {
                if (r.code() == 200) {
                    EditMaterialCentreReponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditMaterialCentreReponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleDeleteMaterialCentre() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.deletematerialcentre(appUser.delete_material_centre_id).enqueue(new Callback<DeleteMaterialCentreResponse>() {
            @Override
            public void onResponse(Call<DeleteMaterialCentreResponse> call, Response<DeleteMaterialCentreResponse> r) {
                if (r.code() == 200) {
                    DeleteMaterialCentreResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteMaterialCentreResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetMaterialCentreDetails() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.getmaterialcentredetails(appUser.edit_material_centre_id).enqueue(new Callback<GetMaterialCentreDetailResponse>() {
            @Override
            public void onResponse(Call<GetMaterialCentreDetailResponse> call, Response<GetMaterialCentreDetailResponse> r) {
                if (r.code() == 200) {
                    GetMaterialCentreDetailResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetMaterialCentreDetailResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetStock() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.getstock(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> r) {
                if (r.code() == 200) {
                    StockResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetUnitList() {
        api.getunitlist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetUnitListResponse>() {
            @Override
            public void onResponse(Call<GetUnitListResponse> call, Response<GetUnitListResponse> r) {
                if (r.code() == 200) {
                    GetUnitListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUnitListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCreateUnit() {
        api.createunit(new RequestCreateUnit(this)).enqueue(new Callback<CreateUnitResponse>() {
            @Override
            public void onResponse(Call<CreateUnitResponse> call, Response<CreateUnitResponse> r) {
                if (r.code() == 200) {
                    CreateUnitResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateUnitResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleEditUnit() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.editunit(new RequestCreateUnit(this),appUser.edit_unit_id).enqueue(new Callback<EditUnitResponse>() {
            @Override
            public void onResponse(Call<EditUnitResponse> call, Response<EditUnitResponse> r) {
                if (r.code() == 200) {
                    EditUnitResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditUnitResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleDeleteUnit() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.deleteunit(appUser.delete_unit_id).enqueue(new Callback<DeleteUnitResponse>() {
            @Override
            public void onResponse(Call<DeleteUnitResponse> call, Response<DeleteUnitResponse> r) {
                if (r.code() == 200) {
                    DeleteUnitResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteUnitResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetUnitDetails() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.getunitdetails(appUser.edit_unit_id).enqueue(new Callback<GetUnitDetailsResponse>() {
            @Override
            public void onResponse(Call<GetUnitDetailsResponse> call, Response<GetUnitDetailsResponse> r) {
                if (r.code() == 200) {
                    GetUnitDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUnitDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetUnitConversionList() {
        api.getunitconversionlist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetUnitConversionListResponse>() {
            @Override
            public void onResponse(Call<GetUnitConversionListResponse> call, Response<GetUnitConversionListResponse> r) {
                if (r.code() == 200) {
                    GetUnitConversionListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUnitConversionListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleCreateUnitConversion() {
        api.createunitconversion(new RequestCreateUnitConversion(this)).enqueue(new Callback<CreateUnitConversionResponse>() {
            @Override
            public void onResponse(Call<CreateUnitConversionResponse> call, Response<CreateUnitConversionResponse> r) {
                if (r.code() == 200) {
                    CreateUnitConversionResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateUnitConversionResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleEditUnitConversion() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.editunitconversion(new RequestCreateUnitConversion(this),appUser.edit_unit_conversion_id).enqueue(new Callback<EditUnitConversionResponse>() {
            @Override
            public void onResponse(Call<EditUnitConversionResponse> call, Response<EditUnitConversionResponse> r) {
                if (r.code() == 200) {
                    EditUnitConversionResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditUnitConversionResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleDeleteUnitConversion() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.deleteunitconversion(appUser.delete_unit_conversion_id).enqueue(new Callback<DeleteUnitConversionResponse>() {
            @Override
            public void onResponse(Call<DeleteUnitConversionResponse> call, Response<DeleteUnitConversionResponse> r) {
                if (r.code() == 200) {
                    DeleteUnitConversionResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteUnitConversionResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }
    private void handleGetUnitConversionDetails() {
        AppUser appUser=LocalRepositories.getAppUser(this);
        api.getunitconversiondetails(appUser.edit_unit_conversion_id).enqueue(new Callback<GetUnitConversionDetailsResponse>() {
            @Override
            public void onResponse(Call<GetUnitConversionDetailsResponse> call, Response<GetUnitConversionDetailsResponse> r) {
                if (r.code() == 200) {
                    GetUnitConversionDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUnitConversionDetailsResponse> call, Throwable t) {
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



