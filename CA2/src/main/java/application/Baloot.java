package application;

import Exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Database;
import entities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static defines.defines.*;

// todo: refactor test class names
// todo: naming consistency!!!
// todo: html files refactoring
// todo: user_name -> userId
public class Baloot {
    private final ObjectMapper objectMapper = new ObjectMapper();
    Database database = new Database();

    public void fetchAndStoreDataFromAPI() {
        DataParser dataParser = new DataParser(database);

        try {
            dataParser.getUsersList();
            dataParser.getProvidersList();
            dataParser.getCommoditiesList();
            dataParser.getCommentsList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCommodityToUserBuyList(String userId, String commodityId) throws MissingUserId, MissingCommodityId, NotExistentUser, NotExistentCommodity {
        if (userId == null)
            throw new MissingUserId();
        if (commodityId == null)
            throw new MissingCommodityId();

        User user = getUserById(userId);
        Commodity commodity = getCommodityById(Integer.parseInt(commodityId));

        user.add_buy_item(commodity);
    }

    public void rateCommodity(String userId, String commodityId, String rate) throws InvalidRateFormat, InvalidRateRange, MissingUserId, MissingCommodityId, NotExistentUser, NotExistentCommodity {
        if (userId == null)
            throw new MissingUserId();
        if (commodityId == null)
            throw new MissingCommodityId();
        int rate_number;
        // todo: check with float.
        try {
            rate_number = Integer.parseInt(rate);
        } catch (NumberFormatException e) {
            throw new InvalidRateFormat();
        }
        if (rate_number < 1 || rate_number > 10)
            throw new InvalidRateRange();

        User user = getUserById(userId);
        Commodity commodity = getCommodityById(Integer.parseInt(commodityId));

        commodity.add_rate(userId, rate_number);
    }

    public void removeCommodityFromUserBuyList(String userId, String commodityId) throws MissingUserId, MissingCommodityId, NotExistentUser, NotExistentCommodity, CommodityIsNotInBuyList {
        if (userId == null)
            throw new MissingUserId();
        if (commodityId == null)
            throw new MissingCommodityId();

        User user = getUserById(userId);
        Commodity commodity = getCommodityById(Integer.parseInt(commodityId));

        user.remove_item_from_buy_list(commodity);
    }

    public void addCreditToUser(String userId, String credit) throws MissingUserId, MissingCreditValue, InvalidCreditFormat, InvalidCreditRange, NotExistentUser {
        if (userId == null)
            throw new MissingUserId();
        if (credit == null)
            throw new MissingCreditValue();
        float credit_number;
        try {
            credit_number = Float.parseFloat(credit);
        } catch (NumberFormatException e) {
            throw new InvalidCreditFormat();
        }
        if (credit_number < 0)
            throw new InvalidCreditRange();

        User user = getUserById(userId);
        user.addCredit(credit_number);
    }

    public void moveCommoditiesFromBuyListToPurchasedList(String userId) throws MissingUserId, NotExistentUser, InsufficientCredit {
        if (userId == null)
            throw new MissingUserId();

        User user = getUserById(userId);
        for (Commodity commodity : user.getBuy_list()) {
            user.add_purchased_item(commodity);
            user.decreaseCredit(commodity.getPrice());
        }
        user.setBuy_list(new ArrayList<>());
    }

    public void userVoteComment(String userId, String commentId, String vote) throws MissingUserId, MissingCommentId, MissingVoteValue, NotExistentUser, NotExistentComment, InvalidVoteFormat {
        if (userId == null)
            throw new MissingUserId();
        if (commentId == null)
            throw new MissingCommentId();
        if (vote == null)
            throw new MissingVoteValue();

        User user = getUserById(userId);
        Comment comment = getCommentById(Integer.parseInt(commentId));

        if (vote.equals("1"))
            comment.addUserVote(userId, "like");
        else if (vote.equals("0"))
            comment.addUserVote(userId, "neutral");
        else if (vote.equals("-1"))
            comment.addUserVote(userId, "dislike");
        else
            throw new InvalidVoteFormat();
    }

    public User getUserById(String userId) throws NotExistentUser {
        for (User user : database.getUsers())
            if (user.getUsername().equals(userId))
                return user;

        throw new NotExistentUser();
    }

    public Provider getProviderById(int provider_id) throws NotExistentProvider {
        for (Provider provider : database.getProviders())
            if (provider.getId() == provider_id)
                return provider;

        throw new NotExistentProvider();
    }

    public Commodity getCommodityById(int commodity_id) throws NotExistentCommodity {
        for (Commodity commodity : database.getCommodities())
            if (commodity.getId() == commodity_id)
                return commodity;

        throw new NotExistentCommodity();
    }

    public ArrayList<Commodity> getCommodities() {
        return database.getCommodities();
    }

    public ArrayList<Commodity> getCommoditiesProvidedByProvider(int providerId) {
        ArrayList<Commodity> commodities = new ArrayList<>();
        for (Commodity commodity : database.getCommodities())
            if (commodity.getProviderId() == providerId)
                commodities.add(commodity);

        return commodities;
    }

    // todo: set comments for commodity when reading from database
    public ArrayList<Comment> getCommentsForCommodity(int commodityId) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : database.getComments())
            if (comment.getCommodityId() == commodityId)
                comments.add(comment);

        return comments;
    }

    public Comment getCommentById(int commentId) throws NotExistentComment {
        for (Comment comment : database.getComments())
            if (comment.getId() == commentId)
                return comment;

        throw new NotExistentComment();
    }

    public ArrayList<Commodity> filterCommoditiesByPrice(float start_price, float end_price) throws InvalidPriceRange {
        if (start_price > end_price || end_price < 0)
            throw new InvalidPriceRange();

        ArrayList<Commodity> result = new ArrayList<>();
        for (Commodity commodity : database.getCommodities())
            if (commodity.getPrice() >= start_price && commodity.getPrice() <= end_price)
                result.add(commodity);

        return result;
    }

    public ArrayList<Commodity> filterCommoditiesByCategory(String category) {
        ArrayList<Commodity> result = new ArrayList<>();
        for (Commodity commodity : database.getCommodities())
            if (commodity.getCategories().contains(category))
                result.add(commodity);

        return result;
    }
}
