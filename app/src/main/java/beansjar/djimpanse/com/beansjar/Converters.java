package beansjar.djimpanse.com.beansjar;


import android.arch.persistence.room.TypeConverter;

import java.time.LocalDate;

import beansjar.djimpanse.com.beansjar.beans.data.BeanRatingEnum;


public class Converters {

    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }

    @TypeConverter
    public static int ratingToInt(BeanRatingEnum ratingEnum) {
        return ratingEnum == null ? null : ratingEnum.getValue();
    }

    @TypeConverter
    public static BeanRatingEnum intToRating(Integer rating) {
        if (rating == null) {
            return null;
        }

        switch(rating) {
            case 0:
                return BeanRatingEnum.NO;
            case 1:
                return BeanRatingEnum.LOW;
            case 2:
                return BeanRatingEnum.MEDIUM;
            case 3:
                return BeanRatingEnum.HIGH;
            default:
                throw new IllegalArgumentException("Illegal input in stringToRating() converter: " + rating);
        }
    }

}
