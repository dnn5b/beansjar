package beansjar.djimpanse.com.beansjar.preferences;


public enum Preference {

    IS_REMINDER_SET("is_reminder_set");

    private String value;

    Preference(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
