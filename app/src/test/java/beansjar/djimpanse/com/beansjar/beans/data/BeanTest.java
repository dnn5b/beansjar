package beansjar.djimpanse.com.beansjar.beans.data;


import org.junit.Before;
import org.junit.Test;

import beansjar.djimpanse.com.beansjar.images.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class BeanTest {

    private Bean tut;

    @Before
    public void setup() {
        tut = new Bean();
    }

    @Test
    public void isValid_nullEvent() {
        tut.setEvent(null);

        assertFalse(tut.isValid());
    }

    @Test
    public void isValid_emptyEvent() {
        tut.setEvent("");

        assertFalse(tut.isValid());
    }

    @Test
    public void isValid_noDate() {
        tut.setDate(null);

        assertFalse(tut.isValid());
    }

    @Test
    public void getImage_noPath() {
        String path = "test/path/to/image";
        tut.setImagePath(path);
        tut.image = null;

        assertEquals(path, tut.getImage().getImageAbsolutePath());
    }

    @Test
    public void getImage_exists() {
        Image existingImage = new Image("");
        tut.image = existingImage;

        assertEquals(existingImage, tut.getImage());
    }

}
