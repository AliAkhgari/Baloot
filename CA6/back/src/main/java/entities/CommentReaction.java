package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_reaction")
@Getter
@Setter
@NoArgsConstructor
public class CommentReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long commentId;
    private String username;
    private String reaction;

    public CommentReaction(Long commentId, String username, String reaction) {
        this.commentId = commentId;
        this.username = username;
        this.reaction = reaction;
    }
}
