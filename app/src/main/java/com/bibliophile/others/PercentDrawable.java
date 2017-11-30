package com.bibliophile.others;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by ANDROID on 9/13/2017.
 */

public class PercentDrawable extends Drawable {

    private final int percent;
    private final Paint paint;

    public PercentDrawable(int percent,String colors) {
        super();
        this.percent = percent;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.parseColor(colors));
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, canvas.getWidth(), percent * canvas.getHeight() / 100, paint);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}