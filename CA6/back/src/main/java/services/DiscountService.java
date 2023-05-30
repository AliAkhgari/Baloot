package services;

import entities.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.DiscountRepository;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public void dummy() {
        System.out.println("dummy");
    }
//    public Discount getDiscountById(String id) {
//        return this.discountRepository.fin
//    }
}
