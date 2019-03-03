package beansjar.djimpanse.com.beansjar.reminder;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import beansjar.djimpanse.com.beansjar.preferences.Preference;
import beansjar.djimpanse.com.beansjar.preferences.Preferences;

/**
 * Debug running alarms:
 * --> adb shell "dumpsys alarm | grep com.beansjar"
 */
public class AlarmManager extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, Notifications.getReminder(context));
    }

    public static void start(Context context, Calendar calendar) {
        android.app.AlarmManager manager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmManager.class),
                0);

        // Timer doesn't have to be exact (improve battery lifetime)
        manager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                android.app.AlarmManager.INTERVAL_DAY, pendingIntent);

        // Persist configured alarm
        Preferences.getInstance(context)
                   .saveBoolean(Preference.IS_REMINDER_SET, true);
    }

    public static void stop(Context context) {
        android.app.AlarmManager manager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmManager.class),
                0);

        manager.cancel(pendingIntent);

        Preferences.getInstance(context)
                   .saveBoolean(Preference.IS_REMINDER_SET, false);
    }

}