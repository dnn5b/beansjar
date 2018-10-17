package beansjar.djimpanse.com.beansjar.beans.details;


import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanRatingEnum;
import beansjar.djimpanse.com.beansjar.beans.ratings.RatingIcon;


public class BeanDetailsFragmentTest {

    private BeanDetailsFragment tut;

    @Mock
    private TextView textView;

    @Mock
    private RatingIcon ratingIcon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        tut = new BeanDetailsFragment();
        tut.mBean = new Bean();
    }

    @Test
    public void showEventText() {
        String event = "awesome event";
        tut.mBean.setEvent(event);

        tut.showEventText(textView);

        Mockito.verify(textView).setText(event);
    }

    @Test
    public void showEventTextPassNull() {
        // NPE shouldn't be thrown
        tut.showEventText(null);
    }

    @Test
    public void showDateText() {
        LocalDate date = LocalDate.now();
        tut.mBean.setDate(date);

        tut.showDateText(textView);

        Mockito.verify(textView).setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    @Test
    public void showNullDateText() {
        tut.mBean.setDate(null);

        tut.showDateText(textView);
    }

    @Test
    public void showDateTextPassNull() {
        // NPE shouldn't be thrown
        tut.showDateText(null);
    }

    @Test
    public void showRating() {
        tut.mBean.setRating(BeanRatingEnum.MEDIUM);

        tut.showRating(ratingIcon);

        Mockito.verify(ratingIcon).colorRedOrHide(BeanRatingEnum.MEDIUM);
    }

    @Test
    public void showNullRating() {
        // NPE shouldn't be thrown
        tut.showRating(null);
    }

}
