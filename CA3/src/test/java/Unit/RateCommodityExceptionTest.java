package Unit;

import application.Baloot;
import entities.Commodity;
import entities.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static defines.Errors.*;
import static org.junit.jupiter.api.Assertions.*;

public class RateCommodityExceptionTest {
    private Baloot baloot;
    private String userId;
    private String commodityId;
    private String rate;
    private String expectedExceptionMessage;

    @BeforeEach
    public void setup() {
        baloot = new Baloot();
        User user1 = new User();
        user1.setUsername("ali");
        baloot.addUser(user1);

        Commodity commodity1 = new Commodity();
        commodity1.setId(1);
        baloot.addCommodity(commodity1);
    }

    @Test
    @DisplayName("Rate Commodity Exceptions Test")
    public void testRateCommodityExceptions() {
        assertThrows(Exception.class, () -> baloot.rateCommodity(userId, commodityId, rate),
                expectedExceptionMessage);
    }

    @AfterEach
    public void teardown() {
        baloot = null;
    }

    @ParameterizedTest(name = "#{index} - Test with userId={0}, commodityId={1}, rate={2}, expectedExceptionMessage={3}")
    @MethodSource("testData")
    public void testCommodityRateExceptions(String userId, String commodityId, String rate, String expectedExceptionMessage) {
        this.userId = userId;
        this.commodityId = commodityId;
        this.rate = rate;
        this.expectedExceptionMessage = expectedExceptionMessage;

        assertThrows(Exception.class, () -> baloot.rateCommodity(userId, commodityId, rate),
                expectedExceptionMessage);
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(null, "1", "5", MISSING_USER_ID),
                Arguments.of("ali", null, "5", MISSING_COMMODITY_ID),
                Arguments.of("ali", "1", "5.5", INVALID_RATE_FORMAT),
                Arguments.of("ali", "1", "5a", INVALID_RATE_FORMAT),
                Arguments.of("ali", "1", "11", INVALID_RATE_RANGE),
                Arguments.of("ali", "1", "0", INVALID_RATE_RANGE),
                Arguments.of("ali", "1", "-1", INVALID_RATE_RANGE),
                Arguments.of("amin", "1", "1", NOT_EXISTENT_USER),
                Arguments.of("ali", "2", "1", NOT_EXISTENT_COMMODITY)
        );
    }
}
