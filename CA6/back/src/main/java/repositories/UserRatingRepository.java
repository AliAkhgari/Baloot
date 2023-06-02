package repositories;

import entities.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, String> {
    @Query("SELECT AVG(u.score) FROM UserRating u WHERE u.commodity.id = :commodityId")
    Float getAverageScoreByCommodityId(@Param("commodityId") String commodityId);
}
