package com.lkintechnology.mBilling.activities.company.transaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.fragments.transaction.sale.CreateSaleVoucherFragment;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.Preferences;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageOpenActivity extends AppCompatActivity {
    @Bind(R.id.image_open)
    ImageView mImageOpen;
    String encodedString, title;
    public Boolean boolAttachment;
    public Boolean bitmapPhotos=false;
    private static final String TAG = "Touch";
    private static final float MIN_ZOOM = 1.0f, MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_open);
        ButterKnife.bind(this);
        // initActionbar();
        Intent intent = getIntent();
        boolAttachment = getIntent().getExtras().getBoolean("booleAttachment");
        if (boolAttachment == true) {
            encodedString = intent.getStringExtra("attachment");
            /*Glide.with(this).load(Uri.parse(encodedString))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(mImageOpen);
                     mImageOpen.setVisibility(View.VISIBLE);*/
            Timber.i("aaaaaaaaaaaaaaaaaaa" + encodedString);

            Picasso.with(getApplicationContext())
                    .load(encodedString)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(mImageOpen);
            mImageOpen.setVisibility(View.VISIBLE);
                    /*.into(mImageOpen, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });*/
        } else {
            encodedString = intent.getStringExtra("encodedString");
            bitmapPhotos = intent.getBooleanExtra("bitmapPhotos",false);
            boolean b=intent.getBooleanExtra("iEncodedString",false);
            if (b){
                if (bitmapPhotos){
                    if (!Preferences.getInstance(this).getUrl_attachment().equals("")){
                        Picasso.with(getApplicationContext())
                                .load(Preferences.getInstance(this).getUrl_attachment())
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .into(mImageOpen);
                    }else {
                        mImageOpen.setImageBitmap(Helpers.base64ToBitmap(Preferences.getInstance(this).getAttachment()));
                    }
                }else {
                    mImageOpen.setImageBitmap(Helpers.base64ToBitmap(encodedString));
                }
            }else {
                mImageOpen.setImageURI(Uri.parse(encodedString));
            }
//            mImageOpen.setImageURI(Uri.parse(encodedString));
            Timber.i("filepath" + encodedString);
            //mImageOpen.setImageBitmap(Helpers.base64ToBitmap(encodedString));
        }
       /* mImageOpen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mImageOpen.setScaleType(ImageView.ScaleType.MATRIX);
                float scale;

                dumpEvent(event);
                // Handle touch events here...

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:   // first finger down only
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                        Log.d(TAG, "mode=DRAG"); // write to LogCat
                        mode = DRAG;
                        break;

                    case MotionEvent.ACTION_UP: // first finger lifted

                    case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                        mode = NONE;
                        Log.d(TAG, "mode=NONE");
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                        oldDist = spacing(event);
                        Log.d(TAG, "oldDist=" + oldDist);
                        if (oldDist > 5f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                            Log.d(TAG, "mode=ZOOM");
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:

                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                        } else if (mode == ZOOM) {
                            // pinch zooming
                            float[] f = new float[9];
                            float newDist = spacing(event);
                            Log.d(TAG, "newDist=" + newDist);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                scale = newDist / oldDist; // setting the scaling of the
                                // matrix...if scale > 1 means
                                // zoom in...if scale < 1 means
                                // zoom out
                                matrix.postScale(scale, scale, mid.x, mid.y);
                            }
                            matrix.getValues(f);
                            float scaleX = f[Matrix.MSCALE_X];
                            float scaleY = f[Matrix.MSCALE_Y];

                            if(scaleX <= 0.3f) {
                                matrix.postScale((0.3f)/scaleX, (0.3f)/scaleY, mid.x, mid.y);
                            } else if(scaleX >= 2.5f) {
                                matrix.postScale((2.5f)/scaleX, (2.5f)/scaleY, mid.x, mid.y);
                            }
                        }
                        break;
                }

                mImageOpen.setImageMatrix(matrix);
                return true;
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

       /* Intent intent = new Intent(this, CreateBankCaseDepositActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

}