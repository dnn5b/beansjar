package beansjar.djimpanse.com.beansjar.images;


import org.junit.Test;

import static org.junit.Assert.assertEquals;


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

}
