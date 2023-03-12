import com.fasterxml.jackson.databind.node.ObjectNode;
import application.Baloot;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static defines.defines.ADDED_SUCCESSFULLY_RESPONSE;
import static defines.defines.ERROR_INVALID_USERNAME;
import static org.junit.Assert.*;

public class addUserTest {
    private static Baloot baloot;
    private final String user_info_file_path = "src/test/java/info/user.json";
    private Map<String, Object> user_info = utils.read_json_file(user_info_file_path);

    @Before
    public void setup() {
        baloot = new Baloot();
    }

    @Test
    public void test_invalid_name_that_has_space() {
        user_info.put("username", "a min");
        ObjectNode response = baloot.add_user(utils.get_json_string(user_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_INVALID_USERNAME, response.get("data").asText());
    }

    @Test
    public void test_invalid_name_that_has_at_sign() {
        user_info.put("username", "@min");
        ObjectNode response = baloot.add_user(utils.get_json_string(user_info));

        assertFalse(response.get("success").asBoolean());
        assertEquals(ERROR_INVALID_USERNAME, response.get("data").asText());
    }

    @Test
    public void test_add_user_success() {
        user_info.put("username", "amin");
        ObjectNode response = baloot.add_user(utils.get_json_string(user_info));

        assertTrue(response.get("success").asBoolean());
        assertEquals(String.format(ADDED_SUCCESSFULLY_RESPONSE, "amin"), response.get("data").asText());
    }

    @AfterClass
    public static void teardown() {
        baloot = null;
    }

}
