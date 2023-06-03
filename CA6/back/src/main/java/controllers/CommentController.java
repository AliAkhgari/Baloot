package controllers;

import entities.Comment;
import exceptions.NotExistentComment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CommentReactionService;
import services.CommentService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentReactionService commentReactionService;

    public CommentController(CommentService commentService, CommentReactionService commentReactionService) {
        this.commentService = commentService;
        this.commentReactionService = commentReactionService;
    }

    @PostMapping(value = "/comment/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        try {
            Comment comment = commentService.getCommentById(id);
            commentReactionService.addReaction(comment, "like");
            return new ResponseEntity<>("comment liked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/comment/{id}/dislike")
    public ResponseEntity<String> dislikeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        try {
            Comment comment = commentService.getCommentById(id);
            commentReactionService.addReaction(comment, "dislike");
            return new ResponseEntity<>("comment disliked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
