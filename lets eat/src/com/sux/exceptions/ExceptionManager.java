package com.sux.exceptions;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.sux.fragments.MessageFragment;

/**
 * Created by Shorty on 4/15/14.
 */
public abstract class ExceptionManager {

    /*
        **SIGNATURE public static String logException(Exception e, Context context, String className, String methodName)
        **REQUIRED**
        * Exception e >> you MUST pass in an exception

        **TOASTS**
        * Context context >> context must be passed from the original activity. This will automatically set up Exception Manager
            to show a toast.

        **DEBUG**
        * String className and String methodName >> Both must be passed in together. You may set one to null if you do not wish
            to display it.

        <<Copy this expression to easily log an exception!>>
            ExceptionManager.logException(e, this, "Enter Class Name", "Enter Method Name");
     */

    public static final String LETS_EAT = "Lets Eat";

    public static String logException(Exception e){
        init(e, null, null, null, false);
        return e.getMessage();
    }

    public static String logException(Exception e, String className, String methodName){
        init(e, null, className, methodName, false);
        return e.getMessage();
    }

    public static String logException(Exception e, Context context){
        init(e, context, null, null, true);
        return e.getMessage();
    }

    public static String logException(Exception e, Context context, String className, String methodName){
        init(e, context, className, methodName, true);
        return e.getMessage();
    }

    private static void init(Exception e, Context context, String className, String methodName, boolean toast){
        StringBuilder sb = new StringBuilder("\n===============================\n");
        if(className != null){
            sb.append("CLASS: " + className + " >> ");
        }
        if(methodName != null){
            sb.append("METHOD: " + methodName + " >> ");
        }
        if(className != null || methodName != null){
            sb.append("\n");
        }

        sb.append(e.getMessage() + "\n" + e.getStackTrace().toString());

        String result = sb.toString();
        Log.e(LETS_EAT, result);
        e.printStackTrace();
        if(toast && context != null){
           //toastException(context, e.getMessage());
            dialogException(context, e.getMessage());
        }
    }

    private static void toastException(Context context, String result){
        Toast t = Toast.makeText(context, result, Toast.LENGTH_SHORT);
        t.show();
    }

    private static void dialogException(Context context, String message){
        FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
        MessageFragment messageFragment = new MessageFragment(message, MessageFragment.ICON_X_MARK);
        messageFragment.show(fragmentManager, "Failed!");
    }
}
