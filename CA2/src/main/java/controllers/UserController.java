package controllers;

import exceptions.*;
import application.Baloot;
import entities.Commodity;
import entities.User;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import static defines.HtmlTemplates.USER_HTML_TEMPLATE_FILE;

// todo: better naming for get and post
public class UserController {
    private final Baloot baloot;

    public UserController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void getUser(Javalin app) {
        app.get("/users/{user_id}", ctx -> {
            try {
                User user = baloot.getUserById(ctx.pathParam("user_id"));
                String userHtml = generateUserHtml(user);
                ctx.contentType("text/html").result(userHtml);
            } catch (NotExistentUser e) {
                ctx.redirect("/404");
            }
        });
    }

    public void increaseUserCredit(Javalin app) {
        app.get("/addCredit/{user_id}/{credit}", ctx -> {
            String user_id = ctx.pathParam("user_id");
            String credit = ctx.pathParam("credit");

            try {
                baloot.addCreditToUser(user_id, credit);
                ctx.redirect("/200");
            } catch (MissingUserId | MissingCreditValue | InvalidCreditFormat | InvalidCreditRange e) {
                ctx.redirect("/403");
            } catch (NotExistentUser e2) {
                ctx.redirect("/404");
            }
        });
    }

    public void increaseUserCreditPost(Javalin app) {
        app.post("/addCredit", ctx -> {
            String user_id = ctx.formParam("userId");
            String credit = ctx.formParam("credit_value");

            try {
                baloot.addCreditToUser(user_id, credit);
                ctx.redirect("/200");
            } catch (MissingUserId | MissingCreditValue | InvalidCreditFormat | InvalidCreditRange e) {
                ctx.redirect("/403");
            } catch (NotExistentUser e2) {
                ctx.redirect("/404");
            }
        });
    }

    public void addToPurchasedList(Javalin app) {
        app.post("/addToPurchasedList", ctx -> {
            String userId = ctx.formParam("userId");

            try {
                baloot.moveCommoditiesFromBuyListToPurchasedList(userId);
                ctx.redirect("/200");
            } catch (MissingUserId | InsufficientCredit e) {
                ctx.redirect("/403");
            } catch (NotExistentUser e2) {
                ctx.redirect("/404");
            }
        });
    }

    private String generateUserHtml(User user) throws IOException {
        File htmlFile = new File(USER_HTML_TEMPLATE_FILE);
        String htmlTemplate = Jsoup.parse(htmlFile, "UTF-8").toString();

        Document doc = Jsoup.parse(htmlTemplate);

        Element usernameElement = doc.getElementById("username");
        Element emailElement = doc.getElementById("email");
        Element birthDateElement = doc.getElementById("birthDate");
        Element addressElement = doc.getElementById("address");
        Element creditElement = doc.getElementById("credit");

        usernameElement.text("Username: " + user.getUsername());
        emailElement.text("Email: " + user.getEmail());
        birthDateElement.text("Birth Date: " + user.getBirthDate());
        addressElement.text(user.getAddress());
        creditElement.text("Credit: " + user.getCredit());

        // add to purchased list
        doc.getElementById("form_payment").attr("value", String.valueOf(user.getUsername()));

        // add credit
        doc.getElementById("form_add_credit").attr("value", String.valueOf(user.getUsername()));

        Element buyListTable = doc.select("table").first();
        Function<Commodity, Element> buyListRowToHtml = commodity -> {
            Element row = new Element("tr");
            row.append("<td>" + commodity.getId() + "</td>");
            row.append("<td>" + commodity.getName() + "</td>");
            row.append("<td>" + commodity.getProviderId() + "</td>");
            row.append("<td>" + commodity.getPrice() + "</td>");
            row.append("<td>" + String.join(",", commodity.getCategories()) + "</td>");
            row.append("<td>" + commodity.getRating() + "</td>");
            row.append("<td>" + commodity.getInStock() + "</td>");
            row.append("<td>" + "<a href='/commodities/" + commodity.getId() + "'>Link</a>" + "</td>");
            row.append("<td><form action='/removeFromBuyList' method='POST' >" +
                    "<input id='form_commodity_id' type='hidden' name='commodityId' value='" + commodity.getId() + "'>" +
                    "<input id='form_user_id' type='hidden' name='userId' value='" + user.getUsername() + "'>" +
                    "<button type='submit'>Remove</button></form></td>");

            return row;
        };

        for (Commodity commodity : user.getBuyList()) {
            Element row = buyListRowToHtml.apply(commodity);
            buyListTable.appendChild(row);
        }

        Element purchasedListTable = doc.select("table").get(1);
        Function<Commodity, Element> purchasedListRowToHtml = commodity -> {
            Element row = new Element("tr");
            row.append("<td>" + commodity.getId() + "</td>");
            row.append("<td>" + commodity.getName() + "</td>");
            row.append("<td>" + commodity.getProviderId() + "</td>");
            row.append("<td>" + commodity.getPrice() + "</td>");
            row.append("<td>" + String.join(",", commodity.getCategories()) + "</td>");
            row.append("<td>" + commodity.getRating() + "</td>");
            row.append("<td>" + commodity.getInStock() + "</td>");
            row.append("<td>" + "<a href='/commodities/" + commodity.getId() + "'>Link</a>" + "</td>");

            return row;
        };

        for (Commodity commodity : user.getPurchasedList()) {
            Element row = purchasedListRowToHtml.apply(commodity);
            purchasedListTable.appendChild(row);
        }

        return doc.toString();
    }

}
