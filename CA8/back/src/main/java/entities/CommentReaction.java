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
    @EmbeddedId
    private CommentUserId id;
    private String reaction;

    @ManyToOne
    @MapsId("commentId")
    @JoinColumn(name = "comment_id", foreignKey = @ForeignKey(name = "fk_comment_reaction_comment"))
    private Comment comment;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "fk_comment_reaction_user"))
    private User user;

    public CommentReaction(Comment comment, User user, String reaction) {
        this.comment = comment;
        this.user = user;
        this.reaction = reaction;
        this.id = new CommentUserId(comment.getId(), user.getUsername());
    }
}
