package com.bibliophile.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.model.CenterProfileJson;
import com.bibliophile.retroFitService.retrofit.NetworkManagerRetroFit;
import com.bibliophile.retroFitService.retrofit.RequestInterface;
import com.bibliophile.utilitys.Utilis_;
import com.google.gson.JsonObject;

import retrofit2.Call;

/**
 * Created by ANDROID on 9/16/2017.
 */

public class CenterProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, NetworkManagerRetroFit.onCallback {
    private Context mContext;

    private TextView txtCenterName, txtCenterEmail, txtCenterPhone, txtCenterPersonName, txtCenterCourse, txtCenterAddress, txtCenterPackage, txtCenterPaymentType, txtCenterTotalAmount, txtCenterPayAmount;

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;

    private TextView activityTitle;
    private Toolbar toolbar;

    //API
    NetworkManagerRetroFit mNetwork;
    RequestInterface request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_profile);
        mContext = CenterProfileActivity.this;
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);


        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(this, R.drawable.ic_arrow_back_black_24dp, R.color.white));
        activityTitle.setText(getString(R.string.settings));
        initialize();
        apiInitialize();
    }

    private void initialize() {
        txtCenterName = (TextView) findViewById(R.id.txtCenterName);
        txtCenterEmail = (TextView) findViewById(R.id.txtCenterEmail);
        txtCenterPhone = (TextView) findViewById(R.id.txtCenterPhone);
        txtCenterPersonName = (TextView) findViewById(R.id.txtCenterPersonName);
        txtCenterCourse = (TextView) findViewById(R.id.txtCenterCourse);
        txtCenterAddress = (TextView) findViewById(R.id.txtCenterAddress);
        txtCenterPackage = (TextView) findViewById(R.id.txtCenterPackage);
        txtCenterPaymentType = (TextView) findViewById(R.id.txtCenterPaymentType);
        txtCenterTotalAmount = (TextView) findViewById(R.id.txtCenterTotalAmount);
        txtCenterPayAmount = (TextView) findViewById(R.id.txtCenterPayAmount);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            mProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    private void apiInitialize() {
        mNetwork = new NetworkManagerRetroFit();
        request = mNetwork.getRetrofit();
        apiCall(request.getCenterProfile(SharedPref.getSP(Constant.CENTER_ID)), true, Constant.WHITCH_1, getString(R.string.please_wait));

    }

    private void apiCall(Call<JsonObject> call, boolean progressBar, int whichApi, String titleProgress) {
        mNetwork.callAPI(call, mContext, this, progressBar, whichApi, titleProgress);
    }

    @Override
    public void onResponce(boolean success, int which, String response) {
        CenterProfileJson profileJson = JsonDeserializer.deserializeJson(response, CenterProfileJson.class);
        if (profileJson.status == 1) {

            Utilis_.setTextValueNA(txtCenterName, profileJson.userProfile.centerName);
            Utilis_.setTextValueNA(txtCenterEmail, profileJson.userProfile.email);
            Utilis_.setTextValueNA(txtCenterPhone, profileJson.userProfile.mobile);
            Utilis_.setTextValueNA(txtCenterPersonName, profileJson.userProfile.contactPersonName);
            Utilis_.setTextValueNA(txtCenterCourse, profileJson.userProfile.courseId);
            Utilis_.setTextValueNA(txtCenterAddress, profileJson.userProfile.address);
            Utilis_.setTextValueNA(txtCenterPackage, profileJson.userProfile.packageId);
            Utilis_.setTextValueNA(txtCenterPaymentType, profileJson.userProfile.paymentMode);
            Utilis_.setTextValueNA(txtCenterTotalAmount, "");
            Utilis_.setTextValueNA(txtCenterPayAmount, "");
        }
    }
}
