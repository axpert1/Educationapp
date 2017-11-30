package com.bibliophile.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.CommanJson;
import com.bibliophile.model.TestSeriesModel;
import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.RequestParams;

import java.util.List;

/**
 * Created by ANDROID on 10/10/2017.
 */

public class TestSeriesAdapter extends RecyclerView.Adapter<TestSeriesAdapter.MyViewHolder> implements NetworkManager.onCallback {
    private List<TestSeriesModel.SeriesList> seriesLists;
    private Context mContext;
    private int poss;
    private Button active, deactive;
    private DilogCustom dilogCustom=new DilogCustom();
    private View.OnClickListener retryClick;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProfile;
        private TextView txtName, txtDataTime, txtSubject, txtCourse, txtDescription;
        private Button btnActive, btnDeactive;
        public MyViewHolder(View view) {
            super(view);
            imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtSubject = (TextView) view.findViewById(R.id.txtSubject);
            txtDataTime = (TextView) view.findViewById(R.id.txtDateTime);
            txtDescription = (TextView) view.findViewById(R.id.txtDescription);
            txtCourse = (TextView) view.findViewById(R.id.txtCourse);
            btnActive = (Button) view.findViewById(R.id.btnActive);
            btnDeactive = (Button) view.findViewById(R.id.btnDeactive);
        }
    }

    public TestSeriesAdapter(Context context, List<TestSeriesModel.SeriesList> seriesLists) {
        this.seriesLists = seriesLists;
        this.mContext = context;
    }

    @Override
    public TestSeriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_test_series, parent, false);
        return new TestSeriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TestSeriesAdapter.MyViewHolder holder, final int position) {
        TestSeriesModel.SeriesList dataSeries = seriesLists.get(position);
        Utilis_.imageFromUrl(mContext, holder.imgProfile, "http://images.statusfacebook.com/profile_pictures/profile-pictures/cool-funny-romantic-stylish-profile-pictures-for-whatsapp-facebook-245.jpg");
        holder.txtName.setText(dataSeries.seriesName);
        holder.txtDataTime.setText(dataSeries.createdAt);
        holder.txtSubject.setText(dataSeries.subjectName);
        holder.txtDescription.setText(Html.fromHtml(dataSeries.description));
        holder.txtCourse.setText(dataSeries.courseName);
        active = holder.btnActive;
        deactive = holder.btnDeactive;
        buttonShow(dataSeries.activated);
        holder.btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poss = position;
                active = holder.btnActive;
                deactive = holder.btnDeactive;
                dilogCustom.retryAlertDialog(mContext, mContext.getResources().getString(R.string.app_name), mContext.getResources().getString(R.string.msg_active), mContext.getResources().getString(R.string.cancel), mContext.getResources().getString(R.string.active), retryClick);
              //  apiCall(mContext.getString(R.string.please_wait), AppUrls.CHANGE_SERIES_STATUS, acceptParams(), true, Constant.WHITCH_1);
            }
        });
        holder.btnDeactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poss = position;
                active = holder.btnActive;
                deactive = holder.btnDeactive;
                dilogCustom.retryAlertDialog(mContext, mContext.getResources().getString(R.string.app_name), mContext.getResources().getString(R.string.msg_deactive), mContext.getResources().getString(R.string.cancel), mContext.getResources().getString(R.string.deactive), retryClick);
            }
        });
        retryClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilogCustom.dismissRetryAlert();
                apiCall(mContext.getString(R.string.please_wait), AppUrls.CHANGE_SERIES_STATUS, acceptParams(), true, Constant.WHITCH_1);
            }
        };
    }

    private void buttonShow(String activated) {
        if (activated.equals("inactive")) {
            active.setVisibility(View.VISIBLE);
            deactive.setVisibility(View.GONE);
        } else {
            active.setVisibility(View.GONE);
            deactive.setVisibility(View.VISIBLE);
        }
    }

    private RequestParams acceptParams() {
        RequestParams params = new RequestParams();
        params.put("series_type", Utilis_.getintentString(Constant.INTENT_NAME, ((Activity) mContext).getIntent()));
        params.put("center_id", SharedPref.getSP(Constant.CENTER_ID));
        params.put("series_id", seriesLists.get(poss).seriesId);
        return params;
    }


    @Override
    public int getItemCount() {
        return seriesLists.size();
    }

    private void apiCall(String apiTitle, String apiUrl, RequestParams requestParams, boolean progressBar, int apiWhitch) {
        NetworkManager networkManager = new NetworkManager();
        networkManager.callAPI(mContext, com.bibliophile.loopjServcice.Constant.VAL_POST, apiUrl, requestParams, apiTitle, this, progressBar, apiWhitch);
    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        CommanJson json = JsonDeserializer.deserializeJson(response, CommanJson.class);
        if (json.status == 1) {
            if (seriesLists.get(poss).activated.toLowerCase().equals("active".toLowerCase())) {
                seriesLists.get(poss).activated = "inactive";
            } else {
                seriesLists.get(poss).activated = "active";
            }
            notifyDataSetChanged();
        } else {
            Utilis_.showCenterToast(mContext, json.message);
        }
    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }
}
