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
public class SuxTextView extends TextView implements CustomFonts {

    public SuxTextView(Context context) {
        super(context);
    }

    public SuxTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs, this);
    }

    public SuxTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs, this);
    }
}
