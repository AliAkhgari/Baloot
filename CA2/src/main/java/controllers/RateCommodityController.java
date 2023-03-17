package controllers;

import application.Baloot;
import entities.Commodity;
import entities.ExceptionHandler;
import entities.User;
import io.javalin.Javalin;

public class RateCommodityController {

    private final Baloot baloot;

    public RateCommodityController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void rateCommodity(Javalin app) {
        app.post("/rateCommodity", ctx -> {
            String commodityId = ctx.formParam("commodity_id");
            String userId = ctx.formParam("user_id");
            String rate_str = ctx.formParam("quantity");

            if ((commodityId == null) || (userId == null) || (rate_str == null))
                ctx.redirect("/403");

            int rate = Integer.parseInt(rate_str);
            if (rate > 10 || rate < 0)
                ctx.redirect("/403");

            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                User user = baloot.getUserById(userId);
                commodity.add_rate(userId, rate);
                ctx.redirect("/200");

            } catch (ExceptionHandler e) {
                ctx.redirect("/404");
            }

        });
    }

    public void showRateCommodityPage(Javalin app) {
        app.get("/rateCommodity/{user_id}/{commodity_id}/{rate}", ctx -> {
            String commodityId = ctx.pathParam("commodity_id");
            String userId = ctx.pathParam("user_id");
            String rate_str = ctx.pathParam("rate");

            int rate = Integer.parseInt(rate_str);
            if (rate > 10 || rate < 0)
                ctx.redirect("/403");

            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                User user = baloot.getUserById(userId);
                commodity.add_rate(userId, rate);
                ctx.redirect("/200");

            } catch (ExceptionHandler e) {
                ctx.redirect("/404");
            }

        });
    }

}
