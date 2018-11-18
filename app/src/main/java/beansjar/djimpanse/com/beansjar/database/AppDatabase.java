package beansjar.djimpanse.com.beansjar.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import java.io.File;

import beansjar.djimpanse.com.beansjar.Converters;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanDao;


@Database(entities = {Bean.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "database_name_beansjar";

    public abstract BeanDao beanDao();

    /**
     * The only instance.
     */
    private static AppDatabase sInstance;

    /**
     * Singleton getter for {@link #sInstance}.
     *
     * @return the singleton instance of the database
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return sInstance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE " + Bean.TABLE_NAME + " ADD COLUMN image_path TEXT");
        }
    };

    public File getDataBasePath(Context context) {
        return context.getDatabasePath(DATABASE_NAME);
    }

}
