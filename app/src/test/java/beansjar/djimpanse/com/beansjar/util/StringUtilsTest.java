package beansjar.djimpanse.com.beansjar.util;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class StringUtilsTest {

    @Test
    public void isNotEmpty_emptyString() {
       assertFalse(StringUtils.isNotEmpty(""));
    }

    @Test
    public void isNotEmpty_nullString() {
        assertFalse(StringUtils.isNotEmpty(null));
    }

    @Test
    public void isNotEmpty_validString() {
        assertTrue(StringUtils.isNotEmpty("test"));
    }

    @Test
    public void cutIfTooLong_shortString() {
        String result = StringUtils.cutIfTooLong("short string", 20);

        assertEquals("short string", result);
    }

    @Test
    public void cutIfTooLong_longString() {
        String result = StringUtils.cutIfTooLong("This string is too long and will be cut", 20);

        assertEquals("This string is too l...", result);
    }
}
