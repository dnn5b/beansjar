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
}
