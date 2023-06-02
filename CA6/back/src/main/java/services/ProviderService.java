package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Provider;
import exceptions.NotExistentProvider;
import org.springframework.stereotype.Service;
import repositories.ProviderRepository;
import utils.Request;

import java.io.IOException;
import java.util.List;

import static defines.Endpoints.HOST;
import static defines.Endpoints.PROVIDERS_ENDPOINT;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
        this.fetchAndSaveProvidersFromApi();

    }

    public void fetchAndSaveProvidersFromApi() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String providersString = Request.makeGetRequest(HOST + PROVIDERS_ENDPOINT);
            List<Provider> providerList = objectMapper.readValue(providersString, new TypeReference<>() {
            });
            providerRepository.saveAll(providerList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Provider getProviderById(String id) throws NotExistentProvider {
        return providerRepository.findById(id)
                .orElseThrow(NotExistentProvider::new);
    }
}
