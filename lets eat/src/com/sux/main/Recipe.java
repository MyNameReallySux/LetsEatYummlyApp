package com.sux.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.sux.utility.*;
import com.sux.exceptions.ExceptionManager;
import com.sux.exceptions.RecipeException;
import com.sux.fragments.LoadingFragment;
import com.sux.fragments.RecipeInfoFragment;
import com.sux.fragments.RecipeIngredientsFragment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Shorty on 4/25/14.
 */
public class Recipe extends FragmentActivity implements MenuBar, OptionsMenu {
    DataAsync recipeResult;
    LoadingFragment loadingFragment;
    RecipeInfoFragment recipeInfoFragment;
    RecipeIngredientsFragment recipeIngredientsFragment;

    String recipeID;
    Bitmap recipeImage;
    ImageView backgroundView;
    JSONObject attribution, recipeInfo, ingredients;
    JSONArray ingredientLines;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        backgroundView = (ImageView)findViewById(R.id.backgroundView);
        Intent intent = getIntent();

        recipeID = intent.getStringExtra("RECIPE_ID");
        if (findViewById(R.id.recipe_info_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            recipeResult = (DataAsync)new DataAsync(this).execute(DataAsync.RECIPE_ASYNC, recipeID);
        }

        loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_parent_container, loadingFragment)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        configMenuBar(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        setBackground(newConfig);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleOptionsItemSelected(this, item) || super.onOptionsItemSelected(item);
    }

    public void initRecipe(JSONObject result){
        try {
            if(!result.containsKey(Yummly.ATTRIBUTION_KEY)) throw new RecipeException("Could not read Yummly response. No attribution!");

            attribution = (JSONObject)result.get(Yummly.ATTRIBUTION_KEY);
            ingredientLines = (JSONArray)result.get(Yummly.INGREDIENT_LINES_KEY);

            recipeInfo = new JSONObject();
            ingredients = new JSONObject();

            recipeInfo.put(Yummly.NAME_KEY, result.get(Yummly.NAME_KEY));
            recipeInfo.put(Yummly.IMAGE_KEY, result.get(Yummly.IMAGE_KEY));

            ingredients.put(Yummly.INGREDIENTS_KEY, ingredientLines);
            Log.d(ExceptionManager.LETS_EAT, ingredients.toString());

            Bundle bundle = new Bundle();
            bundle.putString(LetsEat.RECIPE_INFO, recipeInfo.toString());
            bundle.putString(Yummly.ATTRIBUTION_KEY, attribution.toString());
            bundle.putString(Yummly.INGREDIENTS_KEY, ingredients.toString());

            recipeIngredientsFragment = new RecipeIngredientsFragment();
            recipeIngredientsFragment.setArguments(bundle);

            recipeInfoFragment = new RecipeInfoFragment();
            recipeInfoFragment.setArguments(bundle);

            setBackground(getResources().getConfiguration());


            getSupportFragmentManager().beginTransaction()
                        .remove(loadingFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_info_container, recipeInfoFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.recipe_ingredients_container, recipeIngredientsFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
            }, 500);
        } catch (RecipeException e) {
            ExceptionManager.logException(e, this, "Recipe", "recipeComplete()");
        }
    }

    public void setBackground(Configuration config){
        /*if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (recipeInfo.containsKey(Yummly.IMAGE_KEY) && recipeInfo.get(Yummly.IMAGE_KEY) != null) {
                recipeImage = Encoder.decodeBitmap(recipeInfo.get(Yummly.IMAGE_KEY).toString());
                backgroundView.setImageDrawable(new BitmapDrawable(getResources(), recipeImage));
                backgroundView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            if (recipeInfoFragment != null){
                recipeInfoFragment.setBackground(config);
            }

        } else {
            if(backgroundView!= null){
                backgroundView.setImageResource(android.R.color.transparent);
            }
            if (recipeInfoFragment != null){
                recipeInfoFragment.setBackground(config);
            }
        }*/
    }
}