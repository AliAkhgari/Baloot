import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Baloot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static defines.defines.ERROR_NOT_EXISTENT_COMMODITY;
import static org.junit.Assert.*;

public class getCommodityByIdTest {
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
    public void test_get_commodity_by_id_with_non_existent_commodity() {
        baloot.add_provider(utils.get_json_string(provider_info));
        ObjectNode response = baloot.getCommodityById("{\"id\": 2}");

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_NOT_EXISTENT_COMMODITY, response.get("data").asText());
    }

    @Test
    public void test_get_commodity_by_id_success() {
        provider_info.put("id", 1);
        baloot.add_provider(utils.get_json_string(provider_info));

        commodity_info.put("id", 1);
        commodity_info.put("name", "iPhone");
        commodity_info.put("providerId", 1);
        commodity_info.put("price", 35000);
        commodity_info.put("categories", new ArrayList<>(Arrays. asList("Tech", "Phone")));
        commodity_info.put("rating", 5.5);
        commodity_info.put("inStock", 20);
        baloot.add_commodity(utils.get_json_string(commodity_info));

        ObjectNode response = baloot.getCommodityById("{\"id\": 1}");

        String expected = "{\"id\":1,\"name\":\"iPhone\",\"provider\":\"provider1\",\"price\":35000,\"categories\":[\"Tech\",\"Phone\"],\"rating\":5.5}";
        assertTrue(response.get("success").asBoolean());
        assertEquals(expected, response.get("data").toString());
    }

    @After
    public void teardown() {
        baloot = null;
    }
}
