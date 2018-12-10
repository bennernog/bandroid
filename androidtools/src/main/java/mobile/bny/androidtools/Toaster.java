package mobile.bny.androidtools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * Created by BNY on 04/11/2017.
 */

public class Toaster {

    private static void showToast(Context context, int resId, int length){
        Toast.makeText(context, resId, length).show();
    }

    private static void showToast(Context context, String text, int length){
        Toast.makeText(context, text, length).show();
    }

    /**
     * This method shows a Toast with duration set to Toast.LENGTH_LONG.
     *
     * @param context Context: The context to use. Usually your Application or Activity object.
     * @param text String: The text to show.
     */
    public static void longToast(@NonNull Context context, String text){
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * This method shows a Toast with duration set to Toast.LENGTH_LONG.
     *
     * @param context Context: The context to use. Usually your Application or Activity object.
     * @param resId int: int: The resource id of the string resource to use.
     */
    public static void longToast(@NonNull Context context, int resId){
        showToast(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * This method shows a Toast with duration set to Toast.LENGTH_LONG.
     *
     * @param context Context: The context to use. Usually your Application or Activity object.
     * @param format String: A format string.
     * @param arguments Object[]: Arguments referenced by the format specifiers in the format string. If there are more arguments than format specifiers, the extra arguments are ignored. The number of arguments is variable and may be zero. The maximum number of arguments is limited by the maximum dimension of a Java array as defined by The Java™ Virtual Machine Specification. The behaviour on a null argument depends on the conversion.
     */
    public static void longToast(@NonNull Context context,@NonNull  String format, Object... arguments){
        longToast(context, String.format(format, arguments));
    }

    /**
     * This method shows a Toast with duration set to Toast.LENGTH_SHORT.
     *
     * @param context Context: The context to use. Usually your Application or Activity object.
     * @param text String: The text to show.
     */
    public static void shortToast(@NonNull Context context, String text){
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * This method shows a Toast with duration set to Toast.LENGTH_SHORT.
     *
     * @param context Context: The context to use. Usually your Application or Activity object.
     * @param resId int: int: The resource id of the string resource to use.
     */
    public static void shortToast(@NonNull Context context, int resId){
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * This method shows a Toast with duration set to Toast.LENGTH_SHORT.
     *
     * @param context Context: The context to use. Usually your Application or Activity object.
     * @param format String: A format string.
     * @param arguments Object[]: Arguments referenced by the format specifiers in the format string. If there are more arguments than format specifiers, the extra arguments are ignored. The number of arguments is variable and may be zero. The maximum number of arguments is limited by the maximum dimension of a Java array as defined by The Java™ Virtual Machine Specification. The behaviour on a null argument depends on the conversion.
     */
    public static void shortToast(@NonNull Context context,@NonNull  String format, Object... arguments){
        shortToast(context, String.format(format, arguments));
    }

    /**
     * Convenience method to show the text "Coming Soon". To be used in development, to show functionality before implementation.
     * @param context Context: The context to use. Usually your Application or Activity object.
     */
    public static void comingSoon(@NonNull Context context){
        shortToast(context,"Coming Soon");
    }
}
