package Unit;

import application.Baloot;
import entities.Commodity;
import entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RateCommodityTest {
    private Baloot baloot;

    @BeforeEach
    public void setup() {
        Baloot.resetInstance();
        baloot = Baloot.getInstance();
    }

    @AfterAll
    public static void tearDown() {
        try {
            Field instance = Baloot.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Error resetting singleton instance", e);
        }
    }

    @Test
    public void testRateCommodity() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setUsername("ali");
        user2.setUsername("amin");
        user3.setUsername("amir");
        baloot.addUser(user1);
        baloot.addUser(user2);
        baloot.addUser(user3);

        Commodity commodity1 = new Commodity();
        commodity1.setId(1);
        commodity1.setRating(5);
        baloot.addCommodity(commodity1);

        user1.addRatedCommodities(1, 7);
        user2.addRatedCommodities(1, 6);
        user3.addRatedCommodities(1, 2);

        assertEquals((5 + 7 + 6 + 2) / 4, commodity1.getRating(), 0.001);
    }

    @Test
    public void testRateCommodityWithRetractingRate() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setUsername("ali");
        user2.setUsername("amin");
        user3.setUsername("amir");
        baloot.addUser(user1);
        baloot.addUser(user2);
        baloot.addUser(user3);

        Commodity commodity1 = new Commodity();
        commodity1.setId(1);
        commodity1.setRating(5);
        baloot.addCommodity(commodity1);

        user1.addRatedCommodities(1, 7);
        user2.addRatedCommodities(1, 6);
        user3.addRatedCommodities(1, 2);
        user1.addRatedCommodities(1, 10);

        assertEquals((5 + 10 + 6 + 2) / 4, commodity1.getRating(), 0.001);
    }
}
