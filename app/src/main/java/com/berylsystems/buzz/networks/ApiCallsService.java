package com.berylsystems.buzz.networks;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.berylsystems.buzz.ThisApp;
import com.berylsystems.buzz.networks.api_request.RequestForgotPassword;
import com.berylsystems.buzz.networks.api_request.RequestLoginEmail;
import com.berylsystems.buzz.networks.api_request.RequestNewPassword;
import com.berylsystems.buzz.networks.api_request.RequestRegister;
import com.berylsystems.buzz.networks.api_request.RequestResendOtp;
import com.berylsystems.buzz.networks.api_request.RequestUpdateMobileNumber;
import com.berylsystems.buzz.networks.api_request.RequestVerification;
import com.berylsystems.buzz.networks.api_response.UserResponse.UserApiResponse;
import com.berylsystems.buzz.utils.Cv;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


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

    private void handleResendOtp() {

        api.resendotp(new RequestResendOtp(this)).enqueue(new Callback<UserApiResponse>() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                if (r.code() == 200) {
                    UserApiResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
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

    private void processUserApiResponse(Response<UserApiResponse> response) {
        if (response.code() == 200) {
            UserApiResponse body = response.body();
            EventBus.getDefault().post(body);
        } else {
            EventBus.getDefault().post(Cv.TIMEOUT);
        }

    }
}



