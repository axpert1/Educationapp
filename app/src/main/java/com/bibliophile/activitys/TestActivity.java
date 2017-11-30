package com.bibliophile.activitys;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.adapters.QuestionAdapter;
import com.bibliophile.custom.textdrawable.TextDrawable;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.QuestionModel;
import com.bibliophile.model.Testing;
import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, NetworkManager.onCallback {
    private List<Testing> question = new ArrayList<>();

    private RecyclerView queRecyclerView;
    private QuestionAdapter mQueAdapter;
    private Button btnNext, btnPrevious;
    private RadioGroup rgAnswer;
    private RadioButton rSkip;
    int position = 0;
    //    private CheckBox rbA, rbB, rbC, rbD;
    private TextView txtTimeCount;


    private Context mContext;
    private Toolbar toolbar;
    private TextView activityTitle;

    //    private RecyclerView recyclerViewINDEX;
    //    private OffersAdapters mAdapterINDEX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext = TestActivity.this;
        // mAdapterINDEX = new OffersAdapters(this, movieList);

        // init adapter
        // recyclerViewINDEX = (RecyclerView) findViewById(R.id.recycler_view);
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        // recyclerViewINDEX.setLayoutManager(mLayoutManager);
        // recyclerViewINDEX.setItemAnimator(new DefaultItemAnimator());
        //recyclerViewINDEX.setAdapter(mAdapterINDEX);
        initialize();

//        mQueAdapter = new QuestionAdapter(this, movieList);
//        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        queRecyclerView.setLayoutManager(mLayoutManager2);
//        queRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        queRecyclerView.setAdapter(mQueAdapter);
//        final LinearSnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(queRecyclerView);
//        queRecyclerView.setOnFlingListener(snapHelper);


    }

    private void initialize() {
//        queRecyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
//        queRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        txtTimeCount = (TextView) findViewById(R.id.txtTimeCount);


        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

        // TODO: 10/16/2017 API
        apiCall(getString(R.string.please_wait), AppUrls.GET_QUESTIONS_BY_MAIN_SERIES, getTextParams(), true, Constant.WHITCH_1);

    }

    public void setData(int p) {
        ImageView imageView = (ImageView) findViewById(R.id.TSTnameImage);
        rgAnswer = (RadioGroup) findViewById(R.id.rgAnswer);
        rSkip=(RadioButton) findViewById(R.id.rSkip);
        //ImageView iv_camera = (ImageView) findViewById(R.id.TSTiv_camera);
//
//        rbA = (CheckBox) findViewById(R.id.rbA);
//        rbB = (CheckBox) findViewById(R.id.rbB);
//        rbC = (CheckBox) findViewById(R.id.rbC);
//        rbD = (CheckBox) findViewById(R.id.rbD);
//
//
//        setChange(rbA);
//        setChange(rbB);
//        setChange(rbC);
//        setChange(rbD);


        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = Utilis_.displayHeightWeight(this, 50, Constant.HEIGHT);
        params.width = Utilis_.displayHeightWeight(this, 100, Constant.WIDTH);
        imageView.setLayoutParams(params);
//        TextDrawable drawable = TextDrawable.builder().beginConfig().textColor(Color.WHITE)
//                .useFont(Typeface.DEFAULT)
//                .fontSize(30) /* size in px */
//                .bold()
//                .toUpperCase().endConfig()
//                //.buildRound(String.valueOf(p + 1)+"/100", Color.GRAY);
//                .buildRound(100+"/100", Color.GRAY);
//        iv_camera.setImageDrawable(drawable);
        txtTimeCount.setText(String.valueOf(p + 1) + " / " + question.size());
        Utilis_.imageFromUrl(this, imageView, question.get(p).getImageUrl());
        if (!question.get(p).getValue().equals(getString(R.string.skip))) {
            Utilis_.setRadioGroup(rgAnswer, question.get(p).getValue());//RB
            rSkip.setVisibility(View.VISIBLE);
        } else {
            rSkip.setVisibility(View.INVISIBLE);
            rgAnswer.clearCheck();
        }

        //setRadioButtonValue(p);
    }

