package com.berylsystems.buzz.networks;


import com.berylsystems.buzz.networks.api_request.RequestCreateCompany;
import com.berylsystems.buzz.networks.api_request.RequestForgotPassword;
import com.berylsystems.buzz.networks.api_request.RequestLoginEmail;
import com.berylsystems.buzz.networks.api_request.RequestNewPassword;
import com.berylsystems.buzz.networks.api_request.RequestRegister;
import com.berylsystems.buzz.networks.api_request.RequestResendOtp;
import com.berylsystems.buzz.networks.api_request.RequestUpdateMobileNumber;
import com.berylsystems.buzz.networks.api_request.RequestVerification;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.networks.api_response.otp.OtpResponse;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.networks.api_response.userexist.UserExistResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
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
    @POST("create_company")
    Call<CreateCompanyResponse>createcompany(@Body RequestCreateCompany payload);

}

