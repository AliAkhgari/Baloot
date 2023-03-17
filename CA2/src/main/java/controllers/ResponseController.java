package controllers;

import io.javalin.Javalin;
import org.jsoup.Jsoup;

import java.io.File;

public class ResponseController {
    private static final String OK_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/200.html";
    private static final String FORBIDDEN_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/403.html";
    private static final String NOT_FOUND_HTML_TEMPLATE_FILE = "CA2/src/main/java/resources/404.html";

    public void showSuccessfulPage(Javalin app) {
        app.get("/200", ctx -> {
            File htmlFile = new File(OK_HTML_TEMPLATE_FILE);
            String htmlTemplate = Jsoup.parse(htmlFile, "UTF-8").toString();
            ctx.html(htmlTemplate);
        });
    }

    public void showForbiddenPage(Javalin app) {
        app.get("/403", ctx -> {
            File htmlFile = new File(FORBIDDEN_HTML_TEMPLATE_FILE);
            String htmlTemplate = Jsoup.parse(htmlFile, "UTF-8").toString();
            ctx.html(htmlTemplate);
        });
    }

    public void showNotFoundPage(Javalin app) {
        app.get("/404", ctx -> {
            File htmlFile = new File(NOT_FOUND_HTML_TEMPLATE_FILE);
            String htmlTemplate = Jsoup.parse(htmlFile, "UTF-8").toString();
            ctx.html(htmlTemplate);
        });
    }
}
