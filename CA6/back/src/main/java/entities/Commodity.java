package entities;

import exceptions.NotInStock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "commodities")
@Getter
@Setter
@NoArgsConstructor
public class Commodity {
    @Id
    private String id;
    private String name;
    // TODO: foreign key
    private String providerId;
    private int price;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> categories;

    private float rating;
    private int inStock;

    @Column(length = 1024)
    private String image;

    private float initRate;

    public void updateInStock(int amount) throws NotInStock {
        if ((this.inStock + amount) < 0)
            throw new NotInStock();
        this.inStock += amount;
    }

    public void decreaseInStock() {
        this.inStock -= 1;
    }

    public void updateRating(Float averageRate) {
        this.rating = (averageRate + this.initRate) / 2;
    }
}
