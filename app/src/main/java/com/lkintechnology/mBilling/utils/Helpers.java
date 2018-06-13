package com.lkintechnology.mBilling.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.events.EventFbAuthResponse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import timber.log.Timber;


public class Helpers {

    public static void facebookLogin(CallbackManager callbackManager, LinearLayout fbLoginBtn) {


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                String token = loginResult.getAccessToken().getToken();

                Timber.i("token: " + token);

                Timber.i("login result: " + loginResult);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        (json, response) -> {

                            Timber.i(response.toString());

                            try {

                                Timber.i(json.toString());

                                EventFbAuthResponse fbData = new Gson()
                                        .fromJson(json.toString(), EventFbAuthResponse.class);

                                fbData.token = token;
                                fbData.composeAvatarUrl();
                                EventBus.getDefault().post(fbData);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString(Cv.KEY_FB_PARAMS, Cv.FB_PARAMETERS);
                request.setParameters(parameters);
                request.executeAsync();

                Timber.i("onSuccess.................");
            }

            @Override
            public void onCancel() {
                Timber.i("onCancel................");
            }

            @Override
            public void onError(FacebookException error) {

                Timber.i("onError...........: %s", error.getMessage());
            }
        });
    }


    public static void setProfilePic(ImageView v, String path) {
        v.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    public static String[] createStringRange(int start, int delta) {

        String res[] = new String[delta];

        for (int i = 0; i < delta; i++) {
            res[i] = String.valueOf(start + i);
        }
        return res;
    }

    public static float defineVolume(Context ctx, int stream) {

        AudioManager audioManager = (AudioManager) ctx.getSystemService(ctx.AUDIO_SERVICE);

        final float actualVolume = (float) audioManager.getStreamVolume(stream);
        final float maxVolume = (float) audioManager.getStreamMaxVolume(stream);
        float volume = actualVolume / maxVolume;
        volume = volume == 0 ? .5f : volume;
        return volume;
    }


    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isNetworkAvailableWithDialog(Context ctx) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        boolean available = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if (!available) {
            new AlertDialog.Builder(ctx)
                    .setMessage("Check out your Network connection!")
                    .setPositiveButton(ctx.getString(R.string.ok), null)
                    .show();
        }
        return available;
    }

    public static void isLocationEnabled(Context ctx) {

        LocationManager service = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        boolean gpsEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean netEnabled = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled && !netEnabled) {

            new AlertDialog.Builder(ctx)
                    .setMessage(ctx.getString(R.string.enable_location))
                    .setPositiveButton(ctx.getString(R.string.ok), (d, i) ->
                            ctx.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .show();
        }
    }

    public static void isGPSEnabled(Context ctx) {

        Timber.d("isGPSEnabled...............");

        LocationManager service = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        boolean gpsEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {

            new AlertDialog.Builder(ctx)
                    .setMessage(ctx.getString(R.string.enable_gps))
                    .setPositiveButton(ctx.getString(R.string.ok), (d, i) ->
                            ctx.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton(ctx.getString(R.string.btn_cancel), null)
                    .show();
        }
    }

    public static String getDeviceId(Context ctx) {

        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

        String deviceId;

        if (tm.getDeviceId() != null) {

            deviceId = tm.getDeviceId();
        } else {

            deviceId = Settings.Secure.getString(
                    ctx.getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static Bitmap getRoundBitmap(Bitmap bitmap) {

        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());

        Bitmap bitmapRounded = Bitmap.createBitmap(min, min, bitmap.getConfig());

        Canvas canvas = new Canvas(bitmapRounded);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0.0f, 0.0f, min, min)), min / 2, min / 2, paint);

        return bitmapRounded;
    }


    /****
     * Method for Setting the Height of the ListView dynamically.
     * *** Hack to fix the issue of not showing all the items of the ListView
     * *** when placed inside a ScrollView
     ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 200;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static String mystring(String string) {
        if (string == null||string.equals("null")) {
            string = "";
        }
        return string;
    }

    public static String  getMonth(int month){
        String[] ar={"Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};
        return ar[month];
    }

    public static int  dateValidation(String start, String end){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date dateObject1 = null;
        Date dateObject2 = null;
        try {
            dateObject1 = dateFormatter.parse(start);
            dateObject2 = dateFormatter.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       /* if (dateObject2.compareTo(dateObject1) == -1) {
            return;
        }*/
        return dateObject2.compareTo(dateObject1);
    }

    public static Bitmap selectAttachmentUniversal(Context context, Intent data){
        Uri uri = data.getData();
        Bitmap bm = null;
        try {
            bm= MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static void dialogMessage(Context context ,String message){
        new AlertDialog.Builder(context)
                .setTitle("m-Billing")
                .setMessage(message)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Your code
                    }

                }).show();
               /* .setNegativeButton(R.string.btn_cancel, null)
                .show();*/
    }
}
