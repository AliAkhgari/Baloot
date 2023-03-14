package controllers;

import application.Baloot;
import entities.Commodity;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class commoditiesController {
    private static final String COMMODITIES_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/Commodities.html";
    private static final String COMMODITY_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/Commodity.html";
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

//    public void getCommodity(Javalin app) throws IOException {
//        app.get("/commodities/:commodity_id", ctx -> {
//            Commodity commodity;
//            try {
//                commodity = baloot.getCommodityById(Integer.parseInt(ctx.pathParam("commodity_id")));
//                String commodityPageHtml = generateCommodityPage(commodity);
//                ctx.contentType("text/html").result(commodityPageHtml);
//            } catch (Exception e) {
//                ctx.redirect("/404");
//            }
//            ArrayList<Commodity> commodities = baloot.getCommodities();
//            String commoditiesHtml = generateCommoditiesTable(commodities);
//            ctx.contentType("text/html").result(commoditiesHtml);
//        });
//    }

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

//    private String generateCommodityPage(Commodity commodity) throws IOException {
//        File htmlFile = new File(COMMODITY_HTML_TEMPLATE_FILE);
//        String htmlTemplate = Jsoup.parse(htmlFile, "UTF-8").toString();
//
//        Document doc = Jsoup.parse(htmlTemplate);
//        Element ul = doc.selectFirst("ul");
//
//        List<String> movie_info_ids = Arrays.asList("id", "name", "providerId", "price", "categories",
//                "rating", "inStock");
////        List<String> movie_info_items = getMovieInfoItemsList(movie);
//
//        int index = 0;
//        for (String element_id : movie_info_ids) {
//            ul.getElementById(element_id).append(commodity.get(index));
//            index += 1;
//        }

//    }
}
