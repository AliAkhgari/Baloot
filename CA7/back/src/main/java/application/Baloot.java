package application;

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

//    public void login(String userId, String password) throws NotExistentUser, IncorrectPassword {
//        User user = this.getUserById(userId);
//        if (!user.getPassword().equals(password))
//            throw new IncorrectPassword();
//    }

//    public void addCommodityToUserBuyList(String userId, String commodityId) throws NotExistentUser, NotExistentCommodity, AlreadyInBuyList {
//        User user = getUserById(userId);
//        Commodity commodity = getCommodityById(commodityId);
//
//        user.addBuyItem(commodity);
//    }

//    public void removeCommodityFromUserBuyList(String userId, String commodityId) throws MissingUserId, MissingCommodityId, NotExistentUser, NotExistentCommodity, CommodityIsNotInBuyList {
//        if (userId == null)
//            throw new MissingUserId();
//        if (commodityId == null)
//            throw new MissingCommodityId();
//
//        User user = getUserById(userId);
//        Commodity commodity = getCommodityById(commodityId);
//
//        user.removeItemFromBuyList(commodity);
//    }

//    public float getCurrentBuyListPrice(User user) {
//        float total = 0;
//        for (var entry : new ArrayList<>(user.getBuyList().entrySet())) {
//            try {
//                Commodity commodity = getCommodityById(entry.getKey());
//                total += commodity.getPrice() * entry.getValue();
//            } catch (NotExistentCommodity ignored) {
//            }
//        }
//
//        return total;
//    }
//
//    public void withdrawPayableAmount(User user) throws InsufficientCredit, NotInStock {
//        float amount = getCurrentBuyListPrice(user);
//        float discount_amount = user.getCurrentDiscountAmount() * amount;
//        user.withdrawCredit(amount - discount_amount);
//        user.addCurrentDiscountToUsed();
//
//        for (var entry : new ArrayList<>(user.getBuyList().entrySet())) {
//            user.addPurchasedItem(entry.getKey(), entry.getValue());
//            try {
//                Commodity commodity = getCommodityById(entry.getKey());
//                commodity.updateInStock(-entry.getValue());
//            } catch (NotExistentCommodity ignored) {
//            } catch (NotInStock e) {
//                throw new NotInStock();
//            }
//        }
//
//        user.setBuyList(new HashMap<>());
//    }

//    public void moveCommoditiesFromBuyListToPurchasedList(String userId) throws MissingUserId, NotExistentUser, InsufficientCredit, NotInStock {
//        if (userId == null) throw new MissingUserId();
//
//        User user = getUserById(userId);
//        for (var entry : new ArrayList<>(user.getBuyList().entrySet())) {
//            user.addPurchasedItem(entry.getKey(), entry.getValue());
//            try {
//                Commodity commodity = getCommodityById(entry.getKey());
//                commodity.updateInStock(-entry.getValue());
//            } catch (NotExistentCommodity ignored) {
//            } catch (NotInStock e) {
//                throw new NotInStock();
//            }
//        }
//
//        user.setBuyList(new HashMap<>());
//    }

//    public void userVoteComment(String userId, String commentId, String vote) throws MissingUserId, MissingCommentId, MissingVoteValue, NotExistentUser, NotExistentComment, InvalidVoteFormat {
//        if (userId == null)
//            throw new MissingUserId();
//        if (commentId == null)
//            throw new MissingCommentId();
//        if (vote == null)
//            throw new MissingVoteValue();
//
//        getUserById(userId);
//        Comment comment = getCommentById(Integer.parseInt(commentId));
//
//        switch (vote) {
//            case "1" -> comment.addUserVote(userId, "like");
//            case "0" -> comment.addUserVote(userId, "neutral");
//            case "-1" -> comment.addUserVote(userId, "dislike");
//            default -> throw new InvalidVoteFormat();
//        }
//    }

//    public Commodity getCommodityById(String commodityId) throws NotExistentCommodity {
//        for (Commodity commodity : Database.getInstance().getCommodities())
//            if (Objects.equals(commodity.getId(), commodityId)) return commodity;
//
//        throw new NotExistentCommodity();
//    }
//
//    public ArrayList<Commodity> getSortedCommoditiesByRate(ArrayList<Commodity> commodities) {
//        ArrayList<Commodity> sortedList = new ArrayList<>(commodities);
//        sortedList.sort(new SortCommodities("rating"));
//        return sortedList;
//    }
//
//    public ArrayList<Commodity> getSortedCommoditiesByPrice(ArrayList<Commodity> commodities) {
//        ArrayList<Commodity> sortedList = new ArrayList<>(commodities);
//        sortedList.sort(new SortCommodities("price"));
//        return sortedList;
//    }
//
//    public void checkDiscountExpiration(User user, String discountCode) throws ExpiredDiscount {
//        if (user.getUsedDiscounts().contains(discountCode)) {
//            throw new ExpiredDiscount();
//        }
//    }
}
