package controllers;

import Exceptions.InvalidPriceRange;
import Exceptions.NotExistentCommodity;
import application.Baloot;
import entities.Comment;
import entities.Commodity;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

// todo: add html file paths to define
public class CommoditiesController {
    private static final String COMMODITIES_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/Commodities.html";
    private static final String COMMODITY_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/Commodity.html";
    private final Baloot baloot;

    public CommoditiesController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void getCommodities(Javalin app) throws IOException {
        app.get("/commodities", ctx -> {
            ArrayList<Commodity> commodities = baloot.getCommodities();
            String commoditiesHtml = generateCommoditiesTable(commodities);
            ctx.contentType("text/html").result(commoditiesHtml);
        });
    }

    public void getCommodity(Javalin app) {
        app.get("/commodities/{commodity_id}", ctx -> {
            String commodityId = ctx.pathParam("commodity_id");
            try {
                Commodity commodity = baloot.getCommodityById(Integer.parseInt(commodityId));
                String commodityPageHtml = generateCommodityPage(commodity);
                ctx.contentType("text/html").result(commodityPageHtml);
            } catch (NotExistentCommodity e) {
                ctx.redirect("/404");
            }
        });
    }

    private String generateCommoditiesTable(ArrayList<Commodity> commodities) throws IOException {
        File htmlFile = new File(COMMODITIES_HTML_TEMPLATE_FILE);
        String htmlTemplate = Jsoup.parse(htmlFile, "UTF-8").toString();

        Document doc = Jsoup.parse(htmlTemplate);
        Element table = doc.selectFirst("table");

        Function<Commodity, Element> rowToHtml = commodity -> {
            Element row = new Element("tr");
            row.append("<td>" + commodity.getId() + "</td>");
            row.append("<td>" + commodity.getName() + "</td>");
            row.append("<td>" + commodity.getProviderId() + "</td>");
            row.append("<td>" + commodity.getPrice() + "</td>");
            row.append("<td>" + commodity.getCategories() + "</td>");
            row.append("<td>" + commodity.getRating() + "</td>");
            row.append("<td>" + commodity.getInStock() + "</td>");
            row.append("<td><a href=\"/commodities/" + commodity.getId() + "\">Link</a></td>");
            return row;
        };

        for (Commodity commodity : commodities) {
            Element row = rowToHtml.apply(commodity);
            table.appendChild(row);
        }

        return doc.toString();
    }

    private String generateCommodityPage(Commodity commodity) throws IOException {
        File htmlFile = new File(COMMODITY_HTML_TEMPLATE_FILE);
        String htmlTemplate = Jsoup.parse(htmlFile, "UTF-8").toString();

        Document doc = Jsoup.parse(htmlTemplate);

        // commodity info
        Element idElement = doc.getElementById("id");
        Element nameElement = doc.getElementById("name");
        Element providerIdElement = doc.getElementById("providerId");
        Element priceElement = doc.getElementById("price");
        Element categories = doc.getElementById("categories");
        Element rating = doc.getElementById("rating");
        Element inStock = doc.getElementById("inStock");

        idElement.text("Id: " + commodity.getId());
        nameElement.text("Name: " + commodity.getName());
        providerIdElement.text("Provider Id: " + commodity.getProviderId());
        priceElement.text("Price: " + commodity.getPrice());
        categories.text("Categories: " + commodity.getCategories().toString());
        rating.text("Rating: " + commodity.getRating());
        inStock.text("In Stock: " + commodity.getInStock());

        // rate commodity
        doc.getElementById("rate_commodity_id").attr("value", String.valueOf(commodity.getId()));

        // add to buy list
        doc.getElementById("add_to_buy_list_commodity_id").attr("value", String.valueOf(commodity.getId()));

        // vote comment
        Element table = doc.selectFirst("table");
        Function<Comment, Element> rowToHtml = comment -> {
            Element row = new Element("tr");
            row.append("<td>" + comment.getUserEmail() + "</td>");
            row.append("<td>" + comment.getText() + "</td>");
            row.append("<td>" + comment.getDate() + "</td>");

            Element likeTd = new Element("td");
            Element likeLabel = new Element("label").attr("for", "like_comment_id").text(String.valueOf(comment.getLike()));
            Element likeCommentIdInput = new Element("input").attr("id", "like_comment_id").attr("type", "hidden").attr("name", "comment_id").attr("value", String.valueOf(comment.getId()));
            Element likeButton = new Element("button").attr("type", "submit").attr("name", "vote").attr("value", "1").text("like");
            likeTd.appendChild(likeLabel);
            likeTd.appendChild(likeCommentIdInput);
            likeTd.appendChild(likeButton);
            row.appendChild(likeTd);

            Element dislikeTd = new Element("td");
            Element dislikeLabel = new Element("label").attr("for", "dislike_comment_id").text(String.valueOf(comment.getDislike()));
            Element dislikeCommentIdInput = new Element("input").attr("id", "dislike_comment_id").attr("type", "hidden").attr("name", "comment_id").attr("value", String.valueOf(comment.getId()));
            Element dislikeButton = new Element("button").attr("type", "submit").attr("name", "vote").attr("value", "-1").text("dislike");
            dislikeTd.appendChild(dislikeLabel);
            dislikeTd.appendChild(dislikeCommentIdInput);
            dislikeTd.appendChild(dislikeButton);
            row.appendChild(dislikeTd);

            return row;
        };

        for (Comment comment : baloot.getCommentsForCommodity(commodity.getId())) {
            Element row = rowToHtml.apply(comment);
            table.appendChild(row);
        }

        return doc.toString();
    }

    public void searchCommoditiesBasedOnPrice(Javalin app) {
        app.get("/commodities/search/{start_price}/{end_price}", ctx -> {
            float start_price = Float.parseFloat(ctx.pathParam("start_price"));
            float end_price = Float.parseFloat(ctx.pathParam("end_price"));
            try {
                ArrayList<Commodity> commodities = baloot.filterCommoditiesByPrice(start_price, end_price);
                String commoditiesHtml = generateCommoditiesTable(commodities);
                ctx.contentType("text/html").result(commoditiesHtml);
            } catch (InvalidPriceRange e) {
                ctx.redirect("/403");
            }
        });
    }

    // fixme: categories or category???
    public void searchCommoditiesBasedOnCategories(Javalin app) {
        app.get("/commodities/search/{categories}", ctx -> {
            String category = ctx.pathParam("categories");

            ArrayList<Commodity> commodities = baloot.filterCommoditiesByCategory(category);
            String commoditiesHtml = generateCommoditiesTable(commodities);
            ctx.contentType("text/html").result(commoditiesHtml);
        });
    }
}
