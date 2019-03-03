package beansjar.djimpanse.com.beansjar.preferences;


import android.content.Context;
import android.content.SharedPreferences;


public class Preferences {

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

    public void saveBoolean(Preference preference, boolean value) {
        mAppPreferences.edit()
                       .putBoolean(preference.getValue(), value)
                       .apply();
    }

    public Boolean getBoolean(Preference preference) {
        return mAppPreferences.getBoolean(preference.getValue(), false);
    }

}
