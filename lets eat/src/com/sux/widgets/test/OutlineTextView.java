package com.sux.widgets.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Shorty on 5/1/2014.
 */
public class OutlineTextView extends TextView {
    Paint strokePaint, textPaint;

    public OutlineTextView(Context context) {
        super(context);
        init();
    }

    public OutlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OutlineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init(){
        strokePaint = new Paint();
        textPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        strokePaint.setARGB(255, 0, 0, 0);
        strokePaint.setTextAlign(Paint.Align.CENTER);
        strokePaint.setTextSize(getTextSize());
        strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(4);

        //textPaint.setARGB(255, 255, 255, 255);
        //textPaint.setTextAlign(Paint.Align.CENTER);
        //textPaint.setTextSize(getTextSize());
        //textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        if(getText() != null){
            canvas.drawText(getText().toString(), canvas.getWidth() / 2 + getPaddingLeft(), canvas.getHeight(), strokePaint);
            canvas.drawText(getText().toString(), canvas.getWidth() / 2 + getPaddingLeft(), canvas.getHeight(), textPaint);
        }
    }
}
