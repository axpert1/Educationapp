package com.bibliophile.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.adapters.State_CityAdapter;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.AppUrls;
import com.bibliophile.loopjServcice.JsonDeserializer;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.CityJson;
import com.bibliophile.model.GettersSetters;
import com.bibliophile.model.StateJson;
import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANDROID on 9/21/2017.
 */

public class StateAndCityActivity extends AppCompatActivity implements NetworkManager.onCallback {
    List<GettersSetters> setterses = new ArrayList<>();
    List<GettersSetters> data = new ArrayList<>();
    ProgressBar progressBar;
    TextView progresstext;
    LinearLayout progresslayout;
    String intentState_city = "", intentState_city_id = "";
    EditText search_edittext;
    ImageView cancelImage;
    CardView cardview;
    
    private  RecyclerView stateCityRecyclerView;
    private State_CityAdapter state_cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_and_city);
        // TODO: 12-06-2017
        intentState_city = Utilis_.getintentString(Constant.INTENT_NAME, getIntent());
        intentState_city_id = Utilis_.getintentString(Constant.INTENT_ID, getIntent());

        cardview = (CardView) findViewById(R.id.cardview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        stateCityRecyclerView = (RecyclerView) findViewById(R.id.recycler_member);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        stateCityRecyclerView.setLayoutManager(mLayoutManager);
        stateCityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        stateCityRecyclerView.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        stateCityRecyclerView.setVisibility(View.GONE);
        cardview.setVisibility(View.GONE);
        if (intentState_city.toLowerCase().equals(Constant.STATE)) {
            RequestParams params = new RequestParams();
            params.put("country_id", "101");
            requestApi(params, AppUrls.GET_STATE, 1);
        } else if (intentState_city.toLowerCase().equals(Constant.CITY)) {
            RequestParams params = new RequestParams();
            params.put("state_id", intentState_city_id);
            requestApi(params, AppUrls.GET_CITY, 2);

        }
        search_edittext = (EditText) findViewById(R.id.search_edittext);
        cancelImage = (ImageView) findViewById(R.id.cancelImage);
        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

                if (!cs.toString().equals("")) {
                    // List<Title> filteredTitles = new ArrayList<Title>();
                    data.clear();
                    for (int i = 0; i < setterses.size(); i++) {

                        if (setterses.get(i).getName().toString().trim().toLowerCase().contains(cs.toString().toLowerCase())) {

                            // String tableno = subList.get(i).getTableno().toString();
                            //  (String tableno, String ordernum, String mordernum, String item, String person, String hold, String d_id)
                            data.add(new GettersSetters(setterses.get(i).getId(), setterses.get(i).getName()));
                        }
                    }

                    state_cityAdapter = new State_CityAdapter(StateAndCityActivity.this, data);
                    state_cityAdapter.notifyDataSetChanged();
                    stateCityRecyclerView.setAdapter(state_cityAdapter);
                } else {
                   state_cityAdapter = new State_CityAdapter(StateAndCityActivity.this, setterses);
                    state_cityAdapter.notifyDataSetChanged();
                    stateCityRecyclerView.setAdapter(state_cityAdapter);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void requestApi(RequestParams params, String url, int which) {
        NetworkManager networkManager = new NetworkManager();
        networkManager.callAPI(StateAndCityActivity.this, com.bibliophile.loopjServcice.Constant.VAL_POST, url, params, "Title for Dilog", this, true, which);
    }

    @Override
    public void onSuccess(boolean success, String response, int which) {
        if (which == 1) {
            StateJson stateJson = JsonDeserializer.deserializeJson(response, StateJson.class);
            if (stateJson.status == 1) {

                for (int i = 0; i < stateJson.stateList.size(); i++) {

                    setterses.add(new GettersSetters(stateJson.stateList.get(i).id, stateJson.stateList.get(i).name));
                }
                stateCityRecyclerView.setVisibility(View.VISIBLE);
                cardview.setVisibility(View.VISIBLE);
                state_cityAdapter = new State_CityAdapter(StateAndCityActivity.this, setterses);
                state_cityAdapter.notifyDataSetChanged();
                stateCityRecyclerView.setAdapter(state_cityAdapter);
            } else {

            }

        } else if (which == 2) {
            CityJson citylistJson = JsonDeserializer.deserializeJson(response, CityJson.class);
            if (citylistJson.status == 1) {
                for (int i = 0; i < citylistJson.cityList.size(); i++) {
                    setterses.add(new GettersSetters(citylistJson.cityList.get(i).id, citylistJson.cityList.get(i).name));
                }
                stateCityRecyclerView.setVisibility(View.VISIBLE);
                cardview.setVisibility(View.VISIBLE);

                state_cityAdapter = new State_CityAdapter(StateAndCityActivity.this, setterses);
                state_cityAdapter.notifyDataSetChanged();
                stateCityRecyclerView.setAdapter(state_cityAdapter);
            } else {

            }

        }

    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }

    public void returnData(String id, String name) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result_id", id);
        returnIntent.putExtra("result_name", name);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
