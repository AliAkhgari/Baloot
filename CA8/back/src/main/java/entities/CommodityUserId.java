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
public class CommodityUserId implements Serializable {
    private String commodityId;
    private String username;

    public CommodityUserId(String commodityId, String username) {
        this.commodityId = commodityId;
        this.username = username;
    }

    // Override equals() and hashCode() methods
}