package beansjar.djimpanse.com.beansjar.beans.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

import beansjar.djimpanse.com.beansjar.images.Image;
import beansjar.djimpanse.com.beansjar.util.StringUtils;

import static beansjar.djimpanse.com.beansjar.beans.data.Bean.TABLE_NAME;


@Entity(tableName = TABLE_NAME)
public class Bean implements Parcelable {

    /**
     * The table name of this entity.
     */
    public static final String TABLE_NAME = "beans";

    /**
     * #
     * Initializes by default with
     * {@link beansjar.djimpanse.com.beansjar.beans.data.BeanRatingEnum}.
     */
    public Bean() {
        this.rating = BeanRatingEnum.NO;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "event")
    private String event;

    @ColumnInfo(name = "rating")
    private BeanRatingEnum rating;

    @ColumnInfo(name = "date")
    private LocalDate date;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    @Ignore
    private Image image;

    protected Bean(Parcel in) {
        id = in.readInt();
        event = in.readString();
    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image getImage() {
        if (this.image == null) {
            this.image = new Image(imagePath);
        }
        return this.image;
    }

    public boolean isValid() {
        return StringUtils.isNotEmpty(event) && date != null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(event);
        dest.writeLong(date.toEpochDay());
    }

    public static final Creator<Bean> CREATOR = new Creator<Bean>() {
        @Override
        public Bean createFromParcel(Parcel in) {
            return new Bean(in);
        }

        @Override
        public Bean[] newArray(int size) {
            return new Bean[size];
        }
    };

}
