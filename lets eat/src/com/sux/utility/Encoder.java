package com.sux.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

/**
 * Created by Shorty on 4/26/2014.
 */
public class Encoder {
    public static final String ALPHA_PATTERN = "^[_A-Za-z0-9]";
    public static final String EMAIL_PATTERN = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
    public static final String PASSWORD_PATTERN = "((?=.*[a-zA-Z0-9@#$%]).{6,20})";

    private Encoder(){}

    public static JSONObject JSONParse(String s) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(s);
        JSONObject json = (JSONObject)obj;
        return json;
    }

    public static JSONObject JSONEncode(String[] tags, String[] values){
        JSONObject json = new JSONObject();
        for(int i = 0; i < tags.length; i++){
            json.put(tags[i], values[i]);
        }
        return json;
    }

    public static String encodeBitmap(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap decodeBitmap(String encodedImage){
        byte[] b = Base64.decode(encodedImage, 0);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public static boolean regex(String str, String regex){
        Pattern p = Pattern.compile(regex);
        return p.matcher(str).find();
    }
}
