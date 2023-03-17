package controllers;

import application.Baloot;
import entities.Commodity;
import entities.ExceptionHandler;
import entities.User;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class addToBuyListController {
    private final Baloot baloot;
    private static final String NOT_FOUND_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/404.html";

    public addToBuyListController(Baloot baloot) {
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

            } catch (ExceptionHandler e) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();
                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            }

        });
    }

    public void showAddToBuyListPage(Javalin app) {
        app.get("/addToBuyList/{user_id}/{commodity_id}", ctx -> {
            String commodityId = ctx.pathParam("commodity_id");
            String userId = ctx.pathParam("user_id");

            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                User user = baloot.getUserById(userId);
                user.add_buy_item(commodity);

            } catch (ExceptionHandler e) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();
                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            }

        });
    }
}
