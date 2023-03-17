package application;

import Exceptions.*;
import database.Database;
import entities.Comment;
import entities.Commodity;
import entities.Provider;
import entities.User;

import java.io.IOException;
import java.util.ArrayList;

// todo: html files refactoring
public class Baloot {
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

        user.addBuyItem(commodity);
    }

    public void rateCommodity(String userId, String commodityId, String rate) throws InvalidRateFormat, InvalidRateRange, MissingUserId, MissingCommodityId, NotExistentUser, NotExistentCommodity {
        if (userId == null)
            throw new MissingUserId();
        if (commodityId == null)
            throw new MissingCommodityId();
        int rateNumber;
        // todo: check with float.
        try {
            rateNumber = Integer.parseInt(rate);
        } catch (NumberFormatException e) {
            throw new InvalidRateFormat();
        }
        if (rateNumber < 1 || rateNumber > 10)
            throw new InvalidRateRange();

        User user = getUserById(userId);
        Commodity commodity = getCommodityById(Integer.parseInt(commodityId));

        commodity.addRate(userId, rateNumber);
    }

    public void removeCommodityFromUserBuyList(String userId, String commodityId) throws MissingUserId, MissingCommodityId, NotExistentUser, NotExistentCommodity, CommodityIsNotInBuyList {
        if (userId == null)
            throw new MissingUserId();
        if (commodityId == null)
            throw new MissingCommodityId();

        User user = getUserById(userId);
        Commodity commodity = getCommodityById(Integer.parseInt(commodityId));

        user.removeItemFromBuyList(commodity);
    }

    public void addCreditToUser(String userId, String credit) throws MissingUserId, MissingCreditValue, InvalidCreditFormat, InvalidCreditRange, NotExistentUser {
        if (userId == null)
            throw new MissingUserId();
        if (credit == null)
            throw new MissingCreditValue();
        float creditNumber;
        try {
            creditNumber = Float.parseFloat(credit);
        } catch (NumberFormatException e) {
            throw new InvalidCreditFormat();
        }
        if (creditNumber < 0)
            throw new InvalidCreditRange();

        User user = getUserById(userId);
        user.addCredit(creditNumber);
    }

    public void moveCommoditiesFromBuyListToPurchasedList(String userId) throws MissingUserId, NotExistentUser, InsufficientCredit {
        if (userId == null)
            throw new MissingUserId();

        User user = getUserById(userId);
        for (Commodity commodity : user.getBuyList()) {
            user.addPurchasedItem(commodity);
            user.withdrawCredit(commodity.getPrice());
        }
        user.setBuyList(new ArrayList<>());
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

        switch (vote) {
            case "1" -> comment.addUserVote(userId, "like");
            case "0" -> comment.addUserVote(userId, "neutral");
            case "-1" -> comment.addUserVote(userId, "dislike");
            default -> throw new InvalidVoteFormat();
        }
    }

    public User getUserById(String userId) throws NotExistentUser {
        for (User user : database.getUsers())
            if (user.getUserId().equals(userId))
                return user;

        throw new NotExistentUser();
    }

    public Provider getProviderById(int providerId) throws NotExistentProvider {
        for (Provider provider : database.getProviders())
            if (provider.getId() == providerId)
                return provider;

        throw new NotExistentProvider();
    }

    public Commodity getCommodityById(int commodityId) throws NotExistentCommodity {
        for (Commodity commodity : database.getCommodities())
            if (commodity.getId() == commodityId)
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

    public ArrayList<Commodity> filterCommoditiesByPrice(float startPrice, float endPrice) throws InvalidPriceRange {
        if (startPrice > endPrice || endPrice < 0)
            throw new InvalidPriceRange();

        ArrayList<Commodity> result = new ArrayList<>();
        for (Commodity commodity : database.getCommodities())
            if (commodity.getPrice() >= startPrice && commodity.getPrice() <= endPrice)
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
