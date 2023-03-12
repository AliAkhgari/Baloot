import com.fasterxml.jackson.databind.node.ObjectNode;
import application.Baloot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static defines.defines.*;
import static org.junit.Assert.*;

public class addToBuyListTest {
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

    @After
    public void teardown() {
        baloot = null;
    }

    @Test
    public void test_add_to_buy_list_with_non_existent_user() {
        baloot.add_provider(utils.get_json_string(provider_info));
        baloot.add_commodity(utils.get_json_string(commodity_info));
        ObjectNode response = baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_NOT_EXISTENT_USER, response.get("data").asText());
    }

    @Test
    public void test_add_to_buy_list_with_non_existent_commodity() {
        baloot.add_provider(utils.get_json_string(provider_info));
        baloot.add_user(utils.get_json_string(user_info));
        ObjectNode response = baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_NOT_EXISTENT_COMMODITY, response.get("data").asText());
    }

    @Test
    public void test_add_to_buy_list_with_existent_commodity_in_buy_list() {
        baloot.add_provider(utils.get_json_string(provider_info));
        baloot.add_user(utils.get_json_string(user_info));
        baloot.add_commodity(utils.get_json_string(commodity_info));
        baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));
        ObjectNode response = baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_COMMODITY_IS_ALREADY_IN_BUY_LIST, response.get("data").asText());
    }

    @Test
    public void test_add_to_buy_list_with_in_stock_commodity_equal_to_zero() {
        baloot.add_provider(utils.get_json_string(provider_info));
        baloot.add_user(utils.get_json_string(user_info));
        commodity_info.put("inStock", 0);
        baloot.add_commodity(utils.get_json_string(commodity_info));
        ObjectNode response = baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_COMMODITY_IS_NOT_IN_STOCK, response.get("data").asText());
    }

    @Test
    public void test_add_to_buy_list_with_not_in_stock_commodity_after_buying_different_users() {
        baloot.add_provider(utils.get_json_string(provider_info));
        user_info.put("username", "amin");
        baloot.add_user(utils.get_json_string(user_info));
        user_info.put("username", "ali");
        baloot.add_user(utils.get_json_string(user_info));
        commodity_info.put("inStock", 1);
        baloot.add_commodity(utils.get_json_string(commodity_info));
        add_to_buy_list_info.put("username", "ali");
        baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));
        add_to_buy_list_info.put("username", "amin");
        ObjectNode response = baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_COMMODITY_IS_NOT_IN_STOCK, response.get("data").asText());
    }

    @Test
    public void test_add_to_buy_list_success() {
        baloot.add_provider(utils.get_json_string(provider_info));
        user_info.put("username", "amin");
        baloot.add_user(utils.get_json_string(user_info));
        user_info.put("username", "ali");
        baloot.add_user(utils.get_json_string(user_info));
        commodity_info.put("inStock", 1);
        baloot.add_commodity(utils.get_json_string(commodity_info));
        add_to_buy_list_info.put("username", "amin");
        ObjectNode response = baloot.addToBuyList(utils.get_json_string(add_to_buy_list_info));

        assertTrue(response.get("success").asBoolean());
        assertEquals(String.format(ADDED_SUCCESSFULLY_RESPONSE, 1), response.get("data").asText());
    }


}
