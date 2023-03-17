package controllers;

import io.javalin.Javalin;
import org.jsoup.Jsoup;

import java.io.File;

import static defines.HtmlTemplates.*;

public class ResponseController {


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
