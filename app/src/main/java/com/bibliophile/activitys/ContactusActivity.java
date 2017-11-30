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

public class ContactusActivity extends AppCompatActivity implements View.OnClickListener, NetworkManager.onCallback {
    private Toolbar toolbar;
    private Context mContext;
    private TextView activityTitle, txtEmail, txtError;
    private EditText edtSubject, edtMessage;
    private Button btnSubmit;
    private DilogCustom dilogCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus);
        mContext = ContactusActivity.this;
        dilogCustom = new DilogCustom();
        initialize();
    }

    private void initialize() {
        // title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.contact_us));
        // finds Ids
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtError = (TextView) findViewById(R.id.txtError);
        edtSubject = (EditText) findViewById(R.id.edtSubject);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(this);

        txtEmail.setText(SharedPref.getSP(Constant.USER_EMAIL));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (getValidInput()) {
                    txtError.setText(null);
                    apiCall(getString(R.string.please_wait), AppUrls.CONTACT_US, contactusParams(), true, Constant.WHITCH_1);

                }
                break;
            case R.id.txtRASecond:
                finish();
                break;
        }
    }

    private RequestParams contactusParams() {
        RequestParams params = new RequestParams();
        params.put("email", SharedPref.getSP(Constant.USER_EMAIL));
        params.put("mobile", SharedPref.getSP(Constant.USER_MOBILE));
        params.put("name", SharedPref.getSP(Constant.USER_NAME));
        params.put("subject", Utilis_.getEditText(edtSubject));
        params.put("message", Utilis_.getEditText(edtMessage));
        return params;
    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        //Context context, String callType, String url, RequestParams params, String title, final NetworkManager.onCallback callback, final boolean isShowLoader, final int which) {
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        CommanJson json = JsonDeserializer.deserializeJson(response, CommanJson.class);
        if (json.status == 1) {
            dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), json.message, getString(R.string.retry), getString(R.string.cancel), this);

        } else {
            dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), json.message, getString(R.string.retry), "", this);
        }
    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }

    public boolean getValidInput() {
        if (Utilis_.getEditTextBool(edtSubject) && Utilis_.getEditTextBool(edtMessage)) {
            return true;
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
