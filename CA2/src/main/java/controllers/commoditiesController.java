package controllers;

import application.Baloot;
import entities.Comment;
import entities.Commodity;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

public class commoditiesController {
    private static final String COMMODITIES_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/Commodities.html";
    private static final String COMMODITY_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/Commodity.html";
    private static final String NOT_FOUND_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/404.html";

    private final Baloot baloot;

    public commoditiesController(Baloot baloot) {
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
            Commodity commodity;
            try {
                commodity = baloot.getCommodityById(Integer.parseInt(ctx.pathParam("commodity_id")));
                String commodityPageHtml = generateCommodityPage(commodity);
                ctx.contentType("text/html").result(commodityPageHtml);
            } catch (Exception e) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();
                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
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

        doc.getElementById("rate_commodity_id").attr("value", String.valueOf(commodity.getId()));

        Element table = doc.selectFirst("table");
        Function<Comment, Element> rowToHtml = comment -> {
            Element row = new Element("tr");
            row.append("<td>" + comment.getUserEmail() + "</td>");
            row.append("<td>" + comment.getText() + "</td>");
            row.append("<td>" + comment.getDate() + "</td>");

            Element likeTd = new Element("td");
            Element likeLabel = new Element("label").attr("for", "like_comment_id").text(String.valueOf(comment.getLike()));
            Element likeCommentIdInput = new Element("input").attr("id", "like_comment_id").attr("type", "hidden").attr("name", "comment_id").attr("value", String.valueOf(comment.getId()));
            Element likeButton = new Element("button").attr("type", "submit").attr("name", "vote").attr("value", "like").text("like");
            likeTd.appendChild(likeLabel);
            likeTd.appendChild(likeCommentIdInput);
            likeTd.appendChild(likeButton);
            row.appendChild(likeTd);

            Element dislikeTd = new Element("td");
            Element dislikeLabel = new Element("label").attr("for", "dislike_comment_id").text(String.valueOf(comment.getDislike()));
            Element dislikeCommentIdInput = new Element("input").attr("id", "dislike_comment_id").attr("type", "hidden").attr("name", "comment_id").attr("value", String.valueOf(comment.getId()));
            Element dislikeButton = new Element("button").attr("type", "submit").attr("name", "vote").attr("value", "dislike").text("dislike");
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
}
