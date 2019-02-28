package beansjar.djimpanse.com.beansjar;


import android.app.Application;

import beansjar.djimpanse.com.beansjar.reminder.Notifications;


public class BeansApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // Create notification channel on startup
        Notifications.createChannel(this);
    }
}
