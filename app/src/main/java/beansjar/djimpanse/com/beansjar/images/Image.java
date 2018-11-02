package beansjar.djimpanse.com.beansjar.images;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.permissions.PermissionsHandler;


/**
 * Holds the data of a Bean's image.
 */
public class Image {

    private static final String IMG_FOLDER_NAME = "beans_images";
    private static final String DATA_TYPE = "." + Bitmap.CompressFormat.JPEG.toString();

    private final String imageName = LocalDateTime.now().format(DateTimeFormatter
            .ISO_LOCAL_DATE_TIME) + DATA_TYPE;

    /**
     * The absolute path to the file (including name and file ending).
     */
    protected String imageAbsolutePath;

    /**
     * The URI of the input file. Used when loading the image to the internal storage
     * {@link #saveToInternalStorage(Activity)}.
     */
    private Uri inputUri;

    public Image(Uri inputUri) {
        this.inputUri = inputUri;
    }

    public Image(String inputImagePath) {
        this.imageAbsolutePath = inputImagePath;
    }

    public String getImageAbsolutePath() {
        return imageAbsolutePath;
    }

    public String saveToInternalStorage(Activity activity) {
        ContextWrapper cw = new ContextWrapper(activity);

        // path to /data/data/beansjar.djimpanse.com.beansjar/app_data/beans_images
        File directory = cw.getDir(IMG_FOLDER_NAME, Context.MODE_PRIVATE);

        // Create imageDir
        File path = new File(directory, imageName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);

            // Load the image from the external storage
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),
                    inputUri);

            // Compress the BitMap object and write it to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            this.imageAbsolutePath = path.getAbsolutePath();

            // Try to copy EXIF attributes to new image
            try {
                new ExifHandler(getRealPathFromURI(activity, inputUri), imageAbsolutePath)
                        .copyOrientationTag();
            } catch (IOException ioException) {
                Toast.makeText(activity, R.string.beans_create_fragment_exif_copy_fail, Toast
                        .LENGTH_SHORT).show();
            }

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
        // If the bean has no image the path will be null
        boolean result = true;

        if (imageAbsolutePath != null) {
            File imgFile = new File(imageAbsolutePath);
            if (!imgFile.exists()) {
                throw new IllegalStateException("Delete: image not available!");
            }
            result = imgFile.delete();
        }

        return result;
    }

    /**
     * Returns {@link #imageAbsolutePath} appended by 'file:///'.
     *
     * @return the file path
     */
    protected String getFilePath() {
        return "file:///" + imageAbsolutePath;
    }

    /**
     * Uses the {@link Picasso} library to load this image into the passed ImageView.
     *
     * @param imageView the ImageView where this image should be shown
     */
    public void loadIntoImageView(ImageView imageView) {
        Picasso.get().load(getFilePath()).placeholder(R.color.colorPrimaryDark).into(imageView);
    }

    /**
     * Uses the {@link Picasso} library to load this image into the passed ImageView. The image
     * will be resized to the passed values and center cropped into the ImageView.
     *
     * @param imageView the ImageView where this image should be shown
     * @param width     the desired width
     * @param height    the desired height
     */
    public void loadIntoImageView(ImageView imageView, int width, int height) {
        Picasso.get().load(getFilePath()).resize(width, height).centerCrop().into(imageView);
    }

    /**
     * Gets the system path for the passed URI.
     *
     * @param context    the calling context
     * @param contentURI the file's URI
     * @return the system path to the file
     */
    private String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}