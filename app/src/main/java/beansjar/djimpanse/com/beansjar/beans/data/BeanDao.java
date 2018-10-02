package beansjar.djimpanse.com.beansjar.beans.data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface BeanDao {

    @Query("SELECT * from " + Bean.TABLE_NAME)
    List<Bean> getAll();

    @Insert
    void insertAll(Bean... beans);

    @Delete
    void delete(Bean bean);

    @Query("SELECT COUNT(*) FROM " + Bean.TABLE_NAME)
    int count();
}
