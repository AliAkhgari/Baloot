package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private int credit;
    private Map<Integer,Integer> commodities_rates = new HashMap<Integer,Integer>();
    private ArrayList<Commodity> buy_list = new ArrayList<Commodity>();

    private ArrayList<Commodity> purchased_list = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Commodity> getBuy_list() {
        return buy_list;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public int getCredit() {
        return credit;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setBuy_list(ArrayList<Commodity> buy_list) {
        this.buy_list = buy_list;
    }

    public Map<Integer,Integer> getCommodities_rates() {
        return this.commodities_rates;
    }

    public ArrayList<Commodity> getPurchased_list() {
        return purchased_list;
    }

    public void setPurchased_list(ArrayList<Commodity> purchased_list) {
        this.purchased_list = purchased_list;
    }

    public void add_rated_commodities(int commodity_id, int score) {
        this.commodities_rates.put(commodity_id, score);
    }

    // todo: check if buy item or purchased item is already in the corresponding lists
    public void add_buy_item(Commodity commodity) {
        this.buy_list.add(commodity);
    }

    public void add_purchased_item(Commodity commodity) {
        this.purchased_list.add(commodity);
    }

    public void remove_item_from_buy_list(Commodity commodity) {
        this.buy_list.remove(commodity);
    }
}
