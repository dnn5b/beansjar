package beansjar.djimpanse.com.beansjar.images;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Holds the data of a Bean's image.
 */
public class Image {

    private static final String DATA_TYPE = ".png";

    private final String imageName = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            + DATA_TYPE;

    /** The absolute path to the file (including name and file ending). */
    private String imageAbsolutePath;

    /** The URI of the input file. Used when loading the image to the internal storage {@link #saveToInternalStorage(Activity)}. */
    private Uri inputUri;

    public Image(Uri inputUri) {
        this.inputUri = inputUri;
    }

    public Image(String inputImagePath) {
        this.imageAbsolutePath = inputImagePath;
    }

    public Bitmap getBitmap() {
        File imgFile = new File(imageAbsolutePath);
        if (!imgFile.exists()) {
            throw new IllegalStateException("Loading: image not available!");
        }
        return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
    }

    public String saveToInternalStorage(Activity activity) {
        ContextWrapper cw = new ContextWrapper(activity);

        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        // Create imageDir
        File mypath = new File(directory, imageName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(mypath);

            // Load the image from the external storage
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),
                    inputUri);

            // Compress the BitMap object and write it to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            this.imageAbsolutePath = mypath.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageAbsolutePath;
    }

    public boolean delete() {
        File imgFile = new File(imageAbsolutePath);
        if (!imgFile.exists()) {
            throw new IllegalStateException("Delete: image not available!");
        }
        return imgFile.delete();
    }

}
