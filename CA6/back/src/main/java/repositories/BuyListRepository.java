package repositories;

import entities.BuyList;
import entities.CommodityUserId;
import entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyListRepository extends JpaRepository<BuyList, CommodityUserId> {
    List<BuyList> findByUser(User user);

    @Query("SELECT SUM(b.commodity.price * b.quantity) FROM BuyList b WHERE b.user.username = :username")
    Float getTotalPriceSum(@Param("username") String username);

}
