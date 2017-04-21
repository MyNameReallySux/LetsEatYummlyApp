package com.sux.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.*;
import com.sux.main.R;

/**
 * Created by Shorty on 4/15/14.
 */
public class ProfileTabsFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_tabs_fragment, container, false);
        TabHost tabHost = (TabHost)view.findViewById(R.id.tabHost);
        tabHost.setup();

        TabSpec recipiesTab = tabHost.newTabSpec("Recipies");
        recipiesTab.setContent(R.id.tab1);
        recipiesTab.setIndicator("Recipies", null);
        tabHost.addTab(recipiesTab);

        TabSpec photosTab = tabHost.newTabSpec("Photos");
        photosTab.setContent(R.id.tab2);
        photosTab.setIndicator("Photos", null);
        tabHost.addTab(photosTab);

        TabSpec aboutTab = tabHost.newTabSpec("About");
        aboutTab.setContent(R.id.tab3);
        aboutTab.setIndicator("About", null);
        tabHost.addTab(aboutTab);
        return view;
    }
}
