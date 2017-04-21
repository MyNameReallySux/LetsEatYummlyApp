package com.sux.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.sux.main.R;

/**
 * Created by Shorty on 5/1/2014.
 */
public interface CustomFonts {
    public default void parseAttributes(Context context, AttributeSet attrs, TextView view) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.SuxTextView);
        //The value 0 is a default, but shouldn't ever be used since the attr is an enum
        int typeface = values.getInt(R.styleable.SuxTextView_typeface, 0);

        switch(typeface) {
            case 0: default:
                Typeface forte = Typeface.createFromAsset(context.getAssets(), "fonts/Forte.TTF");
                view.setTypeface(forte);
                break;
        }
        values.recycle();
    }
}
