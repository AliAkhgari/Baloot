import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Baloot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static defines.defines.ERROR_NOT_EXISTENT_USER;
import static org.junit.Assert.*;

public class getBuyListTest {
    private static Baloot baloot;
    private final String provider_info_file_path = "src/test/java/info/provider.json";
    private final String commodity_info_file_path = "src/test/java/info/commodity.json";
    private final String user_info_file_path = "src/test/java/info/user.json";
    private final String add_to_buy_list_info_file_path = "src/test/java/info/add_to_buy_list.json";
    private Map<String, Object> provider_info = utils.read_json_file(provider_info_file_path);
    private Map<String, Object> commodity_info = utils.read_json_file(commodity_info_file_path);
    private Map<String, Object> user_info = utils.read_json_file(user_info_file_path);
    private Map<String, Object> add_to_buy_list_info = utils.read_json_file(add_to_buy_list_info_file_path);


    @Before
    public void setup() {
        baloot = new Baloot();
    }

    @Test
    public void test_get_buy_list_non_existent_user() {
        baloot.add_provider(utils.get_json_string(provider_info));

        commodity_info.put("id", 1);
        baloot.add_commodity(utils.get_json_string(commodity_info));

        user_info.put("username", "user1");
        baloot.add_user(utils.get_json_string(user_info));

        ObjectNode response = baloot.getBuyList("{\"username\": \"user2\"}");

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_NOT_EXISTENT_USER, response.get("data").asText());
    }

    @Test
    public void test_get_buy_list_success() {
        baloot.add_provider(utils.get_json_string(provider_info));

        commodity_info.put("id", 1);
        baloot.add_commodity(utils.get_json_string(commodity_info));

        user_info.put("username", "user1");
        baloot.add_user(utils.get_json_string(user_info));

        add_to_buy_list_info.put("username", "user1");
        baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));

        ObjectNode response = baloot.getBuyList("{\"username\": \"user1\"}");

        String expected = "{\"buyList\":[{\"id\":1,\"name\":\"Headphone\",\"providerId\":1,\"price\":35000,\"categories\":[\"Tech\",\"Phone\"],\"rating\":5.5}]}";
        assertTrue(response.get("success").asBoolean());
        assertEquals(expected, response.get("data").toString());
    }

    @After
    public void teardown() {
        baloot = null;
    }
}
