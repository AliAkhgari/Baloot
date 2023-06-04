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
public class DiscountUserId implements Serializable {
    private String discountCode;
    private String username;

    public DiscountUserId(String discountCode, String username) {
        this.discountCode = discountCode;
        this.username = username;
    }

    // Override equals() and hashCode() methods
}