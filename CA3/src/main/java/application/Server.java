package application;

import controllers.*;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;

import java.io.IOException;

public class Server {
    private final Baloot baloot;
    private final Javalin app;

    public Server(Baloot baloot, Javalin app) {
        this.app = app;
        this.baloot = baloot;
    }

    public void start() {
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
                userController.increaseUserCreditPost(app);

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

        app.exception(NotFoundResponse.class, (e, ctx) -> ctx.redirect("/404"));
    }
}

