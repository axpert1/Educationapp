package com.bibliophile.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.bibliophile.AppInitialization;

/**
 * Created by ANDROID on 9/4/2017.
 */

public class EditTextRegularBold extends EditText {
    public EditTextRegularBold(Context context) {
        super(context);
        initialization();
    }

    public EditTextRegularBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialization();
    }

    public EditTextRegularBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialization();
    }

    public EditTextRegularBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialization();
    }
    public void initialization() {
        setTypeface(AppInitialization.getFontSemiBold());
    }
}
