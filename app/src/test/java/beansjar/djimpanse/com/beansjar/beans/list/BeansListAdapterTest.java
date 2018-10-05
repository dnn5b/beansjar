package beansjar.djimpanse.com.beansjar.beans.list;


import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import beansjar.djimpanse.com.beansjar.beans.data.Bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BeansListAdapterTest {

    private List<Bean> originalList;
    private BeansListAdapter tut;

    @Before
    public void setup() {
        originalList = new ArrayList<>();
        tut = new BeansListAdapter();
    }

    @Test
    public void buildListWithOneDate() {
        mockOriginalBean(2018, 10, 05, "event1");
        mockOriginalBean(2018, 10, 05, "event2");
        mockOriginalBean(2018, 10, 05, "event3");

        tut.buildListWithHeadings(originalList);

        assertEquals(4, tut.mDataset.size());
        assertTrue(tut.mDataset.get(0).isHeader());
    }

    @Test
    public void buildListWithMultipleDate() {
        mockOriginalBean(2018, 10, 05, "event1");
        mockOriginalBean(2018, 12, 05, "event2");
        mockOriginalBean(2019, 10, 05, "event3");

        tut.buildListWithHeadings(originalList);

        assertEquals(6, tut.mDataset.size());
    }

    @Test
    public void buildListWithMultipleDateUnsorted() {
        mockOriginalBean(2019, 10, 05, "event3");
        mockOriginalBean(2018, 10, 05, "event1");
        mockOriginalBean(2018, 12, 05, "event2");
        mockOriginalBean(2022, 1, 05, "event2");


        tut.buildListWithHeadings(originalList);

        assertEquals(8, tut.mDataset.size());
    }

    @Test
    public void buildListWithEmptyList() {
        tut.buildListWithHeadings(originalList);

        assertEquals(0, tut.mDataset.size());
    }

    @Test
    public void buildListAndPassNull() {
        tut.buildListWithHeadings(null);

        assertEquals(0, tut.mDataset.size());
    }

    @Test
    public void buildListAndReset() {
        tut.mDataset.add(new Bean());

        tut.buildListWithHeadings(originalList);

        assertEquals(0, tut.mDataset.size());
    }

    private void mockOriginalBean(int year, int month, int day, String event) {
        Bean bean = new Bean();
        bean.setEvent(event);
        bean.setDate(LocalDate.of(year, month, day));
        originalList.add(bean);
    }

}
