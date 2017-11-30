package com.bibliophile.datas;

import android.app.Activity;
import android.view.View;

/**
 * Created by ANDROID on 9/4/2017.
 */

public class Constant {

    public static final String NOTIFICATION_SHOW = "notification_show";     //count key
    public static final String IS_LOGIN = "is_login";     //count key
    public static final String STATE = "state";     // key
    public static final String CITY = "city";     // key


    // TODO: For Shared Preferences
    public static final String SHARED_PREF = "SHARED_PREF";         //key shared preference
    public static final String FLD_CART_COUNT = "cart_count";     //count key

    public static final String ID = "id";     //count key
    public static final String CENTER_ID = "center_id";     //count key
    public static final String CENTER_NAME = "center_name";     //count key
    public static final String USER_EMAIL = "user_email";     // key
    public static final String USER_NAME = "user_name";     // key
    public static final String USER_MOBILE = "user_mobile";     // key


    public static final String LOGIN_STUDENT = "login_student";     // key
    public static final String LOGIN_CENTER = "login_center";     // key
    public static final String LOGIN_TYPE = "login_type";     // key



    // TODO: 9/21/2017 for Using Intent

    public static final String INTENT_NAME = "intent_name";
    public static final String INTENT_ID = "intent_id";
    public static final String INTENT_TOOLBAR = "intent_toolbar";


    // TODO: For using Utlis

    public static final int HEIGHT = 1;         //key shared preference
    public static final int WIDTH = 2;         //key shared preference


    // TODO: For whitchAPi
    public static final int WHITCH_1 = 1;         //key
    public static final int WHITCH_2 = 2;         //key
    public static final int WHITCH_3 = 3;         //key
    public static final int WHITCH_4 = 4;         //key
    public static final int WHITCH_5 = 5;         //key
    public static final int WHITCH_6 = 6;         //key


    public static void test(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


}
