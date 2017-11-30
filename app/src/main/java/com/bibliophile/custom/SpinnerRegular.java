package com.bibliophile.custom;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by ANDROID on 9/4/2017.
 */

public class SpinnerRegular extends Spinner {
    public SpinnerRegular(Context context) {
        super(context);
    }

    public SpinnerRegular(Context context, int mode) {
        super(context, mode);
    }

    public SpinnerRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpinnerRegular(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public SpinnerRegular(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
    }

    public SpinnerRegular(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
    }
}
