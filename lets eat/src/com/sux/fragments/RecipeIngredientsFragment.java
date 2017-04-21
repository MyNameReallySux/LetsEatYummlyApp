package com.sux.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sux.utility.Encoder;
import com.sux.utility.Yummly;
import com.sux.exceptions.ExceptionManager;
import com.sux.main.R;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
/**
 * Created by Shorty on 5/1/2014.
 */
public class RecipeIngredientsFragment extends Fragment {
    JSONObject json;
    JSONArray ingredientLines;
    LinearLayout left, right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            json = Encoder.JSONParse(getArguments().getString(Yummly.INGREDIENTS_KEY));
            ingredientLines = (JSONArray)json.get(Yummly.INGREDIENTS_KEY);
        } catch (ParseException e){
            ExceptionManager.logException(e, getActivity(), "RecipeIngredientsFragment", "onCreateView");
        }

        View view = inflater.inflate(R.layout.recipe_ingredients_fragment, container, false);

        left = (LinearLayout)view.findViewById(R.id.recipe_ingredients_left);
        right = (LinearLayout)view.findViewById(R.id.recipe_ingredients_right);

        for(int i = 0; i < ingredientLines.size(); i++){
            TextView textView = new TextView(getActivity());
            textView.setText(ingredientLines.get(i).toString());

            if((i + 1) % 2 == 0)  right.addView(textView);
            else left.addView(textView);
        }

        return view;
    }
}