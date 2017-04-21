package com.sux.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sux.utility.LetsEat;
import com.sux.main.R;

/**
 * Created by Shorty on 4/14/14.
 */
public class ProfileInfoFragment extends Fragment {
    String id, email, fname, lname;
    TextView nameView;
    ImageView profPicView;
    Bitmap profPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        id = getArguments().getString(LetsEat.ID);
        email = getArguments().getString(LetsEat.EMAIL);
        fname = getArguments().getString(LetsEat.FNAME);
        lname = getArguments().getString(LetsEat.LNAME);
        profPic = getArguments().getParcelable(LetsEat.PICTURE);

        View view = inflater.inflate(R.layout.profile_info_fragment, container, false);

        nameView = (TextView)view.findViewById(R.id.nameLabel);
        profPicView = (ImageView)view.findViewById(R.id.profPic);

        nameView.setText(fname + " " + lname);
        profPicView.setImageDrawable(new BitmapDrawable(getResources(), profPic));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        /*try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }*/
    }
}