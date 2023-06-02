package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Commodity;
import exceptions.NotExistentCommodity;
import org.springframework.stereotype.Service;
import repositories.CommodityRepository;
import utils.Request;

import java.io.IOException;
import java.util.List;

import static defines.Endpoints.COMMODITIES_ENDPOINT;
import static defines.Endpoints.HOST;

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
}
