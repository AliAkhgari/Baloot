package services;

import entities.Comment;
import entities.CommentReaction;
import entities.User;
import org.springframework.stereotype.Service;
import repositories.CommentReactionRepository;

@Service
public class CommentReactionService {
    private final CommentReactionRepository commentReactionRepository;

    public CommentReactionService(CommentReactionRepository commentReactionRepository) {
        this.commentReactionRepository = commentReactionRepository;
    }

    public void addReaction(Comment comment, User user, String reaction) {
        CommentReaction commentReaction = new CommentReaction(comment, user, reaction);
        commentReactionRepository.save(commentReaction);
    }

    public int getNumberOfLikes(Long commentId) {
        return commentReactionRepository.getLikeCountByCommodityId(commentId);
    }

    public int getNumberOfDislikes(Long commentId) {
        return commentReactionRepository.getDislikeCountByCommodityId(commentId);
    }
}
