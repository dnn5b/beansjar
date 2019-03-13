package beansjar.djimpanse.com.beansjar.util;


/**
 * Created by Dennis Jonietz on 05.10.2018.
 */
public class StringUtils {

    public static boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public static String cutIfTooLong(String string, int maxLength) {
        return string.length() > maxLength ? string.substring(0, maxLength) + "..." : string;
    }

    /**
     * Returns the passed hour and minute in format "hh:mm".
     *
     * @param hour   the hour
     * @param minute the minute
     * @return Time in format hh:mm
     */
    public static String getTime(int hour, int minute) {
        StringBuilder time = new StringBuilder();
        time.append(String.format("%02d", hour))
            .append(":")
            .append(String.format("%02d", minute));
        return time.toString();
    }
}
