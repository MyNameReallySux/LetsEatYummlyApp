package com.sux.widgets.test;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import com.sux.main.R;

/**
 * Created by Shorty on 4/13/14.
 */
public class PasswordField extends EditText {
    private Drawable imgViewButton = getResources().getDrawable(R.drawable.close_button);

    public PasswordField(Context context){
        super(context);
        init();
    }

    public PasswordField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordField(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        // Set bounds of the Clear button so it will look ok
        imgViewButton.setBounds(0, 0, imgViewButton.getIntrinsicWidth(), imgViewButton.getIntrinsicHeight());
        // There may be initial text in the field, so we may need to display the  button
        handleClearButton();

        this.setLongClickable(false);
        this.setTextIsSelectable(false);

        //if the Close image is displayed and the user remove his finger from the button, clear it. Otherwise do nothing
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                PasswordField et = PasswordField.this;

                if (et.getCompoundDrawables()[2] == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP  && event.getX() > et.getWidth() - et.getPaddingRight() - imgViewButton.getIntrinsicWidth()){
                    et.setTransformationMethod(new PasswordTransformationMethod().getInstance());
                    Log.d("Lets Eat", "PasswordField >> onTouch >> UP Event");
                } else if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() > et.getWidth() - et.getPaddingRight() - imgViewButton.getIntrinsicWidth()) {
                    et.setTransformationMethod(null);
                    Log.d("Lets Eat", "PasswordField >> onTouch >> DOWN Event");
                }
                return false;
            }
        });
    }

    //intercept Typeface change and set it with our custom font
    public void setTypeface(Typeface tf, int style) {
        /*if (style == Typeface.BOLD) {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Vegur-B 0.602.otf"));
        } else {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Vegur-R 0.602.otf"));
        }*/
    }

    void handleClearButton() {
        if (this.getText().toString().equals("")){
            // add the clear button
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
        } else {
            //remove clear button
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], imgViewButton, this.getCompoundDrawables()[3]);
        }
    }
}
