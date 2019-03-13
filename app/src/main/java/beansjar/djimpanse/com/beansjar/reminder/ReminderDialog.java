package beansjar.djimpanse.com.beansjar.reminder;


import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;

import beansjar.djimpanse.com.beansjar.R;

import static beansjar.djimpanse.com.beansjar.util.StringUtils.getTime;


public class ReminderDialog {

    private Context mContext;

    public ReminderDialog(Context context) {
        mContext = context;
    }

    public void show() {
        // Fetch current time to initialize TimePicker
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext,
                (timePicker, selectedHour, selectedMinute) -> startAlarm(selectedHour, selectedMinute), hour, minute,
                true);

        mTimePicker.setTitle("Configure reminder");
        mTimePicker.show();
    }

    private void startAlarm(int hour, int minute) {
        // Show info toast
        Toast.makeText(mContext, mContext.getString(R.string.reminder_notification_set, getTime(hour, minute)),
                Toast.LENGTH_SHORT)
             .show();

        // Set alarm
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        new AlarmManager(mContext).start(calendar);
    }

}
