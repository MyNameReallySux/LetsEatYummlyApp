package com.sux.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.sux.exceptions.*;
import com.sux.main.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Shorty on 4/12/14.
 */
public class DataAsync extends AsyncTask<String, Void, JSONObject> {
    public static final String LOGIN_ASYNC = "login";
    public static final String PROFILE_ASYNC = "profile";
    public static final String REGISTER_ASYNC = "register";
    public static final String RECIPE_ASYNC = "recipe";
    public static final String SEARCH_ASYNC = "search";

    public String code, call;
    protected Context context;

    public DataAsync(Context context){
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... args){
        call = args[0];
        JSONObject json;

        if     (args[0].equals(LOGIN_ASYNC))    json = loginStart(args[0], args[1], args[2]);
        else if(args[0].equals(PROFILE_ASYNC))  json = profileStart(args[0], args[1]);
        else if(args[0].equals(REGISTER_ASYNC)) json = registerStart(args[0], args[1], args[2], args[3], args[4], args[5]);
        else if(args[0].equals(RECIPE_ASYNC))   json = recipeStart(args[0], args[1]);
        else if(args[0].equals(SEARCH_ASYNC))   json = searchStart(args[0], args[1], args[2], args[3]);
        else throw new AsyncTaskException("Argument 0 is not a String.");
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject result){
        try {
            if     (call.equals(LOGIN_ASYNC))       ((Login)context).loginComplete(result);
            else if(call.equals(PROFILE_ASYNC))     ((Profile)context).initProfile(result);
            else if(call.equals(REGISTER_ASYNC))    ((Register)context).completeRegister(result);
            else if(call.equals(RECIPE_ASYNC))      ((Recipe)context).initRecipe(result);
            else if(call.equals(SEARCH_ASYNC))      ((Search)context).handleSearchResults(result);
            else throw new AsyncTaskException("The passed in type is not defined.");
        } catch (AsyncTaskException e){
            ExceptionManager.logException(e, context, "DataAsync", "onPostExecute()");
        }
    }

    public JSONObject loginStart(String call, String email, String password){
        if(call == null) throw new AsyncTaskException("The call passed in is null", new NullPointerException());
        if(email == null) throw new AsyncTaskException("The email passed in is null", new NullPointerException());
        if(password == null) throw new AsyncTaskException("The email passed in is null", new NullPointerException());

        String[][] post = {
                {LetsEat.EMAIL, email}
                ,{LetsEat.PASSWORD, password}};

        String result = LetsEat.postRequest(context, LetsEat.LOGIN_LINK, post);
        JSONObject json;
        try {
            json = Encoder.JSONParse(result);
        } catch (ParseException e){
            ExceptionManager.logException(e, context, "DataAsync", "loginStart()");
            String[] tags = {LetsEat.MESSAGE};
            String[] values = {e.getMessage()};
            json =  Encoder.JSONEncode(tags, values);
        }
        return json;
    }

    public JSONObject profileStart(String call, String id){
        if(call == null) throw new AsyncTaskException("The call passed in is null", new NullPointerException());
        if(id == null) throw new AsyncTaskException("The id passed in is null", new NullPointerException());

        String[][] post = {{LetsEat.ID, id}};

        Bitmap profileImage;
        String encodedImage;

        String result = LetsEat.postRequest(context, LetsEat.PROFILE_LINK, post);

        JSONObject json;
        try {
            json = Encoder.JSONParse(result);
        } catch (ParseException e){
            ExceptionManager.logException(e, context, "DataAsync", "loginStart()");
            String[] tags = {LetsEat.MESSAGE};
            String[] values = {e.getMessage()};
            json =  Encoder.JSONEncode(tags, values);
        }

        String picAddress;
        if(json.containsKey(LetsEat.PICTURE) && json.get(LetsEat.PICTURE) != null){
            picAddress = LetsEat.DOMAIN + json.get(LetsEat.PICTURE).toString();
            try{
                URL url = new URL(picAddress);
                profileImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                encodedImage = Encoder.encodeBitmap(profileImage);
                json.remove(LetsEat.PICTURE);
                json.put(LetsEat.PICTURE, encodedImage);
            } catch (MalformedURLException e){
                ExceptionManager.logException(e, context, "DataAsync", "profileStart()");
            } catch (IOException e){
                ExceptionManager.logException(e, context, "DataAsync", "profileStart()");
            }
        }
        return json;
    }

