package com.sux.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import com.sux.utility.*;
import com.sux.exceptions.ExceptionManager;
import com.sux.exceptions.ProfileException;
import com.sux.fragments.LoadingFragment;
import com.sux.fragments.ProfileInfoFragment;
import com.sux.fragments.ProfileTabsFragment;
import org.json.simple.JSONObject;

/**
 * Created by Shorty on 3/14/14.
 */
public class Profile extends FragmentActivity implements MenuBar, OptionsMenu {
    DataAsync profileResults;
    JSONObject loginInfo;

    ProfileInfoFragment profileInfoFragment;
    ProfileTabsFragment tabsFragment;
    LoadingFragment loadingFragment;

    String id, email, fname, lname;
    Bitmap profileImage;

    boolean owner, friend, blocked;

    String login_id, login_email, login_fname, login_lname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent intent = getIntent();

        loginInfo = LetsEat.getLoginInfo(this);
        login_id = loginInfo.get(LetsEat.ID).toString();
        login_email = loginInfo.get(LetsEat.EMAIL).toString();
        login_fname = loginInfo.get(LetsEat.FNAME).toString();
        login_lname = loginInfo.get(LetsEat.LNAME).toString();

        id = intent.getStringExtra("PROFILE_ID");
        if (findViewById(R.id.profile_info_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            profileResults = (DataAsync)new DataAsync(this).execute(DataAsync.PROFILE_ASYNC, id);
        }
        loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.profile_parent_container, loadingFragment)
                .commit();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        configMenuBar(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void initProfile(JSONObject result){
        try {
            if(!result.containsKey(LetsEat.MESSAGE)) throw new ProfileException("Could not connect to server. Please try again later!");
            String code = result.get(LetsEat.MESSAGE).toString();

            if (!code.contains("Profile_Success")) throw new ProfileException("Could not initialize profile!");
            id = result.get(LetsEat.ID).toString();
            email = result.get(LetsEat.EMAIL).toString();
            fname = result.get(LetsEat.FNAME).toString();
            lname = result.get(LetsEat.LNAME).toString();

            if (result.containsKey(LetsEat.PICTURE) && result.get(LetsEat.PICTURE) != null) profileImage = Encoder.decodeBitmap(result.get(LetsEat.PICTURE).toString());
            else profileImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_pic);

            initProfileInfoFragment();
            initRecipeFragment();
        } catch (ProfileException e){
            ExceptionManager.logException(e, this, "DataAsync", "profileComplete()");
        }
    }

    public void initProfileInfoFragment(){
        Bundle profileInfo = new Bundle();
        profileInfo.putString(LetsEat.ID, id);
        profileInfo.putString(LetsEat.EMAIL, email);
        profileInfo.putString(LetsEat.FNAME, fname);
        profileInfo.putString(LetsEat.LNAME, lname);
        profileInfo.putParcelable(LetsEat.PICTURE, profileImage);

        profileInfoFragment = new ProfileInfoFragment();
        profileInfoFragment.setArguments(profileInfo);

        tabsFragment = new ProfileTabsFragment();

        if(findViewById(R.id.loading_fragment) != null){
            getSupportFragmentManager().beginTransaction()
                    .remove(loadingFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.profile_info_container, profileInfoFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void initRecipeFragment(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (findViewById(R.id.loading_fragment) != null) {
                    getSupportFragmentManager().beginTransaction()
                            .remove(loadingFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.profile_tab_container, tabsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        }, 500);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleOptionsItemSelected(this, item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        LetsEat.logOffDialog(this);
    }
}