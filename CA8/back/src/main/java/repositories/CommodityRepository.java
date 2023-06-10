package repositories;

import entities.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, String> {
    List<Commodity> findByCategories(String category);

    List<Commodity> findByNameContainingIgnoreCase(String name);

    List<Commodity> searchCommoditiesByProviderId(String providerName);

}
