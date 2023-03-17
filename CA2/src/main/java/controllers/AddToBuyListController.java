package controllers;

import application.Baloot;
import entities.Commodity;
import entities.ExceptionHandler;
import entities.User;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

public class AddToBuyListController {
    private final Baloot baloot;

    public AddToBuyListController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void addToBuyList(Javalin app) {
        app.post("/addToBuyList", ctx -> {
            String commodityId = ctx.formParam("commodity_id");
            String userId = ctx.formParam("user_id");

            if ((commodityId == null) || (userId == null))
                ctx.redirect("/403");

            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                User user = baloot.getUserById(userId);
                user.add_buy_item(commodity);
                ctx.redirect("/200");

            } catch (ExceptionHandler e) {
                ctx.redirect("/404");
            }

        });
    }

    // fixme: Is it allowed to keep duplicate commodities in buy list?
    public void showAddToBuyListPage(Javalin app) {
        app.get("/addToBuyList/{user_id}/{commodity_id}", ctx -> {
            String commodityId = ctx.pathParam("commodity_id");
            String userId = ctx.pathParam("user_id");
            
            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                User user = baloot.getUserById(userId);
                user.add_buy_item(commodity);
                ctx.redirect("/200");

            } catch (ExceptionHandler e) {
                ctx.redirect("/404");
            }

        });
    }
}
