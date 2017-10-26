package com.berylsystems.buzz.networks;


import com.berylsystems.buzz.networks.api_response.UserResponse.UserApiResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pc on 10/12/2016.
 */
public class LocalCallback implements Callback<UserApiResponse> {

    @Override
    public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> response) {

        UserApiResponse body = response.body();

        if (null != body) {
            EventBus.getDefault().post(body.getMessage());
        }
    }

    @Override
    public void onFailure(Call<UserApiResponse> call, Throwable t) {

        try {

            EventBus.getDefault().post(t.getMessage());
        } catch (Exception ex) {
            EventBus.getDefault().post("Something went wrong...");
        }
    }
}

