package com.bibliophile.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.service.Config;
import com.bibliophile.service.NotificationUtils;
import com.bibliophile.utilitys.Utilis_;
import com.google.firebase.messaging.FirebaseMessaging;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private ImageView imgCenter, imgStudent;
    private TextView activityTitle;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        mContext = SelectionActivity.this;
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };
        initialize();

    }

    private void initialize() {
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.app_name));

        imgCenter = (ImageView) findViewById(R.id.imgCenter);
        imgStudent = (ImageView) findViewById(R.id.imgStudent);


        imgStudent.setOnClickListener(this);
        imgCenter.setOnClickListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //  Constant.test(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgCenter:
                SharedPref.putSP(Constant.LOGIN_TYPE,Constant.LOGIN_CENTER);
                Utilis_.startActivityPutValue(mContext, LoginActivity.class, Constant.LOGIN_CENTER);
                break;
            case R.id.imgStudent:
                SharedPref.putSP(Constant.LOGIN_TYPE,Constant.LOGIN_STUDENT);
                Utilis_.startActivityPutValue(mContext, LoginActivity.class, Constant.LOGIN_STUDENT);
                break;
        }

    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString(Config.FCM_REG_ID, null);

        Log.e("FIREBASE", "Firebase reg id: " + regId);


    }

    @Override
    protected void onResume() {
        super.onResume();


        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
