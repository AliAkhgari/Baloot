package controllers;

import Exceptions.*;
import application.Baloot;
import io.javalin.Javalin;

public class RateCommodityController {

    private final Baloot baloot;

    public RateCommodityController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void rateCommodity(Javalin app) {
        app.post("/rateCommodity", ctx -> {
            String userId = ctx.formParam("user_id");
            String commodityId = ctx.formParam("commodity_id");
            String rate = ctx.formParam("quantity");

            try {
                baloot.rateCommodity(userId, commodityId, rate);
                ctx.redirect("/200");
            } catch (MissingUserId | MissingCommodityId | InvalidRateFormat | InvalidRateRange e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentCommodity e2) {
                ctx.redirect("/404");
            }
        });
    }

    public void showRateCommodityPage(Javalin app) {
        app.get("/rateCommodity/{user_id}/{commodity_id}/{rate}", ctx -> {
            String userId = ctx.pathParam("user_id");
            String commodityId = ctx.pathParam("commodity_id");
            String rate = ctx.pathParam("rate");

            try {
                baloot.rateCommodity(userId, commodityId, rate);
                ctx.redirect("/200");
            } catch (MissingUserId | MissingCommodityId | InvalidRateFormat | InvalidRateRange e) {
                ctx.redirect("/403");
            } catch (NotExistentUser | NotExistentCommodity e2) {
                ctx.redirect("/404");
            }
        });
    }

}
