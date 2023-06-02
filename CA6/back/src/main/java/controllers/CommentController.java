package controllers;

import entities.Comment;
import exceptions.NotExistentComment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CommentService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comment/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
//        int commentId = Integer.parseInt(id);
        try {
//            Comment comment = Baloot.getInstance().getCommentById(commentId);
            Comment comment = commentService.getCommentById(id);
            comment.addUserVote("like");
            return new ResponseEntity<>("comment liked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/comment/{id}/dislike")
    public ResponseEntity<String> dislikeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
//        int commentId = Integer.parseInt(id);
        try {
//            Comment comment = Baloot.getInstance().getCommentById(commentId);
            Comment comment = commentService.getCommentById(id);
            comment.addUserVote("dislike");
            return new ResponseEntity<>("comment liked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
