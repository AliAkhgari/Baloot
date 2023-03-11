package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Commodity {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private ArrayList<String> categories = new ArrayList<String>();
    private float rating;
    private int inStock;
    private Map<String,Integer> user_rate = new HashMap<String,Integer>();
    private float init_rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
        this.init_rate = rating;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void decreaseInStock() { this.inStock -= 1; }
    public void add_rate(String username, int score) {
        user_rate.put(username, score);

        this.calc_rating();
    }

    private void calc_rating() {
        float sum = 0;
        for (Map.Entry<String, Integer> entry : this.user_rate.entrySet()) {
            sum += entry.getValue();
        }

        this.rating = ((this.init_rate + sum) / (this.user_rate.size() + 1));

    }
}
