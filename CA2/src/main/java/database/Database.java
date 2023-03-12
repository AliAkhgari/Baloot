package database;

import entities.Commodity;
import entities.Provider;
import entities.User;

import java.util.ArrayList;

public class Database {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private ArrayList<Commodity> commodities = new ArrayList<>();

    public Database() {
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
}
