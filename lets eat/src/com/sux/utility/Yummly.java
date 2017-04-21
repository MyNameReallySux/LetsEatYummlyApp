package com.sux.utility;

import android.content.Context;
import android.util.Log;
import com.sux.exceptions.ExceptionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Shorty on 4/26/14.
 */
public class Yummly {
    public static final String DOMAIN = "https://api.yummly.com/v1/";
    public static final String RECIPE_LINK = DOMAIN + "api/recipe/";
    public static final String SEARCH_LINK = DOMAIN + "api/recipes";
    public static final String APP_ID = "53d3ca77";
    public static final String APP_KEY = "07067ec4a20089a7f94070b630288073";

    public static final String APP_ID_TAG = "_app_id";
    public static final String APP_KEY_TAG = "_app_key";

    public static final String QUERY = "q";
    public static final String START = "start";
    public static final String MAX_RESULTS = "maxResults";
    public static final String MATCHES = "matches";
    public static final String TOTAL_MATCHES = "totalMatchCount";
    public static final String SMALL_IMAGES = "smallImageUrls";

    public static final String RECIPE_NAME = "recipeName";
    public static final String SOURCE_NAME = "sourceDisplayName";
    public static final String ID = "id";

    public static final String ATTRIBUTION_KEY = "attribution";
    public static final String ATTRIBUTION_URL_KEY = "url";
    public static final String ATTRIBUTION_TEXT_KEY = "text";
    public static final String ATTRIBUTION_LOGO_KEY = "logo";

    public static final String NAME_KEY = "name";
    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String INGREDIENT_LINES_KEY = "ingredientLines";
    public static final String IMAGE_KEY = "images";
    public static final String IMAGE_LARGE_KEY = "hostedLargeUrl";
    public static final String IMAGE_SMALL_KEY = "hostedSmallUrl";

    private Yummly(){}

    public static String getRecipe(Context context, String[][] values){
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            String data = "?";

            data += URLEncoder.encode(APP_ID_TAG, "UTF-8") + "=" + URLEncoder.encode(APP_ID, "UTF-8");
            data += "&" + URLEncoder.encode(APP_KEY_TAG, "UTF-8") + "=" + URLEncoder.encode(APP_KEY, "UTF-8");

            URL url = new URL(RECIPE_LINK + values[0][1] + data);
            URLConnection conn = url.openConnection();
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (MalformedURLException e){
            return ExceptionManager.logException(e, context, "Yummly", "getRecipe");
        } catch (UnsupportedEncodingException e){
            return ExceptionManager.logException(e, context, "Yummly", "getRecipe");
        } catch (IOException e){
            return ExceptionManager.logException(e, context, "Yummly", "getRecipe");
        }
    }

    public static String searchRecipe(Context context, String queryStr, String maxResults, String start){
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            String data = "?";

            data += URLEncoder.encode(APP_ID_TAG, "UTF-8") + "=" + URLEncoder.encode(APP_ID, "UTF-8");
            data += "&" + URLEncoder.encode(APP_KEY_TAG, "UTF-8") + "=" + URLEncoder.encode(APP_KEY, "UTF-8");
            data += "&" + URLEncoder.encode(QUERY, "UTF-8") + "=" + URLEncoder.encode(queryStr, "UTF-8");
            data += "&" + URLEncoder.encode(START, "UTF-8") + "=" + URLEncoder.encode(start, "UTF-8");
            data += "&" + URLEncoder.encode(MAX_RESULTS, "UTF-8") + "=" + URLEncoder.encode(maxResults, "UTF-8");

            URL url = new URL(SEARCH_LINK + data);
            Log.d(ExceptionManager.LETS_EAT, url.toString());
            URLConnection conn = url.openConnection();
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();

        } catch (MalformedURLException e){
            return ExceptionManager.logException(e, context, "Yummly", "searchRecipe");
        } catch (UnsupportedEncodingException e){
            return ExceptionManager.logException(e, context, "Yummly", "searchRecipe");
        } catch (IOException e){
            return ExceptionManager.logException(e, context, "Yummly", "searchRecipe");
        }
    }
}
