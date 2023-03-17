package controllers;

import Exceptions.*;
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
            } catch (MissingCommodityId | MissingUserId | AlreadyInBuyList e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentCommodity e2) {
                ctx.redirect("/404");
            }

        });
    }

    public void showAddToBuyListPage(Javalin app) {
        app.get("/addToBuyList/{user_id}/{commodity_id}", ctx -> {
            String userId = ctx.pathParam("user_id");
            String commodityId = ctx.pathParam("commodity_id");

            try {
                baloot.addCommodityToUserBuyList(userId, commodityId);
                ctx.redirect("/200");
            } catch (MissingCommodityId | MissingUserId | AlreadyInBuyList e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentCommodity e2) {
                ctx.redirect("/404");
            }

        });
    }
}
