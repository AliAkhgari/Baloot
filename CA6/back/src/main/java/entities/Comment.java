package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String commodityId;
    private String text;
    private String date;
    private int likeCount = 0;
    private int dislikeCount = 0;

    @ManyToOne
    @MapsId("commodityId")
    @JoinColumn(name = "commodityId", foreignKey = @ForeignKey(name = "fk_comments_commodity"))
    private Commodity commodity;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "fk_comments_user"))
    private User user;

    public Comment(User user, Commodity commodity, String text) {
        this.userEmail = user.getEmail();
        this.commodityId = commodity.getId();
        this.text = text;
        this.date = currentDate();
    }

    public String currentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(currentDate);
    }

//    public void addUserVote(String vote) {
//        CommentReaction commentReaction = new CommentReaction(id, username, vote);
//        commentReactions.add(commentReaction);
//
//        updateVoteCounts();
//    }
//
//    private void updateVoteCounts() {
//        int likeCount = 0;
//        int dislikeCount = 0;
//
//        for (CommentReaction commentReaction : commentReactions) {
//            if (commentReaction.getReaction().equals("like")) {
//                likeCount++;
//            } else if (commentReaction.getReaction().equals("dislike")) {
//                dislikeCount++;
//            }
//        }
//
//        this.likeCount = likeCount;
//        this.dislikeCount = dislikeCount;
//    }
}
