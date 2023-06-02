package entities;

import exceptions.InsufficientCredit;
import exceptions.InvalidCreditRange;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private float credit;
//    private Map<Integer, Integer> commoditiesRates = new HashMap<>();
//    private Map<String, Integer> buyList = new HashMap<>();
//    private Map<String, Integer> purchasedList = new HashMap<>();
//    private ArrayList<String> usedDiscounts = new ArrayList<>();
//    private Discount currentDiscount = null;


    public User(String username, String password, String email, String birthDate, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
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

//    public void addCurrentDiscountToUsed() {
//        if (this.currentDiscount == null)
//            return;
//        this.usedDiscounts.add(this.currentDiscount.getDiscountCode());
//        this.currentDiscount = null;
//    }
//
//    public void addRatedCommodities(int commodity_id, int score) {
//        this.commoditiesRates.put(commodity_id, score);
//    }
//
//    public void addBuyItem(Commodity commodity) {
//        String id = commodity.getId();
//        if (this.buyList.containsKey(id)) {
//            int existingQuantity = this.buyList.get(id);
//            this.buyList.put(id, existingQuantity + 1);
//        } else
//            this.buyList.put(id, 1);
//    }
//
//    public void addPurchasedItem(String id, int quantity) {
//        if (this.purchasedList.containsKey(id)) {
//            int existingQuantity = this.purchasedList.get(id);
//            this.purchasedList.put(id, existingQuantity + quantity);
//        } else
//            this.purchasedList.put(id, quantity);
//    }
//
//    public void removeItemFromBuyList(Commodity commodity) throws CommodityIsNotInBuyList {
//        String id = commodity.getId();
//        if (this.buyList.containsKey(id)) {
//            int existingQuantity = this.buyList.get(id);
//            if (existingQuantity == 1)
//                this.buyList.remove(commodity.getId());
//            else
//                this.buyList.put(id, existingQuantity - 1);
//        } else
//            throw new CommodityIsNotInBuyList();
//    }
//
//    public float getCurrentDiscountAmount() {
//        if (this.currentDiscount == null)
//            return 0;
//
//        return this.currentDiscount.getDiscount() / 100;
//    }
}
