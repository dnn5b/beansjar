package beansjar.djimpanse.com.beansjar.util;


import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class StringUtilsTest {

    @Test
    public void emptyString() {
       assertFalse(StringUtils.isNotEmpty(""));
    }

    @Test
    public void nullString() {
        assertFalse(StringUtils.isNotEmpty(null));
    }

    @Test
    public void validString() {
        assertTrue(StringUtils.isNotEmpty("test"));
    }


}
