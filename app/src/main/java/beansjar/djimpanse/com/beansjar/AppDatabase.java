package beansjar.djimpanse.com.beansjar;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanDao;


@Database(entities = {Bean.class}, version = 1)
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
            sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
        }
        return sInstance;
    }
}
