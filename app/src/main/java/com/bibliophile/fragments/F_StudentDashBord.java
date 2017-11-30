package com.bibliophile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.activitys.GroupActivity;
import com.bibliophile.activitys.GroupViewActivity;
import com.bibliophile.activitys.TestActivity;
import com.bibliophile.custom.slider.SliderLayout;
import com.bibliophile.custom.slider.p.Animations.DescriptionAnimation;
import com.bibliophile.custom.slider.p.Indicators.PagerIndicator;
import com.bibliophile.custom.slider.p.SliderTypes.BaseSliderView;
import com.bibliophile.custom.slider.p.SliderTypes.DefaultSliderView;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.CommanJson;
import com.bibliophile.model.SliderJson;
import com.bibliophile.retroFitService.retrofit.NetworkManagerRetroFit;
import com.bibliophile.retroFitService.retrofit.RequestInterface;
import com.bibliophile.utilitys.CmdRequest;
import com.bibliophile.utilitys.Utilis_;
import com.google.gson.JsonObject;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Vishal.
 */
public class F_StudentDashBord extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, NetworkManager.onCallback {
    private Context mContext;
    private View view;
    private TextView activityTitle;
    private View bgView;
    private SliderLayout mDemoSlider;
    private List<String> lstSlider;
    private LinearLayout lltestResult, lltakeTest, llgroup, llmyPost;

    //API
    NetworkManagerRetroFit mNetwork;
    RequestInterface request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.f_student_dashbord, container, false);
            mContext = getContext();
            initialize_student();
            apiCall(getString(R.string.please_wait), AppUrls.GET_BANNER, getBannerParams(), true, Constant.WHITCH_1);
        }
        return view;
    }


    private void initialize_student() {
        activityTitle = (TextView) getActivity().findViewById(R.id.activityTitle);
        activityTitle.setText("Home");
        bgView = (View) view.findViewById(R.id.bgView);
        //  bgView.setBackground(new PercentDrawable(60, "#00897b"));

        lltestResult = (LinearLayout) view.findViewById(R.id.lltestResult);
        lltakeTest = (LinearLayout) view.findViewById(R.id.lltakeTest);
        llgroup = (LinearLayout) view.findViewById(R.id.llgroup);
        llmyPost = (LinearLayout) view.findViewById(R.id.llmyPost);

        lltestResult.setOnClickListener(this);
        lltakeTest.setOnClickListener(this);
        llgroup.setOnClickListener(this);
        llmyPost.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void slidingImage(List<String> datums) {
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        mDemoSlider.removeAllSliders();
        for (int i = 0; i < datums.size(); i++) {
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(null)
                    .image(datums.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.setOnSliderClickListener(this);
            textSliderView.getBundle();
            //     .putString("extra", Utilss.jsonSlider(datums, i));
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator2));
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setDuration(5000);
        mDemoSlider.setOnClickListener(this);
        ///////
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lltestResult:
                break;
            case R.id.lltakeTest:
                Utilis_.startActivity(getContext(), TestActivity.class);
                break;
            case R.id.llgroup:
                Utilis_.startActivity(getContext(), GroupActivity.class);
                break;
            case R.id.llmyPost:
                Utilis_.startActivity(getContext(), GroupViewActivity.class);
                break;
        }
    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        //Context context, String callType, String url, RequestParams params, String title, final NetworkManager.onCallback callback, final boolean isShowLoader, final int which) {
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
    }

    private RequestParams getBannerParams() {
        RequestParams params = new RequestParams();
        params.put("center_id", SharedPref.getSP(Constant.CENTER_ID));
        return params;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        SliderJson sliderJson = JsonDeserializer.deserializeJson(response, SliderJson.class);
        if (sliderJson.status == 1) {
            if (sliderJson.sliderList != null && sliderJson.sliderList.size() > 0) {
                lstSlider = new ArrayList<>();
                for (int i = 0; i < sliderJson.sliderList.size(); i++) {
                    lstSlider.add(sliderJson.path + sliderJson.sliderList.get(i).sliderImage);
                }
                slidingImage(lstSlider);
            }
        }
    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }
}
