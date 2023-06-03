package entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CommentUserId implements Serializable {
    private Long commentId;
    private String username;

    public CommentUserId(Long commentId, String username) {
        this.commentId = commentId;
        this.username = username;
    }

    // Override equals() and hashCode() methods
}