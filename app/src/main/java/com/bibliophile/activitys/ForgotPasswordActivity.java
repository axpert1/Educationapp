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
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.retroFitService.retrofit.NetworkManagerRetroFit;
import com.bibliophile.retroFitService.retrofit.RequestInterface;
import com.bibliophile.utilitys.Utilis_;
import com.google.gson.JsonObject;


import retrofit2.Call;

/**
 * Created by ANDROID on 9/26/2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, NetworkManagerRetroFit.onCallback {
    private Context mContext;
    private EditText edtEmail;
    private Button btnResetPassword;
    private Toolbar toolbar;
    private TextView activityTitle;


    //API
    NetworkManagerRetroFit mNetwork;
    RequestInterface request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        mContext = ForgotPasswordActivity.this;
        initialize();
        apiInitialize();

    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));


        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnResetPassword:
                apiCall(request.forgotPassword(Utilis_.getEditText(edtEmail)), true, 1, getString(R.string.please_wait));

                break;
        }
    }

    private void apiInitialize() {
        mNetwork = new NetworkManagerRetroFit();
        request = mNetwork.getRetrofit();
    }

    private void apiCall(Call<JsonObject> call, boolean progressBar, int whichApi, String titleProgress) {
        mNetwork.callAPI(call, mContext, this, progressBar, whichApi, titleProgress);
    }

    @Override
    public void onResponce(boolean success, int which, String response) {

            Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();


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
