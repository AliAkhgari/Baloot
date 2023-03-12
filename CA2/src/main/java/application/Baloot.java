package application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Database;
import entities.Commodity;
import entities.FailureResponse;
import entities.Provider;
import entities.User;

import java.util.Map;

import static defines.defines.*;

// todo: refactor test class names
public class Baloot {
    private final ObjectMapper objectMapper = new ObjectMapper();
    Database database = new Database();

    public ObjectNode add_user(String user_info) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
        };
        Map<String, Object> user_info_map;
        try {
            user_info_map = objectMapper.readValue(user_info, typeRef);
            String username = (String) user_info_map.get("username");

            if (!is_username_valid(username))
                return new FailureResponse(ERROR_INVALID_USERNAME).raise_exception();

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            User user = getUserById(username);

            if (user == null) {
                User new_user = objectMapper.readValue(user_info, User.class);
                database.addUser(new_user);

                success_response.put("success", true);
                success_response.put("data", String.format(ADDED_SUCCESSFULLY_RESPONSE, username));
            } else {
                update_existing_user_info(user, user_info_map);

                success_response.put("success", true);
                success_response.put("data", String.format(USER_DATA_UPDATED, username));
            }
            return success_response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectNode add_provider(String provider_info) {
        try {
            Provider new_provider = objectMapper.readValue(provider_info, Provider.class);
            database.addProvider(new_provider);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);
            success_response.put("data", String.format(ADDED_SUCCESSFULLY_RESPONSE, new_provider.getName()));

            return success_response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectNode add_commodity(String commodity_info) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
        };
        Map<String, Object> commodity_info_map;
        try {
            commodity_info_map = objectMapper.readValue(commodity_info, typeRef);
            if (getProviderById((Integer) commodity_info_map.get("providerId")) == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_PROVIDER);
                return failureResponse.raise_exception();
            }

            Commodity new_commodity = objectMapper.readValue(commodity_info, Commodity.class);
            database.addCommodity(new_commodity);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);
            success_response.put("data", String.format(ADDED_SUCCESSFULLY_RESPONSE, new_commodity.getName()));

            return success_response;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectNode getCommoditiesList() {
        ObjectNode success_response = objectMapper.createObjectNode();

        success_response.put("success", true);
        ObjectNode data = objectMapper.createObjectNode();
        success_response.put("data", data);
        ArrayNode commodities_list = data.putArray("commoditiesList");

        for (Commodity commodity : database.getCommodities()) {
            ObjectNode commodity_info = objectMapper.createObjectNode();
            commodity_info.put("id", commodity.getId());
            commodity_info.put("name", commodity.getName());
            commodity_info.put("providerId", commodity.getProviderId());
            commodity_info.put("price", commodity.getPrice());

            ArrayNode categories = commodity_info.putArray("categories");
            for (String category : commodity.getCategories()) {
                categories.add(category);
            }

            commodity_info.put("rating", commodity.getRating());
            commodity_info.put("inStock", commodity.getInStock());

            commodities_list.add(commodity_info);
        }

        return success_response;
    }

    public ObjectNode rateCommodity(String rate_commodity_info) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
        };
        Map<String, Object> rate_commodity_info_map;
        try {
            rate_commodity_info_map = objectMapper.readValue(rate_commodity_info, typeRef);

            String username = (String) rate_commodity_info_map.get("username");
            User user = getUserById(username);

            int commodity_id = (int) rate_commodity_info_map.get("commodityId");
            Commodity commodity = getCommodityById(commodity_id);
            int score = (int) rate_commodity_info_map.get("score");

            if (!is_score_between_one_to_ten(score)) {
                FailureResponse failureResponse = new FailureResponse(ERROR_INVALID_SCORE_RANGE);
                return failureResponse.raise_exception();
            }

            if (user == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_USER);
                return failureResponse.raise_exception();
            }

            if (commodity == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_COMMODITY);
                return failureResponse.raise_exception();
            }

            user.add_rated_commodities(commodity_id, score);
            commodity.add_rate(username, score);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);
            success_response.put("data", RATE_TO_COMMODITY_RESPONSE);

            return success_response;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectNode addToBuyList(String add_to_buy_list_info) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
        };
        Map<String, Object> add_to_buy_list_info_map;
        try {
            add_to_buy_list_info_map = objectMapper.readValue(add_to_buy_list_info, typeRef);
            String username = (String) add_to_buy_list_info_map.get("username");
            int commodity_id = (int) add_to_buy_list_info_map.get("commodityId");

            User user = getUserById(username);
            Commodity commodity = getCommodityById(commodity_id);

            if (user == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_USER);
                return failureResponse.raise_exception();
            }
            if (commodity == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_COMMODITY);
                return failureResponse.raise_exception();
            }
            if (user.getBuy_list().contains(commodity)) {
                FailureResponse failureResponse = new FailureResponse(ERROR_COMMODITY_IS_ALREADY_IN_BUY_LIST);
                return failureResponse.raise_exception();
            }
            if (commodity.getInStock() <= 0) {
                FailureResponse failureResponse = new FailureResponse(ERROR_COMMODITY_IS_NOT_IN_STOCK);
                return failureResponse.raise_exception();
            }
            commodity.decreaseInStock();

            user.add_buy_item(commodity);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);
            success_response.put("data", String.format(ADDED_SUCCESSFULLY_RESPONSE, commodity_id));

            return success_response;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public ObjectNode removeFromBuyList(String remove_from_buy_list_info) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
        };
        Map<String, Object> remove_from_buy_list_info_map;
        try {
            remove_from_buy_list_info_map = objectMapper.readValue(remove_from_buy_list_info, typeRef);
            String username = (String) remove_from_buy_list_info_map.get("username");
            int commodity_id = (int) remove_from_buy_list_info_map.get("commodityId");

            User user = getUserById(username);
            Commodity commodity = getCommodityById(commodity_id);

            if (user == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_USER);
                return failureResponse.raise_exception();
            }
            if (commodity == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_COMMODITY);
                return failureResponse.raise_exception();
            }
            if (!is_item_in_buy_list(user, commodity_id)) {
                FailureResponse failureResponse = new FailureResponse(ERROR_COMMODITY_IS_NOT_IN_BUY_LIST);
                return failureResponse.raise_exception();
            }

            user.remove_item_from_buy_list(commodity);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);
            success_response.put("data", String.format(REMOVE_COMMODITY_FROM_BUY_LIST_RESPONSE, commodity_id, username));

            return success_response;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public ObjectNode getCommodityById(String get_commodity_by_id_info) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
        };
        Map<String, Object> get_commodity_by_id_info_map;
        try {
            get_commodity_by_id_info_map = objectMapper.readValue(get_commodity_by_id_info, typeRef);
            int commodity_id = (int) get_commodity_by_id_info_map.get("id");

            Commodity selected_commodity = getCommodityById(commodity_id);
            if (selected_commodity == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_COMMODITY);
                return failureResponse.raise_exception();
            }

            Provider commodity_provider = getProviderById(selected_commodity.getProviderId());

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();
            success_response.put("success", true);

            ObjectNode data = mapper.createObjectNode();

            data.put("id", selected_commodity.getId());
            data.put("name", selected_commodity.getName());
            data.put("provider", commodity_provider.getName());
            data.put("price", selected_commodity.getPrice());

            ArrayNode arrayNode = data.putArray("categories");
            for (String item : selected_commodity.getCategories()) {
                arrayNode.add(item);
            }
            data.put("rating", selected_commodity.getRating());

            success_response.put("data", data);

            return success_response;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public ObjectNode getCommoditiesByCategory(String get_commodities_by_category_info) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
        };
        Map<String, Object> get_commodities_by_category_info_map;
        try {
            get_commodities_by_category_info_map = objectMapper.readValue(get_commodities_by_category_info, typeRef);
            String category = (String) get_commodities_by_category_info_map.get("category");

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);

            ObjectNode data = mapper.createObjectNode();
            success_response.put("data", data);

            ArrayNode commoditiesListByCategory = data.putArray("commoditiesListByCategory");
            for (Commodity commodity : database.getCommodities()) {
                if (commodity.getCategories().contains(category)) {
                    ObjectNode category_node = mapper.createObjectNode();
                    category_node.put("id", commodity.getId());
                    category_node.put("name", commodity.getName());
                    category_node.put("providerId", commodity.getProviderId());
                    category_node.put("price", commodity.getPrice());

                    ArrayNode arrayNode = category_node.putArray("categories");
                    for (String item : commodity.getCategories()) {
                        arrayNode.add(item);
                    }
                    commoditiesListByCategory.add(category_node);
                    category_node.put("rating", commodity.getRating());
                }
            }

            return success_response;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectNode getBuyList(String get_buy_list_info) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
        };
        Map<String, Object> get_buy_list_info_map;
        try {
            get_buy_list_info_map = objectMapper.readValue(get_buy_list_info, typeRef);
            String username = (String) get_buy_list_info_map.get("username");

            User user = getUserById(username);
            if (user == null) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_USER);
                return failureResponse.raise_exception();
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);

            ObjectNode data = mapper.createObjectNode();
            success_response.put("data", data);

            ArrayNode arrayNode = data.putArray("buyList");

            for (Commodity item : user.getBuy_list()) {
                ObjectNode buy_list_item = mapper.createObjectNode();
                buy_list_item.put("id", item.getId());
                buy_list_item.put("name", item.getName());
                buy_list_item.put("providerId", item.getProviderId());
                buy_list_item.put("price", item.getPrice());

                ArrayNode categories = buy_list_item.putArray("categories");
                for (String category : item.getCategories()) {
                    categories.add(category);
                }

                buy_list_item.put("rating", item.getRating());
                arrayNode.add(buy_list_item);
            }

            return success_response;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean is_username_valid(String username) {
        return username.matches("\\w+");
    }

    private boolean is_item_in_buy_list(User user, int commodity_id) {
        for (Commodity commodity : user.getBuy_list()) {
            if (commodity.getId() == commodity_id) {
                return true;
            }
        }
        return false;
    }

    private boolean is_score_between_one_to_ten(Integer score) {
        return score >= 1 && score <= 10;
    }

    public void update_existing_user_info(User user, Map<String, Object> user_info) {
        user.setPassword((String) user_info.get("password"));
        user.setEmail((String) user_info.get("email"));
        user.setAddress((String) user_info.get("address"));
        user.setCredit((int) user_info.get("credit"));
        user.setBirthDate((String) user_info.get("birthDate"));
    }

    public User getUserById(String user_name) {
        for (User user : database.getUsers())
            if (user.getUsername().equals(user_name))
                return user;

        return null;
    }

    public Provider getProviderById(int provider_id) {
        for (Provider provider : database.getProviders()) {
            if (provider.getId() == provider_id) {
                return provider;
            }
        }
        return null;
    }

    public Commodity getCommodityById(int commodity_id) {
        for (Commodity commodity : database.getCommodities())
            if (commodity.getId() == commodity_id)
                return commodity;

        return null;
    }
}
