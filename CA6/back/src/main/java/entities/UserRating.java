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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String commodityId;
    private String username;
    private int score;

    public UserRating(String commodityId, String username, int score) {
        this.commodityId = commodityId;
        this.username = username;
        this.score = score;
    }

}
