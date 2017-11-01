package com.berylsystems.buzz.networks;


import com.berylsystems.buzz.networks.api_request.RequestCompanyAdditional;
import com.berylsystems.buzz.networks.api_request.RequestCompanyAuthenticate;
import com.berylsystems.buzz.networks.api_request.RequestCompanyDetails;
import com.berylsystems.buzz.networks.api_request.RequestCompanyGst;
import com.berylsystems.buzz.networks.api_request.RequestCompanyLogin;
import com.berylsystems.buzz.networks.api_request.RequestCompanyLogo;
import com.berylsystems.buzz.networks.api_request.RequestCompanySignature;
import com.berylsystems.buzz.networks.api_request.RequestCreateCompany;
import com.berylsystems.buzz.networks.api_request.RequestForgotPassword;
import com.berylsystems.buzz.networks.api_request.RequestLoginEmail;
import com.berylsystems.buzz.networks.api_request.RequestNewPassword;
import com.berylsystems.buzz.networks.api_request.RequestRegister;
import com.berylsystems.buzz.networks.api_request.RequestResendOtp;
import com.berylsystems.buzz.networks.api_request.RequestUpdateMobileNumber;
import com.berylsystems.buzz.networks.api_request.RequestVerification;
import com.berylsystems.buzz.networks.api_response.company.CompanyAuthenticateResponse;
import com.berylsystems.buzz.networks.api_response.company.CompanyListResponse;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.networks.api_response.company.DeleteCompanyResponse;
import com.berylsystems.buzz.networks.api_response.company.IndustryTypeResponse;
import com.berylsystems.buzz.networks.api_response.otp.OtpResponse;
import com.berylsystems.buzz.networks.api_response.packages.PackageResponse;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.networks.api_response.userexist.UserExistResponse;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @POST("signup")
    Call<UserApiResponse> register(@Body RequestRegister payload);
    @PATCH("forgot_password")
    Call<UserApiResponse> forgotpassword(@Body RequestForgotPassword payload);
    @POST("verify_otp")
    Call<UserApiResponse> verifyotp(@Body RequestVerification payload);
    @PATCH("update_user")
    Call<UserApiResponse> newpassword(@Body RequestNewPassword payload);
    @PATCH("resend_otp")
    Call<OtpResponse> resendotp(@Body RequestResendOtp payload);
    @POST("login")
    Call<UserApiResponse>login(@Body RequestLoginEmail payload);
    @PATCH("update_user")
    Call<UserApiResponse> updatemobile(@Body RequestUpdateMobileNumber payload);
    @GET("check_exist")
    Call<UserExistResponse> exist(@Query("fb_id") String fb_id);
    @POST("company/{id}")
    Call<CreateCompanyResponse>createcompany(@Body RequestCreateCompany payload,@Path("id") String id);
    @PATCH("company/{id}")
    Call<CreateCompanyResponse> cdetails(@Body RequestCompanyDetails payload, @Path("id") String id);
    @PATCH("company/{id}")
    Call<CreateCompanyResponse> cgst(@Body RequestCompanyGst payload, @Path("id") String id);
    @PATCH("company/{id}")
    Call<CreateCompanyResponse> cadditional(@Body RequestCompanyAdditional payload, @Path("id") String id);
    @PATCH("company/{id}")
    Call<CreateCompanyResponse> clogo(@Body RequestCompanyLogo payload, @Path("id") String id);
    @PATCH("company/{id}")
    Call<CreateCompanyResponse> csignature(@Body RequestCompanySignature payload, @Path("id") String id);
    @PATCH("company/{id}")
    Call<CreateCompanyResponse> clogin(@Body RequestCompanyLogin payload, @Path("id") String id);
    @GET("get_company/{id}")
    Call<CompanyListResponse> getCompanyList(@Path("id") String id);
    @DELETE("company/{id}")
    Call<DeleteCompanyResponse> cdelete(@Path("id") String id);
    @GET("get_industry")
    Call<IndustryTypeResponse> getIndustry();
    @POST("company_auth/{id}")
    Call<CompanyAuthenticateResponse> cauthenticate(@Body RequestCompanyAuthenticate payload, @Path("id") String id );
    @GET("getplan")
    Call<PackageResponse> getpackage();





}

