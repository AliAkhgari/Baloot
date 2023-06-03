package services;

import entities.Commodity;
import entities.PurchasedList;
import entities.User;
import org.springframework.stereotype.Service;
import repositories.PurchasedListRepository;

import java.util.List;

@Service
public class PurchasedListService {
    private final PurchasedListRepository purchasedListRepository;

    public PurchasedListService(PurchasedListRepository purchasedListRepository) {
        this.purchasedListRepository = purchasedListRepository;
    }

    public void addItem(Commodity commodity, User user) {
        PurchasedList purchasedList = new PurchasedList(commodity, user);
        purchasedListRepository.save(purchasedList);
    }

    public List<PurchasedList> getUserItems(User user) {
        return purchasedListRepository.findByUser(user);
    }
}
