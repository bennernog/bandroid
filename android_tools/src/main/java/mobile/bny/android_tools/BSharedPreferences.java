package mobile.bny.android_tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.Set;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by BNY on 04/11/2017.
 */

public class BSharedPreferences {


    private static final String HEIGHT = "mobile.bny.bandroid.utils.height";
    private static final String WIDTH = "mobile.bny.bandroid.utils.width";
    private static String IS_INTEGER="mobile.bny.bandroid.utils.isInteger";
    private static String FORMAT="%s %s";

    //Integers
    public static void putInt(@NonNull Context context, @NonNull String key,@NonNull  int value){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static int getInt (@NonNull Context context, @NonNull String key){
        return getInt(context,key,-1);
    }

    public static int getInt (@NonNull Context context,@NonNull  String key, int defaultValue){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        return sharedPref.getInt(key, defaultValue);
    }

    public static void putIntSet(@NonNull Context context, @NonNull String key,@NonNull  Set<Integer> value){
        Set<String> set = Collections.emptySet();
        for (int i : value) {
            set.add(String.valueOf(i));
        }
        putStringSet(context, String.format(FORMAT,IS_INTEGER, key),set);
    }

    public static Set<Integer> getIntSet(@NonNull Context context, @NonNull String key) {
        return getIntSet(context, key, null);
    }

    public static Set<Integer> getIntSet(@NonNull Context context, @NonNull  String key, Set<Integer> defaultValue){
        Set<String> set = getStringSet(context, String.format(FORMAT,IS_INTEGER, key));
        if (set == null)
            return  defaultValue;

        Set<Integer> result = Collections.emptySet();
        for (String s : set) {
            result.add(Integer.parseInt(s));
        }
        return result;
    }

    //Strings
    public static void putString(@NonNull Context context, @NonNull String key,@NonNull  String value){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getString(@NonNull Context context, @NonNull String key){
        return getString(context, key, null);
    }

    public static String getString (@NonNull Context context, @NonNull String key, String defaultvalue){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        return sharedPref.getString(key, defaultvalue);
    }

    public static void putStringSet(@NonNull Context context,@NonNull  String key,@NonNull  Set<String> value){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(key,value);
        editor.apply();
    }

    public static Set<String> getStringSet(@NonNull Context context, @NonNull String key) {
        return getStringSet(context, key, null);
    }

    public static Set<String> getStringSet (@NonNull Context context, @NonNull  String key, Set<String> defaultValue){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        return sharedPref.getStringSet(key, defaultValue);
    }

    //Booleans
    public static void putBoolean(@NonNull Context context,@NonNull  String key,@NonNull  boolean value){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static boolean getBoolean(@NonNull Context context, @NonNull String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(@NonNull Context context, @NonNull  String key, boolean defaultValue){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        return sharedPref.getBoolean(key, defaultValue);
    }

    //Floats
    public static void putFloat(@NonNull Context context,@NonNull  String key, @NonNull float value){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(key,value);
        editor.apply();
    }

    public static float getFloat(@NonNull Context context, @NonNull String key) {
        return getFloat(context, key, -1);
    }

    public static float getFloat (@NonNull Context context, @NonNull  String key, float defaultValue){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        return sharedPref.getFloat(key, defaultValue);
    }

    //Longs
    public static void putLong(@NonNull Context context,@NonNull  String key,@NonNull  long value){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key,value);
        editor.apply();
    }

    public static long getLong(@NonNull Context context, @NonNull String key) {
        return getLong(context, key, -1);
    }

    public static long getLong (@NonNull Context context, @NonNull  String key, long defaultValue){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        return sharedPref.getLong(key, defaultValue);
    }

    //Screenmetrics
    public static void putMetrics(@NonNull Activity activity){
        //record screen size for future reference

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        putInt(activity, WIDTH,displayMetrics.widthPixels);
        putInt(activity, HEIGHT,displayMetrics.heightPixels);
    }

    public static int getWidth(@NonNull Context context){
        int width = getInt(context,WIDTH,0);
        int height = getInt(context,HEIGHT,0);

        return Math.min(width, height);
    }

    public static int getHeight(@NonNull Context context){
        int width = getInt(context,WIDTH,0);
        int height = getInt(context,HEIGHT,0);

        return Math.max(width, height);
    }

    public static void putObject(@NonNull Context context, @NonNull String key,@NonNull Object object){
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        putString(context,key,jsonString);
    }

    public static Object getObject(@NonNull Context context, @NonNull String key, Class c){
        Gson gson = new Gson();
        String jsonString = getString(context,key);
        return gson.fromJson(jsonString,c);
    }
}
