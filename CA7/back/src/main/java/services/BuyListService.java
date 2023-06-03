package services;

import entities.BuyList;
import entities.Commodity;
import entities.CommodityUserId;
import entities.User;
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

    public void addItem(Commodity commodity, User user) {
        BuyList buyList = new BuyList(commodity, user);
        buyListRepository.save(buyList);
    }

    public void removeItem(String commodityId, String username) throws CommodityIsNotInBuyList {
        CommodityUserId id = new CommodityUserId(commodityId, username);
        Optional<BuyList> buyListOptional = buyListRepository.findById(id);

        if (buyListOptional.isPresent()) {
            buyListRepository.deleteById(id);
        } else {
            throw new CommodityIsNotInBuyList();
        }
    }

    public List<BuyList> getUserItems(User user) {
        return buyListRepository.findByUser(user);
    }
}
