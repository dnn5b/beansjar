package beansjar.djimpanse.com.beansjar.beans.data;


/**
 * Created by Dennis Jonietz on 04.10.2018.
 */
public enum BeanRatingEnum {

    LOW(1),

    MEDIUM(2),

    HIGH(3);

    private final int rating;

    BeanRatingEnum(int rating) {
        this.rating = rating;
    }

    public int getValue() {
        return this.rating;
    }
}
