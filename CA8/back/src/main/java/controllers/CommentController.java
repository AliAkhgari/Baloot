package controllers;

import entities.Comment;
import exceptions.NotExistentComment;
import exceptions.NotExistentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CommentReactionService;
import services.CommentService;
import services.UserService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentReactionService commentReactionService;
    private final UserService userService;

    public CommentController(CommentService commentService, CommentReactionService commentReactionService, UserService userService) {
        this.commentService = commentService;
        this.commentReactionService = commentReactionService;
        this.userService = userService;
    }

    @PostMapping(value = "/comment/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        try {
            Comment comment = commentService.getCommentById(id);
            String username = input.get("username");

            commentReactionService.addReaction(comment, userService.getUserById(username), "like");
            comment.setLikeCount(commentReactionService.getNumberOfLikes(comment.getId()));
            comment.setDislikeCount(commentReactionService.getNumberOfDislikes(comment.getId()));
            commentService.save(comment);

            return new ResponseEntity<>("comment liked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NotExistentUser e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/comment/{id}/dislike")
    public ResponseEntity<String> dislikeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        try {
            Comment comment = commentService.getCommentById(id);
            String username = input.get("username");

            commentReactionService.addReaction(comment, userService.getUserById(username), "dislike");
            comment.setLikeCount(commentReactionService.getNumberOfLikes(comment.getId()));
            comment.setDislikeCount(commentReactionService.getNumberOfDislikes(comment.getId()));
            commentService.save(comment);

            return new ResponseEntity<>("comment disliked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NotExistentUser e) {
            throw new RuntimeException(e);
        }

    }
}
