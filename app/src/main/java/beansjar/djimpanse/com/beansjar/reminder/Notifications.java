package beansjar.djimpanse.com.beansjar.reminder;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.OverviewActivity;

import static android.app.PendingIntent.FLAG_ONE_SHOT;


public class Notifications {

    public static String NOTIFICATION_CHANNEL_ID = "beansjar_notification_channel_id";

    public static void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.reminder_notification_channel_name);
            String description = context.getString(R.string.reminder_notification_channel_desc);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static Notification getReminder(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                Notifications.NOTIFICATION_CHANNEL_ID);

        Intent myIntent = new Intent(context, OverviewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, FLAG_ONE_SHOT);

        builder.setAutoCancel(true)
               .setDefaults(Notification.DEFAULT_ALL)
               .setWhen(System.currentTimeMillis())
               .setSmallIcon(R.mipmap.ic_launcher)
               .setContentTitle(context.getString(R.string.reminder_notification_title))
               .setContentIntent(pendingIntent)
               .setContentText(context.getString(R.string.reminder_notification_desc))
               .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        return builder.build();
    }
}
