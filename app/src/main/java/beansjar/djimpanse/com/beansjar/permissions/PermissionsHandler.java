package beansjar.djimpanse.com.beansjar.permissions;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


public class PermissionsHandler {

    public static final int REQUEST_GRANT_PERMISSION = 1337;

    private Activity activity;
    private String permission;

    public PermissionsHandler(Activity activity, String permission) {
        this.activity = activity;
        this.permission = permission;
    }

    public boolean isGranted() {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager
                .PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    // TODO improve request and handle result
    public void request() {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager
                .PERMISSION_GRANTED) {

            // Permission is not granted Should we show an explanation?
            //if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            // else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(activity, new String[]{permission},
                    REQUEST_GRANT_PERMISSION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            //}
        } else {
            // Permission has already been granted
        }
    }

}