    public JSONObject recipeStart(String call, String id){
        if(call == null)    throw new AsyncTaskException("The call passed in is null");
        if(id == null)      throw new AsyncTaskException("The id passed in is null");
        String[][] get = {{LetsEat.ID, id}};

        String result = Yummly.getRecipe(context, get);
        Bitmap profileImage;
        String encodedImage;

        JSONObject json;
        try {
            json = Encoder.JSONParse(result);
            json.put(LetsEat.MESSAGE, "Recipe_Success");
        } catch (ParseException e){
            ExceptionManager.logException(e, context, "DataAsync", "recipeStart()");
            String[] tags = {LetsEat.MESSAGE};
            String[] values = {e.getMessage()};
            json =  Encoder.JSONEncode(tags, values);
        }

        JSONArray imageArray;
        JSONObject images;
        String picAddress;

        if(json.containsKey(Yummly.IMAGE_KEY) && json.get(Yummly.IMAGE_KEY) != null){
            try {
                imageArray = (JSONArray)json.get(Yummly.IMAGE_KEY);
                images = (JSONObject)imageArray.get(0);
                picAddress = images.get(Yummly.IMAGE_LARGE_KEY).toString();

                URL url = new URL(picAddress);
                profileImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                encodedImage = Encoder.encodeBitmap(profileImage);
                json.remove(Yummly.IMAGE_KEY);
                json.put(Yummly.IMAGE_KEY, encodedImage);
            } catch (MalformedURLException e){
                ExceptionManager.logException(e, context, "DataAsync", "profileStart()");
            } catch (IOException e){
                ExceptionManager.logException(e, context, "DataAsync", "profileStart()");
            }
        }
        return json;upport.
    }

    public JSONObject registerStart(String call, String fname, String lname, String email, String password, String password2){
        if(call == null)                throw new AsyncTaskException("The call passed in is null");
        if(fname == null)               throw new AsyncTaskException("The first name passed in is null");
        if(lname == null)               throw new AsyncTaskException("The last name passed in is null");
        if(email == null)               throw new AsyncTaskException("The email passed in is null");
        if(password == null)            throw new AsyncTaskException("The password passed in is null");
        if(!password2.equals(password)) throw new AsyncTaskException("The passwords do not match");

        String[][] post = {
                {LetsEat.FNAME, fname},
                {LetsEat.LNAME, lname},
                {LetsEat.EMAIL, email},
                {LetsEat.PASSWORD, password},
                {LetsEat.PASSWORD + "2", password2}};

        String result = LetsEat.postRequest(context, LetsEat.REGISTER_LINK, post);
        JSONObject json;
        try {
            json = Encoder.JSONParse(result);
        } catch (ParseException e){
            ExceptionManager.logException(e, context, "DataAsync", "registerStart()");
            String[] tags = {LetsEat.MESSAGE};
            String[] values = {e.getMessage()};
            json =  Encoder.JSONEncode(tags, values);
        }
        return json;
    }

    public JSONObject searchStart(String call, String query, String start, String maxResults){
        if(call == null)    throw new AsyncTaskException("The call passed in is null");
        if(query == null)      throw new AsyncTaskException("The query passed in is null");

        String result = Yummly.searchRecipe(context, query, maxResults, start);
        Bitmap recipeImage;
        String encodedImage;

        JSONObject json;

        try {
            json = Encoder.JSONParse(result);
            json.put(LetsEat.MESSAGE, "Search_Success");
        } catch (ParseException e){
            ExceptionManager.logException(e, context, "DataAsync", "searchStart()");
            String[] tags = {LetsEat.MESSAGE};
            String[] values = {e.getMessage()};
            json =  Encoder.JSONEncode(tags, values);
        }
        JSONArray matches, imageArray, images;
        JSONObject match;
        String picAddress;

        if(json.containsKey(Yummly.MATCHES)){
            matches = (JSONArray)json.get(Yummly.MATCHES);
            images = new JSONArray();
            for(int i = 0; i < matches.size(); i++){
                try {
                    match = (JSONObject)matches.get(i);
                    imageArray = (JSONArray)match.get(Yummly.SMALL_IMAGES);
                    picAddress = imageArray.get(0).toString();

                    URL url = new URL(picAddress);
                    recipeImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    encodedImage = Encoder.encodeBitmap(recipeImage);

                    images.add(encodedImage);
                } catch (IOException e){
                    ExceptionManager.logException(e, context, "DataAsync", "profileStart()");
                }
            }
            json.put(Yummly.IMAGE_KEY, images);
        }
        return json;
    }
}
