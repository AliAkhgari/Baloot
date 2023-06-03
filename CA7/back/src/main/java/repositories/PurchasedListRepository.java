package repositories;

import entities.CommodityUserId;
import entities.PurchasedList;
import entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedListRepository extends JpaRepository<PurchasedList, CommodityUserId> {
    List<PurchasedList> findByUser(User user);
}
