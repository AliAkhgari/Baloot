package services;

import entities.BuyList;
import entities.Commodity;
import entities.CommodityUserId;
import entities.User;
import exceptions.AlreadyInBuyList;
import exceptions.CommodityIsNotInBuyList;
import org.springframework.stereotype.Service;
import repositories.BuyListRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BuyListService {
    private final BuyListRepository buyListRepository;

    public BuyListService(BuyListRepository buyListRepository) {
        this.buyListRepository = buyListRepository;
    }

    public void addItem(Commodity commodity, User user) throws AlreadyInBuyList {
        CommodityUserId id = new CommodityUserId(commodity.getId(), user.getUsername());
        Optional<BuyList> buyListOptional = buyListRepository.findById(id);

        if (buyListOptional.isPresent()) {
            buyListOptional.get().increaseQuantity();
            buyListRepository.save(buyListOptional.get());
        } else {
            BuyList buyList = new BuyList(commodity, user);
            buyList.setQuantity(1);
            buyListRepository.save(buyList);
        }
    }

    public void removeItem(String commodityId, String username) throws CommodityIsNotInBuyList {
        CommodityUserId id = new CommodityUserId(commodityId, username);
        Optional<BuyList> buyListOptional = buyListRepository.findById(id);

        if (buyListOptional.isPresent()) {
            if (buyListOptional.get().getQuantity() > 1) {
                buyListOptional.get().decreaseQuantity();
                buyListRepository.save(buyListOptional.get());
            } else
                buyListRepository.deleteById(id);
        } else {
            throw new CommodityIsNotInBuyList();
        }
    }

    public void deleteItem(CommodityUserId id) {
        buyListRepository.deleteById(id);
    }

    public List<BuyList> getUserItems(User user) {
        return buyListRepository.findByUser(user);
    }

    public float getCurrentBuyListPrice(String username) {
        Float totalPriceSum = buyListRepository.getTotalPriceSum(username);
        if (totalPriceSum == null) {
            return 0;
        }
        return totalPriceSum;
    }

}
