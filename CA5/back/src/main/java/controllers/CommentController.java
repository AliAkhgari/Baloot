package controllers;

import application.Baloot;
import entities.Comment;
import exceptions.NotExistentComment;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CommentController {
    @PostMapping(value = "/comment/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        int commentId = Integer.parseInt(id);
        try {
            Comment comment = Baloot.getInstance().getCommentById(commentId);
            String username = input.get("username");
            comment.addUserVote(username, "like");
            return new ResponseEntity<>("comment liked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/comment/{id}/dislike")
    public ResponseEntity<String> dislikeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        int commentId = Integer.parseInt(id);
        try {
            Comment comment = Baloot.getInstance().getCommentById(commentId);
            String username = input.get("username");
            comment.addUserVote(username, "dislike");
            return new ResponseEntity<>("comment liked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
