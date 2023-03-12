import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Baloot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static defines.defines.*;
import static org.junit.Assert.*;

public class rateCommodityTest {
    private Baloot baloot;
    private final String provider_info_file_path = "src/test/java/info/provider.json";
    private final String commodity_info_file_path = "src/test/java/info/commodity.json";
    private final String rate_commodity_info_file_path = "src/test/java/info/rate_commodity.json";
    private final String user_info_file_path = "src/test/java/info/user.json";
    private Map<String, Object> provider_info = utils.read_json_file(provider_info_file_path);
    private Map<String, Object> commodity_info = utils.read_json_file(commodity_info_file_path);
    private Map<String, Object> rate_commodity_info = utils.read_json_file(rate_commodity_info_file_path);
    private Map<String, Object> user_info = utils.read_json_file(user_info_file_path);

    @Before
    public void setup() {
        this.baloot = new Baloot();
    }

    @Test
    public void test_rate_is_out_of_1_to_10_range() {
        rate_commodity_info.put("score", 30);

        baloot.add_provider(utils.get_json_string(provider_info));
        baloot.add_commodity(utils.get_json_string(commodity_info));
        ObjectNode response = baloot.rateCommodity(utils.get_json_string(rate_commodity_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_INVALID_SCORE_RANGE, response.get("data").asText());
    }

    @Test
    public void Test_commodity_rating_with_a_non_existent_user() {
        baloot.add_provider(utils.get_json_string(provider_info));
        baloot.add_commodity(utils.get_json_string(commodity_info));
        ObjectNode response = baloot.rateCommodity(utils.get_json_string(rate_commodity_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_NOT_EXISTENT_USER, response.get("data").asText());

    }

    @Test
    public void Test_commodity_rating_with_a_non_existent_commodity() {
        baloot.add_provider(utils.get_json_string(provider_info));
        baloot.add_user(utils.get_json_string(user_info));
        ObjectNode response = baloot.rateCommodity(utils.get_json_string(rate_commodity_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_NOT_EXISTENT_COMMODITY, response.get("data").asText());
    }

    @Test
    public void test_commodity_rating_with_non_existent_rate() {
        baloot.add_provider(utils.get_json_string(provider_info));

        commodity_info.put("rating", 5);
        baloot.add_commodity(utils.get_json_string(commodity_info));

        user_info.put("username", "user1");
        baloot.add_user(utils.get_json_string(user_info));

        user_info.put("username", "user2");
        baloot.add_user(utils.get_json_string(user_info));

        rate_commodity_info.put("score", 9);
        rate_commodity_info.put("username", "user1");
        baloot.rateCommodity(utils.get_json_string(rate_commodity_info));

        rate_commodity_info.put("score", 10);
        rate_commodity_info.put("username", "user2");
        baloot.rateCommodity(utils.get_json_string(rate_commodity_info));

        ObjectNode response = baloot.getCommodityById("{\"id\": 1}");

        assertEquals(8.0, response.get("data").get("rating").asDouble(), 0.01);
    }

    @Test
    public void test_commodity_rating_with_existent_rate() {
        baloot.add_provider(utils.get_json_string(provider_info));

        commodity_info.put("rating", 5.5);
        baloot.add_commodity(utils.get_json_string(commodity_info));

        user_info.put("username", "user1");
        baloot.add_user(utils.get_json_string(user_info));

        user_info.put("username", "user2");
        baloot.add_user(utils.get_json_string(user_info));

        rate_commodity_info.put("score", 9);
        rate_commodity_info.put("username", "user1");
        baloot.rateCommodity(utils.get_json_string(rate_commodity_info));

        rate_commodity_info.put("score", 10);
        rate_commodity_info.put("username", "user1");
        baloot.rateCommodity(utils.get_json_string(rate_commodity_info));

        ObjectNode response = baloot.getCommodityById("{\"id\": 1}");

        assertEquals(7.75, response.get("data").get("rating").asDouble(), 0.01);
    }

    @Test
    public void test_commodity_rating_success() {
        baloot.add_provider(utils.get_json_string(provider_info).toString());
        baloot.add_commodity(utils.get_json_string(commodity_info));
        baloot.add_user(utils.get_json_string(user_info));
        ObjectNode response = baloot.rateCommodity(utils.get_json_string(rate_commodity_info));

        assertTrue(response.get("success").asBoolean());
        assertEquals(RATE_TO_COMMODITY_RESPONSE, response.get("data").asText());
    }

    @After
    public void teardown() {
        this.baloot = null;
    }
}
