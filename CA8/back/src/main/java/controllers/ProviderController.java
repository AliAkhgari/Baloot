package controllers;

import entities.Commodity;
import entities.Provider;
import exceptions.NotExistentProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import services.CommodityService;
import services.ProviderService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProviderController {
    private final ProviderService providerService;
    private final CommodityService commodityService;

    public ProviderController(ProviderService providerService, CommodityService commodityService) {
        this.providerService = providerService;
        this.commodityService = commodityService;
    }

    @GetMapping(value = "/providers/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable String id) {
        try {
            Provider provider = providerService.getProviderById(id);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        } catch (NotExistentProvider e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/providers/{id}/commodities")
    public ResponseEntity<List<Commodity>> getProvidedCommodities(@PathVariable String id) {
        List<Commodity> commodities = commodityService.findByProviderContaining(id);
        return new ResponseEntity<>(commodities, HttpStatus.OK);
    }
}
