package com.bibliophile.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.fragments.F_CenterDashBord;
import com.bibliophile.fragments.F_StudentDashBord;
import com.bibliophile.utilitys.Utilis_;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, DilogCustom.onNewGroup {
    private Toolbar toolbar;

    private TextView txtCounter;

    private ImageView imvSettings, imvCenter;


    boolean doubleBackToExitPressedOnce = false;

    private Fragment fragment = null;

    private Context mContext;

    private ImageButton imBtnTruck;
    private ImageButton imBtnCart;
    private ImageButton imBtnHotDeal;
    private ImageButton imBtnHistory;
    private ImageButton imBtnNotifi;

    private View.OnClickListener retryClick;
    private boolean mShowingBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_dashboard);
        mContext = DashboardActivity.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtCounter = (TextView) findViewById(R.id.txtCounter);
        imvSettings = (ImageView) findViewById(R.id.imvSettings);
        imvCenter = (ImageView) findViewById(R.id.imvCenter);
        imBtnTruck = (ImageButton) findViewById(R.id.imBtnTruck);
        imBtnCart = (ImageButton) findViewById(R.id.imBtnCart);
        imBtnHotDeal = (ImageButton) findViewById(R.id.imBtnHotDeal);
        imBtnHistory = (ImageButton) findViewById(R.id.imBtnHistory);
        imBtnNotifi = (ImageButton) findViewById(R.id.imBtnNotifi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imvSettings.setVisibility(View.VISIBLE);
        imvCenter.setVisibility(View.VISIBLE);
        imBtnTruck.setOnClickListener(this);
        imBtnCart.setOnClickListener(this);
        imBtnHotDeal.setOnClickListener(this);
        imBtnHistory.setOnClickListener(this);
        imBtnNotifi.setOnClickListener(this);

//        if (SharedPref.getOrderCount(mContext) > 0) {
//            txtCounter.setVisibility(View.VISIBLE);
//            txtCounter.setText("" + SharedPref.getOrderCount(mContext));
//        } else {
//            txtCounter.setVisibility(View.GONE);
//        }

        setBottomBar(mContext, imBtnTruck, imBtnCart, imBtnHotDeal, imBtnHistory, imBtnNotifi);
        if (SharedPref.getSP(Constant.LOGIN_TYPE).equals(Constant.LOGIN_CENTER)) {
            fragment = new F_CenterDashBord();
        } else if (SharedPref.getSP(Constant.LOGIN_TYPE).equals(Constant.LOGIN_STUDENT)) {
            fragment = new F_StudentDashBord();
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fmContainer, fragment).addToBackStack(null).commit();
        }

        imvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilis_.startActivity(mContext, SettingActivity.class);
            }
        });

        retryClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Util.dismissRetryAlert();
            }
        };


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imBtnTruck) {
            if (fragment instanceof F_StudentDashBord) {
                //TODO..
                Utilis_.startActivity(this, AddStudentActivity.class);
//                DilogCustom dilogCustom=new DilogCustom();
//                dilogCustom.newGroupDilog(mContext,this,0);
            } else {
                setBottomBar(mContext, imBtnTruck, imBtnCart, imBtnHotDeal, imBtnHistory, imBtnNotifi);
                fragment = new F_StudentDashBord();
                FragmentManager mFragmentManager = getSupportFragmentManager();
                mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                mFragmentManager.beginTransaction().replace(R.id.fmContainer, fragment).addToBackStack(null).commit();
            }
        } else {
            if (v.getId() == R.id.imBtnCart) {
                setBottomBar(mContext, imBtnCart, imBtnTruck, imBtnHotDeal, imBtnHistory, imBtnNotifi);

                fragment = new F_StudentDashBord();

            } else if (v.getId() == R.id.imBtnHotDeal) {
                setBottomBar(mContext, imBtnHotDeal, imBtnCart, imBtnTruck, imBtnHistory, imBtnNotifi);

                fragment = new F_CenterDashBord();

            } else if (v.getId() == R.id.imBtnHistory) {
                setBottomBar(mContext, imBtnHistory, imBtnHotDeal, imBtnCart, imBtnTruck, imBtnNotifi);

                fragment = new F_StudentDashBord();

            } else if (v.getId() == R.id.imBtnNotifi) {
                setBottomBar(mContext, imBtnNotifi, imBtnHistory, imBtnHotDeal, imBtnCart, imBtnTruck);

                fragment = new F_StudentDashBord();
            }

            if (fragment != null) {
                FragmentManager mFragmentManager = getSupportFragmentManager();
                mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                mFragmentManager.beginTransaction().replace(R.id.fmContainer, fragment).addToBackStack(null).commit();
            }
        }
    }

    public static void setBottomBar(Context mContext, ImageButton view1, ImageButton view2, ImageButton view3, ImageButton view4, ImageButton view5) {
        view1.setColorFilter(mContext.getResources().getColor(R.color.purple));
        view2.setColorFilter(mContext.getResources().getColor(R.color.white));
        view3.setColorFilter(mContext.getResources().getColor(R.color.white));
        view4.setColorFilter(mContext.getResources().getColor(R.color.white));
        view5.setColorFilter(mContext.getResources().getColor(R.color.white));
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 1) {
            backCountToExit();
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            backCountToExit();
        }
    }

    private void backCountToExit() {
        if (doubleBackToExitPressedOnce) {
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onCancel(boolean success, int which) {

    }

    @Override
    public void onSeccess(boolean success, int which) {

    }
}