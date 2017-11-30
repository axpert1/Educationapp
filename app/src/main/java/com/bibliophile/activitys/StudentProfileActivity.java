package com.bibliophile.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.StudentProfileModel;
import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.RequestParams;

/**
 * Created by ANDROID on 9/16/2017.
 */

public class StudentProfileActivity extends AppCompatActivity implements NetworkManager.onCallback {
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;


    private int mMaxScrollSize;

    private TextView activityTitle;
    private Toolbar toolbar;

    private Context mContext;
    private String student_id;


    private ImageView imgProfile;
    private TextView txt_email, txt_phone, txtName, txtphone2, txtCourse, txtAddress;

    private StudentProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile_layout);
        mContext = StudentProfileActivity.this;

        student_id =Utilis_.getintentString(Constant.INTENT_NAME,getIntent());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(this, R.drawable.ic_arrow_back_black_24dp, R.color.white));
        activityTitle.setText(getString(R.string.settings));

        initialize();
    }

    private void initialize() {
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txtName = (TextView) findViewById(R.id.txtName);
        txtphone2 = (TextView) findViewById(R.id.txtphone2);
        txtCourse = (TextView) findViewById(R.id.txtCourse);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        // TODO: 10/16/2017 API
        apiCall(getString(R.string.please_wait), AppUrls.GET_STUDENT_DETAILS, getParams(), true, Constant.WHITCH_1);
    }


    private RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.put("student_id", student_id);
        return params;
    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        profileModel = JsonDeserializer.deserializeJson(response, StudentProfileModel.class);
        if (profileModel.status == 1) {
            setText();
        }
    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }

    private void setText() {
        Utilis_.imageFromUrl(mContext, imgProfile, profileModel.path + profileModel.studentDetails.get(0).profileImage);
        Utilis_.setTextValueNA(txt_email, profileModel.studentDetails.get(0).email);
        Utilis_.setTextValueNA(txt_phone, profileModel.studentDetails.get(0).mobile);
        Utilis_.setTextValueNA(txtName, profileModel.studentDetails.get(0).studentName);
        Utilis_.setTextValueNA(txtphone2, profileModel.studentDetails.get(0).mobile);
        Utilis_.setTextValueNA(txtCourse, "");
        Utilis_.setTextValueNA(txtAddress, profileModel.studentDetails.get(0).address);

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

}
