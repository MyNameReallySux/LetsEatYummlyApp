package com.sux.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.widget.EditText;
import com.sux.exceptions.ExceptionManager;
import com.sux.exceptions.LoginException;
import com.sux.main.*;
import org.json.simple.*;

import java.io.*;
import java.net.*;

/**
 * Created by Shorty on 3/13/14.
 */
public abstract class LetsEat {
    public static final String DOMAIN = "http://www.detailingbywayne.com/scripts/test/";
    public static final String LOGIN_LINK = DOMAIN + "login.php";
    public static final String PROFILE_LINK = DOMAIN + "profile.php";
    public static final String REGISTER_LINK = DOMAIN + "register.php";

    public static final String CALL = "call";
    public static final String MESSAGE = "msg";

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FNAME = "fname";
    public static final String LNAME = "lname";
    public static final String PICTURE = "pic";

    public static final String RECIPE_INFO = "recipe_info";

    public static final String USER_INFO = "loginStatus";

    private LetsEat(){}

    public static void setLoginInfo(Context context, JSONObject json){
        String id, email, fname, lname, pic;
        try {
            if(!json.containsKey(LetsEat.ID)) throw new LoginException("The id field could not be parsed!");
            if(!json.containsKey(LetsEat.EMAIL)) throw new LoginException("The email field could not be parsed!");
            if(!json.containsKey(LetsEat.FNAME)) throw new LoginException("The first name field could not be parsed!");
            if(!json.containsKey(LetsEat.LNAME)) throw new LoginException("The last name field could not be parsed!");
            if(!json.containsKey(LetsEat.PICTURE))throw new LoginException("The picture field could not be parsed!");

            id = json.get(LetsEat.ID).toString();
            email = json.get(LetsEat.EMAIL).toString();
            fname = json.get(LetsEat.FNAME).toString();
            lname = json.get(LetsEat.LNAME).toString();
            pic = json.get(LetsEat.PICTURE).toString();

            SharedPreferences userInfo = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();

            editor.putString(LetsEat.ID, id);
            editor.putString(LetsEat.EMAIL, email);
            editor.putString(LetsEat.FNAME, fname);
            editor.putString(LetsEat.LNAME, lname);
            editor.putString(LetsEat.PICTURE, pic);
            editor.commit();
        } catch (LoginException e){
            ExceptionManager.logException(e, context, "Database", "setLoginInfo");
        }
    }

    public static JSONObject getLoginInfo(Context context){
        SharedPreferences userInfo = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        String[] tags = {
                LetsEat.ID,
                LetsEat.EMAIL,
                LetsEat.FNAME,
                LetsEat.LNAME,
                LetsEat.PICTURE};
        String[] values = {
                userInfo.getString(LetsEat.ID, "DEFAULT"),
                userInfo.getString(LetsEat.EMAIL, "DEFAULT"),
                userInfo.getString(LetsEat.FNAME, "DEFAULT"),
                userInfo.getString(LetsEat.LNAME, "DEFAULT"),
                userInfo.getString(LetsEat.PICTURE, null)};
        return Encoder.JSONEncode(tags, values);
    }

    public static void logOffDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.log_off_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        logOff(context);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {}
                });
        builder.create().show();
    }

    public static void goToProfileDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Go To Profile");
        builder.setMessage("Enter Profile ID: ");

        // Use an EditText view to get user input.
        final EditText input = new EditText(context);
        input.setId(0);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int button) {
                String value;
                if(input.getText() != null){
                    value = input.getText().toString();
                } else {
                    value = "0";
                }
                loadProfile(context, value);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int button){}
        });
        builder.create().show();
    }

    public static void goToRecipeDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Go To Recipe");
        builder.setMessage("Enter Recipe ID:\n" +
                "1: Teriyaki Chicken\n" +
                "2: Chicken Quesadillas\n" +
                "3: Sweet and Crispy Wings\n" +
                "4: Big Italian Salad\n" +
                "5: Manicotti's\n" +
                "6: Italian Sausage Soup");

        // Use an EditText view to get user input.
        final EditText input = new EditText(context);
        input.setId(0);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int button) {
                String value;
                if(input.getText() != null){
                    value = input.getText().toString();
                    if(value.equals("1")) value = "Teriyaki-chicken-310788";
                    else if(value.equals("2")) value = "Basic-Chicken-Quesadillas-505984";
                    else if(value.equals("3")) value = "Sweet-and-Crispy-Chicken-Wings-502054";
                    else if(value.equals("4")) value = "Big-Italian-Salad-Once-Upon-A-Chef-200045";
                    else if(value.equals("5")) value = "Manicotti-Italian-Casserole-Allrecipes";
                    else if(value.equals("6")) value = "Italian-sausage_-tomato_-and-macaroni-soup-with-basil-309363";
                    else value = "Teriyaki-chicken-310788";
                } else {
                    value = "Teriyaki-chicken-310788";
                }
                loadRecipe(context, value);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int button){}
        });
        builder.create().show();
    }

    public static void loadProfile(Context context, String id){
        Intent intent = new Intent(context, Profile.class);
        intent.putExtra("PROFILE_ID", id);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public static void loadRecipe(Context context, String id){
        Intent intent = new Intent(context, Recipe.class);
        intent.putExtra("RECIPE_ID", id);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public static void loadSearch(Context context){
        Intent intent = new Intent(context, Search.class);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public static void logOff(Context context){
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();

        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public static String postRequest(Context context, String link, String[][] values) {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            String data = "";
            URL url = new URL(link);
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null && values[i] != null) {
                    data += URLEncoder.encode(values[i][0], "UTF-8") + "=" + URLEncoder.encode(values[i][1], "UTF-8");
                    if (values.length >= i - 1) {
                        data += "&";
                    }
                }
            }
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            return ExceptionManager.logException(e, context, "Database", "postRequest");
        } catch (UnsupportedEncodingException e) {
            return ExceptionManager.logException(e, context, "Database", "postRequest");
        } catch (IOException e) {
            return ExceptionManager.logException(e, context, "Database", "postRequest");
        }
    }
}
