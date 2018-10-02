package beansjar.djimpanse.com.beansjar.beans.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static beansjar.djimpanse.com.beansjar.beans.data.Bean.TABLE_NAME;


@Entity(tableName = TABLE_NAME)
public class Bean {

    /** The table name of this entity. */
    public static final String TABLE_NAME = "beans";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "event")
    private String event;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }

}
