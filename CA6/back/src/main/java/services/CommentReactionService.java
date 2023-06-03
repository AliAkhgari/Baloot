package services;

import entities.Comment;
import entities.CommentReaction;
import org.springframework.stereotype.Service;
import repositories.CommentReactionRepository;

@Service
public class CommentReactionService {
    private final CommentReactionRepository commentReactionRepository;

    public CommentReactionService(CommentReactionRepository commentReactionRepository) {
        this.commentReactionRepository = commentReactionRepository;
    }

    public void addReaction(Comment comment, String reaction) {
        CommentReaction commentReaction = new CommentReaction(comment.getCommodity(), comment.getUser(), reaction);
        commentReactionRepository.save(commentReaction);
    }
}
