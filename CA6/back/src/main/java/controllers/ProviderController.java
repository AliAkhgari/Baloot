package controllers;

import application.Baloot;
import entities.Commodity;
import entities.Provider;
import exceptions.NotExistentProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import services.ProviderService;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProviderController {
    private ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping(value = "/providers/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable String id) {
        try {
//            Provider provider = Baloot.getInstance().getProviderById(id);
            Provider provider = providerService.getProviderById(id);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        } catch (NotExistentProvider e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/providers/{id}/commodities")
    public ResponseEntity<ArrayList<Commodity>> getProvidedCommodities(@PathVariable String id) {
        ArrayList<Commodity> commodities = Baloot.getInstance().getCommoditiesProvidedByProvider(id);
        return new ResponseEntity<>(commodities, HttpStatus.OK);
    }
}
