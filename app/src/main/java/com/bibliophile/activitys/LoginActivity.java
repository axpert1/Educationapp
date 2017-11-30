package com.bibliophile.activitys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.datas.ValidField;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.model.CenterLoginJson;
import com.bibliophile.model.StudentLoginJson;
import com.bibliophile.retroFitService.retrofit.NetworkManagerRetroFit;
import com.bibliophile.retroFitService.retrofit.RequestInterface;
import com.bibliophile.utilitys.Utilis_;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, DilogCustom.onCallbackMultiItem, NetworkManagerRetroFit.onCallback {
    private EditText eTextEmailPhone, eTextLoginPass;
    private Button btnLogin, btnJoinus;
    private Context mContext;
    private TextView activityTitle, txtForgotPass;
    private Toolbar toolbar;
    private ImageView imgIcon;
    private String loginType;

    private View.OnClickListener retryClick;
    DilogCustom dilogCustom;

    //API
    NetworkManagerRetroFit mNetwork;
    RequestInterface request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        dilogCustom = new DilogCustom();
        initialize();
        apiInitialize();
    }

    private void initialize() {
        loginType = Utilis_.getintentString(Constant.INTENT_NAME, getIntent());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.login_to_existing_acc));
        activityTitle.setTextSize(16);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));


        eTextEmailPhone = (EditText) findViewById(R.id.eTextEmailPhone);
        eTextLoginPass = (EditText) findViewById(R.id.eTextLoginPass);
        txtForgotPass = (TextView) findViewById(R.id.txtForgotPass);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnJoinus = (Button) findViewById(R.id.btnJoinus);
        btnLogin.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        btnJoinus.setOnClickListener(this);
        retryClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilogCustom.dismissRetryAlert();
            }
        };

        setLoginType();
    }

    private void setLoginType() {
        if (loginType.toLowerCase().equals(Constant.LOGIN_CENTER)) {
            btnJoinus.setVisibility(View.VISIBLE);
            imgIcon.setImageResource(R.drawable.ic_center_icon);
        } else if (loginType.toLowerCase().equals(Constant.LOGIN_STUDENT)) {
            btnJoinus.setVisibility(View.GONE);
            imgIcon.setImageResource(R.drawable.ic_student_logo);
        }
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Constant.test(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (loginType.toLowerCase().equals(Constant.LOGIN_CENTER)) {
                    if (getTextEmpety()) {
                        apiCall(request.centerLogin(Utilis_.getEditText(eTextEmailPhone), Utilis_.getEditText(eTextLoginPass)), true, Constant.WHITCH_1, getString(R.string.please_wait));
                    }
                } else if (loginType.toLowerCase().equals(Constant.LOGIN_STUDENT)) {
                    apiCall(request.studentLogin(Utilis_.getEditText(eTextEmailPhone), Utilis_.getEditText(eTextLoginPass)), true, Constant.WHITCH_2, getString(R.string.please_wait));

                }
//                DilogCustom dilogCustom = new DilogCustom();
//                dilogCustom.callDilogefullscreen(this, "Select Course", "No message", this, 0, prepareMovieData());
//                if (ValidField.isValidEmailOrPhone(Utilis_.getEditText(eTextEmailPhone)) && ValidField.isValidPass(Utilis_.getEditText(eTextLoginPass))) {
//                    apiCall(getString(R.string.requesting_starting), "https://github.com/daimajia/AndroidImageSlider", null, true, Constant.WHITCH_1);
//                } else {
//                    dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), getString(R.string.msg_email_empty), getString(R.string.cancel), getString(R.string.retry), retryClick);
//                }
                break;
            case R.id.txtForgotPass:
                Utilis_.startActivity(mContext, ForgotPasswordActivity.class);
                break;
            case R.id.btnJoinus:
                Utilis_.startActivity(mContext, JoinUsActivity.class);
                break;
        }
    }

    private void apiInitialize() {
        mNetwork = new NetworkManagerRetroFit();
        request = mNetwork.getRetrofit();
    }

