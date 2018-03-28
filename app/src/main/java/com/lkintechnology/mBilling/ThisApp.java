package com.lkintechnology.mBilling;

import android.app.Application;
import android.content.Context;

import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.networks.Api;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ThisApp extends Application

    {


        public static ThisApp get(Context ctx) {
        return (ThisApp) ctx.getApplicationContext();
    }

    public static Api getApi(Context ctx) {
        return ThisApp.get(ctx).api;
    }

    private Api api;
    private static ThisApp mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) +
                            ":timber: line=" + element.getLineNumber() +
                            " method: " + element.getMethodName();
                }
            });
        }
        api = createApi();
    }

    public static synchronized ThisApp getInstance() {
        return mInstance;
    }



    private Api createApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        client.addInterceptor(chain -> {

            Request.Builder builder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/vnd.accounts-backend.v1");

            String token= LocalRepositories.getAppUser(this).auth_token;
            Timber.i("Authorization"+token);
            if (null != token) {
                builder.addHeader("Authorization",token);
            }


            return chain.proceed(builder.build());
        });

        if (BuildConfig.DEBUG) {
            client.addInterceptor(interceptor);
        }

        return new Retrofit.Builder()
                .baseUrl(Cv.BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api.class);
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}