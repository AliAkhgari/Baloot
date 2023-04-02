package Unit;

import application.Baloot;
import entities.Commodity;
import entities.User;
import exceptions.AlreadyInBuyList;
import exceptions.CommodityIsNotInBuyList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static defines.Errors.COMMODITY_IS_ALREADY_IN_THE_BUY_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GetBuyListTest {
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
    public void testSuccess() throws AlreadyInBuyList {
        User user1 = new User();
        user1.setUsername("ali");
        baloot.addUser(user1);

        Commodity commodity1 = new Commodity();
        commodity1.setId(1);
        baloot.addCommodity(commodity1);

        Commodity commodity2 = new Commodity();
        commodity1.setId(2);
        baloot.addCommodity(commodity2);

        user1.addBuyItem(commodity1);
        user1.addBuyItem(commodity2);

        ArrayList<Commodity> expected = new ArrayList<>(Arrays.asList(commodity1, commodity2));

        assertEquals(expected, user1.getBuyList());
    }

    @Test
    public void testAfterRemovingItem() throws AlreadyInBuyList, CommodityIsNotInBuyList {
        User user1 = new User();
        user1.setUsername("ali");
        baloot.addUser(user1);

        Commodity commodity1 = new Commodity();
        commodity1.setId(1);
        baloot.addCommodity(commodity1);

        Commodity commodity2 = new Commodity();
        commodity1.setId(2);
        baloot.addCommodity(commodity2);

        Commodity commodity3 = new Commodity();
        commodity1.setId(3);
        baloot.addCommodity(commodity3);

        user1.addBuyItem(commodity1);
        user1.addBuyItem(commodity2);
        user1.addBuyItem(commodity3);

        user1.removeItemFromBuyList(commodity2);

        ArrayList<Commodity> expected = new ArrayList<>(Arrays.asList(commodity1, commodity3));

        assertEquals(expected, user1.getBuyList());
    }

    @Test
    public void testAddingExistentItem() throws CommodityIsNotInBuyList {
        User user1 = new User();
        user1.setUsername("ali");
        baloot.addUser(user1);

        Commodity commodity1 = new Commodity();
        commodity1.setId(1);
        baloot.addCommodity(commodity1);

        Commodity commodity2 = new Commodity();
        commodity1.setId(2);
        baloot.addCommodity(commodity2);

        Commodity commodity3 = new Commodity();
        commodity1.setId(3);
        baloot.addCommodity(commodity3);

        try {
            user1.addBuyItem(commodity1);
            user1.addBuyItem(commodity2);
            user1.addBuyItem(commodity2);
            fail("Expected exception was not thrown.");
        } catch (Exception e) {
            assertEquals(COMMODITY_IS_ALREADY_IN_THE_BUY_LIST, e.getMessage());
        }
    }
}
