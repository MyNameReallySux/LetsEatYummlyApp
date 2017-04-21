package com.sux.utility;

import android.content.Context;
import android.view.MenuItem;
import com.sux.main.R;

/**
 * Created by Shorty on 5/2/2014.
 */
public interface OptionsMenu {
    public default boolean handleOptionsItemSelected(Context context, MenuItem item){
        switch (item.getItemId()) {
            case R.id.search:
                LetsEat.loadSearch(context);
                return true;
            case R.id.log_off:
                LetsEat.logOffDialog(context);
                return true;
            case R.id.menu_profile:
                LetsEat.goToProfileDialog(context);
                return true;
            case R.id.menu_recipe:
                LetsEat.goToRecipeDialog(context);
                return true;
            default:
                return false;
        }

    }
}
