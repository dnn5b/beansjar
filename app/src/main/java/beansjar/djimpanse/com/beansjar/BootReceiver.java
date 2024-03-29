package beansjar.djimpanse.com.beansjar;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.logging.Logger;

import beansjar.djimpanse.com.beansjar.preferences.Preference;
import beansjar.djimpanse.com.beansjar.preferences.Preferences;
import beansjar.djimpanse.com.beansjar.reminder.AlarmManager;
import beansjar.djimpanse.com.beansjar.util.StringUtils;


/**
 * Handles boot broadcasts and sets the notification timer again.
 */
public class BootReceiver extends BroadcastReceiver {

    /**
     * The class logger.
     */
    Logger LOGGER = Logger.getLogger(BootReceiver.class.getSimpleName());

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()
                  .equals("android.intent.action.BOOT_COMPLETED")) {
            // Load timer data from LocalStorage
            Preferences preferences = Preferences.getInstance(context);
            Boolean reminderEnabled = preferences.getBoolean(Preference.IS_REMINDER_SET);
            LOGGER.info("'BOOT_COMPLETED' received. The reminder notification is currently " + (reminderEnabled ?
                    "enabled" : "disabled"));
            if (reminderEnabled) {
                Calendar repeatTimer = Calendar.getInstance();
                repeatTimer.set(Calendar.HOUR_OF_DAY, preferences.getInt(Preference.REMINDER_HOUR));
                repeatTimer.set(Calendar.MINUTE, preferences.getInt(Preference.REMINDER_MINUTE));

                LOGGER.info("This time was loaded from DB: " + StringUtils.getTime(preferences.getInt(Preference.REMINDER_HOUR), preferences.getInt(Preference.REMINDER_MINUTE)));
                new AlarmManager(context).start(repeatTimer);
            }
        }
    }
}
