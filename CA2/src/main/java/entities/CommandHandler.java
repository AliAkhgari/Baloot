package entities;


import application.Baloot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static defines.defines.*;

public class CommandHandler {
    private final Baloot baloot;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ObjectNode response;
    public CommandHandler(Baloot baloot) {
        this.baloot = baloot;
    }

    public ObjectNode handleCommand(String commandLine) {
        String command = commandLine.split(" ", 2)[0];

        switch (command) {
            case ADD_USER_COMMAND:
                String user_info = commandLine.split(" ", 2)[1];
                response = baloot.add_user(user_info);

                break;

            case ADD_PROVIDER_COMMAND:
                String provider_info = commandLine.split(" ", 2)[1];
                response = baloot.add_provider(provider_info);

                break;

            case ADD_COMMODITY_COMMAND:
                String commodity_info = commandLine.split(" ", 2)[1];
                response = baloot.add_commodity(commodity_info);

                break;

            case GET_COMMODITIES_LIST_COMMAND:
                response = baloot.getCommoditiesList();

                break;

            case RATE_COMMODITY_COMMAND:
                String rate_commodity_info = commandLine.split(" ", 2)[1];
                response = baloot.rateCommodity(rate_commodity_info);

                break;

            case ADD_TO_BUY_LIST_COMMAND:
                String add_to_buy_list_info = commandLine.split(" ", 2)[1];
                response = baloot.addToBuyList(add_to_buy_list_info);

                break;

            case REMOVE_FROM_BUY_LIST_COMMAND:
                String remove_from_buy_list = commandLine.split(" ", 2)[1];
                response = baloot.removeFromBuyList(remove_from_buy_list);

                break;

            case GET_COMMODITY_BY_ID_COMMAND:
                String get_commodity_by_id_info = commandLine.split(" ", 2)[1];
                response = baloot.getCommodityById(get_commodity_by_id_info);

                break;

            case GET_COMMODITIES_BY_CATEGORY_COMMAND:
                String get_commodities_by_category_info = commandLine.split(" ", 2)[1];
                response = baloot.getCommoditiesByCategory(get_commodities_by_category_info);

                break;

            case GET_BUY_LIST_COMMAND:
                String get_buy_list_info = commandLine.split(" ", 2)[1];
                response = baloot.getBuyList(get_buy_list_info);

                break;

            default:
                response.put("success", false);
                response.put("data", ERROR_INVALID_COMMAND);

        }
        return response;
    }
}
