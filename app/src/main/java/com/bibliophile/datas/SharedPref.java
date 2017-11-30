package com.bibliophile.datas;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by wingstud on 24-03-2017.
 */
public class SharedPref {
    private static SharedPreferences mPref;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        mPref = context.getSharedPreferences(Constant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = mPref.edit();
    }

    public static String getSP(String Key) {
        return mPref.getString(Key, null);
    }
    public static int getintSP(String Key) {
        return mPref.getInt(Key, 0);
    }
    public static boolean getboolSP(String Key) {
        return mPref.getBoolean(Key, false);
    }
    public static void putboolSP(String Key, boolean value) {
        editor.putBoolean(Key, value);
        editor.commit();
    }
    public static void putSP(String Key, String value) {
        editor.putString(Key, value);
        editor.commit();
    }
    public static void putIntSP(String Key, int value) {
        if (value>=0){
            editor.putInt(Key, value);
            editor.commit();
        }

    }
    public static void clearSp() {
        editor.clear();
        editor.commit();
    }
    public static void removeSP(String key) {
        editor.remove(key);
        editor.commit();
    }
    public static int getCartCount() {
        int cartCount = mPref.getInt(Constant.FLD_CART_COUNT, 0);
        return cartCount;
    }
}
