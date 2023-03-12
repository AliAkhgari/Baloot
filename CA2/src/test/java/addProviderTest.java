import com.fasterxml.jackson.databind.node.ObjectNode;
import application.Baloot;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static defines.defines.ADDED_SUCCESSFULLY_RESPONSE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class addProviderTest {
    private static Baloot baloot;
    private final String provider_info_file_path = "src/test/java/info/provider.json";
    private Map<String, Object> provider_info = utils.read_json_file(provider_info_file_path);

    @Before
    public void setup() {
        baloot = new Baloot();
    }

    @Test
    public void test_add_provider_success() {
        provider_info.put("name", "provider1");
        ObjectNode response = baloot.add_provider(utils.get_json_string(provider_info));

        assertTrue(response.get("success").asBoolean());
        assertEquals(String.format(ADDED_SUCCESSFULLY_RESPONSE, "provider1"), response.get("data").asText());
    }

    @AfterClass
    public static void teardown() {
        baloot = null;
    }
}
