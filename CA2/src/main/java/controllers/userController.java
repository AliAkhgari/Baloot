package controllers;

import application.Baloot;
import entities.Commodity;
import entities.Provider;
import entities.User;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;


public class userController {
    private final Baloot baloot;
    private static final String USER_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/User.html";
    private static final String NOT_FOUND_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/404.html";

    public userController(Baloot baloot) {
        this.baloot = baloot;
    }

    public void getUser(Javalin app) {
        app.get("/users/{user_id}", ctx -> {
            User user = baloot.getUserById(ctx.pathParam("user_id"));
            if (user == null) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();

                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            } else {
                String userHtml = generateUserHtml(user);
                ctx.contentType("text/html").result(userHtml);
            }
        });
    }

    public void addCreditToUser(Javalin app) {
        app.get("/users/{user_id}/{credit}", ctx -> {
            User user = baloot.getUserById(ctx.pathParam("user_id"));
            int credit = Integer.parseInt(ctx.pathParam("credit"));
            if (user == null) {
                File notFoundHtmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
                String htmlTemplate = Jsoup.parse(notFoundHtmlFile, "UTF-8").toString();

                Document doc = Jsoup.parse(htmlTemplate);
                ctx.contentType("text/html").result(doc.toString());
            } else {
                String userHtml = generateUserHtml(user);
                ctx.contentType("text/html").result(userHtml);
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

        return doc.toString();
    }

}
