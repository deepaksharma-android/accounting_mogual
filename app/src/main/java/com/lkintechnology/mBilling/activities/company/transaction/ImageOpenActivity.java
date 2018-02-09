package com.lkintechnology.mBilling.activities.company.transaction;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.utils.Helpers;
import com.squareup.picasso.Picasso;
import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageOpenActivity extends AppCompatActivity {
    @Bind(R.id.image_open)
    ImageView mImageOpen;
    String encodedString,title;
    public Boolean boolAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_open);
        ButterKnife.bind(this);
       // initActionbar();
        Intent intent =getIntent();
        boolAttachment = getIntent().getBooleanExtra("booleAttachment",false);
        if(boolAttachment==true){
            encodedString=intent.getStringExtra("attachment");
            /*Glide.with(this).load(Uri.parse(encodedString))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(mImageOpen);
                     mImageOpen.setVisibility(View.VISIBLE);*/
            Timber.i("aaaaaaaaaaaaaaaaaaa"+encodedString);

            Picasso.with(getApplicationContext())
                    .load(encodedString)
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
        }else {

            encodedString=intent.getStringExtra("encodedString");
            mImageOpen.setImageURI(Uri.parse(encodedString));
            //mImageOpen.setImageBitmap(Helpers.base64ToBitmap(encodedString));
        }

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
}
