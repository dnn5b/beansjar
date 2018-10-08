package beansjar.djimpanse.com.beansjar.beans.ratings;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.data.BeanRatingEnum;


public class RatingIcon extends android.support.v7.widget.AppCompatImageView {

    private BeanRatingEnum ratingEnum;

    public RatingIcon(Context context) {
        super(context);
    }

    public RatingIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeRatingEnumWithAttrs(context, attrs);
    }

    public RatingIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeRatingEnumWithAttrs(context, attrs);
    }

    private void initializeRatingEnumWithAttrs(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.RatingIcon);
        Integer xmlDefinedRating = arr.getInteger(R.styleable.RatingIcon_rating, -1);
        switch (xmlDefinedRating) {
            case 1:
                ratingEnum = BeanRatingEnum.LOW;
                break;
            case 2:
                ratingEnum = BeanRatingEnum.MEDIUM;
                break;
            case 3:
                ratingEnum = BeanRatingEnum.HIGH;
                break;
            default:
                throw new IllegalArgumentException("RatingIcon initiliazed with wrong XML rating " +
                        "" + "attribute!");
        }
        arr.recycle();
    }

    /**
     * Passes a {@link BeanRatingEnum} representing an event item. Depending on the
     * {@link #ratingEnum} this view will be colored red or hidden.
     *
     * @param itemRatingEnum the item's rating
     */
    public void colorRedOrHide(BeanRatingEnum itemRatingEnum) {
        boolean apply = itemRatingEnum.getValue() >= ratingEnum.getValue();
        if (apply) {
            this.setVisibility(View.VISIBLE);
            this.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        } else {
            this.setVisibility(View.INVISIBLE);
            this.clearColorFilter();
        }
    }

}
