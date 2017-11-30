package com.bibliophile.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.adapters.StudentListAdapter;
import com.bibliophile.adapters.TestSeriesAdapter;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.StudentListModel;
import com.bibliophile.others.RecyclerTouchListener;
import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANDROID on 10/10/2017.
 */

public class StudentListActivity extends AppCompatActivity implements NetworkManager.onCallback, View.OnClickListener {
    private Context mContext;
    private DilogCustom dilogCustom;
    private Toolbar toolbar;
    private TextView activityTitle;
    private RecyclerView recTestSeries;
    private List<StudentListModel.StudentList> studentLists = new ArrayList<>();
    private FloatingActionButton fadAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);
        mContext = StudentListActivity.this;
        dilogCustom = new DilogCustom();
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.student_));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));

        fadAddStudent = (FloatingActionButton) findViewById(R.id.fadAddStudent);
        recTestSeries = (RecyclerView) findViewById(R.id.recStudentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recTestSeries.setLayoutManager(mLayoutManager);
        recTestSeries.setItemAnimator(new DefaultItemAnimator());
        recTestSeries.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recTestSeries.addOnItemTouchListener(new RecyclerTouchListener(this, recTestSeries, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Utilis_.startActivityPutValue(mContext, StudentProfileActivity.class,studentLists.get(position).studentId);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        // TODO: 10/16/2017 API
        apiCall(getString(R.string.please_wait), AppUrls.GET_STUDENT_LIST, getParams(), true, Constant.WHITCH_1);
        fadAddStudent.setOnClickListener(this);
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

    private RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.put("center_id", SharedPref.getSP(Constant.CENTER_ID));
        return params;
    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        StudentListModel studentList = JsonDeserializer.deserializeJson(response, StudentListModel.class);
        fadAddStudent.setVisibility(View.VISIBLE);
        if (studentList.status == 1) {
            if (studentList.studentList != null && studentList.studentList.size() > 0) {
                studentLists = studentList.studentList;
                StudentListAdapter studentAdapter = new StudentListAdapter(this, studentLists);
                studentAdapter.notifyDataSetChanged();
                recTestSeries.setAdapter(studentAdapter);
            }

        }

    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fadAddStudent:
                Utilis_.startActivity(mContext, AddStudentActivity.class);
                break;
        }
    }
}
