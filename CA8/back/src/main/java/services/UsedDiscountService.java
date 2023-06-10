package services;

import entities.Discount;
import entities.DiscountUserId;
import entities.UsedDiscount;
import entities.User;
import exceptions.ExpiredDiscount;
import org.springframework.stereotype.Service;
import repositories.UsedDiscountRepository;

import java.util.Optional;

@Service
public class UsedDiscountService {
    private final UsedDiscountRepository usedDiscountRepository;

    public UsedDiscountService(UsedDiscountRepository usedDiscountRepository) {
        this.usedDiscountRepository = usedDiscountRepository;
    }

    public void addDiscountForUser(Discount discount, User user) throws ExpiredDiscount {
        DiscountUserId id = new DiscountUserId(discount.getDiscountCode(), user.getUsername());
        Optional<UsedDiscount> usedDiscountOptional = usedDiscountRepository.findById(id);
        if (usedDiscountOptional.isPresent())
            throw new ExpiredDiscount();

        UsedDiscount usedDiscount = new UsedDiscount(discount, user);
        usedDiscountRepository.save(usedDiscount);
    }

    public void checkDiscountExpiration(String discountCode, String username) throws ExpiredDiscount {
        DiscountUserId id = new DiscountUserId(discountCode, username);
        Optional<UsedDiscount> usedDiscountOptional = usedDiscountRepository.findById(id);

        if (usedDiscountOptional.isPresent())
            throw new ExpiredDiscount();
    }
}
