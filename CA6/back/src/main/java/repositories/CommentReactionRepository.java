package repositories;

import entities.CommentReaction;
import entities.CommodityUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, CommodityUserId> {
}
