package liram.dev.cryptocurrency.utility;

import android.content.Context;
import android.content.SharedPreferences;
/*
    This class handle in SharedPreferences across thr Application.
    it's singleton class because w'll use it in few places in project
 */
public class UserPreferencesManager {

    private UserPreferencesManager(){}

    private static SharedPreferences getSharedPreferences(String keyPreferences, Context context){
        return context.getSharedPreferences(keyPreferences, Context.MODE_PRIVATE);
    }

    public static void putStringValue(Context context,String key,String value){
        SharedPreferences sharedPreferences = getSharedPreferences(key, context);
        SharedPreferences.Editor editor = sharedPreferences.edit().putString(key, value);
        editor.apply();
    }

    public static String getStringValue(Context context, String key){
        return getSharedPreferences(key, context.getApplicationContext()).getString(key,"");
    }

    public static void putBooleanValue(Context context, String key, boolean value){
        getSharedPreferences(key, context.getApplicationContext()).edit().putBoolean(key, value).apply();
    }

    public static boolean getBooleanValue(Context context, String key){
       return getSharedPreferences(key, context.getApplicationContext()).getBoolean(key, false);
    }
}
