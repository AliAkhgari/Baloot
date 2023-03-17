package controllers;

import Exceptions.*;
import application.Baloot;
import io.javalin.Javalin;

public class RemoveFromBuyListController {
    private final Baloot baloot;

    public RemoveFromBuyListController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void removeFromBuyList(Javalin app) {
        app.post("/removeFromBuyList", ctx -> {
            String userId = ctx.formParam("userId");
            String commodityId = ctx.formParam("commodityId");

            try {
                baloot.removeCommodityFromUserBuyList(userId, commodityId);
                ctx.redirect("/200");
            } catch (MissingUserId | MissingCommodityId e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentCommodity | CommodityIsNotInBuyList e2) {
                ctx.redirect("/404");
            }
        });
    }

    public void showRemoveFromBuyListPage(Javalin app) {
        app.get("/removeFromBuyList/{user_id}/{commodity_id}", ctx -> {
            String userId = ctx.pathParam("user_id");
            String commodityId = ctx.pathParam("commodity_id");

            try {
                baloot.removeCommodityFromUserBuyList(userId, commodityId);
                ctx.redirect("/200");
            } catch (MissingUserId | MissingCommodityId e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentCommodity | CommodityIsNotInBuyList e2) {
                ctx.redirect("/404");
            }
        });
    }
}
