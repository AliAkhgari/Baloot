package utils;

import entities.Commodity;

import java.util.Comparator;

public class SortCommodities implements Comparator<Commodity> {
    private String sortAttribute;

    public SortCommodities(String sortAttribute) {
        this.sortAttribute = sortAttribute;
    }

    public int compare(Commodity c1, Commodity c2) {
        if (sortAttribute.equals("price")) {
            return Double.compare(c1.getPrice(), c2.getPrice());
        } else if (sortAttribute.equals("rating")) {
            return Double.compare(c1.getRating(), c2.getRating());
        } else {
            throw new IllegalArgumentException("Invalid sort attribute");
        }
    }
}
