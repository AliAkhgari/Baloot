package repositories;

import entities.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, String> {

    @Query(value = "SELECT c FROM Commodity c JOIN c.categories cat WHERE LOWER(cat) LIKE LOWER(concat('%', :category, '%'))", nativeQuery = true)
    List<Commodity> findByCategoriesContainingIgnoreCase(@Param("category") String category);

//    List<Commodity> findByCategoriesContainingIgnoreCase(String category);

    List<Commodity> findByNameContainingIgnoreCase(String name);

    List<Commodity> searchCommoditiesByProviderId(String providerName);

}
