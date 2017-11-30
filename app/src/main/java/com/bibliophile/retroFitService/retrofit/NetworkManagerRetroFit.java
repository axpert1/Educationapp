package com.bibliophile.retroFitService.retrofit;

import android.content.Context;
import android.util.Log;

import com.bibliophile.datas.AppUrls;
import com.bibliophile.utilitys.Utilis_;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wingstud on 15-04-2017.
 */
public class NetworkManagerRetroFit {
    private Retrofit retrofit;

    public interface onCallback {
        public void onResponce(boolean success, int which, String response);

    }

    public RequestInterface getRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl(AppUrls.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(RequestInterface.class);
    }

    public void callAPI(Call<JsonObject> call, Context context, final onCallback callback, final boolean progressBar, final int whichApi, String titleProgress) {
        if (progressBar) {
            Utilis_.progressDialog(context, titleProgress);
        }

//        retrofit = new Retrofit.Builder().baseUrl(AppUrls.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//        RequestInterface request = retrofit.create(RequestInterface.class);
        //Call<JsonObject> call = request.getcartCountDataA("95");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                Utilis_.dismissProgressDialog();
                try {
                    callback.onResponce(true, whichApi, object.toString());
                    Log.d("Responce", object.toString());
                } catch (NullPointerException e) {
                    callback.onResponce(false, whichApi, e.getMessage());
                    Log.d("Responce", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.d("Responce", t.getMessage());
                callback.onResponce(false, whichApi, t.getMessage());
                Utilis_.dismissProgressDialog();
            }
        });
    }
}
