package entities;

import exceptions.InsufficientCredit;
import exceptions.InvalidCreditRange;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private float credit;

    public User(String username, String password, String email, String birthDate, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
    }

    public void addCredit(float amount) throws InvalidCreditRange {
        if (amount < 0)
            throw new InvalidCreditRange();

        this.credit += amount;
    }

    public void withdrawCredit(float amount) throws InsufficientCredit {
        if (amount > this.credit)
            throw new InsufficientCredit();

        this.credit -= amount;
    }
}
