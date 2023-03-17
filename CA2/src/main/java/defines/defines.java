package defines;

// todo: separate errors and other defines
// todo: every first char of words should not be capitalized
public class defines {
    public static final String ADD_USER_COMMAND = "addUser";
    public static final String ADD_PROVIDER_COMMAND = "addProvider";
    public static final String ADD_COMMODITY_COMMAND = "addCommodity";
    public static final String GET_COMMODITIES_LIST_COMMAND = "getCommoditiesList";
    public static final String RATE_COMMODITY_COMMAND = "rateCommodity";
    public static final String ADD_TO_BUY_LIST_COMMAND = "addToBuyList";
    public static final String REMOVE_FROM_BUY_LIST_COMMAND = "removeFromBuyList";
    public static final String GET_COMMODITY_BY_ID_COMMAND = "getCommodityById";
    public static final String GET_COMMODITIES_BY_CATEGORY_COMMAND = "getCommoditiesByCategory";
    public static final String GET_BUY_LIST_COMMAND = "getBuyList";
    public static final String ERROR_INVALID_COMMAND = "Invalid Command: %s";
    public static final String ERROR_INVALID_USERNAME = "Username must contain only letters, numbers, and underscores.";
    public static final String ERROR_NOT_EXISTENT_PROVIDER = "Provider Does Not Exist.";
    public static final String ERROR_NOT_EXISTENT_USER = "User Does Not Exist.";
    public static final String ERROR_NOT_EXISTENT_COMMODITY = "Commodity Does Not Exist.";
    public static final String ERROR_NOT_EXISTENT_COMMENT = "Comment Does Not Exist.";
    public static final String ERROR_INSUFFICIENT_CREDIT = "Insufficient Credit.";
    public static final String ERROR_INVALID_SCORE_RANGE = "Score Should be Between 1 to 10.";
    public static final String ERROR_INVALID_COMMENT_VOTE = "Vote Is Not Valid.";
    public static final String ERROR_COMMODITY_IS_ALREADY_IN_BUY_LIST = "Commodity Is Already In The Buy List.";
    public static final String ERROR_COMMODITY_IS_NOT_IN_BUY_LIST = "Commodity is not in the buy list.";
    public static final String ERROR_COMMODITY_IS_NOT_IN_STOCK = "Commodity is not in stock.";

    public static final String ERROR_MISSING_USER_ID = "User ID cannot be null.";
    public static final String ERROR_MISSING_COMMODITY_ID = "Commodity ID cannot be null.";
    public static final String ERROR_MISSING_CREDIT_VALUE = "Credit value cannot be null.";
    public static final String ERROR_MISSING_COMMENT_ID = "Comment ID cannot be null.";
    public static final String ERROR_MISSING_VOTE_VALUE = "Vote value cannot be null.";

    public static final String ERROR_INVALID_RATE_FORMAT = "Rate should be an integer.";
    public static final String ERROR_INVALID_CREDIT_FORMAT = "Credit should be a float.";
    public static final String ERROR_INVALID_VOTE_FORMAT = "Invalid vote: vote should be either 1, 0, or -1.";

    public static final String ERROR_INVALID_RATE_RANGE = "Rate value must be an integer between 1 and 10";
    public static final String ERROR_INVALID_CREDIT_RANGE = "Credit value must be a positive float";


    public static final String ERROR_INVALID_PRICE_RANGE = "Invalid Price Range.";
    public static final String ADDED_SUCCESSFULLY_RESPONSE = "%s Added Successfully!";
    public static final String USER_DATA_UPDATED = "User %s data Updated Successfully!";
    public static final String RATE_TO_COMMODITY_RESPONSE = "The rate was successfully added to the system.";

    public static final String ADD_COMMODITY_TO_BUY_LIST_RESPONSE = "The commodity with %d id was successfully added to the buy list of the user %s.";
    public static final String REMOVE_COMMODITY_FROM_BUY_LIST_RESPONSE = "The commodity with %d id was successfully removed from the buy list of the user %s.";

}
