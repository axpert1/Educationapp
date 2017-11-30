package com.bibliophile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.activitys.StudentListActivity;
import com.bibliophile.activitys.TestSeriesActivity;
import com.bibliophile.custom.slider.SliderLayout;
import com.bibliophile.custom.slider.p.Animations.DescriptionAnimation;
import com.bibliophile.custom.slider.p.Indicators.PagerIndicator;
import com.bibliophile.custom.slider.p.SliderTypes.BaseSliderView;
import com.bibliophile.custom.slider.p.SliderTypes.DefaultSliderView;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.BottomModel;
import com.bibliophile.model.SliderJson;
import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANDROID on 9/28/2017.
 */

public class F_CenterDashBord extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, NetworkManager.onCallback, DilogCustom.onCallbackBottom {
    private View view;
    private Context mContext;
    private TextView activityTitle;
    private View bgView;
    private SliderLayout mDemoSlider;
    private List<String> lstSlider;
    private LinearLayout lltestResult, llStudent;
    private DilogCustom dilogCustom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.f_center_dashbord, container, false);
            mContext = getContext();
            initialize_student();
            apiCall(getString(R.string.please_wait), AppUrls.GET_BANNER, getBannerParams(), true, Constant.WHITCH_1);
            dilogCustom = new DilogCustom();
        }
        return view;
    }

    private void initialize_student() {
        activityTitle = (TextView) getActivity().findViewById(R.id.activityTitle);
        activityTitle.setText("Home");
        bgView = (View) view.findViewById(R.id.bgView);
        //  bgView.setBackground(new PercentDrawable(60, "#00897b"));
        lltestResult = (LinearLayout) view.findViewById(R.id.lltestResult);
        llStudent = (LinearLayout) view.findViewById(R.id.llStudent);

        lltestResult.setOnClickListener(this);
        llStudent.setOnClickListener(this);


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

                List<BottomModel> mPackage = new ArrayList<>();
                mPackage.add(new BottomModel("Mock Text", "0", ""));
                mPackage.add(new BottomModel("Main Text", "1", ""));

                dilogCustom.openBottomSheet(mContext, getString(R.string.test_series), mPackage, this);
                break;
            case R.id.llStudent:
                Utilis_.startActivity(getContext(), StudentListActivity.class);
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

    @Override
    public void onSelectPackage(String name, String id, String price) {
        if (id.equals("0")) {//moke
            Utilis_.startActivityPutValue( mContext, TestSeriesActivity.class, "mock");
        } else if (id.equals("1")) {//main
            Utilis_.startActivityPutValue( mContext, TestSeriesActivity.class, "main");
        }

    }
}
