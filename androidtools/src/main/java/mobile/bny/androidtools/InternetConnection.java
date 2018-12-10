package mobile.bny.androidtools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by BNY on 04/11/2017.
 */

public class InternetConnection {

    /**
     * This method checks if the user is connected to the internet. Needs permissions!
     * @param context Context: The context to use. Usually your Application or Activity object.
     * @return boolean: true if access to the internet
     */
    @SuppressWarnings("MissingPermission")
    public static boolean isConnected(@NonNull Context context) {
        return  ((ConnectivityManager) context.getSystemService
                (CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
