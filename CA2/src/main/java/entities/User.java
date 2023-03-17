package entities;

import Exceptions.AlreadyInBuyList;
import Exceptions.CommodityIsNotInBuyList;
import Exceptions.InsufficientCredit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private int credit;
    private Map<Integer, Integer> commoditiesRates = new HashMap<>();
    private ArrayList<Commodity> buyList = new ArrayList<>();

    private ArrayList<Commodity> purchasedList = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Commodity> getBuyList() {
        return buyList;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
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

    public void addCredit(float amount) {
        this.credit += amount;
    }

    public void withdrawCredit(int amount) throws InsufficientCredit {
        if (amount > this.credit)
            throw new InsufficientCredit();

        this.credit -= amount;
    }

    public void setBuyList(ArrayList<Commodity> buy_list) {
        this.buyList = buy_list;
    }

    public Map<Integer, Integer> getCommoditiesRates() {
        return this.commoditiesRates;
    }

    public ArrayList<Commodity> getPurchasedList() {
        return purchasedList;
    }

    public void setPurchasedList(ArrayList<Commodity> purchased_list) {
        this.purchasedList = purchased_list;
    }

    public void addRatedCommodities(int commodity_id, int score) {
        this.commoditiesRates.put(commodity_id, score);
    }

    // todo: check if buy item or purchased item is already in the corresponding lists
    public void addBuyItem(Commodity commodity) throws AlreadyInBuyList {
        if (this.buyList.contains(commodity))
            throw new AlreadyInBuyList();
        else
            this.buyList.add(commodity);
    }

    public void addPurchasedItem(Commodity commodity) {
        this.purchasedList.add(commodity);
    }

    public void removeItemFromBuyList(Commodity commodity) throws CommodityIsNotInBuyList {
        if (this.buyList.contains(commodity))
            this.buyList.remove(commodity);
        else
            throw new CommodityIsNotInBuyList();
    }
}
