package beansjar.djimpanse.com.beansjar.beans.list;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import beansjar.djimpanse.com.beansjar.beans.data.Bean;


/**
 * Model that represents the CardView in the list of beans. Each object represents a day and the
 * beans.
 */
public class BeansCardModel {

    private LocalDate date;

    private List<Bean> beans;

    public BeansCardModel() {
        this.beans = new ArrayList<>();
    }

    public BeansCardModel(LocalDate date, List<Bean> beans) {
        this.date = date;
        this.beans = beans;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void addBeans(List<Bean> beans) {
        this.beans.addAll(beans);
    }

    public static List<BeansCardModel> createModels(List<Bean> beans) {
        List<BeansCardModel> models = new ArrayList<>();

        if (beans != null && !beans.isEmpty()) {

            HashMap<LocalDate, List<Bean>> dateBeansMap = new HashMap<>();
            beans.stream().forEach(bean -> {
                LocalDate date = bean.getDate();
                if (dateBeansMap.containsKey(date)) {
                    dateBeansMap.get(date).add(bean);
                } else {
                    dateBeansMap.put(date, new ArrayList<>());
                    dateBeansMap.get(date).add(bean);
                }
            });

            dateBeansMap.forEach((k, v) -> {
                models.add(new BeansCardModel(k, v));
            });
        }

        return models.stream().sorted((m1, m2) -> m1.getDate().isAfter(m2.getDate()) ? 1 : -1)
                .collect(Collectors.toList());

    }
}
