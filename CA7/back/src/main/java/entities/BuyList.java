package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "buy_list")
@Getter
@Setter
@NoArgsConstructor
public class BuyList {
    @EmbeddedId
    private CommodityUserId id;

    private int quantity;

    @ManyToOne
    @MapsId("commodityId")
    @JoinColumn(name = "commodity_id", foreignKey = @ForeignKey(name = "fk_buy_list_commodity"))
    private Commodity commodity;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "fk_buy_list_user"))
    private User user;

    public BuyList(Commodity commodity, User user) {
        this.id = new CommodityUserId(commodity.getId(), user.getUsername());
        this.commodity = commodity;
        this.user = user;
    }

    public void increaseQuantity() {
        this.quantity += 1;
    }

    public void decreaseQuantity() {
        this.quantity -= 1;
    }

}
