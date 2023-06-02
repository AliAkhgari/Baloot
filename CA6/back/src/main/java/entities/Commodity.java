package entities;

import com.mysql.cj.Query;
import com.mysql.cj.Session;
import exceptions.NotInStock;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "commodities")
@Getter
@Setter
@NoArgsConstructor
public class Commodity {
    @Id
    private String id;
    private String name;
    private String providerId;
    private int price;

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> categories;

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
