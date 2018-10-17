package beansjar.djimpanse.com.beansjar.beans.list;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.images.Image;
import beansjar.djimpanse.com.beansjar.util.StringUtils;


/**
 * Model that represents the CardView in the list of beans. Each object represents a day and the
 * beans.
 */
public class BeansCardModel {

    private LocalDate date;
    private Image image;
    private List<Bean> beans;

    /**
     * Constructor, that will use one
     *
     * @param date
     * @param beans
     */
    public BeansCardModel(LocalDate date, List<Bean> beans) {
        this.date = date;
        this.beans = beans;

        List<String> imagePaths = beans.stream().map(bean -> bean.getImagePath()).filter(path ->
                StringUtils.isNotEmpty(path)).collect(Collectors.toList());
        if (!imagePaths.isEmpty()) {
            Collections.shuffle(imagePaths);
            this.image = new Image(imagePaths.get(0));
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public Image getImage() {
        return image;
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
