package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_rating")
@Getter
@Setter
@NoArgsConstructor
public class UserRating {
    @EmbeddedId
    private UserRatingId id;
    private int score;

    @ManyToOne
    @MapsId("commodityId")
    @JoinColumn(name = "commodity_id", foreignKey = @ForeignKey(name = "fk_user_rating_commodity"))
    private Commodity commodity;

//    @ManyToOne
//    @MapsId("username")
//    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "fk_user_rating_user"))
//    private User user;

    public UserRating(Commodity commodity, User user, int score) {
        this.score = score;
        this.id = new UserRatingId(commodity.getId(), user.getUsername());
        this.commodity = commodity;
    }

}
