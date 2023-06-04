package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "used_discounts")
@Getter
@Setter
@NoArgsConstructor
public class UsedDiscount {
    @EmbeddedId
    private DiscountUserId id;

    @ManyToOne
    @MapsId("discountCode")
    @JoinColumn(name = "dicsount_code", foreignKey = @ForeignKey(name = "fk_used_discount_discount"))
    private Discount discount;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "fk_used_discount_user"))
    private User user;

    public UsedDiscount(Discount discount, User user) {
        this.discount = discount;
        this.user = user;
        this.id = new DiscountUserId(discount.getDiscountCode(), user.getUsername());
    }

}
