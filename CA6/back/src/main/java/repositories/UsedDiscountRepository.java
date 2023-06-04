package repositories;

import entities.DiscountUserId;
import entities.UsedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UsedDiscountRepository extends JpaRepository<UsedDiscount, DiscountUserId> {
}
