package controllers;

import Exceptions.MissingCommodityId;
import Exceptions.MissingUserId;
import Exceptions.NotExistentCommodity;
import Exceptions.NotExistentUser;
import application.Baloot;
import io.javalin.Javalin;

public class AddToBuyListController {
    private final Baloot baloot;
    public AddToBuyListController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void addToBuyList(Javalin app) {
        app.post("/addToBuyList", ctx -> {
            String userId = ctx.formParam("user_id");
            String commodityId = ctx.formParam("commodity_id");

            try {
                baloot.addCommodityToUserBuyList(userId, commodityId);
                ctx.redirect("/200");
            } catch (MissingCommodityId | MissingUserId e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentCommodity e2) {
                ctx.redirect("/404");
            }

        });
    }

    // fixme: Is it allowed to keep duplicate commodities in buy list?
    public void showAddToBuyListPage(Javalin app) {
        app.get("/addToBuyList/{user_id}/{commodity_id}", ctx -> {
            String userId = ctx.pathParam("user_id");
            String commodityId = ctx.pathParam("commodity_id");

            try {
                baloot.addCommodityToUserBuyList(userId, commodityId);
                ctx.redirect("/200");
            } catch (MissingCommodityId | MissingUserId e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentCommodity e2) {
                ctx.redirect("/404");
            }

        });
    }
}
