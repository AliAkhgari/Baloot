package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userEmail;
    private String username;
    private int commodityId;
    private String text;
    private String date;
    private int likeCount = 0;
    private int dislikeCount = 0;
    @JsonIgnore
    @OneToMany
    private List<CommentReaction> commentReactions;

    public Comment(String userEmail, int commodityId, String text) {
        this.userEmail = userEmail;
        this.commodityId = commodityId;
        this.text = text;
        this.date = currentDate();
    }

    public String currentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(currentDate);
    }

    public void addUserVote(String vote) {
        CommentReaction commentReaction = new CommentReaction(id, username, vote);
        commentReactions.add(commentReaction);

        updateVoteCounts();
    }

    private void updateVoteCounts() {
        int likeCount = 0;
        int dislikeCount = 0;

        for (CommentReaction commentReaction : commentReactions) {
            if (commentReaction.getReaction().equals("like")) {
                likeCount++;
            } else if (commentReaction.getReaction().equals("dislike")) {
                dislikeCount++;
            }
        }

        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}
