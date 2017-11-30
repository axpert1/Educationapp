package com.bibliophile.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

/**
 * Created by ANDROID on 9/18/2017.
 */

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, NetworkManager.onCallback {
    private Toolbar toolbar;
    private Context mContext;
    private TextView activityTitle, txtError;

    private EditText oldpassword, newpassword, confirmpassword;

    private Button btnSave;
    private DilogCustom dilogCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        mContext = ChangePasswordActivity.this;
        dilogCustom = new DilogCustom();

        initialize();
    }

    private void initialize() {
        //ToolBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));

        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.change_password));

        txtError = (TextView) findViewById(R.id.txtError);

        oldpassword = (EditText) findViewById(R.id.oldpassword);
        newpassword = (EditText) findViewById(R.id.newpassword);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                if (getValidInput()) {
                    txtError.setText(null);
                    apiCall(getString(R.string.please_wait), AppUrls.CHANGE_PASSWORD, changePassword(), true, Constant.WHITCH_1);
                }
                break;
            case R.id.txtRASecond:
                finish();
                break;

        }
    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        //Context context, String callType, String url, RequestParams params, String title, final NetworkManager.onCallback callback, final boolean isShowLoader, final int which) {
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        if (Constant.WHITCH_1 == 1) {
            CommanJson json = JsonDeserializer.deserializeJson(response, CommanJson.class);
            if (json.status == 1) {
                dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), json.message, getString(R.string.retry), getString(R.string.cancel), this);

            } else {
                dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), json.message, getString(R.string.retry), "", this);

            }

        }

    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }

    private RequestParams changePassword() {
        RequestParams params = new RequestParams();
        params.put("user_id", SharedPref.getSP(Constant.ID));
        params.put("old_password", Utilis_.getEditText(oldpassword));
        params.put("new_password", Utilis_.getEditText(newpassword));
        return params;
    }

    public boolean getValidInput() {
        if (Utilis_.getEditTextBool(oldpassword) && Utilis_.getEditTextBool(newpassword) && Utilis_.getEditTextBool(confirmpassword)) {
            if (Utilis_.getEditText(newpassword).equals(Utilis_.getEditText(confirmpassword))) {
                return true;
            } else {
                txtError.setText(getString(R.string.new_password_and_confirm));
                return false;
            }

        } else {
            txtError.setText(getString(R.string.all_fields_are_required));
            return false;
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
}
