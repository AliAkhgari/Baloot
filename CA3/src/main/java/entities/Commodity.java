package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Commodity {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private ArrayList<String> categories = new ArrayList<>();
    private float rating;
    private int inStock;
    private Map<String, Integer> userRate = new HashMap<>();
    private float initRate;

    private ArrayList<Comment> comments = new ArrayList<>();

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
        this.initRate = rating;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void decreaseInStock() {
        this.inStock -= 1;
    }

    public void addRate(String username, int score) {
        userRate.put(username, score);

        this.calcRating();
    }

    private void calcRating() {
        float sum = 0;
        for (Map.Entry<String, Integer> entry : this.userRate.entrySet()) {
            sum += entry.getValue();
        }

        this.rating = ((this.initRate + sum) / (this.userRate.size() + 1));

    }
}
