package beansjar.djimpanse.com.beansjar.images;


import android.media.ExifInterface;

import java.io.IOException;


/**
 * This class handles the EXIF attributes on images.
 */
public class ExifHandler {

    private String mInputPath;
    private String mOutputPath;

    public ExifHandler(String inputPath, String outputPath) {
        this.mInputPath = inputPath;
        this.mOutputPath = outputPath;
    }

    public void copyOrientationTag() throws IOException {
        ExifInterface oldExif = new ExifInterface(mInputPath);
        String exifOrientation = oldExif.getAttribute(ExifInterface.TAG_ORIENTATION);

        if (exifOrientation != null) {
            ExifInterface newExif = new ExifInterface(mOutputPath);
            newExif.setAttribute(ExifInterface.TAG_ORIENTATION, exifOrientation);
            newExif.saveAttributes();
        }
    }



}
