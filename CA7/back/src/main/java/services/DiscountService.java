package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Discount;
import exceptions.NotExistentDiscount;
import org.springframework.stereotype.Service;
import repositories.DiscountRepository;
import utils.Request;

import java.io.IOException;
import java.util.List;

import static defines.Endpoints.DISCOUNTS_ENDPOINT;
import static defines.Endpoints.HOST;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
        this.fetchAndSaveDiscountsFromApi();
    }

    public void fetchAndSaveDiscountsFromApi() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String discountsString = Request.makeGetRequest(HOST + DISCOUNTS_ENDPOINT);
            List<Discount> discountList = objectMapper.readValue(discountsString, new TypeReference<>() {
            });
            discountRepository.saveAll(discountList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Discount getDiscountById(String id) throws NotExistentDiscount {
        return discountRepository.findById(id)
                .orElseThrow(NotExistentDiscount::new);
    }
}
