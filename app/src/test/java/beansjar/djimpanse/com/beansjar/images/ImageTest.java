package beansjar.djimpanse.com.beansjar.images;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ImageTest {

    private Image tut;

    @Test
    public void getFilePath() {
        String inputUrl = "some.random.url";
        tut = new Image(inputUrl);

        String result = tut.getFilePath();

        String expected = "file:///" + inputUrl;
        assertEquals(expected, result);
    }

    @Test
    public void delete_noImage() {
        tut = new Image("");

        try {
            tut.delete();
            fail("Exception expected!");
        } catch (IllegalStateException e) {
            assertEquals("Delete: image not available!", e.getMessage());
        }
    }

    @Test
    public void delete_nullImage() {
        tut = new Image("");
        tut.imageAbsolutePath = null;

        // Nothing should happen and NPE should be prevented
        tut.delete();
    }

}
