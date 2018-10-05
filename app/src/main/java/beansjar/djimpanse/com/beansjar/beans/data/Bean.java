package beansjar.djimpanse.com.beansjar.beans.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.time.LocalDate;

import beansjar.djimpanse.com.beansjar.util.StringUtils;

import static beansjar.djimpanse.com.beansjar.beans.data.Bean.TABLE_NAME;


@Entity(tableName = TABLE_NAME)
public class Bean {

    /**
     * The table name of this entity.
     */
    public static final String TABLE_NAME = "beans";

    public Bean() {

    }

    public Bean(LocalDate date, boolean isHeader) {
        this.date = date;
        this.isHeader = isHeader;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "event")
    private String event;

    @ColumnInfo(name = "rating")
    private BeanRatingEnum rating;

    @ColumnInfo(name = "date")
    private LocalDate date;

    @Ignore
    private boolean isHeader;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BeanRatingEnum getRating() {
        return rating;
    }

    public void setRating(BeanRatingEnum rating) {
        this.rating = rating;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isValid() {
        return StringUtils.isNotEmpty(event) && rating != null && date != null;
    }
}
