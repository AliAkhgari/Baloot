package controllers;

import application.Baloot;
import entities.Commodity;
import entities.ExceptionHandler;
import entities.User;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

public class RemoveFromBuyListController {
    private final Baloot baloot;

    public RemoveFromBuyListController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void removeFromBuyList(Javalin app) {
        app.post("/removeFromBuyList", ctx -> {
            String commodityId = ctx.formParam("commodityId");
            String userId = ctx.formParam("userId");

            if (commodityId == null)
                ctx.redirect("/403");

            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                User user = baloot.getUserById(userId);
                user.remove_item_from_buy_list(commodity);
                ctx.redirect("/200");
            } catch (ExceptionHandler e) {
                ctx.redirect("/404");
            }
        });
    }

    public void showRemoveFromBuyListPage(Javalin app) {
        app.get("/removeFromBuyList/{user_id}/{commodity_id}", ctx -> {
            String commodityId = ctx.pathParam("commodity_id");
            String userId = ctx.pathParam("user_id");

            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                User user = baloot.getUserById(userId);
                user.remove_item_from_buy_list(commodity);
                ctx.redirect("/200");

            } catch (ExceptionHandler e) {
                ctx.redirect("/404");
            }

        });
    }
}
