package controllers;

import application.Baloot;
import entities.Comment;
import exceptions.NotExistentComment;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @PostMapping(value = "/comment/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable String id, HttpSession session) {
        int commentId = Integer.parseInt(id);
        try {
            Comment comment = Baloot.getInstance().getCommentById(commentId);
            String username = (String) session.getAttribute("username");
            comment.addUserVote(username, "like");
            return new ResponseEntity<>("comment liked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/comment/{id}/dislike")
    public ResponseEntity<String> dislikeComment(@PathVariable String id, HttpSession session) {
        int commentId = Integer.parseInt(id);
        try {
            Comment comment = Baloot.getInstance().getCommentById(commentId);
            String username = (String) session.getAttribute("username");
            comment.addUserVote(username, "dislike");
            return new ResponseEntity<>("comment liked successfully!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
