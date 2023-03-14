package controllers;

import application.Baloot;
import entities.Commodity;
import entities.Provider;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class providerController {
    private final Baloot baloot;
    private static final String PROVIDER_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/Provider.html";
    private static final String NOT_FOUND_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/404.html";

    public providerController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void getProvider(Javalin app) {
        app.get("/providers/{provider_id}", ctx -> {
            Provider provider = baloot.getProviderById(Integer.parseInt(ctx.pathParam("provider_id")));
            if (provider == null) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();

                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            } else {
                String providerHtml = generateProviderHtml(provider);
                ctx.contentType("text/html").result(providerHtml);
            }
        });
    }

    private String generateProviderHtml(Provider provider) throws IOException {
        File htmlFile = new File(PROVIDER_HTML_TEMPLATE_FILE);
        String htmlTemplate = Jsoup.parse(htmlFile, "UTF-8").toString();

        Document doc = Jsoup.parse(htmlTemplate);

        Element idElement = doc.getElementById("id");
        Element nameElement = doc.getElementById("name");
        Element registryDateElement = doc.getElementById("registryDate");

        idElement.text("Id: " + provider.getId());
        nameElement.text("Name: " + provider.getName());
        registryDateElement.text("Registry Date: " + provider.getRegistryDate());

        Element table = doc.selectFirst("table");

        Function<Commodity, Element> rowToHtml = commodity -> {
            Element row = new Element("tr");
            row.append("<td>" + commodity.getId() + "</td>");
            row.append("<td>" + commodity.getName() + "</td>");
            row.append("<td>" + commodity.getPrice() + "</td>");
            row.append("<td>" + commodity.getCategories() + "</td>");
            row.append("<td>" + commodity.getRating() + "</td>");
            row.append("<td>" + commodity.getInStock() + "</td>");
            row.append("<td><a href=\"/commodities/" + commodity.getId() + "\">Link</a></td>");
            return row;
        };

        for (Commodity commodity : baloot.getCommoditiesProvidedByProvider(provider.getId())) {
            Element row = rowToHtml.apply(commodity);
            table.appendChild(row);
        }

        System.out.println(doc.toString());

        return doc.toString();
    }

}
