package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Commodity;
import exceptions.NotExistentCommodity;
import org.springframework.stereotype.Service;
import repositories.CommodityRepository;
import utils.Request;

import java.io.IOException;
import java.util.*;

import static defines.Endpoints.COMMODITIES_ENDPOINT;
import static defines.Endpoints.HOST;
import static defines.defines.MAX_NUMBER_OF_COMMODITY_SUGGESTIONS;

@Service
public class CommodityService {
    private final CommodityRepository commodityRepository;

    public CommodityService(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
        this.fetchAndSaveCommoditiesFromApi();
    }

    public void fetchAndSaveCommoditiesFromApi() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String CommoditiesString = Request.makeGetRequest(HOST + COMMODITIES_ENDPOINT);
            List<Commodity> commodityList = objectMapper.readValue(CommoditiesString, new TypeReference<>() {
            });
            for (Commodity commodity : commodityList)
                commodity.setInitRate(commodity.getRating());

            commodityRepository.saveAll(commodityList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Commodity getCommodityById(String id) throws NotExistentCommodity {
        return commodityRepository.findById(id)
                .orElseThrow(NotExistentCommodity::new);
    }

    public List<Commodity> getAllCommodities() {
        return commodityRepository.findAll();
    }

    public void save(Commodity commodity) {
        commodityRepository.save(commodity);
    }

    public List<Commodity> searchCommoditiesByCategory(String category) {
        return commodityRepository.findByCategoriesContainingIgnoreCase(category);
    }

    public List<Commodity> searchCommoditiesByName(String name) {
        return commodityRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Commodity> findByProviderContaining(String providerName) {
        return commodityRepository.searchCommoditiesByProviderId(providerName);
    }

    public int isInSimilarCategoryWithFirstCommodity(Commodity c1, Commodity c2) {
        for (String category : c2.getCategories())
            if (c1.getCategories().contains(category))
                return 1;

        return 0;
    }

    public ArrayList<Commodity> suggestSimilarCommodities(Commodity commodity) {
        ArrayList<Commodity> results = new ArrayList<>();
        Hashtable<Commodity, Float> commodityScore = new Hashtable<>();

        for (Commodity commodity1 : getAllCommodities()) {
            if (commodity == commodity1)
                continue;

            float score = 11 * isInSimilarCategoryWithFirstCommodity(commodity, commodity1) + commodity1.getRating();
            commodityScore.put(commodity1, score);
        }

        List<Map.Entry<Commodity, Float>> list = new ArrayList<>(commodityScore.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        int count = 0;
        for (Map.Entry<Commodity, Float> entry : list) {
            results.add(entry.getKey());

            count += 1;
            if (count >= MAX_NUMBER_OF_COMMODITY_SUGGESTIONS)
                break;
        }

        return results;
    }
}
