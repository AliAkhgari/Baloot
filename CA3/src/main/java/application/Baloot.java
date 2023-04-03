package application;

import database.Database;
import entities.Comment;
import entities.Commodity;
import entities.Provider;
import entities.User;
import exceptions.*;

import java.io.IOException;
import java.util.ArrayList;

public class Baloot {
    private static Baloot instance;

    private Baloot() {
    }

    public static Baloot getInstance() {
        if (instance == null) {
            instance = new Baloot();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void fetchAndStoreDataFromAPI() {
        DataParser dataParser = new DataParser(Database.getInstance());

        try {
            dataParser.getUsersList();
            dataParser.getProvidersList();
            dataParser.getCommoditiesList();
            dataParser.getCommentsList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(String userId, String password) throws NotExistentUser, IncorrectPassword {
        User user = this.getUserById(userId);
        if (!user.getPassword().equals(password))
            throw new IncorrectPassword();
    }

    public void addCommodityToUserBuyList(String userId, String commodityId) throws MissingUserId, MissingCommodityId, NotExistentUser, NotExistentCommodity, AlreadyInBuyList {
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
        try {
            rateNumber = Integer.parseInt(rate);
        } catch (NumberFormatException e) {
            throw new InvalidRateFormat();
        }
        if (rateNumber < 1 || rateNumber > 10)
            throw new InvalidRateRange();

        getUserById(userId);
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

        getUserById(userId);
        Comment comment = getCommentById(Integer.parseInt(commentId));

        switch (vote) {
            case "1" -> comment.addUserVote(userId, "like");
            case "0" -> comment.addUserVote(userId, "neutral");
            case "-1" -> comment.addUserVote(userId, "dislike");
            default -> throw new InvalidVoteFormat();
        }
    }

    public User getUserById(String userId) throws NotExistentUser {
        for (User user : Database.getInstance().getUsers())
            if (user.getUsername().equals(userId))
                return user;

        throw new NotExistentUser();
    }

    public Provider getProviderById(int providerId) throws NotExistentProvider {
        for (Provider provider : Database.getInstance().getProviders())
            if (provider.getId() == providerId)
                return provider;

        throw new NotExistentProvider();
    }

    public Commodity getCommodityById(int commodityId) throws NotExistentCommodity {
        for (Commodity commodity : Database.getInstance().getCommodities())
            if (commodity.getId() == commodityId)
                return commodity;

        throw new NotExistentCommodity();
    }

    public ArrayList<Commodity> getCommodities() {
        return Database.getInstance().getCommodities();
    }

    public ArrayList<Commodity> getCommoditiesProvidedByProvider(int providerId) {
        ArrayList<Commodity> commodities = new ArrayList<>();
        for (Commodity commodity : Database.getInstance().getCommodities())
            if (commodity.getProviderId() == providerId)
                commodities.add(commodity);

        return commodities;
    }

    public ArrayList<Comment> getCommentsForCommodity(int commodityId) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : Database.getInstance().getComments())
            if (comment.getCommodityId() == commodityId)
                comments.add(comment);

        return comments;
    }

    public Comment getCommentById(int commentId) throws NotExistentComment {
        for (Comment comment : Database.getInstance().getComments())
            if (comment.getId() == commentId)
                return comment;

        throw new NotExistentComment();
    }

    public ArrayList<Commodity> filterCommoditiesByPrice(String startPrice, String endPrice) throws InvalidPriceRange, MissingStartOrEndPrice {
        if (startPrice == null || endPrice == null)
            throw new MissingStartOrEndPrice();
        float startPriceInt = Float.parseFloat(startPrice);
        float endPriceInt = Float.parseFloat(endPrice);
        if (startPriceInt > endPriceInt || endPriceInt < 0)
            throw new InvalidPriceRange();

        ArrayList<Commodity> result = new ArrayList<>();
        for (Commodity commodity : Database.getInstance().getCommodities())
            if (commodity.getPrice() >= startPriceInt && commodity.getPrice() <= endPriceInt)
                result.add(commodity);

        return result;
    }

    public ArrayList<Commodity> filterCommoditiesByCategory(String category) throws MissingCategory {
        if (category == null)
            throw new MissingCategory();

        ArrayList<Commodity> result = new ArrayList<>();
        for (Commodity commodity : Database.getInstance().getCommodities())
            if (commodity.getCategories().contains(category))
                result.add(commodity);

        return result;
    }

    public ArrayList<Commodity> getUserBuyList(String userId) throws NotExistentUser {
        User user = getUserById(userId);
        return user.getBuyList();
    }

    public void addUser(User user) {
        Database.getInstance().addUser(user);
    }

    public void addCommodity(Commodity commodity) {
        Database.getInstance().addCommodity(commodity);
    }

    public void addProvider(Provider provider) {
        Database.getInstance().addProvider(provider);
    }

    public void addComment(Comment comment) {
        Database.getInstance().addComment(comment);
    }

    public ArrayList<User> getUsers() {
        return Database.getInstance().getUsers();
    }
}
