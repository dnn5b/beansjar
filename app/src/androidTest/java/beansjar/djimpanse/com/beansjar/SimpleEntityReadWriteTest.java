package beansjar.djimpanse.com.beansjar;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanDao;
import beansjar.djimpanse.com.beansjar.beans.data.BeanRatingEnum;
import beansjar.djimpanse.com.beansjar.database.AppDatabase;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private BeanDao mUserDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUserDao = mDb.beanDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeBeanAndReadInList() {
        Bean bean = new Bean();
        bean.setEvent("eventName");
        bean.setRating(BeanRatingEnum.MEDIUM);
        mUserDao.insertAll(bean);

        List<Bean> all = mUserDao.getAll();

        assertEquals(1, all.size());
        assertEquals("eventName", all.get(0).getEvent());
        assertEquals(2, all.get(0).getRating().getValue());
    }
}
