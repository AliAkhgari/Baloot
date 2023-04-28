package entities;

import exceptions.AlreadyInBuyList;
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
    private ArrayList<Commodity> buyList = new ArrayList<>();
    private ArrayList<Commodity> purchasedList = new ArrayList<>();
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

    public ArrayList<Commodity> getBuyList() {
        return buyList;
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

    public float getCurrentBuyListPrice() {
        float total = 0;
        for (Commodity commodity : this.getBuyList())
            total += commodity.getPrice();

        return total;
    }

    public float getCurrentDiscountAmount() {
        if (this.currentDiscount == null)
            return 0;

        return this.getCurrentBuyListPrice() * this.currentDiscount.getDiscount() / 100;
    }

    public void withdrawPayableAmount() throws InsufficientCredit {
        float amount = getCurrentBuyListPrice();
        float discount_amount = this.getCurrentDiscountAmount();
        this.withdrawCredit(amount - discount_amount);
        this.addCurrentDiscountToUsed();

    }


}
