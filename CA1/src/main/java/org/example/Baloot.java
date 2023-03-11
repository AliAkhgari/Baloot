package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import static org.example.defines.*;

// todo: refactor test class names
public class Baloot {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Provider> providers = new ArrayList<Provider>();
    private ArrayList<Commodity> commodities = new ArrayList<Commodity>();

    public ObjectNode add_user(String user_info) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> user_info_map = null;
        try {
            user_info_map = objectMapper.readValue(user_info, typeRef);
            String username = (String) user_info_map.get("username");

            if (!is_username_valid(username)) {
                FailureResponse failureResponse = new FailureResponse(ERROR_INVALID_USERNAME);
                return failureResponse.raise_exception();
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            int user_index = find_user(username);
            if (user_index == -1) {
                User new_user = objectMapper.readValue(user_info, User.class);
                users.add(new_user);

                success_response.put("success", true);
                success_response.put("data", String.format(ADDED_SUCCESSFULLY_RESPONSE, username));
                return success_response;
            }
            else {
                update_existing_user_info(user_index, user_info_map);

                success_response.put("success", true);
                success_response.put("data", String.format(USER_DATA_UPDATED, username));
                return success_response;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectNode add_provider(String provider_info) {
        try {
            Provider new_provider = objectMapper.readValue(provider_info, Provider.class);
            providers.add(new_provider);

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
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> commodity_info_map = null;
        try {
            commodity_info_map = objectMapper.readValue(commodity_info, typeRef);
            if (!check_if_provider_exists((Integer) commodity_info_map.get("providerId"))) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_PROVIDER);
                return failureResponse.raise_exception();
            }

            Commodity new_commodity = objectMapper.readValue(commodity_info, Commodity.class);
            commodities.add(new_commodity);

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
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode success_response = mapper.createObjectNode();

        success_response.put("success", true);
        ObjectNode data = mapper.createObjectNode();
        success_response.put("data", data);
        ArrayNode commodities_list = data.putArray("commoditiesList");

        for (Commodity commodity : commodities) {
            ObjectNode commodity_info = mapper.createObjectNode();
            commodity_info.put("id", commodity.getId());
            commodity_info.put("name", commodity.getName());
            commodity_info.put("providerId", commodity.getProviderId());
            commodity_info.put("price", commodity.getPrice());

            ArrayNode categories = commodity_info.putArray("categories");
            for (String category: commodity.getCategories()) {
                categories.add(category);
            }

            commodity_info.put("rating", commodity.getRating());
            commodity_info.put("inStock", commodity.getInStock());

            commodities_list.add(commodity_info);
        }

        return success_response;
    }

    public ObjectNode rateCommodity(String rate_commodity_info) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> rate_commodity_info_map = null;
        try {
            rate_commodity_info_map = objectMapper.readValue(rate_commodity_info, typeRef);

            String username = (String) rate_commodity_info_map.get("username");
            int user_index = find_user(username);
            int commodityId = (int) rate_commodity_info_map.get("commodityId");
            int commodity_index = find_commodity(commodityId);
            int score = (int) rate_commodity_info_map.get("score");

            if (!is_score_between_one_to_ten(score)) {
                FailureResponse failureResponse = new FailureResponse(ERROR_INVALID_SCORE_RANGE);
                return failureResponse.raise_exception();
            }

            if (find_user(username) == -1) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_USER);
                return failureResponse.raise_exception();
            }

            if (find_commodity(commodityId) == -1) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_COMMODITY);
                return failureResponse.raise_exception();
            }

