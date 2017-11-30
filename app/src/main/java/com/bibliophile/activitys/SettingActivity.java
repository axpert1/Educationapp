package com.bibliophile.activitys;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.CommanJson;
import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.RequestParams;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, NetworkManager.onCallback {
    private TextView activityTitle;
    private Toolbar toolbar;
    private DilogCustom dilogCustom;

    private Context mContext;

    private ImageView imvAccount;
    private ImageView imvChangePassword;
    private ImageView imvNotif;
    private ImageView imvContactUs;
    private ImageView imvRateUs;
    private ImageView imvTermsCond;
    private ImageView imvLogout;

    private LinearLayout llLogout;
    private LinearLayout llEditProfile;
    private LinearLayout llTerms;
    private LinearLayout llSupport;
    private LinearLayout llRateUs;
    private LinearLayout llChangePassword;


    private Switch swPushNotifi;
    private View.OnClickListener retryClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        mContext = SettingActivity.this;
        dilogCustom = new DilogCustom();

        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));

        imvAccount = (ImageView) findViewById(R.id.imvAccount);
        imvChangePassword = (ImageView) findViewById(R.id.imvChangePassword);
        imvNotif = (ImageView) findViewById(R.id.imvNotif);
        imvContactUs = (ImageView) findViewById(R.id.imvContactUs);
        imvRateUs = (ImageView) findViewById(R.id.imvRateUs);
        imvTermsCond = (ImageView) findViewById(R.id.imvTermsCond);
        imvLogout = (ImageView) findViewById(R.id.imvLogout);

        llLogout = (LinearLayout) findViewById(R.id.llLogout);
        llEditProfile = (LinearLayout) findViewById(R.id.llEditProfile);
        llTerms = (LinearLayout) findViewById(R.id.llTerms);
        llSupport = (LinearLayout) findViewById(R.id.llSupport);
        llRateUs = (LinearLayout) findViewById(R.id.llRateUs);
        llChangePassword = (LinearLayout) findViewById(R.id.llChangePassword);

        swPushNotifi = (Switch) findViewById(R.id.swPushNotifi);

        llLogout.setOnClickListener(this);
        llEditProfile.setOnClickListener(this);
        llTerms.setOnClickListener(this);
        llSupport.setOnClickListener(this);
        llRateUs.setOnClickListener(this);
        llChangePassword.setOnClickListener(this);

        swPushNotifi.setChecked(SharedPref.getboolSP(Constant.NOTIFICATION_SHOW));
        activityTitle.setText(getString(R.string.settings));
        swPushNotifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPref.putboolSP(Constant.NOTIFICATION_SHOW, b);

            }
        });

        retryClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilogCustom.dismissRetryAlert();
                apiCall(getString(R.string.please_wait), AppUrls.LOGOUT, logoutParams(), true, Constant.WHITCH_1);
                // Toast.makeText(mContext, "Log out code", Toast.LENGTH_SHORT).show();

                //Util.startActivityWithFinish(mContext, IntroActivity.class);

            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llEditProfile:
                Utilis_.startActivity(mContext, CenterProfileActivity.class);
                break;
            case R.id.llLogout:
                dilogCustom.retryAlertDialog(mContext, getResources().getString(R.string.app_name), getResources().getString(R.string.msg_logout), getResources().getString(R.string.cancel), getResources().getString(R.string.logout), retryClick);

                break;
            case R.id.llChangePassword:
                Utilis_.startActivity(mContext, ChangePasswordActivity.class);
                break;
            case R.id.llSupport:
                Utilis_.startActivity(mContext, ContactusActivity.class);
                break;
            case R.id.llRateUs:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.llTerms:
                Intent in = new Intent(mContext, WebViewActivity.class);
                in.putExtra("flag", 0);
                startActivity(in);
                break;
        }

    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);

    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        if (Constant.WHITCH_1 == 1) {
            //logout code
            CommanJson json = JsonDeserializer.deserializeJson(response, CommanJson.class);
            if (json.status == 1) {
                SharedPref.clearSp();
                Utilis_.finish_previous_activities(mContext, SplaceActivity.class);
            } else {
                Toast.makeText(mContext, json.message, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }

    private RequestParams logoutParams() {
        RequestParams params = new RequestParams();
        params.put("user_id", SharedPref.getSP(Constant.ID));

        return params;
    }

}
