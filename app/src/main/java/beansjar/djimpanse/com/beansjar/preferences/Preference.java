package beansjar.djimpanse.com.beansjar.preferences;


public enum Preference {

    IS_REMINDER_SET("is_reminder_set"),
    REMINDER_HOUR("reminder_hour"),
    REMINDER_MINUTE("reminder_minute");

    private String value;

    Preference(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
