package com.bibliophile.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bibliophile.R;
import com.bibliophile.datas.AppUrls;

/**
 * Created by ANDROID on 9/18/2017.
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    /**
     * 0 = terms and condition
     * 1 = privacy policy
     */

    private int flag;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        mContext = WebViewActivity.this;
        flag = getIntent().getIntExtra("flag", 0);
        initialize();
    }

    private void initialize() {
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        if (flag == 0) {
           // webView.loadUrl(AppUrls.TERMS_CONDITION_URL);
            webView.loadUrl("https://www.google.co.in/search?q=anilxpert&oq=anilxpert");
        } else if (flag == 1) {
         //   webView.loadUrl(AppUrls.PRIVACY_POLICY_URL);
            webView.loadUrl("http://accept/");
        }
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
