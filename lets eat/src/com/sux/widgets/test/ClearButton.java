package com.sux.widgets.test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.sux.main.R;

/**
 * Created by Shorty on 4/15/14.
 */
public class ClearButton extends ImageButton {
    EditText target;
    Drawable icon;

    public ClearButton(Context context, EditText editText){
        super(context);
        target = editText;
        init();
    }


    public void init(){
        icon = getResources().getDrawable(R.drawable.ic_action_cancel);
        this.setImageDrawable(icon);
        this.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN);
                target.setText("");
                return false;
            }
        });

    }
}
