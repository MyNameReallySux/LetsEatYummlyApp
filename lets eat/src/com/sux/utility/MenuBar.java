package com.sux.utility;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import com.sux.main.R;

/**
 * Created by Shorty on 5/2/2014.
 */
public interface MenuBar {
    public default void configMenuBar(Context context, Menu menu){
        MenuInflater inflater = ((Activity)context).getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
    }
}
