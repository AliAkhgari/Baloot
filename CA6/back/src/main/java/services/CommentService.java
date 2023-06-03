package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Comment;
import exceptions.NotExistentComment;
import org.springframework.stereotype.Service;
import repositories.CommentRepository;
import utils.Request;

import java.io.IOException;
import java.util.List;

import static defines.Endpoints.COMMENTS_ENDPOINT;
import static defines.Endpoints.HOST;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.fetchAndSaveCommentsFromApi();
    }

    public void fetchAndSaveCommentsFromApi() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String commentsString = Request.makeGetRequest(HOST + COMMENTS_ENDPOINT);

            List<Comment> commentList = objectMapper.readValue(commentsString, new TypeReference<>() {
            });

            for (Comment comment : commentList) {
                comment.setUsername("amir");
            }

            commentRepository.saveAll(commentList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> getCommentsForCommodity(String commodityId) {
        return commentRepository.findByCommodityId(commodityId);
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment getCommentById(String id) throws NotExistentComment {
        return commentRepository.findById(id)
                .orElseThrow(NotExistentComment::new);
    }
}
