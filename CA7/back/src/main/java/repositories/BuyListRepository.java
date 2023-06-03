package repositories;

import entities.BuyList;
import entities.CommodityUserId;
import entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyListRepository extends JpaRepository<BuyList, CommodityUserId> {
    List<BuyList> findByUser(User user);
}
