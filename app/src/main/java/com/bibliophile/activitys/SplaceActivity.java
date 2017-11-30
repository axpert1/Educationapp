package com.bibliophile.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bibliophile.R;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.others.AbsRuntimePermission;
import com.bibliophile.service.NotificationUtils;
import com.bibliophile.utilitys.Utilis_;


public class SplaceActivity extends AbsRuntimePermission {


    private static final int REQUEST_PERMISSION = 10;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=SplaceActivity.this;
//        Intent intent=new Intent(this,SelectionActivity.class);
//        NotificationUtils notificationUtils=new NotificationUtils(this);
//        notificationUtils.showNotificationMessage(getString(R.string.app_name),getString(R.string.msg_email_empty),"00",intent,"");
        requestAppPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE

                },
                R.string.please_enable_all_permission, REQUEST_PERMISSION);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPref.getSP(Constant.ID) != null) {
                    Utilis_.startActivityWithFinish(mContext, DashboardActivity.class);
                } else {
                    Utilis_.startActivityWithFinish(mContext, SelectionActivity.class);
                }

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 2000);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Constant.test(this);
        }
    }
}
