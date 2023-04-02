package Unit;

import application.Baloot;
import entities.Commodity;
import exceptions.InvalidPriceRange;
import exceptions.MissingStartOrEndPrice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static defines.Errors.INVALID_PRICE_RANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SearchCommodityBasedOnPriceTest {
    private Baloot baloot;

    @BeforeEach
    public void setup() {
        baloot = new Baloot();
    }

    @AfterEach
    public void teardown() {
        baloot = null;
    }

    @Test
    public void testSuccess() throws InvalidPriceRange, MissingStartOrEndPrice {
        Commodity commodity1 = new Commodity();
        commodity1.setId(1);
        commodity1.setPrice(90);
        baloot.addCommodity(commodity1);

        Commodity commodity2 = new Commodity();
        commodity2.setId(2);
        commodity2.setPrice(87);
        baloot.addCommodity(commodity2);

        Commodity commodity3 = new Commodity();
        commodity3.setId(3);
        commodity3.setPrice(101);
        baloot.addCommodity(commodity3);

        ArrayList<Commodity> expected = new ArrayList<>(Arrays.asList(commodity1, commodity2));

        assertEquals(expected, baloot.filterCommoditiesByPrice("50", "100"));
    }

    @Test
    public void testInvalidPriceRange() {
        try {
            baloot.filterCommoditiesByPrice("50", "40");
            fail("Expected exception was not thrown.");
        } catch (Exception e) {
            assertEquals(INVALID_PRICE_RANGE, e.getMessage());
        }
    }
}
