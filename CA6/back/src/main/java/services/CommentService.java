package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Comment;
import entities.CommentReaction;
import exceptions.NotExistentComment;
import org.springframework.stereotype.Service;
import repositories.CommentReactionRepository;
import repositories.CommentRepository;
import utils.Request;

import java.io.IOException;
import java.util.List;

import static defines.Endpoints.COMMENTS_ENDPOINT;
import static defines.Endpoints.HOST;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentReactionRepository commentReactionRepository;

    public CommentService(CommentRepository commentRepository, CommentReactionRepository commentReactionRepository) {
        this.commentRepository = commentRepository;
        this.commentReactionRepository = commentReactionRepository;
        this.fetchAndSaveCommentsFromApi();
    }

    public void fetchAndSaveCommentsFromApi() {
        try {
            System.out.println("1 in fetchAndSaveCommentsFromApi");

            ObjectMapper objectMapper = new ObjectMapper();
            String commentsString = Request.makeGetRequest(HOST + COMMENTS_ENDPOINT);

            System.out.println(commentsString);
            List<Comment> commentList = objectMapper.readValue(commentsString, new TypeReference<>() {
            });

            System.out.println("2 in fetchAndSaveCommentsFromApi");
//
//            // TODO: setUsername
//
            System.out.println(commentList);

            commentRepository.saveAll(commentList);
            System.out.println("3 in fetchAndSaveCommentsFromApi");
            for (Comment comment : commentList) {
                comment.setUsername("ff");
//
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void setCommentsUsername() {
//        List<Comment> comments = commentRepository.findAll();
//        for (Comment comment : comments) {
//            User user = userRepository.findByEmail(comment.getUserEmail());
//            if (user != null) {
//                comment.setUsername(user.getUsername());
//                commentRepository.save(comment);
//            }
//        }
//    }


    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment getCommentById(String id) throws NotExistentComment {
        return commentRepository.findById(id)
                .orElseThrow(NotExistentComment::new);
    }

//    public void reactComment(String reaction) {
//        commentReactionRepository.save(new CommentReaction())
//    }
}
