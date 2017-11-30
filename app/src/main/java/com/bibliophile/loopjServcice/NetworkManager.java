package com.bibliophile.loopjServcice;

import android.content.Context;
import android.util.Log;

import com.bibliophile.utilitys.Utilis_;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wingstud on 14-04-2017.
 */
public class NetworkManager {
    private AsyncHttpClient client;
    private Context mContext;

    public interface onCallback {
        public void onSuccess(boolean success, String response, int which);
        public void onFailure(boolean success, String response, int which);
    }

    public void callAPI(Context context, String callType, String url, RequestParams params, String title, final onCallback callback, final boolean isShowLoader, final int which) {
        mContext = context;
        if (isShowLoader) {
            Utilis_.progressDialog(mContext, title);
        }
        client = new AsyncHttpClient();
        client.setTimeout(120000);
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        if (callType.equals(Constant.VAL_POST)) {
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    String res = String.valueOf(new String(response));
                    Log.d("Responce", res);
                    callback.onSuccess(true, res, which);
                    if (isShowLoader) {
                        Utilis_.dismissProgressDialog();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    callback.onFailure(false, "", which);
                    if (isShowLoader) {
                        Utilis_.dismissProgressDialog();
                    }
                }


            });
        } else if (callType.equals(Constant.VAL_GET)) {
            client.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    String res = String.valueOf(new String(response));
                    callback.onSuccess(true, res, which);
                    if (isShowLoader) {
                        Utilis_.dismissProgressDialog();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    callback.onFailure(false, "", which);
                    if (isShowLoader) {
                        Utilis_.dismissProgressDialog();
                    }
                }
            });
        }
    }

    public void cancelRunningApiCalling(Context context) {
        if (client != null)
            client.cancelRequests(context, true);
    }
}
