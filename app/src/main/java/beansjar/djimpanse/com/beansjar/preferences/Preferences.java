package beansjar.djimpanse.com.beansjar.preferences;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Preferences {

    /** The class logger. */
    Logger LOGGER = Logger.getLogger(Preferences.class.getSimpleName());

    private static Preferences mInstance;

    private static SharedPreferences mAppPreferences;

    private Preferences(Context context) {
        mAppPreferences = context.getSharedPreferences("BEANSJAR_PREFERENCES", Context.MODE_PRIVATE);
    }

    public static Preferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Preferences(context);
        }
        return mInstance;
    }

    public void save(Preference preference, boolean value) {
        LOGGER.info("Saving boolean '" + value + "' to preference '" + preference.getValue()+ "'");
        mAppPreferences.edit()
                       .putBoolean(preference.getValue(), value)
                       .apply();
    }

    public void save(Preference preference, int value) {
        LOGGER.info("Saving int '" + value + "' to preference '" + preference.getValue()+ "'");
        mAppPreferences.edit()
                       .putInt(preference.getValue(), value)
                       .apply();
    }

    public Boolean getBoolean(Preference preference) {
        LOGGER.info("Saving boolean from preference '" + preference.getValue()+ "'");
        return mAppPreferences.getBoolean(preference.getValue(), false);
    }

    public int getInt(Preference preference) {
        LOGGER.info("Loading int from preference '" + preference.getValue()+ "'");
        return mAppPreferences.getInt(preference.getValue(), 0);
    }

}
