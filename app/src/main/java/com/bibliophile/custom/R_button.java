package com.bibliophile.custom;

/**
 * Created by ANDROID on 10/16/2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.bibliophile.AppInitialization;


/**
 * Created by anil xpert 9887230800.
 */

public class R_button extends RadioButton {
    public R_button(Context context) {
        super(context);
        init();
    }

    public R_button(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public R_button(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public R_button(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        this.setTypeface(AppInitialization.getFontRegular());
    }
}

