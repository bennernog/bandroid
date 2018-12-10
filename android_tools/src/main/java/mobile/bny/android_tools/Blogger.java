package mobile.bny.android_tools;

import android.support.annotation.NonNull;
import android.util.Log;

import mobile.bny.javatools.StringUtils;

/**
 * Created by BNY on 04/11/2017.
 */

public class Blogger {

    public static void debug(@NonNull Class c, String message){
        debug(c,"%s",message);
    }

    public static void debug(@NonNull Class c, Object... objects) {
        debug(c, StringUtils.simpleFormat(objects));
    }

    public static void debug(@NonNull Class c, @NonNull String format, Object... objects){
        Log.println(Log.DEBUG,c.getSimpleName(),String.format(format, objects));
    }

    public static void verbose(@NonNull Class c, String message){
        verbose(c,"%s",message);
    }

    public static void verbose(@NonNull Class c, Object... objects) {
        verbose(c, StringUtils.simpleFormat(objects));
    }

    public static void verbose(@NonNull Class c, @NonNull String format, Object... objects){
        Log.println(Log.VERBOSE,c.getSimpleName(),String.format(format, objects));
    }

    public static void error(@NonNull Class c, String message){
        error(c,"%s",message);
    }

    public static void error(@NonNull Class c, Object... objects) {
        error(c, StringUtils.simpleFormat(objects));
    }

    public static void error(@NonNull Class c, @NonNull  String format, Object... objects){
        Log.println(Log.ERROR,c.getSimpleName(),String.format(format, objects));
    }

    public static void info(@NonNull Class c, String message){
        info(c,"%s",message);
    }

    public static void info(@NonNull Class c, Object... objects) {
        info(c, StringUtils.simpleFormat(objects));
    }

    public static void info(@NonNull Class c, @NonNull  String format, Object... objects){
        Log.println(Log.INFO,c.getSimpleName(),String.format(format, objects));
    }

    public static void warn(@NonNull Class c, String message){
        warn(c,"%s",message);
    }

    public static void warn(@NonNull Class c, Object... objects) {
        warn(c, StringUtils.simpleFormat(objects));
    }

    public static void warn(@NonNull Class c, @NonNull  String format, Object... objects){
        Log.println(Log.WARN,c.getSimpleName(),String.format(format, objects));
    }

    public static void logcat(@NonNull Class c, String message){
        logcat(c,"%s",message);
    }

    public static void logcat(@NonNull Class c, Object... objects) {
        logcat(c, StringUtils.simpleFormat(objects));
    }

    public static void logcat(@NonNull Class c, @NonNull  String format, Object... objects){
        Log.println(Log.DEBUG,"_______________________","___________________________________________________________");
        Log.println(Log.DEBUG,c.getSimpleName(),String.format(format, objects));
        Log.println(Log.DEBUG,"_______________________","___________________________________________________________");
    }
}
