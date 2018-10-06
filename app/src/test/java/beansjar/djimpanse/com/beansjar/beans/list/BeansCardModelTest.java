package beansjar.djimpanse.com.beansjar.beans.list;


import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import beansjar.djimpanse.com.beansjar.beans.data.Bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BeansCardModelTest {

    @Test
    public void createModelsWithOneDate() {
        List<Bean> beansList = new ArrayList<>();
        beansList.addAll(mockBeans(3, 2018, 10, 5, "event_"));

        List<BeansCardModel> result = BeansCardModel.createModels(beansList);

        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getBeans().size());
        assertEquals(beansList.get(0).getDate(), result.get(0).getDate());
    }

    @Test
    public void createModelsWithMultipleDates() {
        List<Bean> beansList = new ArrayList<>();
        beansList.addAll(mockBeans(3, 2018, 10, 5, "event_"));
        beansList.addAll(mockBeans(4, 2020, 2, 25, "event2_"));

        List<BeansCardModel> result = BeansCardModel.createModels(beansList);

        assertEquals(2, result.size());
        assertEquals(3, result.get(0).getBeans().size());
        assertEquals(beansList.get(0).getDate(), result.get(0).getDate());

        assertEquals(4, result.get(1).getBeans().size());
        assertEquals(beansList.get(5).getDate(), result.get(1).getDate());
    }

    @Test
    public void createModelsWithMultipleDatesUnsorted() {
        List<Bean> beansList = new ArrayList<>();
        beansList.addAll(mockBeans(4, 2020, 2, 25, "event2_"));
        beansList.addAll(mockBeans(3, 2018, 10, 5, "event_"));

        List<BeansCardModel> result = BeansCardModel.createModels(beansList);

        assertEquals(2, result.size());
        assertTrue(result.get(0).getDate().isBefore(result.get(1).getDate()));
    }

    @Test
    public void createModelsWith2DifferentDates() {
        List<Bean> beansList = new ArrayList<>();
        beansList.addAll(mockBeans(1, 2018, 10, 5, "event_"));
        beansList.addAll(mockBeans(1, 2018, 11, 6, "event_2"));

        List<BeansCardModel> result = BeansCardModel.createModels(beansList);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getBeans().size());
        assertEquals(1, result.get(1).getBeans().size());
    }

    @Test
    public void createModelsWithEmptyList() {
        List<Bean> beansList = new ArrayList<>();

        List<BeansCardModel> result = BeansCardModel.createModels(beansList);

        assertEquals(0, result.size());
    }

    @Test
    public void createModelsAndPassNull() {
        List<BeansCardModel> result = BeansCardModel.createModels(null);

        assertEquals(0, result.size());
    }

    /**
     * Creates a list of {@link Bean}s with the passed data and the passed number of elements.
     */
    private List<Bean> mockBeans(int num, int year, int month, int day, String event) {
        List<Bean> list = new ArrayList<>();
        IntStream.range(0, num).forEach(i -> {
            Bean bean = new Bean();
            bean.setEvent(event + i);
            bean.setDate(LocalDate.of(year, month, day));
            list.add(bean);
        });
        return list;
    }

}
