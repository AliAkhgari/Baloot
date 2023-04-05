package database;

import entities.*;

import java.util.ArrayList;

public class Database {
    private static final Database instance = new Database();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private ArrayList<Commodity> commodities = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Discount> discounts = new ArrayList<>();

    private Database() {
    }

    public static Database getInstance() {
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addProvider(Provider provider) {
        providers.add(provider);
    }

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }


    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Provider> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<Provider> providers) {
        this.providers = providers;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    public void setCommodities(ArrayList<Commodity> commodities) {
        this.commodities = commodities;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(ArrayList<Discount> discounts) {
        this.discounts = discounts;
    }
}
