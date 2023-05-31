package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Discount;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
        this.fetchAndSaveDiscountsFromApi();
    }

    public void fetchAndSaveDiscountsFromApi() {
        try {
            String discountsString = Request.makeGetRequest(HOST + DISCOUNTS_ENDPOINT);
            List<Discount> discountList = objectMapper.readValue(discountsString, new TypeReference<>() {
            });
            discountRepository.saveAll(discountList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public Discount getDiscountById(String id) {
//        return this.discountRepository.fin
//    }
}
