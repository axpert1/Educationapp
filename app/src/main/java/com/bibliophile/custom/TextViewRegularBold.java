package com.bibliophile.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bibliophile.AppInitialization;

/**
 * Created by ANDROID on 9/4/2017.
 */

public class TextViewRegularBold extends TextView {
    public TextViewRegularBold(Context context) {
        super(context);
    }

    public TextViewRegularBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialization();

    }

    public TextViewRegularBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialization();

    }

    public TextViewRegularBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialization();

    }
    public void initialization() {
        setTypeface(AppInitialization.getFontSemiBold());
    }
}
