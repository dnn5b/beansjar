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
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public void request() {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_GRANT_PERMISSION);
        }
    }

}
