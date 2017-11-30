package com.bibliophile.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bibliophile.AppInitialization;

/**
 * Created by ANDROID on 9/4/2017.
 */

public class TextViewRegular extends TextView {
    public TextViewRegular(Context context) {
        super(context);
        initialization();
    }

    public TextViewRegular(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialization();

    }

    public TextViewRegular(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialization();

    }

    public TextViewRegular(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialization();

    }
    public void initialization() {
        setTypeface(AppInitialization.getFontRegular());
    }
}
