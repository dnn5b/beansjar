package beansjar.djimpanse.com.beansjar.reminder;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.util.StringUtil;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.Calendar;
import java.util.logging.Logger;

import beansjar.djimpanse.com.beansjar.BootReceiver;
import beansjar.djimpanse.com.beansjar.preferences.Preference;
import beansjar.djimpanse.com.beansjar.preferences.Preferences;

import static beansjar.djimpanse.com.beansjar.util.StringUtils.getTime;


/**
 * Debug running alarms:
 * --> adb shell "dumpsys alarm | grep com.beansjar"
 */
public class AlarmManager extends BroadcastReceiver {

    /**
     * The class logger.
     */
    private static Logger LOGGER = Logger.getLogger(AlarmManager.class.getSimpleName());

    /**
     * The current App context.
     */
    private Context mContext;

    /**
     * Should be only used to handle broadcasts.
     */
    public AlarmManager() {}

    public AlarmManager(Context context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, Notifications.getReminder(context));
    }

    /**
     * Sets the timer with the passed time. The timer is initialized with setInexactRepeating() improve battery
     * lifetime. The timer data will also be persisted in the LocalStorage so it can be set again after booting the
     * device.
     *
     * @param calendar the repeat time
     */
    public void start(Calendar calendar) {
        if (mContext == null) {
            throw new IllegalStateException("AlarmManager has been initialized without the context!");
        }

        android.app.AlarmManager manager = getAlarmManager();
        PendingIntent pendingIntent = getPendingIntent();
        manager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                android.app.AlarmManager.INTERVAL_DAY, pendingIntent);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        persistTimerData(true, hour, minute);
        enableBootReceiver(true);

        LOGGER.info("Notification timer has been set to " + getTime(hour, minute));
    }

    /**
     * Cancels the current timer. The persisted data in the LocalStorage will also be removed.
     */
    public void stop() {
        if (mContext == null) {
            throw new IllegalStateException("AlarmManager has been initialized without the context!");
        }

        android.app.AlarmManager manager = getAlarmManager();
        PendingIntent pendingIntent = getPendingIntent();
        manager.cancel(pendingIntent);

        persistTimerData(false, 0, 0);
        enableBootReceiver(false);

        LOGGER.info("Notification timer has been canceled!");
    }

    /**
     * Returns the App's current {@link android.app.AlarmManager}.
     *
     * @return {@link android.app.AlarmManager}
     */
    private android.app.AlarmManager getAlarmManager() {
        return (android.app.AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * Creates a {@link PendingIntent} for this AlarmManager.
     *
     * @return {@link PendingIntent}
     */
    private PendingIntent getPendingIntent() {
        return PendingIntent.getBroadcast(mContext, 0, new Intent(mContext, AlarmManager.class), 0);
    }

    /**
     * Persists the passed timer data to the LocalStorage of the App.
     *
     * @param isEnabled true, if the timer is enabled; false if not.
     * @param hour      the repeat hour.
     * @param minute    the repeat minute.
     */
    private void persistTimerData(boolean isEnabled, int hour, int minute) {
        // En/Disable
        Preferences.getInstance(mContext)
                   .save(Preference.IS_REMINDER_SET, isEnabled);

        // Time
        Preferences.getInstance(mContext)
                   .save(Preference.REMINDER_HOUR, hour);
        Preferences.getInstance(mContext)
                   .save(Preference.REMINDER_MINUTE, minute);
    }

    /**
     * En/Disables the {@link BootReceiver}.
     *
     * @param enable true, if the {@link BootReceiver} should be enabled; false if not.
     */
    private void enableBootReceiver(boolean enable) {
        LOGGER.info("BootReceiver is " + (enable ? "enabled" : "disabled"));

        ComponentName receiver = new ComponentName(mContext, BootReceiver.class);
        PackageManager pm = mContext.getPackageManager();

        int receiverMode = enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        pm.setComponentEnabledSetting(receiver, receiverMode, PackageManager.DONT_KILL_APP);
    }

}