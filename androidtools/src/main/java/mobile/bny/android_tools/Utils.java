package mobile.bny.android_tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;

import java.util.Calendar;

/**
 * Created by BNY on 12/11/2017.
 */

public class Utils {

    /**
     * This method checks if your screen configuration is of large (or larger) type
     *
     * @param context Context to get resources and device specific display metrics
     *
     * @return A boolean, true if screen is >= large
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    /**
     * Generates a random unique integer
     * @return an int value
     */
    public static int randomId(){

        long l = Calendar.getInstance().getTimeInMillis();
        String ls = Long.toString(l);

        if(ls.length() >= 10) {
            int start = ls.length() - 10;
            ls = ls.substring(start);
        }

        if(Long.parseLong(ls) >= Integer.MAX_VALUE)
            ls = ls.substring(1);

        return  Integer.valueOf(ls);
    }

    /**
     * Generates a random unique integer based on a String
     * @return an int value
     */
    public static int randomId(String string){

        long l = string.hashCode() + Calendar.getInstance().getTimeInMillis();
        String ls = Long.toString(l);

        if(ls.length() >= 10) {
            int start = ls.length() - 10;
            ls = ls.substring(start);
        }

        if(Long.parseLong(ls) >= Integer.MAX_VALUE)
            ls = ls.substring(1);

        return  Integer.valueOf(ls);
    }

    /**
     *
     * @param context Context: The context to use. Usually your Application or Activity object.
     * @param resourceId int: id for the resource drawable' R.drawable.my_drawable_resource
     * @return Uri: for the drawable resoure
     */
    public static Uri resourceIdToUri(Context context, int resourceId) {

        return Uri.parse(String.format(
                "android.resource://%s/%s",
                context.getPackageName(),
                resourceId));
    }

    /**
     * THis method converts an amount of milliseconds in days
     *
     * @param millis long: the amount of milliseconds to be converted
     * @return float: amount of days
     */
    public static float millisToDays(long millis){
        return millis / (1000 * 60 * 60 * 24f);
//        return TimeUnit.MILLISECONDS.toDays(millis);
    }

    /**
     * THis method converts an amount of milliseconds in weeks
     *
     * @param millis long: the amount of milliseconds to be converted
     * @return float: amount of weeks
     */
    public static float millisToWeeks(long millis){
        return millisToDays(millis) / 7;
    }

    /**
     * THis method converts an amount of milliseconds in months
     *
     * @param millis long: the amount of milliseconds to be converted
     * @return float: amount of months
     */
    public static float millisToMonths(long millis){
        return  (float) ( millisToDays(millis) * (12 / 365.25) );
    }

    /**
     * This method converts an amount of millis, usually from System.currentTimeMillis() and gives
     * a string represenation of the date
     *
     * @param millis long: the amount of milliseconds to be converted
     * @return String: the date as DD/MM/YYYY
     */
    public static String getDateString(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return String.format("%s/%s/%s",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) +1,
                calendar.get(Calendar.YEAR));
    }

    public static double round(double d, int digits){
        double dig = Math.pow(10,digits);
        return Math.round(d * dig)/dig;
    }

    public long getRandomDate () {
        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        Calendar randomDate = (Calendar) now.clone();
        int minYear = 2018;
        int minMonth = 1;
        int minDay = 1;
        min.set(minYear, minMonth-1, minDay);
        int numberOfDaysToAdd = (int) (Math.random() * (daysBetween(min, now) + 1));
        randomDate.add(Calendar.DAY_OF_YEAR, -numberOfDaysToAdd);
        return randomDate.getTimeInMillis();
    }

    public static int daysBetween(Calendar from, Calendar to) {
        Calendar date = (Calendar) from.clone();
        int daysBetween = 0;
        while (date.before(to)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }

        return daysBetween;
    }


    /**
     * This method starts a new activity given the targetclass
     *  @param source Activity: the source activity, usually MyActivity.this
     * @param target Class: the target activity class, usually TargetActivity.class
     */
    public static void startNextActivity(Activity source, Class target) {
        startNextActivity(source, target, false);
    }

    /**
     * This method starts a new activity given the targetclass
     *
     * @param source Activity: the source activity, usually MyActivity.this
     * @param target Class: the target activity class, usually TargetActivity.class
     * @param finish boolean: true to finish() the source activity
     */
    public static void startNextActivity(Activity source, Class target, boolean finish){
        Intent i = new Intent(source, target);
        source.startActivity(i);
        if(finish)
            source.finish();
    }
}
