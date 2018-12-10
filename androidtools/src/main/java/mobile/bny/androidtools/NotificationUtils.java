package mobile.bny.androidtools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import java.util.Calendar;

import mobile.bny.javatools.StringUtils;

public class NotificationUtils {

    private static final String channel_id ="bandroid_channel_id";
    private static final String Name = "bandroid_channel_name";
    private static final String Description = "bandroid_channel_description";


    String channelId, name,description;

    public NotificationUtils() {
        this(channel_id);
    }

    public NotificationUtils(String channelId) {
        this(channelId, Name);
    }

    public NotificationUtils(String channelId, String name) {
        this(channelId, name, Description);
    }

    public NotificationUtils(String channelId, String name, String description) {
        this.channelId = channelId;
        this.name = name;
        this.description = description;
    }

    public void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    public int createNotificationId(String ref){
        if(StringUtils.isNullOrEmpty(ref))
            ref = channelId;

        int id = 0;
        long l = ref.hashCode() + Calendar.getInstance().getTimeInMillis();
        String ls = Long.toString(l);
        if(ls.length() >= 10) {
            int start = ls.length() - 10;
            ls = ls.substring(start);
        }

        if(Long.parseLong(ls) >= Integer.MAX_VALUE)
            ls = ls.substring(1);

        return  Integer.valueOf(ls);
    }
}
