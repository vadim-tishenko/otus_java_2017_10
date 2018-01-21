package ru.cwl.otus.jawa.hw12.mustache;

import java.util.Arrays;
import java.util.List;

public class Ctx {
    public List<Item> getItems() {
        return Arrays.asList(
                new Item("Item 1", "$19.99", Arrays.asList(new Feature("New!"), new Feature("Awesome!"))),
                new Item("Item 2", "$29.99", Arrays.asList(new Feature("Old."), new Feature("Ugly.")))
        );
    }

    public static class Item {
        Item(String name, String price, List<Feature> features) {
            this.name = name;
            this.price = price;
            this.features = features;
        }
        String name, price;
        List<Feature> features;

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public List<Feature> getFeatures() {
            return features;
        }
    }

    public static class Feature {
        Feature(String description) {
            this.description = description;
        }
        String description;

        public String getDescription() {
            return description;
        }
    }
}
