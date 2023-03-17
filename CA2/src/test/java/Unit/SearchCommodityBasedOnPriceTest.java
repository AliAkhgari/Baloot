package Unit;

import application.Baloot;
import entities.Commodity;
import exceptions.InvalidPriceRange;
import exceptions.MissingStartOrEndPrice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static defines.Errors.INVALID_PRICE_RANGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SearchCommodityBasedOnPriceTest {
    private Baloot baloot;

    @Before
    public void setup() {
        baloot = new Baloot();
    }

    @After
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
