package services;

import entities.PurchasedList;
import entities.User;
import org.springframework.stereotype.Service;
import repositories.PurchasedListRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PurchasedListService {
    private final PurchasedListRepository purchasedListRepository;

    public PurchasedListService(PurchasedListRepository purchasedListRepository) {
        this.purchasedListRepository = purchasedListRepository;
    }

    public void addItem(PurchasedList item) {
        Optional<PurchasedList> purchasedListOptional = purchasedListRepository.findById(item.getId());

        if (purchasedListOptional.isPresent()) {
            purchasedListOptional.get().increaseQuantity();
            purchasedListRepository.save(purchasedListOptional.get());
        } else
            purchasedListRepository.save(item);
    }

    public List<PurchasedList> getUserItems(User user) {
        return purchasedListRepository.findByUser(user);
    }
}
