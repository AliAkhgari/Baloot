//import com.fasterxml.jackson.databind.node.ObjectNode;
//import application.Baloot;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Map;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class getCommoditiesByCategoryTest {
//    private static Baloot baloot;
//    private final String provider_info_file_path = "src/test/java/info/provider.json";
//    private final String commodity_info_file_path = "src/test/java/info/commodity.json";
//    private Map<String, Object> provider_info = utils.read_json_file(provider_info_file_path);
//    private Map<String, Object> commodity_info = utils.read_json_file(commodity_info_file_path);
//
//    @Before
//    public void setup() {
//        baloot = new Baloot();
//    }
//
//    @Test
//    public void test_get_commodities_by_category_with_no_occurrence() {
//        baloot.add_provider(utils.get_json_string(provider_info));
//
//        commodity_info.put("id", 1);
//        commodity_info.put("name", "Headphone");
//        commodity_info.put("providerId", 1);
//        commodity_info.put("price", 35000);
//        commodity_info.put("categories", new ArrayList<>(Arrays. asList("Tech", "Phone")));
//        commodity_info.put("rating", 5.5);
//        commodity_info.put("inStock", 50);
//        baloot.add_commodity(utils.get_json_string(commodity_info));
//
//        ObjectNode response = baloot.getCommoditiesByCategory("{\"category\": \"art\"}");
//
//        assertTrue(response.get("success").asBoolean());
//        assertEquals("{\"commoditiesListByCategory\":[]}", response.get("data").toString());
//    }
//
//    @Test
//    public void test_get_commodities_by_category_with_one_occurrence() {
//        baloot.add_provider(utils.get_json_string(provider_info).toString());
//
//        commodity_info.put("id", 1);
//        commodity_info.put("name", "Headphone");
//        commodity_info.put("providerId", 1);
//        commodity_info.put("price", 35000);
//        commodity_info.put("categories", new ArrayList<>(Arrays. asList("Tech", "Phone")));
//        commodity_info.put("rating", 5.5);
//        commodity_info.put("inStock", 50);
//        baloot.add_commodity(utils.get_json_string(commodity_info));
//
//        ObjectNode response = baloot.getCommoditiesByCategory("{\"category\": \"Tech\"}");
//
//        String expected = "{\"commoditiesListByCategory\":[{\"id\":1,\"name\":\"Headphone\",\"providerId\":1,\"price\":35000,\"categories\":[\"Tech\",\"Phone\"],\"rating\":5.5}]}";
//        assertTrue(response.get("success").asBoolean());
//        assertEquals(expected, response.get("data").toString());
//    }
//
//    @Test
//    public void test_get_commodities_by_category_with_more_than_one_occurrence() {
//        baloot.add_provider(utils.get_json_string(provider_info).toString());
//
//        commodity_info.put("id", 1);
//        commodity_info.put("name", "Headphone");
//        commodity_info.put("providerId", 1);
//        commodity_info.put("price", 35000);
//        commodity_info.put("categories", new ArrayList<>(Arrays. asList("Tech", "Phone")));
//        commodity_info.put("rating", 5.5);
//        commodity_info.put("inStock", 50);
//        baloot.add_commodity(utils.get_json_string(commodity_info));
//
//        commodity_info.put("id", 2);
//        commodity_info.put("name", "iPhone");
//        commodity_info.put("providerId", 1);
//        commodity_info.put("price", 5000000);
//        commodity_info.put("categories", new ArrayList<>(Arrays. asList("Tech")));
//        commodity_info.put("rating", 8.5);
//        commodity_info.put("inStock", 100);
//        baloot.add_commodity(utils.get_json_string(commodity_info));
//
//        ObjectNode response = baloot.getCommoditiesByCategory("{\"category\": \"Tech\"}");
//
//        String expected = "{\"commoditiesListByCategory\":[{\"id\":1,\"name\":\"Headphone\",\"providerId\":1,\"price\":35000,\"categories\":[\"Tech\",\"Phone\"],\"rating\":5.5},{\"id\":2,\"name\":\"iPhone\",\"providerId\":1,\"price\":5000000,\"categories\":[\"Tech\"],\"rating\":8.5}]}";
//        assertTrue(response.get("success").asBoolean());
//        assertEquals(expected, response.get("data").toString());
//    }
//
//    @After
//    public void teardown() {
//        baloot = null;
//    }
//}
