package com.bibliophile.datas;

import android.text.TextUtils;

/**
 * Created by wingstud on 01-06-2017.
 */
public class ValidField {
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public final static boolean isValidEmailOrPhone(CharSequence target) {
        if (target.length()!=10) {
            return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }
    public final static boolean isValidPass(CharSequence target) {
        if (target.toString().length() == 6) {
            return true;
        } else {
            return false;
        }

    }
    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target.length()!=10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }
}
