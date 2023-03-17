package application;

import controllers.*;
import io.javalin.Javalin;

import java.io.IOException;

import static defines.Endpoints.SERVER_PORT;


public class Main {
    public static void main(String[] args) {
        Baloot baloot = new Baloot();

        Javalin app = Javalin.create().start(SERVER_PORT);

        baloot.fetchAndStoreDataFromAPI();

        CommoditiesController commoditiesController = new CommoditiesController(baloot);
        ProviderController providerController = new ProviderController(baloot);
        UserController userController = new UserController(baloot);
        VoteCommentController voteCommentController = new VoteCommentController(baloot);
        RateCommodityController rateCommodityController = new RateCommodityController(baloot);
        AddToBuyListController addToBuyListController = new AddToBuyListController(baloot);
        RemoveFromBuyListController removeFromBuyListController = new RemoveFromBuyListController(baloot);
        ResponseController responseController = new ResponseController();

        app.routes(() -> {
            try {
                commoditiesController.getCommodities(app);
                commoditiesController.getCommodity(app);
                commoditiesController.searchCommoditiesBasedOnPrice(app);
                commoditiesController.searchCommoditiesBasedOnCategories(app);

                providerController.getProvider(app);

                userController.getUser(app);
                userController.increaseUserCredit(app);
                userController.addToPurchasedList(app);

                voteCommentController.voteComment(app);
                voteCommentController.showVoteCommentPage(app);

                rateCommodityController.rateCommodity(app);
                rateCommodityController.showRateCommodityPage(app);

                addToBuyListController.addToBuyList(app);
                addToBuyListController.showAddToBuyListPage(app);

                removeFromBuyListController.removeFromBuyList(app);
                removeFromBuyListController.showRemoveFromBuyListPage(app);

                responseController.showSuccessfulPage(app);
                responseController.showForbiddenPage(app);
                responseController.showNotFoundPage(app);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
