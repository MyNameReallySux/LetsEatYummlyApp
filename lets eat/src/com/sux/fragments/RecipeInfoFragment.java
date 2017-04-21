package com.sux.fragments;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sux.utility.LetsEat;
import com.sux.utility.Encoder;
import com.sux.utility.Yummly;
import com.sux.exceptions.ExceptionManager;
import com.sux.main.R;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Created by Shorty on 4/29/2014.
 */
public class RecipeInfoFragment extends Fragment {
    String recipeName;
    JSONObject attribution, recipeInfo;
    String attributionURL, attributionText, attributionImg;

    Bitmap recipeImage;

    TextView recipeNameView;
    ImageView recipeImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            recipeInfo = Encoder.JSONParse(getArguments().getString(LetsEat.RECIPE_INFO));
            attribution = Encoder.JSONParse(getArguments().getString(Yummly.ATTRIBUTION_KEY));
        } catch (ParseException e){
            ExceptionManager.logException(e, getActivity(), "RecipeInfoFragment", "onCreateView");
        }

        attributionURL = attribution.get(Yummly.ATTRIBUTION_URL_KEY).toString();
        attributionText = attribution.get(Yummly.ATTRIBUTION_TEXT_KEY).toString();
        attributionImg = attribution.get(Yummly.ATTRIBUTION_LOGO_KEY).toString();

        recipeName = recipeInfo.get(Yummly.NAME_KEY).toString();

        if (recipeInfo.containsKey(Yummly.IMAGE_KEY) && recipeInfo.get(Yummly.IMAGE_KEY) != null) recipeImage = Encoder.decodeBitmap(recipeInfo.get(Yummly.IMAGE_KEY).toString());
        else recipeImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_pic);

        View view = inflater.inflate(R.layout.recipe_info_fragment, container, false);

        recipeNameView = (TextView)view.findViewById(R.id.recipe_name);
        recipeImageView = (ImageView)view.findViewById(R.id.recipe_image);

        recipeNameView.setText(recipeName);
        setBackground(getActivity().getResources().getConfiguration());

        return view;
    }

    public void setBackground(Configuration config){
        if(isAdded()){
            if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
                recipeImageView.setImageResource(android.R.color.transparent);
                recipeImageView.setBackground(null);
            } else {
                recipeImageView.setImageDrawable(new BitmapDrawable(getResources(), recipeImage));
                recipeImageView.setBackgroundResource(R.drawable.box_white);
            }
        }

    }
}