//    public void setRadioButtonValue(int poo) {
//        Log.d("myvalue", question.get(poo).getValue().toUpperCase());
//
//        switch (question.get(poo).getValue().toUpperCase()) {
//            case "A":
//                setCheck(rbA, rbB, rbC, rbD);
//                break;
//            case "B":
//                setCheck(rbB, rbA, rbC, rbD);
//                break;
//            case "C":
//                setCheck(rbC, rbB, rbA, rbD);
//                break;
//            case "D":
//                setCheck(rbD, rbB, rbC, rbA);
//                break;
//            case "SKIP":
//                rbA.setChecked(false);
//                rbB.setChecked(false);
//                rbC.setChecked(false);
//                rbD.setChecked(false);
//                break;
//        }
//
////        } else {
////            rbA.setChecked(false);
////            rbB.setChecked(false);
////            rbC.setChecked(false);
////            rbD.setChecked(false);
////        }
//
//    }

    public void setCheck(CheckBox a, CheckBox b, CheckBox c, CheckBox d) {
        a.setChecked(true);
        b.setChecked(false);
        c.setChecked(false);
        d.setChecked(false);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                // Log.d("Hello",""+queRecyclerView.getCurrentPosition()+1) ;
                // queRecyclerView.setVerticalScrollbarPosition(queRecyclerView.getCurrentPosition()+1);
                Log.d("Possssss", "A " + position);
                question.get(position).setValue(Utilis_.getTextfromRadioGroup2(rgAnswer));

                if (position >= 0 && question.size() - 1 > position) {
                    position = position + 1;
                    // queRecyclerView.smoothScrollToPosition(position);
                    setData(position);
                }
                Log.d("Possssss", "B " + position);
                break;
            case R.id.btnPrevious:
                Log.d("Possssss", "C " + position);
                question.get(position).setValue(Utilis_.getTextfromRadioGroup2(rgAnswer));
                if (position > 0 && question.size() >= position) {
                    position = position - 1;
                    // queRecyclerView.smoothScrollToPosition(position);
                    setData(position);
                }
                Log.d("Possssss", "D " + position);
                break;

        }
    }

//    private void setChange(final CheckBox a) {
//        a.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (a.getText().toString().toUpperCase().equals(question.get(position).getValue())) {
//                    rbA.setChecked(false);
//                    rbB.setChecked(false);
//                    rbC.setChecked(false);
//                    rbD.setChecked(false);
//
//                    Toast.makeText(TestActivity.this, "SKIP", Toast.LENGTH_SHORT).show();
//                    question.get(position).setValue("SKIP");
//                } else {
//                    rbA.setChecked(false);
//                    rbB.setChecked(false);
//                    rbC.setChecked(false);
//                    rbD.setChecked(false);
//                    a.setChecked(true);
//
//
//                    question.get(position).setValue(a.getText().toString());
//                }
//
//
//            }
//        });
//
//    }


    // countdowntimer is an abstract class, so extend it and fill in methods
    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //  txtTimeCount.setText("done!");   //TextView object should be defined in onCreate
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //txtTimeCount.setText("Left: " + millisUntilFinished / 1000);// This will be called every Second.
            activityTitle.setText("Left: " + millisUntilFinished / 1000);// This will be called every Second.

        }
    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        //Context context, String callType, String url, RequestParams params, String title, final NetworkManager.onCallback callback, final boolean isShowLoader, final int which) {
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
    }

    private RequestParams getTextParams() {
        RequestParams params = new RequestParams();

        params.put("series_id", "4");
        return params;
    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        QuestionModel questionModel = JsonDeserializer.deserializeJson(response, QuestionModel.class);
        if (questionModel.status == 1) {
            if (questionModel.listOfQuestions != null && questionModel.listOfQuestions.size() > 0) {
                for (int i = 0; i < questionModel.listOfQuestions.size(); i++) {
                    Testing movie = new Testing("" + (i + 1), questionModel.listOfQuestions.get(i).id, getString(R.string.skip), questionModel.path + "/" + questionModel.listOfQuestions.get(i).questionImage);
                    question.add(movie);
                }
                Log.d("ListData", "" + question);
                MyCount counter = new MyCount(150000, 1000);
                counter.start();
                setData(position);
            }


        }

    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }

}
