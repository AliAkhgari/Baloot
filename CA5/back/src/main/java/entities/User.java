package entities;

import exceptions.CommodityIsNotInBuyList;
import exceptions.InsufficientCredit;
import exceptions.InvalidCreditRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private float credit;
    private Map<Integer, Integer> commoditiesRates = new HashMap<>();
    private Map<String, Integer> buyList = new HashMap<>();
    private Map<String, Integer> purchasedList = new HashMap<>();
    private ArrayList<Discount> usedDiscounts = new ArrayList<>();
    private Discount currentDiscount = null;

    public User() {

    }

    public User(String username, String password, String email, String birthDate, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, Integer> getBuyList() {
        return buyList;
    }

    public void setBuyList(Map<String, Integer> buyList) {
        this.buyList = buyList;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public float getCredit() {
        return this.credit;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public void addCredit(float amount) throws InvalidCreditRange {
        if (amount < 0)
            throw new InvalidCreditRange();
        
        this.credit += amount;
    }

    public void withdrawCredit(float amount) throws InsufficientCredit {
        if (amount > this.credit)
            throw new InsufficientCredit();

        this.credit -= amount;
    }
    public Map<Integer, Integer> getCommoditiesRates() {
        return this.commoditiesRates;
    }

    public Map<String, Integer> getPurchasedList() {
        return purchasedList;
    }

    public void setPurchasedList(Map<String, Integer> purchased_list) {
        this.purchasedList = purchased_list;
    }

    public ArrayList<Discount> getUsedDiscounts() {
        return usedDiscounts;
    }

    public void setUsedDiscounts(ArrayList<Discount> usedDiscounts) {
        this.usedDiscounts = usedDiscounts;
    }

    public void addCurrentDiscountToUsed() {
        this.usedDiscounts.add(this.currentDiscount);
        this.currentDiscount = null;
    }

    public Discount getCurrentDiscount() {
        return currentDiscount;
    }

    public void setCurrentDiscount(Discount currentDiscount) {
        this.currentDiscount = currentDiscount;
    }

    public void addRatedCommodities(int commodity_id, int score) {
        this.commoditiesRates.put(commodity_id, score);
    }

    public void addBuyItem(Commodity commodity) {
        String id = commodity.getId();
        if (this.buyList.containsKey(id)) {
            int existingQuantity = this.buyList.get(id);
            this.buyList.put(id, existingQuantity + 1);
        } else
            this.buyList.put(id, 1);
    }

    public void addPurchasedItem(String id, int quantity) {
        if (this.purchasedList.containsKey(id)) {
            int existingQuantity = this.purchasedList.get(id);
            this.purchasedList.put(id, existingQuantity + quantity);
        } else
            this.purchasedList.put(id, quantity);
    }

    public void removeItemFromBuyList(Commodity commodity) throws CommodityIsNotInBuyList {
        String id = commodity.getId();
        if (this.buyList.containsKey(id)) {
            int existingQuantity = this.buyList.get(id);
            if (existingQuantity == 1)
                this.buyList.remove(commodity.getId());
            else
                this.buyList.put(id, existingQuantity - 1);
        } else
            throw new CommodityIsNotInBuyList();
    }

    public float getCurrentDiscountAmount() {
        if (this.currentDiscount == null)
            return 0;

        return this.currentDiscount.getDiscount() / 100;
    }
}
