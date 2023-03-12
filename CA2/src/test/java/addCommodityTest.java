import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Baloot;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static defines.defines.ADDED_SUCCESSFULLY_RESPONSE;
import static defines.defines.ERROR_NOT_EXISTENT_PROVIDER;
import static org.junit.Assert.*;

public class addCommodityTest {
    private static Baloot baloot;
    private final String provider_info_file_path = "src/test/java/info/provider.json";
    private final String commodity_info_file_path = "src/test/java/info/commodity.json";
    private Map<String, Object> provider_info = utils.read_json_file(provider_info_file_path);
    private Map<String, Object> commodity_info = utils.read_json_file(commodity_info_file_path);

    @Before
    public void setup() {
        baloot = new Baloot();
    }

    @Test
    public void test_add_commodity_success() {
        baloot.add_provider(utils.get_json_string(provider_info));
        ObjectNode response = baloot.add_commodity(utils.get_json_string(commodity_info));

        assertTrue(response.get("success").asBoolean());
        assertEquals(String.format(ADDED_SUCCESSFULLY_RESPONSE, "Headphone"), response.get("data").asText());
    }

    @Test
    public void test_does_not_exist_provider() {
        provider_info.put("id", 1);
        baloot.add_provider(utils.get_json_string(provider_info));

        commodity_info.put("providerId", 2);
        ObjectNode response = baloot.add_commodity(utils.get_json_string(commodity_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(String.format(ERROR_NOT_EXISTENT_PROVIDER), response.get("data").asText());
    }

    @AfterClass
    public static void teardown() {
        baloot = null;
    }
}
