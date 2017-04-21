package com.sux.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Shorty on 4/15/14.
 */
public class AnimButton extends ImageButton {
    TransitionDrawable d;

    public AnimButton(Context context){
        super(context);
        init();
    }

    public AnimButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init(){
        d = (TransitionDrawable)getDrawable();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    d.startTransition(500);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    d.reverseTransition(500);
                }
                return false;
            }
        });
    }

}
