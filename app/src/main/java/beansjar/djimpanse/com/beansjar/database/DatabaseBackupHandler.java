package beansjar.djimpanse.com.beansjar.database;


import android.Manifest;
import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import beansjar.djimpanse.com.beansjar.permissions.PermissionsHandler;


public class DatabaseBackupHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("_yyyyMMdd_HHmmss");
    private static final String BACKUP_FILENAME = "beans_backup";

    private final Activity mActivity;

    public DatabaseBackupHandler(Activity activity) {
        this.mActivity = activity;
    }

    public boolean exportBackup() {
        PermissionsHandler permissionsHandler = new PermissionsHandler(mActivity, Manifest.permission
                .WRITE_EXTERNAL_STORAGE);
        if (!permissionsHandler.isGranted()) {
            // Stop backup and request permission
            permissionsHandler.request();
            return false;

        } else {
            AppDatabase dbInstance = AppDatabase.getInstance(mActivity);
            dbInstance.close();

            File dbFile = dbInstance.getDataBasePath(mActivity);
            File saveDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
            String savePath = saveDir.getPath() + File.separator + BACKUP_FILENAME + LocalDateTime.now()
                                                                                                  .format(FORMATTER);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            File savefile = new File(savePath);
            try {
                savefile.createNewFile();
                int buffersize = 8 * 1024;
                byte[] buffer = new byte[buffersize];
                int bytes_read = buffersize;
                OutputStream savedb = new FileOutputStream(savePath);
                InputStream indb = new FileInputStream(dbFile);
                while ((bytes_read = indb.read(buffer, 0, buffersize)) > 0) {
                    savedb.write(buffer, 0, bytes_read);
                }
                savedb.flush();
                indb.close();
                savedb.close();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public boolean importBackup() {
        // TODO
        return false;
    }

}