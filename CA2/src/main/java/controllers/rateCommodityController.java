package controllers;

import application.Baloot;
import entities.Comment;
import entities.Commodity;
import entities.ExceptionHandler;
import entities.User;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class rateCommodityController {

    private final Baloot baloot;
    private static final String NOT_FOUND_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/404.html";

    public rateCommodityController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void rateCommodity(Javalin app) throws IOException {
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

            } catch (ExceptionHandler e) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();
                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            }

        });
    }

}
