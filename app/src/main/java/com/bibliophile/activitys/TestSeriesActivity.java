package com.bibliophile.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.adapters.TestSeriesAdapter;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.TestSeriesModel;
import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.RequestParams;

/**
 * Created by ANDROID on 10/9/2017.
 */

public class TestSeriesActivity extends AppCompatActivity implements NetworkManager.onCallback {
    private Context mContext;
    private DilogCustom dilogCustom;
    private Toolbar toolbar;
    private TextView activityTitle;
    private RecyclerView recTestSeries;
    private LinearLayout llTestSeriesNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_series);
        mContext = TestSeriesActivity.this;
        dilogCustom = new DilogCustom();

        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.test_series));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));

        llTestSeriesNotFound = (LinearLayout) findViewById(R.id.llTestSeriesNotFound);

        recTestSeries = (RecyclerView) findViewById(R.id.recTextSeries);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recTestSeries.setLayoutManager(mLayoutManager);
        recTestSeries.setItemAnimator(new DefaultItemAnimator());
        recTestSeries.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


//        recTestSeries.addOnItemTouchListener(new RecyclerTouchListener(this, recTestSeries, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
        apiCall(getString(R.string.please_wait), AppUrls.GET_TEST_SERIES, getTextSeriesParams(), true, Constant.WHITCH_1);

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

    private RequestParams getTextSeriesParams() {
        RequestParams params = new RequestParams();
        //params.put("type", "mock");
        params.put("type", Utilis_.getintentString(Constant.INTENT_NAME, getIntent()));
        params.put("center_id", SharedPref.getSP(Constant.CENTER_ID));
        return params;
    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        //Context context, String callType, String url, RequestParams params, String title, final NetworkManager.onCallback callback, final boolean isShowLoader, final int which) {
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
    }

    private void recycleVisible() {
        recTestSeries.setVisibility(View.VISIBLE);
        llTestSeriesNotFound.setVisibility(View.GONE);
    }

    private void recycleGone() {
        recTestSeries.setVisibility(View.GONE);
        llTestSeriesNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        TestSeriesModel seriesModel = JsonDeserializer.deserializeJson(response, TestSeriesModel.class);
        if (seriesModel.status == 1) {
            if (seriesModel.seriesList != null && seriesModel.seriesList.size() > 0) {
                TestSeriesAdapter seriesAdapter = new TestSeriesAdapter(this, seriesModel.seriesList);
                seriesAdapter.notifyDataSetChanged();
                recTestSeries.setAdapter(seriesAdapter);
                recycleVisible();
            }
        } else {
            recycleGone();
        }
    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }


}
