package repositories;

import entities.CommentReaction;
import entities.CommentUserId;
import entities.CommodityUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, CommentUserId> {
    @Query("SELECT SUM(CASE WHEN c.reaction = 'like' THEN 1 ELSE 0 END) FROM CommentReaction c WHERE c.id.commentId = :commentId")
    Integer getLikeCountByCommodityId(Long commentId);

    @Query("SELECT SUM(CASE WHEN c.reaction = 'dislike' THEN 1 ELSE 0 END) FROM CommentReaction c WHERE c.id.commentId = :commentId")
    Integer getDislikeCountByCommodityId(Long commentId);
}
