package com.sux.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sux.main.R;

/**
 * Created by Shorty on 4/16/14.
 */
public class MessageFragment extends DialogFragment implements OnClickListener {
    public static int ICON_CHECK_MARK = 0;
    public static int ICON_EXCLAMATION_MARK = 1;
    public static int ICON_INFO_MARK = 2;
    public static int ICON_X_MARK = 3;

    private Context context;
    private String message;
    private int icon;

    public MessageFragment(){
    }


    public MessageFragment(String message){
        this.message = message;
        icon = 0;
    }

    public MessageFragment(String message, int icon){
        this.message = message;
        this.icon = icon;
    }


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);

        Drawable[] icons = {
                context.getResources().getDrawable(R.drawable.check_mark),
                context.getResources().getDrawable(R.drawable.check_mark),
                context.getResources().getDrawable(R.drawable.check_mark),
                context.getResources().getDrawable(R.drawable.x_mark),
        };
        setCancelable(false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        TextView messageView = (TextView)view.findViewById(R.id.message);
        ImageView imageView = (ImageView)view.findViewById(R.id.message_icon);
        Button button = (Button)view.findViewById(R.id.ok_button);
        button.setOnClickListener(this);

        messageView.setText(message);
        imageView.setBackground(icons[icon]);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ok_button){
            dismiss();
        }
    }

    interface CallBack {
        public void onDialogMessage(String message);
    }

}