            users.get(user_index).add_rated_commodities(commodityId, score);
            commodities.get(commodity_index).add_rate(username, score);

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

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> add_to_buy_list_info_map = null;
        try {
            add_to_buy_list_info_map = objectMapper.readValue(add_to_buy_list_info, typeRef);
            String username = (String) add_to_buy_list_info_map.get("username");
            int commodity_id = (int) add_to_buy_list_info_map.get("commodityId");
            int user_index = find_user(username);
            int commodity_index = find_commodity(commodity_id);
            if (user_index == -1) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_USER);
                return failureResponse.raise_exception();
            }
            if (commodity_index == -1) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_COMMODITY);
                return failureResponse.raise_exception();
            }
            if (users.get(user_index).getBuy_list().contains(commodities.get(commodity_index))) {
                FailureResponse failureResponse = new FailureResponse(ERROR_COMMODITY_IS_ALREADY_IN_BUY_LIST);
                return failureResponse.raise_exception();
            }
            if (commodities.get(commodity_index).getInStock() <= 0) {
                FailureResponse failureResponse = new FailureResponse(ERROR_COMMODITY_IS_NOT_IN_STOCK);
                return failureResponse.raise_exception();
            }
            commodities.get(commodity_index).decreaseInStock();

            users.get(user_index).add_buy_item(commodities.get(commodity_index));

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
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> remove_from_buy_list_info_map = null;
        try {
            remove_from_buy_list_info_map = objectMapper.readValue(remove_from_buy_list_info, typeRef);
            String username = (String) remove_from_buy_list_info_map.get("username");
            int commodity_id = (int) remove_from_buy_list_info_map.get("commodityId");
            int user_index = find_user(username);
            int commodity_index = find_commodity(commodity_id);
            if (user_index == -1) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_USER);
                return failureResponse.raise_exception();
            }
            if (commodity_index == -1) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_COMMODITY);
                return failureResponse.raise_exception();
            }
            if (! is_item_in_buy_list(user_index, commodity_id)) {
                FailureResponse failureResponse = new FailureResponse(ERROR_COMMODITY_IS_NOT_IN_BUY_LIST);
                return failureResponse.raise_exception();
            }

            users.get(user_index).remove_item_from_buy_list(commodities.get(commodity_index));

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
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> get_commodity_by_id_info_map = null;
        try {
            get_commodity_by_id_info_map = objectMapper.readValue(get_commodity_by_id_info, typeRef);
            int commodity_id = (int) get_commodity_by_id_info_map.get("id");
            int commodity_index = find_commodity(commodity_id);
            if (commodity_index == -1) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_COMMODITY);
                return failureResponse.raise_exception();
            }
            Commodity selected_commodity = commodities.get(commodity_index);
            Provider commodity_provider = providers.get(find_provider(selected_commodity.getProviderId()));

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
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
        Map<String, Object> get_commodities_by_category_info_map = null;
        try {
            get_commodities_by_category_info_map = objectMapper.readValue(get_commodities_by_category_info, typeRef);
            String category = (String) get_commodities_by_category_info_map.get("category");
            ArrayList<String> selected_commodities = new ArrayList<String>();

            for (Commodity commodity : commodities) {
                if (commodity.getCategories().contains(category)) {
                    selected_commodities.add(new Gson().toJson(commodity));
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);

            ObjectNode data = mapper.createObjectNode();
            success_response.put("data", data);

            ArrayNode commoditiesListByCategory = data.putArray("commoditiesListByCategory");
            for (Commodity commodity : commodities) {
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
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> get_buy_list_info_map = null;
        try {
            get_buy_list_info_map = objectMapper.readValue(get_buy_list_info, typeRef);
            String username = (String) get_buy_list_info_map.get("username");

            int user_index = find_user(username);
            if (user_index == -1) {
                FailureResponse failureResponse = new FailureResponse(ERROR_NOT_EXISTENT_USER);
                return failureResponse.raise_exception();
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode success_response = mapper.createObjectNode();

            success_response.put("success", true);

            ObjectNode data = mapper.createObjectNode();
            success_response.put("data", data);

            ArrayNode arrayNode = data.putArray("buyList");

            for (Commodity item: users.get(user_index).getBuy_list()) {
                ObjectNode buy_list_item = mapper.createObjectNode();
                buy_list_item.put("id", item.getId());
                buy_list_item.put("name", item.getName());
                buy_list_item.put("providerId", item.getProviderId());
                buy_list_item.put("price", item.getPrice());

                ArrayNode categories = buy_list_item.putArray("categories");
                for (String category: item.getCategories()) {
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
        if (username.matches("\\w+"))
            return true;
        return false;
    }

    private boolean is_item_in_buy_list(int user_index, int commodity_id) {
        for (Commodity commodity : users.get(user_index).getBuy_list()) {
            if (commodity.getId() == commodity_id) {
                return true;
            }
        }
        return false;
    }

    private boolean is_score_between_one_to_ten(Integer score) {
        return score >= 1 && score <= 10;
    }

    public int find_user(String user_name) {
        for (User user : users) {
            if (user.getUsername().equals(user_name)) {
                return users.indexOf(user);
            }
        }
        return -1;
    }

    public void update_existing_user_info(int user_index, Map<String, Object> user_info) {
        User user = users.get(user_index);
        user.setPassword((String) user_info.get("password"));
        user.setEmail((String) user_info.get("email"));
        user.setAddress((String) user_info.get("address"));
        user.setCredit((int) user_info.get("credit"));
        user.setBirthDate((String) user_info.get("birthDate"));
    }

    public boolean check_if_provider_exists(int provider_id) {
        for (Provider provider : providers) {
            if (provider.getId() == provider_id) {
                return true;
            }
        }
        return false;
    }

    public int find_commodity(int commodity_id) {
        for (Commodity commodity : commodities) {
            if (commodity.getId() == commodity_id) {
                return commodities.indexOf(commodity);
            }
        }
        return -1;
    }

    public int find_provider(int provider_id) {
        for (Provider provider : providers) {
            if (provider.getId() == provider_id) {
                return providers.indexOf(provider);
            }
        }
        return -1;
    }

}
