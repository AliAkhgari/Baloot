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

public class removeFromBuyListController {
    private final Baloot baloot;
    private static final String NOT_FOUND_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/404.html";

    public removeFromBuyListController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void removeFromBuyList(Javalin app) throws IOException {
        app.post("/removeFromBuyList", ctx -> {
            String commodityId = ctx.formParam("commodityId");
            String userId = ctx.formParam("userId");

            if (commodityId == null)
                ctx.redirect("/403");

            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                User user = baloot.getUserById(userId);
                user.remove_item_from_buy_list(commodity);

            } catch (ExceptionHandler e) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();
                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            }

        });
    }
}