//    // TODO  TEST FOR FUNCTION
//    private List<MultiModel> prepareMovieData() {
//        List<MultiModel> multiModels = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            MultiModel movie = new MultiModel("ABCDEGHIJKL", "ANDROID", false);
//            multiModels.add(movie);
//        }
//        return multiModels;
//    }

    @Override
    public void onPositiveResult(boolean success, int which) {
        Utilis_.showCenterToast(this, "Success");
    }

    private void apiCall(Call<JsonObject> call, boolean progressBar, int whichApi, String titleProgress) {
        mNetwork.callAPI(call, mContext, this, progressBar, whichApi, titleProgress);
    }

//    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
//        NetworkManager networkManager = new NetworkManager();
//        //Context context, String callType, String url, RequestParams params, String title, final NetworkManager.onCallback callback, final boolean isShowLoader, final int which) {
//        networkManager.callAPI(mContext, com.educationapp.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
//
//    }
//    @Override
//    public void onSuccess(boolean success, String response, int which) {
//
//    }
//
//    @Override
//    public void onFailure(boolean success, String response, int which)
//   {
//
//    }

    @Override
    public void onResponce(boolean success, int which, String response) {
        if (which == Constant.WHITCH_1) {
            CenterLoginJson loginJson = JsonDeserializer.deserializeJson(response, CenterLoginJson.class);
            if (loginJson.status == 1) {
                SharedPref.putSP(Constant.ID, loginJson.userDetails.userId);
                SharedPref.putSP(Constant.CENTER_ID, loginJson.userDetails.userId); //id
                SharedPref.putSP(Constant.CENTER_NAME, loginJson.userDetails.centerName); //center_id
                SharedPref.putSP(Constant.USER_EMAIL, loginJson.userDetails.email); //email
                SharedPref.putSP(Constant.USER_MOBILE, loginJson.userDetails.mobile); //mobile
                SharedPref.putSP(Constant.USER_NAME, loginJson.userDetails.username); //name

                Utilis_.startActivityWithFinish(mContext, DashboardActivity.class);
            } else {
                dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), loginJson.msg, getString(R.string.cancel), "", this);
            }
        } else if (which == Constant.WHITCH_2) {
            StudentLoginJson loginJson = JsonDeserializer.deserializeJson(response, StudentLoginJson.class);
            if (loginJson.status == 1) {
                SharedPref.putSP(Constant.ID, loginJson.userDetails.userId); //id
                SharedPref.putSP(Constant.CENTER_ID, loginJson.userDetails.centerId); //center id
                SharedPref.putSP(Constant.USER_EMAIL, loginJson.userDetails.email); //email
                SharedPref.putSP(Constant.USER_MOBILE, loginJson.userDetails.mobile); //mobile
                SharedPref.putSP(Constant.USER_NAME, loginJson.userDetails.username); //name


                Utilis_.startActivityWithFinish(mContext, DashboardActivity.class);
            } else {
                dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), loginJson.msg, getString(R.string.cancel), "", this);
            }
        }
    }

    private boolean getTextEmpety() {
        if (Utilis_.getEditTextBool(eTextEmailPhone)
                && Utilis_.getEditTextBool(eTextLoginPass)
                ) {
            if (ValidField.isValidEmailOrPhone(Utilis_.getEditText(eTextEmailPhone))) {
                if (Utilis_.getEditText(eTextLoginPass).length() > 5) {
                    return true;
                } else {
                    dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), getString(R.string.msg_pass_length), getString(R.string.cancel), "", this);
                    return false;
                }
            } else {
                dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), getString(R.string.msg_invalid_email), getString(R.string.cancel), "", this);
                return false;
            }
        }
        dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), getString(R.string.msg_invalid_email), getString(R.string.cancel), "", this);
        return false;
    }
}
