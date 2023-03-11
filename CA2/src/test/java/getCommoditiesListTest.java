import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Baloot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class getCommoditiesListTest {
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
    public void test_get_commodities_list_success() {
        provider_info.put("id", 1);
        baloot.add_provider(utils.get_json_string(provider_info));

        commodity_info.put("providerId", 1);
        baloot.add_commodity(utils.get_json_string(commodity_info));

        ObjectNode response = baloot.getCommoditiesList();

        String expected = "{\"commoditiesList\":[{\"id\":1,\"name\":\"Headphone\",\"providerId\":1,\"price\":35000,\"categories\":[\"Tech\",\"Phone\"],\"rating\":5.5,\"inStock\":50}]}";

        assertTrue(response.get("success").asBoolean());
        assertEquals(expected, response.get("data").toString());
    }

    @After
    public void teardown() {
        baloot = null;
    }
}
