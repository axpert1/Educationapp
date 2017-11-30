package com.bibliophile;

import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;
import com.bibliophile.datas.SharedPref;


public class AppInitialization extends MultiDexApplication {
    private static Typeface fontRegular;
    private static Typeface josefinsans_semibold;

    @Override
    public void onCreate() {

        SharedPref.init(this);
        fontRegular = Typeface.createFromAsset(getAssets(), "lato-regular.ttf");
        josefinsans_semibold = Typeface.createFromAsset(getAssets(), "lato-bold.ttf");
        super.onCreate();
    }

    public static Typeface getFontRegular() {
        return fontRegular;
    }

    public static Typeface getFontSemiBold() {
        return josefinsans_semibold;
    }
}
