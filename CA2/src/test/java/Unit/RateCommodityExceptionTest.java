package Unit;

import application.Baloot;
import entities.Commodity;
import entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static defines.Errors.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class RateCommodityExceptionTest {
    private Baloot baloot;
    private final String userId;
    private final String commodityId;
    private final String rate;
    private final String expectedExceptionMessage;

    public RateCommodityExceptionTest(String userId, String commodityId, String rate, String expectedExceptionMessage) {
        this.userId = userId;
        this.commodityId = commodityId;
        this.rate = rate;
        this.expectedExceptionMessage = expectedExceptionMessage;
    }

    @Before
    public void setup() {
        baloot = new Baloot();
    }

    @Test
    public void testRateCommodityExceptions() {
        User user1 = new User();
        user1.setUsername("ali");
        baloot.addUser(user1);

        Commodity commodity1 = new Commodity();
        commodity1.setId(1);
        baloot.addCommodity(commodity1);

        try {
            baloot.rateCommodity(userId, commodityId, rate);
            fail("Expected exception was not thrown.");
        } catch (Exception e) {
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }

    @After
    public void teardown() {
        baloot = null;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {null, "1", "5", MISSING_USER_ID},
                {"ali", null, "5", MISSING_COMMODITY_ID},
                {"ali", "1", "5.5", INVALID_RATE_FORMAT},
                {"ali", "1", "5a", INVALID_RATE_FORMAT},
                {"ali", "1", "11", INVALID_RATE_RANGE},
                {"ali", "1", "0", INVALID_RATE_RANGE},
                {"ali", "1", "-1", INVALID_RATE_RANGE},
                {"amin", "1", "1", NOT_EXISTENT_USER},
                {"ali", "2", "1", NOT_EXISTENT_COMMODITY}
        });
    }
}
